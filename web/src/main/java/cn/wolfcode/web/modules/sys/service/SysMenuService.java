package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询上级
     *
     * @param parentMenuCode 父节点菜单码
     * @return
     */
    List<SysMenu> queryMenuParentName(@Param("parentMenuCode") String parentMenuCode);


    /**
     * 查询上级ID
     *
     * @param childId
     * @return
     */
    String queryParentId(@Param("childId") String childId);

    /**
     * 树形结构
     *
     * @param
     * @param page
     * @return
     */
    List<Map> queryList(IPage page);

    /**
     * 根据用户名查询对应的权限菜单。注意：admin用户直接查询全部菜单
     *
     * @param username
     * @return
     */
    List<SysMenu> selectByUserName(@Param("username") String username);


    /**
     *  查询角色的权限集
     * @return
     */
    List<SysMenu> queryRoleMenus( String roleId);

}
