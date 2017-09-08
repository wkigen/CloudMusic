package com.vicky.cloudmusic.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.vicky.android.baselib.net.NetBase;
import com.vicky.cloudmusic.Application;

/**
 * Created by vicky on 2017/8/29.
 */
public class Net {

    public interface ImageLoaderCallBack{
        void getBitmap(Bitmap bitmap);
    }
    private static WYApi wyApi = new WYApi();
    private static QQApi qqApi = new QQApi();
    public static void init(){
        NetBase.init();
    }

    public static WYApi getWyApi(){
        return wyApi;
    }

    public static QQApi getQqApi(){
        return qqApi;
    }

    public static void imageLoader(Context context, final String imageurl, final ImageView view,
                                           final int defaultImageResId, final int errorImageResId) {
        if (context == null) {
            context = Application.getInstance();
        }
        Glide.with(context)
                .load(imageurl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .skipMemoryCache(true)
                .placeholder(defaultImageResId)
                .error(errorImageResId)
                .into(view);
    }

    public static void imageLoader(Context context, final String imageurl,final ImageLoaderCallBack callBack){
        Glide.with(context)
                .load(imageurl)
                .asBitmap()
                .toBytes()
                .centerCrop()
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] data, GlideAnimation anim) {
                        Bitmap bitmap = null;
                        if (data != null)
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        callBack.getBitmap(bitmap);
                    }
                });
    }
}
