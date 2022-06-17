package cn.wolfcode.web.modules.sys.service;

import cn.wolfcode.web.modules.sys.form.LoginForm;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/15 14:40
 * @email chenshiyun2011@163.com
 */
public interface SignService {

    /**
     * 用户登录
     *
     * @param form
     * @param session
     * @throws Exception
     */
    void checkLogin(LoginForm form, HttpSession session) throws Exception;

    /**
     * 生成校验码图片
     *
     * @param session
     * @return
     * @throws Exception
     */
    BufferedImage createVerifyCodeImage(HttpSession session) throws Exception;
}
