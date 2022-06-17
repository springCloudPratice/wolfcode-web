package cn.wolfcode.web.commons.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2018/12/10 15:07
 * @email chenshiyun2011@163.com
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        //字体颜色
        properties.put("kaptcha.textproducer.font.color", "1,170,237");
        properties.put("kaptcha.textproducer.font.names", "Courier");
        properties.put("kaptcha.textproducer.char.space", "5");
        properties.put("kaptcha.image.width", "219");
        properties.put("kaptcha.image.height", "86");
        //干扰线颜色
        properties.put("kaptcha.noise.color", "1,170,237");
        //验证码字体
        properties.put("kaptcha.textproducer.char.string", "1234567890");
        //背景颜色
        properties.put("kaptcha.background.clear.from", "245,245,245");
        properties.put("kaptcha.background.clear.to", "245,245,245");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
