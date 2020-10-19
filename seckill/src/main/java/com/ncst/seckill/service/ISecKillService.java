package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.vo.GoodsVo;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @Date 2020/10/14 20:13
 * @Author by LSY
 * @Description
 */
public interface ISecKillService {
    SkOrderInfo secKill(SeckillUser seckillUser, GoodsVo goodsVo);

    long getSecKillResult(long id, long goodsId);

    void reset(List<GoodsVo> goodsList);

    String creatPath(SeckillUser user, long goodsId);

    boolean checkPath(SeckillUser seckillUser, long goodsId, String path);

    BufferedImage createVerifyCode(SeckillUser user, long goodsId);

    boolean checkVerifyCode(SeckillUser user, long goodsId, int verifyCode);

}
