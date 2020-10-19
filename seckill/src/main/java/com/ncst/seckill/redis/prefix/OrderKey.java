package com.ncst.seckill.redis.prefix;

import com.ncst.seckill.redis.BasePrefix;

public class OrderKey extends BasePrefix {

	private OrderKey(String prefix) {
		super(prefix);
	}

	public static OrderKey getSecKillaOrderByUidGid = new OrderKey("seckOrder");
}
