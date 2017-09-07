package com.vicky.cloudmusic.bean;

import com.vicky.cloudmusic.Constant;

/**
 * Created by vicky on 2017/9/7.
 */

//正在播放的音乐
public class PlayingMusicBean {

    public MusicBean musicBean;
    public int musicType = Constant.NormalMusic;

    public PlayingMusicBean(){
        musicBean = new MusicBean();
    }

}
