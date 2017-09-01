package com.vicky.cloudmusic.bean;

/**
 * Created by vicky on 2017/8/31.
 */
public class MusicBean {

    public static final int Invalid_ID = -1;

    public int mid = Invalid_ID;    //数据库主键

    public int cloudType;
    public String name;
    public String artist;
    public String readId;
    public String path;
    public String picture;
    public String lyr;

}
