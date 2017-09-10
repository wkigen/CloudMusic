package com.vicky.cloudmusic.callback;

/**
 * Created by vicky on 2017/9/9.
 */
public interface IMoveCallback {
    void onMove(float offestX,float offestY,int width,int height);
    void onUp(float offestX,float offestY,int width,int height);
}
