package com.ncst.seckill.redis;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
public class OrderKey extends BasePrefix {

	public OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}
