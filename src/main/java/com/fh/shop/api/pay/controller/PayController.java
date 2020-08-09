package com.fh.shop.api.pay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.fh.shop.api.Member.vo.MemberVo;
import com.fh.shop.api.common.Login;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConst;
import com.fh.shop.api.pay.biz.PayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pays")
@Api(value = "pays",description="支付接口")
public class PayController {



    @Autowired
    private PayService payService;

    @GetMapping("createOrder")
    @Login
    public ServerResponse  createOrder(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberVoId = memberVo.getId();
        return payService.createOrder(memberVoId);
    }

    @GetMapping("queryOrder")
    @Login
    public ServerResponse  queryOrder(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConst.MEMBERINFO);
        Long memberVoId = memberVo.getId();
        return payService.queryOrder(memberVoId);
    }


    @GetMapping("zhifubao")
    public ServerResponse  zhifubao(){
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    .preCreate("Apple iPhone11 128G", "2231527890", "5799.00");
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return ServerResponse.success();
    }


    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        config.appId = "201609250059132";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCcF+Oc3uJEFt59LXB30LkP/Ocn6cMtxTeeqiM99tzGi3nyMao33jPYjwKvQk+djDb7i4bgJNVjx5QtEuJruMsiOMUO34QzYqnSZLLdA8Oltbf5LG/+qJMnF+K1dw9DWvw5dF6rbXvvqMpzruBJ99ntbWBK5hE/ayYODPhmcQ8rLV0nedYCb4nIhQueF3l6pFDG5e/eRyTj3Vb6faSf30DTKhMk4Yvj3Mx3f8h4eGGjFvvbSKdO8oI8HQYkEQh8k8pqkkQs3FSawp5KNAH13CNzGEnLewsOaHiEEwT1qSTc7ppewp7oyP2KtXw9TQySlkNFyLorqUHjF71hgCFmDEIxAgMBAAECggEACT5c60hDq/aSvGT5eQ7yNqEqazGrrkU0kv/OUHgTAOr3kMEiLGbLOTLW3NwXbOQFyYWsug9UV7FU9ApfQDPUS7WC7euMn6JjHiN6eB4l8uU8/NOF2lXPSQxgD5D6ZWm48AoVJR+5rCqZupoLjDXSQP4uKqFPOis1OLXJ6/8b/9l7L0jf2eW70Ht6HrNxfXTAn07FoJAEHnjePCgyYHuor9oD/0bi/0KRJkeQtqMZHX3oYhhYmQzIMTXIi9hMd+U6lmaknDFV8AMOfemX0Dq6KNYPwWP86YWelZ230xHXgE3Jk493unFZetliGG6vVyq4Whd5OTcXmE64+8GRKxO5QQKBgQDOwRR4KhyqbA7LXjyNhYXr86pOEt4wpeZGFsbcryRsxf0HSZpVWwUMVsOFkHRBZ3InbkfRCdHMBcqiA5dDwYOyo4l0R7sWdc9rteXvLdw57tBDbkiLK2HW+iUP0mXXghiD7ynyaAyLdvu5upUdDj1G1OI40Ye1ThOK60WseXyC6QKBgQDBRb2dg4YDKh0HtTsl8e/A1rPzob7KyRSi7bY31WtEOETeY6FMsC9jMm6fxJtT+Xtp5XfrWhQptn0E1fLJ7c7TAM1hFs2YBzOLKzinVxo0nFfRGkMrBotoAQmh+SKME1BjyW8qGeVIC8DSSazvVDDpp3Z6+WjNESL7e8yAn3VoCQKBgDVxGwVkfTxf6gMPPL+n/9HZje/YWK5ic38ya/ynGNc05g54K/hcKpXh53AiwqoVPyCKFO4nh58g8BuoqfTNlGkcdG8mC2nm0dKZlfGY/Q/MdvC3FPFEKWoUBnhsff6SlhukOkd6NaPnAXIklF6KS7R/WmotXtKyd8K0xbGrRU/hAoGAX4diHp3oul2xdaLddnbsA0vcLFrRHBhhq9qFC3ngiBqr56QlEOuwsH438Z5TDCGvH3t9V4Xxm2CI0MATPj3d0at+2DNWMYO1tV8KpKPH3yM7wK4TbwvQEfsZUMb8jXHqdTgWSx/7lS3CA+7InI7nkkXkqQnoBBwpR1Y8kYK/nEkCgYBmVBBabnYfaUkooDBPOZu7xCQH1xf+yPV0jH8T8FMYFn6biCXVnHhjZ5Mm8h8Y7gctRpZtqnG30TD71HLsRohxfQkxRyp9NvhiJpingBL860lM89JA3C/pNDf8FV3hq4RFIPDkBA3v7QbytDTNl4bQ7oYiFegWndLVBboNIntNFg==";

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
     /*   config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
        config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
        config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";*/

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
         config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1ynoaKrpzpipzhsivNwIRBrEPMeFYLWkWSMU9E3KAdBBpSGHN9x3OVWYe/F3yxRgLtbOdqN1DBYiKLUBvUu2NAqH1KV6nkt3Rx/gc7MKMwL+TWBmXKX5L1eK17TOPKMXoqVN5grFrCH0hx/U6lZnr2M/7A93yPlSKLgJwVYj560wzElBL+fvjR0V0OA+FWJryKluBtJYUKXBadFKuyVwNnyGqmHFxvtSHuErhOrg0YiYIfhV3f7zE5q1mIz7ecwH0W7nB7gtFjLt1k4WSpwyeEI8Xpl7SLMvHdwEXUwTU0h/V7TumYWgUe0ft70NTps+zPwJlmcEluHg0VSs7UrDVQIDAQAB";

        //可设置异步通知接收服务地址（可选）
        //config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        //config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

        return config;
    }
}
