package com.ncst.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.ncst.seckill.access.AccessLimit;
import com.ncst.seckill.pojo.SecKillMsg;
import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.pojo.SkOrder;
import com.ncst.seckill.key.prefix.GoodsKey;
import com.ncst.seckill.key.prefix.OrderKey;
import com.ncst.seckill.key.prefix.SecKillKey;
import com.ncst.seckill.result.CodeMsg;
import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.*;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Date 2020/10/14 19:48
 * @Author by LSY
 * @Description
 */
@RestController
@RequestMapping("/secKill")
public class SecKillController implements InitializingBean {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISecKillService secKillService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IMqSenderService senderService;

    private Map<Long, Boolean> map = new HashMap<Long, Boolean>();


    private RateLimiter rateLimiter=RateLimiter.create(10);

    /**
     * 系统初始化
     */
    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goodsVo : goodsList) {
            redisService.set(GoodsKey.getGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
            //内存标记，减少redis访问
            map.put(goodsVo.getId(), false);
        }
    }

    /*
    未优化      优化后    再次优化 判断是否秒杀加入缓存
    QPS :178    1570     2430   1060  2033  1853  2161
    负载                                    10.02  8.58
    5000 * 10
     */


    @PostMapping("/{path}/do_secKill")
    public Result<Integer> doSecKill(SkUser skUser,
                                     @RequestParam("goodsId") long goodsId,
                                     @PathVariable("path") String path) {
        //判断是否登录
        if (skUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //令牌桶算法 限流
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }

        //验证Path
        boolean check = secKillService.checkPath(skUser, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问  判断是否秒杀
        Boolean goodsEmpty = map.get(goodsId);
        if (goodsEmpty) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //预减库存
        Long stock = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
        //由库存 1 减一 得到的 stock
        if (stock < 0) {
            map.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //判断是否已经秒杀到了
        SkOrder order = orderService.getSecKillOrderByUserIdAndGoodsId(skUser.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_SEC_KILL);
        }

        //入队
        SecKillMsg secKillMsg = new SecKillMsg();
        secKillMsg.setSkUser(skUser);
        secKillMsg.setGoodsId(goodsId);
        senderService.sendSecKill(secKillMsg);
        //排队中
        return Result.success(0);
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @GetMapping("/result")
    public Result<Long> result(SkUser user,
                               @RequestParam("goodsId") long goodsId) {

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = secKillService.getSecKillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping("/path")
    public Result<String> getPath(SkUser user,
                                  @RequestParam("goodsId") long goodsId,
                                  @RequestParam(value = "verifyCode") int verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证验证码是否正确
        boolean check = secKillService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //生成随机地址
        String path = secKillService.creatPath(user, goodsId);
        return Result.success(path);
    }

    @GetMapping(value = "/verifyCode")
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, SkUser user,
                                              @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = secKillService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }

    @GetMapping(value = "/reset")
    public Result<Boolean> reset() {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for (GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), 10);
            map.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getSecKillaOrderByUidGid);
        redisService.delete(SecKillKey.getGoodsEmpty);
        secKillService.reset(goodsList);
        return Result.success(true);
    }


}
