package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;

import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.view.activity.DownMusicActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.cloudmusic.view.view.DialogEx;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class DownMusicVM extends AbstractViewModel<DownMusicActivity> {

    public void select(final int position){

        if (position <0 || position >= CacheManager.getImstance().getDownMusicList().size())
            return;

        if (getView() != null){
            final DialogEx dialogEx = new DialogEx(getView());
            dialogEx.setText("确定要删除该音乐？","取消","确定");
            dialogEx.setDialogButtonCallback(new DialogEx.OnDialogButtonCallback() {
                @Override
                public void leftClick() {
                    dialogEx.dismiss();
                }

                @Override
                public void rightClick() {
                    MusicBean musicBean = CacheManager.getImstance().getDownMusicList().get(position);
                    CacheManager.getImstance().deleteDownMusic(musicBean);
                    getView().refresh();
                    dialogEx.dismiss();
                }
            });
            dialogEx.show();
        }

    }

}
