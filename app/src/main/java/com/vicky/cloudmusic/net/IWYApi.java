package com.vicky.cloudmusic.net;

import com.vicky.android.baselib.http.annotation.FILE;
import com.vicky.android.baselib.http.annotation.GET;
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

    @FILE("")
    RequestCall downFile(@PARAMS("url")String url);


}
