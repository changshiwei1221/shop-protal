package com.fh.shop.api.rabbitMq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailMsg implements Serializable {

    private String toMail;

    private String title;

    private String content;
}
