package com.vicky.cloudmusic.bean;

/**
 * Created by vicky on 2017/8/31.
 */
public class MusicBean implements Cloneable {

    public static final int Invalid_ID = -1;

    public int mid = Invalid_ID;    //数据库主键

    public int cloudType;
    public String name;
    public String artist;
    public String readId;
    public String album;
    public String path;
    public String picture;
    public String lyr;

    @Override
    public Object clone() throws CloneNotSupportedException {
        MusicBean copyMusicBean = (MusicBean) super.clone();
        if (name!=null)
            copyMusicBean.name = new String(name);
        if (artist!=null)
            copyMusicBean.artist = new String(artist);
        if (readId!=null)
            copyMusicBean.readId = new String(readId);
        if (album!=null)
            copyMusicBean.album = new String(album);
        if (path!=null)
            copyMusicBean.path = new String(path);
        if (picture!=null)
            copyMusicBean.picture = new String(picture);
        if (lyr!=null)
            copyMusicBean.lyr = new String(lyr);
        return copyMusicBean;
    }
}
