package com.vicky.cloudmusic.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * Created by vicky on 2017/9/13.
 */
public class VideoView extends RelativeLayout implements MediaPlayerWrapper.MainThreadMediaPlayerListener, View.OnClickListener{

    private static final long Operation_Show_Time = 1000;
    private static final float offset = 50;

    private float lastX,lastY;

    private VideoPlayerView videoPlayerView;
    private ImageView backgroundImageView;
    private ImageView playStatusImageView;
    private ImageView maxVideoImageView;

    private int playState;
    private Handler handler = new Handler();

    private VideoPlayerManager<MetaData> videoPlayerManager;

    Runnable showOperationRunnable = new Runnable() {
        @Override
        public void run() {
            showOperation(false);
        }
    };

    public VideoView(Context context) {
        super(context);
        init();
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        setBackgroundColor(getContext().getResources().getColor(R.color.black));

        videoPlayerView = new VideoPlayerView(getContext());
        backgroundImageView= new ImageView(getContext());
        playStatusImageView = new ImageView(getContext());
        maxVideoImageView = new ImageView(getContext());

        videoPlayerView.addMediaPlayerListener(this);

        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        playStatusImageView.setImageResource(R.drawable.lock_btn_play);
        playStatusImageView.setOnClickListener(this);

        maxVideoImageView.setImageResource(R.drawable.titlebar_menu);
        maxVideoImageView.setOnClickListener(this);

        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        LayoutParams lp2 = new LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT);

        LayoutParams lp3 = new LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        addView(videoPlayerView, lp);
        addView(backgroundImageView, lp);
        addView(playStatusImageView, lp2);
        addView(maxVideoImageView, lp3);
    }

    public void setVideoPlayerManager(VideoPlayerManager<MetaData> videoPlayerManager){
        this.videoPlayerManager = videoPlayerManager;
    }

    public ImageView getBackgroundImageView(){
        return backgroundImageView;
    }

    public void setBackground(Bitmap bitmap){
        backgroundImageView.setImageBitmap(bitmap);
    }

    public VideoPlayerView getVideoPlayerView(){
        return videoPlayerView;
    }

    public void setStatus(int status){
        switch (status){
            case Constant.Status_Stop:
                playState = Constant.Status_Stop;
                backgroundImageView.setVisibility(VISIBLE);
                playStatusImageView.setVisibility(VISIBLE);
                maxVideoImageView.setVisibility(VISIBLE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_play);
                break;
            case Constant.Status_Resume:
                playState = Constant.Status_Resume;
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                maxVideoImageView.setVisibility(GONE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_pause);
                break;
            case Constant.Status_Play:
                playState = Constant.Status_Play;
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                maxVideoImageView.setVisibility(GONE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_pause);
                break;
            case Constant.Status_Pause:
                playState = Constant.Status_Pause;
                backgroundImageView.setVisibility(VISIBLE);
                playStatusImageView.setVisibility(VISIBLE);
                maxVideoImageView.setVisibility(VISIBLE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_play);
                break;
            case Constant.Status_Prepare:
                playState = Constant.Status_Prepare;
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                maxVideoImageView.setVisibility(GONE);
                break;
        }
    }


    private void showOperation(boolean isShow){
        if (isShow){
            playStatusImageView.setVisibility(VISIBLE);
        }else {
            playStatusImageView.setVisibility(GONE);
        }
    }

    @Override
    public void onVideoSizeChangedMainThread(int width, int height) {

    }

    @Override
    public void onVideoPreparedMainThread() {

    }

    @Override
    public void onVideoCompletionMainThread() {
        setStatus(Constant.Status_Stop);
    }

    @Override
    public void onErrorMainThread(int what, int extra) {
        setStatus(Constant.Status_Stop);
    }

    @Override
    public void onBufferingUpdateMainThread(int percent) {
    }

    @Override
    public void onVideoStoppedMainThread() {
        setStatus(Constant.Status_Stop);
    }

    @Override
    public void onVideoStartedMainThread(){
        setStatus(Constant.Status_Play);
    }

    @Override
    public void onVideoPausedMainThread(){
        setStatus(Constant.Status_Pause);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                return false;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(lastX - event.getX()) >= offset||
                        Math.abs(lastY - event.getY() )>= offset){
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (playStatusImageView.getVisibility() == View.GONE){
                    showOperation(true);
                    handler.postDelayed(showOperationRunnable,Operation_Show_Time);
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == playStatusImageView){
            switch (playState){
                case Constant.Status_Play:
                    videoPlayerManager.pausePlayer();
                    break;
                case Constant.Status_Pause:
                    videoPlayerManager.resumePlayer();
                    break;
            }
        }else if (v == maxVideoImageView){

        }
    }
}
