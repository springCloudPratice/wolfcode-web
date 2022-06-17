package cn.wolfcode.web.commons.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/1/12 12:22 上午
 */
@Configuration
@ConfigurationProperties(prefix = "system")
@Setter
@Getter
public class SystemConfig {

    @NotNull
    private String name;
}
