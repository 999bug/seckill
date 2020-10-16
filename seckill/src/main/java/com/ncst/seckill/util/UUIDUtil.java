package com.ncst.seckill.util;

import java.util.UUID;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 获取随机的UUID
 */
public class UUIDUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
