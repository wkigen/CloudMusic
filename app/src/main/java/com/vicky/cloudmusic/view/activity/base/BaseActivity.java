package com.vicky.cloudmusic.view.activity.base;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.mvvm.base.BaseLibActivity;

/**
 * Created by vicky on 2017/4/2.
 */
public abstract class BaseActivity<T extends IView, RM extends AbstractViewModel<T>> extends BaseLibActivity<T, RM> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
