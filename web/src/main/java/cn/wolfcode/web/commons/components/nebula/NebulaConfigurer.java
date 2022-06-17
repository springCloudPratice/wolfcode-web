package cn.wolfcode.web.commons.components.nebula;

import cn.hutool.script.JavaScriptEngine;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/15 9:41
 * @email chenshiyun2011@163.com
 */
@Configuration
public class NebulaConfigurer {

    private static final String DEFAULT_PASSWORD_ENCRYPTOR_NAME = "nebula_passwordEncryptor";
    private static final String DEFAULT_VERIFYCODE_GENERATOR_NAME = "nebula_verifyCode_generator";

    @ConditionalOnMissingBean(PasswordEncryptor.class)
    @Bean(name = DEFAULT_PASSWORD_ENCRYPTOR_NAME)
    public PasswordEncryptor passwordEncryptor() {
        String salt = "45zRG154";
        return new PasswordEncryptor() {
            @Override
            public String encrypt(String password) {
                AppAssertUtil.isBlank(password, "要加密的密码不能为空");
                String enhancer = String.format("%s%s%s%s%s", salt.charAt(0), salt.charAt(2), DigestUtils.md5Hex(password), salt.charAt(5), salt.charAt(4));
                return DigestUtils.md5Hex(enhancer);
            }

            @Override
            public String decrypt(String encryptedPassword) {
                throw new UnsupportedOperationException("不支持的解密操作");
            }
        };
    }

    @ConditionalOnMissingBean(VerifyCodeGenerator.class)
    @Bean(name = DEFAULT_VERIFYCODE_GENERATOR_NAME)
    public VerifyCodeGenerator verifyCodeGenerator() {
        return (String key, HttpSession session) -> {
            char[] ops = new char[]{'+', '-', '*'};
            Random rdm = new Random();
            int num1 = rdm.nextInt(10);
            int num2 = rdm.nextInt(10);
            int num3 = rdm.nextInt(10);
            char op1 = ops[rdm.nextInt(3)];
            char op2 = ops[rdm.nextInt(3)];

            String text = String.format("%s%s%s%s%s", num1, op1, num2, op2, num3);

            session.setAttribute(key, new JavaScriptEngine().eval(text).toString());
            return text;
        };
    }
}
