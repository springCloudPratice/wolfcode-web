package cn.wolfcode.web.commons.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Eastern unbeaten
 * @version 1.0
 * @date 2019/7/29 23:59
 * @mail chenshiyun2011@163.com
 */
public enum DeleteType {


    /**
     * 常规状态
     */
    NORMAL(0),

    /**
     * 禁用状态
     */
    DISABLE(1);


    @JsonValue
    private final int value;

    DeleteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
