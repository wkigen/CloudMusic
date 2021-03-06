package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.QQSreachBean;
import com.vicky.cloudmusic.bean.SreachBean;
import com.vicky.cloudmusic.bean.WYSreachBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.QQCallback;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.SreachActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class SreachVM extends AbstractViewModel<SreachActivity> {

    public int sreachType = Constant.CloudType_WANGYI;

    //网易参数
    public String limit = "60";
    public String offest ="0";
    public String type = "1";// 搜索单曲(1)，歌手(100)，专辑(10)，歌单(1000)，用户(1002)

    public List<SreachBean> sreachBeans = new LinkedList<>();

    @Override
    public void onBindView(@NonNull SreachActivity view) {
        super.onBindView(view);
        List<String> sreachHistroyBeanList = CacheManager.getImstance().getSreachHistroy();

        if (getView() != null){
            getView().setHistroyData(sreachHistroyBeanList);

            settMenuParams();
        }
    }

    public void settMenuParams(){
        List<MenuObject> menuObjectList = new ArrayList<>();
        MenuObject wangyi = new MenuObject();
        wangyi.setResource(R.drawable.wangyi);

        MenuObject qq = new MenuObject();
        qq.setResource(R.drawable.qq);

        menuObjectList.add(wangyi);
        menuObjectList.add(qq);

        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getView().getResources().getDimension(R.dimen.dimen_50dp));
        menuParams.setMenuObjects(menuObjectList);
        menuParams.setClosableOutside(true);
        menuParams.setFitsSystemWindow(true);
        menuParams.setClipToPadding(false);

       if (getView() != null)
           getView().setSreachType(menuParams);
    }

    public void sreach(String keyWord){

        addHistroy(keyWord);

        switch (sreachType){
            case Constant.CloudType_WANGYI:
                sreachWY(keyWord);
                break;
            case Constant.CloudType_QQ:
                sreachQQ(keyWord);
                break;
        }
    }

    private void sreachWY(String keyWord){
        Net.getWyApi().getApi().sreach(keyWord, limit, offest, "true", type).showProgress(getView()).execute(new WYCallback() {

            @Override
            public void onRequestSuccess(String result) {
                WYSreachBean wyBean = JSON.parseObject(result, WYSreachBean.class);
                if (wyBean != null) {
                    sreachBeans.clear();
                    for (WYSreachBean.ResultBean.SongsBean song : wyBean.getResult().getSongs()) {
                        SreachBean sreachBean = new SreachBean();
                        sreachBean.cloudType = Constant.CloudType_WANGYI;
                        sreachBean.name = song.getName();
                        sreachBean.id = song.getId();
                        StringBuilder art = new StringBuilder();
                        for (WYSreachBean.ResultBean.SongsBean.ArBean arBean : song.getAr()) {
                            art.append(arBean.getName() + " ");
                        }
                        sreachBean.album = song.getAl().getName();
                        sreachBean.artist = art.toString().substring(0, art.length() - 1);
                        sreachBeans.add(sreachBean);
                    }
                    if (getView() != null) {
                        getView().showSreachOrHistroy(true);
                        getView().setData(sreachBeans);
                    }
                }
            }
        });
    }

    private void sreachQQ(String keyWord){
        Net.getQqApi().getApi().sreach("50",keyWord,"0","1","1","0","json","utf-8","utf-8","0","jqminiframe.json","0","1","0","sizer.newclient.next_song")
                .showProgress(getView()).execute(new QQCallback() {
            @Override
            public void onRequestSuccess(String result) {
                QQSreachBean qqSreachBean = JSON.parseObject(result, QQSreachBean.class);
                if (qqSreachBean != null) {
                    sreachBeans.clear();
                    for (QQSreachBean.DataBean.SongBean.ListBean song : qqSreachBean.getData().getSong().getList()) {
                        String[] songInfo = song.getF().split("\\|");
                        if (songInfo.length < 6)
                            continue;
                        SreachBean sreachBean = new SreachBean();
                        sreachBean.cloudType = Constant.CloudType_QQ;
                        sreachBean.name = songInfo[1];
                        //sreachBean.id = songInfo[0];      //歌曲id
                        sreachBean.id = songInfo[20];       //歌曲mid  拿着这个去取歌曲详情
                        sreachBean.album = songInfo[5];
                        sreachBean.artist = songInfo[3];
                        sreachBeans.add(sreachBean);
                    }
                }
                if (getView() != null) {
                    getView().showSreachOrHistroy(true);
                    getView().setData(sreachBeans);
                }
            }
                });
    }

    public void selectHistroy(int position){
        List<String> sreachHistroyBeanList = CacheManager.getImstance().getSreachHistroy();
        if (position < 0  || position >= sreachHistroyBeanList.size())
            return;
        String keyWord = sreachHistroyBeanList.get(position);
        if (getView() != null)
            getView().setSreachKeyWord(keyWord);
        sreach(keyWord);
    }

    public void addHistroy(String keyWord){
        List<String> sreachHistroyBeanList = CacheManager.getImstance().getSreachHistroy();
        for (String str : sreachHistroyBeanList){
            if (str.equals(keyWord)) {
                sreachHistroyBeanList.remove(str);
                break;
            }
        }
        if (sreachHistroyBeanList.size() >= Constant.MaxHistroy){
            sreachHistroyBeanList.remove(sreachHistroyBeanList.size()-1);
        }
        sreachHistroyBeanList.add(0,keyWord);
        if (getView() != null)
            getView().setHistroyData(sreachHistroyBeanList);

        CacheManager.getImstance().saveSreachHistroy();
    }

    public void deleteHistroy(int position){
        List<String> sreachHistroyBeanList = CacheManager.getImstance().getSreachHistroy();
        if (position < 0  || position >= sreachHistroyBeanList.size())
            return;

        sreachHistroyBeanList.remove(position);
        if (getView() != null)
            getView().setHistroyData(sreachHistroyBeanList);
    }

    public void selectSong(int position){
        if (position < 0 || position >= sreachBeans.size())
            return;

        SreachBean sreachBean = sreachBeans.get(position);

        if (getView()!=null){
            getView().goPlay(sreachBean);
        }
    }
}
