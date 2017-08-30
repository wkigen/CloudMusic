package com.vicky.cloudmusic.viewmodel;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.SreachBean;
import com.vicky.cloudmusic.bean.WYSreachBean;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.SreachActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class SreachVM extends AbstractViewModel<SreachActivity> {

    //对应网易
    public int limit = 10;
    public int offest = 0;
    public int type = 1;// 搜索单曲(1)，歌手(100)，专辑(10)，歌单(1000)，用户(1002)

    public List<SreachBean> sreachBeans = new LinkedList<>();

    public void sreach(String keyWord){

        addHistroy(keyWord);

        Net.getWyApi().getApi().sreach(keyWord,limit,offest,"true",type).showProgress(getView()).execute(new WYCallback() {

            @Override
            public void onRequestSuccess(String result) {
                WYSreachBean wyBean = JSON.parseObject(result,WYSreachBean.class);
                for (WYSreachBean.ResultBean.SongsBean song:wyBean.getResult().getSongs()){
                    SreachBean sreachBean = new SreachBean();
                    sreachBean.setCloudType(Constant.CloudType.WANGYI);
                    sreachBean.setName(song.getName());
                    sreachBean.setId(song.getId());
                    sreachBeans.add(sreachBean);
                }
                if (getView() != null)
                    getView().setData(sreachBeans);
            }
        });

    }

    public void addHistroy(String keyWord){
        List<String> sreachHistroyBeanList = CacheManager.getImstance().getSreachHistroy();
        if (sreachHistroyBeanList.size() >= Constant.MaxHistroy){
            sreachHistroyBeanList.remove(sreachHistroyBeanList.size()-1);
        }
        sreachHistroyBeanList.add(0,keyWord);
        if (getView() != null)
            getView().setHistroyData(sreachHistroyBeanList);
    }

    public void deleteHistroy(int position){
        List<String> sreachHistroyBeanList = CacheManager.getImstance().getSreachHistroy();
        if (position >0 && position <sreachHistroyBeanList.size()){
            sreachHistroyBeanList.remove(position);
        }
        if (getView() != null)
            getView().setHistroyData(sreachHistroyBeanList);
    }

}
