package com.fh.shop.api.rabbitMq;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.Cart.biz.CartServiceImpl;
import com.fh.shop.api.Cart.vo.Cart;
import com.fh.shop.api.Cart.vo.CartItem;
import com.fh.shop.api.Config.MQConfig;
import com.fh.shop.api.Order.Po.Order;
import com.fh.shop.api.Order.Po.OrderItem;
import com.fh.shop.api.Order.Po.OrderParam;
import com.fh.shop.api.Order.mapper.OrderItemMapper;
import com.fh.shop.api.Order.mapper.OrderMapper;
import com.fh.shop.api.PayLog.mapper.PayLogMapper;
import com.fh.shop.api.PayLog.po.PayLog;
import com.fh.shop.api.Product.mapper.ProductMapper;
import com.fh.shop.api.Product.po.Product;
import com.fh.shop.api.Recipient.mapper.RecipientMapper;
import com.fh.shop.api.Recipient.po.Recipient;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.exception.OrderStockException;
import com.fh.shop.api.utils.EmailHelper;
import com.fh.shop.api.utils.KeyUtils;
import com.fh.shop.api.utils.RedisUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MQReceiver {
    @Autowired
    private EmailHelper emailHelper;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private RecipientMapper recipientMapper;


    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PayLogMapper payLogMapper;

  /*  @RabbitListener(queues = "hello")
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

    //接受消息  默认是推的方式   queues=队列名
    @RabbitListener(queues = "test-queue")
    public void handleTestMessage(String msg){
        System.out.println(msg);
    }
*/
  @RabbitListener(queues = MQConfig.MAILQUEUE)
  public void handleMail(String mailJson ,Message message, Channel channel) throws IOException {
      MessageProperties messageProperties = message.getMessageProperties();
      long deliveryTag = messageProperties.getDeliveryTag();
      MailMsg mailMsg = JSONObject.parseObject(mailJson, MailMsg.class);
      emailHelper.sendEmail(mailMsg.getToMail(),mailMsg.getTitle(),mailMsg.getContent());
      channel.basicAck(deliveryTag,false);
  } 
  
  
  @RabbitListener(queues = MQConfig.ORDERQUEUE)
  @Transactional(rollbackFor = Exception.class)
  public void handleOrder(String orderJson, Message message, Channel channel) throws IOException {
      MessageProperties messageProperties = message.getMessageProperties();
      long deliveryTag = messageProperties.getDeliveryTag();
      OrderParam orderParam = JSONObject.parseObject(orderJson, OrderParam.class);
      Long memberId = orderParam.getMemberId();
      Integer payType = orderParam.getPayType();
      Long recipientId = orderParam.getRecipientId();
      //通过会员id获取会员的购物车
      String cartJson = RedisUtil.get(KeyUtils.cartKey(memberId));
      Cart cart = JSONObject.parseObject(cartJson, Cart.class);
      List<CartItem> itemList = cart.getItemList();
      List<Long> idList=itemList.stream().map(x ->x.getProductId()).collect(Collectors.toList());
      QueryWrapper<Product> wrapper = new QueryWrapper<>();
      wrapper.in("id",idList);
      List<Product> productList = productMapper.selectList(wrapper);
      //List<Product> productList = productMapper.selectList(null);
      Order order = new Order();
      order.setId(IdWorker.getIdStr());
      //判断购物车中的商品 库存是否足够
      if (cart!=null&&itemList!=null){
          Iterator<CartItem> iterator = itemList.iterator();
          while (iterator.hasNext()){
              CartItem next = iterator.next( );
              for (Product product : productList) {
                  //找到对应的商品
                  if (next.getProductId().longValue()==product.getId().longValue()){
                      //判断该商品库存是否充足
                      if (Integer.valueOf(next.getNum())>Integer.valueOf(product.getStock())){
                          //库存不足
                            RedisUtil.set(KeyUtils.orderStockKey(memberId),"stock lee");
                            channel.basicAck(deliveryTag,false);
                            return;
                      }//该商品库存充足
                      else {
                          //减库存
                          int updateStock = productMapper.updateStock(product.getId(), next.getNum());
                          if (updateStock==0){
                              //提示信息
                              RedisUtil.set(KeyUtils.orderStockKey(memberId),"stock lee");
                              channel.basicAck(deliveryTag,false);
                              throw new OrderStockException("stock lee");
                          }
                      }
                      //插入订单详情表
                      OrderItem orderItem = new OrderItem();
                      orderItem.setOrderId(order.getId());
                      orderItem.setMemberId(memberId);
                      orderItem.setProductImgUrl(product.getMainImagePath());
                      orderItem.setProductName(product.getProductName());
                      orderItem.setProductNum(next.getNum());
                      orderItem.setProductPrice(next.getPrice());
                      orderItem.setProductSubPrice(next.getSubPrice());
                      orderItemMapper.insert(orderItem);
                  }

              }
          }
          //生成订单
          order.setCreateTime(new Date());
          order.setMemberId(memberId);
          order.setPayType(payType);
          order.setTotalNum(cart.getTotalNum());
          order.setTotalPrice(cart.getTotalPrice());
          order.setStatus(SystemConst.WEIZHIFU);
          Recipient recipient = recipientMapper.selectById(recipientId);
          order.setRecipientName(recipient.getRecipientName());
          order.setRecipientPhone(recipient.getRecipientPhone());
          order.setRecipientArea(recipient.getRecipientArea());
          order.setRecipientMail(recipient.getRecipientEmail());
          orderMapper.insert(order);
          //生成支付日志表
          PayLog payLog = new PayLog();
          payLog.setOutTradeNo(IdWorker.getIdStr());
          payLog.setMemberId(memberId);
          payLog.setOrderId(order.getId());
          payLog.setPayCreateTime(new Date());
          payLog.setPayStatu(SystemConst.WEIZHIFU);
          payLog.setPayTotalPrice(order.getTotalPrice());
          payLog.setPayType(payType);
          payLogMapper.insert(payLog);
          String payLogJson = JSONObject.toJSONString(payLog);
          RedisUtil.set(KeyUtils.payLog(memberId),payLogJson);
          //删除购物车的数据
          RedisUtil.del(KeyUtils.cartKey(memberId));
          RedisUtil.set(KeyUtils.orderSuccess(memberId),"ok");
          channel.basicAck(deliveryTag,false);
      }
  }
}
