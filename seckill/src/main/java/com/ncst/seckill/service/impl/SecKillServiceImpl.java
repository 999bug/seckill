package com.ncst.seckill.service.impl;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.ISecKillService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Date 2020/10/14 20:16
 * @Author by LSY
 * @Description
 */
@Service
public class SecKillServiceImpl implements ISecKillService {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Override
    @Transactional
    public SkOrderInfo secKill(SeckillUser seckillUser, GoodsVo goodsVo) {
       //减秒杀订单库存
        goodsService.reduceStock(goodsVo);
        //写入秒杀订单
        return orderService.insertOrder(seckillUser,goodsVo);
    }
}
