package com.ncst.seckill.service;

import com.ncst.seckill.redis.KeyPrefix;

/**
 * @Date 2020/10/14 12:46
 * @Author by LSY
 * @Description
 */
public interface IRedisService {

    <T> T get(KeyPrefix prefix, String key, Class<T> clazz);

    <T> boolean delete(KeyPrefix prefix, String key, T value);

    <T> boolean set(KeyPrefix prefix, String key, T value);
}
