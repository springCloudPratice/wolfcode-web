package cn.wolfcode.web.commons.entity;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/11 14:21
 * @email chenshiyun2011@163.com
 */
public class ExcelExportEntityWrapper {

    List<ExcelExportEntity> titles = new ArrayList<>(0);

    public ExcelExportEntityWrapper entity() {
        titles.add(new ExcelExportEntity());
        return this;
    }

    public ExcelExportEntityWrapper entity(String name) {
        titles.add(new ExcelExportEntity(name));
        return this;
    }

    public ExcelExportEntityWrapper entity(String name, Object key) {
        titles.add(new ExcelExportEntity(name, key));
        return this;
    }

    public ExcelExportEntityWrapper entity(String name, Object key, int width) {
        titles.add(new ExcelExportEntity(name, key, width));
        return this;
    }

    public List<ExcelExportEntity> getResult() {
        return titles;
    }
}
