package com.vicky.cloudmusic.utils;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.net.IHandleHttpParams;
import com.vicky.android.baselib.utils.AES;
import com.vicky.android.baselib.utils.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by vicky on 2017/8/29.
 */
public class WYUtils {

    public static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("Referer", "http://music.163.com");
        headers.put("Origin", "http://music.163.com");
        headers.put("cookie", "appver=1.5.2");
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

            Map<String,String> finalParams = new HashMap<>();
            finalParams.put("params",params.toString());
            finalParams.put("encSecKey",encSecKey.toString());

            return finalParams;
        }
    };

    public static void getParamJson(Map<String,String> data,StringBuilder outParams,StringBuilder outEncSecKey) {

        String paramJson = JSON.toJSONString(data);

        String secKey = getRandomString();
        String encText = aesEncrypt(aesEncrypt(paramJson, nonce),secKey );

        outParams.append(encText);
        outEncSecKey.append(rsaEncrypt(secKey,pubKey,modulus));
    }

    public static String getRandomString() {
        String base = "0123456789abcde";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for(int ii = 0; ii < 16; ++ii) {
            int number = random.nextInt(base.length());
            builder.append(base.charAt(number));
        }

        return builder.toString();
    }

    private static String aesEncrypt(String text, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            return Base64.encodeToString(encrypted,Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }
    }

    private static String rsaEncrypt(String text, String pubKey, String modulus) {
        text = new StringBuilder(text).reverse().toString();
        BigInteger rs = new BigInteger(String.format("%x", new BigInteger(1, text.getBytes())), 16)
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


    // 歌曲加密算法
    public static String encrypted_id(String id){
        try
        {
            byte[] magic = "3go8&$8*3*3h0k(2)2".getBytes("UTF-8");
            byte[] songId = id.getBytes("UTF-8");
            int magicLen = magic.length;
            int songIdLen = songId.length;
            for (int ii = 0;ii<songIdLen;ii++){
                songId[ii] = (byte)(songId[ii] ^ magic[ii % magicLen]);
            }
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(songId);
            byte[] resultByteArray = messageDigest.digest();
            String result = new String(Base64.encode(resultByteArray,Base64.DEFAULT));
            result.replace('/', '_');
            result.replace('+', '-');
            return result;
        }catch (Exception e){

        }

        return "";
    }

}
