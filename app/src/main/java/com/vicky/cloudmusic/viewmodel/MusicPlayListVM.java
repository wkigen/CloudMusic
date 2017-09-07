package com.vicky.cloudmusic.viewmodel;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.bean.MusicBean;
import com.vicky.cloudmusic.bean.WYMusicPlayListBean;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.net.callback.WYCallback;
import com.vicky.cloudmusic.view.activity.MusicPlayListActivity;
import com.vicky.android.baselib.mvvm.AbstractViewModel;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Author:  vicky
 * Description:
 * Date:
 ************************************************************/
public class MusicPlayListVM extends AbstractViewModel<MusicPlayListActivity> {

    public String playlistId;
    public String playlistName;
    public String playlistAuthor;
    public String playlistPicture;
    public String playlistBlurPicture;
    public List<MusicBean> musicBeans = new ArrayList<>();

    public void onBindView(@NonNull MusicPlayListActivity view) {
        super.onBindView(view);

        getPlayListDetail();
    }

    public void getPlayListDetail(){
        Net.getWyApi().getApi().detailPlaylist(playlistId,"0","true","20","").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                WYMusicPlayListBean wyMusicPlayListBean = JSON.parseObject(result, WYMusicPlayListBean.class);
                if (wyMusicPlayListBean != null) {
                    playlistName = wyMusicPlayListBean.getPlaylist().getName();
                    playlistAuthor = wyMusicPlayListBean.getPlaylist().getCreator().getNickname();
                    playlistPicture = wyMusicPlayListBean.getPlaylist().getCoverImgUrl();
                    playlistBlurPicture = wyMusicPlayListBean.getPlaylist().getCoverImgId_str();

                    for (WYMusicPlayListBean.PlaylistBean.TracksBean tracksBean : wyMusicPlayListBean.getPlaylist().getTracks()) {
                        MusicBean musicBean = new MusicBean();
                        musicBean.cloudType = Constant.CloudType_WANGYI;
                        musicBean.name = tracksBean.getName();
                        musicBean.readId = tracksBean.getId();
                        musicBean.picture = tracksBean.getAl().getPicUrl();
                        StringBuilder art = new StringBuilder();
                        for (WYMusicPlayListBean.PlaylistBean.TracksBean.ArBean arBean : tracksBean.getAr()) {
                            art.append(arBean.getName() + " ");
                        }
                        musicBean.artist = art.toString();
                        musicBeans.add(musicBean);
                    }

                    if (getView() != null) {
                        getView().setHead();
                        getView().setData(musicBeans);
                    }
                }
            }
        });
    }

    public MusicBean getMusic(int position){
        if (position < 0 || position >= musicBeans.size())
            return null;
        return musicBeans.get(position);
    }

}
