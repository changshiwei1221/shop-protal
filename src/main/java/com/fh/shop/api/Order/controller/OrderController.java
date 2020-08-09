package com.fh.shop.api.Order.controller;

import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.Order.Po.OrderParam;
import com.fh.shop.api.Order.biz.OrderService;
import com.fh.shop.api.common.*;
import com.fh.shop.api.utils.KeyUtils;
import com.fh.shop.api.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("orders")
@Api(value = "orders",description="订单接口")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder")
    @Login
    @Idempotent
    @ApiOperation(value = "创建订单",notes = "创建订单", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
            @ApiImplicitParam(name = "recipientId",value ="收货人id" ,required = true,type = "long",paramType = "query"),
            @ApiImplicitParam(name = "payType",value ="支付类型" ,required = true,type = "int",paramType = "query"),
    })
    public ServerResponse createOrder(HttpServletRequest request, OrderParam orderParam){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        orderParam.setMemberId(memberVo.getId());
        return  orderService.createOrder(orderParam);
    }

    @GetMapping("orderRedis")
    @Login
    @ApiOperation(value = "查看订单是否成功",notes = "查看订单是否成功", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse orderRedis(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberVoId = memberVo.getId();
        String orderStock = RedisUtil.get(KeyUtils.orderStockKey(memberVoId));
        if (orderStock!=null){
            return  ServerResponse.error(ResponseEnum.ORDER_PRODUCT_STOCK_IS_LEE);
        }
        String orderSuccess = RedisUtil.get(KeyUtils.orderSuccess(memberVoId));
        if (orderSuccess!=null){
            return ServerResponse.success();
        }
        return ServerResponse.error(ResponseEnum.ORDER_IS_PAIDUI);
    }
}
