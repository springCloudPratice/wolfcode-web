package cn.wolfcode.web.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.wolfcode.web.modules.sys.entity.SysRoleByMenu;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Aimer
 * @Date 2019/4/11 16:43
 * @Version 1.0
 */
public interface SysRoleByMenuMapper extends BaseMapper<SysRoleByMenu> {
    /**
     * 删除中间表数据
     * @param roleId
     */
    void deleteRoleAndMenu(@Param("roleId") String roleId);

    /**
     * 删除中间表数据
     * @param menuId
     */
    void deleteMenuAndRole(@Param("menuCode") String menuId);
}
