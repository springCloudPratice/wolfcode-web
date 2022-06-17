package cn.wolfcode.web.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.modules.sys.entity.SysUserOperationLog;
import cn.wolfcode.web.modules.sys.form.SysOperationLogForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户操作日志表 Mapper 接口
 * </p>
 *
 * @author Aimer
 * @since 2019-04-11
 */
public interface SysUserOperationLogMapper extends BaseMapper<SysUserOperationLog> {
    /**
     * 日志查询
     * @param sysOperationLogForm
     * @param page
     * @return
     */
    List<SysUserOperationLog> queryOperationList(@Param("sysOperationLogForm") SysOperationLogForm sysOperationLogForm, IPage page);

    /**
     * 查询操作类型
     * @return
     */
    List<SysUserOperationLog> queryType();
}
