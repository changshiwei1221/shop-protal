package com.fh.shop.api.PayLog.controller;

import com.fh.shop.api.pay.biz.PayService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payLogs")
public class PayLogController {
    @Autowired
    private PayService payService;

}
