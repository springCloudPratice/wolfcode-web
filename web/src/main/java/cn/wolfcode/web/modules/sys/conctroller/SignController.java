package cn.wolfcode.web.modules.sys.conctroller;

import cn.hutool.crypto.asymmetric.RSA;
import cn.wolfcode.web.modules.sys.service.SignService;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.commons.entity.CacheKeyConstant;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;


/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/15 14:05
 * @email chenshiyun2011@163.com
 */
@Controller
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @GetMapping("/login.html")
    public ModelAndView toLogin(HttpServletRequest request, ModelAndView mv) throws NoSuchAlgorithmException {
        RSA rsa = new RSA();
        request.getSession().setAttribute(LoginForm.KEY_PAIR_STR, rsa.getPrivateKeyBase64());

        mv.setViewName("/login");
        mv.addObject("publicKey", rsa.getPublicKeyBase64());
        return mv;
    }

    /*************************action method************************************/

    @SysLog(value = "登录验证码", module = "登录模块")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedImage image = signService.createVerifyCodeImage(request.getSession());

        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            AppAssertUtil.err("验证码生成有误");
        }
    }

    @SysLog(value = "用户登录", module = "登录模块")
    @PostMapping("/login")
    public ResponseEntity<ApiModel> login(HttpServletRequest request, @Validated @RequestBody LoginForm form) throws Exception {
        signService.checkLogin(form, request.getSession());

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = "用户退出", module = "登录模块")
    @CacheEvict(value = CacheKeyConstant.USER_CACHE_KEY, beforeInvocation = true, allEntries = true)
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/sign/login.html";
    }
}
