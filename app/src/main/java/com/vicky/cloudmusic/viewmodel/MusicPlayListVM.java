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

    public List<int[]> pictureColor;

    public String playlistId;
    public String playlistName;
    public String playlistAuthor;
    public String playlistPicture;
    public String playlistBlurPicture;
    public String playDes;
    public List<MusicBean> musicBeans = new ArrayList<>();

    public int offest = 0;
    public int total = 0;

    public void onBindView(@NonNull MusicPlayListActivity view) {
        super.onBindView(view);

        getPlayListDetail(true);
    }

    public void getPlayListDetail(final boolean isRefresh){
        if (isRefresh){
            offest = 0;
            musicBeans.clear();
        }else {
            if (offest >= total)
                return;
        }
        Net.getWyApi().getApi().detailPlaylist(playlistId,offest+"","true","1000","1000","").execute(new WYCallback() {
            @Override
            public void onRequestSuccess(String result) {
                WYMusicPlayListBean wyMusicPlayListBean = JSON.parseObject(result, WYMusicPlayListBean.class);
                if (wyMusicPlayListBean != null) {
                    playlistName = wyMusicPlayListBean.getPlaylist().getName();
                    playlistAuthor = wyMusicPlayListBean.getPlaylist().getCreator().getNickname();
                    playlistPicture = wyMusicPlayListBean.getPlaylist().getCoverImgUrl();
                    playlistBlurPicture = wyMusicPlayListBean.getPlaylist().getCoverImgId_str();
                    playDes = wyMusicPlayListBean.getPlaylist().getDescription();
                    total = wyMusicPlayListBean.getPlaylist().getTrackCount();
                    if (isRefresh)
                        offest = wyMusicPlayListBean.getPlaylist().getTracks().size();
                    else
                        offest += wyMusicPlayListBean.getPlaylist().getTracks().size();

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
                        if (isRefresh)
                            getView().setData(musicBeans);
                        else
                            getView().addData(musicBeans);
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
