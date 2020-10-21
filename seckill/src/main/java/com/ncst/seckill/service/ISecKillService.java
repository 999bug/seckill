package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SkUser;
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
    SkOrderInfo secKill(SkUser skUser, GoodsVo goodsVo);

    long getSecKillResult(long id, long goodsId);

    void reset(List<GoodsVo> goodsList);

    String creatPath(SkUser user, long goodsId);

    boolean checkPath(SkUser skUser, long goodsId, String path);

    BufferedImage createVerifyCode(SkUser user, long goodsId);

    boolean checkVerifyCode(SkUser user, long goodsId, int verifyCode);

}
