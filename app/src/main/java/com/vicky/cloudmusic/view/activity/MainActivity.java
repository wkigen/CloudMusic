package com.vicky.cloudmusic.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.utils.FileUtils;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.service.MusicService;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.adapter.FragmentAdapter;
import com.vicky.cloudmusic.viewmodel.MainVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class MainActivity extends BaseActivity<MainActivity, MainVM> implements IView, View.OnClickListener {


    @Bind(R.id.vp_main)
    ViewPager vpMain;
    @Bind(R.id.dr_main)
    DrawerLayout drMain;
    @Bind(R.id.iv_music_list)
    ImageView ivMusicList;
    @Bind(R.id.iv_music_recommend)
    ImageView ivMusicRecommend;

    FragmentAdapter fragmentAdapter;
    FragmentManager fragmentManager;

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Class<MainVM> getViewModelClass() {
        return MainVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setStatusBar();

        fragmentManager = getSupportFragmentManager();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CacheManager.getImstance().getDownMusicList();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_RESPONSE_DOWN_LIST_MUSIC));
            }
        }).start();


    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    public void setFragments(List<Fragment> fragmentList) {
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragmentList);
        vpMain.setAdapter(fragmentAdapter);
        vpMain.setOffscreenPageLimit(3);
        vpMain.setCurrentItem(0);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ivMusicList.setImageResource(R.drawable.actionbar_music_selected);
                    ivMusicRecommend.setImageResource(R.drawable.actionbar_discover_prs);
                } else {
                    ivMusicList.setImageResource(R.drawable.actionbar_music_prs);
                    ivMusicRecommend.setImageResource(R.drawable.actionbar_discover_selected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.iv_sreach, R.id.iv_music_list, R.id.rl_menu, R.id.rl_clear_cache,
            R.id.iv_music_recommend})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sreach:
                readyGo(SreachActivity.class);
                break;
            case R.id.iv_music_list:
                vpMain.setCurrentItem(0);
                break;
            case R.id.iv_music_recommend:
                vpMain.setCurrentItem(1);
                break;
            case R.id.rl_menu:
                drMain.openDrawer(Gravity.LEFT);
                break;
            case R.id.rl_clear_cache:
                String path = CacheManager.getImstance().getDirPath();
                FileUtils.deleteAllFiles(path + "/" + Constant.temp_dir_name);
                drMain.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(MainActivity.this, MusicService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, MusicService.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:
                boolean isPlaying = (boolean) event.object2;
                final MusicBean musicBean = (MusicBean) event.object1;
                setBottomMusic(isPlaying, musicBean);
                break;
        }
    }


}
