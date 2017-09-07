package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.viewmodel.MusicRecommendVM;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:   vicky
 * Description:
 * Date:
 */
public class MusicRecommendFragment extends BaseFragment<MusicRecommendFragment, MusicRecommendVM> {

    @Bind(R.id.iv_fm)
    ImageView ivFm;
    @Bind(R.id.iv_day)
    ImageView ivDay;
    @Bind(R.id.tv_day)
    TextView tvDay;
    @Bind(R.id.iv_hot)
    ImageView ivHot;
    @Bind(R.id.ll_recommend_list)
    LinearLayout llRecommendList;

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_musicrecommend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Nullable
    @Override
    public Class<MusicRecommendVM> getViewModelClass() {
        return MusicRecommendVM.class;
    }



}
