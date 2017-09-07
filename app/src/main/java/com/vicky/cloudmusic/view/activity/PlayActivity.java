package com.vicky.cloudmusic.view.activity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.android.baselib.utils.StringUtils;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.BitmapManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.lyric.Lyric;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.view.LyricView;
import com.vicky.cloudmusic.viewmodel.PlayVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

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
    @Bind(R.id.iv_song_picture)
    ImageView ivSongPicture;
    @Bind(R.id.rl_disc)
    RelativeLayout rlDisc;
    @Bind(R.id.im_play)
    ImageView imPlay;
    @Bind(R.id.iv_background)
    ImageView ivBackground;
    @Bind(R.id.im_pre)
    ImageView imPre;
    @Bind(R.id.im_next)
    ImageView imNext;
    @Bind(R.id.tv_run_time)
    TextView tvRunTime;
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    @Bind(R.id.sb_music)
    SeekBar sbMusic;
    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.lv_lyric)
    LyricView lvLyric;
    @Bind(R.id.rl_lyric)
    RelativeLayout rlLyric;
    @Bind(R.id.im_music_list)
    ImageView imMusicList;

    UpActivityTask upActivityTask;
    LycTask lycTask;

    Timer timer;
    Animation rotateAnimation;


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

        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC).
                        Object1(MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC_PERCENTAGE).Object2((float) seekBar.getProgress() / 100));
            }
        });

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        lvLyric.setSeekCallback(new LyricView.ISeekCallback() {
            @Override
            public void callback(long time) {
                if (getViewModel().isPlaying)
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC).
                            Object1(MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC_TIME).Object2((float) time));
            }
        });

        lvLyric.setTouchOnceCallback(new LyricView.ITouchOnceCallback() {
            @Override
            public void onTouchOnce() {
                rlDisc.setVisibility(View.VISIBLE);
                rlLyric.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAYING_PROGRESS_MUSIC));
                }
            }, 0, 1000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @OnClick({R.id.im_play, R.id.im_pre, R.id.im_next,
            R.id.rl_disc, R.id.rl_lyric,R.id.im_music_list})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_play:
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_OR_PAUSE_MUSIC));
                break;
            case R.id.im_pre:
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PRE_MUSIC));
                break;
            case R.id.im_next:
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_NEXT_MUSIC));
                break;
            case R.id.rl_disc:
                rlDisc.setVisibility(View.GONE);
                rlLyric.setVisibility(View.VISIBLE);
                break;
            case R.id.im_music_list:
                showMusicList();
                break;
            case  R.id.rl_lyric:
                rlDisc.setVisibility(View.VISIBLE);
                rlLyric.setVisibility(View.GONE);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:

                final MusicBean musicBean = (MusicBean) event.object1;
                boolean isPlaying = (boolean) event.object2;
                if (event.object3 != null) {
                    int duration = (int) event.object3;
                    tvTotalTime.setText(StringUtils.getMinuteSecond(duration));
                }

                if (isPlaying) {
                    imPlay.setImageResource(R.drawable.note_btn_pause);
                    getViewModel().isPlaying = true;
                    //rlDisc.startAnimation(rotateAnimation);
                } else {
                    imPlay.setImageResource(R.drawable.note_btn_play);
                    getViewModel().isPlaying = false;
                    //rlDisc.clearAnimation();
                }

                if (musicBean != null) {
                    tvMusicName.setText(musicBean.name);
                    tvArtist.setText(musicBean.artist);
                    upActivityTask = new UpActivityTask();
                    upActivityTask.execute(musicBean.picture);
                    lycTask = new LycTask();
                    lycTask.execute(musicBean.lyr);
                }

                break;
            case MessageEvent.ID_RESPONSE_DOWN_PROGRESS_MUSIC:
                float downProgress = (float) event.object2;
                sbMusic.setSecondaryProgress((int) (downProgress * 100));
                break;
            case MessageEvent.ID_RESPONSE_PLAYING_PROGRESS_MUSIC:
                float playProgress = (int) event.object1;
                float totalProgress = (int) event.object2;
                int progress = (int) (playProgress / totalProgress * 100);
                tvRunTime.setText(StringUtils.getMinuteSecond((int) playProgress));
                sbMusic.setProgress(progress);
                lvLyric.seekTime((int) playProgress);
                break;
        }
    }

    private class UpActivityTask extends AsyncTask<String, Object, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(String... strings) {
            Bitmap bitmap = null;
            Bitmap blur = null;
            if (strings[0] != null && !StringUtils.hasHttpPrefix(strings[0])) {
                bitmap = BitmapManager.getInstance().getBitmap(strings[0]);
                if (bitmap != null)
                    blur = BitmapManager.getInstance().getBitmapBlur(strings[0]);
            }
            return new Bitmap[]{bitmap, blur};
        }

        @Override
        protected void onPostExecute(Bitmap[] result) {
            if (ivSongPicture != null && ivBackground != null) {
                if (result[0] != null)
                    ivSongPicture.setImageBitmap(result[0]);
                if (result[1] != null)
                    ivBackground.setImageBitmap(result[1]);
            }
            upActivityTask = null;
        }
    }

    private class LycTask extends AsyncTask<String, Object, Lyric> {

        @Override
        protected Lyric doInBackground(String... params) {
            if (!TextUtils.isEmpty(params[0])) {
                String lyc = FileUtils.getStringFromFile(params[0]);
                if (!TextUtils.isEmpty(lyc)) {
                    Lyric lyric = new Lyric(lyc);
                    return lyric;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Lyric lyric) {
            if (lvLyric != null)
                lvLyric.setLyric(lyric);
        }
    }

}
