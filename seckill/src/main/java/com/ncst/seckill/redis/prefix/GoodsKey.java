package com.ncst.seckill.redis.prefix;

import com.ncst.seckill.redis.BasePrefix;

public class GoodsKey extends BasePrefix {

	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
	public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
	public static GoodsKey getGoodsStock = new GoodsKey(0, "gs");
}
