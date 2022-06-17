package cn.wolfcode.web.commons.components.loginlog;

import cn.wolfcode.web.modules.sys.service.SysUserLoginLogService;
import com.baomidou.mybatisplus.extension.api.R;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.entity.SysUserLoginLog;
import link.ahsj.core.utils.http.AhsjHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;


/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/19 8:59
 * @email chenshiyun2011@163.com
 */
@Component
public class LoginLogTemplateImpl implements LoginLogTemplate {

    @Autowired
    private SysUserLoginLogService loginLogService;

    @Override
    public void record(Function<SysUser, R> f, SysUser sysUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LocalDateTime start = LocalDateTime.now();

        SysUserLoginLog loginLog = new SysUserLoginLog();

        loginLog.setUserName(Objects.nonNull(sysUser) ? sysUser.getUsername() : "");
        loginLog.setLoginTime(start);
        loginLog.setIpAddress(AhsjHttpUtils.getIPAddr(request));
        try {
            f.apply(sysUser);
        } catch (Exception e) {
            loginLog.setSuccess(0);
            loginLog.setErrorLog(e.getMessage());
            throw e;
        } finally {
            LocalDateTime end = LocalDateTime.now();
            long seconds = Duration.between(start, end).getSeconds();
            loginLog.setDuration(seconds * 1000);

            loginLogService.save(loginLog);
        }
    }
}
