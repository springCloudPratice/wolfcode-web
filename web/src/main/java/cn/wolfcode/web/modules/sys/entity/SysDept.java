package cn.wolfcode.web.modules.sys.entity;

import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.components.tree.IBusinessBuild;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/1/19 3:13 下午
 */
@Data
@TableName("SYS_DEPT_INFO")
@JsonIgnoreProperties({"id", "pid",})
public class SysDept implements Serializable, IBusinessBuild {

    @TableId(type = IdType.ASSIGN_ID)
    @TableField("DEPT_ID")
    private String deptId;

    @TableField("DEPT_NAME")
    @NotBlank(message = "机构名称不能空", groups = {AddGroup.class})
    private String deptName;

    @TableField("PARENT_DEPT_ID")
    private String parentDeptId;

    @TableField("DEPT_PATH")
    private String deptPath;

    /**
     * 层级
     */
    @TableField("NUMBER_LEVEL")
    private int numberLevel;
    /**
     * 业务层级  这个只给了500长度,也就是10多层可能就超长了,因为 deptId 用了几十位的
     */
    @TableField("BUSINESS_LEVEL")
    private String businessLevel;

    @TableField("DEPT_LEVEL")
    private Integer deptLevel;

    @TableField("DISABLE")
    private DeleteType disable;

    public SysDept(String deptId, String parentDeptId) {
        this.deptId = deptId;
        this.parentDeptId = parentDeptId;
    }

    public SysDept() {
    }

    @Override
    public String getPid() {
        return this.parentDeptId;
    }

    @Override
    public String getId() {
        return this.deptId;
    }
}
