package com.fh.shop.api.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static BigDecimal  cheng(String a,String b){
        BigDecimal a1= new BigDecimal(a);
        BigDecimal b1= new BigDecimal(b);
        return a1.multiply(b1).setScale(2);
    }

    public static BigDecimal  sum(String a,String b){
        BigDecimal a1= new BigDecimal(a);
        BigDecimal b1= new BigDecimal(b);
        return a1.add(b1).setScale(2);
    }
}
