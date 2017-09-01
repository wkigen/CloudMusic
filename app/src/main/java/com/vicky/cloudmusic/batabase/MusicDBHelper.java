package com.vicky.cloudmusic.batabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vicky on 2017/9/1.
 */
public class MusicDBHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "cloud_music.db";
    private static final int VERSION = 1;

    public static final int MID = 0;
    public static final int NAME = 1;
    public static final int CLOUDTYPE = 2;
    public static final int READID = 3;
    public static final int ARTIST = 4;
    public static final int PICTURE = 5;
    public static final int PATH = 6;
    public static final int LYR = 7;

    public MusicDBHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //主键  名称 云类型 真实ID（对应云类型） 图片路劲 资源路劲(本地的) 歌词路劲
        db.execSQL("CREATE TABLE music (mId INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,cloud_type int,realId TEXT,artist TEXT,picture TEXT,path TEXT,lyr TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAll(String where, String orderBy) {//返回表中的数据,where是调用时候传进来的搜索内容,orderby是设置中传进来的列表排序类型
        StringBuilder buf=new StringBuilder("SELECT mId, name, cloud_type, realId,artist, picture, path,lyr FROM music");

        if (where!=null) {
            buf.append(" WHERE ");
            buf.append(where);
        }

        if (orderBy!=null) {
            buf.append(" ORDER BY ");
            buf.append(orderBy);
        }

        return(getReadableDatabase().rawQuery(buf.toString(), null));
    }

    public void insert(String name, int cloud_type, String realId,String artist, String picture, String path,String lyr) {
        ContentValues cv=new ContentValues();

        cv.put("name", name);
        cv.put("cloud_type", cloud_type);
        cv.put("realId", realId);
        cv.put("artist", artist);
        cv.put("picture", picture);
        cv.put("path", path);
        cv.put("lyr", lyr);

        getWritableDatabase().insert("music", "name", cv);
    }

    public void update(String mId, String name, int cloud_type, String realId, String artist, String picture, String path,String lyr) {
        ContentValues cv = new ContentValues();
        String[] args= { mId };

        cv.put("name", name);
        cv.put("cloud_type", cloud_type);
        cv.put("realId", realId);
        cv.put("artist", artist);
        cv.put("picture", picture);
        cv.put("path", path);
        cv.put("lyr", lyr);

        getWritableDatabase().update("music", cv, "mId = ?", args);
    }

    public void delete(int mId){
        String[] args= { mId+"" };
        getWritableDatabase().delete("music","mId = ?",args);
    }


}
