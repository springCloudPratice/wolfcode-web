package cn.wolfcode.web.modules.custinfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import link.ahsj.core.annotations.AddGroup;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author 姚鸿伟
 * @since 2022-06-20
 */
public class TbCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 企业名称
     */
    @NotBlank(message = "请填写企业名称！",groups = {AddGroup.class})
    private String customerName;

    /**
     * 法定代表人
     */
    private String legalLeader;

    /**
     * 成立时间
     */
    private LocalDate registerDate;

    /**
     * 经营状态, 0 开业、1 注销、2 破产
     */
    private Integer openStatus;

    /**
     * 所属地区省份
     */
    private String province;

    /**
     * 注册资本,(万元)
     */
    private String regCapital;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 经营范围
     */
    private String scope;

    /**
     * 注册地址
     */
    private String regAddr;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 录入人
     */
    private String inputUserId;
    /**
     * 列表信息展示，展示省份名
     */
    @TableField(exist = false)
    private String provinceName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getLegalLeader() {
        return legalLeader;
    }

    public void setLegalLeader(String legalLeader) {
        this.legalLeader = legalLeader;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }
    public LocalDateTime getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    @Override
    public String toString() {
        return "TbCustomer{" +
            "id=" + id +
            ", customerName=" + customerName +
            ", legalLeader=" + legalLeader +
            ", registerDate=" + registerDate +
            ", openStatus=" + openStatus +
            ", province=" + province +
            ", regCapital=" + regCapital +
            ", industry=" + industry +
            ", scope=" + scope +
            ", regAddr=" + regAddr +
            ", inputTime=" + inputTime +
            ", updateTime=" + updateTime +
            ", inputUserId=" + inputUserId +
        "}";
    }
}
