package cn.wolfcode.web.commons.entity;

/**
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @data 2018/11/5
 */
public interface CodeMsg {

    String DEFAULT_MSG = "";
    String SUCCESS_MSG = "操作成功";
    String SYS_ERR_MSG = "服务器处理异常";
    String VALID_ERR_MSG = "数据校验失败";
    String LOGIN_CODE_ERR_MSG = "验证码不正确";
    String ACCOUNT_LOCKED = "账号已锁定";
    String ACCOUNT_UNALLOW_LOGIN = "账号已禁用登录";
    String ACCOUNT_OR_PASSWORD_ERR_MSG = "账号或密码有误";
    String AUTH_FAIL_MSG = "无权操作";
    String EXPIRE_LOGIN_MSG = "登录已失效";
}
