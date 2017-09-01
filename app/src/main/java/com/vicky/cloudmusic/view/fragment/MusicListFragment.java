package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.viewmodel.MusicListVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:   vicky
 * Description:
 * Date:
 */
public class MusicListFragment extends BaseFragment<MusicListFragment, MusicListVM> {

    @Bind(R.id.tv_local_music_count)
    TextView tvLocalMusicCount;
    @Bind(R.id.tv_latey_music_count)
    TextView tvLateyMusicCount;
    @Bind(R.id.tv_down_music_count)
    TextView tvDownMusicCount;

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_musiclist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Nullable
    @Override
    public Class<MusicListVM> getViewModelClass() {
        return MusicListVM.class;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        switch (event.what) {
            case MessageEvent.ID_REFRESH_DOWN_LIST_MUSIC:
                List<MusicBean>  musicBeanList = CacheManager.getImstance().getMusicList();
                break;
        }
    }

}
