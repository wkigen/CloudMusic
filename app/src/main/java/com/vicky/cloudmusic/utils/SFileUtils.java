package com.vicky.cloudmusic.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vicky.cloudmusic.bean.WYLyricBean;
import com.vicky.cloudmusic.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by vicky on 2017/9/5.
 */
public class SFileUtils {

    public static void saveStringToFile(String str, String path){
        try{
            FileOutputStream outputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(str);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
        }catch (Exception e){
        }
    }

    public static String getStringFromFile(String path){
        try{
            FileInputStream inputStream = new FileInputStream(path);
            InputStreamReader inputReader = new InputStreamReader(inputStream );
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufferedReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            return result;
        }catch (Exception E){
            Log.e("ffff",E.getMessage());
        }
        return "";
    }
}
