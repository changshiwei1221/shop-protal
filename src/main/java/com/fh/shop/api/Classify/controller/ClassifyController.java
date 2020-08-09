package com.fh.shop.api.Classify.controller;

import com.fh.shop.api.Classify.biz.ClassifyService;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classifys")
@Api(value = "classifys",description="分类接口")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;


    @GetMapping("queryClassIfylist")
    @ApiOperation(value = "查询分类集合",notes = "查询分类集合", httpMethod = "GET", response = ServerResponse.class)
    public ServerResponse queryClassIfylist(){

        return   classifyService.queryClassIfylist();
    }
}
