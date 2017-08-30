package com.vicky.cloudmusic.cache;

/**
 * Created by vicky on 2017/8/30.
 */
public class CacheManager {

    public static final String SHARENAME = "cache_data";

    private CacheManager mInstance;

    private CacheManager(){

    }

    public CacheManager getImstance(){
        if (mInstance == null){
            synchronized (CacheManager.class){
                if (mInstance == null){
                    mInstance = new CacheManager();
                }
            }
        }
        return mInstance;
    }
}
