package cn.wolfcode.web.modules.linkmanvisit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import link.ahsj.core.annotations.AddGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 拜访信息表
 * </p>
 *
 * @author 姚鸿伟
 * @since 2022-06-22
 */
public class TbVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private String id;

    /**
     * 客户id
     */
    @NotBlank(message = "!拜访客户不能为空!",groups = {AddGroup.class})
    private String custId;

    /**
     * 联系人id
     */
    @NotBlank(message = "!客户联系人不能为空!",groups = {AddGroup.class})
    private String linkmanId;

    /**
     * 拜访方式, 1 上门走访, 2 电话拜访
     */
    @NotNull(message = "!拜访方式不能为空，请选择拜访方式!",groups = {AddGroup.class})
    private Integer visitType;

    /**
     * 拜访原因
     */
    @Length(max = 100,message = "!拜访原因不能超过1000个字符!",groups ={AddGroup.class})
    @NotBlank(message = "!未填拜访原因!",groups = {AddGroup.class})
    private String visitReason;

    /**
     * 交流内容
     */
    @Length(max = 1000,message = "!职位不能超过1000个字符!",groups ={AddGroup.class})
    @NotBlank(message = "!未填交流内容!",groups = {AddGroup.class})
    private String content;

    /**
     * 拜访时间
     */
    private LocalDate visitDate;

    /**
     * 录入人
     */
    private String inputUser;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;
    /**
     * 客户名字
     */
    @TableField(exist = false)
    private String custName;
    /**
     * 联系人名字
     */
    @TableField(exist = false)
    private String linkmanName;

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }
    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }
    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }
    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }
    public LocalDateTime getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }

    @Override
    public String toString() {
        return "TbVisit{" +
            "id=" + id +
            ", custId=" + custId +
            ", linkmanId=" + linkmanId +
            ", visitType=" + visitType +
            ", visitReason=" + visitReason +
            ", content=" + content +
            ", visitDate=" + visitDate +
            ", inputUser=" + inputUser +
            ", inputTime=" + inputTime +
        "}";
    }
}
