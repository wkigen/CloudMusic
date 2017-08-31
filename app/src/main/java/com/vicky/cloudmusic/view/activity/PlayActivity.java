package com.vicky.cloudmusic.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.viewmodel.PlayVM;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class PlayActivity extends BaseActivity<PlayActivity, PlayVM> implements IView,View.OnClickListener {


    @Override
    protected int tellMeLayout() {
        return R.layout.activity_play;
    }

    @Override
    public Class<PlayVM> getViewModelClass() {
        return PlayVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @OnClick({R.id.iv_back,R.id.im_play})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.im_play:
                getViewModel().play();
                break;
        }
    }
}
