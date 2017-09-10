package com.vicky.cloudmusic.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vicky.cloudmusic.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class AudioBecomingNoisyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ID_REQUEST_PLAY_OR_PAUSE_MUSIC));
    }
}
