package com.vicky.cloudmusic.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by vicky on 2017/9/1.
 */
public class FragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> fragments;


    public FragmentAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public T getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
