package com.ncst.seckill.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncst.seckill.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ncst.seckill.pojo.SeckillUser;
import com.ncst.seckill.service.impl.UserServiceImpl;

/**
 * @Date 2020/10/14 12:30
 * @Author by LSY
 * @Description
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == SeckillUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        request.getMethod();
        String paramToken = request.getParameter(UserServiceImpl.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length<=0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(UserServiceImpl.COOKIE_NAME_TOKEN)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
