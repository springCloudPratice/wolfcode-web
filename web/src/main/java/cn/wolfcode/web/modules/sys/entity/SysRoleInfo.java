package cn.wolfcode.web.modules.sys.entity;

import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import link.ahsj.core.components.tree.IBusinessBuild;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author Aimer
 * @since 2019-04-02
 */
@Data
@Accessors(chain = true)
@TableName("SYS_ROLE_INFO")
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleInfo implements Serializable, IBusinessBuild {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ROLE_ID", type = IdType.ASSIGN_UUID)
    private String roleId;


    @TableField(value = "PID")
    private String pid;

    /**
     * 层级
     */
    @TableField("NUMBER_LEVEL")
    private int numberLevel;

    /**
     * 业务层级
     */
    @TableField("BUSINESS_LEVEL")
    private String businessLevel;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 角色编码
     */
    @TableField("ROLE_CODE")
    private String roleCode;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 状态
     */
    @TableField("DISABLE")
    private DeleteType disable = DeleteType.NORMAL;

    @Override
    public void setBusinessLevel(String businessLevel) {
        this.businessLevel = businessLevel;
    }

    @Override
    public void setNumberLevel(int numberLevel) {
        this.numberLevel = numberLevel;
    }

    /**
     * 菜单集合
     */
    @TableField(exist = false)
    private List<SysMenu> sysMenuList;

    public SysRoleInfo(String roleId, String roleName, String description, String roleCode, LocalDateTime createTime) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.roleCode = roleCode;
        this.createTime = createTime;
    }

    public SysRoleInfo(String roleName, String description, String roleCode, LocalDateTime createTime) {
        this.roleName = roleName;
        this.description = description;
        this.roleCode = roleCode;
        this.createTime = createTime;
    }

    public SysRoleInfo(String roleId, String pid, int numberLevel, String businessLevel, String roleName, String description, String roleCode, LocalDateTime createTime) {
        this.roleId = roleId;
        this.pid = pid;
        this.numberLevel = numberLevel;
        this.businessLevel = businessLevel;
        this.roleName = roleName;
        this.description = description;
        this.roleCode = roleCode;
        this.createTime = createTime;
    }

    public SysRoleInfo(String roleId, String pid) {
        this.roleId = roleId;
        this.pid = pid;
    }

    @Override
    public String getId() {
        return this.getRoleId();
    }


}
