package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysUserOperationLog;
import cn.wolfcode.web.modules.sys.form.SysOperationLogForm;

import java.util.List;

/**
 * <p>
 * 用户操作日志表 服务类
 * </p>
 *
 * @author Aimer
 * @since 2019-04-11
 */
public interface SysUserOperationLogService extends IService<SysUserOperationLog> {
    /**
     * 查询功能
     * @param sysOperationLogForm
     * @param page
     * @return
     */
    List<SysUserOperationLog> queryOperationList(SysOperationLogForm sysOperationLogForm, IPage page);

    /**
     * 查询操作类型
     * @return
     */
    List<SysUserOperationLog> queryType();
}
