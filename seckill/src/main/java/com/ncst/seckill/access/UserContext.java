package com.ncst.seckill.access;

import com.ncst.seckill.pojo.SeckillUser;

/**
 * @Date 2020/10/19 15:07
 * @Author by LSY
 * @Description
 */
public class UserContext {
    private static ThreadLocal<SeckillUser> userHolder = new ThreadLocal<SeckillUser>();

    public static void setUserHolder(SeckillUser user) {
        userHolder.set(user);
    }

    public static SeckillUser getUser() {
        return userHolder.get();
    }

}
