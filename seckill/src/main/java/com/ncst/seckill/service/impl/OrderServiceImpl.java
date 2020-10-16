package com.ncst.seckill.service.impl;

import com.ncst.seckill.mapper.OrderMapper;
import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Date 2020/10/14 20:05
 * @Author by LSY
 * @Description
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public SeckillUser getSecKillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return orderMapper.getSecKillOrderByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    public SkOrderInfo insertOrder(SeckillUser seckillUser, GoodsVo goodsVo) {
        SkOrderInfo orderInfo = new SkOrderInfo();

        //写入订单详细信息
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(seckillUser.getId());

        long returnOrderId = orderMapper.insertOrderInfo(orderInfo);

        //写入秒杀订单信息
        SkOrder skOrder = new SkOrder();
        skOrder.setGoodsId(goodsVo.getId());
        skOrder.setOrderId(returnOrderId);
        skOrder.setUserId(seckillUser.getId());

        System.out.println(skOrder);
        orderMapper.insert(skOrder);
        return orderInfo;
    }

    @Override
    public SkOrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
