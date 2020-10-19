package com.ncst.seckill.service;

import com.ncst.seckill.key.KeyPrefix;

/**
 * @Date 2020/10/14 12:46
 * @Author by LSY
 * @Description
 */
public interface IRedisService {

    /**
     * 获取对象
     *
     * @param prefix 前缀
     * @param key    key
     * @param clazz  对象类型
     * @param <T>    泛型
     * @return 对象
     */
    <T> T get(KeyPrefix prefix, String key, Class<T> clazz);

    /**
     * 删除元素
     *
     * @param prefix 前缀
     * @param key    key
     * @return 是否成功
     */
    boolean delete(KeyPrefix prefix, String key);

    boolean delete(KeyPrefix prefix);

    /**
     * 设置对象
     *
     * @param prefix 前缀
     * @param key    key
     * @param value  值
     * @param <T>    泛型
     * @return 是否成功
     */
    <T> boolean set(KeyPrefix prefix, String key, T value);

    <T> Long incr(KeyPrefix prefix, String key);

    <T> Long decr(KeyPrefix prefix, String key);

    <T> boolean exists(KeyPrefix prefix, String key);
}
