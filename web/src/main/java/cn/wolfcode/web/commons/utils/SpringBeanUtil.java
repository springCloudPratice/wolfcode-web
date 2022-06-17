package cn.wolfcode.web.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 使用方法示例:
 * @Component
 * public class AnotherClass {
 *      /...
 * }
 *
 * public class MyClass {
 *     private AnotherClass anotherClass = SpringBeanUtil.getBean(AnotherClass.class)
 * }
 *
 * AnotherClass是spring容器管理的bean
 * 我们在自己new出来MyClass时里面的anotherClass就能注入进去了
 * @author dengyangbo
 * @date 2020/2/12 17:51
 * @email 13005100647@163.com
 * @description 解决自己new 出来的对象属性中无法注入bean的问题
 *
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    protected static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 通过bean name 获取bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 通过bean的类型 获取bean
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }

    /**
     * 通过bean name 和bean 的类型 获取bean对象
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clz) {
        return context.getBean(name, clz);
    }
}
