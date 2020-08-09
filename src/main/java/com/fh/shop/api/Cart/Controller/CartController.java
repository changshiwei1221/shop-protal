package com.fh.shop.api.Cart.Controller;

import com.fh.shop.api.Cart.biz.CartService;
import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.common.Login;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("carts")
@Api(value = "carts",description="购物车接口")
public class CartController {


    @Autowired
    private CartService cartService;


    @PostMapping("cart")
    @Login
    @ApiOperation(value = "添加/删除商品到购物车",notes = "添加/删除商品到购物车", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
            @ApiImplicitParam(name = "productId",value ="商品id" ,required = true,type = "long",paramType = "query"),
            @ApiImplicitParam(name = "num",value ="商品数量" ,required = true,type = "int",paramType = "query")
    })
    public ServerResponse cart(HttpServletRequest request,Long productId,int num){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberId = memberVo.getId();
        return cartService.cart(memberId,productId,num);
    }


    @GetMapping("cartList")
    @Login
    @ApiOperation(value = "购物车查询",notes = "购物车查询", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse cartList(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberId = memberVo.getId();
        return cartService.cartList(memberId);
    }

    @DeleteMapping("del/{productId}")
    @Login
    @ApiOperation(value = "删除指定商品",notes = "删除指定商品", httpMethod = "DELETE", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
            @ApiImplicitParam(paramType="path", name = "productId", value = "商品ID", required = true, dataType = "Long")

            })
    public ServerResponse delProduct(HttpServletRequest request,@PathVariable Long productId){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberId = memberVo.getId();
        return cartService.delProduct(memberId,productId);

    }

    @GetMapping("cartItemCount")
    @Login
    @ApiOperation(value = "购物车商品数量",notes = "购物车商品数量", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse cartItemCount(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberId = memberVo.getId();
        return cartService.cartItemCount(memberId);
    }


    @PostMapping("deletes")
    @Login
    @ApiOperation(value = "删除购物车的多个商品",notes = "删除购物车的多个商品", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
            @ApiImplicitParam(name = "arr",value ="id数组" ,required = true,type = "list",paramType = "query"),
    })
    public ServerResponse deletes(HttpServletRequest request,@RequestParam("arr[]") List<Long> arr){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberId = memberVo.getId();
        return cartService.deletes(memberId,arr);
    }


}
