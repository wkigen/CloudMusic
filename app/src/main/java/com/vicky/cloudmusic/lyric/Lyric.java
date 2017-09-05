package com.vicky.cloudmusic.lyric;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vicky on 2017/9/5.
 */
public class Lyric {


    private List<LyricRow> lyricRowList = new ArrayList<>();

    public Lyric(String lyc){
        parse(lyc);
    }

    public List<LyricRow> getLyricRowList() {
        return lyricRowList;
    }

    public void parse(String lyc){
        if (TextUtils.isEmpty(lyc))
            return;

        StringReader reader = new StringReader(lyc);
        BufferedReader br = new BufferedReader(reader);

       try {
           String line  = br.readLine();
            while (!TextUtils.isEmpty(line)){

                List<LyricRow> lrcRows = LyricRow.getLyricRows(line);
                if(lrcRows != null && lrcRows.size() > 0){
                    for(LyricRow row : lrcRows){
                        lyricRowList.add(row);
                    }
                }

                line  = br.readLine();
            }
           if (lyricRowList.size() > 0){
               Collections.sort(lyricRowList);
           }
       }catch (Exception e){

       }
    }
}
