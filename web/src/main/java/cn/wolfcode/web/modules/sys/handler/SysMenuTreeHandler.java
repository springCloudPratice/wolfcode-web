package cn.wolfcode.web.modules.sys.handler;

import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.modules.sys.vo.SysMenuList;
import cn.wolfcode.web.commons.builder.SysMenuTreeSelect;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 10:57
 * @email chenshiyun2011@163.com
 */
public interface SysMenuTreeHandler {

    /***
     * 解析成树形结构
     * @param sysMenus
     * @return
     */
    List<SysMenu> parseTree(List<SysMenu> sysMenus);

    /**
     * 树结构格式封装
     * @param stringList
     * @return
     */
    List<SysMenuList>  parseList(List<SysMenuList> stringList);

    /**
     * 封装树形下拉框
     * @param sysMenuTreeSelects
     * @return
     */
    List<SysMenuTreeSelect> parseMenuList(List<SysMenuTreeSelect> sysMenuTreeSelects);
}
