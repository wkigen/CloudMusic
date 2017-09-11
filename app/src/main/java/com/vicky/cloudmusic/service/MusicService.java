package com.vicky.cloudmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.http.OkHttpUtils;
import com.vicky.android.baselib.http.callback.FileCallBack;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.bean.PlayingMusicBean;
import com.vicky.cloudmusic.bean.QQMusicDetailBean;
import com.vicky.cloudmusic.bean.WYFMBean;
import com.vicky.cloudmusic.bean.WYLyricBean;
import com.vicky.cloudmusic.bean.WYSongDetailBean;
import com.vicky.cloudmusic.bean.WYSongUrlBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.QQCallback;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.proxy.MediaPlayerProxy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by vicky on 2017/8/31.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener,AudioManager.OnAudioFocusChangeListener{

    private static final int Sound_Coefficient = 3;

    private volatile MediaPlayer mediaPlayer;
    private MediaPlayerProxy mediaPlayerProxy;
    private AudioManager audioManager;

    private volatile PlayingMusicBean playingMusic;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private int LoopType = Constant.Play_List_Loop;
    private int lossVolume;
    private boolean lossPlayStatus;

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

        playingMusic = CacheManager.getImstance().getPlayingMusicBean();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        mediaPlayerProxy = new MediaPlayerProxy();
        mediaPlayerProxy.start();

        List<MusicBean> musicBeanList = CacheManager.getImstance().getDownMusicList();
        CacheManager.getImstance().getPlayMusicList();
        if (musicBeanList.size() > 0){
            readyPlay(musicBeanList.get(0).cloudType, musicBeanList.get(0).readId, false);
        }

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        audioManager.abandonAudioFocus(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(final MessageEvent event) {
        synchronized (MusicService.class){
            switch (event.what){
                case MessageEvent.ID_REQUEST_PLAY_MUSIC:
                    play((int) event.object1, (String) event.object2, (boolean) event.object3);
                    break;
                case MessageEvent.ID_REQUEST_PLAY_OR_PAUSE_MUSIC:
                    playOrPause();
                    break;
                case MessageEvent.ID_REQUEST_PLAYING_INFO_MUSIC:
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC)
                            .Object1(true).Object2(mediaPlayer.isPlaying()).Object3(mediaPlayer.getDuration()));
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
                case MessageEvent.ID_REQUEST_PLAYING_PROGRESS_MUSIC:
                    playProgress();
                    break;
                case MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC:
                    seek((int)event.object1,(float)event.object2);
                    break;
                case MessageEvent.ID_RESPONSE_FM:
                    fm();
                    break;
            }
        }
    }

    public void play(int cloudType,String songId,boolean play){

        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if ( cloudType == Constant.CloudType_NULL
                ||( cloudType == playingMusic.musicBean.cloudType && songId.equals(playingMusic.musicBean.readId))){
            if (!mediaPlayer.isPlaying()){
                mediaPlayerPlay(null, true);
            }
        }else {
            playingMusic.musicType = Constant.NormalMusic;
            readyPlay(cloudType,songId,play);
        }
    }

    public void playProgress(){
        if (mediaPlayer.isPlaying())
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_PROGRESS_MUSIC).Object1(mediaPlayer.getCurrentPosition()).Object2(mediaPlayer.getDuration()));
    }

    public void seek(int type,float progress){
        if (type == MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC_TIME){
            mediaPlayer.seekTo((int)progress);
        }else if(type == MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC_PERCENTAGE){
            int total = mediaPlayer.getDuration();
            float millsecond = total * progress;
            mediaPlayer.seekTo((int)millsecond);
        }
    }

    public void playOrPause(){
        if (mediaPlayer.isPlaying())
            pause();
        else
            play(Constant.CloudType_NULL,null,true);
    }

    public void pause(){
        mediaPlayer.pause();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(false).Object2(mediaPlayer.isPlaying()));
    }

    public void stop(){
        mediaPlayer.stop();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(false).Object2(mediaPlayer.isPlaying()));
    }

    public void next(){
        MusicBean musicBean = CacheManager.getImstance().getNextDownMusic(playingMusic.musicBean.cloudType, playingMusic.musicBean.readId, LoopType);
        if (musicBean != null){
            try{
                playingMusic.musicBean = musicBean;
                mediaPlayerPlay(playingMusic,true);
            }catch (Exception E){}
        }else {
            stop();
        }
    }

    private void pre(){
        MusicBean musicBean = CacheManager.getImstance().getPreDownMusic(playingMusic.musicBean.cloudType, playingMusic.musicBean.readId, LoopType);
        if (musicBean != null){
            try{
                playingMusic.musicBean = musicBean;
                mediaPlayerPlay(playingMusic,true);
            }catch (Exception E){}
        }else {
            stop();
        }
    }

    private void fm(){
        Net.getWyApi().getApi().radio().execute(new WYCallback() {
            @Override
            public void onRequestSuccess(final String result) {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        playingMusic.musicType = Constant.FMMusic;
                        WYFMBean wyfmBean = JSON.parseObject(result, WYFMBean.class);
                        readPlayForNet(Constant.CloudType_WANGYI,wyfmBean.getData().get(0).getId(),true);
                    }
                });
            }
        });
    }

    private void mediaPlayerPlay(PlayingMusicBean playingMusic,boolean play){
        try{

            if (playingMusic != null){
                mediaPlayer.reset();

                //音乐文件不在？去网络获取
                if (!FileUtils.isExistsFile(playingMusic.musicBean.path)){
                    readPlayForNet(playingMusic.musicBean.cloudType,playingMusic.musicBean.readId,play);
                    return;
                }

                mediaPlayer.setDataSource(playingMusic.musicBean.path);
                mediaPlayer.prepare();
            }

            if (play)
                mediaPlayer.start();

            if (playingMusic != null)
                CacheManager.getImstance().selectPlayMusicList(playingMusic.musicBean.cloudType,playingMusic.musicBean.readId);

            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(true).Object2(mediaPlayer.isPlaying()).Object3(mediaPlayer.getDuration()));

        }catch (Exception e){

        }
    }

    private void readyPlay(int cloudType,String songId,boolean play){
        if (CacheManager.getImstance().hasDownMusic(cloudType,songId)){
            readPlayFormLocal(cloudType,songId,play);
        }else {
            readPlayForNet(cloudType,songId,play);
        }
    }

    private void readPlayFormLocal(int cloudType,String songId,boolean play){
        try
        {
            MusicBean musicBean = CacheManager.getImstance().getDownMusic(cloudType,songId);
            if (musicBean != null){
                playingMusic.musicBean = musicBean;
                mediaPlayerPlay(playingMusic, play);
            }else {
                stop();
            }
        }catch (Exception e){
        }
    }

    private void readPlayForNet(int cloudType,String songId,boolean play){
        switch (cloudType){
            case Constant.CloudType_WANGYI:
                readPlayFormNetWY(songId,play);
                break;
            case Constant.CloudType_QQ:
                readPlayFormNetQQ(songId,play);
                break;
        }
    }

    private void readPlayFormNetWY(final String songId,final boolean play){
        Net.getWyApi().getApi().detail("[{\"id\":\"" + songId + "\"}]").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(final String result) {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final WYSongDetailBean wySongDetailBean = JSON.parseObject(result, WYSongDetailBean.class);
                        if (wySongDetailBean.getSongs().size() > 0){
                            Net.getWyApi().getApi().songRes("[" + songId + "]", "999000", "").execute(new WYCallback() {
                                @Override
                                public void onRequestSuccess(final String result) {
                                    threadPoolExecutor.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            WYSongUrlBean songUrlBean = JSON.parseObject(result, WYSongUrlBean.class);
                                            if ( songUrlBean != null && songUrlBean.getData()!= null && songUrlBean.getData().size() > 0){
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
                                                musicBean.picture = songsBean.getAl().getPicUrl();
                                                musicBean.path = songUrlBean.getData().get(0).getUrl();

                                                playingMusic.musicBean = musicBean ;

                                                readyPlayFormNetProxy(musicBean,play);
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void readPlayFormNetQQ(final String songId,final boolean play){

        Net.getQqApi().getApi().detailSong(songId, "json").execute(new QQCallback() {
            @Override
            public void onRequestSuccess(final String result) {
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        QQMusicDetailBean detailBean = JSON.parseObject(result,QQMusicDetailBean.class);
                        if (detailBean != null){
                            MusicBean musicBean = new MusicBean();
                            musicBean.name = detailBean.getData().get(0).getName();
                            musicBean.cloudType = Constant.CloudType_QQ;
                            musicBean.readId = songId;
                            StringBuilder stringBuilder = new StringBuilder();
                            for (QQMusicDetailBean.DataBean.SingerBean singerBean : detailBean.getData().get(0).getSinger()){
                                stringBuilder.append(singerBean.getName()+" ");
                            }
                            musicBean.artist = stringBuilder.toString().substring(0, stringBuilder.length()-1);
                            musicBean.album = detailBean.getData().get(0).getAlbum().getName();
                            String albumId = detailBean.getData().get(0).getAlbum().getMid();
                            musicBean.picture = String.format("http://imgcache.qq.com/music/photo/mid_album_300/%s/%s/%s.jpg",albumId.substring(albumId.length()-2,albumId.length()-1)
                            ,albumId.substring(albumId.length()-1,albumId.length()),albumId);
                            musicBean.path = String.format("http://ws.stream.qqmusic.qq.com/%s.m4a?fromtag=46",detailBean.getData().get(0).getId());
                            musicBean.lyr = String.format("http://music.qq.com/miniportal/static/lyric/%d/%d.xml",detailBean.getData().get(0).getId()%100,detailBean.getData().get(0).getId());

                            playingMusic.musicBean = musicBean ;

                            readyPlayFormNetProxy(musicBean,play);
                        }
                    }
                });
            }
        });
    }

    //代理播放 边下载边播放
    private void readyPlayFormNetProxy(final MusicBean downMusicBean,final boolean play){
        try{

            mediaPlayer.pause();
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC)
                    .Object1(true).Object2(mediaPlayer.isPlaying()));

            final String dirPath = CacheManager.getImstance().getDirPath();
            final String filName = (downMusicBean.cloudType+"_"+downMusicBean.artist+"-"+ downMusicBean.name+".mp3").replace(" ", "");
            String[] temp = downMusicBean.picture.split("/");
            String pictureName = downMusicBean.cloudType+"_"+temp[temp.length-1];
            String lyricName = downMusicBean.cloudType+"_"+downMusicBean.artist+"-"+ downMusicBean.name+".lyr";

            String urlPicture = downMusicBean.picture;
            String urlPath = downMusicBean.path;
            String urlLyc = downMusicBean.lyr;

            String tempMusicStr = playingMusic.musicType == Constant.NormalMusic? "":"temp/";
            downMusicBean.picture =  dirPath+"/"+tempMusicStr+pictureName;
            downMusicBean.path = dirPath+"/"+tempMusicStr+filName;
            downMusicBean.lyr = dirPath+"/"+tempMusicStr+lyricName;

            //图片
            downPicture(downMusicBean,urlPicture,dirPath,tempMusicStr,pictureName);
            //歌词
            downLyric(downMusicBean,urlLyc,dirPath,tempMusicStr,lyricName);

            //使用代理边下载边播放
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mediaPlayerProxy.getLoacalUrl(urlPath, filName,playingMusic.musicType));
            mediaPlayer.prepare();
            if (play)
                mediaPlayer.start();

            if (playingMusic.musicType == Constant.NormalMusic){
                CacheManager.getImstance().insertDownMusic(downMusicBean);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_DOWN_LIST_MUSIC));
            }
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(true).Object2(mediaPlayer.isPlaying()));

        }catch (Exception e){

        }
    }

    public void downPicture(final MusicBean downMusicBean,String urlPicture,String dirPath,String tempMusicStr,String pictureName){
        Net.getWyApi().getApi().downFile(urlPicture).execute(new FileCallBack(dirPath + "/"+tempMusicStr, pictureName) {
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(File file, int i) {
                if (downMusicBean.cloudType == playingMusic.musicBean.cloudType &&
                        downMusicBean.readId.equals(playingMusic.musicBean.readId)) {
                    playingMusic.musicBean.picture = file.getAbsolutePath();
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(true).Object2(mediaPlayer.isPlaying()));
                }
            }
        });
    }


    public void downLyric(final MusicBean downMusicBean,String urlLyc,String dirPath,String tempMusicStr,String lyricName){

        if (downMusicBean.cloudType == Constant.CloudType_WANGYI){
            Net.getWyApi().getApi().lyric("pc", downMusicBean.readId, "-1", "-1", "-1").execute(new WYCallback() {
                @Override
                public void onRequestSuccess(String result) {
                    WYLyricBean bean = JSON.parseObject(result, WYLyricBean.class);
                    if (bean != null && bean.getLrc() != null){
                        FileUtils.saveStringToFile(bean.getLrc().getLyric(), downMusicBean.lyr);
                        if (downMusicBean.cloudType == playingMusic.musicBean.cloudType &&
                                downMusicBean.readId.equals(playingMusic.musicBean.readId)) {
                            playingMusic.musicBean.lyr = downMusicBean.lyr;
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(true).Object2(mediaPlayer.isPlaying()));
                        }
                    }
                }
            });
        }else if (downMusicBean.cloudType == Constant.CloudType_QQ){
            Net.getWyApi().getApi().downFile(urlLyc).execute(new FileCallBack(dirPath + "/" + tempMusicStr, lyricName) {

                public File saveFile(Response response, final int id) throws IOException {
                    InputStream is = null;
                    FileOutputStream fos = null;
                    InputStreamReader inputStreamReader= null;
                    BufferedReader bufferedReader= null;
                    OutputStreamWriter outputStreamWriter= null;
                    BufferedWriter bufferedWriter= null;
                    try {
                        is = response.body().byteStream();
                        inputStreamReader = new InputStreamReader(is,"GB2312");
                        bufferedReader = new BufferedReader(inputStreamReader);

                        File dir = new File(destFileDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(dir, destFileName);
                        fos = new FileOutputStream(file);
                        outputStreamWriter = new OutputStreamWriter(fos);
                        bufferedWriter = new BufferedWriter(outputStreamWriter);

                        String line="";
                        StringBuilder result= new StringBuilder();
                        while((line = bufferedReader.readLine()) != null){
                            if(line.trim().equals(""))
                                continue;
                            result.append(line + "\r\n");
                        }

                        String xmlHead = "<?xml version=\"1.0\" encoding=\"GB2312\" ?><lyric><![CDATA[";
                        String xmlTail = "]]></lyric>";
                        String realLyc = result.substring(xmlHead.length(), result.length() - xmlTail.length());
                        bufferedWriter.write(realLyc);

                        fos.flush();
                        return file;

                    } finally {
                        try {
                            response.body().close();
                            if (bufferedReader!= null)
                                bufferedReader.close();
                            if (inputStreamReader!= null)
                                inputStreamReader.close();
                            if (is != null)
                                is.close();
                            if (bufferedWriter!= null)
                                bufferedWriter.close();
                            if (outputStreamWriter!= null)
                                outputStreamWriter.close();
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {

                        }
                    }
                }

                @Override
                public void onError(Call call, Exception e, int i) {}

                @Override
                public void onResponse(File file, int i) {
                    try {
                        if (downMusicBean.cloudType == playingMusic.musicBean.cloudType &&
                                downMusicBean.readId.equals(playingMusic.musicBean.readId)) {
                            playingMusic.musicBean.lyr = downMusicBean.lyr;
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC).Object1(true).Object2(mediaPlayer.isPlaying()));
                        }
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        //mediaPlayer.reset();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (playingMusic.musicType == Constant.NormalMusic)
            next();
        else if (playingMusic.musicType == Constant.FMMusic)
            fm();
    }

    @Override
    public void onAudioFocusChange(int i) {
        int volume;
        switch (i) {
            // 获得焦点
            case AudioManager.AUDIOFOCUS_GAIN:
                if (lossPlayStatus) {
                    play(Constant.CloudType_NULL,null,true);
                }
                if (lossVolume > 0 ) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lossVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
                break;
            // 永久丢失焦点
            case AudioManager.AUDIOFOCUS_LOSS:
                lossPlayStatus =mediaPlayer.isPlaying();
                lossVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                pause();
                break;
            // 短暂丢失焦点
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                lossPlayStatus =mediaPlayer.isPlaying();
                lossVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                pause();
                break;
            // 瞬间丢失焦点
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                lossPlayStatus =mediaPlayer.isPlaying();
                lossVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (mediaPlayer.isPlaying() && volume > 0) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume / Sound_Coefficient , AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                }
                break;
        }
    }
}

