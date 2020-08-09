package com.fh.shop.api.Order.biz;

import com.fh.shop.api.Order.Po.OrderParam;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.stereotype.Service;

public interface OrderService {
    ServerResponse createOrder(OrderParam orderParam);

}
