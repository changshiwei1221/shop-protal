package com.fh.shop.api.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.UnsupportedEncodingException;

public class RsaUtils {
  public static  final  String PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC25QjSyEYFIG2X1XF0+Q4qwEZZd+ZN6g+QDx5g6FOa5B4fe/X1YFCMmrROuxm6+romXx9zQZO4Oh//qp33/PBE4lu2VfDQN4ECf3kMM/z+NkcfyoL6ZXeA+OM+SmUJIrIxK7pX72J7p7vjgcPS2Ly8+KQGs0e+iOXw2r1yNc+44wIDAQAB";
  public static  final  String PRIVATEKEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALblCNLIRgUgbZfVcXT5DirARll35k3qD5APHmDoU5rkHh979fVgUIyatE67Gbr6uiZfH3NBk7g6H/+qnff88ETiW7ZV8NA3gQJ/eQwz/P42Rx/Kgvpld4D44z5KZQkisjErulfvYnunu+OBw9LYvLz4pAazR76I5fDavXI1z7jjAgMBAAECgYB8PgpIZhRq1ySbyDtSov2kvQkNLg4dT6tSyXJxkmF5Dw4HhUYIcm1FlrJ16VQJWNmrx2KuARZAR/wEyfqVxBXTD3yEbPfigQgMzN+bQdfpHrpkpHEV4xidE2TLDxakCHvGfscBkehC5USF+EzRSm4bqSnrvZnkWeXVL6mnW6TJQQJBAN4QPBKH0dsdrX3ObaVlZmdv48SV4KwJF69rfz4wHtpODZGB5XcrOoUm89u5NN1pHdq5RPsj+1xOOAJP25C6ST8CQQDS2GeT+wGfKv4I6NAje1Sy/U34dfFDxG8DoFQpxq1KGCZk06e8Aw+PMgN+XzSMyaxS1T7kl07Eke5cI00agKNdAkEAzm7RKXjRgZypN5a7H1KQTAAcARhDcCpTtmN8OleJlu+QdYAHzSyGjlmTwOL/XgTmF/q7QaxFc53TO3L5biV/CQJAJr6jkfrfGmuhEOwPS2Xfc6C+kjjCJAzVxZnRvXeH4oS7kW2fdhot4sdzAubl1jU9GF+dVg5D6DVU0tOd2I/o9QJAKKdtLE/FiSZllk4vOargoRAUPKzqy9mcpHVQui4qbIgV9/dIHQ20wdNgH6da4x71mNLsRywLLovzvwfV6bCG9w==";

  public static String decrypt(String data) throws UnsupportedEncodingException {
      RSA rsa = new RSA(PRIVATEKEY,PUBLICKEY);
      byte[] bytes = rsa.decryptFromBase64(data, KeyType.PrivateKey);
      String result = new String(bytes, "utf-8");
      return result;
  }
}
