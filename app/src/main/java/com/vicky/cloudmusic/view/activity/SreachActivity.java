package com.vicky.cloudmusic.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.vicky.cloudmusic.event.MessageEvent;
import com.vicky.cloudmusic.view.activity.base.BaseActivity;
import com.vicky.cloudmusic.view.adapter.SreachAdapter;
import com.vicky.cloudmusic.view.adapter.SreachHistroyAdapter;
import com.vicky.cloudmusic.viewmodel.SreachVM;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class SreachActivity extends BaseActivity<SreachActivity, SreachVM> implements IView, View.OnClickListener, OnMenuItemClickListener {

    @Bind(R.id.lv_listview)
    ListView mListview;
    @Bind(R.id.et_key_word)
    EditText etKeyWord;
    @Bind(R.id.rl_sreach_toolbar)
    RelativeLayout rlSreachToolbar;
    @Bind(R.id.lv_histroy_listview)
    ListView lvHistroyListview;
    @Bind(R.id.rl_sreach_type)
    RelativeLayout rlSreachType;
    @Bind(R.id.iv_sreach_type)
    ImageView ivSreachType;

    private SreachAdapter mAdapter;
    private SreachHistroyAdapter mHistroyAdapter;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment menuDialogFragment;

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

        fragmentManager = getSupportFragmentManager();

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

    public void setSreachKeyWord(String keyWord) {
        etKeyWord.setText(keyWord);
    }

    public void setData(List<SreachBean> data) {
        mAdapter.setDatas(data);
    }

    public void setHistroyData(List<String> data) {
        mHistroyAdapter.setDatas(data);
    }

    public void setSreachType(MenuParams menuParams) {
        menuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        menuDialogFragment.setItemClickListener(this);
    }

    public void showSreachOrHistroy(boolean isSreach) {
        if (isSreach) {
            mListview.setVisibility(View.VISIBLE);
            lvHistroyListview.setVisibility(View.GONE);
        } else {
            mListview.setVisibility(View.GONE);
            lvHistroyListview.setVisibility(View.VISIBLE);
        }
    }

    public void goPlay(SreachBean sreachBean) {
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_MUSIC).Object1(sreachBean.cloudType).Object2(sreachBean.id).Object3(true));
        readyGo(PlayActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.ID_RESPONSE_PLAYING_INFO_MUSIC:
                setBottomMusic(event);
                break;
        }
    }

    @OnClick({R.id.rl_sreach_type})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sreach_type:
                menuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {
        getViewModel().sreachType = position;
        switch (position) {
            case Constant.CloudType_WANGYI:
                ivSreachType.setImageResource(R.drawable.wangyi);
                break;
            case Constant.CloudType_QQ:
                ivSreachType.setImageResource(R.drawable.qq);
                break;
        }
    }


}
