package cn.wolfcode.web.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/10 13:37
 * @email chenshiyun2011@163.com
 */
@Data
@TableName("SYS_USER_ROLE")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole implements Serializable {

    private String userId;
    private String roleId;
}
