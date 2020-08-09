package com.fh.shop.api.Recipient.controller;

import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.Recipient.biz.RecipientService;
import com.fh.shop.api.Recipient.po.Recipient;
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

@RequestMapping("recipients")
@RestController
@Api(value = "recipients",description="订单信息接口")
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @GetMapping("recipientList")
    @Login
    @ApiOperation(value = "查询当前登录的会员的订单信息",notes = "查询当前登录的会员的订单信息", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse recipientList(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        return  recipientService.recipientList(memberVo);
    }


    @PostMapping("addRecipient")
    @Login
    @ApiOperation(value = "新增地址",notes = "新增地址", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipientName", value = "收件人姓名", required = true, type = "string", paramType = "query"),
            @ApiImplicitParam(name = "recipientPhone", value = "收件人电话", required = true, type = "string", paramType = "query"),
            @ApiImplicitParam(name = "recipientArea", value = "收件人地址", required = true, type = "string", paramType = "query"),
            @ApiImplicitParam(name = "recipientEmail", value = "收件人邮箱", required = true, type = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaType", value = "地址类型", required = true, type = "string", paramType = "query"),
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),

    })
    public ServerResponse addRecipient(Recipient recipient,HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberVoId = memberVo.getId();
        recipient.setMemberId(memberVoId);
        return  recipientService.addRecipient(recipient);
    }


    @DeleteMapping("/{id}")
    @Login
    @ApiOperation(value = "删除收货人信息",notes = "删除收货人信息", httpMethod = "DELETE", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),

    })
    public ServerResponse deleteRecipient(@PathVariable Long id,HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberVoId = memberVo.getId();
        return recipientService.deleteRecipient(id,memberVoId);
    }


    @GetMapping("recipientOne")
    @Login
    @ApiOperation(value = "查询收货人信息",notes = "查询收货人信息", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name = "id", value = "ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse recipientOne(Long id,HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberVoId = memberVo.getId();
        return  recipientService.recipientOne(id,memberVoId);
    }


}
