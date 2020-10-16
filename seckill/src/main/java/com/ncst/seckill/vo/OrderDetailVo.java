package com.ncst.seckill.vo;


import com.ncst.seckill.pojo.SkOrderInfo;

/**
 * @author hp
 */
public class OrderDetailVo {
	private GoodsVo goods;
	private SkOrderInfo order;
	public GoodsVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}

	public SkOrderInfo getOrder() {
		return order;
	}

	public void setOrder(SkOrderInfo order) {
		this.order = order;
	}
}
