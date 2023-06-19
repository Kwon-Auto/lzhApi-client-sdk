package com.lzh.lzhapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 生成签名算法的工具类
 */
public class GenSignUtils {
    /**
     * 生成的签名算法
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body,String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }
}
