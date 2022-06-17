package cn.wolfcode.web.commons.utils;

import cn.wolfcode.web.commons.config.SysConstant;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.File;

/**
 * Filename utilities.
 *
 * @author johnniang
 * @date 3/26/19
 */
public class FilenameUtils {

    private FilenameUtils() {
    }

    /**
     * Gets base name of file name. <br>
     * eg: <br>
     * filename: /home/test/test.txt <br>
     * basename: test
     *
     * @param filename filename must not be blank
     * @return basename of the given file name
     */
    @NonNull
    public static String getBasename(@NonNull String filename) {
        AppAssertUtil.isBlank(filename, "Filename must not be blank");

        // Find the last slash
        int separatorLastIndex = StringUtils.lastIndexOf(filename, File.separatorChar);

        if (separatorLastIndex == filename.length() - 1) {
            return "";
        }

        if (separatorLastIndex >= 0 && separatorLastIndex < filename.length() - 1) {
            filename = filename.substring(separatorLastIndex + 1);
        }

        // Find last dot
        int dotLastIndex = StringUtils.lastIndexOf(filename, '.');

        if (dotLastIndex < 0) {
            return filename;
        }

        return filename.substring(0, dotLastIndex);
    }

    /**
     * Gets extension of the file name. <br>
     * <code>
     * eg: <br>
     * filename: /home/test/test.txt <br>
     * extension: txt
     * </code>
     *
     * @param filename filename must not be blank
     * @return an extension of the given file name
     */
    @NonNull
    public static String getExtension(@NonNull String filename) {

        AppAssertUtil.isBlank(filename, "Filename must not be blank");
        // Find the last slash
        int separatorLastIndex = StringUtils.lastIndexOf(filename, File.separatorChar);

        if (separatorLastIndex == filename.length() - 1) {
            return "";
        }

        if (separatorLastIndex >= 0 && separatorLastIndex < filename.length() - 1) {
            filename = filename.substring(separatorLastIndex + 1);
        }

        // Find last dot
        int dotLastIndex = StringUtils.lastIndexOf(filename, '.');

        if (dotLastIndex < 0) {
            return "";
        }

        return filename.substring(dotLastIndex + 1);
    }

    /**
     * Changes file separator to url separator.
     *
     * @param pathname full path name must not be blank.
     * @return text with url separator
     */
    public static String changeFileSeparatorToUrlSeparator(@NonNull String pathname) {
        Assert.hasText(pathname, "Path name must not be blank");

        return pathname.replace(SysConstant.FILE_SEPARATOR, "/");
    }

}
