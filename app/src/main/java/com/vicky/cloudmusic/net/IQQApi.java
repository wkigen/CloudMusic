package com.vicky.cloudmusic.net;

import com.vicky.android.baselib.http.annotation.FILE;
import com.vicky.android.baselib.http.annotation.GET;
import com.vicky.android.baselib.http.annotation.HEADPARAMS;
import com.vicky.android.baselib.http.annotation.PARAMS;
import com.vicky.android.baselib.http.annotation.POSTENCRYPT;
import com.vicky.android.baselib.http.request.RequestCall;

/**
 * Created by vicky on 2017/8/29.
 */
public interface IQQApi {

    // n = 查询数量  p = 第几页  n= 关键字 loginUin= QQ号
    @GET("http://s.music.qq.com/fcgi-bin/music_search_new_platform")
    RequestCall sreach(@PARAMS("n")String num,@PARAMS("w")String name,@PARAMS("t")String t,@PARAMS("aggr")String aggr,@PARAMS("cr")String cr,
                       @PARAMS("loginUin")String loginUin,@PARAMS("format")String format,@PARAMS("inCharset")String inCharset,@PARAMS("outCharset")String outCharset,
                       @PARAMS("notice")String notice,@PARAMS("platform")String platform,@PARAMS("needNewCode")String needNewCode,@PARAMS("p")String p,
                       @PARAMS("catZhida")String catZhida,@PARAMS("remoteplace")String remoteplace);


    @GET("http://c.y.qq.com/v8/fcg-bin/fcg_play_single_song.fcg")
    RequestCall detailSong(@PARAMS("songmid")String songmid,@PARAMS("format") String format);


    @GET("http://music.163.com/api/song/lyric")
    RequestCall lyric(@PARAMS("os")String os,@PARAMS("id")String id,@PARAMS("lv")String lv,@PARAMS("kv")String kv,@PARAMS("tv")String tv);
}
