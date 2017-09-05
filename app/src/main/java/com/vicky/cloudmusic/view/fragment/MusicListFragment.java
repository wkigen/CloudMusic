package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.DownMusicActivity;
import com.vicky.cloudmusic.view.activity.LocalMusicActivity;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.viewmodel.MusicListVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:   vicky
 * Description:
 * Date:
 */
public class MusicListFragment extends BaseFragment<MusicListFragment, MusicListVM> implements View.OnClickListener {

    @Bind(R.id.tv_local_music_count)
    TextView tvLocalMusicCount;
    @Bind(R.id.tv_latey_music_count)
    TextView tvLateyMusicCount;
    @Bind(R.id.tv_down_music_count)
    TextView tvDownMusicCount;
    @Bind(R.id.iv_ico_local)
    ImageView ivIcoLocal;
    @Bind(R.id.tv_local)
    TextView tvLocal;
    @Bind(R.id.rl_local_music)
    RelativeLayout rlLocalMusic;
    @Bind(R.id.iv_ico_lately)
    ImageView ivIcoLately;
    @Bind(R.id.tv_latey)
    TextView tvLatey;
    @Bind(R.id.rl_lately_play)
    RelativeLayout rlLatelyPlay;
    @Bind(R.id.iv_ico_down)
    ImageView ivIcoDown;
    @Bind(R.id.tv_down)
    TextView tvDown;
    @Bind(R.id.rl_down_music)
    RelativeLayout rlDownMusic;

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_musiclist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        List<MusicBean> musicBeanList = CacheManager.getImstance().getDownMusicList();
        tvLocalMusicCount.setText("(" + musicBeanList.size() + ")");
        tvDownMusicCount.setText("(" + musicBeanList.size() + ")");
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

    @Override
    public void onResume(){
        super.onResume();
        List<MusicBean> musicBeanList = CacheManager.getImstance().getDownMusicList();
        tvLocalMusicCount.setText("(" + musicBeanList.size() + ")");
        tvDownMusicCount.setText("(" + musicBeanList.size() + ")");
    }

    @Nullable
    @Override
    public Class<MusicListVM> getViewModelClass() {
        return MusicListVM.class;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
    }

    @OnClick({R.id.rl_local_music,R.id.rl_down_music})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_local_music:
                readyGo(LocalMusicActivity.class);
                break;
            case R.id.rl_down_music:
                readyGo(DownMusicActivity.class);
                break;
        }
    }

}
