package com.vicky.cloudmusic.utils;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * Created by vicky on 2017/9/12.
 */
public class ViewHelper {


    public static boolean isHalfVisibility(View view,float needPre){
        int height = view.getHeight();

        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);

        if (rect.top < 0)
            return false;
        else if (rect.top == 0)
            return true;

        int offestY = height - rect.top;

        if (offestY == 0)
            return false;

        float pre = (float)offestY / (float)height;
        Log.e("fffff",pre+"");
        return pre > needPre ? true : false;
    }
}
