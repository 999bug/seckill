package com.ncst.seckill.controller;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.redis.GoodsKey;
import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.impl.RedisServiceImpl;
import com.ncst.seckill.vo.GoodsDetailVo;
import com.ncst.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncst.seckill.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 优化前        优化后
     * load 10.32   load 4.23
     * QPS  1626    3302
     * 5000 10
     */

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model, SeckillUser user) {
        model.addAttribute("user", user);
        //取缓存
        String html = redisServiceImpl.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVos);
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisServiceImpl.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(SeckillUser seckillUser, @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int[] arr = goodsService.getSeckillStatusAndRemainSeconds(goods);

        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setMiaoshaStatus(arr[0]);
        detailVo.setRemainSeconds(arr[1]);
        detailVo.setGoods(goods);
        detailVo.setUser(seckillUser);
        return Result.success(detailVo);
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response,
                          Model model, SeckillUser seckillUser, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", seckillUser);

        //取缓存
        String html = redisServiceImpl.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        int[] arr = goodsService.getSeckillStatusAndRemainSeconds(goods);
        model.addAttribute("miaoshaStatus", arr[0]);
        model.addAttribute("remainSeconds", arr[1]);

        //加缓存
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisServiceImpl.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }

}
