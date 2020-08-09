package com.fh.shop.api.Area.controller;

import com.fh.shop.api.Area.biz.AreaService;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("areas")
@Api(value = "areas",description="地区接口")
public class AreaController {

    @Autowired
    private AreaService areaService;


    @GetMapping("/{id}")
    @ApiOperation(value = "查询地区",notes = "查询地区", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParam(name = "id",value ="id" ,required = true,type = "long",paramType = "path")
    public ServerResponse queryArea(@PathVariable Long id){


        return areaService.queryArea(id);
    }

}
