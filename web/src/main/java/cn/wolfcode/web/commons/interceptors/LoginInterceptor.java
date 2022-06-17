package cn.wolfcode.web.commons.interceptors;

import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.service.MenuService;
import cn.wolfcode.web.modules.sys.service.SysUserService;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import link.ahsj.core.utils.str.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/15 18:32
 * @email chenshiyun2011@163.com
 */
@Component
@Slf4j
public class LoginInterceptor extends GenericFilterBean {

    @Value("${interceptor.release.filter}")
    private String releaseFilterPath;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MenuService menuService;

    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
    private static final String AJAX_HEADER_KEY = "X-Requested-With";


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String url = request.getServletPath();
        log.debug("当前访问接口:"+url);
        //校验是否是过滤请求
        if (!UrlUtils.checkURL(releaseFilterPath, url)) {
            SysUser sysUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
            //判断用户登录状态
            if (!isAllowVisit(sysUser)) {
                response.sendRedirect(String.format("%s/sign/login.html", request.getContextPath()));
//                if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_KEY))) {
//                    throw new LoginFailException(CodeMsg.EXPIRE_LOGIN_MSG);
//                } else {
//                }
                return;
            }
            invokeConfig(sysUser);
        }

        chain.doFilter(request, response);
    }

    private void invokeConfig(SysUser sysUser) {
        List<SysMenu> authorities = menuService.selectByUserName(sysUser.getUsername());
        List<String> grants = new ArrayList<>(authorities.size() * 4);
        if (CollectionUtils.isNotEmpty(authorities)) {
            for (int i = 0; i < authorities.size(); i++) {
                SysMenu sysMenu = authorities.get(i);
                if (Objects.nonNull(sysMenu) && StringUtils.isNotEmpty(sysMenu.getAuthorization())) {
                    grants.addAll(Arrays.asList(sysMenu.getAuthorization().split(",")));
                }

            }
        }
        //去重和去空
        String[] authorityList = grants.stream().filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList()).toArray(new String[]{});

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword(), AuthorityUtils.createAuthorityList(authorityList));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    /**
     * 判断用户是否允许访问
     *
     * @param sysUser
     * @return
     */
    private boolean isAllowVisit(SysUser sysUser) {
        if (Objects.isNull(sysUser)) {
            return false;
        }
        SysUser exist = sysUserService.getById(sysUser.getUserId());
        boolean allow = true;
        if (Objects.isNull(exist) || exist.getDisable().equals(DeleteType.DISABLE) || exist.getLoginStatus().equals(DeleteType.DISABLE)) {
            allow = false;
        }
        return allow;
    }

}
