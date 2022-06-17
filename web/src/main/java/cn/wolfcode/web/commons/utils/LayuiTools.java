package cn.wolfcode.web.commons.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.commons.entity.CodeMsg;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 17:18
 * @email chenshiyun2011@163.com
 */
@Data
public class LayuiTools {

    private Integer code;
    private String msg;
    private Long count;
    private List data;

    public static Map<String, Object> toLayuiTableModel(IPage page) {
        Map<String, Object> layuiModel = new HashMap<>(4);
        layuiModel.put("code", 0);
        layuiModel.put("msg", CodeMsg.DEFAULT_MSG);
        layuiModel.put("count", page.getTotal());
        layuiModel.put("data", page.getRecords());
        return layuiModel;
    }

    public static Map<String, Object> toLayuiTreeTableModel(List list) {
        Map<String, Object> teeTableModel = new HashMap<>(4);
        teeTableModel.put("code", 0);
        teeTableModel.put("msg", "ok");
        teeTableModel.put("data", list);
        return teeTableModel;
    }
}
