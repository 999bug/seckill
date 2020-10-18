package com.ncst.seckill.service.impl;

import com.ncst.seckill.mapper.OrderMapper;
import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.redis.prefix.OrderKey;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.IRedisService;
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

    @Autowired
    private IRedisService redisService;

    @Override
    public SkOrder getSecKillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        //添加缓存层，提高访问效率
        return redisService.get(OrderKey.getSecKillaOrderByUidGid, ""+userId+"_"+goodsId, SkOrder.class);
      //  return orderMapper.getSecKillOrderByUserIdAndGoodsId(userId, goodsId);
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

        long l = orderMapper.insertOrderInfo(orderInfo);

        //写入秒杀订单信息
        SkOrder skOrder = new SkOrder();
        skOrder.setGoodsId(goodsVo.getId());
        skOrder.setOrderId(orderInfo.getId());
        skOrder.setUserId(seckillUser.getId());

        orderMapper.insert(skOrder);
        //添加缓存层，提高访问效率
        redisService.set(OrderKey.getSecKillaOrderByUidGid, ""+seckillUser.getId()+"_"+goodsVo.getId(), skOrder);

        return orderInfo;
    }

    @Override
    public SkOrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public void deleteOrders() {
        orderMapper.deleteOrders();
        orderMapper.deleteMiaoshaOrders();
    }
}
