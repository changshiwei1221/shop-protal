package com.fh.shop.api.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static  final  String  Y_M_D="yyyy-MM-dd";
    public static  final  String  FULL_YEAR="yyyy-MM-dd HH:mm:ss";
    public static  final  String  FULL_YEAR_INFO="yyyyMMddHHmmss";

    //当前时间  后面的 分钟
    public static Date addMinutes(Date date, int minutes) {
        Calendar instance = Calendar.getInstance( );
        instance.setTime(date);
        instance.add(Calendar.MINUTE, minutes);
        Date time = instance.getTime( );
        return time;
    }



     public  static String date2str(Date date,String parame){
        if(date==null){

            return  "";
        }
        SimpleDateFormat sdf=new SimpleDateFormat(parame);
        String result= sdf.format(date);
        return  result;
    }

     public  static  Date str2date(String str,String parame){
         if(StringUtils.isEmpty(str)){
             return null;

         }
         SimpleDateFormat sdf=new SimpleDateFormat(parame);
         Date dateTime = null;
         try {
             dateTime = sdf.parse(str);
         } catch (ParseException e) {
             e.printStackTrace();
         }

         return dateTime;
     }
}
