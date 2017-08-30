package com.vicky.cloudmusic.view.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;

import com.vicky.android.baselib.mvvm.IView;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.viewmodel.main.MainVM;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class MainActivity extends BaseActivity<MainActivity, MainVM> implements IView, View.OnClickListener  {

    @Bind(R.id.rl_sreach)
    RelativeLayout rlSreach;
    @Bind(R.id.rl_sreach_toolbar)
    RelativeLayout rlSreachToolbar;

    @Override
    protected int tellMeLayout() {
        return R.layout.main_activity_main;
    }

    @Override
    public Class<MainVM> getViewModelClass() {
        return MainVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.rl_sreach})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_sreach:
                readyGoForTransitionAnimation(SreachActivity.class);
                break;
        }
    }
}
