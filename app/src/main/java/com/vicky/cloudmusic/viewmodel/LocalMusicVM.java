package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;

import com.vicky.cloudmusic.bean.LocalMusicBean;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.LocalMusicActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
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

    private List<LocalMusicBean> localMusicBeanList = new LinkedList<>();

    public List<LocalMusicBean> getLocalMusic(){
        localMusicBeanList.clear();
        List<MusicBean> musicBeenList = CacheManager.getImstance().getDownMusicList();
        for (MusicBean musicBean : musicBeenList){
            LocalMusicBean localMusicBean = new LocalMusicBean();
            localMusicBean.musicBean = musicBean;
            localMusicBean.isPlaying = false;
            localMusicBeanList.add(localMusicBean);
        }
        return localMusicBeanList;
    }

    public void playMusic(int position){
        if (position >= 0 && position < localMusicBeanList.size()){
            LocalMusicBean localMusicBean = localMusicBeanList.get(position);
            if (!localMusicBean.isPlaying)
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_MUSIC).Object1(localMusicBean.musicBean.cloudType).Object2(localMusicBean.musicBean.readId));
            else{
                if (getView() != null)
                    getView().readyGo(PlayActivity.class);
            }
        }
    }

    public void setPlayIco(int cloudType,String id){
        for (LocalMusicBean localMusicBean : localMusicBeanList){
            if (localMusicBean.musicBean.cloudType == cloudType &&
                    localMusicBean.musicBean.readId.equals(id)){
                localMusicBean.isPlaying = true;
            }else {
                localMusicBean.isPlaying = false;
            }
        }
        if (getView() != null )
            getView().refresh();

    }
}
