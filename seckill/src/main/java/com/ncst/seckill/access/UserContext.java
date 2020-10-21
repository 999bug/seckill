package com.ncst.seckill.access;

import com.ncst.seckill.pojo.SkUser;

/**
 * @Date 2020/10/19 15:07
 * @Author by LSY
 * @Description
 */
public class UserContext {
    private static ThreadLocal<SkUser> userHolder = new ThreadLocal<SkUser>();

    public static void setUserHolder(SkUser user) {
        userHolder.set(user);
    }

    public static SkUser getUser() {
        return userHolder.get();
    }

}
