package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.vo.GoodsVo;

/**
 * @Date 2020/10/14 20:13
 * @Author by LSY
 * @Description
 */
public interface ISecKillService {
    SkOrderInfo secKill(SeckillUser seckillUser, GoodsVo goodsVo);
}
