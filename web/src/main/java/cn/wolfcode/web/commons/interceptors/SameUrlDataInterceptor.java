package cn.wolfcode.web.commons.interceptors;

import cn.hutool.json.JSONUtil;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 17:10
 * @email chenshiyun2011@163.com
 */
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {

    private static final String REPEAT_DATA = "repeatData";
    private static final String REPEAT_TIME_LIMIT = "repeatTimeLimit";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SameUrlData annotation = method.getAnnotation(SameUrlData.class);
            if (annotation != null) {
                if (repeatDataValidator(request)) {
                    AppAssertUtil.err("你的操作过于频繁");
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 验证同一个url数据是否相同提交  ,相同返回true
     *
     * @param request
     * @return
     */
    public boolean repeatDataValidator(HttpServletRequest request) {
        String params = JSONUtil.toJsonStr(request.getParameterMap());
        String url = request.getRequestURI();
        Map<String, String> map = new HashMap<>(0);
        map.put(url, params);
        String nowUrlParams = map.toString();

        Object preUrlParams = request.getSession().getAttribute(REPEAT_DATA);
        LocalDateTime limit = (LocalDateTime) request.getSession().getAttribute(REPEAT_TIME_LIMIT);

        if (preUrlParams == null) {
            //如果上一个数据为null,表示还没有访问页面
            request.getSession().setAttribute(REPEAT_TIME_LIMIT, LocalDateTime.now());
            request.getSession().setAttribute(REPEAT_DATA, nowUrlParams);
            return false;
        } else {
            //否则，已经访问过页面
            if ((preUrlParams.toString().equals(nowUrlParams)) &&
                    ((Duration.between(limit, LocalDateTime.now()).getSeconds() <= 3L))) {
                //如果上次url+数据和本次url+数据相同且时间差不超过3秒,则表示重复添加数据
                return true;
            } else {
                //如果上次 url+数据 和本次url加数据不同，则不是重复提交
                request.getSession().setAttribute(REPEAT_TIME_LIMIT, LocalDateTime.now());
                request.getSession().setAttribute(REPEAT_DATA, nowUrlParams);
                return false;
            }
        }
    }
}
