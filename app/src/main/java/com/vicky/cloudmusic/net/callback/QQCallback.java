package com.vicky.cloudmusic.net.callback;

import com.vicky.android.baselib.http.callback.StringCallback;
import com.vicky.android.baselib.utils.ILog;

import okhttp3.Call;


/**
 * Created by vicky on 2017/8/30.
 */
public abstract class QQCallback extends StringCallback {

    private static final String TAG = QQCallback.class.getSimpleName();

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
