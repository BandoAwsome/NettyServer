package com.jason.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis操作类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/7/15 18:09
 */
public class RedisProcessor {

    /** 单例 */
    private final static RedisProcessor instance = new RedisProcessor();

    private RedisTemplate<String, Object> redisTemplate;

    private RedisProcessor(){}

    /**
     * 获取单例
     * @return: com.jason.redis.RedisProcessor
     * @date: 2019/7/15 18:10
     */
    public static RedisProcessor getInstance() {
        return instance;
    }

    /**
     * 初始化
     * @param redisTemplate
     * @return: void
     * @date: 2019/7/15 18:11
     */
    public void init(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 检查是否存在key
     * @param key
     * @return: boolean
     * @date: 2019/7/15 18:17
     */
    public boolean checkHasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得缓存
     * @param key
     * @return: java.lang.Object
     * @date: 2019/7/15 18:23
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return: boolean
     * @date: 2019/7/15 18:24
     */
    public boolean put(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }
}
