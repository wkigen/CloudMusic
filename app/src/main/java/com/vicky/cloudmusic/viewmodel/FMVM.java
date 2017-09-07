package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;

import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.PlayingMusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.FMActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import org.greenrobot.eventbus.EventBus;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class FMVM extends AbstractViewModel<FMActivity> {

    public boolean isPlaying ;

    public void onBindView(@NonNull FMActivity view) {
        super.onBindView(view);
        PlayingMusicBean playingMusicBean = CacheManager.getImstance().getPlayingMusicBean();
        if (playingMusicBean.musicType != Constant.FMMusic)
            next();
    }

    public void next(){
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_FM));
    }

}
