package com.vicky.cloudmusic.media;


import android.os.Build;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.vicky.cloudmusic.Application;
import com.vicky.cloudmusic.bean.MusicBean;

/**
 * Created by vicky on 2017/9/10.
 */
public class MediaSessionManager {
    private static final String TAG = MediaSessionManager.class.getSimpleName();

    private static final long MEDIA_SESSION_ACTIONS = PlaybackStateCompat.ACTION_PLAY
            | PlaybackStateCompat.ACTION_PAUSE
            | PlaybackStateCompat.ACTION_PLAY_PAUSE
            | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            | PlaybackStateCompat.ACTION_STOP
            | PlaybackStateCompat.ACTION_SEEK_TO;

    private static MediaSessionManager mInstance;

    private MediaSessionCompat mediaSession;

    private MediaSessionManager(){

    }

    public void init(){
        mediaSession = new MediaSessionCompat(Application.getInstance(), TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS | MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS);
        mediaSession.setCallback(mediaSessionCallback);
        mediaSession.setActive(true);
    }

    public static MediaSessionManager getInstance(){
        if (mInstance == null){
            synchronized (MediaSessionManager.class){
                if (mInstance == null)
                    mInstance = new MediaSessionManager();
            }
        }
        return mInstance;
    }

    public void setPlaybackState(int isPlay,long postion){
        mediaSession.setPlaybackState(
                new PlaybackStateCompat.Builder()
                        .setActions(MEDIA_SESSION_ACTIONS)
                        .setState(isPlay, postion, 1)
                        .build());
    }

    public void setMetadata(MusicBean musicBean,long duration) {
        if (musicBean == null) {
            mediaSession.setMetadata(null);
            return;
        }

        MediaMetadataCompat.Builder metaData = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE,musicBean.name)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST,musicBean.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, musicBean.album)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST,musicBean.artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,duration);
               // .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, CoverLoader.getInstance().loadThumbnail(music));


        mediaSession.setMetadata(metaData.build());
    }

    private MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onSkipToNext() {

        }

        @Override
        public void onSkipToPrevious() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onSeekTo(long pos) {

        }
    };

}
