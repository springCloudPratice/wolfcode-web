package cn.wolfcode.web.modules.sys.entity;


import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.ImportGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/1 16:22
 * @email chenshiyun2011@163.com
 */
@Data
@TableName("SYS_USER_INFO")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"deleteTime", "password", "oldPassword", "createUserId"})
public class SysUser implements Serializable {

    public static final String USERNAME_COL = "账号";
    public static final String NICKNAME_COL = "姓名";
    public static final String ROLE_COL = "角色";
    public static final String ORGANIZE_COL = "机构";
    public static final String PHONE_COL = "手机号码";
    public static final String EMAIL_COL = "邮箱";

    @TableId(type = IdType.ASSIGN_ID)
    @NotBlank(message = "用户ID不能为空", groups = {UpdateGroup.class})
    private String userId;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空", groups = {AddGroup.class, ImportGroup.class})
    @Length(max = 30, message = "账号不能超出30个字符", groups = {AddGroup.class, ImportGroup.class})
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = {AddGroup.class, ImportGroup.class})
    @Length(max = 50, message = "姓名不能超出50个字符", groups = {AddGroup.class, ImportGroup.class})
    private String nickName;

    /**
     * 手机号
     */
    @Pattern(regexp = "\\d{1,11}", message = "手机号码格式不正确", groups = {AddGroup.class, ImportGroup.class})
    @NotBlank(message = "手机号码不能为空", groups = {AddGroup.class, ImportGroup.class})
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, ImportGroup.class})
    @Length(max = 50, message = "邮箱不能超出50个字符", groups = {AddGroup.class, ImportGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, ImportGroup.class})
    private String email;

    /**
     * 状态 1.正常 0.删除
     */
    private DeleteType disable;

    /**
     * 登录状态 1.允许登录 0.禁用登录
     */
    private DeleteType loginStatus;

    private String createUserId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;

    private LocalDateTime loginTime;

    private String remark;
    /**
     * 机构ID
     */
    @NotBlank(message = "机构不能为空", groups = {AddGroup.class})
    private String deptId;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private String roleId;

    /**
     * 角色ID集合
     */
    @TableField(exist = false)
    private List<String> roleIds;

    /**
     * 部门
     */
    @TableField(exist = false)
    private SysDept sysDept;

    /**
     * 角色信息
     */
    @TableField(exist = false)
    private List<SysRoleInfo> roleInfoList;

    public SysUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
