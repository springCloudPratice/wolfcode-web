package cn.wolfcode.web.modules.sys.conctroller;

import cn.wolfcode.web.modules.sys.service.ISysFilesService;
import cn.wolfcode.web.modules.sys.entity.SysFiles;
import cn.wolfcode.web.commons.utils.FilenameUtils;
import cn.wolfcode.web.commons.utils.LocalFileHandler;
import cn.wolfcode.web.commons.utils.UploadResult;
import link.ahsj.core.entitys.ApiModel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/9/24 0:18
 * @mail chenshiyun2011@163.com
 */
@Log4j2
@Controller
@RequestMapping("/file")
public class FileController {

    private String workDir;

    @Autowired
    private LocalFileHandler localFileHandler;

    @Autowired
    private ISysFilesService sysFilesService;


    @GetMapping("/list.html")
    public String list() {
        return "sys/file/fileList";
    }

    @PostMapping(value = "/upload", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseEntity<ApiModel> upload(@RequestParam("file") MultipartFile file,
                                           HttpServletRequest request) {
        if (file != null) {
            // Get work dir
            workDir = StringUtils.appendIfMissing(System.getProperties().getProperty("user.home") + File.separator + "xx1xx", File.separator);

            UploadResult uploadResult = localFileHandler.upload(file, workDir);

            // Build attachment
            SysFiles sysfile = new SysFiles();
            sysfile.setName(uploadResult.getFilename());
            // Convert separator
            sysfile.setPath(FilenameUtils.changeFileSeparatorToUrlSeparator(uploadResult.getFilePath()));
            sysfile.setFileKey(uploadResult.getKey());
            sysfile.setThumbPath(uploadResult.getThumbPath());
            sysfile.setMediaType(uploadResult.getMediaType().toString());
            sysfile.setSuffix(uploadResult.getSuffix());
            sysfile.setWidth(uploadResult.getWidth());
            sysfile.setHeight(uploadResult.getHeight());
            sysfile.setFileSize(uploadResult.getSize());
            sysFilesService.save(sysfile);
            log.debug("Creating attachment: [{}]", sysfile);

            // Check work directory
        }
        return ResponseEntity.ok(ApiModel.data("ok"));
    }

}
