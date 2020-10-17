package com.ncst.seckill.redis;

public class OrderKey extends BasePrefix {

	public OrderKey(String prefix) {
		super(prefix);
	}

	public static OrderKey getSecKillaOrderByUidGid = new OrderKey("seckOrder");
}
