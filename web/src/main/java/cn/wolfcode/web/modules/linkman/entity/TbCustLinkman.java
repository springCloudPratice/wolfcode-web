package cn.wolfcode.web.modules.linkman.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 客户联系人
 * </p>
 *
 * @author 姚鸿伟
 * @since 2022-06-21
 */
public class TbCustLinkman implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户id
     */
    private String custId;

    /**
     * 联系人名字
     */
    private String linkman;

    /**
     * 性别 1 男 0 女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 联系人电话
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门
     */
    private String department;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 录入人
     */
    private String inputUser;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

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
    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "TbCustLinkman{" +
            "id=" + id +
            ", custId=" + custId +
            ", linkman=" + linkman +
            ", sex=" + sex +
            ", age=" + age +
            ", phone=" + phone +
            ", position=" + position +
            ", department=" + department +
            ", remark=" + remark +
            ", inputUser=" + inputUser +
            ", inputTime=" + inputTime +
        "}";
    }
}
