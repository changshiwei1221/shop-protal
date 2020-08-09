package com.fh.shop.api.rabbitMq;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.Config.MQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQsender {

    @Autowired
    public AmqpTemplate amqpTemplate;

  /*  public  void sendTest(Member member){

         String jsonString = JSONObject.toJSONString(member);
       //交换机，路由key,消息的数据
        amqpTemplate.convertAndSend("testExchange","test",jsonString);
    }


    //使用默认交换机
    public  void sendTest1(Member member){

        String jsonString = JSONObject.toJSONString(member);
        //交换机，路由key,消息的数据
        amqpTemplate.convertAndSend("test-queue",jsonString);
    }*/

  public void sendMail(MailMsg mailMsg){
      String mailJson = JSONObject.toJSONString(mailMsg);
      amqpTemplate.convertAndSend(MQConfig.MAILEXCHANGE,MQConfig.MAILKEY,mailJson);
  }
}
