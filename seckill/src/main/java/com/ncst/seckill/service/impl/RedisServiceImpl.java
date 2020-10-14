package com.ncst.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.ncst.seckill.redis.KeyPrefix;
import com.ncst.seckill.service.IRedisService;
import com.ncst.seckill.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 获取当个对象
     */
    @Override
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = JsonUtils.stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除对象
     */
    @Override
    public <T> boolean delete(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = JsonUtils.beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            jedis.del(realKey, str);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 设置对象
     */
    @Override
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = JsonUtils.beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
