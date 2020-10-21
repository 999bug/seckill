package com.ncst.seckill.service.impl;

import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.pojo.Verify;
import com.ncst.seckill.key.prefix.SecKillKey;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.IRedisService;
import com.ncst.seckill.service.ISecKillService;
import com.ncst.seckill.util.Md5Utils;
import com.ncst.seckill.util.UUIDUtil;
import com.ncst.seckill.util.VerifyUtils;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
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
    @Transactional()
    public SkOrderInfo secKill(SkUser skUser, GoodsVo goodsVo) {
        //判断减库存是否成功
        boolean stock = goodsService.reduceStock(goodsVo);
        if (stock) {
            //成功 写入秒杀订单
            return orderService.insertOrder(skUser, goodsVo);
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

    @Override
    public String creatPath(SkUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        String path = Md5Utils.md5(UUIDUtil.uuid() + "2223@$%#%%!!~~~``···2");
        redisService.set(SecKillKey.getSecKillPath, "" + user.getId() + "_" + goodsId, path);
        return path;
    }

    @Override
    public boolean checkPath(SkUser skUser, long goodsId, String path) {
        if (skUser == null || path == null) {
            return false;
        }
        String oldPath = redisService.get(SecKillKey.getSecKillPath, "" + skUser.getId() + "_" + goodsId, String.class);
        return path.equals(oldPath);
    }

    @Override
    public BufferedImage createVerifyCode(SkUser user, long goodsId) {
        Verify verifyCode = VerifyUtils.createVerifyCode(user, goodsId);
        //把验证码存入缓存
        redisService.set(SecKillKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId, verifyCode.getCalc());
        return verifyCode.getBufferedImage();
    }

    @Override
    public boolean checkVerifyCode(SkUser user, long goodsId, int verifyCode) {
        if (user == null || goodsId <= 0) {
            return false;
        }

        //从缓存中取出验证码
        Integer codeOld = redisService.get(SecKillKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId, Integer.class);
        if (codeOld == null || codeOld - verifyCode != 0) {
            return false;
        }
        //删除验证码
        redisService.delete(SecKillKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId);
        return true;
    }


    private void setGoodsEmpty(long id) {
        redisService.set(SecKillKey.getGoodsEmpty, "" + id, true);
    }

    private boolean getGoodsStatus(long id) {
        return redisService.exists(SecKillKey.getGoodsEmpty, "" + id);
    }
}
