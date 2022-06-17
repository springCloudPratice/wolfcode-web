package cn.wolfcode.web.modules.sys.entity;

import cn.wolfcode.web.commons.utils.JacksonUtil;
import cn.wolfcode.web.commons.utils.SpringBeanUtil;

/**
 * @author dengyangbo
 * @date 2020/2/14 15:54
 * @email 13005100647@163.com
 * @description freemarker对所有的对象包装处理, 不符合的对象会包装成StringModel, 渲染时调用对象的toString方法
 * 如果对象需要freemarker打印渲染成JSON字符串,继承此类
 */
public class JsonEntity {

    @Override
    public String toString() {
        JacksonUtil jacksonUtil = SpringBeanUtil.getBean(JacksonUtil.class);
        return jacksonUtil.objToJsonStr(this);
    }
}
