package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.view.adapter.FragmentAdapter;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.viewmodel.RecommendVM;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:   vicky
 * Description:
 * Date:
 */
public class RecommendFragment extends BaseFragment<RecommendFragment, RecommendVM> implements View.OnClickListener {

    @Bind(R.id.vp_main)
    ViewPager vpMain;

    FragmentAdapter fragmentAdapter;
    FragmentManager fragmentManager;
    @Bind(R.id.tv_music)
    TextView tvMusic;
    @Bind(R.id.rl_music)
    RelativeLayout rlMusic;
    @Bind(R.id.tv_video)
    TextView tvVideo;
    @Bind(R.id.rl_video)
    RelativeLayout rlVideo;

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragmentManager = getChildFragmentManager();
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Nullable
    @Override
    public Class<RecommendVM> getViewModelClass() {
        return RecommendVM.class;
    }


    @OnClick({R.id.rl_music,R.id.rl_video})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_music:
                vpMain.setCurrentItem(0);
                break;
            case R.id.rl_video:
                vpMain.setCurrentItem(1);
                break;
        }
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
                switch (position) {
                    case 0:
                        tvMusic.setTextColor(getResources().getColor(R.color.red_main));
                        tvVideo.setTextColor(getResources().getColor(R.color.text_color_main));
                        break;
                    case 1:
                        tvMusic.setTextColor(getResources().getColor(R.color.text_color_main));
                        tvVideo.setTextColor(getResources().getColor(R.color.red_main));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
