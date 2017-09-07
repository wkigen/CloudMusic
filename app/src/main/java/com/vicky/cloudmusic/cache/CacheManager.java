package com.vicky.cloudmusic.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.Application;
import com.vicky.cloudmusic.batabase.MusicDataBase;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.bean.PlayMusicStausBean;
import com.vicky.cloudmusic.bean.PlayingMusicBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vicky on 2017/8/30.
 */
public class CacheManager {

    public static final String SHARENAME = "cache_data";

    private static CacheManager mInstance;

    SharedPreferences sharedPreferences;

    private PlayingMusicBean playingMusicBean = new PlayingMusicBean();
    private List<String> sreachHistroyBeens; //搜索历史
    private List<MusicBean> downMusicList;//下载歌曲列表
    private List<PlayMusicStausBean> playMusicStausBeanList;    //播放列表
    private String dirPath;

    private CacheManager(){
        sharedPreferences = Application.getInstance().getSharedPreferences(SHARENAME, Context.MODE_PRIVATE); //
    }

    public static CacheManager getImstance(){
        if (mInstance == null){
            synchronized (CacheManager.class){
                if (mInstance == null){
                    mInstance = new CacheManager();
                }
            }
        }
        return mInstance;
    }

    public PlayingMusicBean getPlayingMusicBean(){
        return playingMusicBean;
    }

    //获取搜索历史
    public List<String> getSreachHistroy(){
        if (sreachHistroyBeens == null){
            String json = sharedPreferences.getString(Constant.SreachHistroy, "");
            if (TextUtils.isEmpty(json))
                sreachHistroyBeens =  new LinkedList<>();
            else
                sreachHistroyBeens = JSON.parseArray(json,String.class);
        }
        return sreachHistroyBeens;
    }

    //保存搜索历史
    public void saveSreachHistroy(){
        if (sreachHistroyBeens != null){
            String json = JSON.toJSONString(sreachHistroyBeens);
            sharedPreferences.edit().putString(Constant.SreachHistroy,json).commit();
        }
    }

    public String getDirPath(){
        if (dirPath == null) {
            dirPath = FileUtils.getAbsoulutePath(Application.getInstance(), Constant.dir_name);
            FileUtils.createDirectory(dirPath);
        }
        return dirPath;
    }

    public synchronized List<MusicBean> getDownMusicList(){
        if (downMusicList == null){
            downMusicList = MusicDataBase.getInstance().getAllMusic();
        }
        return downMusicList;
    }

    public synchronized MusicBean getDownMusic(int cloudType, String readId){
        if (downMusicList == null)
            return null;
        for (MusicBean music : downMusicList){
            if (music.cloudType == cloudType && music.readId.equals(readId))
                return music;
        }
        return null;
    }

    public synchronized MusicBean getPreDownMusic(int cloudType, String readId,int loopType){
        if (downMusicList == null || downMusicList.size()==0)
            return null;
        MusicBean preMusicBean = null;
        switch (loopType){
            case Constant.Play_List_Loop:
                for (MusicBean music : downMusicList){
                    if (music.cloudType == cloudType && music.readId.equals(readId)){
                        break;
                    }
                    preMusicBean = music;
                }
                break;
        }
        if (preMusicBean == null)
            preMusicBean = downMusicList.get(downMusicList.size()-1);
        return preMusicBean;
    }


    public synchronized MusicBean getNextDownMusic(int cloudType, String readId,int loopType){
        if (downMusicList == null)
            return null;
        MusicBean musicBean = null;
        switch (loopType){
            case Constant.Play_List_Loop:
                boolean flag = false;
                for (MusicBean music : downMusicList){
                    if (flag)
                        return music;
                    if (music.cloudType == cloudType && music.readId.equals(readId))
                        flag = true;
                }
                break;
        }
        if (musicBean == null)
            musicBean =  downMusicList.get(0);
        return musicBean;
    }

    public synchronized boolean hasDownMusic(int cloudType, String readId){
        if (downMusicList == null)
            return false;

        for (MusicBean music : downMusicList){
            if (music.cloudType == cloudType && music.readId.equals(readId))
                return true;
        }
        return false;
    }

    public synchronized void insertDownMusic(MusicBean musicBean){
        if (musicBean != null){
            if (!hasDownMusic(musicBean.cloudType,musicBean.readId)) {
                downMusicList.add(musicBean);
                MusicDataBase.getInstance().insertMusic(musicBean);
                //// TODO: 2017/9/5     修改了数据库
                playMusicStausBeanList = null;
            }
        }
    }

    public synchronized void deleteAllDownMusic(){
        if (downMusicList == null)
            return;
        for (MusicBean music : downMusicList){
            FileUtils.deleteFile(music.path);
            FileUtils.deleteFile(music.lyr);
            FileUtils.deleteFile(music.picture);
            MusicDataBase.getInstance().deleteMusic(music);
        }
        playMusicStausBeanList = null;
        downMusicList.clear();
    }

    public synchronized void deleteDownMusic(MusicBean musicBean){
        if (musicBean != null){
            for (MusicBean music : downMusicList){
                if (music.cloudType == musicBean.cloudType && music.readId.equals(musicBean.readId)){
                    FileUtils.deleteFile(music.path);
                    FileUtils.deleteFile(music.lyr);
                    FileUtils.deleteFile(music.picture);
                    downMusicList.remove(music);
                    MusicDataBase.getInstance().deleteMusic(musicBean);
                    //// TODO: 2017/9/5     修改了数据库
                    playMusicStausBeanList = null;
                    break;
                }
            }
        }
    }

    public synchronized List<PlayMusicStausBean> getPlayMusicList(){
        if (playMusicStausBeanList == null){
            playMusicStausBeanList = new ArrayList<>();
            List<MusicBean> downMusicList = getDownMusicList();
            for (MusicBean musicBean : downMusicList){
                PlayMusicStausBean playMusicStausBean = new PlayMusicStausBean();
                playMusicStausBean.musicBean = musicBean;
                playMusicStausBean.isSelect = false;
                playMusicStausBeanList.add(playMusicStausBean);
            }
        }
        return playMusicStausBeanList;
    }

    public synchronized void selectPlayMusicList(int cludeType,String readId){
        getPlayMusicList();
        if (playMusicStausBeanList == null && cludeType >0 && !TextUtils.isEmpty(readId))
            return;
        for (PlayMusicStausBean playMusicStausBean : playMusicStausBeanList){
            if (playMusicStausBean.musicBean.cloudType == cludeType &&
                    playMusicStausBean.musicBean.readId.equals(readId)){
                playMusicStausBean.isSelect = true;
            }else {
                playMusicStausBean.isSelect = false;
            }
        }
    }

}
