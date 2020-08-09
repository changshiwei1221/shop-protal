package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.Config.MyConfig;
import com.fh.shop.api.Order.Po.Order;
import com.fh.shop.api.Order.mapper.OrderMapper;
import com.fh.shop.api.PayLog.mapper.PayLogMapper;
import com.fh.shop.api.PayLog.po.PayLog;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.pay.vo.PayInfo;
import com.fh.shop.api.utils.BigDecimalUtil;
import com.fh.shop.api.utils.DateTimeUtils;
import com.fh.shop.api.utils.KeyUtils;
import com.fh.shop.api.utils.RedisUtil;
import com.fh.shop.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayService {

    @Autowired
    private PayLogMapper payLogMapper;

    @Autowired
    private OrderMapper orderMapper;
    public ServerResponse createOrder(Long memberVoId) {
        String payLog = RedisUtil.get(KeyUtils.payLog(memberVoId));
        PayLog log = JSONObject.parseObject(payLog, PayLog.class);
        if (log==null){
            return ServerResponse.error(ResponseEnum.PAL_LOG_IS_NULL);
        }

        MyConfig config = null;
        WXPay wxpay=null;
        try {
            config = new MyConfig();
            wxpay = new WXPay(config);
            String outTradeNo = log.getOutTradeNo();
            BigDecimal payTotalPrice = log.getPayTotalPrice();
            int totlPrice = BigDecimalUtil.cheng(payTotalPrice.toString(), 100 + "").intValue();
            Date date = DateTimeUtils.addMinutes(new Date(), 2);
            String time = DateTimeUtils.date2str(date, DateTimeUtils.FULL_YEAR_INFO);
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐商城");
            data.put("out_trade_no", outTradeNo);
            data.put("device_info", "");
            data.put("time_expire", time);
            data.put("fee_type", "CNY");
            data.put("total_fee", totlPrice+"");
            data.put("spbill_create_ip", "192.168.13.88");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            Map<String, String> resp = wxpay.unifiedOrder(data);
            String return_code = resp.get("return_code");
            String return_msg = resp.get("return_msg");
            if (!return_code.equalsIgnoreCase("SUCCESS")) {
                System.out.println(return_msg);
                return ServerResponse.error(9999, "微信支付平台错误" + return_msg, null);
            }
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if (!result_code.equalsIgnoreCase("SUCCESS")) {
                System.out.println(err_code_des);
                return ServerResponse.error(9999, "微信支付平台错误" + err_code_des, null);
            }
            String code_url = resp.get("code_url");
            PayInfo payInfo = new PayInfo();
            payInfo.setCodeUrl(code_url);
            payInfo.setOrderId(outTradeNo);
            payInfo.setTotlPrice(payTotalPrice+"");
            return ServerResponse.success(payInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }


    public ServerResponse queryOrder(Long memberVoId) {
        String payLog = RedisUtil.get(KeyUtils.payLog(memberVoId));
        PayLog log = JSONObject.parseObject(payLog, PayLog.class);
        if (log==null){
            return ServerResponse.error(ResponseEnum.PAL_LOG_IS_NULL);
        }
        String outTradeNo = log.getOutTradeNo();
        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", outTradeNo);
            int count = 0;
            while (true) {
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if (!return_code.equalsIgnoreCase("SUCCESS")) {
                    System.out.println(return_msg);
                    return ServerResponse.error(9999, "微信支付平台错误" + return_msg, null);
                }
                String result_code = resp.get("result_code");
                String err_code = resp.get("err_code");
                if (!result_code.equalsIgnoreCase("SUCCESS")) {
                    System.out.println(err_code);
                    return ServerResponse.error(9999, "微信支付平台错误" + err_code, null);
                }
                String trade_state = resp.get("trade_state");
                if (trade_state.equalsIgnoreCase("SUCCESS")) {
                    System.out.println("========支付成功");
                    //修改订单表和日志表
                    String transaction_id = resp.get("transaction_id");
                    log.setPayStatu(SystemConst.YIZHIFU);
                    log.setPayTime(new Date());
                    log.setOutTradeNo(outTradeNo);
                    log.setTransactionId(transaction_id);
                    payLogMapper.updateById(log);

                    //修改订单表
                    Order order = new Order();
                    order.setStatus(SystemConst.YIZHIFU);
                    order.setId(log.getOrderId());
                    order.setPayTime(new Date());
                    orderMapper.updateById(order);

                    //清除redis
                    RedisUtil.del(KeyUtils.payLog(memberVoId));
                    return ServerResponse.success();
                }
                count++;
                Thread.sleep(3000);
                if (count >= 40) {
                    System.out.println("=====支付超时");
                    return ServerResponse.error(ResponseEnum.PAL_TIM_IS_OUT);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}

