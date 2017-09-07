package com.vicky.cloudmusic.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vicky.android.baselib.adapter.core.OnItemChildClickListener;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.bean.PlayingMusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.adapter.DownMusicAdapter;
import com.vicky.cloudmusic.viewmodel.DownMusicVM;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class DownMusicActivity extends BaseActivity<DownMusicActivity, DownMusicVM> implements IView, View.OnClickListener {

    @Bind(R.id.lv_listview)
    ListView mListview;

    private DownMusicAdapter mAdapter;

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_downmusic;
    }

    @Override
    public Class<DownMusicVM> getViewModelClass() {
        return DownMusicVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setStatusBar();
        mAdapter = new DownMusicAdapter(this);
        /**
         * 对item的child添加点击事件
         */
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.rl_delete) {
                    getViewModel().delete(position);
                }
            }
        });
        /**
         * Add clickevent for the item
         */
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setDatas(CacheManager.getImstance().getDownMusicList());
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:
                refresh();
                setBottomMusic(event);
                break;
        }
    }

    @OnClick({R.id.rl_clear})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear:
                getViewModel().deleteAll();
                break;
        }
    }

}
