package com.ncst.seckill.key.prefix;

import com.ncst.seckill.key.BasePrefix;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
public class MiaoshaUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");

}
