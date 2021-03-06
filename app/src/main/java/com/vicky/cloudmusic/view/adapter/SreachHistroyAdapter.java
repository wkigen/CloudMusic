package com.vicky.cloudmusic.view.adapter;

import android.content.Context;

import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;
import com.vicky.cloudmusic.R;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class SreachHistroyAdapter extends AdapterViewAdapter<String> {

    public SreachHistroyAdapter(Context context) {
        super(context, R.layout.item_histroy_sreach);
    }

    @Override
    protected void fillData(ViewHolderHelper viewHolderHelper, int position, String name) {
         viewHolderHelper.setText(R.id.tv_histroy,name);
    }

    /**
     * 制定item子元素点击事件/长按事件
     */
    @Override
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
         viewHolderHelper.setItemChildClickListener(R.id.im_delete);
    }
}