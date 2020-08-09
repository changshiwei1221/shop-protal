package com.fh.shop.api;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;

public class TestRAS {
    @Test
    public void testRsa(){
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();

        System.out.println("公钥"+publicKeyBase64);
        System.out.println("私钥"+privateKeyBase64);
    }
    @Test
    public void test1(){
    String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC25QjSyEYFIG2X1XF0+Q4qwEZZd+ZN6g+QDx5g6FOa5B4fe/X1YFCMmrROuxm6+romXx9zQZO4Oh//qp33/PBE4lu2VfDQN4ECf3kMM/z+NkcfyoL6ZXeA+OM+SmUJIrIxK7pX72J7p7vjgcPS2Ly8+KQGs0e+iOXw2r1yNc+44wIDAQAB";
    String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALblCNLIRgUgbZfVcXT5DirARll35k3qD5APHmDoU5rkHh979fVgUIyatE67Gbr6uiZfH3NBk7g6H/+qnff88ETiW7ZV8NA3gQJ/eQwz/P42Rx/Kgvpld4D44z5KZQkisjErulfvYnunu+OBw9LYvLz4pAazR76I5fDavXI1z7jjAgMBAAECgYB8PgpIZhRq1ySbyDtSov2kvQkNLg4dT6tSyXJxkmF5Dw4HhUYIcm1FlrJ16VQJWNmrx2KuARZAR/wEyfqVxBXTD3yEbPfigQgMzN+bQdfpHrpkpHEV4xidE2TLDxakCHvGfscBkehC5USF+EzRSm4bqSnrvZnkWeXVL6mnW6TJQQJBAN4QPBKH0dsdrX3ObaVlZmdv48SV4KwJF69rfz4wHtpODZGB5XcrOoUm89u5NN1pHdq5RPsj+1xOOAJP25C6ST8CQQDS2GeT+wGfKv4I6NAje1Sy/U34dfFDxG8DoFQpxq1KGCZk06e8Aw+PMgN+XzSMyaxS1T7kl07Eke5cI00agKNdAkEAzm7RKXjRgZypN5a7H1KQTAAcARhDcCpTtmN8OleJlu+QdYAHzSyGjlmTwOL/XgTmF/q7QaxFc53TO3L5biV/CQJAJr6jkfrfGmuhEOwPS2Xfc6C+kjjCJAzVxZnRvXeH4oS7kW2fdhot4sdzAubl1jU9GF+dVg5D6DVU0tOd2I/o9QJAKKdtLE/FiSZllk4vOargoRAUPKzqy9mcpHVQui4qbIgV9/dIHQ20wdNgH6da4x71mNLsRywLLovzvwfV6bCG9w==";
        RSA rsa = new RSA(privateKey, publicKey);
        String s = rsa.encryptBase64("啊啊啊", KeyType.PublicKey);
        System.out.println(s);
        byte[] bytes = rsa.decryptFromBase64(s, KeyType.PrivateKey);
        System.out.println(new String(bytes));
    }
}
