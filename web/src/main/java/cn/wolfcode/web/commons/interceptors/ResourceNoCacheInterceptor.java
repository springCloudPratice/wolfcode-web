package cn.wolfcode.web.commons.interceptors;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/15 18:34
 * @email chenshiyun2011@163.com
 */
@Component
public class ResourceNoCacheInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("randomNum", IdWorker.getIdStr());
        return true;
    }
}
