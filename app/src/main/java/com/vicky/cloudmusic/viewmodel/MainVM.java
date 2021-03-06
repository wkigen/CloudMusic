package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.runtimepermission.PermissionsManager;
import com.vicky.android.baselib.runtimepermission.PermissionsResultAction;
import com.vicky.cloudmusic.bean.WYMVFirstBean;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.MainActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.cloudmusic.view.fragment.MusicListFragment;
import com.vicky.cloudmusic.view.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;


/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MainVM extends AbstractViewModel<MainActivity> {

    List<Fragment> fragmentList = new ArrayList<>();
    @Override
    public void onBindView(@NonNull MainActivity view) {
        super.onBindView(view);
        init();
        //getPermission();
    }

    private void init(){
        fragmentList.add(new MusicListFragment());
        fragmentList.add(new RecommendFragment());
        if (getView() != null)
            getView().setFragments(fragmentList);
    }

    public void getPermission() {
        if (getView() != null){
            PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(getView(), new PermissionsResultAction() {
                @Override
                public void onGranted() {
                }

                @Override
                public void onDenied(String s) {
                    getPermission();
                }
            });
        }

    }

}
