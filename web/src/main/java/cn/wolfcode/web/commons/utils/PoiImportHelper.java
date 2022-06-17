package cn.wolfcode.web.commons.utils;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/11 16:47
 * @email chenshiyun2011@163.com
 */
public class PoiImportHelper {

    public static ImportParams buildImportParams(String[] importFields, Class[] verifyGroup) {
        ImportParams params = new ImportParams();
        params.setImportFields(importFields);
        params.setNeedVerify(true);
        params.setNeedCheckOrder(true);
        params.setVerifyGroup(verifyGroup);
        return params;
    }

    public static List<String> getErrors(ExcelImportResult result) {
        List<String> errors = new ArrayList<>(0);
        List failList = result.getFailList();
        if (CollectionUtils.isNotEmpty(failList)) {
            failList.forEach(item -> {
                StringBuffer sb = new StringBuffer();
                if (IExcelDataModel.class.isAssignableFrom(item.getClass())) {
                    IExcelDataModel dm = (IExcelDataModel) item;
                    sb.append(String.format("第%s行", dm.getRowNum()));
                }
                if (IExcelModel.class.isAssignableFrom(item.getClass())) {
                    IExcelModel em = (IExcelModel) item;
                    sb.append(String.format(String.format(",%s", em.getErrorMsg())));
                }
                errors.add(sb.toString());
            });
        }
        return errors;
    }
}
