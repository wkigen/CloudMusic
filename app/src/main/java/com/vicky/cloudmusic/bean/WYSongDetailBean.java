package com.vicky.cloudmusic.bean;

import java.util.List;

/**
 * Created by vicky on 2017/8/31.
 */
public class WYSongDetailBean {

    /**
     * songs : [{"name":"A","readId":5389958,"pst":0,"t":0,"ar":[{"readId":127524,"name":"Rainbow","tns":[],"alias":[]}],"alia":[],"pop":95,"st":0,"rt":"","fee":0,"v":4,"crbt":null,"cf":"","al":{"readId":528982,"name":"A","picUrl":"http://p1.music.126.net/-12f00C3qYZOtFEMKzDwrw==/756463999913844.jpg","tns":[],"picture":756463999913844},"dt":201404,"h":{"br":320000,"fid":0,"size":8068435,"vd":-3.88},"m":{"br":160000,"fid":0,"size":4043489,"vd":-3.46},"l":{"br":96000,"fid":0,"size":2433093,"vd":-3.57},"a":null,"cd":"1","no":1,"rtUrl":null,"ftype":0,"rtUrls":[],"djId":0,"copyright":2,"s_id":0,"cp":0,"rtype":0,"rurl":null,"mst":9,"mv":235235}]
     * privileges : [{"readId":5389958,"fee":0,"payed":0,"st":0,"pl":320000,"dl":320000,"sp":7,"cp":1,"subp":1,"cs":false,"maxbr":320000,"fl":320000,"toast":false,"flag":0}]
     * code : 200
     */

    private int code;
    /**
     * name : A
     * readId : 5389958
     * pst : 0
     * t : 0
     * ar : [{"readId":127524,"name":"Rainbow","tns":[],"alias":[]}]
     * alia : []
     * pop : 95.0
     * st : 0
     * rt :
     * fee : 0
     * v : 4
     * crbt : null
     * cf :
     * al : {"readId":528982,"name":"A","picUrl":"http://p1.music.126.net/-12f00C3qYZOtFEMKzDwrw==/756463999913844.jpg","tns":[],"picture":756463999913844}
     * dt : 201404
     * h : {"br":320000,"fid":0,"size":8068435,"vd":-3.88}
     * m : {"br":160000,"fid":0,"size":4043489,"vd":-3.46}
     * l : {"br":96000,"fid":0,"size":2433093,"vd":-3.57}
     * a : null
     * cd : 1
     * no : 1
     * rtUrl : null
     * ftype : 0
     * rtUrls : []
     * djId : 0
     * copyright : 2
     * s_id : 0
     * cp : 0
     * rtype : 0
     * rurl : null
     * mst : 9
     * mv : 235235
     */

    private List<SongsBean> songs;
    /**
     * readId : 5389958
     * fee : 0
     * payed : 0
     * st : 0
     * pl : 320000
     * dl : 320000
     * sp : 7
     * cp : 1
     * subp : 1
     * cs : false
     * maxbr : 320000
     * fl : 320000
     * toast : false
     * flag : 0
     */

    private List<PrivilegesBean> privileges;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SongsBean> getSongs() {
        return songs;
    }

    public void setSongs(List<SongsBean> songs) {
        this.songs = songs;
    }

