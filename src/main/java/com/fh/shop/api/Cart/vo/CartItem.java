package com.fh.shop.api.Cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.shop.api.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartItem implements Serializable {

    private Long productId;

    private String productName;

    private String imgUrl;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal price;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal subPrice;

    private int num;
}
