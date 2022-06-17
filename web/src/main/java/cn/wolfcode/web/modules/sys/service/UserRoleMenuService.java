package cn.wolfcode.web.modules.sys.service;

import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.modules.sys.form.RoleMenuForm;

import java.util.List;

/**
 * 用户角色关系 角色菜单关系 相关操作
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @date 2019-08-03 15:58
 */
public interface UserRoleMenuService {

    /**
     * 查询可用的角色列表
     *
     * @return
     */
    List<SysRoleInfo> queryEnableRole();


    /**
     *  添加角色跟菜单相关关系
     * @param roleMenuForm
     */
    boolean insertRoleAndMenu(RoleMenuForm roleMenuForm);


    /**
     * 更新角色角色跟菜单相关关系
     * @param roleMenuForm
     */
    boolean updateRoleAndMenu(RoleMenuForm roleMenuForm);





}
