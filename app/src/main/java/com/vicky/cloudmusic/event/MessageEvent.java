package com.vicky.cloudmusic.event;

import com.vicky.android.baselib.BaseApplication;
import com.vicky.android.baselib.http.callback.ObjectCallback;
import com.vicky.cloudmusic.net.Net;

/**
 * Created by vicky on 2017/8/29.
 */
public class MessageEvent {

    public static final int ID_NULL = 0;
    public static final int ID_READY_MUSIC = 1;
    public static final int ID_PLAY_MUSIC = 2;
    public static final int ID_PAUSE_MUSIC = 3;
    public static final int ID_STOP_MUSIC = 4;
    public static final int ID_REQUEST_STATUS_MUSIC = 5;
    public static final int ID_RESPONSE_STATUS_MUSIC = 6;

    public int what;
    public Object object1;
    public Object object2;

    public MessageEvent(int what){
        this.what = what;
    }

    public MessageEvent Object1(Object object){
        this.object1 = object;
        return this;
    }

    public MessageEvent Object2(Object object){
        this.object2 = object;
        return this;
    }
}
