package com.vicky.cloudmusic.lyric;

import com.vicky.cloudmusic.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicky on 2017/9/5.
 */
public class LyricRow implements Comparable<LyricRow>{

    public String strTime;
    public String content;
    public long time;

    public LyricRow(String strTime,String content){
        this.strTime = strTime;
        this.content = content;
        calTime();
    }

    private void calTime(){
        strTime = strTime.replace('.', ':');
        String[] times = strTime.split(":");
        time = Integer.valueOf(times[0]) * Constant.g_Minute +Integer.valueOf(times[1]) * Constant.g_Second +Integer.valueOf(times[2]) ;//毫秒
    }

    @Override
    public int compareTo(LyricRow o) {
        return (int)(this.time - o.time);
    }

    public static List<LyricRow> getLyricRows(String lyr){
        try{
            //判断前后的 【 】
            if (lyr.indexOf("[") == 0 &&(lyr.indexOf("]") == 9 || lyr.indexOf("]") == 10 ) ){
                String times = lyr.substring(0,lyr.lastIndexOf("]"));
                String content = lyr.substring(lyr.lastIndexOf("]")+1,lyr.length());

                String[] allTime = times.split("]");
                List<LyricRow> lyricRowList = new ArrayList<LyricRow>();
                for(String temp : allTime){
                    if(temp.trim().length() == 0){
                        continue;
                    }
                    LyricRow lrcRow = new LyricRow(temp.substring(1,temp.length()), content);
                    lyricRowList.add(lrcRow);
                }
                return lyricRowList;
            }
        }catch (Exception e){

        }
        return null;
    }


}
