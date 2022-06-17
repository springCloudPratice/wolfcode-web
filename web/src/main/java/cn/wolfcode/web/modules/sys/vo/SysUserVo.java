package cn.wolfcode.web.modules.sys.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.date.DatePattern;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/2/5 5:18 下午
 */
@Setter
@Getter
public class SysUserVo extends SysUser {

    @Excel(name = USERNAME_COL, width = 20)
    private String username;

    @Excel(name = NICKNAME_COL, orderNum = "1", width = 20)
    private String nickName;

    @Excel(name = PHONE_COL, orderNum = "4", width = 20)
    private String phone;

    @Excel(name = EMAIL_COL, orderNum = "5", width = 20)
    private String email;

    /**
     * 状态 1.正常 0.删除
     */

    private DeleteType disable;

    @Excel(name = "状态", orderNum = "6", replace = {"删除_1", "正常_0", "_null"}, width = 20)
    private int disableState;

    private LocalDateTime loginTime;

    @Excel(name = "最近登录时间", orderNum = "7", exportFormat = DatePattern.NORM_DATETIME_PATTERN, width = 20)
    private Date loginDateTime;

    /**
     * 角色名称列表
     */
    private String roles;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色名称列表
     */
    @Excel(name = "角色", orderNum = "2", width = 20)
    private String roleNames;
    /**
     * 部门路径
     */
    @Excel(name = "机构", orderNum = "3", width = 40)
    private String deptPath;

    /**
     * 角色信息
     */
    private List<SysRoleInfo> roleInfoList;

    public int getDisableState() {
        return this.disable.getValue();
    }

    public Date getLoginDateTime() {
        if (Objects.nonNull(this.loginTime)) {
            return Date.from(this.loginTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }
}
