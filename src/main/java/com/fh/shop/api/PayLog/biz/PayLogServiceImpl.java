package com.fh.shop.api.PayLog.biz;

import com.fh.shop.api.PayLog.mapper.PayLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayLogServiceImpl implements PayLogService {
    @Autowired
    private PayLogMapper payLogMapper;
}
