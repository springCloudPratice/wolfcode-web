package cn.wolfcode.web.commons.builder;

import com.google.common.collect.Lists;
import cn.wolfcode.web.modules.sys.entity.SysMenu;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/5/28 16:21
 * @email chenshiyun2011@163.com
 */
public class MenuTreeSelectBuilder {

    private List<SysMenu> menus;

    public MenuTreeSelectBuilder(List<SysMenu> menus) {
        this.menus = menus;
    }

    public List<SysMenuTreeSelect> build() {
        //获取一级菜单
        List<SysMenuTreeSelect> firstMenus = Lists.newArrayList();
        List<SysMenu> temps = new ArrayList<>(10);
        if (!CollectionUtils.isEmpty(menus)) {
            menus.forEach(item -> {
                if (StringUtils.isEmpty(item.getParentMenuCode())) {
                    firstMenus.add(new SysMenuTreeSelect(item.getMenuId(),
                            item.getMenuName(),
                            item.getParentMenuCode(),
                            item.getMenuIcon(),
                            item.getMenuType(),
                            item.getSort(),
                            item.getMenuCode(),
                            item.getMenuUrl(),
                            item.getAuthorization(),
                            item.getDisable()));
                    temps.add(item);
                }
            });
        }
        menus.removeAll(temps);
        //构建成树形结构
        buildMenuTreeSelectList(firstMenus, menus);
        return firstMenus;
    }

    private void buildMenuTreeSelectList(List<SysMenuTreeSelect> firstMenus, List<SysMenu> sysMenus) {
        List<SysMenu> copyMenus = Lists.newCopyOnWriteArrayList(sysMenus);
        for (SysMenuTreeSelect menu : firstMenus) {
            for (SysMenu m : copyMenus) {
                if (m.getParentMenuCode().equals(menu.getMenuCode())) {
                    menu.addChildren(new SysMenuTreeSelect(m.getMenuId(),
                            m.getMenuName(),
                            m.getParentMenuCode(),
                            m.getMenuIcon(),
                            m.getMenuType(),
                            m.getSort(),
                            m.getMenuCode(),
                            m.getMenuUrl(),
                            m.getAuthorization(),
                            m.getDisable()));
                    copyMenus.remove(m);
                }
            }
        }
        for (SysMenuTreeSelect menu : firstMenus) {
            if (!CollectionUtils.isEmpty(menu.getChildren())) {
                buildMenuTreeSelectList(menu.getChildren(), copyMenus);
            }
        }
    }
}
