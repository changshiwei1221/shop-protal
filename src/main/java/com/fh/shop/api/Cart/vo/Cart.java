package com.fh.shop.api.Cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.shop.api.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    private Integer totalNum;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal totalPrice;

    private List<CartItem> itemList=new ArrayList<>();

}
