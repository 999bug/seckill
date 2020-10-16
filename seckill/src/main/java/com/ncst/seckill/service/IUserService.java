package com.ncst.seckill.service;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2020/10/14 12:41
 * @Author by LSY
 * @Description
 */
public interface IUserService {
    SeckillUser getByToken(HttpServletResponse response, String token);

    void insertSeckill(SeckillUser seckillUser);

    String login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo);

}
