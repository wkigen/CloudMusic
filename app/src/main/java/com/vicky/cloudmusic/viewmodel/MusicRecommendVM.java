package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.vicky.android.baselib.utils.ILog;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.fragment.MusicRecommendFragment;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MusicRecommendVM extends AbstractViewModel<MusicRecommendFragment> {

    public void onBindView(@NonNull MusicRecommendFragment view) {
        super.onBindView(view);

        recommend();
    }

    public void recommend(){

        Net.getWyApi().getApi().recommend("0","true","10","").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                ILog.e("fsdfsdf",result);
            }
        });

    }

}
