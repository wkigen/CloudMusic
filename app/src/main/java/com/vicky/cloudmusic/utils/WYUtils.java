package com.vicky.cloudmusic.utils;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.net.IHandleHttpParams;
import com.vicky.android.baselib.utils.AES;
import com.vicky.android.baselib.utils.StringUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by vicky on 2017/8/29.
 */
public class WYUtils {

    public static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("Referer", "http://music.163.com");
        headers.put("Origin", "http://music.163.com");
        headers.put("cookie", "appver=1.5.21");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
    }

    private final static String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7" +
            "b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280" +
            "104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932" +
            "575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b" +
            "3ece0462db0a22b8e7";

    private final static String nonce = "0CoJUm6Qyw8W8jud";
    private final static String pubKey = "010001";

    public static IHandleHttpParams handleHttpParams = new IHandleHttpParams() {
        @Override
        public Map<String, String> handleParams(Map<String, String> map) {

            StringBuilder params = new StringBuilder();
            StringBuilder encSecKey = new StringBuilder();
            getParamJson(map,params,encSecKey);

            Map<String,String> finslParams = new HashMap<>();
            finslParams.put("params",params.toString());
            finslParams.put("encSecKey",encSecKey.toString());

            return finslParams;
        }
    };

    public static void getParamJson(Map<String,String> data,StringBuilder outParams,StringBuilder outEncSecKey) {

        String paramJson = JSON.toJSONString(data);

        String aesKey = StringUtils.getRandomString(16);
        String enData = encryptAES(paramJson,aesKey);

        outParams.append(enData);
        outEncSecKey.append(encryptRSA(aesKey,pubKey,modulus));
    }


    public static String encryptAES(String data,String key){
        byte[] enData =  AES.encrypt(data.getBytes(),nonce.getBytes(),AES.AES_CBC_PKCS5_ALGORITHM,"0102030405060708");
        enData = AES.encrypt(enData,key.getBytes(),AES.AES_CBC_PKCS5_ALGORITHM,"0102030405060708");
        return  Base64.encodeToString(enData, Base64.DEFAULT);
    }

    public static String encryptRSA(String data, String pubKey, String modulus) {
        data = new StringBuilder(data).reverse().toString();
        BigInteger rs = new BigInteger(String.format("%x", new BigInteger(1, data.getBytes())), 16)
                .modPow(new BigInteger(pubKey, 16), new BigInteger(modulus, 16));
        String r = rs.toString(16);
        if (r.length() >= 256) {
            return r.substring(r.length() - 256, r.length());
        } else {
            while (r.length() < 256) {
                r = 0 + r;
            }
            return r;
        }
    }

}
