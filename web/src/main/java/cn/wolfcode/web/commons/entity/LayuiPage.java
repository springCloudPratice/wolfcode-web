package cn.wolfcode.web.commons.entity;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 17:44
 * @email chenshiyun2011@163.com
 */
@Getter
@Log4j2
public class LayuiPage {

    private Integer page = 1;
    private Integer limit = 10;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
