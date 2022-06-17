package cn.wolfcode.web.modules.sys.entity;

import cn.wolfcode.web.commons.enums.DeleteType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Eastern unbeaten
 * @since 2020-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_FILES")
public class SysFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 删除状态
     */
    @TableField("DISABLE")
    private DeleteType disable = DeleteType.NORMAL;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 文件key
     */
    @TableField("FILE_KEY")
    private String fileKey;

    /**
     * 高度
     */
    @TableField("HEIGHT")
    private Integer height;

    /**
     * 文件头类型
     */
    @TableField("MEDIA_TYPE")
    private String mediaType;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 路径
     */
    @TableField("PATH")
    private String path;

    /**
     * 大小
     */
    @TableField("FILE_SIZE")
    private Long fileSize;

    /**
     * 后缀
     */
    @TableField("SUFFIX")
    private String suffix;

    /**
     * 压缩路径
     */
    @TableField("THUMB_PATH")
    private String thumbPath;

    /**
     * 类型
     */
    @TableField("FILE_TYPE")
    private Integer fileType;

    /**
     * 宽度
     */
    @TableField("WIDTH")
    private Integer width;


}
