package cn.wolfcode.web.commons.redis;

/**
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @data 2019/3/20
 */
public interface RedisKeys {

    /**
     * 微信 token 唯一常量
     */
    String ACCESS_TOKEN = "server:user:token:";

    /**
     * 用户token 唯一常量
     */
    String APP_TOKEN_KEY = "server:app:user_token_key:";
    /**
     * 微信access_token
     */
    String WX_ACCESS_TOKEN = "server:app:wx_access_token";
    /**
     * tokenip地址
     */
    String TOKEN_IP = "http:web:token:ipadder:";
    /**
     * 用户token 唯一常量
     */
    String APP_TOKEN = "app:user:token:";


    String APP_USER_TOKEN_KEYS = "app:user:token_keys:";


    static String getAppToken(String str) {
        return APP_TOKEN.concat(str);
    }

    static String getAppTokenKey(String str) {
        return APP_TOKEN_KEY.concat(str);
    }

    static String getTokenIp(String str) {
        return TOKEN_IP.concat(str);
    }

    static String getAccessToken(String str) {
        return ACCESS_TOKEN.concat(str);
    }

}
