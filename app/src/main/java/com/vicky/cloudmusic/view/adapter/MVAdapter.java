package com.vicky.cloudmusic.view.adapter;

import android.content.Context;
import android.view.View;

import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MVBean;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.net.Net;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class MVAdapter extends AdapterViewAdapter<MVBean> {

    private MVBean lastPlayingMVBean;

    public MVAdapter(Context context) {
        super(context, R.layout.item_mv_list);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, MVBean model) {
        Net.imageLoader(mContext,model.picture,viewHolderHelper.getIamgeView(R.id.iv_picture),R.drawable.img_one_bi_one,R.drawable.img_one_bi_one);
        viewHolderHelper.setText(R.id.tv_name, model.name);
        if (model.isPlaying) {
            viewHolderHelper.setVisibility(R.id.rl_real, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.rl_fake, View.GONE);
        }else {
            viewHolderHelper.setVisibility(R.id.rl_real, View.GONE);
            viewHolderHelper.setVisibility(R.id.rl_fake, View.VISIBLE);
        }
    }

    /**
     * 制定item子元素点击事件/长按事件
     */
    @Override
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.rl_fake);
        viewHolderHelper.setItemChildClickListener(R.id.rl_real);
    }

    public void play(int position){
        if (position < 0 || position >= getCount())
            return;

        if (lastPlayingMVBean != null)
            lastPlayingMVBean.isPlaying = false;

        lastPlayingMVBean = getDatas().get(position);
        lastPlayingMVBean.isPlaying = true;

        notifyDataSetChanged();
    }


}