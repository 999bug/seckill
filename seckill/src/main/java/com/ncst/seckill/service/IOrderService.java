package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.vo.GoodsVo;

/**
 * @Date 2020/10/14 20:02
 * @Author by LSY
 * @Description
 */
public interface IOrderService {
    SeckillUser getSecKillOrderByUserIdAndGoodsId(long userId,long goodsId);

    SkOrderInfo insertOrder(SeckillUser seckillUser, GoodsVo goodsVo);

    SkOrderInfo getOrderById(long orderId);
}
