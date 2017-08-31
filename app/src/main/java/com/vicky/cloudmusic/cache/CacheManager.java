package com.vicky.cloudmusic.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vicky on 2017/8/30.
 */
public class CacheManager {

    public static final String SHARENAME = "cache_data";

    private static CacheManager mInstance;

    SharedPreferences sharedPreferences;

    List<String> sreachHistroyBeens; //搜索历史
    String dirPath;

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
            String json = sharedPreferences.getString(Constant.SreachHistroy,"");
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

}
