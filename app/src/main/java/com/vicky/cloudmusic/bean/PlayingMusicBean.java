package com.vicky.cloudmusic.bean;

/**
 * Created by vicky on 2017/9/7.
 */

//正在播放的音乐
public class PlayingMusicBean {

    public static final int NormalMusic = 0;
    public static final int FMMusic = 1;

    public MusicBean musicBean;
    public int musicType = NormalMusic;

    public PlayingMusicBean(){
        musicBean = new MusicBean();
    }

}
