package cn.wolfcode.web.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author Aimer
 * @Date 2019/4/11 16:40
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_ROLE_MENU")
public class SysRoleByMenu {

    private String roleId;

    private String menuCode;
}
