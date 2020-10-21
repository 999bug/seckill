package com.ncst.seckill.controller;


import com.ncst.seckill.pojo.SkAddress;
import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.pojo.SkOrderInfo;
import com.ncst.seckill.result.CodeMsg;
import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.IGoodsService;
import com.ncst.seckill.service.IOrderService;
import com.ncst.seckill.vo.GoodsVo;
import com.ncst.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    IOrderService orderService;

    @Autowired
    IGoodsService goodsService;

    @GetMapping("/detail")
    public Result<OrderDetailVo> info(SkUser user,
                                      @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //获取订单详情
        SkOrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        //获取商品详情
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(order.getGoodsId());

        //获取收件人地址 姓名
        SkAddress address = goodsService.getAddressBySkUserId(order.getUserId());

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        vo.setAddress(address);
        return Result.success(vo);
    }

    @PostMapping("/pay")
    public Result<Boolean> pay(SkUser user,
                               @RequestParam("uid") long uid) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean status= orderService.pay(uid);
        return Result.success(status);
    }

    @GetMapping("/payStatus")
    public Result<Long> payStatus(SkUser user,
                                  @RequestParam("uid") long uid) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long orderPayStatus = orderService.getOrderPayStatus(uid);
        return Result.success(orderPayStatus);
    }

}
