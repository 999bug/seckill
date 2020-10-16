package com.ncst.seckill.vo;


import com.ncst.seckill.pojo.SeckillUser;

/**
 * @author hp
 */
public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private GoodsVo goods ;
	private SeckillUser user;

	public int getMiaoshaStatus() {
		return miaoshaStatus;
	}

	public void setMiaoshaStatus(int miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}

	public int getRemainSeconds() {
		return remainSeconds;
	}

	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}

	public GoodsVo getGoods() {
		return goods;
	}

	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}

	public SeckillUser getUser() {
		return user;
	}

	public void setUser(SeckillUser user) {
		this.user = user;
	}
}
