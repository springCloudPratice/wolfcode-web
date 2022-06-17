package cn.wolfcode.web.commons.config;

import cn.wolfcode.web.commons.interceptors.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/5/6 16:10
 * @email chenshiyun2011@163.com
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Value("${interceptor.release.security}")
    private String securityReleasePath;

    @Override
    public void configure(WebSecurity web) {
        // "/static/**", "/images/**", "/sign/**", "/druid/**", "/error"
        //用正则表达式替换:  /{spring:^(static|druid|images|sign|druid|error)$}/**
        //解析:  正则表达式, 可拆分成 /static/** , /druid/** 等,以此类推

        // "/**/*.js", "/**/*.css", "/**/*.woff*", "/**/*.gif", "/**/*.json", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "*.ico"
        //用正则表达式替换:  /**/{spring:^.*\.((?!html).)*$}
        //解析:  匹配最后的"/"前面可以是任意表达式,最后的分隔符"/"后面必须有".",同时不以html结尾的url,如: layui.css , xx.xxx
        //      用来可以排除 .js .css .ico .woff等出了html之外的静态资源
        web.ignoring().antMatchers("/{spring:^(static|druid|images|sign|druid|error)$}/**", "/**/{spring:^.*\\.((?!html).)*$}");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        String[] split = securityReleasePath.split(",");
        httpSecurity
                .sessionManagement().maximumSessions(1).expiredUrl("/sign/login.html")
                .and()
                .and()
                .authorizeRequests()
                .antMatchers(split).permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin().loginPage("/sign/login.html").successForwardUrl("/index.html");

        httpSecurity.addFilterAt(loginInterceptor, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
