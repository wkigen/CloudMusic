package com.vicky.cloudmusic.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vicky.android.baselib.adapter.core.OnItemChildClickListener;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.utils.SystemTool;
import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.bean.SreachBean;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.adapter.SreachAdapter;
import com.vicky.cloudmusic.view.adapter.SreachHistroyAdapter;
import com.vicky.cloudmusic.viewmodel.SreachVM;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class SreachActivity extends BaseActivity<SreachActivity, SreachVM> implements IView, View.OnClickListener {

    @Bind(R.id.lv_listview)
    ListView mListview;
    @Bind(R.id.et_key_word)
    EditText etKeyWord;
    @Bind(R.id.rl_sreach_toolbar)
    RelativeLayout rlSreachToolbar;
    @Bind(R.id.lv_histroy_listview)
    ListView lvHistroyListview;

    private SreachAdapter mAdapter;
    private SreachHistroyAdapter mHistroyAdapter;

    @Override
    protected int tellMeLayout() {
        return R.layout.activity_sreach;
    }

    @Override
    public Class<SreachVM> getViewModelClass() {
        return SreachVM.class;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setStatusBar();
        mAdapter = new SreachAdapter(this);
        mHistroyAdapter = new SreachHistroyAdapter(this);

        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {

            }
        });

        mHistroyAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.im_delete) {
                    getViewModel().deleteHistroy(position);
                }
            }
        });


        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getViewModel().selectSong(position);
            }
        });

        lvHistroyListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getViewModel().selectHistroy(position);
            }
        });

        mListview.setAdapter(mAdapter);
        lvHistroyListview.setAdapter(mHistroyAdapter);

        etKeyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SystemTool.hideKeyboardSafe(context);
                    if (TextUtils.isEmpty(etKeyWord.getText().toString())) {
                        Toast.makeText(SreachActivity.this, getString(R.string.input_key_word), Toast.LENGTH_SHORT);
                        return false;
                    }
                    getViewModel().sreach(etKeyWord.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    public void setSreachKeyWord(String keyWord){
        etKeyWord.setText(keyWord);
    }

    public void setData(List<SreachBean> data) {
        mAdapter.setDatas(data);
    }

    public void setHistroyData(List<String> data){
        mHistroyAdapter.setDatas(data);
    }

    public void showSreachOrHistroy(boolean isSreach){
        if (isSreach){
            mListview.setVisibility(View.VISIBLE);
            lvHistroyListview.setVisibility(View.GONE);
        }else {
            mListview.setVisibility(View.GONE);
            lvHistroyListview.setVisibility(View.VISIBLE);
        }
    }

    public void goPlay(SreachBean sreachBean){
        Bundle bundle = new Bundle();
        bundle.putInt(PlayActivity.CLOUD_TYPE, sreachBean.getCloudType());
        bundle.putString(PlayActivity.SONG_ID,sreachBean.getId());
        readyGo(PlayActivity.class, bundle);
    }

    @OnClick({R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}
