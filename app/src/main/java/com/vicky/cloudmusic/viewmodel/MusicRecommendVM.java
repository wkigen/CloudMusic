package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.utils.ILog;
import com.vicky.cloudmusic.bean.WYPersonalizedPlaylistBean;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.MusicPlayListActivity;
import com.vicky.cloudmusic.view.fragment.MusicRecommendFragment;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import java.util.Calendar;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MusicRecommendVM extends AbstractViewModel<MusicRecommendFragment> {


    WYPersonalizedPlaylistBean personalizedPlaylistBean;

    public void onBindView(@NonNull MusicRecommendFragment view) {
        super.onBindView(view);
        recommend();
    }

    public void recommend(){
        Net.getWyApi().getApi().personalizedPlaylist().execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                personalizedPlaylistBean = JSON.parseObject(result, WYPersonalizedPlaylistBean.class);
                if (personalizedPlaylistBean != null) {
                    if (getView() != null)
                        getView().setPersonalizedPlaylist(personalizedPlaylistBean);
                }
            }
        });

        Net.getWyApi().getApi().privateContent().execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                Log.e("fdff",result);
            }
        });
    }

    public String getPlayListId(int position){
        if (position < 0 || position > personalizedPlaylistBean.getResult().size())
            return "";
        return personalizedPlaylistBean.getResult().get(position).getId();
    }

    public int getDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
