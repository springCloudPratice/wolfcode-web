package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysRoleByMenu;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author Aimer
 * @since 2019-04-02
 */
@Service
public interface SysRoleByMenuService extends IService<SysRoleByMenu> {

    /**
     * 删除中间表数据
     * @param roleId
     */
    void deleteRoleAndMenu(String roleId);

    /**
     * 删除中间表数据
     * @param menuId
     */
    void deleteMenuAndRole(String menuId);


}
