package cn.wolfcode.web.modules.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.ImportGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 17:58
 * @email chenshiyun2011@163.com
 */
@Data
@TableName("SYS_DICE_INFO")
public class SysDict {

    public static final String PARENT_DICE_CODE = "所属字典编码";
    public static final String DICE_NAME = "字典名称";
    public static final String DICE_CODE = "字典编码";
    public static final String DICE_VALUE = "字典值";
    public static final String DESCRIPTION = "描述";
    @JsonProperty("diceId")
    @TableId(type = IdType.ASSIGN_ID)
    @NotBlank(message = "字典ID不能为空", groups = {UpdateGroup.class})
    private String id;
    @TableField("DICE_NAME")
    @Excel(name = DICE_NAME)
    @NotBlank(message = "字典名称不能为空", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    @Length(max = 50, message = "字典名称不能超出50个字符", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    private String diceName;
    @TableField("DICE_CODE")
    @Excel(name = DICE_CODE)
    @NotBlank(message = "字典编码不能为空", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    @Length(max = 50, message = "字典编码不能超出50个字符", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    private String diceCode;
    @TableField("PARENT_DICE_CODE")
    @Excel(name = PARENT_DICE_CODE)
    @Length(max = 50, message = "所属字典编码不能超出50个字符", groups = {ImportGroup.class})
    private String parentDiceCode;
    @TableField("DICE_VALUE")
    @Excel(name = DICE_VALUE)
    @NotBlank(message = "字典值不能为空", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    @Length(max = 255, message = "字典值不能超出255个字符", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    private String diceValue;
    @TableField("SORT")
    private String sort;
    @TableField("DESCRIPTION")
    @Length(max = 255, message = "描述不能超出255个字符", groups = {AddGroup.class, UpdateGroup.class, ImportGroup.class})
    private String description;
    @TableField("DISABLE")
    private DeleteType disable;


    public String getParentDiceCode() {
        return StringUtils.isBlank(parentDiceCode) ? "" : parentDiceCode;
    }
}
