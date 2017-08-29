package com.vicky.cloudmusic.view;

import com.vicky.android.baselib.BaseApplication;
import com.vicky.cloudmusic.net.Net;

/**
 * Created by vicky on 2017/8/29.
 */
public class Application extends BaseApplication {

    @Override
    public void onCreate(){
        super.onCreate();

        Net.init();
    }
}
