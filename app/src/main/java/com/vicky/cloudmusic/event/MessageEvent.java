package com.vicky.cloudmusic.event;

import com.vicky.android.baselib.BaseApplication;
import com.vicky.android.baselib.http.callback.ObjectCallback;
import com.vicky.cloudmusic.net.Net;

/**
 * Created by vicky on 2017/8/29.
 */
public class MessageEvent {

    public static final int ID_NULL = 0;

    public static final int ID_REQUEST_PLAY_MUSIC = 1;      //请求播放音乐
    public static final int ID_RESPONSE_PLAY_MUSIC = 2;     //回应播放音乐

    public static final int ID_REQUEST_PAUSE_MUSIC = 3;     //请求暂停音乐
    public static final int ID_RESPONSE_PAUSE_MUSIC = 4;     //回应暂停音乐

    public static final int ID_REQUEST_STOP_MUSIC = 5;       //请求停止音乐
    public static final int ID_RESPONSE_STOP_MUSIC = 6;     //回应停止音乐

    public static final int ID_RESPONSE_DOWN_LIST_MUSIC = 11;    //请求下载列表状态

    public static final int ID_REQUEST_PLAYING_INFO_MUSIC = 12; //请求当前音乐状态
    public static final int ID_RESPONSE_PLAYING_INFO_MUSIC = 13; //回应当前音乐状态



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
