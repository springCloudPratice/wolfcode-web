package cn.wolfcode.web.commons.components.nebula;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/15 9:38
 * @email chenshiyun2011@163.com
 */
public interface PasswordEncryptor {

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456@!Ab";

    /**
     * 加密密码
     *
     * @param password 要加密的密码
     * @return
     */
    String encrypt(String password);

    /**
     * 解密密码
     *
     * @param encryptedPassword 已加密过的密码
     * @return
     */
    String decrypt(String encryptedPassword);
}
