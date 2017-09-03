package com.vicky.cloudmusic.view.activity.base;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.mvvm.base.BaseLibActivity;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.BitmapManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.activity.PlayActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by vicky on 2017/4/2.
 */
public abstract class BaseActivity<T extends IView, RM extends AbstractViewModel<T>> extends BaseLibActivity<T, RM> {

    @Nullable
    @Bind(R.id.rl_bottom_main_play)
    RelativeLayout rlBottomMainPlay;
    @Nullable
    @Bind(R.id.iv_bottom_music_picture)
    ImageView ivBottomMusicPicture;
    @Nullable
    @Bind(R.id.iv_bottom_music_play)
    ImageView ivBottomMusicPlay;
    @Nullable
    @Bind(R.id.tv_bottom_music_name)
    TextView tvBottomMusicName;
    @Nullable
    @Bind(R.id.tv_bottom_music_des)
    TextView tvBottomMusicDes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (rlBottomMainPlay !=null){
            rlBottomMainPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    readyGo(PlayActivity.class);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAYING_INFO_MUSIC));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void setBottomMusic(boolean isPlaying,MusicBean music){
        if (music.mid == MusicBean.Invalid_ID ){
            if (ivBottomMusicPicture != null)
                Net.imageLoader(this,music.picture,ivBottomMusicPicture,R.drawable.img_one_bi_one,R.drawable.img_one_bi_one);
        }else {
            if (ivBottomMusicPicture != null)
                ivBottomMusicPicture.setImageBitmap(BitmapManager.getInstance().getBitmap(music.picture));

        }
        if (tvBottomMusicName != null)
            tvBottomMusicName.setText(music.name);
        if (tvBottomMusicDes != null)
            tvBottomMusicDes.setText(music.artist);
    }

}
