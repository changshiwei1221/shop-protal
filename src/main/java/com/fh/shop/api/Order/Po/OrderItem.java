package com.fh.shop.api.Order.Po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("t_orderItem")
public class OrderItem implements Serializable {

    private Long id;
    private String orderId;
    private Long memberId;
    private String productName;
    private BigDecimal productSubPrice;
    private Integer productNum;
    private String productImgUrl;
    private BigDecimal productPrice;
}
