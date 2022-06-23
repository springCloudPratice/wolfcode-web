package cn.wolfcode.web.modules.contractinfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 合同信息
 * </p>
 *
 * @author 写代码没有出息的
 * @since 2022-06-22
 */
@Data
public class TbContract implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户id
     */
    @NotBlank(message = "所属客户为空",groups ={AddGroup.class, UpdateGroup.class})
    private String custId;

    @TableField(exist = false)
    private String customerName;


    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称为空",groups ={AddGroup.class, UpdateGroup.class})
    private String contractName;

    /**
     * 合同编码
     */
    @NotBlank(message = "合同编码为空",groups ={AddGroup.class, UpdateGroup.class})
    private String contractCode;

    /**
     * 合同金额
     */
    @NotNull(message = "合同金额为空",groups ={AddGroup.class, UpdateGroup.class})
    private Integer amounts;

    /**
     * 合同生效开始时间
     */
    @NotNull(message = "合同生效开始时间为空",groups ={AddGroup.class, UpdateGroup.class})
    private LocalDate startDate;

    /**
     * 合同生效结束时间
     */
    @NotNull(message = "合同生效结束时间为空",groups ={AddGroup.class, UpdateGroup.class})
    private LocalDate endDate;

    /**
     * 合同内容
     */
    @NotBlank(message = "合同内容为空",groups ={AddGroup.class, UpdateGroup.class})
    private String content;

    /**
     * 是否盖章确认 0 否 1 是
     */
    @NotNull(message = "是否盖章确认为空",groups ={AddGroup.class, UpdateGroup.class})
    private Integer affixSealStatus;

    /**
     * 审核状态 0 未审核 1 审核通过 -1 审核不通过
     */
    @NotNull(message = "审核状态为空",groups ={AddGroup.class, UpdateGroup.class})
    private Integer auditStatus;

    /**
     * 是否作废 1 作废 0 在用
     */
    @NotNull(message = "是否作废为空",groups ={AddGroup.class, UpdateGroup.class})
    private Integer nullifyStatus;

    /**
     * 录入人
     */
    private String inputUser;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
