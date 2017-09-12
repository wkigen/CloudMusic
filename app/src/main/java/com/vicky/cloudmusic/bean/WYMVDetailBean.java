package com.vicky.cloudmusic.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by vicky on 2017/9/12.
 */
public class WYMVDetailBean {
    /**
     * loadingPic :
     * bufferPic :
     * loadingPicFS :
     * bufferPicFS :
     * subed : false
     * data : {"id":5662098,"name":"Instant love","artistId":190319,"artistName":"俊昊","briefDesc":"TWICE成员SANA助阵2PM俊昊新曲MV！","desc":"2PM俊昊第一张迷你专辑《CANVAS》收录曲《Instant Love》MV公开！","cover":"http://p4.music.126.net/LVmKx70HMSj6QTjYCyPSSQ==/109951163023200817.jpg","coverId":109951163023200820,"playCount":3756,"subCount":58,"shareCount":13,"likeCount":25,"commentCount":60,"duration":195000,"nType":0,"publishTime":"2017-09-11","brs":{"240":"http://v4.music.126.net/20170913053329/e2cf457bf16baf0235c4d6cb7700dd53/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/af04aa4a7ce5d0c60fed39f12aed655d.mp4","480":"http://v4.music.126.net/20170913053329/cce7c05cd12cf7a3211f0ba78067d6d3/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/c9a3d9f9413f9a90402542aea76606ce.mp4","720":"http://v4.music.126.net/20170913053329/90f52bdfa9f5b350169d8e2d1970b559/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/dc7f16b5b2089bdb32344345b10afa7a.mp4","1080":"http://v4.music.126.net/20170913053329/cb1b588bec75901c6b3281bb7bc2cd59/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/fe8c73f3a5b8d60dd9f3a70ab1c434c6.mp4"},"artists":[{"id":190319,"name":"俊昊"}],"isReward":false,"commentThreadId":"R_MV_5_5662098"}
     * code : 200
     */

    private String loadingPic;
    private String bufferPic;
    private String loadingPicFS;
    private String bufferPicFS;
    private boolean subed;
    /**
     * id : 5662098
     * name : Instant love
     * artistId : 190319
     * artistName : 俊昊
     * briefDesc : TWICE成员SANA助阵2PM俊昊新曲MV！
     * desc : 2PM俊昊第一张迷你专辑《CANVAS》收录曲《Instant Love》MV公开！
     * cover : http://p4.music.126.net/LVmKx70HMSj6QTjYCyPSSQ==/109951163023200817.jpg
     * coverId : 109951163023200820
     * playCount : 3756
     * subCount : 58
     * shareCount : 13
     * likeCount : 25
     * commentCount : 60
     * duration : 195000
     * nType : 0
     * publishTime : 2017-09-11
     * brs : {"240":"http://v4.music.126.net/20170913053329/e2cf457bf16baf0235c4d6cb7700dd53/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/af04aa4a7ce5d0c60fed39f12aed655d.mp4","480":"http://v4.music.126.net/20170913053329/cce7c05cd12cf7a3211f0ba78067d6d3/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/c9a3d9f9413f9a90402542aea76606ce.mp4","720":"http://v4.music.126.net/20170913053329/90f52bdfa9f5b350169d8e2d1970b559/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/dc7f16b5b2089bdb32344345b10afa7a.mp4","1080":"http://v4.music.126.net/20170913053329/cb1b588bec75901c6b3281bb7bc2cd59/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/fe8c73f3a5b8d60dd9f3a70ab1c434c6.mp4"}
     * artists : [{"id":190319,"name":"俊昊"}]
     * isReward : false
     * commentThreadId : R_MV_5_5662098
     */

    private DataBean data;
    private int code;

    public String getLoadingPic() {
        return loadingPic;
    }

    public void setLoadingPic(String loadingPic) {
        this.loadingPic = loadingPic;
    }

    public String getBufferPic() {
        return bufferPic;
    }

    public void setBufferPic(String bufferPic) {
        this.bufferPic = bufferPic;
    }

    public String getLoadingPicFS() {
        return loadingPicFS;
    }

    public void setLoadingPicFS(String loadingPicFS) {
        this.loadingPicFS = loadingPicFS;
    }

    public String getBufferPicFS() {
        return bufferPicFS;
    }

    public void setBufferPicFS(String bufferPicFS) {
        this.bufferPicFS = bufferPicFS;
    }

    public boolean isSubed() {
        return subed;
    }

    public void setSubed(boolean subed) {
        this.subed = subed;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        private int id;
        private String name;
        private int artistId;
        private String artistName;
        private String briefDesc;
        private String desc;
        private String cover;
        private long coverId;
        private int playCount;
        private int subCount;
        private int shareCount;
        private int likeCount;
        private int commentCount;
        private int duration;
        private int nType;
        private String publishTime;
        /**
         * 240 : http://v4.music.126.net/20170913053329/e2cf457bf16baf0235c4d6cb7700dd53/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/af04aa4a7ce5d0c60fed39f12aed655d.mp4
         * 480 : http://v4.music.126.net/20170913053329/cce7c05cd12cf7a3211f0ba78067d6d3/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/c9a3d9f9413f9a90402542aea76606ce.mp4
         * 720 : http://v4.music.126.net/20170913053329/90f52bdfa9f5b350169d8e2d1970b559/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/dc7f16b5b2089bdb32344345b10afa7a.mp4
         * 1080 : http://v4.music.126.net/20170913053329/cb1b588bec75901c6b3281bb7bc2cd59/web/cloudmusic/mv/20170911050038/52e1c159-fcc7-4114-a9ab-4c7cea0c3dbe/fe8c73f3a5b8d60dd9f3a70ab1c434c6.mp4
         */

        private Map<String,String> brs;
        private boolean isReward;
        private String commentThreadId;
        /**
         * id : 190319
         * name : 俊昊
         */

        private List<ArtistsBean> artists;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getArtistId() {
            return artistId;
        }

        public void setArtistId(int artistId) {
            this.artistId = artistId;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public String getBriefDesc() {
            return briefDesc;
        }

        public void setBriefDesc(String briefDesc) {
            this.briefDesc = briefDesc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public long getCoverId() {
            return coverId;
        }

        public void setCoverId(long coverId) {
            this.coverId = coverId;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public int getSubCount() {
            return subCount;
        }

        public void setSubCount(int subCount) {
            this.subCount = subCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getNType() {
            return nType;
        }

        public void setNType(int nType) {
            this.nType = nType;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public Map<String,String> getBrs() {
            return brs;
        }

        public void setBrs(Map<String,String> brs) {
            this.brs = brs;
        }

        public boolean isIsReward() {
            return isReward;
        }

        public void setIsReward(boolean isReward) {
            this.isReward = isReward;
        }

        public String getCommentThreadId() {
            return commentThreadId;
        }

        public void setCommentThreadId(String commentThreadId) {
            this.commentThreadId = commentThreadId;
        }

        public List<ArtistsBean> getArtists() {
            return artists;
        }

        public void setArtists(List<ArtistsBean> artists) {
            this.artists = artists;
        }

        public static class ArtistsBean {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
