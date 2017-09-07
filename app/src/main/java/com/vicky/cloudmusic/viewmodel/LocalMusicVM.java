package com.vicky.cloudmusic.viewmodel;

import com.vicky.cloudmusic.bean.PlayMusicStausBean;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.LocalMusicActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.cloudmusic.view.activity.PlayActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class LocalMusicVM extends AbstractViewModel<LocalMusicActivity> {

    public void playMusic(int position){
        if (position >= 0 && position < CacheManager.getImstance().getPlayMusicList().size()){
            PlayMusicStausBean playMusicStausBean = CacheManager.getImstance().getPlayMusicList().get(position);
            if (!playMusicStausBean.isSelect)
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_MUSIC).Object1(playMusicStausBean.musicBean.cloudType).Object2(playMusicStausBean.musicBean.readId).Object3(true));
            else{
                if (getView() != null)
                    getView().goPlayActivity();
            }
        }
    }
}
