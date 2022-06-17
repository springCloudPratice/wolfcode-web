package cn.wolfcode.web.modules.sys.entity;

import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/7/15 21:28
 * @mail chenshiyun2011@163.com
 */
@TableName("SYS_CONFIG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig implements Serializable {


    /**
     * 系统xss开关
     */
    public static final String SYSTEM_XSS_ENABLED = "system:xss:enabled";

    /**
     * 配置开关开启判断
     */
    public static final String ENABLED = "true";


    /**
     * 配置key 唯一
     */
    @TableId
    @NotBlank(message = "配置编码不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String configKey;

    /**
     * 所属key
     */

    @NotBlank(message = "所属配置不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String parentKey;

    /**
     * 说明
     */

    @NotBlank(message = "描述不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String info;

    /**
     * 配置的值
     */

    @NotBlank(message = "配置的值不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String configValue;

    /**
     * 0有效 其他失效
     */
    private DeleteType disable = DeleteType.NORMAL;


}
