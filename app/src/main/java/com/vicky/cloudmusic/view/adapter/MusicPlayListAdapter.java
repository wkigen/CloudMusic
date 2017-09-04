package com.vicky.cloudmusic.view.adapter;

import android.content.Context;
import android.view.View;

import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.bean.PlayMusicStausBean;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class MusicPlayListAdapter extends AdapterViewAdapter<PlayMusicStausBean> {

    public MusicPlayListAdapter(Context context) {
        super(context, R.layout.item_music_list_play);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position,PlayMusicStausBean model) {
        if (model.isSelect) {
            viewHolderHelper.setVisibility(R.id.iv_ico_play, View.VISIBLE);
            viewHolderHelper.setTextColor(R.id.tv_name, R.color.red_main);
            viewHolderHelper.setTextColor(R.id.tv_art,R.color.red_main);
        }else {
            viewHolderHelper.setVisibility(R.id.iv_ico_play, View.GONE);
            viewHolderHelper.setTextColor(R.id.tv_name, R.color.text_color_main);
            viewHolderHelper.setTextColor(R.id.tv_art, R.color.text_color_4);
        }
        viewHolderHelper.setText(R.id.tv_name, model.musicBean.name);
        viewHolderHelper.setText(R.id.tv_art, " - "+model.musicBean.artist);
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