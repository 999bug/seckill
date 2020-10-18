package com.ncst.seckill.redis.prefix;

import com.ncst.seckill.redis.BasePrefix;

/**
 * @Date 2020/10/18 9:59
 * @Author by LSY
 * @Description
 */
public class SecKillKey extends BasePrefix {
    public SecKillKey(String prefix) {
        super(prefix);
    }

    public static  SecKillKey getGoodsEmpty=new SecKillKey("ge");
}
