package com.fh.shop.api;

import cn.hutool.crypto.asymmetric.RSA;
import com.fh.shop.api.Config.MyConfig;
import com.fh.shop.api.rabbitMq.MQsender;
import com.fh.shop.github.wxpay.sdk.WXPay;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@EnableScheduling
class ShopApiApplicationTests {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    void contextLoads() {
    }


    @Autowired
    private MQsender mQsender;
   /* @Test
    public void mqSend(){
        for (long i = 0; i < 10; i++) {
            Member member = new Member();
            member.setId(i);
            member.setMemberName("test");
            member.setPhone("124124124124");
            mQsender.sendTest(member);
        }

    }

    @Test
    public void mqSend1(){
        for (long i = 0; i < 10; i++) {
            Member member = new Member();
            member.setId(i);
            member.setMemberName("test");
            member.setPhone("124124124124");
            mQsender.sendTest1(member);
        }
    }*/
  /* @Test
   public void SendMail(){

       mQsender.sendMail("发送邮件！！！！");
   }*/
   @Test
    public void mqTest() {
        rabbitTemplate.convertAndSend("hello","测试");
    }

    @Test
    public void payTest(){


        MyConfig config = null;
        WXPay wxpay=null;
        try {
            config = new MyConfig();
             wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "飞狐商城");
        data.put("out_trade_no", "2016090910595900000082");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "192.168.13.88");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", "12");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void payQuery(){
        MyConfig config = new MyConfig();
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", "2016090910595900000022");

        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    }


