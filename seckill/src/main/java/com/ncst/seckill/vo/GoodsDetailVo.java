package com.ncst.seckill.vo;


import com.ncst.seckill.pojo.SkAddress;
import com.ncst.seckill.pojo.SkUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
@Setter
public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private GoodsVo goods ;
	private SkUser user;
	private SkAddress address;


}
