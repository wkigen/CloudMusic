package com.vicky.cloudmusic.view.adapter;

import android.content.Context;

import com.vicky.cloudmusic.R;
import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;
import com.vicky.cloudmusic.bean.MusicBean;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class DownMusicAdapter extends AdapterViewAdapter<MusicBean> {

    public DownMusicAdapter(Context context) {
        super(context, R.layout.item_down_music);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, MusicBean model) {
        viewHolderHelper.setText(R.id.tv_name, model.name);
        viewHolderHelper.setText(R.id.tv_des, model.artist+"-"+model.album);
    }

    /**
     * 制定item子元素点击事件/长按事件
     */
    @Override
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
         viewHolderHelper.setItemChildClickListener(R.id.rl_delete);
    }
}