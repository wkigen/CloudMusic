package com.vicky.cloudmusic.viewmodel.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vicky.android.baselib.http.callback.NetCallback;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.activity.main.MainActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import okhttp3.Call;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MainVM extends AbstractViewModel<MainActivity> {

    @Override
    public void onBindView(@NonNull MainActivity view) {
        super.onBindView(view);


        Net.getWyApi().getApi().sreach("我",0,0,"true","",1).execute(new NetCallback(getView()) {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("fff","fff");
            }

            @Override
            public void onResponse(String s, int i) {
                Log.e("fff",s);
            }
        });
    }

}
