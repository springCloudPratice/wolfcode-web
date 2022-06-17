package cn.wolfcode.web.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@TableName("DEMO")
@ToString
public class Demo implements Serializable {

    @TableId
    private String id;
    private String name;
    private String info;
    private LocalDateTime createTime;
    private int age;

}
