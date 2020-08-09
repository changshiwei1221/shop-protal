package com.fh.shop.api.Recipient.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_recipient")
@Data
public class Recipient implements Serializable {

    private Long id;
    private Long memberId;
    private String recipientName;
    private String recipientPhone;
    private String recipientArea;
    private String areaType;
    private String recipientEmail;
    private Integer deflutArea;



}
