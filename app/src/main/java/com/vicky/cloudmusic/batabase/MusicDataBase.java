package com.vicky.cloudmusic.batabase;

import android.database.Cursor;

import com.vicky.cloudmusic.Application;
import com.vicky.cloudmusic.bean.MusicBean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vicky on 2017/9/1.
 */
public class MusicDataBase {

    private static MusicDataBase mInstance;
    MusicDBHelper musicDBHelper;

    private MusicDataBase(){
        musicDBHelper = new MusicDBHelper(Application.getInstance());
    }

    public static MusicDataBase getInstance(){
        if (mInstance==null){
            synchronized (MusicDataBase.class){
                if (mInstance == null)
                    mInstance = new MusicDataBase();
            }
        }
        return mInstance;
    }

    public List<MusicBean> getAllMusic(){
        Cursor cursor = musicDBHelper.getAll(null,null);
        List<MusicBean> musicBeanList = new LinkedList<>();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            MusicBean musicBean = new MusicBean();
            musicBean.mid = cursor.getInt(MusicDBHelper.MID);
            musicBean.name = cursor.getString(MusicDBHelper.NAME);
            musicBean.cloudType = cursor.getInt(MusicDBHelper.CLOUDTYPE);
            musicBean.readId = cursor.getString(MusicDBHelper.READID);
            musicBean.artist = cursor.getString(MusicDBHelper.ARTIST);
            musicBean.picture = cursor.getString(MusicDBHelper.PICTURE);
            musicBean.path = cursor.getString(MusicDBHelper.PATH);
            musicBean.lyr = cursor.getString(MusicDBHelper.LYR);

            musicBeanList.add(musicBean);
        }
        return musicBeanList;
    }

    public void insertMusic(MusicBean musicBean){
        if (musicBean != null){
            musicDBHelper.insert(musicBean.name,musicBean.cloudType,musicBean.readId,musicBean.artist,musicBean.picture,musicBean.path,musicBean.lyr);
        }
    }

    public void deleteMusic(MusicBean musicBean){
        if (musicBean != null && musicBean.mid != MusicBean.Invalid_ID){
            musicDBHelper.delete(musicBean.mid);
        }
    }

}
