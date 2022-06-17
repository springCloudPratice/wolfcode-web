package cn.wolfcode.web.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author Aimer
 * @since 2019-04-02
 */
public interface SysRoleInfoMapper extends BaseMapper<SysRoleInfo> {

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    List<SysRoleInfo> queryUserRole(@Param("userId") String userId);

}
