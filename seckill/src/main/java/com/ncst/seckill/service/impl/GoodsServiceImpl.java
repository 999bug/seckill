package com.ncst.seckill.service.impl;

import com.ncst.seckill.mapper.AddressMapper;
import com.ncst.seckill.mapper.GoodsMapper;
import com.ncst.seckill.pojo.SkAddress;
import com.ncst.seckill.pojo.SkGoodsSeckill;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Date 2020/10/14 16:04
 * @Author by LSY
 * @Description
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public int[] getSeckillStatusAndRemainSeconds(GoodsVo goods) {
        int[] arr = new int[2];
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //表示秒杀状态 0：未开始 1：进行中 2：已经结束
        int seckillStatus = 0;
        //表示剩余时间，在秒杀未开始之前
        int remainSeconds = 0;

        //秒杀还未开始
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
        }
        arr[0] = seckillStatus;
        arr[1] = remainSeconds;
        return arr;
    }

    @Override
    public boolean reduceStock(GoodsVo goodsVo) {
        SkGoodsSeckill goods = new SkGoodsSeckill();
        goods.setGoodsId(goodsVo.getId());
        return goodsMapper.reduceStock(goods) > 0;
    }

    @Override
    public void resetStock(List<GoodsVo> goodsList) {
        for (GoodsVo goods : goodsList) {
            SkGoodsSeckill g = new SkGoodsSeckill();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsMapper.resetStock(g);
        }
    }

    @Override
    public SkAddress getAddressBySkUserId(long id) {
       return addressMapper.getAddressByUid( id);
    }

    @Override
    public SkAddress insertAddressByUid(long id) {

        SkAddress skAddress = new SkAddress();
        skAddress.setUid(id);
        skAddress.setAddress("河北省承德市双滦区");
        skAddress.setCreatedTime(new Date());
        skAddress.setModifiedTime(new Date());
        skAddress.setPhone(id);
        skAddress.setNickname("李四");
        addressMapper.insert(skAddress);

        return skAddress;
    }

}
