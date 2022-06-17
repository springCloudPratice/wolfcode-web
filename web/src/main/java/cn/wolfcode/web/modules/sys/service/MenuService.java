package cn.wolfcode.web.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
public interface MenuService  {



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
     * 根据用户名查询对应的权限菜单
     *
     * @param username
     * @return
     */
    List<SysMenu> selectByUserName(String username);

    /**
     * 查询用户左侧菜单
     *
     * @param sysUser
     * @return
     */
    List<SysMenu> queryUserLeftMenu(SysUser sysUser);

}
