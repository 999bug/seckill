package com.ncst.seckill.vo;


import com.ncst.seckill.pojo.SkAddress;
import com.ncst.seckill.pojo.SkOrderInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
@Setter
public class OrderDetailVo {
	private GoodsVo goods;
	private SkOrderInfo order;
	private SkAddress address;


}
