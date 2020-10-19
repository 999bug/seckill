package com.ncst.seckill.key.prefix;

import com.ncst.seckill.key.BasePrefix;

public class OrderKey extends BasePrefix {

	private OrderKey(String prefix) {
		super(prefix);
	}

	public static OrderKey getSecKillaOrderByUidGid = new OrderKey("seckOrder");
}
