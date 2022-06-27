package cn.wolfcode.web.modules.orderinfo.entity;

import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 谭浩
 * @since 2022-06-22
 */
public class TbOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 所属客户id
     */
    @NotBlank(message = "!客户不能为空!",groups = {AddGroup.class})
    private String custId;

    /**
     * 产品名称
     */
    @NotBlank(message = "!产品名称不能为空!",groups = {AddGroup.class})
    private String prodName;

    /**
     * 产品数量
     */

    private Integer amounts;

    /**
     * 产品价格
     */

    private Integer price;

    /**
     * 状态 0 未发货 1 已发货 2 已收货
     */

    private Integer status;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货人电话
     */
    private String linkPhone;

    /**
     * 收货地址
     */

    private String address;

    /**
     * 物流
     */
    private String logistcs;

    /**
     * 物流单号
     */
    private String logisticsCode;

    /**
     * 发货时间
     */
    private LocalDateTime deliverTime;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 录入人
     */
    private String inputUserId;

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
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getLogistcs() {
        return logistcs;
    }

    public void setLogistcs(String logistcs) {
        this.logistcs = logistcs;
    }
    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }
    public LocalDateTime getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(LocalDateTime deliverTime) {
        this.deliverTime = deliverTime;
    }
    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public LocalDateTime getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }



    @Override
    public String toString() {
        return "TbOrderInfo{" +
            "id=" + id +
            ", custId=" + custId +
            ", prodName=" + prodName +
            ", amounts=" + amounts +
            ", price=" + price +
            ", status=" + status +
            ", receiver=" + receiver +
            ", linkPhone=" + linkPhone +
            ", address=" + address +
            ", logistcs=" + logistcs +
            ", logisticsCode=" + logisticsCode +
            ", deliverTime=" + deliverTime +
            ", receiveTime=" + receiveTime +
            ", inputTime=" + inputTime +
            ", inputUserId=" + inputUserId +
        "}";
    }
}
