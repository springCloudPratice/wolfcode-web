package cn.wolfcode.web.commons.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.wolfcode.web.commons.config.SysConstant;
import cn.wolfcode.web.commons.config.SystemConfig;
import link.ahsj.core.utils.base.AppAssertUtil;

import java.util.Objects;

/**
 * 系统校验工具类
 *
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/1/17 11:06 上午
 */
public class CheckUtils {


    private static SystemConfig systemConfig = null;

    private CheckUtils() {
    }

    private static CheckUtils obj = null;


    public static CheckUtils getInstance() {
        synchronized (CheckUtils.class) {
            if (obj == null) {
                obj = new CheckUtils();
            }
            return obj;
        }

    }


    /**
     * 校验分页数据大小
     * @param page
     */
    public void checkMaxPage(IPage<?> page) {
        if (Objects.nonNull(page)) {
            if (SysConstant.PAGE_ERR) {
                AppAssertUtil.isErr(page.getSize() >= SysConstant.PAGE_MAX_RECORD, String.format("每页记录数超出%s条限制", SysConstant.PAGE_MAX_RECORD));
            } else {
                if (SysConstant.PAGE_MAX_RECORD < page.getSize()) {
                    page.setSize(SysConstant.PAGE_MAX_RECORD);
                }
            }
        }
    }
}