package com.vicky.cloudmusic.utils;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by vicky on 2017/9/12.
 */
public class ViewHelper {


    public static boolean isHalfVisibility(View view,float needPre){
        int height = view.getHeight();

        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);

        int offestY = height - rect.top;

        if (offestY == 0)
            return false;

        int pre = offestY / height;

        return pre > needPre ? true : false;
    }
}
