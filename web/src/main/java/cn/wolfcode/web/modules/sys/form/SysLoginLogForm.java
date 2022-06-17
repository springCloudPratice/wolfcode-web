package cn.wolfcode.web.modules.sys.form;


import cn.wolfcode.web.modules.sys.entity.SysUserLoginLog;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Aimer
 * @Date 2019/4/18 17:17
 * @Version 1.0
 */
@Getter
@Setter
public class SysLoginLogForm extends SysUserLoginLog {
    private String startDate;
    private String endDate;
}
