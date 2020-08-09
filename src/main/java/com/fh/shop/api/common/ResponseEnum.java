package com.fh.shop.api.common;

public enum ResponseEnum {

    MEMBERNAME_IS_NULL(1001,"用户名不能为空"),
    PASSWORD_IS_NULL(1002,"密码不能为空"),
    REALNAME_IS_NULL(1003,"真实姓名不能为空"),
    BIRTHDAY_IS_NULL(1004,"出生年月日为空"),
    MAIL_IS_NULL(1005,"邮箱不能为空"),
    PHONE_IS_NULL(1006,"电话号码不能为空"),
    MEMBERNAME_IS_CUNZAI(1007,"用户名已存在"),
    PHONE_IS_CUNZAI(1008,"手机号已存在"),
    MAIL_IS_CUNZAI(1009,"邮箱已存在"),
    MEMBER_IS_NULL(1010,"用户信息不能为空"),
    MEMBER_IS_ERROR(1011,"用户信息不存在"),
    PASSWORD_IS_ERROR(1012,"密码输入错误,请重新输入"),
    HEADER_IS_NULL(1013,"头信息丢失"),
    HEADER_IS_NOT_COMPLETE(1014,"头信息不完整"),
    DATA_IS_ERROR(1015,"数据被篡改"),
    LOGIN_TIME_IS_OUT(1016,"登录已失效请重新登录"),
    CART_PRODUCT_IS_NULL(2000,"商品不存在"),
    CART_PRODUCT_IS_DOWN(2001,"商品已下架"),
    CART_NUM_IS_ERROR(2002,"传入的数量有误"),
    ORDER_PRODUCT_STOCK_IS_LEE(2003,"商品库存不足，请重新购买！！"),
    ORDER_CREATE_IS_ERROR(2004,"创建订单失败！"),
    ORDER_IS_PAIDUI(2005,"订单正在排队处理！"),
    PAL_LOG_IS_NULL(2006,"没有要支付的订单！"),
    PAL_TIM_IS_OUT(2007,"支付超时！"),
    TOKEN_IS_NULL(2008,"token头信息丢失！"),
    TOKEN_IS_ERROR(2009,"token头信息错误！"),
    TOKEN_IS_REPEAT(2010,"请求重复,拒绝处理！"),
    ;

    private Integer code;
    private String msg;





    private ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
