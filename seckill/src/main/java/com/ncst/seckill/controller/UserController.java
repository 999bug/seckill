package com.ncst.seckill.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.impl.UserServiceImpl;
import com.ncst.seckill.vo.LoginVo;
/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 用户Controller
 */
@Controller
@RequestMapping("/login")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    UserServiceImpl userServiceImpl;
	
	@Autowired
    RedisServiceImpl redisServiceImpl;
	
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
    	//登录
    	userServiceImpl.login(response, loginVo);
    	return Result.success(true);
    }
    @RequestMapping("/to_regist")
    public String toRegist() {
        return "regist";
    }

    @RequestMapping("/do_regist")
    @ResponseBody
    public Result<Boolean> doregist(@Valid SeckillUser seckillUser) {
        log.info(seckillUser.toString());
       //userService.insertSeckill(seckillUser);
        return Result.success(true);
    }
}
