package com.vicky.cloudmusic.view.activity.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.mvvm.base.BaseLibActivity;
import com.vicky.android.baselib.utils.StringUtils;
import com.vicky.android.baselib.widget.PopupWindowEx;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.PlayMusicStausBean;
import com.vicky.cloudmusic.bean.PlayingMusicBean;
import com.vicky.cloudmusic.cache.BitmapManager;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.FMActivity;
import com.vicky.cloudmusic.view.activity.PlayActivity;
import com.vicky.cloudmusic.view.adapter.BottomMusicPlayListAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by vicky on 2017/4/2.
 */
public abstract class BaseActivity<T extends IView, RM extends AbstractViewModel<T>> extends BaseLibActivity<T, RM> {

    @Nullable
    @Bind(R.id.rl_bottom_main_play)
    RelativeLayout rlBottomMainPlay;
    @Nullable
    @Bind(R.id.iv_bottom_music_picture)
    ImageView ivBottomMusicPicture;
    @Nullable
    @Bind(R.id.iv_bottom_music_play)
    ImageView ivBottomMusicPlay;
    @Nullable
    @Bind(R.id.tv_bottom_music_name)
    TextView tvBottomMusicName;
    @Nullable
    @Bind(R.id.tv_bottom_music_des)
    TextView tvBottomMusicDes;
    @Nullable
    @Bind(R.id.iv_bottom_music_list)
    ImageView ivBottomMusicList;
    @Nullable
    @Bind(R.id.iv_back)
    ImageView ivBack;

    protected BottomMusicPlayListAdapter bottomMusicPlayListAdapter;
    protected PopupWindowEx  popupWindowEx;
    protected View popupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (rlBottomMainPlay !=null){
            rlBottomMainPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goPlayActivity();
                }
            });
        }
        if (ivBottomMusicPlay != null){
            ivBottomMusicPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_OR_PAUSE_MUSIC));
                }
            });
        }
        if (ivBottomMusicList != null){
            ivBottomMusicList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMusicList();
                }
            });
        }
        if (ivBack != null){
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAYING_INFO_MUSIC));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void setBottomMusic(MessageEvent event){
        boolean isRefresh = (boolean) event.object1;
        boolean isPlaying = (boolean) event.object2;
        if (isPlaying){
            ivBottomMusicPlay.setImageResource(R.drawable.playbar_btn_pause);
        }else {
            ivBottomMusicPlay.setImageResource(R.drawable.playbar_btn_play);
        }
        PlayingMusicBean playingMusicBean = isRefresh ?CacheManager.getImstance().getPlayingMusicBean() : null;
        if (playingMusicBean != null && playingMusicBean.musicBean!= null){
            if (playingMusicBean.musicBean.picture != null &&!StringUtils.hasHttpPrefix(playingMusicBean.musicBean.picture)){
                if (ivBottomMusicPicture != null)
                    ivBottomMusicPicture.setImageBitmap(BitmapManager.getInstance().getBitmap(playingMusicBean.musicBean.picture));
            }
            if (tvBottomMusicName != null)
                tvBottomMusicName.setText(playingMusicBean.musicBean.name);
            if (tvBottomMusicDes != null)
                tvBottomMusicDes.setText(playingMusicBean.musicBean.artist);
            if (bottomMusicPlayListAdapter != null)
                bottomMusicPlayListAdapter.setDatas(CacheManager.getImstance().getPlayMusicList());
        }
    }

    protected void showMusicList(){
        if (popupWindowEx == null){
            popupView = getLayoutInflater().inflate(R.layout.popup_muisc_list, null);
            popupWindowEx = new PopupWindowEx((Activity)context, popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindowEx.setFocusable(true);

            ListView listView = (ListView)popupView.findViewById(R.id.ls_music_list);
            bottomMusicPlayListAdapter = new BottomMusicPlayListAdapter(context);
            listView.setAdapter(bottomMusicPlayListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position < 0 || position >= CacheManager.getImstance().getPlayMusicList().size())
                        return;
                    PlayMusicStausBean playMusicStausBean = CacheManager.getImstance().getPlayMusicList().get(position);
                    if (!playMusicStausBean.isSelect)
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_MUSIC).
                                Object1(playMusicStausBean.musicBean.cloudType).Object2(playMusicStausBean.musicBean.readId).Object3(true));

                    popupWindowEx.dismiss();
                }
            });

            RelativeLayout rlMain = (RelativeLayout)popupView.findViewById(R.id.rl_main);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindowEx.dismiss();
                }
            });

            popupWindowEx.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindowEx.setOutsideTouchable(true);
            popupWindowEx.setAnimationStyle(R.style.popwin_anim_style_bottom);
        }

        popupWindowEx.showAtLocation(popupView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        bottomMusicPlayListAdapter.setDatas(CacheManager.getImstance().getPlayMusicList());
    }

    public void goPlayActivity(){
        PlayingMusicBean playingMusicBean = CacheManager.getImstance().getPlayingMusicBean();
        if (playingMusicBean.musicType == Constant.NormalMusic){
            readyGo(PlayActivity.class);
        }else if (playingMusicBean.musicType ==Constant.FMMusic){
            readyGo(FMActivity.class);
        }
    }

}
