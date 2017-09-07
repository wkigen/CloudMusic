package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;

import com.vicky.cloudmusic.view.activity.FMActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class FMVM extends AbstractViewModel<FMActivity> {


    public void onBindView(@NonNull FMActivity view) {
        super.onBindView(view);
        next();
    }

    public void next(){

    }

}
