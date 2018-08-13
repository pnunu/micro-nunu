package pnunu.user.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: pnunu
 * @Date: Created in 11:18 2018/8/9
 * @Description:
 */
@Component
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;

    /** 根据key从redis中获取对象，redis中存储字符串 */
    public <K, V> V getJson2Object(K key, Class<V> clazz) {
        String value = get(key);
        V valueObject = Object2Json.fromJson(value, clazz);
        return valueObject;
    }

    /** 获取Value */
    public <K, V> V get(K key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    public <K, V> void setObject2Json(K key, V value) {
        String json = Object2Json.toJson(value);
        set(key, json);
    }

    public <K, V> void setObject2Json(K key, V value, long timeout) {
        String json = Object2Json.toJson(value);
        set(key, json, timeout);
    }

    /** 设置Value */
    public <K, V> void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /** 设置Value，以秒为单位 */
    public <K, V> void set(K key, V value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /** 更新key的过期时间，以秒为单位 */
    public <K> void expire(K key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

}
