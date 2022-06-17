package cn.wolfcode.web.modules.sys.handler.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wolfcode.web.modules.sys.handler.SysMenuTreeHandler;
import com.google.common.collect.Lists;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import cn.wolfcode.web.modules.sys.vo.SysMenuList;
import cn.wolfcode.web.commons.builder.SysMenuTreeSelect;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 10:58
 * @email chenshiyun2011@163.com
 */
@Component
public class SysMenuHandlerImpl implements SysMenuTreeHandler {

    @Override
    public List<SysMenu> parseTree(List<SysMenu> menus) {
        //获取一级菜单
        List<SysMenu> firstMenus = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(menus)) {
            menus.forEach(item -> {
                if (StringUtils.isBlank(item.getParentMenuCode())) {
                    firstMenus.add(item);
                }
            });
        }
        menus.removeAll(firstMenus);
        //构建成树形结构
        buildMenuTree(firstMenus, menus);
        return firstMenus;
    }

    @Override
    public List<SysMenuList> parseList(List<SysMenuList> sysMenuLists) {
        //获取一级菜单
        List<SysMenuList> firstMenus = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(sysMenuLists)) {
            sysMenuLists.forEach(item -> {
                if (StringUtils.isBlank(item.getParentMenuCode())) {
                    firstMenus.add(item);
                }
            });
        }
        sysMenuLists.removeAll(firstMenus);
        //构建成树形结构
        buildMenuTreeList(firstMenus, sysMenuLists);
        return firstMenus;
    }

    @Override
    public List<SysMenuTreeSelect> parseMenuList(List<SysMenuTreeSelect> sysMenuTreeSelects) {
        //获取一级菜单
        List<SysMenuTreeSelect> firstMenus = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(sysMenuTreeSelects)) {
            sysMenuTreeSelects.forEach(item -> {
                if (StringUtils.isBlank(item.getParentMenuCode())) {
                    firstMenus.add(item);
                }
            });
        }
        sysMenuTreeSelects.removeAll(firstMenus);
        //构建成树形结构
        buildMenuTreeSelectList(firstMenus, sysMenuTreeSelects);
        return firstMenus;
    }

    private void buildMenuTreeSelectList(List<SysMenuTreeSelect> firstMenus, List<SysMenuTreeSelect> sysMenuTreeSelects) {
        List<SysMenuTreeSelect> copyMenus = Lists.newCopyOnWriteArrayList(sysMenuTreeSelects);
        for (SysMenuTreeSelect menu : firstMenus) {
            for (SysMenuTreeSelect m : copyMenus) {
                if (m.getParentMenuCode().equals(menu.getMenuCode())) {
                    menu.addChildren(m);
                    copyMenus.remove(m);
                }
            }
        }
        for (SysMenuTreeSelect menu : firstMenus) {
            if (!CollectionUtil.isEmpty(menu.getChildren())) {
                buildMenuTreeSelectList(menu.getChildren(), copyMenus);
            }
        }
    }

    private void buildMenuTree(List<SysMenu> firstMenus, List<SysMenu> menus) {
        List<SysMenu> copyMenus = Lists.newCopyOnWriteArrayList(menus);
        for (SysMenu menu : firstMenus) {
            for (SysMenu m : copyMenus) {
                if (m.getParentMenuCode().equals(menu.getMenuCode())) {
                    menu.addChilds(m);
                    copyMenus.remove(m);
                }
            }
        }
        for (SysMenu menu : firstMenus) {
            if (!CollectionUtils.isEmpty(menu.getChilds())) {
                buildMenuTree(menu.getChilds(), copyMenus);
            }
        }
    }

    private void buildMenuTreeList(List<SysMenuList> firstMenus, List<SysMenuList> sysMenuLists) {
        List<SysMenuList> copyMenus = Lists.newCopyOnWriteArrayList(sysMenuLists);
        for (SysMenuList menu : firstMenus) {
            for (SysMenuList m : copyMenus) {
                if (m.getParentMenuCode().equals(menu.getMenuCode())) {
                    menu.addData(m);
                    copyMenus.remove(m);
                }
            }
        }
        for (SysMenuList menu : firstMenus) {
            if (!CollectionUtils.isEmpty(menu.getData())) {
                buildMenuTreeList(menu.getData(), copyMenus);
            }
        }
    }
}
