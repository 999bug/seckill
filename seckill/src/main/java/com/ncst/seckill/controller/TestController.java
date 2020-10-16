package com.ncst.seckill.controller;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.result.Result;
import com.ncst.seckill.service.IRedisService;
import com.ncst.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date 2020/10/15 17:07
 * @Author by LSY
 * @Description
 */
@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<SeckillUser> info(Model model, SeckillUser user) {
        return Result.success(user);
    }

}
