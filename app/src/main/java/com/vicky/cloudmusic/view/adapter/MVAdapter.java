package com.vicky.cloudmusic.view.adapter;

import android.content.Context;

import com.vicky.android.baselib.adapter.core.AdapterViewAdapter;
import com.vicky.android.baselib.adapter.core.ViewHolderHelper;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MVBean;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.view.VideoView;

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
        viewHolderHelper.setText(R.id.tv_name, model.name);
        VideoView videoView = viewHolderHelper.getView(R.id.vv_play);
        Net.imageLoader(mContext, model.picture, videoView.getBackgroundImageView(), R.drawable.img_four_bi_three, R.drawable.img_four_bi_three);

        if (lastPlayingMVBean != null ){
            if (model.id == lastPlayingMVBean.id) {
                videoView.setStatus(lastPlayingMVBean.playingStatus);
            }else {
                videoView.setStatus(Constant.Status_Stop);
            }

        }
    }

    /**
     * 制定item子元素点击事件/长按事件
     */
    @Override
    protected void setItemChildListener(ViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.vv_play);
    }

    public void pause(){
        if (lastPlayingMVBean != null){
            lastPlayingMVBean.playingStatus = Constant.Status_Pause;
        }
    }

    public int playOrPause(int position){

        if (position < 0 || position >= getCount())
            return Constant.Status_Stop;

        MVBean mvBean = getDatas().get(position);

        if (lastPlayingMVBean != null) {
            if ( lastPlayingMVBean.id == mvBean.id ) {
                if (lastPlayingMVBean.playingStatus == Constant.Status_Play) {
                    lastPlayingMVBean.playingStatus = Constant.Status_Pause;
                    return lastPlayingMVBean.playingStatus;
                } else if (lastPlayingMVBean.playingStatus == Constant.Status_Pause){
                    lastPlayingMVBean.playingStatus = Constant.Status_Resume;
                    return lastPlayingMVBean.playingStatus;
                }
            }
            lastPlayingMVBean.playingStatus = Constant.Status_Pause;
            return lastPlayingMVBean.playingStatus;
        }

        lastPlayingMVBean = mvBean;
        lastPlayingMVBean.playingStatus = Constant.Status_Play;

        return lastPlayingMVBean.playingStatus;
    }


}