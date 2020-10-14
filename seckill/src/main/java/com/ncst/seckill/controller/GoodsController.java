package com.ncst.seckill.controller;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.impl.RedisServiceImpl;
import com.ncst.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncst.seckill.service.impl.UserServiceImpl;

import java.util.List;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 商品页面
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/to_lists")
    public String list(Model model, SeckillUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVos);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, SeckillUser seckillUser, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", seckillUser);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        int[] arr = goodsService.getSeckillStatusAndRemainSeconds(goods);
        model.addAttribute("miaoshaStatus", arr[0]);
        model.addAttribute("remainSeconds", arr[1]);
        return "goods_detail";
    }

}
