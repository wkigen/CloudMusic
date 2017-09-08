package com.vicky.cloudmusic.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by vicky on 2017/9/8.
 */
public class QQMusicDetailBean {
    /**
     * code : 0
     * data : [{"action":{"alert":100002,"icons":8060,"msgdown":0,"msgfav":0,"msgid":14,"msgpay":6,"msgshare":0,"switch":636675},"album":{"id":362,"mid":"0022QXnD2oNOPY","name":"大热","subtitle":"","time_public":"2000-07-01","title":"大热"},"bpm":0,"data_type":0,"file":{"media_mid":"0017wX4U32bRW4","size_128mp3":3592547,"size_192aac":5162117,"size_192ogg":4826028,"size_24aac":699915,"size_320mp3":8981086,"size_48aac":1374502,"size_96aac":2654073,"size_ape":20842852,"size_dts":0,"size_flac":21350839,"size_try":654523,"try_begin":88800,"try_end":129637},"fnote":4009,"genre":1,"id":7132726,"index_album":12,"index_cd":0,"interval":224,"isonly":1,"ksong":{"id":10349,"mid":"004HD1qz3cWdG3"},"label":"4611686153727246337","language":0,"mid":"0017wX4U32bRW4","modify_stamp":0,"mv":{"id":247266,"name":"","title":"","vid":"g0013ms03tl"},"name":"我","pay":{"pay_down":1,"pay_month":1,"pay_play":0,"pay_status":0,"price_album":0,"price_track":200,"time_free":0},"singer":[{"id":87,"mid":"0001v4XU1cZxPy","name":"张国荣","title":"张国荣","type":0,"uin":0}],"status":0,"subtitle":"","time_public":"2000-07-01","title":"我 (国语)","trace":"","type":0,"url":"http://stream2.qqmusic.qq.com/19132726.wma","version":9,"volume":{"gain":-7.686,"lra":16.796,"peak":0.815}}]
     * url : {"7132726":"ws.stream.qqmusic.qq.com/C1000017wX4U32bRW4.m4a?fromtag=38"}
     * url1 : {}
     * extra_data : []
     * joox : 0
     * joox_login : 0
     */

    private int code;
    /**
     * 7132726 : ws.stream.qqmusic.qq.com/C1000017wX4U32bRW4.m4a?fromtag=38
     */

    private UrlBean url;
    private int joox;
    private int joox_login;
    /**
     * action : {"alert":100002,"icons":8060,"msgdown":0,"msgfav":0,"msgid":14,"msgpay":6,"msgshare":0,"switch":636675}
     * album : {"id":362,"mid":"0022QXnD2oNOPY","name":"大热","subtitle":"","time_public":"2000-07-01","title":"大热"}
     * bpm : 0
     * data_type : 0
     * file : {"media_mid":"0017wX4U32bRW4","size_128mp3":3592547,"size_192aac":5162117,"size_192ogg":4826028,"size_24aac":699915,"size_320mp3":8981086,"size_48aac":1374502,"size_96aac":2654073,"size_ape":20842852,"size_dts":0,"size_flac":21350839,"size_try":654523,"try_begin":88800,"try_end":129637}
     * fnote : 4009
     * genre : 1
     * id : 7132726
     * index_album : 12
     * index_cd : 0
     * interval : 224
     * isonly : 1
     * ksong : {"id":10349,"mid":"004HD1qz3cWdG3"}
     * label : 4611686153727246337
     * language : 0
     * mid : 0017wX4U32bRW4
     * modify_stamp : 0
     * mv : {"id":247266,"name":"","title":"","vid":"g0013ms03tl"}
     * name : 我
     * pay : {"pay_down":1,"pay_month":1,"pay_play":0,"pay_status":0,"price_album":0,"price_track":200,"time_free":0}
     * singer : [{"id":87,"mid":"0001v4XU1cZxPy","name":"张国荣","title":"张国荣","type":0,"uin":0}]
     * status : 0
     * subtitle :
     * time_public : 2000-07-01
     * title : 我 (国语)
     * trace :
     * type : 0
     * url : http://stream2.qqmusic.qq.com/19132726.wma
     * version : 9
     * volume : {"gain":-7.686,"lra":16.796,"peak":0.815}
     */

    private List<DataBean> data;
    private List<?> extra_data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UrlBean getUrl() {
        return url;
    }

    public void setUrl(UrlBean url) {
        this.url = url;
    }

    public int getJoox() {
        return joox;
    }

    public void setJoox(int joox) {
        this.joox = joox;
    }

    public int getJoox_login() {
        return joox_login;
    }

