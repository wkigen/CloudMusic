package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.http.OkHttpUtils;
import com.vicky.android.baselib.http.callback.FileCallBack;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.WYSongDetailBean;
import com.vicky.cloudmusic.bean.WYSongUrlBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.PlayActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import java.io.File;

import okhttp3.Call;


/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class PlayVM extends AbstractViewModel<PlayActivity> {

    public int cloudType=0;
    public String id="";

    @Override
    public void onBindView(@NonNull PlayActivity view) {
        super.onBindView(view);
        detail();
    }


    public void detail(){
        switch (cloudType){
            case Constant.CloudType_WANGYI:
                wyDetail();
                break;
        }
    }

    private void wyDetail(){

        Net.getWyApi().getApi().detail("[{\"id\":\""+id+"\"}]").showProgress(getView()).execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                WYSongDetailBean songDetailBean = JSON.parseObject(result, WYSongDetailBean.class);


            }
        });

        Net.getWyApi().getApi().songRes("[" + id + "]", "999000", "").showProgress(getView()).execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                WYSongUrlBean songUrlBean = JSON.parseObject(result,WYSongUrlBean.class);

                String dirPath = CacheManager.getImstance().getDirPath();
                File file =new File(dirPath+"/1.mp3");
                try {
                    file.createNewFile();
                }catch (Exception e){

                }
                OkHttpUtils.getInstance().get().url(songUrlBean.getData().get(0).getUrl()).build().execute(new FileCallBack(dirPath+"/","1.mp3") {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Log.e("ff","fff");
                    }

                    @Override
                    public void onResponse(File file, int i) {
                        Log.e("ff","fff");
                    }
                });
            }
        });

    }

}
