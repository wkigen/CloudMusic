package com.vicky.cloudmusic;

/**
 * Created by vicky on 2017/8/30.
 */
public class Constant {

    public static final String dir_name = "CloudMusic";
    public static final String temp_dir_name = "temp";

    public static final String WYBlurHead = "http://music.163.com/api/img/blur/";

    public static final int g_Second = 1000;
    public static final int g_Minute = 60000;

    public static final int CloudType_NULL = -1;
    public static final int CloudType_WANGYI = 0;
    public static final int CloudType_QQ = 1;
    public static final int CloudType_BAIDU = 2;

    public static final int MaxHistroy = 10;
    public static final String SreachHistroy = "Sreach_histroy";
    public static final String PlayingSong = "Playing_song";

    public static final int Play_List_Loop = 0;     //列表播放
    public static final int Play_Random_Play = 1;   //随机播放
    public static final int Play_Single_Loop= 2;    //单曲循环


    public static final int NormalMusic = 0;        //普通歌曲
    public static final int FMMusic = 1;            //FM歌曲


    public static final int Status_Stop = 0;
    public static final int Status_Play = 1;
    public static final int Status_Resume = 2;
    public static final int Status_Pause = 3;


    //平台定义
    public static final String WY_ToplistHot = "3778678";//热歌榜id


}
