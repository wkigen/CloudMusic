package com.vicky.cloudmusic.net;

import com.vicky.android.baselib.http.annotation.GET;
import com.vicky.android.baselib.http.annotation.PARAMS;
import com.vicky.android.baselib.http.annotation.POST;
import com.vicky.android.baselib.http.annotation.POSTJSON;
import com.vicky.android.baselib.http.request.RequestCall;

import java.util.List;

/**
 * Created by vicky on 2017/8/29.
 */
public interface IWYApi {

    @POST("http://music.163.com/api/search/get")
    RequestCall sreach(@PARAMS("s")String s,@PARAMS("limit")int limit,@PARAMS("offset")int offset,@PARAMS("total")String total,@PARAMS("type")int type);


}
