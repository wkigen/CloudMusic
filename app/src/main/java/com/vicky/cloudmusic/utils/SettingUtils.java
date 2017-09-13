package com.vicky.cloudmusic.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vicky on 2017/9/13.
 */
public class SettingUtils {

    private static List<String> MVBrParams = new ArrayList<>();

    private static int currMVBr;

    public static void init(){

        MVBrParams.add("240");
        MVBrParams.add("480");
        MVBrParams.add("720");
        MVBrParams.add("1080");

        currMVBr = 0;
    }

    public static String getMVUrl(Map<String,String> urls){

        urls.get(currMVBr);
        int br = currMVBr;
        String url = "";
        while (br >= 0){
            url = urls.get(MVBrParams.get(br));
            if (!TextUtils.isEmpty(url))
                break;
            br--;
        }
        return url;
    }
}
