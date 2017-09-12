package com.vicky.cloudmusic.view.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.BackHandlerHelper;
import com.vicky.android.baselib.mvvm.FragmentBackHandler;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.mvvm.base.BaseLibFragment;

/**
 * Created by vicky on 2017/4/2.
 */
public abstract class BaseFragment<T extends IView, R extends AbstractViewModel<T>> extends BaseLibFragment<T, R> implements FragmentBackHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }
}
