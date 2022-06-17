package cn.wolfcode.web.commons.config;

import cn.wolfcode.web.modules.sys.service.SysConfigService;
import cn.wolfcode.web.commons.interceptors.LoginInterceptor;
import cn.wolfcode.web.commons.interceptors.ResourceNoCacheInterceptor;
import freemarker.ext.jsp.TaglibFactory;
import link.ahsj.core.http.xss.XssFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/5/6 16:10
 * @email chenshiyun2011@163.com
 */
@Configuration
public class SysMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private FreeMarkerConfigurer configurer;
    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private ResourceNoCacheInterceptor resourceNoCacheInterceptor;
    @Value("${interceptor.release.xss}")
    private String releaseXss;
    @Value("${interceptor.release.xss-pattern}")
    private String releaseXssPattern;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/", "classpath:/public/");
        registry.addResourceHandler("/layuiadmin/**").addResourceLocations("classpath:/public/layuiAdmin/");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + System.getProperties().getProperty("user.home") + "/halo/upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourceNoCacheInterceptor);
    }


    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        //开启动态的xss开关
        // boolean enabled = SysConfig.ENABLED.equals(sysConfigService.getConfig(SysConfig.SYSTEM_XSS_ENABLED));
        boolean enabled = true;
        XssFilter xssFilter = new XssFilter(releaseXss, releaseXssPattern, () -> enabled);
        FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 解决跨域问题
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(loginInterceptor);
        registration.setEnabled(false);
        return registration;
    }


    @PostConstruct
    public void freeMarkerConfigurer() {
        List<String> tlds = new ArrayList<>(1);
        tlds.add("/tags/security.tld");
        TaglibFactory taglibFactory = configurer.getTaglibFactory();
        taglibFactory.setClasspathTlds(tlds);
        if (taglibFactory.getObjectWrapper() == null) {
            taglibFactory.setObjectWrapper(configurer.getConfiguration().getObjectWrapper());
        }
    }
}
