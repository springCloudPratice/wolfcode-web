package cn.wolfcode.web.modules.sys.entity;

import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.*;
import com.google.common.collect.Lists;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 14:08
 * @email chenshiyun2011@163.com
 */
@Data
@TableName("SYS_MENU_INFO")
public class SysMenu implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String menuId;

    @NotBlank(message = "菜单名称不能空", groups = {UpdateGroup.class, AddGroup.class})
    private String menuName;
    private String parentMenuCode;
    private String menuIcon;
    private Integer menuType;
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private String menuCode;
    private String menuUrl;
    private String authorization;
    private DeleteType disable = DeleteType.NORMAL;

    @TableField(exist = false)
    private List<SysMenu> childs;

    public void addChilds(SysMenu sysMenu) {
        if (Objects.isNull(childs)) {
            childs = Lists.newArrayList();
        }
        childs.add(sysMenu);
    }

    public String getParentMenuCode() {
        return StringUtils.isBlank(parentMenuCode) ? "" : parentMenuCode;
    }
}
