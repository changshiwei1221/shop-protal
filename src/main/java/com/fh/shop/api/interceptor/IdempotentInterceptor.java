package com.fh.shop.api.interceptor;

import com.fh.shop.api.common.Idempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.IdempotentException;
import com.fh.shop.api.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)){
            return  true;
        }
        //判断头信息是否存在
        String tokenHeader = request.getHeader("x-token");
        if (StringUtils.isBlank(tokenHeader)){
            throw new IdempotentException(ResponseEnum.TOKEN_IS_NULL);
        }
        String tokenRedis = RedisUtil.get(tokenHeader);
        if (StringUtils.isBlank(tokenRedis)){
            throw new IdempotentException(ResponseEnum.TOKEN_IS_ERROR);
        }
        Long flag = RedisUtil.del(tokenRedis);
        if(flag==0){
            throw new IdempotentException(ResponseEnum.TOKEN_IS_REPEAT);
        }
        return true;
    }
}