    public List<PrivilegesBean> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegesBean> privileges) {
        this.privileges = privileges;
    }

    public static class SongsBean {
        private String name;
        private String id;
        private int pst;
        private int t;
        private double pop;
        private int st;
        private String rt;
        private int fee;
        private int v;
        private Object crbt;
        private String cf;
        /**
         * readId : 528982
         * name : A
         * picUrl : http://p1.music.126.net/-12f00C3qYZOtFEMKzDwrw==/756463999913844.jpg
         * tns : []
         * picture : 756463999913844
         */

        private AlBean al;
        private int dt;
        /**
         * br : 320000
         * fid : 0
         * size : 8068435
         * vd : -3.88
         */

        private HBean h;
        /**
         * br : 160000
         * fid : 0
         * size : 4043489
         * vd : -3.46
         */

        private MBean m;
        /**
         * br : 96000
         * fid : 0
         * size : 2433093
         * vd : -3.57
         */

        private LBean l;
        private Object a;
        private String cd;
        private int no;
        private Object rtUrl;
        private int ftype;
        private int djId;
        private int copyright;
        private int s_id;
        private int cp;
        private int rtype;
        private Object rurl;
        private int mst;
        private int mv;
        /**
         * readId : 127524
         * name : Rainbow
         * tns : []
         * alias : []
         */

        private List<ArBean> ar;
        private List<?> alia;
        private List<?> rtUrls;

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

        public int getPst() {
            return pst;
        }

        public void setPst(int pst) {
            this.pst = pst;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public double getPop() {
            return pop;
        }

        public void setPop(double pop) {
            this.pop = pop;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int st) {
            this.st = st;
        }

        public String getRt() {
            return rt;
        }

        public void setRt(String rt) {
            this.rt = rt;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public Object getCrbt() {
            return crbt;
        }

        public void setCrbt(Object crbt) {
            this.crbt = crbt;
        }

        public String getCf() {
            return cf;
        }

        public void setCf(String cf) {
            this.cf = cf;
        }

        public AlBean getAl() {
            return al;
        }

        public void setAl(AlBean al) {
            this.al = al;
        }

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public HBean getH() {
            return h;
        }

        public void setH(HBean h) {
            this.h = h;
        }

        public MBean getM() {
            return m;
        }

        public void setM(MBean m) {
            this.m = m;
        }

        public LBean getL() {
            return l;
        }

        public void setL(LBean l) {
            this.l = l;
        }

        public Object getA() {
            return a;
        }

        public void setA(Object a) {
            this.a = a;
        }

        public String getCd() {
            return cd;
        }

        public void setCd(String cd) {
            this.cd = cd;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public Object getRtUrl() {
            return rtUrl;
        }

        public void setRtUrl(Object rtUrl) {
            this.rtUrl = rtUrl;
        }

        public int getFtype() {
            return ftype;
        }

        public void setFtype(int ftype) {
            this.ftype = ftype;
        }

        public int getDjId() {
            return djId;
        }

        public void setDjId(int djId) {
            this.djId = djId;
        }

        public int getCopyright() {
            return copyright;
        }

        public void setCopyright(int copyright) {
            this.copyright = copyright;
        }

        public int getS_id() {
            return s_id;
        }

        public void setS_id(int s_id) {
            this.s_id = s_id;
        }

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
        }

        public int getRtype() {
            return rtype;
        }

        public void setRtype(int rtype) {
            this.rtype = rtype;
        }

        public Object getRurl() {
            return rurl;
        }

        public void setRurl(Object rurl) {
            this.rurl = rurl;
        }

        public int getMst() {
            return mst;
        }

        public void setMst(int mst) {
            this.mst = mst;
        }

        public int getMv() {
            return mv;
        }

        public void setMv(int mv) {
            this.mv = mv;
        }

        public List<ArBean> getAr() {
            return ar;
        }

        public void setAr(List<ArBean> ar) {
            this.ar = ar;
        }

        public List<?> getAlia() {
            return alia;
        }

        public void setAlia(List<?> alia) {
            this.alia = alia;
        }

        public List<?> getRtUrls() {
            return rtUrls;
        }

        public void setRtUrls(List<?> rtUrls) {
            this.rtUrls = rtUrls;
        }

        public static class AlBean {
            private int id;
            private String name;
            private String picUrl;
            private long pic;
            private List<?> tns;

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

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public long getPic() {
                return pic;
            }

            public void setPic(long pic) {
                this.pic = pic;
            }

            public List<?> getTns() {
                return tns;
            }

            public void setTns(List<?> tns) {
                this.tns = tns;
            }
        }

        public static class HBean {
            private int br;
            private int fid;
            private int size;
            private double vd;

            public int getBr() {
                return br;
            }

            public void setBr(int br) {
                this.br = br;
            }

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public double getVd() {
                return vd;
            }

            public void setVd(double vd) {
                this.vd = vd;
            }
        }

        public static class MBean {
            private String br;
            private int fid;
            private int size;
            private double vd;

            public String getBr() {
                return br;
            }

            public void setBr(String br) {
                this.br = br;
            }

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public double getVd() {
                return vd;
            }

            public void setVd(double vd) {
                this.vd = vd;
            }
        }

        public static class LBean {
            private String br;
            private int fid;
            private int size;
            private double vd;

            public String getBr() {
                return br;
            }

            public void setBr(String br) {
                this.br = br;
            }

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public double getVd() {
                return vd;
            }

            public void setVd(double vd) {
                this.vd = vd;
            }
        }

        public static class ArBean {
            private int id;
            private String name;
            private List<?> tns;
            private List<?> alias;

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

            public List<?> getTns() {
                return tns;
            }

            public void setTns(List<?> tns) {
                this.tns = tns;
            }

            public List<?> getAlias() {
                return alias;
            }

            public void setAlias(List<?> alias) {
                this.alias = alias;
            }
        }
    }

    public static class PrivilegesBean {
        private int id;
        private int fee;
        private int payed;
        private int st;
        private int pl;
        private int dl;
        private int sp;
        private int cp;
        private int subp;
        private boolean cs;
        private int maxbr;
        private int fl;
        private boolean toast;
        private int flag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int st) {
            this.st = st;
        }

        public int getPl() {
            return pl;
        }

        public void setPl(int pl) {
            this.pl = pl;
        }

        public int getDl() {
            return dl;
        }

        public void setDl(int dl) {
            this.dl = dl;
        }

        public int getSp() {
            return sp;
        }

        public void setSp(int sp) {
            this.sp = sp;
        }

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
        }

        public int getSubp() {
            return subp;
        }

        public void setSubp(int subp) {
            this.subp = subp;
        }

        public boolean isCs() {
            return cs;
        }

        public void setCs(boolean cs) {
            this.cs = cs;
        }

        public int getMaxbr() {
            return maxbr;
        }

        public void setMaxbr(int maxbr) {
            this.maxbr = maxbr;
        }

        public int getFl() {
            return fl;
        }

        public void setFl(int fl) {
            this.fl = fl;
        }

        public boolean isToast() {
            return toast;
        }

        public void setToast(boolean toast) {
            this.toast = toast;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}
