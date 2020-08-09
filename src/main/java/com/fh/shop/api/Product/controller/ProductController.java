package com.fh.shop.api.Product.controller;

import com.fh.shop.api.Product.biz.ProductService;
import com.fh.shop.api.common.Login;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
@Api(value = "products",description="商品接口")
public class ProductController {

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "热销商品", notes = "热销商品", httpMethod = "GET", response = ServerResponse.class)
    @GetMapping("queryHotList")
    @Login
    public ServerResponse queryHotList(){

        return productService.queryHotList();
    }

}
