package com.ncst.seckill.controller;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.result.CodeMsg;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.service.ISecKillService;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;

/**
 * @Date 2020/10/14 19:48
 * @Author by LSY
 * @Description
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ISecKillService secKillService;

    @RequestMapping("/do_secKill")
    public String doSecKill(Model model, SeckillUser seckillUser,
                            @RequestParam("goodsId") long goodsId) {
        //判断是否登录
        model.addAttribute("user", seckillUser);
        if (seckillUser == null) {
            return "login";
        }

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        System.out.println(goods);
        int stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            model.addAttribute("errMsg", CodeMsg.SECKILL_OVER.getMsg());
            return "secKill_fail";
        }

        //判断是否已经秒杀到了
        SeckillUser order = orderService.getSecKillOrderByUserIdAndGoodsId(seckillUser.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errMsg", CodeMsg.REPEAT_SEC_KILL.getMsg());
            return "secKill_fail";
        }

        //减库存 下订单 写入秒杀订单
        SkOrderInfo orderInfo =secKillService.secKill(seckillUser, goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }
}
