package com.ncst.seckill.service.impl;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.redis.prefix.SecKillKey;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.IRedisService;
import com.ncst.seckill.service.ISecKillService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private IRedisService redisService;

    @Override
    @Transactional
    public SkOrderInfo secKill(SeckillUser seckillUser, GoodsVo goodsVo) {
        //判断减库存是否成功
        boolean stock = goodsService.reduceStock(goodsVo);
        if (stock) {
            //成功 写入秒杀订单
            return orderService.insertOrder(seckillUser, goodsVo);
        } else {
            //不成功 库存为 0
            setGoodsEmpty(goodsVo.getId());
            return null;
        }
    }

    @Override
    public long getSecKillResult(long id, long goodsId) {
        SkOrder skOrder = orderService.getSecKillOrderByUserIdAndGoodsId(id, goodsId);
        if (skOrder != null) {
            //秒杀成功
            return skOrder.getOrderId();
        } else {
            //库存是否为空
            boolean goodsStatus = getGoodsStatus(goodsId);
            if (goodsStatus) {
                //redis 缓存种存在 此 商品 id 秒杀失败
                return -1;
            } else {
                //继续排队
                return 0;
            }
        }
    }

    @Override
    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }

    private void setGoodsEmpty(long id) {
        redisService.set(SecKillKey.getGoodsEmpty, "" + id, true);
    }

    private boolean getGoodsStatus(long id) {
        return redisService.exists(SecKillKey.getGoodsEmpty, "" + id);
    }
}
