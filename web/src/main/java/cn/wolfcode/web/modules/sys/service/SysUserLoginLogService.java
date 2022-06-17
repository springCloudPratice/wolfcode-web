package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysUserLoginLog;
import cn.wolfcode.web.modules.sys.form.SysLoginLogForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户登录日志表 服务类
 * </p>
 *
 * @author Aimer
 * @since 2019-04-08
 */
public interface SysUserLoginLogService extends IService<SysUserLoginLog> {
    /**
     * 查询列表
     * @param sysLoginLogForm
     * @param page
     * @return
     */
    List<SysUserLoginLog> queryLoginLogList(@Param("sysLoginLogForm") SysLoginLogForm sysLoginLogForm, IPage page);
}
