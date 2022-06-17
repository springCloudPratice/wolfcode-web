package cn.wolfcode.web.commons.exception;

import cn.hutool.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 16:03
 * @email chenshiyun2011@163.com
 */
@Controller
public class SysErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == HttpStatus.HTTP_UNAUTHORIZED) {
            return "/401";
        } else if (statusCode == HttpStatus.HTTP_NOT_FOUND) {
            return "/404";
        } else if (statusCode == HttpStatus.HTTP_FORBIDDEN) {
            return "/403";
        } else {
            return "/500";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
