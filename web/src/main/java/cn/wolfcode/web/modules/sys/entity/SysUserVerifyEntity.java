package cn.wolfcode.web.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.baomidou.mybatisplus.annotation.TableField;
import link.ahsj.core.annotations.ImportGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/11 16:50
 * @email chenshiyun2011@163.com
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserVerifyEntity extends SysUser implements IExcelModel, IExcelDataModel {

    /**
     * 角色名称
     */
    @TableField(exist = false)
    @NotBlank(message = "角色不能为空", groups = {ImportGroup.class})
    @Length(max = 30, message = "角色长度不能超出50个字符", groups = {ImportGroup.class})
    @Excel(name = ROLE_COL)
    private String roleName;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    @NotBlank(message = "部门不能为空", groups = {ImportGroup.class})
    @Length(max = 30, message = "部门长度不能超出50个字符", groups = {ImportGroup.class})
    @Excel(name = ORGANIZE_COL)
    private String deptName;

    private String errorMsg;
    private int rowNum;

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum + 1;
    }
}
