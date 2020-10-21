package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.vo.GoodsVo;

/**
 * @Date 2020/10/14 20:02
 * @Author by LSY
 * @Description
 */
public interface IOrderService {
    SkOrder getSecKillOrderByUserIdAndGoodsId(long userId, long goodsId);

    SkOrderInfo insertOrder(SkUser skUser, GoodsVo goodsVo);

    SkOrderInfo getOrderById(long orderId);

    void deleteOrders();

    long getOrderPayStatus(long uid);

    boolean pay(long uid);

}
