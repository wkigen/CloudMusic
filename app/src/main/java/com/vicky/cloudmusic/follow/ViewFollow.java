package com.vicky.cloudmusic.follow;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by vicky on 2017/9/18.
 */
public class ViewFollow implements ViewTreeObserver.OnScrollChangedListener,ValueAnimator.AnimatorUpdateListener{

    private Activity activity;
    private View followView;
    private View attachView;
    private View attachScrollView;
    private FrameLayout frameLayout;
    private boolean isAttach;
    private int[] scrollLoacl = new int[2];

    public ViewFollow(Activity activity){
        this.activity = activity;
    }

    public ViewFollow followView(View followView){
        this.followView = followView;
        return this;
    }

    public ViewFollow attachView(View attachView){
        this.attachView = attachView;
        return this;
    }

    public ViewFollow attachScrollView(View attachScrollView){
        this.attachScrollView = attachScrollView;
        frameLayout = new FrameLayout(activity);
        ViewGroup viewGroup = (ViewGroup)activity.getWindow().getDecorView();
        FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(attachScrollView.getWidth(),attachScrollView.getHeight());

        attachScrollView.getLocationOnScreen(scrollLoacl);
        frameLayout.setX(scrollLoacl[0]);
        frameLayout.setY(scrollLoacl[1]);
        viewGroup.addView(frameLayout,frameLayoutLayoutParams);
        return this;
    }

    public ViewFollow attach(){
        if (followView != null && attachView != null && attachScrollView != null){
            if (followView.getParent() != null){
                ((ViewGroup)followView.getParent()).removeView(followView);
            }

            int[] attachScrollLoacl = new int[2];
            attachScrollView.getLocationOnScreen(attachScrollLoacl);
            FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(attachView.getWidth(),attachView.getHeight());
            frameLayout.addView(followView,params);

            attachView.getViewTreeObserver().addOnScrollChangedListener(this);

            isAttach = true;

            moveFollowViewToAttachView();
        }

        return this;
    }

    public void detach(){

        if (followView!= null && followView.getParent() != null){
            ((ViewGroup)followView.getParent()).removeView(followView);
        }

        if (attachView != null)
            attachView.getViewTreeObserver().removeOnScrollChangedListener(this);

        isAttach = false;
    }

    public void show(){
        if (!isAttach)
            return;

        if (followView!= null)
            followView.setVisibility(View.VISIBLE);
    }

    public void hide(){
        if (!isAttach)
            return;

        if (followView!= null)
            followView.setVisibility(View.GONE);
    }

    private void moveFollowViewToAttachView(){
        if (followView ==null || attachView == null || attachScrollView == null)
            return;

        int[] followLoacl = new int[2];
        int[] attachLoacl = new int[2];

        followView.getLocationOnScreen(followLoacl);
        attachView.getLocationOnScreen(attachLoacl);

        followView.setX(attachLoacl[0]);
        followView.setY(attachLoacl[1] - scrollLoacl[1]);

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }

    @Override
    public void onScrollChanged() {
        moveFollowViewToAttachView();
    }


}
