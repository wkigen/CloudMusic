package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.MainActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.cloudmusic.view.fragment.MusicListFragment;
import com.vicky.cloudmusic.view.fragment.MusicRecommendFragment;

import java.util.ArrayList;
import java.util.List;


/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MainVM extends AbstractViewModel<MainActivity> {

    List<Fragment> fragmentList = new ArrayList<>();
    @Override
    public void onBindView(@NonNull MainActivity view) {
        super.onBindView(view);
        init();

        Net.getWyApi().getApi().toplistDetail("3778678","20","").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                Log.e("dfdf",result);
            }
        });
    }

    private void init(){
        fragmentList.add(new MusicListFragment());
        fragmentList.add(new MusicRecommendFragment());
        if (getView() != null)
            getView().setFragments(fragmentList);
    }

}
