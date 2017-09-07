package com.vicky.cloudmusic.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vicky.android.baselib.mvvm.IView;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.view.LyricView;
import com.vicky.cloudmusic.viewmodel.FMVM;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class FMActivity extends BaseActivity<FMActivity, FMVM> implements IView {

    @Bind(R.id.iv_background)
    ImageView ivBackground;
    @Bind(R.id.tv_run_time)
    TextView tvRunTime;
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    @Bind(R.id.sb_music)
    SeekBar sbMusic;
    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.im_play)
    ImageView imPlay;
    @Bind(R.id.im_next)
    ImageView imNext;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.iv_song_picture)
    ImageView ivSongPicture;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.ll_disc)
    LinearLayout llDisc;
    @Bind(R.id.lv_lyric)
    LyricView lvLyric;
    @Bind(R.id.rl_lyric)
    RelativeLayout rlLyric;

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_fm;
    }

    @Override
    public Class<FMVM> getViewModelClass() {
        return FMVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:

                break;
        }
    }

}