    public void setJoox_login(int joox_login) {
        this.joox_login = joox_login;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<?> getExtra_data() {
        return extra_data;
    }

    public void setExtra_data(List<?> extra_data) {
        this.extra_data = extra_data;
    }

    public static class UrlBean {

    }

    public static class DataBean {
        /**
         * alert : 100002
         * icons : 8060
         * msgdown : 0
         * msgfav : 0
         * msgid : 14
         * msgpay : 6
         * msgshare : 0
         * switch : 636675
         */

        private ActionBean action;
        /**
         * id : 362
         * mid : 0022QXnD2oNOPY
         * name : 大热
         * subtitle :
         * time_public : 2000-07-01
         * title : 大热
         */

        private AlbumBean album;
        private int bpm;
        private int data_type;
        /**
         * media_mid : 0017wX4U32bRW4
         * size_128mp3 : 3592547
         * size_192aac : 5162117
         * size_192ogg : 4826028
         * size_24aac : 699915
         * size_320mp3 : 8981086
         * size_48aac : 1374502
         * size_96aac : 2654073
         * size_ape : 20842852
         * size_dts : 0
         * size_flac : 21350839
         * size_try : 654523
         * try_begin : 88800
         * try_end : 129637
         */

        private FileBean file;
        private int fnote;
        private int genre;
        private int id;
        private int index_album;
        private int index_cd;
        private int interval;
        private int isonly;
        /**
         * id : 10349
         * mid : 004HD1qz3cWdG3
         */

        private KsongBean ksong;
        private String label;
        private int language;
        private String mid;
        private int modify_stamp;
        /**
         * id : 247266
         * name :
         * title :
         * vid : g0013ms03tl
         */

        private MvBean mv;
        private String name;
        /**
         * pay_down : 1
         * pay_month : 1
         * pay_play : 0
         * pay_status : 0
         * price_album : 0
         * price_track : 200
         * time_free : 0
         */

        private PayBean pay;
        private int status;
        private String subtitle;
        private String time_public;
        private String title;
        private String trace;
        private int type;
        private String url;
        private int version;
        /**
         * gain : -7.686
         * lra : 16.796
         * peak : 0.815
         */

        private VolumeBean volume;
        /**
         * id : 87
         * mid : 0001v4XU1cZxPy
         * name : 张国荣
         * title : 张国荣
         * type : 0
         * uin : 0
         */

        private List<SingerBean> singer;

        public ActionBean getAction() {
            return action;
        }

        public void setAction(ActionBean action) {
            this.action = action;
        }

        public AlbumBean getAlbum() {
            return album;
        }

        public void setAlbum(AlbumBean album) {
            this.album = album;
        }

        public int getBpm() {
            return bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }

        public int getData_type() {
            return data_type;
        }

        public void setData_type(int data_type) {
            this.data_type = data_type;
        }

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public int getFnote() {
            return fnote;
        }

        public void setFnote(int fnote) {
            this.fnote = fnote;
        }

        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIndex_album() {
            return index_album;
        }

        public void setIndex_album(int index_album) {
            this.index_album = index_album;
        }

        public int getIndex_cd() {
            return index_cd;
        }

        public void setIndex_cd(int index_cd) {
            this.index_cd = index_cd;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getIsonly() {
            return isonly;
        }

        public void setIsonly(int isonly) {
            this.isonly = isonly;
        }

        public KsongBean getKsong() {
            return ksong;
        }

        public void setKsong(KsongBean ksong) {
            this.ksong = ksong;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getLanguage() {
            return language;
        }

        public void setLanguage(int language) {
            this.language = language;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public int getModify_stamp() {
            return modify_stamp;
        }

        public void setModify_stamp(int modify_stamp) {
            this.modify_stamp = modify_stamp;
        }

        public MvBean getMv() {
            return mv;
        }

        public void setMv(MvBean mv) {
            this.mv = mv;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PayBean getPay() {
            return pay;
        }

        public void setPay(PayBean pay) {
            this.pay = pay;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTime_public() {
            return time_public;
        }

        public void setTime_public(String time_public) {
            this.time_public = time_public;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTrace() {
            return trace;
        }

        public void setTrace(String trace) {
            this.trace = trace;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public VolumeBean getVolume() {
            return volume;
        }

        public void setVolume(VolumeBean volume) {
            this.volume = volume;
        }

        public List<SingerBean> getSinger() {
            return singer;
        }

        public void setSinger(List<SingerBean> singer) {
            this.singer = singer;
        }

        public static class ActionBean {
            private int alert;
            private int icons;
            private int msgdown;
            private int msgfav;
            private int msgid;
            private int msgpay;
            private int msgshare;


            public int getAlert() {
                return alert;
            }

            public void setAlert(int alert) {
                this.alert = alert;
            }

            public int getIcons() {
                return icons;
            }

            public void setIcons(int icons) {
                this.icons = icons;
            }

            public int getMsgdown() {
                return msgdown;
            }

            public void setMsgdown(int msgdown) {
                this.msgdown = msgdown;
            }

            public int getMsgfav() {
                return msgfav;
            }

            public void setMsgfav(int msgfav) {
                this.msgfav = msgfav;
            }

            public int getMsgid() {
                return msgid;
            }

            public void setMsgid(int msgid) {
                this.msgid = msgid;
            }

            public int getMsgpay() {
                return msgpay;
            }

            public void setMsgpay(int msgpay) {
                this.msgpay = msgpay;
            }

            public int getMsgshare() {
                return msgshare;
            }

            public void setMsgshare(int msgshare) {
                this.msgshare = msgshare;
            }
        }

        public static class AlbumBean {
            private int id;
            private String mid;
            private String name;
            private String subtitle;
            private String time_public;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getTime_public() {
                return time_public;
            }

            public void setTime_public(String time_public) {
                this.time_public = time_public;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class FileBean {
            private String media_mid;
            private int size_128mp3;
            private int size_192aac;
            private int size_192ogg;
            private int size_24aac;
            private int size_320mp3;
            private int size_48aac;
            private int size_96aac;
            private int size_ape;
            private int size_dts;
            private int size_flac;
            private int size_try;
            private int try_begin;
            private int try_end;

            public String getMedia_mid() {
                return media_mid;
            }

            public void setMedia_mid(String media_mid) {
                this.media_mid = media_mid;
            }

            public int getSize_128mp3() {
                return size_128mp3;
            }

            public void setSize_128mp3(int size_128mp3) {
                this.size_128mp3 = size_128mp3;
            }

            public int getSize_192aac() {
                return size_192aac;
            }

            public void setSize_192aac(int size_192aac) {
                this.size_192aac = size_192aac;
            }

            public int getSize_192ogg() {
                return size_192ogg;
            }

            public void setSize_192ogg(int size_192ogg) {
                this.size_192ogg = size_192ogg;
            }

            public int getSize_24aac() {
                return size_24aac;
            }

            public void setSize_24aac(int size_24aac) {
                this.size_24aac = size_24aac;
            }

            public int getSize_320mp3() {
                return size_320mp3;
            }

            public void setSize_320mp3(int size_320mp3) {
                this.size_320mp3 = size_320mp3;
            }

            public int getSize_48aac() {
                return size_48aac;
            }

            public void setSize_48aac(int size_48aac) {
                this.size_48aac = size_48aac;
            }

            public int getSize_96aac() {
                return size_96aac;
            }

            public void setSize_96aac(int size_96aac) {
                this.size_96aac = size_96aac;
            }

            public int getSize_ape() {
                return size_ape;
            }

            public void setSize_ape(int size_ape) {
                this.size_ape = size_ape;
            }

            public int getSize_dts() {
                return size_dts;
            }

            public void setSize_dts(int size_dts) {
                this.size_dts = size_dts;
            }

            public int getSize_flac() {
                return size_flac;
            }

            public void setSize_flac(int size_flac) {
                this.size_flac = size_flac;
            }

            public int getSize_try() {
                return size_try;
            }

            public void setSize_try(int size_try) {
                this.size_try = size_try;
            }

            public int getTry_begin() {
                return try_begin;
            }

            public void setTry_begin(int try_begin) {
                this.try_begin = try_begin;
            }

            public int getTry_end() {
                return try_end;
            }

            public void setTry_end(int try_end) {
                this.try_end = try_end;
            }
        }

        public static class KsongBean {
            private int id;
            private String mid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }
        }

        public static class MvBean {
            private int id;
            private String name;
            private String title;
            private String vid;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }
        }

        public static class PayBean {
            private int pay_down;
            private int pay_month;
            private int pay_play;
            private int pay_status;
            private int price_album;
            private int price_track;
            private int time_free;

            public int getPay_down() {
                return pay_down;
            }

            public void setPay_down(int pay_down) {
                this.pay_down = pay_down;
            }

            public int getPay_month() {
                return pay_month;
            }

            public void setPay_month(int pay_month) {
                this.pay_month = pay_month;
            }

            public int getPay_play() {
                return pay_play;
            }

            public void setPay_play(int pay_play) {
                this.pay_play = pay_play;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public int getPrice_album() {
                return price_album;
            }

            public void setPrice_album(int price_album) {
                this.price_album = price_album;
            }

            public int getPrice_track() {
                return price_track;
            }

            public void setPrice_track(int price_track) {
                this.price_track = price_track;
            }

            public int getTime_free() {
                return time_free;
            }

            public void setTime_free(int time_free) {
                this.time_free = time_free;
            }
        }

        public static class VolumeBean {
            private double gain;
            private double lra;
            private double peak;

            public double getGain() {
                return gain;
            }

            public void setGain(double gain) {
                this.gain = gain;
            }

            public double getLra() {
                return lra;
            }

            public void setLra(double lra) {
                this.lra = lra;
            }

            public double getPeak() {
                return peak;
            }

            public void setPeak(double peak) {
                this.peak = peak;
            }
        }

        public static class SingerBean {
            private int id;
            private String mid;
            private String name;
            private String title;
            private int type;
            private int uin;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUin() {
                return uin;
            }

            public void setUin(int uin) {
                this.uin = uin;
            }
        }
    }
}
