package cn.wolfcode.web.modules.sys.form;


import cn.wolfcode.web.modules.sys.entity.SysUserOperationLog;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Aimer
 * @Date 2019/4/18 10:19
 * @Version 1.0
 */
@Getter
@Setter
public class SysOperationLogForm extends SysUserOperationLog {
    private String startDate;
    private String endDate;
}
