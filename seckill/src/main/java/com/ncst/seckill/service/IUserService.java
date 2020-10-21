package com.ncst.seckill.service;

import com.ncst.seckill.vo.RegistVo;
import com.ncst.seckill.pojo.SkUser;
import com.ncst.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2020/10/14 12:41
 * @Author by LSY
 * @Description
 */
public interface IUserService {
    SkUser getByToken(HttpServletResponse response, String token);

    void insertSeckill(RegistVo registVo);

    String login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo);

    /**
     *  更新密码
     * @param token token
     * @param id 用户 id
     * @param password 用户密码 加密过的
     * @return 是否更新成功
     */
    boolean updatePassword(String token, long id, String password);

}
