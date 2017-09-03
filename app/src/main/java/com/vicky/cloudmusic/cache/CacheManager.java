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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vicky on 2017/8/30.
 */
public class CacheManager {

    public static final String SHARENAME = "cache_data";

    private static CacheManager mInstance;

    SharedPreferences sharedPreferences;

    private List<String> sreachHistroyBeens; //搜索历史
    private List<MusicBean> downMusicList;//下载歌曲列表
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
            }
        }
    }

    public synchronized void deleteDownMusic(MusicBean musicBean){
        if (musicBean != null){
            for (MusicBean music : downMusicList){
                if (music.cloudType == musicBean.cloudType && music.readId.equals(musicBean.readId)){
                    downMusicList.remove(music);
                    MusicDataBase.getInstance().deleteMusic(musicBean);
                    break;
                }
            }
        }
    }

}
