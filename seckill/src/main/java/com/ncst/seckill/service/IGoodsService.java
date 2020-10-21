package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SkAddress;
import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.vo.AddressVo;
import com.ncst.seckill.vo.GoodsVo;

import java.util.List;

/**
 * @Date 2020/10/14 15:55
 * @Author by LSY
 * @Description
 */
public interface IGoodsService {

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    int[] getSeckillStatusAndRemainSeconds(GoodsVo goods);

    boolean reduceStock(GoodsVo goodsVo);

    void resetStock(List<GoodsVo> goodsList);

    SkAddress getAddressBySkUserId(long id);

    SkAddress insertAddressByUid(long id);
}
