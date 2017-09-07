package com.vicky.cloudmusic.event;

import com.vicky.android.baselib.BaseApplication;
import com.vicky.android.baselib.http.callback.ObjectCallback;
import com.vicky.cloudmusic.net.Net;

/**
 * Created by vicky on 2017/8/29.
 */
public class MessageEvent {

    /*消息类型定义*/
    public static final int ID_NULL = 0;

    //无参数
    public static final int ID_REQUEST_PLAY_MUSIC = 1;      //请求播放音乐
    public static final int ID_RESPONSE_PLAY_MUSIC = 2;     //回应播放音乐

    //无参数
    public static final int ID_REQUEST_PAUSE_MUSIC = 3;     //请求暂停音乐
    public static final int ID_RESPONSE_PAUSE_MUSIC = 4;     //回应暂停音乐

    //无参数
    public static final int ID_REQUEST_STOP_MUSIC = 5;       //请求停止音乐
    public static final int ID_RESPONSE_STOP_MUSIC = 6;     //回应停止音乐

    //无参数
    public static final int ID_REQUEST_PLAY_OR_PAUSE_MUSIC = 7;  //请求当播放的时候暂停音乐 暂停的时候播放音乐

    //无参数
    public static final int ID_REQUEST_NEXT_MUSIC = 8;  //请求当播放下一首

    //无参数
    public static final int ID_REQUEST_PRE_MUSIC = 10;  //请求当播放上一首

    //无参数
    public static final int ID_RESPONSE_DOWN_LIST_MUSIC = 21;    //回应下载列表状态

    //无参数
    public static final int ID_REQUEST_PLAYING_INFO_MUSIC = 22; //请求当前音乐状态
    //1.是否刷新 2.是否在播放 3.音乐长度
    public static final int ID_RESPONSE_PLAYING_INFO_MUSIC = 23; //回应当前音乐状态

    //无参数
    public static final int ID_REQUEST_PLAYING_PROGRESS_MUSIC = 24; //请求当前音乐进度

    //1.当前进度  2.总长度
    public static final int ID_RESPONSE_PLAYING_PROGRESS_MUSIC = 25; //回应当前音乐进度

    //1.请求类型  //进度位置类型
    public static final int ID_REQUEST_SEEK_PROGRESS_MUSIC = 26; //请求音乐进度位置

    //1.文件名  2.进度  3.总长度
    public static final int ID_RESPONSE_DOWN_PROGRESS_MUSIC = 41;//回应音乐下载状态



    /*参数定义*/
    //进度位置类型
    public static final int ID_REQUEST_SEEK_PROGRESS_MUSIC_TIME = 261; //直接时间
    public static final int ID_REQUEST_SEEK_PROGRESS_MUSIC_PERCENTAGE = 262; //百分比




    public int what;
    public Object object1;
    public Object object2;
    public Object object3;

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

    public MessageEvent Object3(Object object){
        this.object3 = object;
        return this;
    }
}
