package cn.wolfcode.web.modules.sys.form;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @Author Aimer
 * @Date 2019/4/11 10:06
 * @Version 1.0
 */
@Data
public class SysMenuList {
    private String menuId;
    private String title;
    private String parentMenuCode;
    private String menuIcon;
    private Integer menuType;
    private Integer sort;
    private String menuCode;
    private String menuUrl;
    private String authorization;
    private Integer disable;
    private List<SysMenuList> data;

    public void addData(SysMenuList sysMenuList) {
        if (Objects.isNull(data)) {
            data = Lists.newArrayList();
        }
        data.add(sysMenuList);
    }
}
