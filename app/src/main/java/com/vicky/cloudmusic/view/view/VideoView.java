package com.vicky.cloudmusic.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * Created by vicky on 2017/9/13.
 */
public class VideoView extends RelativeLayout implements MediaPlayerWrapper.MainThreadMediaPlayerListener, View.OnClickListener{

    private static final long Operation_Show_Time = 1000;

    private VideoPlayerView videoPlayerView;
    private ImageView backgroundImageView;
    private ImageView playStatusImageView;

    private boolean isDisplayOperation = false;

    private Handler handler = new Handler();

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

        videoPlayerView.addMediaPlayerListener(this);

        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        playStatusImageView.setImageResource(R.drawable.lock_btn_play);
        playStatusImageView.setOnClickListener(this);

        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        LayoutParams lp2 = new LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT);

        addView(videoPlayerView, lp);
        addView(backgroundImageView, lp);
        addView(playStatusImageView, lp2);

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
                backgroundImageView.setVisibility(VISIBLE);
                playStatusImageView.setVisibility(VISIBLE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_play);
                break;
            case Constant.Status_Resume:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                break;
            case Constant.Status_Play:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                break;
            case Constant.Status_Pause:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(VISIBLE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_play);
                break;
            case Constant.Status_Prepare:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
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
            case MotionEvent.ACTION_UP:
                if (!isDisplayOperation){
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

        }
    }
}
