package com.vicky.cloudmusic.view.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.viewmodel.main.MainVM;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;

import butterknife.Bind;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class MainActivity extends BaseActivity<MainActivity, MainVM> implements IView {

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


}
