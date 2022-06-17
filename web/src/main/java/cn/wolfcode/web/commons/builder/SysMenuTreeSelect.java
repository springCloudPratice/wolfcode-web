package cn.wolfcode.web.commons.builder;

import cn.wolfcode.web.commons.enums.DeleteType;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @Author Aimer
 * @Date 2019/4/15 15:10
 * @Version 1.0
 */
@Getter
@Setter
public class SysMenuTreeSelect {
    private String id;
    private String name;
    private String parentMenuCode;
    private String menuIcon;
    private Integer menuType;
    private Integer sort;
    private String menuCode;
    private String menuUrl;
    private String authorization;
    private DeleteType disable;
    private List<SysMenuTreeSelect> children;

    public void addChildren(SysMenuTreeSelect sysMenuTreeSelect) {
        if (Objects.isNull(children)) {
            children = Lists.newArrayList();
        }
        children.add(sysMenuTreeSelect);
    }


    public SysMenuTreeSelect(String id, String name, String parentMenuCode, String menuIcon, Integer menuType, Integer sort, String menuCode, String menuUrl, String authorization, DeleteType disable) {
        this.id = id;
        this.name = name;
        this.parentMenuCode = parentMenuCode;
        this.menuIcon = menuIcon;
        this.menuType = menuType;
        this.sort = sort;
        this.menuCode = menuCode;
        this.menuUrl = menuUrl;
        this.authorization = authorization;
        this.disable = disable;
    }
}