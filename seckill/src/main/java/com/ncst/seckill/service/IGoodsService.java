package com.ncst.seckill.service;

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

    void reduceStock(GoodsVo goodsVo);
}
