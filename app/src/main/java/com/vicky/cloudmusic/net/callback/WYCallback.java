package com.vicky.cloudmusic.net.callback;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.http.callback.StringCallback;
import com.vicky.android.baselib.utils.ILog;

import okhttp3.Call;


/**
 * Created by vicky on 2017/8/30.
 */
public abstract class WYCallback extends StringCallback {

    private static final String TAG = WYCallback.class.getSimpleName();

    @Override
    public void onResponse(String s, int i) {
        onRequestSuccess(s);
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        onRequestError(e.getMessage());
    }

    public abstract void onRequestSuccess(String result);

    public void onRequestError(String msg){
        ILog.e(TAG,msg);
    }
}
