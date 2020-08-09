package com.fh.shop.api.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MQConfig {

    public  static final  String  MAILEXCHANGE="mailExchange";
    public  static final  String  MAILQUEUE="mailQueue";
    public  static final  String  MAILKEY="mailKey";

     public  static final  String  ORDEREXCHANGE="orderExchange";
    public  static final  String  ORDERQUEUE="orderQueue";
    public  static final  String  ORDERKEY="orderKey";




    public  static final  String  FINOUTEXCHANGE="finoutExchange";
    public  static final  String  FINOUTQUEUE1="fanoutQueue1";
    public  static final  String  FINOUTQUEUE2="fanoutQueue2";

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(ORDEREXCHANGE,true,false);
    }

    @Bean
    public Queue orderQueue(){
        return  new Queue(ORDERQUEUE,true,false,false);
    }
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDERKEY);
    }

  /* //队列
    @Bean
    public Queue testQueue(){
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-message-ttl",5000);
        args.put("x-dead-letter-exchange","testDlxExchange");
        return  new Queue("test-queue",true,false,false,args);
    }
    //交换机
    @Bean
    public DirectExchange testExchange(){

        return new DirectExchange("testExchange",true,false);
    }
    //绑定交换机和队列
    @Bean
    public Binding testBinding(){
        return BindingBuilder.bind(testQueue()).to(testExchange()).with("test");
    }



    //死信队列
    @Bean
    public Queue testDLXQueue(){
        return  new Queue("test-dlx-queue",true);
    }
    //交换机
    @Bean
    public DirectExchange testDLXExchange(){

        return new DirectExchange("testDlxExchange",true,false);
    }
    //绑定交换机和队列
    @Bean
    public Binding testDXLBinding(){
        return BindingBuilder.bind(testDLXQueue()).to(testDLXExchange()).with("#");
    }*/
  /*   @Bean
     public FanoutExchange   fanoutExchange(){
         return  new FanoutExchange(MQConfig.FINOUTEXCHANGE,true,false);
     }

     @Bean
     public Queue finoutqueue1(){
         return new Queue(MQConfig.FINOUTQUEUE1,true,false,false);
     }

     @Bean
     public Queue finoutqueue2(){
         return new Queue(MQConfig.FINOUTQUEUE2,true,false,false);
     }

     @Bean
     public Binding finoutBinding1(){
         return BindingBuilder.bind(finoutqueue1()).to(fanoutExchange());
     }

     @Bean
     public Binding finoutBinding2(){
         return BindingBuilder.bind(finoutqueue2()).to(fanoutExchange());
     }
*/
     @Bean
     public DirectExchange mailExchange(){
         return new DirectExchange(MQConfig.MAILEXCHANGE,true,false);
     }

     @Bean
     public Queue mailQueue(){
         return  new Queue(MQConfig.MAILQUEUE,true,false,false);
     }

     @Bean
     public Binding mailBinding(){
         return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MQConfig.MAILKEY);
     }




}
