package com.vicky.cloudmusic.view.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.android.baselib.adapter.core.OnItemChildClickListener;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.refreshlayout.RefreshHolderUtil;
import com.vicky.android.baselib.refreshlayout.RefreshLayout;
import com.vicky.android.baselib.utils.MMCQ;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.adapter.MusicPlayListAdapter;
import com.vicky.cloudmusic.viewmodel.MusicPlayListVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class MusicPlayListActivity extends BaseActivity<MusicPlayListActivity, MusicPlayListVM> implements IView {

    public final static String MUSICPLAYLISTID = "music_play_list";

    @Bind(R.id.rl_refresh)
    RefreshLayout mRefreshLayout;
    @Bind(R.id.lv_listview)
    ListView mListview;

    RelativeLayout rlHeadMain;
    TextView tvPlaylistName;
    TextView tvCreatorName;
    ImageView ivPlaylistPic;
    @Bind(R.id.tv_music_name)
    TextView tvMusicName;
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    @Bind(R.id.rl_toolbar)
    RelativeLayout rlToolbar;

    private MusicPlayListAdapter mAdapter;


    private int beginVisible = -1;

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_musicplaylist;
    }

    @Override
    public Class<MusicPlayListVM> getViewModelClass() {
        return MusicPlayListVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mAdapter = new MusicPlayListAdapter(this);
        /**
         * 对item的child添加点击事件
         */
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {

            }
        });
        /**
         * Add clickevent for the item
         */
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicBean musicBean = getViewModel().getMusic(position - 1);
                if (musicBean != null) {
                    readyGo(PlayActivity.class);
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_MUSIC).Object1(musicBean.cloudType).Object2(musicBean.readId).Object3(true));
                }
            }
        });

        mRefreshLayout.setRefreshViewHolder(RefreshHolderUtil.getHolder(this));
        mRefreshLayout.setDelegate(new RefreshLayout.RefreshLayoutDelegate() {
            @Override
            public void onRefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
                getViewModel().getPlayListDetail(true);
            }

            @Override
            public boolean onRefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
                getViewModel().getPlayListDetail(false);
                return false;
            }
        });
        mRefreshLayout.setPullDownRefreshEnable(false);

        mListview.setAdapter(mAdapter);

        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (beginVisible <= 0 )
                    beginVisible = mListview.getLastVisiblePosition();
                if ( beginVisible > 0 &&(firstVisibleItem + visibleItemCount > beginVisible + 3 )) {
                    if (getViewModel().pictureColor != null){
                        int[] dominantColor = getViewModel().pictureColor.get(0);
                        rlToolbar.setBackgroundColor(Color.rgb(dominantColor[0], dominantColor[1], dominantColor[2]));
                        tvMusicName.setText(getViewModel().playlistName);
                    }
                }else {
                    rlToolbar.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    tvMusicName.setText(R.string.music_play_list);
                }
            }
        });

        addHead();
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
        getViewModel().playlistId = extras.getString(MUSICPLAYLISTID, "");
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
    }

    public void addHead() {
        View headView = getLayoutInflater().inflate(R.layout.item_music_play_list_head, null);
        rlHeadMain = (RelativeLayout) headView.findViewById(R.id.rl_mian);
        tvPlaylistName = (TextView) headView.findViewById(R.id.tv_playlist_name);
        tvCreatorName = (TextView) headView.findViewById(R.id.tv_author);
        ivPlaylistPic = (ImageView) headView.findViewById(R.id.tv_playlist_picture);
        mListview.addHeaderView(headView);
    }

    public void setHead() {
        tvPlaylistName.setText(getViewModel().playlistName);
        tvCreatorName.setText(getViewModel().playlistAuthor);
        tvArtist.setText(getViewModel().playDes);
        Net.imageLoader(this, getViewModel().playlistPicture, new Net.ImageLoaderCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                ivPlaylistPic.setImageBitmap(bitmap);
                BitmapTask task = new BitmapTask();
                task.execute(bitmap);
            }
        });
    }

    public void addData(List<MusicBean> musicBeanList) {
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.endLoadingMore();
    }

    public void setData(List<MusicBean> musicBeanList) {
        mAdapter.setDatas(musicBeanList);
        mRefreshLayout.endRefreshing();
    }


    private class BitmapTask extends AsyncTask<Bitmap, Object, List<int[]>> {

        @Override
        protected List<int[]> doInBackground(Bitmap... params) {
            List<int[]> result = new ArrayList<>();
            if (params[0] != null) {
                try {
                    result = MMCQ.compute(params[0], 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<int[]> rgbs) {
            getViewModel().pictureColor = rgbs;
            int[] dominantColor = rgbs.get(0);
            if (rlHeadMain != null)
                rlHeadMain.setBackgroundColor(Color.rgb(dominantColor[0], dominantColor[1], dominantColor[2]));
        }

    }

}
