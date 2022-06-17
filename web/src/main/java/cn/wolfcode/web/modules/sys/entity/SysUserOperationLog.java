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
 * 用户操作日志表
 * </p>
 *
 * @author Aimer
 * @since 2019-04-11
 */
@Data
@TableName("SYS_USER_OPERATION_LOG")
public class SysUserOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 操作模块
     */
    @TableField("OPERATION_MODULE")
    private String operationModule;

    /**
     * 操作类型
     */
    @TableField("OPERATION_TYPE")
    private String operationType;

    /**
     * 请求方法
     */
    @TableField("VISIT_METHOD")
    private String visitMethod;

    /**
     * 请求参数
     */
    @TableField("PARAMETERS")
    private String parameters;

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
