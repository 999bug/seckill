package com.ncst.seckill.controller;

import com.ncst.seckill.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.service.impl.UserServiceImpl;
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
	
    @RequestMapping("/to_list")
    public String list(Model model, SeckillUser user) {
    	model.addAttribute("user", user);
        return "goods_list";
    }
    
}
