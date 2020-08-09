package com.fh.shop.api.Order.Po;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrderParam implements Serializable {

    private Long memberId;

    private Long recipientId;

    private Integer payType;



}
