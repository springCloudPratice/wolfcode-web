package cn.wolfcode.web.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户登录日志表
 * </p>
 *
 * @author Aimer
 * @since 2019-04-08
 */
@Data
@TableName("SYS_USER_LOGIN_LOG")
public class SysUserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String USER_LOGIN = "用户登录";

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 登录时间
     */
    @TableField(value = "LOGIN_TIME")
    private LocalDateTime loginTime;

    /**
     * 是否登录成功
     */
    @TableField("SUCCESS")
    private Integer success;

    /**
     * 错误日志
     */
    @TableField("ERROR_LOG")
    private String errorLog;

    /**
     * IP地址
     */
    @TableField("IP_ADDRESS")
    private String ipAddress;

    /**
     * 执行时长
     */
    @TableField("DURATION")
    private Long duration;

    /**
     * 操作时间
     */
    @TableField("OPERATION_TIME")
    private LocalDateTime operationTime;


}
