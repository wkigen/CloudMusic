package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;


import com.vicky.cloudmusic.view.fragment.MusicRecommendFragment;
import com.vicky.cloudmusic.view.fragment.RecommendFragment;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.cloudmusic.view.fragment.VideoRecommendFragment;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class RecommendVM extends AbstractViewModel<RecommendFragment> {

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void onBindView(@NonNull RecommendFragment view) {
        super.onBindView(view);
        init();
    }

    private void init(){
        fragmentList.add(new MusicRecommendFragment());
        fragmentList.add(new VideoRecommendFragment());
        if (getView() != null)
            getView().setFragments(fragmentList);
    }

}
