package com.fh.shop.api.Brand.controller;

import com.fh.shop.api.Brand.biz.BrandService;
import com.fh.shop.api.Brand.po.Brand;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brands")
@Api(value = "brands",description="品牌接口")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @PostMapping
    @ApiOperation(value = "新增品牌",notes = "新增品牌", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandName",value ="品牌名" ,required = true,type = "string",paramType = "query"),
    })
    public ServerResponse addBrand(Brand brand){

        return brandService.addBrand(brand);
    }

    @GetMapping
    @ApiOperation(value = "查询品牌",notes = "查询品牌", httpMethod = "GET", response = ServerResponse.class)
    public ServerResponse selectBrand(){

        return brandService.selectBrand();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除品牌",notes = "删除品牌", httpMethod = "DELETE", response = ServerResponse.class)
    @ApiImplicitParam(paramType="path", name = "id", value = "用户ID", required = true, dataType = "Long")
    public ServerResponse deleteBrand(@PathVariable Long id){

        return brandService.deleteBrand(id);
    }

    @PutMapping
    @ApiOperation(value = "修改品牌",notes = "修改品牌", httpMethod = "PUT", response = ServerResponse.class)
    @ApiImplicitParam(name = "brand",value ="brand" ,required = true,type = "string",paramType = "body")
    public ServerResponse updateBrand(@RequestBody Brand brand){

        return brandService.updateBrand(brand);
    }

    @DeleteMapping
    @ApiOperation(value = "批量删除",notes = "批量删除", httpMethod = "DELETE", response = ServerResponse.class)
    @ApiImplicitParam(name = "ids",value ="ids" ,required = true,type = "string",paramType = "query")
    public ServerResponse delBach(String ids){

        return brandService.delBach(ids);
    }

}
