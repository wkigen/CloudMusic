package com.vicky.cloudmusic.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vicky.android.baselib.adapter.core.OnItemChildClickListener;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.LocalMusicBean;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.adapter.LocalMusicAdapter;
import com.vicky.cloudmusic.viewmodel.LocalMusicVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class LocalMusicActivity extends BaseActivity<LocalMusicActivity, LocalMusicVM> implements IView,View.OnClickListener {

    @Bind(R.id.lv_listview)
    ListView mListview;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_music_name)
    TextView tvMusicName;

    private LocalMusicAdapter mAdapter;

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_localmusic;
    }

    @Override
    public Class<LocalMusicVM> getViewModelClass() {
        return LocalMusicVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setStatusBar();
        mAdapter = new LocalMusicAdapter(this);
        /**
         * 对item的child添加点击事件
         */
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                /**if (childView.getId() == R.id.tv_item_normal_title) {
                 }*/
            }
        });
        /**
         * Add clickevent for the item
         */
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getViewModel().playMusic(position);
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
        mAdapter.setDatas(getViewModel().getLocalMusic());
    }

    public void refresh(){
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:
                boolean isPlaying = (boolean) event.object2;
                final MusicBean musicBean = (MusicBean) event.object1;
                if (musicBean!=null )
                    getViewModel().setPlayIco(musicBean.cloudType,musicBean.readId);
                setBottomMusic(isPlaying,musicBean);
                break;
        }
    }
}
