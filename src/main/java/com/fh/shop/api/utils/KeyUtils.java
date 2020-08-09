package com.fh.shop.api.utils;

public class KeyUtils {
    public static  final int MEMBER_EXPIRE=30*60;
    public static  String memberKey(Long id,String uuid){
        return "member:"+id+":"+uuid;
    }
    public static  String cartKey(Long id){
        return "cart:"+id;
    }

    public static String orderStockKey(Long memberId) {
        return "orderStockKey:"+memberId;
    }

    public static String orderSuccess(Long memberId) {
        return "orderSuccess:"+memberId;
    }

    public static String payLog(Long memberId) {
        return "payLog:"+memberId;
    }
}
