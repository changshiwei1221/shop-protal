package com.fh.shop.api.Member.controller;

import com.fh.shop.api.Member.biz.IMemberService;
import com.fh.shop.api.Member.po.Member;
import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.common.Login;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.utils.KeyUtils;
import com.fh.shop.api.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("members")
@Api(value = "members",description="会员接口")
public class MemberController {


    @Autowired
    private IMemberService memberService;

    @PostMapping("add")
    @ApiOperation(value = "会员注册",notes = "会员注册", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName",value ="会员名" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "password",value ="密码" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "realName",value ="真实姓名" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "birthday",value ="出生年月" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "mail",value ="邮箱" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "phone",value ="电话" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "shengId",value ="省Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "shiId",value ="市Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "xianId",value ="县Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "areaName",value ="地区名" ,required = false,type = "string",paramType = "query"),
    })
    public ServerResponse register(Member member){

        return  memberService.register(member);
    }


    @GetMapping("query")
    @ApiOperation(value = "会员查询",notes = "会员查询", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName",value ="会员名" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "password",value ="密码" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "realName",value ="真实姓名" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "birthday",value ="出生年月" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "mail",value ="邮箱" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "phone",value ="电话" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "shengId",value ="省Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "shiId",value ="市Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "xianId",value ="县Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "areaName",value ="地区名" ,required = false,type = "string",paramType = "query"),
    })
    public ServerResponse query(Member member){

        return  memberService.query(member);
    }


    @PostMapping("login")
    @ApiOperation(value = "会员登陆",notes = "会员登陆", httpMethod = "POST", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName",value ="会员名" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "password",value ="密码" ,required = true,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "realName",value ="真实姓名" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "birthday",value ="出生年月" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "mail",value ="邮箱" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "phone",value ="电话" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "shengId",value ="省Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "shiId",value ="市Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "xianId",value ="县Id" ,required = false,type = "string",paramType = "query"),
            @ApiImplicitParam(name = "areaName",value ="地区名" ,required = false,type = "string",paramType = "query"),
    })
    public ServerResponse login(Member member){

        return  memberService.login(member);
    }


    @GetMapping("memberInfo")
    @Login
    @ApiOperation(value = "查询当前登录的会员",notes = "查询当前登录的会员", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse memberInfo(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        return  ServerResponse.success(memberVo);
    }

    @GetMapping("logOut")
    @Login
    @ApiOperation(value = "退出登录",notes = "退出登录", httpMethod = "GET", response = ServerResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value ="头信息" ,required = true,type = "string",paramType = "header"),
    })
    public ServerResponse logOut(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        RedisUtil.del(KeyUtils.memberKey(memberVo.getId(),memberVo.getUuid()));
        return  ServerResponse.success();
    }
}
