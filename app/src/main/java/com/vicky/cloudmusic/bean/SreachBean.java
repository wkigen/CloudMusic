package com.vicky.cloudmusic.bean;

import com.vicky.cloudmusic.Constant;

import java.io.Serializable;

/**
 * Author:  vicky
 * Description:
 * Date:
 */
public class SreachBean implements Serializable {

    private int  cloudType;
    private String name;
    private String des;
    private String id;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getCloudType() {
        return cloudType;
    }

    public void setCloudType(int cloudType) {
        this.cloudType = cloudType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
