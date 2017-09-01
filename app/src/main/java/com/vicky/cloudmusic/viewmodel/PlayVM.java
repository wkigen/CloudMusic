package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;

import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.PlayActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import org.greenrobot.eventbus.EventBus;


/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class PlayVM extends AbstractViewModel<PlayActivity> {

    @Override
    public void onBindView(@NonNull PlayActivity view) {
        super.onBindView(view);

    }


    public void play(){

    }

}
