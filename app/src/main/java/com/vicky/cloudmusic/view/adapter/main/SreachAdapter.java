package com.vicky.cloudmusic.view.adapter.main;

import android.content.Context;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.main.SreachBean;
import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class SreachAdapter extends AdapterViewAdapter<SreachBean> {

    public SreachAdapter(Context context) {
        super(context, R.layout.item_sreach);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, SreachBean model) {
         viewHolderHelper.setText(R.id.tv_histroy, model.getName());
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