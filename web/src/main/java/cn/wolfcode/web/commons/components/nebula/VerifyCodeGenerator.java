package cn.wolfcode.web.commons.components.nebula;

import javax.servlet.http.HttpSession;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/16 8:50
 * @email chenshiyun2011@163.com
 */
public interface VerifyCodeGenerator {

    /**
     * 生成校验码文本
     *
     * @param key
     * @param session
     * @return
     * @throws Exception
     */
    String generate(String key, HttpSession session) throws Exception;
}
