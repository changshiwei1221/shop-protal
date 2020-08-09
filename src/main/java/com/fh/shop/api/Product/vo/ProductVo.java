package com.fh.shop.api.Product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductVo implements Serializable {

    private Long id;
    private String productName;
    private String price;
    private String mainImagePath;
    private Integer stock;

}
