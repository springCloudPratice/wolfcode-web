package cn.wolfcode.web.commons.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/11 11:10
 * @email chenshiyun2011@163.com
 */
public class PoiExportHelper {

    public static void exportExcel(HttpServletResponse resp, String fileName, Workbook wk) throws UnsupportedEncodingException {
        resp.setHeader("Set-Cookie", "fileDownload=true; path=/");
        resp.setContentType("application/octet-stream;charset=utf-8");
        resp.setHeader("Content-Disposition", "attachment;filename=".concat(URLEncoder.encode(fileName.concat(".xls"), "UTF-8")));

        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            wk.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                wk.close();
                if (Objects.nonNull(out)) {
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
