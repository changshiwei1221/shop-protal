package com.fh.shop.api.Token.controller;

import com.fh.shop.api.common.Login;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("tokens")
@Api("生成tonken的接口")
public class TonkenController {


    @GetMapping("getTonken")
    @Login
    public ServerResponse getTonken(){
        String token = UUID.randomUUID().toString();

        RedisUtil.set(token,token);
        return ServerResponse.success(token);
    }
}
