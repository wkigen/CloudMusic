package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.vicky.android.baselib.adapter.core.OnItemChildClickListener;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MVBean;

import com.vicky.cloudmusic.utils.ViewHelper;
import com.vicky.cloudmusic.view.adapter.MVAdapter;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.viewmodel.VideoRecommendVM;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.util.List;

import butterknife.Bind;

/**
 * Author:   vicky
 * Description:
 * Date:
 */
public class VideoRecommendFragment extends BaseFragment<VideoRecommendFragment, VideoRecommendVM> implements AbsListView.OnScrollListener {

    @Bind(R.id.lv_listview)
    ListView lvListview;

    MVAdapter mAdapter;
    VideoPlayerView currVideoPlayerView;

    private VideoPlayerManager<MetaData> videoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {

        }
    });

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_videorecommend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mAdapter = new MVAdapter(getActivity());
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.rl_real) {
                    videoPlayerManager.stopAnyPlayback();
                } else if (childView.getId() == R.id.rl_fake) {
                    VideoPlayerView videoPlayerView = (VideoPlayerView) parent.findViewById(R.id.vpv_main);
                    mAdapter.play(position);
                    getViewModel().play(position, videoPlayerView);
                }
            }
        });

        lvListview.setAdapter(mAdapter);
        lvListview.setOnScrollListener(this);
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Nullable
    @Override
    public Class<VideoRecommendVM> getViewModelClass() {
        return VideoRecommendVM.class;
    }

    public void setData( List<MVBean> data){
        mAdapter.setDatas(data);
    }

    public void playMV(String url,VideoPlayerView videoPlayerView){
        currVideoPlayerView = videoPlayerView;
        videoPlayerManager.playNewVideo(null, videoPlayerView,url);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (currVideoPlayerView != null){
            if (!ViewHelper.isHalfVisibility(currVideoPlayerView,0.5f)){
                currVideoPlayerView = null;
                videoPlayerManager.stopAnyPlayback();
            }
        }
    }
}
