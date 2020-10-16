package com.ncst.seckill.controller;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.result.CodeMsg;
import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.ISecKillService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;

/**
 * @Date 2020/10/14 19:48
 * @Author by LSY
 * @Description
 */
@RestController
@RequestMapping("/secKill")
public class SecKillController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISecKillService secKillService;

    /*
    未优化      优化后
    QPS :178    1570
    5000 * 10
     */

    @PostMapping("/do_secKill")
    public Result<SkOrderInfo> doSecKill(Model model, SeckillUser seckillUser,
                            @RequestParam("goodsId") long goodsId) {
        //判断是否登录
        model.addAttribute("user", seckillUser);
        if (seckillUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        //判断是否已经秒杀到了
        SeckillUser order = orderService.getSecKillOrderByUserIdAndGoodsId(seckillUser.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_SEC_KILL);
        }

        //减库存 下订单 写入秒杀订单
        SkOrderInfo orderInfo = secKillService.secKill(seckillUser, goods);
        return Result.success(orderInfo);
    }
}
