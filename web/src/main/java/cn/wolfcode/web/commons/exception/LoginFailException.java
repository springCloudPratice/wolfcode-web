package cn.wolfcode.web.commons.exception;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/16 14:13
 * @email chenshiyun2011@163.com
 */
public class LoginFailException extends RuntimeException {

    public LoginFailException() {
        super();
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(Throwable cause) {
        super(cause);
    }

    protected LoginFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
