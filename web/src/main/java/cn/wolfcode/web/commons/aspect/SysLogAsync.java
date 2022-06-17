package cn.wolfcode.web.commons.aspect;

import cn.hutool.json.JSONUtil;
import cn.wolfcode.web.commons.utils.WebUtil;
import cn.wolfcode.web.modules.sys.mapper.SysUserOperationLogMapper;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.entity.SysUserOperationLog;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.utils.http.AhsjHttpUtils;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2018/12/3 10:38
 * @email chenshiyun2011@163.com
 */
@Component
@Log
public class SysLogAsync {

    @Autowired
    private SysUserOperationLogMapper sysUserOperationLogMapper;

    public void saveSysLog(HttpServletRequest request, ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        SysUserOperationLog uol = new SysUserOperationLog();
        if (request != null) {
            HttpSession session = request.getSession();
            String username = "Anonymous";
            if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute(LoginForm.LOGIN_USER_KEY))) {
                SysUser loginUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);
                username = loginUser.getUsername();
            }
            uol.setUserName(username);
            uol.setOperationModule(sysLog.module());
            uol.setOperationType(sysLog.value());
            uol.setVisitMethod(method.getName());
            uol.setParameters(JSONUtil.toJsonStr(WebUtil.getParamMap(request)));
            uol.setIpAddress(AhsjHttpUtils.getIPAddr(request));
            uol.setDuration(time);
            uol.setOperationTime(LocalDateTime.now());

            sysUserOperationLogMapper.insert(uol);
        }
    }
}
