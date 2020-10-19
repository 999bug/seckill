package com.ncst.seckill.key;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
public interface KeyPrefix {

    /**
     * @return 过期时间
     */
    int expireSeconds();

    /**
     * @return 前缀
     */
    String getPrefix();

}
