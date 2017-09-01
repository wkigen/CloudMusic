package com.vicky.cloudmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
import java.lang.ref.PhantomReference;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;

/**
 * Created by vicky on 2017/8/31.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;
    private MusicBean playingMusic = new MusicBean();
    private ConcurrentHashMap<String,MusicBean> downMusicMap = new ConcurrentHashMap<>();

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
            case MessageEvent.ID_REQUEST_PLAYING_INFO_MUSIC:
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REFRESH_PLAYING_INFO_MUSIC).Object1(playingMusic));
                break;
        }
    }

    private void readyPlay(int cloudType,String songId){
//        if (CacheManager.getImstance().hasMusic(cloudType,songId)){
//
//        }else {
//            switch (cloudType){
//                case Constant.CloudType_WANGYI:
//                    readPlayFormNetWY(songId);
//                    break;
//            }
//        }
        readPlayFormNetWY(songId);
    }


    private void readPlayFormNetWY(final String songId){
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

                                //图片  歌词  资源 在后面下载后再保存
                                MusicBean musicBean = new MusicBean();
                                musicBean.name = songsBean.getName();
                                musicBean.cloudType = Constant.CloudType_WANGYI;
                                musicBean.readId = songId;
                                musicBean.artist = art.toString().substring(0, art.length() - 1);

                                playingMusic.cloudType = Constant.CloudType_WANGYI;
                                playingMusic.readId = songId;
                                playingMusic.name = songsBean.getName();
                                playingMusic.picture = songsBean.getAl().getPicUrl();
                                playingMusic.artist = art.toString().substring(0, art.length() - 1);
                                playingMusic.path = songUrlBean.getData().get(0).getUrl();

                                readyPlayFormNet(musicBean);
                            }
                        }
                    });
                }
            }
        });
    }

    private void readyPlayFormNet(MusicBean downMusicBean){

        final String dirPath = CacheManager.getImstance().getDirPath();

        try{

            mediaPlayer.reset();
            mediaPlayer.setDataSource(playingMusic.path);
            mediaPlayer.prepare();
            mediaPlayer.start();

            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REFRESH_PLAYING_INFO_MUSIC).Object1(playingMusic));

            //加上网易标示
            String filName = Constant.CloudType_WANGYI+"_"+downMusicBean.artist+"-"+ downMusicBean.name+".mp3";
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
                        CacheManager.getImstance().insertMusic(finshMusic);
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REFRESH_DOWN_LIST_MUSIC));
                    }
                }
            });

            if (playingMusic.picture != null){
                String[] temp = playingMusic.picture.split("/");
                if (temp.length > 0){
                    String pictureName = Constant.CloudType_WANGYI+"_"+temp[temp.length-1];
                    
                }
            }



        }catch (Exception e) {

        }
    }

    public void play(int cloudType,String songId){
        if (songId.equals(playingMusic.readId) &&
                cloudType == playingMusic.cloudType){
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

