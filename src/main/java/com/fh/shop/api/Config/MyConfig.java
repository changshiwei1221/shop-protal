package com.fh.shop.api.Config;

import com.fh.shop.github.wxpay.sdk.IWXPayDomain;
import com.fh.shop.github.wxpay.sdk.WXPayConfig;
import com.fh.shop.github.wxpay.sdk.WXPayConstants;

import java.io.InputStream;


public class MyConfig extends WXPayConfig {


    @Override
    public String getAppID() {
        return "wxa1e44e130a9a8eee";
    }

    @Override
    public String getMchID() {
        return "1507758211";
    }

    @Override
    public String getKey() {
        return "feihujiaoyu12345678yuxiaoyang123";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
