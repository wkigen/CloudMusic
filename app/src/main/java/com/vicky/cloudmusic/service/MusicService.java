package com.vicky.cloudmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.http.callback.FileCallBack;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.MusicBean;
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
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import okhttp3.Call;

/**
 * Created by vicky on 2017/8/31.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;

    private volatile MusicBean playingMusic = new MusicBean();
    private ConcurrentHashMap<String,MusicBean> downMusicMap = new ConcurrentHashMap<>();

    private int LoopType = Constant.Play_List_Loop;

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
        mediaPlayer.reset();
        EventBus.getDefault().register(this);

        List<MusicBean> musicBeanList = CacheManager.getImstance().getDownMusicList();
        CacheManager.getImstance().getPlayMusicList();
        if (musicBeanList.size() > 0){
            readyPlay(musicBeanList.get(0).cloudType, musicBeanList.get(0).readId, false);
        }

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

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(final MessageEvent event) {
        synchronized (MusicService.class){
            switch (event.what){
                case MessageEvent.ID_REQUEST_PLAY_MUSIC:
                    play((int) event.object1, (String) event.object2, (boolean) event.object3);
                    break;
                case MessageEvent.ID_REQUEST_PLAY_PAUSE_MUSIC:
                    if (mediaPlayer.isPlaying())
                        pause();
                    else
                        play(Constant.CloudType_NULL,null,true);
                    break;
                case MessageEvent.ID_REQUEST_PLAYING_INFO_MUSIC:
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC)
                            .Object1(playingMusic).Object2(mediaPlayer.isPlaying()).Object3(mediaPlayer.getDuration()));
                    break;
                case MessageEvent.ID_REQUEST_PAUSE_MUSIC:
                    pause();
                    break;
                case MessageEvent.ID_REQUEST_STOP_MUSIC:
                    stop();
                    break;
                case MessageEvent.ID_REQUEST_NEXT_MUSIC:
                    next();
                    break;
                case MessageEvent.ID_REQUEST_PRE_MUSIC:
                    pre();
                    break;
                case MessageEvent.Id_REQUEST_PLAYING_PROGRESS_MUSIC:
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Id_RESPONSE_PLAYING_PROGRESS_MUSIC).Object1(mediaPlayer.getCurrentPosition()).Object2(mediaPlayer.getDuration()));
                    break;
                case MessageEvent.Id_REQUEST_SEEK_PROGRESS_MUSIC:
                    seek((float)event.object1);
                    break;
            }
        }
    }

    public synchronized void play(int cloudType,String songId,boolean play){
        if ( cloudType == Constant.CloudType_NULL
                ||( cloudType == playingMusic.cloudType && songId.equals(playingMusic.readId))){
            if (!mediaPlayer.isPlaying()){
                mediaPlayerPlay(null, true);
            }
        }else {
            readyPlay(cloudType,songId,play);
        }
    }

    public synchronized void seek(float progress){
        int total = mediaPlayer.getDuration();
        float millsecond = total * progress;
        mediaPlayer.seekTo((int)millsecond);
    }

    public synchronized void pause(){
        mediaPlayer.pause();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object2(mediaPlayer.isPlaying()));
    }

    public synchronized void stop(){
        mediaPlayer.stop();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object2(mediaPlayer.isPlaying()));
    }

    public synchronized  void next(){
        MusicBean musicBean = CacheManager.getImstance().getNextDownMusic(playingMusic.cloudType,playingMusic.readId,LoopType);
        if (musicBean != null){
            try{
                playingMusic = (MusicBean)musicBean.clone();
                mediaPlayerPlay(musicBean,true);
            }catch (Exception E){}
        }else {
            stop();
        }
    }

    private void pre(){
        MusicBean musicBean = CacheManager.getImstance().getPreDownMusic(playingMusic.cloudType, playingMusic.readId, LoopType);
        if (musicBean != null){
            try{
                playingMusic = (MusicBean)musicBean.clone();
                mediaPlayerPlay(musicBean,true);
            }catch (Exception E){}
        }else {
            stop();
        }
    }

    private void mediaPlayerPlay(MusicBean musicBean,boolean play){
        try{
            if (musicBean != null){
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musicBean.path);
                mediaPlayer.prepare();
            }
            if (play)
                mediaPlayer.start();
            if (musicBean != null)
                CacheManager.getImstance().selectPlayMusicList(musicBean.cloudType,musicBean.readId);
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC)
                    .Object1(playingMusic).Object2(mediaPlayer.isPlaying()).Object3(mediaPlayer.getDuration()));
        }catch (Exception e){
        }
    }

    private void readyPlay(int cloudType,String songId,boolean play){
        if (CacheManager.getImstance().hasDownMusic(cloudType,songId)){
            readPlayFormLocal(cloudType,songId,play);
        }else {
            switch (cloudType){
                case Constant.CloudType_WANGYI:
                    readPlayFormNetWY(songId,play);
                    break;
            }
        }
    }

    private void readPlayFormLocal(int cloudType,String songId,boolean play){
        try
        {
            MusicBean musicBean = CacheManager.getImstance().getDownMusic(cloudType,songId);
            if (musicBean != null){
                playingMusic = (MusicBean)musicBean.clone();
                mediaPlayerPlay(playingMusic, play);
            }else {
                stop();
            }
        }catch (Exception e){
        }
    }

    private void readPlayFormNetWY(final String songId,final boolean play){
        Net.getWyApi().getApi().detail("[{\"id\":\""+songId+"\"}]").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                final WYSongDetailBean wySongDetailBean = JSON.parseObject(result, WYSongDetailBean.class);

                if (wySongDetailBean.getSongs().size() > 0){
                    Net.getWyApi().getApi().songRes("[" + songId + "]", "999000", "").execute(new WYCallback() {
                        @Override
                        public void onRequestSuccess(String result) {
                            WYSongUrlBean songUrlBean = JSON.parseObject(result, WYSongUrlBean.class);
                            if ( songUrlBean.getData().size() > 0){
                                WYSongDetailBean.SongsBean songsBean = wySongDetailBean.getSongs().get(0);

                                StringBuilder art = new StringBuilder();
                                for (WYSongDetailBean.SongsBean.ArBean arBean : songsBean.getAr()) {
                                    art.append(arBean.getName() + " ");
                                }

                                MusicBean musicBean = new MusicBean();
                                musicBean.name = songsBean.getName();
                                musicBean.cloudType = Constant.CloudType_WANGYI;
                                musicBean.readId = songId;
                                musicBean.artist = art.toString().substring(0, art.length() - 1);
                                musicBean.album = songsBean.getAl().getName();

                                playingMusic.cloudType = Constant.CloudType_WANGYI;
                                playingMusic.readId = songId;
                                playingMusic.name = songsBean.getName();
                                playingMusic.picture = songsBean.getAl().getPicUrl();
                                playingMusic.album= songsBean.getAl().getName();
                                playingMusic.artist = art.toString().substring(0, art.length() - 1);
                                playingMusic.path = songUrlBean.getData().get(0).getUrl();

                                readyPlayFormNet(musicBean,play);
                            }
                        }
                    });
                }
            }
        });
    }

    private void readyPlayFormNet(final MusicBean downMusicBean,final boolean play){

        final String dirPath = CacheManager.getImstance().getDirPath();

        try{

            mediaPlayer.pause();

            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC)
                    .Object1(playingMusic).Object2(mediaPlayer.isPlaying()));

            //加上网易标示
            final String filName = Constant.CloudType_WANGYI+"_"+downMusicBean.artist+"-"+ downMusicBean.name+".mp3";
            String[] temp = playingMusic.picture.split("/");
            String pictureName = Constant.CloudType_WANGYI+"_"+temp[temp.length-1];
            downMusicBean.picture =  dirPath+"/"+pictureName;
            downMusicBean.path = dirPath+"/"+filName;

            downMusicMap.put(downMusicBean.path, downMusicBean);

            Net.getWyApi().getApi().downFile(playingMusic.path).execute(new FileCallBack(dirPath+"/",filName) {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(File file, int i) {
                    String path = file.getAbsolutePath();
                    MusicBean finshMusic = downMusicMap.get(path);
                    if (finshMusic != null){
                        CacheManager.getImstance().insertDownMusic(finshMusic);
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_DOWN_LIST_MUSIC));
                    }
                    //要播放的歌曲已经下载完了
                    if (finshMusic.cloudType == playingMusic.cloudType &&
                            finshMusic.readId.equals(playingMusic.readId)) {
                        playingMusic.path = finshMusic.path;
                        readPlayFormLocal(playingMusic.cloudType, playingMusic.readId, play);
                    }

                    downMusicMap.remove(path);
                }

                @Override
                public void inProgress(float progress, long total, int id){
                    Log.e("inProgress", filName + " " + progress + " " + total);
                    if (downMusicBean.cloudType == playingMusic.cloudType &&
                            downMusicBean.readId.equals(playingMusic.readId)) {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_DOWN_PROGRESS_MUSIC).Object1(filName).Object2(progress).Object3(total));
                    }
                }
            });

            Net.getWyApi().getApi().downFile(playingMusic.picture).execute(new FileCallBack(dirPath+"/",pictureName) {
                @Override
                public void onError(Call call, Exception e, int i) {
                }

                @Override
                public void onResponse(File file, int i) {
                    if (downMusicBean.cloudType == playingMusic.cloudType &&
                            downMusicBean.readId.equals(playingMusic.readId)){
                        playingMusic.picture = file.getAbsolutePath();
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(playingMusic).Object2(mediaPlayer.isPlaying()));
                    }
                }
            });

        }catch (Exception e) {

        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

}

