package com.vicky.cloudmusic.net;

import com.vicky.android.baselib.net.NetBase;

/**
 * Created by vicky on 2017/8/29.
 */
public class Net {

    private static WYApi wyApi = new WYApi();

    public static void init(){
        NetBase.init();
    }

    public static WYApi getWyApi(){
        return wyApi;
    }

}
