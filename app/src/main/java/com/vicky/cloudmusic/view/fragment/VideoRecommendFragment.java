package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vicky.android.baselib.adapter.core.OnItemChildClickListener;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MVBean;

import com.vicky.cloudmusic.follow.ViewFollow;
import com.vicky.cloudmusic.utils.ViewHelper;
import com.vicky.cloudmusic.view.adapter.MVAdapter;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.view.view.ListVideoView;
import com.vicky.cloudmusic.view.view.VideoView;
import com.vicky.cloudmusic.viewmodel.VideoRecommendVM;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.player_messages.Pause;
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
    ListVideoView currPlayerView;

    ViewFollow viewFollow;
    VideoView videoView;

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
                if (childView.getId() == R.id.vv_play) {
                    ListVideoView listVideoView = (ListVideoView) childView;
                    currPlayerView = listVideoView;
                    switch (mAdapter.playOrPause(position)) {
                        case Constant.Status_Play:
                            getViewModel().play(position);
                            viewFollow.followView(videoView).attachView(listVideoView).attach().show();
                            break;
                        case Constant.Status_Resume:
                            videoPlayerManager.resumePlayer();
                            viewFollow.show();
                            return;
                        case Constant.Status_Pause:
                            videoPlayerManager.pausePlayer();
                            viewFollow.show();
                            return;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        lvListview.setAdapter(mAdapter);
        lvListview.setOnScrollListener(this);

        videoView = new VideoView(getActivity());
        videoView.setVideoPlayerManager(videoPlayerManager);
        viewFollow = new ViewFollow(getActivity());

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

    @Override
    protected void onFirstUserVisible() {
        viewFollow.attachScrollView(lvListview);
    }

    @Override
    protected void onUserVisible() {
        videoPlayerManager.pausePlayer();
    }

    @Override
    protected void onUserInvisible() {
        videoPlayerManager.pausePlayer();
    }

    public void setData( List<MVBean> data){
        mAdapter.setDatas(data);
    }

    public void playMV(String url){
        videoPlayerManager.playNewVideo(null, videoView.getVideoPlayerView(),url);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (currPlayerView != null){
            if (!ViewHelper.isHalfVisibility(currPlayerView,0.5f)){
                mAdapter.pause();
                currPlayerView = null;
                videoPlayerManager.pausePlayer();
                videoView.setStatus(Constant.Status_Pause);
                viewFollow.hide();
            }
        }
    }
}
