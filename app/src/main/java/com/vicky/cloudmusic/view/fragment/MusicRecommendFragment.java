package com.vicky.cloudmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.WYPersonalizedPlaylistBean;
import com.vicky.cloudmusic.net.Net;
import com.vicky.cloudmusic.view.activity.FMActivity;
import com.vicky.cloudmusic.view.activity.MusicPlayListActivity;
import com.vicky.cloudmusic.viewmodel.MusicRecommendVM;
import com.vicky.cloudmusic.view.fragment.base.BaseFragment;
import com.vicky.cloudmusic.viewmodel.RecommendVM;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Author:   vicky
 * Description:
 * Date:
 */
public class MusicRecommendFragment extends BaseFragment<MusicRecommendFragment, MusicRecommendVM> implements View.OnClickListener{

    @Bind(R.id.iv_fm)
    ImageView ivFm;
    @Bind(R.id.iv_day)
    ImageView ivDay;
    @Bind(R.id.tv_day)
    TextView tvDay;
    @Bind(R.id.iv_hot)
    ImageView ivHot;
    @Bind(R.id.ll_recommend_list)
    LinearLayout llRecommendList;

    List<PersonalizedPlaylistLayout> personalizedPlaylistLayouts = new ArrayList<>();
    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_musicrecommend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvDay.setText(getViewModel().getDay()+"");
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }


    @Nullable
    @Override
    public Class<MusicRecommendVM> getViewModelClass() {
        return MusicRecommendVM.class;
    }


    @OnClick({R.id.iv_fm,R.id.iv_hot})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_fm:
                readyGo(FMActivity.class);
                break;
            case R.id.iv_hot:
                Bundle bundle = new Bundle();
                bundle.putString(MusicPlayListActivity.MUSICPLAYLISTID, Constant.WY_ToplistHot);
                readyGo(MusicPlayListActivity.class,bundle);
                break;
        }
    }

    public void addPersonalizedPlaylistLayout(){
        if (personalizedPlaylistLayouts.size() > 0)
            return;
        for (int jj = 0;jj<2;jj++){
            View threeView = getActivity().getLayoutInflater().inflate(R.layout.item_three_music_list,null);
            for (int ii = 1;ii<= 3;ii++){
                int mainResId = getResources().getIdentifier("ll_main_"+ii, "id" , getActivity().getPackageName());
                int picResId = getResources().getIdentifier("iv_picture_"+ii, "id" , getActivity().getPackageName());
                int nameResId = getResources().getIdentifier("tv_name_"+ii, "id" , getActivity().getPackageName());
                LinearLayout llMain = (LinearLayout)threeView.findViewById(mainResId);
                ImageView imageView = (ImageView)threeView.findViewById(picResId);
                TextView textView = (TextView)threeView.findViewById(nameResId);

                PersonalizedPlaylistLayout playlistLayout = new PersonalizedPlaylistLayout();
                playlistLayout.ll_main = llMain;
                playlistLayout.tv_name = textView;
                playlistLayout.iv_pic = imageView;

                int position = ii - 1 + jj*3;
                llMain.setTag(position);
                llMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int)v.getTag();
                        String id = getViewModel().getPlayListId(position);
                        Bundle bundle = new Bundle();
                        bundle.putString(MusicPlayListActivity.MUSICPLAYLISTID,id);
                        readyGo(MusicPlayListActivity.class,bundle);
                    }
                });

                personalizedPlaylistLayouts.add(playlistLayout);
            }
            llRecommendList.addView(threeView);
        }
    }

    public void setPersonalizedPlaylist(WYPersonalizedPlaylistBean personalizedPlaylist){
        addPersonalizedPlaylistLayout();
        int count = 0;
        for (WYPersonalizedPlaylistBean.ResultBean playlistBean : personalizedPlaylist.getResult()){
            if (count >= personalizedPlaylistLayouts.size())
                break;
            personalizedPlaylistLayouts.get(count).tv_name.setText(playlistBean.getName());
            Net.imageLoader(getActivity(), playlistBean.getPicUrl(), personalizedPlaylistLayouts.get(count).iv_pic, R.drawable.img_one_bi_one, R.drawable.img_one_bi_one);
            count++;
        }
    }

    class PersonalizedPlaylistLayout{
        public LinearLayout ll_main;
        public TextView tv_name;
        public ImageView iv_pic;
    }
}
