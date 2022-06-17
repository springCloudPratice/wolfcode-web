package cn.wolfcode.web.commons.utils;

import link.ahsj.core.exception.AppServerException;
import link.ahsj.core.utils.base.AppAssertUtil;
import link.ahsj.core.utils.str.TokenGeneratorUtils;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * 图片上传,支持压缩
 *
 * @author Eastern unbeaten
 * @version 2.0
 * @date 2019/9/28 15:07
 * @mail chenshiyun2011@163.com
 */
@Log4j2
@Component
public class LocalFileHandler implements FileHandler {


    /**
     * 文件上传
     *
     * @param file
     * @param workDir
     * @return
     */
    @Override
    public UploadResult upload(MultipartFile file, String workDir) {

        Assert.notNull(file, "Multipart file must not be null");
        checkWorkDir(workDir);
        // Get current time 获取当前时间
        Calendar current = Calendar.getInstance(Locale.CHINESE);
        // Get month and day of month  获取年份以及月份
        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;

        // Build directory 创建目录
        String subDir = UPLOAD_SUB_DIR + year + File.separator + month + File.separator;

        String originalBasename = FilenameUtils.getBasename(file.getOriginalFilename());

        // Get basename 获取名称
        String basename = originalBasename + '-' + TokenGeneratorUtils.get32UUID();

        // Get extension  获取后缀
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        log.debug("Base name: [{}], extension: [{}] of original filename: [{}]", basename, extension, file.getOriginalFilename());

        // Build sub file path  构建子文件路径
        String subFilePath = subDir + basename + '.' + extension;

        // Get upload path  获取上传路径
        Path uploadPath = Paths.get(workDir, subFilePath);

        log.info("Uploading to directory: [{}]", uploadPath.toString());

        try {
            // TODO Synchronize here
            // Create directory 建立目录
            Files.createDirectories(uploadPath.getParent());
            Files.createFile(uploadPath);

            // Upload this file 上传到此目录
            file.transferTo(uploadPath);

            // Build upload result  建立上传结果
            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(originalBasename);
            uploadResult.setFilePath(subFilePath);
            uploadResult.setKey(subFilePath);
            uploadResult.setSuffix(extension);
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            uploadResult.setSize(file.getSize());

            // Check file type
            if (FileHandler.isImageType(uploadResult.getMediaType())) {
                //是否需要压缩
                if (true) {
                    // Upload a thumbnail 上传缩略图
                    String thumbnailBasename = basename + THUMBNAIL_SUFFIX;
                    String thumbnailSubFilePath = subDir + thumbnailBasename + '.' + extension;
                    Path thumbnailPath = Paths.get(workDir + thumbnailSubFilePath);

                    // Create the thumbnail 创建缩略图
                    Files.createFile(thumbnailPath);

                    // Generate thumbnail   产生缩略图
                    generateThumbnail(uploadPath, thumbnailPath);

                    // Read as image
                    BufferedImage image = ImageIO.read(Files.newInputStream(uploadPath));

                    // Set width and height     设置高度宽度
                    uploadResult.setWidth(image.getWidth());
                    uploadResult.setHeight(image.getHeight());

                    // Set thumb path   设置路径
                    uploadResult.setThumbPath(thumbnailSubFilePath);
                }
            }
            return uploadResult;
        } catch (IOException e) {
            log.error("Failed to upload file to local 无法将文件上传到本地 : " + uploadPath, e);
            throw new AppServerException("上传附件失败");
        }
    }

    /**
     * Upload sub directory.  上传子目录
     */
    private final static String UPLOAD_SUB_DIR = "upload" + File.separator;

    /**
     * 校验目录是否有权限,
     *
     * @param workDir
     */
    private static void checkWorkDir(String workDir) {
        // Get work path
        Path workPath = Paths.get(workDir);
        try {
            final File mediaPath = new File(workPath.toString());
            if (!mediaPath.exists()) {
                if (!mediaPath.mkdirs()) {
                    log.info("创建文件目录");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Check file type
        Assert.isTrue(Files.isDirectory(workPath), workDir + " isn't a directory");

        // Check readable
        Assert.isTrue(Files.isReadable(workPath), workDir + " isn't readable");

        // Check writable
        Assert.isTrue(Files.isWritable(workPath), workDir + " isn't writable");
    }

    private final static String THUMBNAIL_SUFFIX = "-thumbnail";

    /**
     * Thumbnail width.
     */
    private final static int THUMB_WIDTH = 256;

    /**
     * Thumbnail height.
     */
    private final static int THUMB_HEIGHT = 256;

    /**
     * Generates thumbnail image.
     *
     * @param imagePath image path must not be null
     * @param thumbPath thumbnail path must not be null
     * @throws IOException throws if image provided is not valid
     */
    private static void generateThumbnail(@NonNull Path imagePath, @NonNull Path thumbPath) throws IOException {
        Assert.notNull(imagePath, "Image path must not be null");
        Assert.notNull(thumbPath, "Thumb path must not be null");

        log.info("Generating thumbnail: [{}] for image: [{}]", thumbPath.getFileName(), imagePath.getFileName());

        // Convert to thumbnail and copy the thumbnail
        Thumbnails.of(imagePath.toFile()).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true).toFile(thumbPath.toFile());
    }

    @Override
    public void delete(String key, String workDir) {
        AppAssertUtil.isBlank(key, "File key must not be blank");
        // Get path
        Path path = Paths.get(workDir, key);


        // Delete the file key
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new AppServerException("附件 " + key + " 删除失败",e);
        }

        // Delete thumb if necessary
        String basename = FilenameUtils.getBasename(key);
        String extension = FilenameUtils.getExtension(key);

        // Get thumbnail name
        String thumbnailName = basename + THUMBNAIL_SUFFIX + '.' + extension;

        // Get thumbnail path
        Path thumbnailPath = Paths.get(path.getParent().toString(), thumbnailName);

        // Delete thumbnail file
        try {
            boolean deleteResult = Files.deleteIfExists(thumbnailPath);
            if (!deleteResult) {
                log.warn("Thumbnail: [{}] way not exist", thumbnailPath.toString());
            }
        } catch (IOException e) {
            throw new AppServerException("附件缩略图 " + thumbnailName + " 删除失败", e);
        }
    }
}
