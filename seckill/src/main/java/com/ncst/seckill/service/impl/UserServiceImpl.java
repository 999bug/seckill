package com.ncst.seckill.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncst.seckill.vo.RegistVo;
import com.ncst.seckill.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncst.seckill.mapper.UserMapper;
import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.exception.GlobalException;
import com.ncst.seckill.key.prefix.MiaoshaUserKey;
import com.ncst.seckill.result.CodeMsg;
import com.ncst.seckill.util.Md5Utils;
import com.ncst.seckill.util.UUIDUtil;
import com.ncst.seckill.vo.LoginVo;

import java.util.Date;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description 用户Service
 */
@Service
public class UserServiceImpl implements IUserService {


    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisServiceImpl redisServiceImpl;

    public SeckillUser getById(long id) {
        //取缓存
        SeckillUser seckillUser = redisServiceImpl.get(MiaoshaUserKey.getById, "" + id, SeckillUser.class);
        if (seckillUser != null) {
            return seckillUser;
        }
        //取数据库
        seckillUser = userMapper.getById(id);
        if (seckillUser != null) {
            redisServiceImpl.set(MiaoshaUserKey.getById, +id + "", seckillUser);
        }
        return seckillUser;
    }

    @Override
    public boolean updatePassword(String token, long id, String password) {
        //取seckillUser
        SeckillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //更新数据库 新创建一个user 的目的是为了减少数据库的 binlog 文件
        SeckillUser seckillUser = new SeckillUser();
        seckillUser.setId(id);
        seckillUser.setPassword(Md5Utils.inputPassToDbPass(password, user.getSalt()));
        userMapper.updateUser(seckillUser);

        //更新缓存
        redisServiceImpl.delete(MiaoshaUserKey.getById, "" + id);
        user.setPassword(seckillUser.getPassword());
        redisServiceImpl.set(MiaoshaUserKey.token, token, user);
        return true;
    }

    @Override
    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SeckillUser user = redisServiceImpl.get(MiaoshaUserKey.token, token, SeckillUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


    @Override
    public void insertSeckill(RegistVo registVo) {
        //输入为空检查
		if (registVo == null) {
			throw new GlobalException(CodeMsg.REGIST_ERROR);
		}
		SeckillUser dbUser = userMapper.getById(registVo.getId());

        //防止重复注册
		if (dbUser != null && registVo.getId()==dbUser.getId()) {
			throw new GlobalException(CodeMsg.ID_already_exists);
		}

        //对密码进行二次加密
        String password = registVo.getPassword();
        String salt = UUIDUtil.uuid();
        String dbPwd = Md5Utils.fromInputPwdToDbPwd(password, salt);

        //设置其他值进行数据库
        SeckillUser seckillUser=new SeckillUser();
        seckillUser.setId(registVo.getId());
        seckillUser.setPassword(dbPwd);
        seckillUser.setSalt(salt);
        seckillUser.setLoginCount(1);
        seckillUser.setLastLoginDate(new Date());
        seckillUser.setRegisterDate(new Date());
        seckillUser.setHead("nuu");
        seckillUser.setNickname("nick");
        System.out.println("=====" + seckillUser + "======");
        userMapper.insertSecKill(seckillUser);
    }

    @Override
    public String login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SeckillUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = Md5Utils.fromInputPwdToDbPwd(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //检查redis 是否存在此用户的token
        String token = getUserCookieToken(request);
        if (!StringUtils.isEmpty(token)) {
            SeckillUser seckillUser = redisServiceImpl.get(MiaoshaUserKey.token, token, SeckillUser.class);
            if (seckillUser != null && Long.parseLong(loginVo.getMobile()) == seckillUser.getId()) {
                return token;
            }
        }

        //生成cookie
        token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    private String getUserCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("token")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        redisServiceImpl.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
