package cn.wolfcode.web.modules.appdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 姚鸿伟
 * @since 2022-06-17
 */
public class AppDemo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("AGE")
    private Integer age;

    /**
     * 信息
     */
    @TableField("INFO")
    private String info;

    /**
     * 描述
     */
    @TableField("DESCS")
    private String descs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    @Override
    public String toString() {
        return "AppDemo{" +
            "id=" + id +
            ", name=" + name +
            ", createTime=" + createTime +
            ", age=" + age +
            ", info=" + info +
            ", descs=" + descs +
        "}";
    }
}
