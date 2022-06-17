package cn.wolfcode.web.commons.utils;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.MediaType;

/**
 * @author Eastern unbeaten
 * @version 2.0
 * @date 2019/9/28 8:31
 * @mail chenshiyun2011@163.com
 */
@Data
@ToString
public class UploadResult {

    /**
     * 文件名字
     */
    private String filename;

    private String filePath;

    private String key;

    private String thumbPath;

    private String suffix;

    private MediaType mediaType;

    private Long size;

    private Integer width;

    private Integer height;

}
