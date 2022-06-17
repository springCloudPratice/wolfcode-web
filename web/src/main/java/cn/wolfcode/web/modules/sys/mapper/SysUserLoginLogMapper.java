package cn.wolfcode.web.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.modules.sys.entity.SysUserLoginLog;
import cn.wolfcode.web.modules.sys.form.SysLoginLogForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户登录日志表 Mapper 接口
 * </p>
 *
 * @author Aimer
 * @since 2019-04-08
 */
public interface SysUserLoginLogMapper extends BaseMapper<SysUserLoginLog> {

    /**
     * 查询列表
     * @param sysLoginLogForm
     * @param page
     * @return
     */
    List<SysUserLoginLog> queryLoginLogList(@Param("sysLoginLogForm") SysLoginLogForm sysLoginLogForm, IPage page);
}
