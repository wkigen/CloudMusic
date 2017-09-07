package com.vicky.cloudmusic.bean;

import java.util.List;

/**
 * Created by vicky on 2017/9/7.
 */
public class WYPersonalizedPlaylistBean {
    /**
     * hasTaste : false
     * code : 200
     * category : 0
     * result : [{"id":883826039,"type":0,"name":"他（她）们不但演的好，唱的也不赖","copywriter":"编辑推荐：带你盘点那些被演绎事业耽误的\u201c业余歌手们\u201d","picUrl":"http://p1.music.126.net/V94bgA3xtXVMwoPOyLH0hQ==/19060034067829712.jpg","canDislike":false,"playCount":1672119.4,"trackCount":67,"highQuality":false,"alg":"featured"},{"id":889811694,"type":0,"name":"试一试，你也是不经意的词人","copywriter":"编辑推荐：字里行间总是情","picUrl":"http://p1.music.126.net/Ot9eySVXmJaGLa2CdDjcrQ==/109951163020209690.jpg","canDislike":false,"playCount":624064,"trackCount":35,"highQuality":false,"alg":"featured"},{"id":872185834,"type":0,"name":"「中国嘻哈」着迷说唱","copywriter":"根据你喜欢的标签 华语 推荐","picUrl":"http://p1.music.126.net/Emkrxwcq39Ql6PDRn0_8LA==/109951162996595662.jpg","canDislike":true,"playCount":1446221.1,"trackCount":123,"highQuality":false,"alg":"taste"},{"id":893153237,"type":0,"name":"粤语男声：我爱你依旧如初，不曾改变。","copywriter":"热门推荐","picUrl":"http://p1.music.126.net/Xd6h-xOoPj2yTUuQXOhyCQ==/18612532836990988.jpg","canDislike":true,"playCount":3538347,"trackCount":100,"highQuality":false,"alg":"cityLevel_unknow"},{"id":882960422,"type":0,"name":"那些在你酩酊大醉后嘶吼出来的心事","copywriter":"热门推荐","picUrl":"http://p1.music.126.net/cCtqHG_Ju9ELfmGBeZNUTQ==/109951163003990470.jpg","canDislike":true,"playCount":10886165,"trackCount":100,"highQuality":false,"alg":"cityLevel_unknow"},{"id":898072985,"type":0,"name":"【 Taylor Swift 丨才女的进化史 〗","copywriter":"热门推荐","picUrl":"http://p1.music.126.net/uGo7SvGDrHPM9Tra0XOO8w==/19145795974784949.jpg","canDislike":true,"playCount":3461160.5,"trackCount":47,"highQuality":false,"alg":"cityLevel_unknow"}]
     */

    private boolean hasTaste;
    private int code;
    private int category;
    /**
     * id : 883826039
     * type : 0
     * name : 他（她）们不但演的好，唱的也不赖
     * copywriter : 编辑推荐：带你盘点那些被演绎事业耽误的“业余歌手们”
     * picUrl : http://p1.music.126.net/V94bgA3xtXVMwoPOyLH0hQ==/19060034067829712.jpg
     * canDislike : false
     * playCount : 1672119.4
     * trackCount : 67
     * highQuality : false
     * alg : featured
     */

    private List<ResultBean> result;

    public boolean isHasTaste() {
        return hasTaste;
    }

    public void setHasTaste(boolean hasTaste) {
        this.hasTaste = hasTaste;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String id;
        private int type;
        private String name;
        private String copywriter;
        private String picUrl;
        private boolean canDislike;
        private double playCount;
        private int trackCount;
        private boolean highQuality;
        private String alg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCopywriter() {
            return copywriter;
        }

        public void setCopywriter(String copywriter) {
            this.copywriter = copywriter;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public boolean isCanDislike() {
            return canDislike;
        }

        public void setCanDislike(boolean canDislike) {
            this.canDislike = canDislike;
        }

        public double getPlayCount() {
            return playCount;
        }

        public void setPlayCount(double playCount) {
            this.playCount = playCount;
        }

        public int getTrackCount() {
            return trackCount;
        }

        public void setTrackCount(int trackCount) {
            this.trackCount = trackCount;
        }

        public boolean isHighQuality() {
            return highQuality;
        }

        public void setHighQuality(boolean highQuality) {
            this.highQuality = highQuality;
        }

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }
    }
}
