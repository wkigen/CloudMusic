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
import com.vicky.cloudmusic.bean.PlayingMusicBean;
import com.vicky.cloudmusic.cache.BitmapManager;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.callback.IMoveCallback;
import com.vicky.cloudmusic.callback.ITouchCallback;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.lyric.Lyric;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.view.DiscView;
import com.vicky.cloudmusic.view.view.LyricView;
import com.vicky.cloudmusic.viewmodel.PlayVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.dv_main)
    DiscView dvMain;

    UpActivityTask upActivityTask;
    LycTask lycTask;

    Timer timer;


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

        lvLyric.setSeekCallback(new LyricView.ISeekCallback() {
            @Override
            public void callback(long time) {
                if (getViewModel().isPlaying)
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC).
                            Object1(MessageEvent.ID_REQUEST_SEEK_PROGRESS_MUSIC_TIME).Object2((float) time));
            }
        });

        lvLyric.setTouchCallback(new ITouchCallback() {
            @Override
            public void onTouchOnce() {
                dvMain.setVisibility(View.VISIBLE);
                rlLyric.setVisibility(View.GONE);
            }
        });

        dvMain.setTouchCallback(new ITouchCallback() {
            @Override
            public void onTouchOnce() {
                dvMain.setVisibility(View.GONE);
                rlLyric.setVisibility(View.VISIBLE);
            }
        });

        dvMain.setMoveCallback(new IMoveCallback() {
            @Override
            public void onMove(float offestX, float offestY, int width, int height) {

            }

            @Override
            public void onUp(float offestX, float offestY, int width, int height) {
                if (Math.abs(offestX) > width / 2){
                    if (offestX < 0){
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_NEXT_MUSIC));
                    }else {
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PRE_MUSIC));
                    }
                }
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

    @Override
    public void onStop() {
        super.onStop();
        if (dvMain != null)
            dvMain.stop();
    }

    @OnClick({R.id.im_play, R.id.im_pre, R.id.im_next, R.id.rl_lyric, R.id.im_music_list})
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
            case R.id.im_music_list:
                showMusicList();
                break;
            case R.id.rl_lyric:
                dvMain.setVisibility(View.VISIBLE);
                rlLyric.setVisibility(View.GONE);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:
                boolean isRefresh = (boolean) event.object1;
                boolean isPlaying = (boolean) event.object2;
                if (event.object3 != null) {
                    int duration = (int) event.object3;
                    tvTotalTime.setText(StringUtils.getMinuteSecond(duration));
                }

                if (isPlaying) {
                    imPlay.setImageResource(R.drawable.note_btn_pause);
                    getViewModel().isPlaying = true;
                    dvMain.start();
                } else {
                    imPlay.setImageResource(R.drawable.note_btn_play);
                    getViewModel().isPlaying = false;
                    dvMain.pause();
                }
                PlayingMusicBean playingMusicBean = isRefresh ? CacheManager.getImstance().getPlayingMusicBean() : null;
                if (playingMusicBean != null) {
                    tvMusicName.setText(playingMusicBean.musicBean.name);
                    tvArtist.setText(playingMusicBean.musicBean.artist);
                    upActivityTask = new UpActivityTask();
                    upActivityTask.execute(playingMusicBean.musicBean.picture);
                    lycTask = new LycTask();
                    lycTask.execute(playingMusicBean.musicBean.lyr);
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
                tvTotalTime.setText(StringUtils.getMinuteSecond((int) totalProgress));
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
            if (dvMain != null && ivBackground != null) {
                if (result[0] != null)
                    dvMain.setBitmap(result[0]);
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
