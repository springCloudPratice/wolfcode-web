package cn.wolfcode.web.commons.utils;

import cn.hutool.json.JSONUtil;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Eastern unbeaten
 * @email chenshiyun2011@163.com
 * @data 2019/3/6
 */
@Component
@Log
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;

    /**
     * 默认过期时长，单位：秒
     */
    @Value("${app.config.default-expire}")
    public Long DEFAULT_EXPIRE;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire) {
        if (StringUtils.isNotEmpty(key) && Objects.nonNull(value)) {
            valueOperations.set(key, JSONUtil.toJsonStr(value));
            if (expire != NOT_EXPIRE) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
        }
    }

    public void set(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && Objects.nonNull(value)) {
            if (value instanceof String) {
                valueOperations.set(key, (String) value);
            } else {
                valueOperations.set(key, JSONUtil.toJsonStr(value));
            }
        }
    }

    /**
     * 携带了默认的缓存时间的set方法
     *
     * @param key
     * @param value
     */
    public void setDefault(String key, Object value) {
        if (StringUtils.isNotEmpty(key)) {
            set(key, value, DEFAULT_EXPIRE);
        }
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : JSONUtil.toBean(value, clazz);
    }


    public <T> T get(String key, Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return get(key, NOT_EXPIRE);
    }

    /**
     * 获取key 剩余时间
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key);
        return Objects.isNull(expire) ? 0L : expire;
    }

    /**
     * @param key
     * @param timeUnit
     * @return
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        Long expire = redisTemplate.getExpire(key, timeUnit);
        return Objects.isNull(expire) ? 0L : expire;
    }

    /**
     * 判断key 是否存在
     *
     * @param key
     * @return
     */
    public boolean isExpire(String key) {
        Long expire = redisTemplate.getExpire(key);
        return Objects.isNull(expire) || expire.equals(0L) ||expire.equals(-2L)? false : true;
    }


    public void delete(String key) {
        Boolean isok = false;
        if (StringUtils.isNotEmpty(key)) {
            isok = redisTemplate.delete(key);
        }
        log.info("redis 缓存删除,key:" + key + "    删除结果:" + isok);
    }


}
