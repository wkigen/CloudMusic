package com.vicky.cloudmusic.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.LruCache;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.utils.FastBlurUtil;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.cloudmusic.Application;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.batabase.MusicDataBase;
import com.vicky.cloudmusic.bean.MusicBean;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vicky on 2017/8/30.
 */
public class BitmapManager {

    private static BitmapManager mInstance;

    private LruCache<String, Bitmap> mBitapCache;
    private static final String BlurPre = "blur_";
    private BitmapManager(){
        int MAXMEMONRY = (int) (Runtime.getRuntime() .maxMemory() / 1024);
        mBitapCache = new LruCache<>(MAXMEMONRY / 4);
    }

    public static BitmapManager getInstance(){
        if (mInstance == null){
            synchronized (BitmapManager.class){
                if (mInstance == null)
                    mInstance = new BitmapManager();
            }
        }
        return mInstance;
    }

    public synchronized  Bitmap getBitmapBlur(String key){
        Bitmap bm = getBitmap(BlurPre+key);
        if (bm == null){
            bm = getBitmap(key);
            if (bm != null) {
                bm =FastBlurUtil.doBlur(bm, 100, false);
                mBitapCache.put(BlurPre+key,bm);
            }
        }
        return bm;
    }

    public synchronized Bitmap getBitmap(String key) {
        Bitmap bm = mBitapCache.get(key);
        if (bm == null) {
            bm = readBitmap(key);
        }
        return bm;
    }

    private Bitmap readBitmap(String key){
        FileInputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = new FileInputStream(key);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
        }finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            }catch (Exception e){}
            return bitmap;
        }
    }

    public synchronized void removeBitmap(String key) {
        if (key != null) {
            if (mBitapCache != null) {
                Bitmap bm = mBitapCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }

    public void clear() {
        if (mBitapCache != null) {
            if (mBitapCache.size() > 0) {
                mBitapCache.evictAll();
            }
        }
    }

}
