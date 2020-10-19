package com.ncst.seckill.key.prefix;

import com.ncst.seckill.key.BasePrefix;

/**
 * @Date 2020/10/18 9:59
 * @Author by LSY
 * @Description
 */
public class SecKillKey extends BasePrefix {

    private SecKillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SecKillKey getGoodsEmpty = new SecKillKey(0, "ge");
    public static SecKillKey getSecKillPath = new SecKillKey(0, "skp");
    public static SecKillKey getMiaoshaVerifyCode = new SecKillKey(300, "svc");

}
