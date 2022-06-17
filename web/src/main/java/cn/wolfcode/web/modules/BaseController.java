package cn.wolfcode.web.modules;

import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/1/17 3:29 下午
 */
public abstract class BaseController {

    @Autowired
    private HttpSession session;

    protected SysUser getUser() {
        return (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);
    }
}
