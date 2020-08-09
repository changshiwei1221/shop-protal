package com.fh.shop.api.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayInfo implements Serializable {
    private String codeUrl;
    private String orderId;
    private String totlPrice;

}
