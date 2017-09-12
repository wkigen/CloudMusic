package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.cloudmusic.bean.MVBean;
import com.vicky.cloudmusic.bean.WYMVFirstBean;
import com.vicky.cloudmusic.bean.WYMVDetailBean;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.fragment.VideoRecommendFragment;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class VideoRecommendVM extends AbstractViewModel<VideoRecommendFragment> {

    List<MVBean> mvBeanList = new ArrayList<>();

    @Override
    public void onBindView(@NonNull VideoRecommendFragment view) {
        super.onBindView(view);

        getMV();
    }

    public void getMV(){
        mvBeanList.clear();
        Net.getWyApi().getApi().mvFirst("0","true","50","").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                WYMVFirstBean wymvFirstBean = JSON.parseObject(result, WYMVFirstBean.class);
                for (WYMVFirstBean.DataBean mvBean : wymvFirstBean.getData()) {
                    MVBean bean = new MVBean();
                    bean.id = mvBean.getId();
                    bean.name = mvBean.getName();
                    bean.picture = mvBean.getCover();
                    mvBeanList.add(bean);
                    if (getView() != null)
                        getView().setData(mvBeanList);
                }
            }
        });
    }

    private String getBestUrl(Map<String,String> urls){
//        int beatBr = 0;
//        String url = "";
//        for (Map.Entry<String,String> entry:urls.entrySet()){
//            String key = entry.getKey();
//            if (Integer.parseInt(key) > beatBr)
//                beatBr = Integer.parseInt(key);
//        }
//        if (beatBr > 0)
//            url = urls.get(beatBr+"");
//
//        return url;

        return urls.get("240");
    }

    public void play(int position,final VideoPlayerView videoPlayerView){
        if (position < 0 || position >= mvBeanList.size())
            return;
        final MVBean mvBean = mvBeanList.get(position);
        if (TextUtils.isEmpty(mvBean.url)){
            Net.getWyApi().getApi().mvDetail(mvBean.id).execute(new WYCallback() {
                @Override
                public void onRequestSuccess(String result) {
                    WYMVDetailBean wymvDetailBean = JSON.parseObject(result,WYMVDetailBean.class);
                    mvBean.url = getBestUrl(wymvDetailBean.getData().getBrs());
                    if (getView() != null)
                        getView().playMV(mvBean.url,videoPlayerView);
                }
            });
        }else {
            if (getView() != null)
                getView().playMV(mvBean.url,videoPlayerView);
        }
    }

}
