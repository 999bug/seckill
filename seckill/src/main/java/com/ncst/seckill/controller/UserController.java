package com.ncst.seckill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ncst.seckill.vo.RegistVo;
import com.ncst.seckill.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    UserServiceImpl userService;

    @Autowired
    RedisServiceImpl redisService;

    @GetMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request, HttpServletResponse response,
                                  @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        //登录
        String token = userService.login(request, response, loginVo);
        return Result.success(token);
    }

    @RequestMapping("/to_regist")
    public String toRegist() {
        return "regist";
    }

    @RequestMapping("/do_regist")
    @ResponseBody
    public Result<Boolean> doRegist(@Valid RegistVo registVo) {
        log.info(registVo.toString());
        userService.insertSeckill(registVo);
        return Result.success(true);
    }
}
