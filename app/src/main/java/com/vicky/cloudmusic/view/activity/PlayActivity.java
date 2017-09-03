package com.vicky.cloudmusic.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.utils.FastBlurUtil;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.BitmapManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.viewmodel.PlayVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileInputStream;

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
    @Bind(R.id.im_pre)
    ImageView imPre;
    @Bind(R.id.im_next)
    ImageView imNext;


    UpActivityTask upActivityTask;

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
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAYING_INFO_MUSIC));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @OnClick({R.id.iv_back, R.id.im_play})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.im_play:
                if (!getViewModel().isPlaying)
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_MUSIC).Object1(Constant.CloudType_NULL));
                else
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PAUSE_MUSIC));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:

                boolean isPlaying = (boolean) event.object2;
                final MusicBean musicBean = (MusicBean) event.object1;

                if (isPlaying) {
                    imPlay.setImageResource(R.drawable.note_btn_pause);
                    getViewModel().isPlaying = true;
                } else {
                    imPlay.setImageResource(R.drawable.note_btn_play);
                    getViewModel().isPlaying = false;
                }

                if (musicBean != null){
                    tvMusicName.setText(musicBean.name);
                    tvArtist.setText(musicBean.artist);
                    if (musicBean.mid == MusicBean.Invalid_ID) {
                        Net.imageLoader(this, musicBean.picture, new Net.ImageLoaderCallBack() {
                            @Override
                            public void getBitmap(Bitmap bitmap) {
                                ivSongPicture.setImageBitmap(bitmap);
                                ivBackground.setImageBitmap(FastBlurUtil.doBlur(bitmap, 8, false));
                            }
                        });
                    } else {
                        upActivityTask = new UpActivityTask();
                        upActivityTask.execute(musicBean.picture);
                    }
                }
                break;
        }
    }

    private class UpActivityTask extends AsyncTask<String,Object,Bitmap[]>{

        @Override
        protected Bitmap[] doInBackground(String... strings) {
            Bitmap bitmap = null ;
            Bitmap blur = null ;
            bitmap = BitmapManager.getInstance().getBitmap(strings[0]);
            if (bitmap != null)
                blur = FastBlurUtil.doBlur(bitmap,100,false);
            return  new Bitmap[]{bitmap,blur};
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

}
