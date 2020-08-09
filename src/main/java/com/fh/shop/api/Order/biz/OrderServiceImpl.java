package com.fh.shop.api.Order.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.Config.MQConfig;
import com.fh.shop.api.Order.Po.OrderParam;
import com.fh.shop.api.Order.mapper.OrderItemMapper;
import com.fh.shop.api.Order.mapper.OrderMapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.utils.KeyUtils;
import com.fh.shop.api.utils.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public ServerResponse createOrder(OrderParam orderParam) {
        RedisUtil.del(KeyUtils.orderStockKey(orderParam.getMemberId()));
        RedisUtil.del(KeyUtils.orderSuccess(orderParam.getMemberId()));
        String orderParamJson = JSONObject.toJSONString(orderParam);
        rabbitTemplate.convertAndSend(MQConfig.ORDEREXCHANGE,MQConfig.ORDERKEY,orderParamJson);
        return ServerResponse.success();
    }
}
