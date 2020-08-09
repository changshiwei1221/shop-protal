package com.fh.shop.api.ControllerException;


import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.exception.IdempotentException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ServerResponse handerGlobalException(GlobalException  globalException){
        ResponseEnum responseEnum =  globalException.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }

    @ResponseBody
    @ExceptionHandler(IdempotentException.class)
    public ServerResponse handerIdempotentException(IdempotentException  idempotentException){
        ResponseEnum responseEnum =  idempotentException.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }

}
