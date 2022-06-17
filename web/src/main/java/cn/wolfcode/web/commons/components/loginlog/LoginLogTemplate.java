package cn.wolfcode.web.commons.components.loginlog;

import com.baomidou.mybatisplus.extension.api.R;
import cn.wolfcode.web.modules.sys.entity.SysUser;

import java.util.function.Function;

/**
 * 日志模板
 *
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/19 8:56
 * @email chenshiyun2011@163.com
 */
public interface LoginLogTemplate {

    /**
     * 记录日志
     *
     * @param sysUser
     * @param function
     */
    void record(Function<SysUser, R> function, SysUser sysUser);
}
