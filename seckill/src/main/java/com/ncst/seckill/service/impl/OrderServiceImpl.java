package com.ncst.seckill.service.impl;

import com.ncst.seckill.mapper.AddressMapper;
import com.ncst.seckill.mapper.OrderMapper;
import com.ncst.seckill.pojo.SkAddress;
import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.key.prefix.OrderKey;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.IRedisService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private AddressMapper addressMapper;

    @Autowired
    private IRedisService redisService;


    @Override
    public SkOrder getSecKillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        //添加缓存层，提高访问效率
        return redisService.get(OrderKey.getSecKillaOrderByUidGid, "" + userId + "_" + goodsId, SkOrder.class);
        //  return orderMapper.getSecKillOrderByUserIdAndGoodsId(userId, goodsId);
    }

    @Override
    public SkOrderInfo insertOrder(SkUser skUser, GoodsVo goodsVo) {
        //创建地址对象，写入订单详情时 ，写入地址的id
        SkAddress address = addressMapper.getAddressByUid(skUser.getId());

        //写入订单详细信息
        SkOrderInfo orderInfo = new SkOrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(skUser.getId());
        orderInfo.setDeliveryAddrId(address.getAid());
        orderMapper.insertOrderInfo(orderInfo);

        //写入秒杀订单信息
        SkOrder skOrder = new SkOrder();
        skOrder.setGoodsId(goodsVo.getId());
        skOrder.setOrderId(orderInfo.getId());
        skOrder.setUserId(skUser.getId());

        orderMapper.insert(skOrder);
        //添加缓存层，提高访问效率
        redisService.set(OrderKey.getSecKillaOrderByUidGid,
                "" + skUser.getId() + "_" + goodsVo.getId(), skOrder);
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



    @Override
    public long getOrderPayStatus(long uid) {
        SkOrderInfo orderById = orderMapper.getOrderByUid(uid);
        return orderById.getStatus();

    }

    @Override
    @Transactional()
    public boolean pay(long uid) {

        orderMapper.update(uid);
        return true;
    }
}
