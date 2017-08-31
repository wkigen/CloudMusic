package com.vicky.cloudmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.http.callback.FileCallBack;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.SongPlayBean;
import com.vicky.cloudmusic.bean.WYSongDetailBean;
import com.vicky.cloudmusic.bean.WYSongUrlBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import okhttp3.Call;

/**
 * Created by vicky on 2017/8/31.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;
    private SongPlayBean playingSong = new SongPlayBean();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what){
            case MessageEvent.ID_PLAY_MUSIC:
                play((int) event.object1,(String)event.object2);
                break;
            case MessageEvent.ID_PAUSE_MUSIC:
                pause();
                break;
            case MessageEvent.ID_STOP_MUSIC:
                stop();
                break;
        }
    }

    private void readyPlay(int cloudType,String songId){
        switch (cloudType){
            case Constant.CloudType_WANGYI:
                readPlayFormNetWY(songId);
                break;
        }
    }

    private void readPlayFormNetWY(final String songId){
        Net.getWyApi().getApi().detail("[{\"id\":\""+songId+"\"}]").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                final WYSongDetailBean wySongDetailBean = JSON.parseObject(result, WYSongDetailBean.class);

                Net.getWyApi().getApi().songRes("[" + songId + "]", "999000", "").execute(new WYCallback() {
                    @Override
                    public void onRequestSuccess(String result) {
                        WYSongUrlBean songUrlBean = JSON.parseObject(result,WYSongUrlBean.class);

                        playingSong.name = wySongDetailBean.getSongs().get(0).getName();
                        playingSong.pic = wySongDetailBean.getSongs().get(0).getAl().getPicUrl();
                        StringBuilder art = new StringBuilder();
                        for (WYSongDetailBean.SongsBean.ArBean arBean : wySongDetailBean.getSongs().get(0).getAr()) {
                            art.append(arBean.getName() + " ");
                        }
                        playingSong.artist = art.toString();
                        playingSong.id = songId;
                        playingSong.url = songUrlBean.getData().get(0).getUrl();

                        readyPlayFormNet();
                    }
                });
            }
        });
    }

    private void readyPlayFormNet(){

        final String dirPath = CacheManager.getImstance().getDirPath();
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(playingSong.url);
            mediaPlayer.prepare();
            mediaPlayer.start();

            Net.getWyApi().getApi().downFile(playingSong.url).execute(new FileCallBack(dirPath+"/",playingSong.name+".mp3") {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(File file, int i) {
                }
            });

        }catch (Exception e) {

        }
    }

    public void play(int cloudType,String songId){
        if (songId.equals(playingSong.id) &&
                cloudType == playingSong.cloudType){
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();
        }else {
            readyPlay(cloudType,songId);
        }
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

}

