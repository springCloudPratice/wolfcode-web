package cn.wolfcode.web.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * @author dengyangbo
 * @date 2019-12-30 11:51
 * @email 13005100647@163.com
 * @description
 */
@Component
@Slf4j
public class JacksonUtil {

    private ObjectMapper objectMapper;

    @Autowired
    public JacksonUtil (ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public JacksonUtil() {
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj
     * @return
     * @see com.nebula.web.JacksonUtilTest.objToJsonStr()
     */
    public String objToJsonStr(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * JSON字符串转对象
     *
     * @param jsonStr JSON格式字符串
     * @param clazz 对象类型
     * @return
     * @see com.nebula.web.JacksonUtilTest.jsonStrToBean()
     */
    public <T> T jsonStrToBean(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr) || Objects.isNull(clazz)) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? ((T) jsonStr) : objectMapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Bean error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * JSON字符串转集合对象
     *
     * @param jsonStr JSON格式字符串
     * @param collectionClass 集合类型
     * @param elementClass 元素类型
     * @return
     * @see com.nebula.web.JacksonUtilTest.jsonStrToCollection()
     */
    public <T> T jsonStrToCollection(String jsonStr, Class<? extends Collection> collectionClass, Class<?> elementClass) {
        if (StringUtils.isEmpty(jsonStr) || Objects.isNull(collectionClass) || Objects.isNull(elementClass)) {
            return null;
        }
        try {
            JavaType collectionType = objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
            return objectMapper.readValue(jsonStr, collectionType);
        } catch (IOException e) {
            log.warn("Parse String to Collection error : {}" + e.getMessage());
            e.printStackTrace();
            return null;

        }
    }

    /**
     * JSON字符串转对象
     *
     * @param jsonStr JSON格式字符串
     * @param typeReference 对象的包装参考类型
     * @return
     * @see com.nebula.web.JacksonUtilTest.jsonStrToObj()
     */
    public <T> T jsonStrToObj(String jsonStr, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(jsonStr) || Objects.isNull(typeReference)) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? ((T) jsonStr) : objectMapper.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }
}
