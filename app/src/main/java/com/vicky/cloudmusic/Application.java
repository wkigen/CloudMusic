package com.vicky.cloudmusic;

import com.vicky.android.baselib.BaseApplication;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.media.MediaSessionManager;
import com.vicky.cloudmusic.net.Net;

/**
 * Created by vicky on 2017/8/29.
 */
public class Application extends BaseApplication {

    @Override
    public void onCreate(){
        super.onCreate();

        init();
    }

    private void init(){
        Net.init();

        MediaSessionManager.getInstance().init();
    }
}
