package com.vicky.cloudmusic.view.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.utils.FastBlurUtil;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.viewmodel.PlayVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class PlayActivity extends BaseActivity<PlayActivity, PlayVM> implements IView, View.OnClickListener {


    @Bind(R.id.tv_music_name)
    TextView tvMusicName;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_song_picture)
    ImageView ivSongPicture;
    @Bind(R.id.rl_disc)
    RelativeLayout rlDisc;
    @Bind(R.id.im_play)
    ImageView imPlay;
    @Bind(R.id.iv_background)
    ImageView ivBackground;


    @Override
    protected int tellMeLayout() {
        return R.layout.activity_play;
    }

    @Override
    public Class<PlayVM> getViewModelClass() {
        return PlayVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.iv_back, R.id.im_play})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.im_play:
                getViewModel().play();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_REFRESH_PLAYING_INFO_MUSIC:
                MusicBean musicBean = (MusicBean) event.object1;
                tvMusicName.setText(musicBean.name);
                tvArtist.setText(musicBean.artist);
                //Net.imageLoader(this, musicBean.picture, ivSongPicture, R.drawable.placeholder_disk_play_program, R.drawable.placeholder_disk_play_program);
                Net.imageLoader(this, musicBean.picture, new Net.ImageLoaderCallBack() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        ivSongPicture.setImageBitmap(bitmap);
                        ivBackground.setImageBitmap(FastBlurUtil.doBlur(bitmap,8,false));
                    }
                });
                break;
        }
    }

}
