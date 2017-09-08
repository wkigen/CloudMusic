package com.vicky.cloudmusic.net;

import com.vicky.android.baselib.http.annotation.FILE;
import com.vicky.android.baselib.http.annotation.GET;
import com.vicky.android.baselib.http.annotation.HEADPARAMS;
import com.vicky.android.baselib.http.annotation.PARAMS;
import com.vicky.android.baselib.http.annotation.POST;
import com.vicky.android.baselib.http.annotation.POSTENCRYPT;
import com.vicky.android.baselib.http.annotation.POSTJSON;
import com.vicky.android.baselib.http.request.RequestCall;

import java.util.List;

/**
 * Created by vicky on 2017/8/29.
 */
public interface IWYApi {

    @POSTENCRYPT("http://music.163.com/weapi/cloudsearch/get/web")
    RequestCall sreach(@PARAMS("s")String s,@PARAMS("limit")String limit,@PARAMS("offset")String offset,@PARAMS("total")String total,@PARAMS("type")String type);

    @POSTENCRYPT("http://music.163.com/weapi/v3/song/detail")
    RequestCall detail(@PARAMS("c")String c);

    @POSTENCRYPT("http://music.163.com/weapi/song/enhance/player/url?csrf_token=")
    RequestCall songRes(@PARAMS("ids")String ids,@PARAMS("br")String br,@PARAMS("csrf_token")String csrf_token);

    @GET("http://music.163.com/api/song/lyric")
    RequestCall lyric(@PARAMS("os")String os,@PARAMS("id")String id,@PARAMS("lv")String lv,@PARAMS("kv")String kv,@PARAMS("tv")String tv);

    //重定向了？
    @POSTENCRYPT("http://music.163.com/weapi/v1/discovery/recommend/songs")
    RequestCall recommendSongs(@PARAMS("offset")String offset,@PARAMS("total")String total,@PARAMS("limit")String limit,@PARAMS("csrf_token")String csrf_token);

    //节目推荐
    @POSTENCRYPT("http://music.163.com/weapi/program/recommend/v1")
    RequestCall recommend(@PARAMS("cateId")String cateId);

    //个人歌单推荐
    @POSTENCRYPT("http://music.163.com/weapi/personalized/playlist")
    RequestCall personalizedPlaylist();

    //热门歌单
    @POSTENCRYPT("http://music.163.com/weapi/playlist/hottags")
    RequestCall hottagsPlaylist();

    //排行榜详情
    @POSTENCRYPT("http://music.163.com/weapi/toplist/detail")
    RequestCall toplistDetail(@PARAMS("id")String id,@PARAMS("limit")String limit,@PARAMS("csrf_token")String csrf_token);

    //全部歌单
    @POSTENCRYPT("http://music.163.com/weapi/playlist/catalogue")
    RequestCall cataloguePlaylist();

    @POSTENCRYPT("http://music.163.com/weapi/v3/playlist/detail")
    RequestCall detailPlaylist(@PARAMS("id")String id,@PARAMS("offset")String offset,@PARAMS("total")String total,@PARAMS("limit")String limit,@PARAMS("n")String n,@PARAMS("csrf_token")String csrf_token);

    //独家内容
    @POSTENCRYPT("http://music.163.com/weapi/personalized/privatecontent")
    RequestCall privateContent();

    @GET("http://music.163.com/api/radio/get")
    RequestCall radio();

    @FILE("")
    RequestCall downFile(@PARAMS("url")String url);

    @FILE("")
    RequestCall downFile(@HEADPARAMS("Range") String Range,@PARAMS("url")String url);

}
