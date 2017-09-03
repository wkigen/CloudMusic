package com.vicky.cloudmusic.view.adapter;

import android.content.Context;
import android.view.View;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.LocalMusicBean;
import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class LocalMusicAdapter extends AdapterViewAdapter<LocalMusicBean> {

    public LocalMusicAdapter(Context context) {
        super(context, R.layout.iten_local_misic);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, LocalMusicBean model) {
        viewHolderHelper.setText(R.id.tv_name, model.musicBean.name);
        viewHolderHelper.setText(R.id.tv_des, model.musicBean.artist + "-"+model.musicBean.album);
        if (model.isPlaying)
            viewHolderHelper.setVisibility(R.id.iv_ico_play, View.VISIBLE);
        else
            viewHolderHelper.setVisibility(R.id.iv_ico_play, View.GONE);
    }

    /**
     * 制定item子元素点击事件/长按事件
     */
    @Override
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
        /**
         viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_title);
         viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_title);*/
    }
}