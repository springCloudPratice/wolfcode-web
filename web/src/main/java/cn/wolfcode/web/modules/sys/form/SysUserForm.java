package cn.wolfcode.web.modules.sys.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/6/11 7:16
 * @mail chenshiyun2011@163.com
 */
@Setter
@Getter
public class SysUserForm {

    private String username;
    private String nickName;
    private String phone;
    private String deptId;
    private String businessLevel;
    private Integer disable;
    private List<String> roles;

}
