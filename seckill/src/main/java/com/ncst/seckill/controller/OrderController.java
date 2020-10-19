package com.ncst.seckill.controller;


import com.ncst.seckill.pojo.SeckillUser;
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
    public Result<OrderDetailVo> info( SeckillUser user,
									  @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
		SkOrderInfo order = orderService.getOrderById(orderId);
		if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
