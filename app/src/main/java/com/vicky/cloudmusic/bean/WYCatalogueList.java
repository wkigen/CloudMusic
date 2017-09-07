package com.vicky.cloudmusic.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by vicky on 2017/9/7.
 */
public class WYCatalogueList {
    /**
     * name : 全部歌单
     * resourceCount : 1370
     * imgId : 109951163019113485
     * imgUrl : http://p1.music.126.net/XnyZsQSf5F3eJ7XYO6qUew==/109951163019113485.jpg
     * type : 0
     * category : 4
     * resourceType : 0
     * hot : false
     */

    private AllBean all;
    /**
     * 0 : 语种
     * 1 : 风格
     * 2 : 场景
     * 3 : 情感
     * 4 : 主题
     */

    private CategoriesBean categories;
    /**
     * all : {"name":"全部歌单","resourceCount":1370,"imgId":109951163019113485,"imgUrl":"http://p1.music.126.net/XnyZsQSf5F3eJ7XYO6qUew==/109951163019113485.jpg","type":0,"category":4,"resourceType":0,"hot":false}
     * sub : [{"name":"流行","resourceCount":1355,"imgId":0,"imgUrl":"http://p1.music.126.net/V94bgA3xtXVMwoPOyLH0hQ==/19060034067829712.jpg","type":0,"category":1,"resourceType":0,"hot":true},{"name":"怀旧","resourceCount":1407,"imgId":0,"imgUrl":"http://p1.music.126.net/XnyZsQSf5F3eJ7XYO6qUew==/109951163019113485.jpg","type":0,"category":3,"resourceType":0,"hot":true},{"name":"清晨","resourceCount":1442,"imgId":0,"imgUrl":"http://p1.music.126.net/yUxsEGkA9_nSPgr6P7GzKg==/18645518185873305.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"华语","resourceCount":1328,"imgId":0,"imgUrl":"http://p1.music.126.net/XnyZsQSf5F3eJ7XYO6qUew==/109951163019113485.jpg","type":0,"category":0,"resourceType":0,"hot":true},{"name":"影视原声","resourceCount":1355,"imgId":0,"imgUrl":"http://p1.music.126.net/XkRvNMB6NxVh77PGhBswVQ==/109951162858134081.jpg","type":0,"category":4,"resourceType":0,"hot":true},{"name":"欧美","resourceCount":1364,"imgId":0,"imgUrl":"http://p1.music.126.net/qiIMbaMZZ76ZdCKVdJJ6aA==/109951163017999677.jpg","type":1,"category":0,"resourceType":0,"hot":false},{"name":"ACG","resourceCount":1409,"imgId":0,"imgUrl":"http://p1.music.126.net/JX1rlKY9bXTEKxDMcR3CMw==/18648816720716232.jpg","type":0,"category":4,"resourceType":0,"hot":true},{"name":"摇滚","resourceCount":1408,"imgId":0,"imgUrl":"http://p1.music.126.net/X70vJLgGmsbvAyD67-P6FA==/19052337486436289.jpg","type":0,"category":1,"resourceType":0,"hot":true},{"name":"夜晚","resourceCount":1371,"imgId":0,"imgUrl":"http://p1.music.126.net/qiIMbaMZZ76ZdCKVdJJ6aA==/109951163017999677.jpg","type":0,"category":2,"resourceType":0,"hot":true},{"name":"清新","resourceCount":1441,"imgId":0,"imgUrl":"http://p1.music.126.net/FtFNGCMA7QJePX5n2pEEDA==/109951163006321799.jpg","type":0,"category":3,"resourceType":0,"hot":true},{"name":"浪漫","resourceCount":1410,"imgId":0,"imgUrl":"http://p1.music.126.net/9pB2DBMcE3Md4EoO5x2F7Q==/18525671418395802.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"学习","resourceCount":1432,"imgId":0,"imgUrl":"http://p1.music.126.net/aLLgf1lW_hONTjsWfG4MFQ==/19136999881699849.jpg","type":0,"category":2,"resourceType":0,"hot":true},{"name":"校园","resourceCount":1418,"imgId":0,"imgUrl":"http://p1.music.126.net/ZWi_2aS2Ha1jpZhWjLblKQ==/19018252625819610.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"日语","resourceCount":1389,"imgId":0,"imgUrl":"http://p1.music.126.net/fr70lrFyc5IBpnd501339A==/109951163019140637.jpg","type":1,"category":0,"resourceType":0,"hot":false},{"name":"民谣","resourceCount":1396,"imgId":0,"imgUrl":"http://p1.music.126.net/X70vJLgGmsbvAyD67-P6FA==/19052337486436289.jpg","type":0,"category":1,"resourceType":0,"hot":true},{"name":"电子","resourceCount":1344,"imgId":0,"imgUrl":"http://p1.music.126.net/KZLBoDOezHKVlThjLjK5Rg==/109951163019770674.jpg","type":0,"category":1,"resourceType":0,"hot":true},{"name":"性感","resourceCount":1265,"imgId":0,"imgUrl":"http://p1.music.126.net/4SZ7gkNxL19C7pl3UWGxcA==/109951163019589590.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"工作","resourceCount":1460,"imgId":0,"imgUrl":"http://p1.music.126.net/3uAjeGzvsIldcZ0MdSdnaQ==/19153492556011875.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"韩语","resourceCount":1427,"imgId":0,"imgUrl":"http://p1.music.126.net/FquGJ8ElkOtoz7Rz5_LhUg==/109951163017904213.jpg","type":1,"category":0,"resourceType":0,"hot":false},{"name":"游戏","resourceCount":1384,"imgId":0,"imgUrl":"http://p1.music.126.net/Qba9L-ZU3EZMDxA1I8W7kg==/109951163001141157.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"70后","resourceCount":1455,"imgId":0,"imgUrl":"http://p1.music.126.net/xlrakD6ysRjr0AHvNSzfVA==/19140298416625522.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"午休","resourceCount":1415,"imgId":0,"imgUrl":"http://p1.music.126.net/XnyZsQSf5F3eJ7XYO6qUew==/109951163019113485.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"舞曲","resourceCount":1403,"imgId":0,"imgUrl":"http://p1.music.126.net/Ioby8856pzgK9uSfrzMB0Q==/109951163019490021.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"粤语","resourceCount":1437,"imgId":0,"imgUrl":"http://p1.music.126.net/V94bgA3xtXVMwoPOyLH0hQ==/19060034067829712.jpg","type":1,"category":0,"resourceType":0,"hot":false},{"name":"伤感","resourceCount":1424,"imgId":0,"imgUrl":"http://p1.music.126.net/afga-2ELJ_PF-BellxHK3Q==/109951163012228281.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"80后","resourceCount":1459,"imgId":0,"imgUrl":"http://p1.music.126.net/xlrakD6ysRjr0AHvNSzfVA==/19140298416625522.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"治愈","resourceCount":1421,"imgId":0,"imgUrl":"http://p1.music.126.net/RAvVflpjBzzSgyXJfRbYcw==/19191975463130026.jpg","type":0,"category":3,"resourceType":0,"hot":true},{"name":"小语种","resourceCount":1413,"imgId":0,"imgUrl":"http://p1.music.126.net/KZLBoDOezHKVlThjLjK5Rg==/109951163019770674.jpg","type":1,"category":0,"resourceType":0,"hot":false},{"name":"下午茶","resourceCount":1448,"imgId":0,"imgUrl":"http://p1.music.126.net/RAvVflpjBzzSgyXJfRbYcw==/19191975463130026.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"说唱","resourceCount":1355,"imgId":0,"imgUrl":"http://p1.music.126.net/FquGJ8ElkOtoz7Rz5_LhUg==/109951163017904213.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"放松","resourceCount":1429,"imgId":0,"imgUrl":"http://p1.music.126.net/qiIMbaMZZ76ZdCKVdJJ6aA==/109951163017999677.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"轻音乐","resourceCount":1437,"imgId":0,"imgUrl":"http://p1.music.126.net/JX1rlKY9bXTEKxDMcR3CMw==/18648816720716232.jpg","type":0,"category":1,"resourceType":0,"hot":true},{"name":"地铁","resourceCount":1446,"imgId":0,"imgUrl":"http://p1.music.126.net/kWoXgp9mADiiaxzyYpwBhQ==/19094118928321497.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"90后","resourceCount":1446,"imgId":0,"imgUrl":"http://p1.music.126.net/IQgWsd_W2Oq5NF8p1XX6dw==/109951163015034633.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"孤独","resourceCount":1426,"imgId":0,"imgUrl":"http://p1.music.126.net/q16tT_GbT_iqK-tVfOXvyw==/19107313067859794.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"驾车","resourceCount":1427,"imgId":0,"imgUrl":"http://p1.music.126.net/UM4UTNdmKUk8Cu2q8Ydu3w==/18520173860162185.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"网络歌曲","resourceCount":1413,"imgId":0,"imgUrl":"http://p1.music.126.net/4WcHEGt4zXrwOQDi_rjUGg==/18659811836934808.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"爵士","resourceCount":1437,"imgId":0,"imgUrl":"http://p1.music.126.net/tJLOwbline4YKb0jgWOW9A==/18690598162582645.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"乡村","resourceCount":1441,"imgId":0,"imgUrl":"http://p1.music.126.net/5UKfLNF4L-nWenu8OioazQ==/18688399139342972.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"感动","resourceCount":1427,"imgId":0,"imgUrl":"http://p1.music.126.net/dQma0x6cI0yV1mDHzsXuNQ==/109951163006142264.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"运动","resourceCount":1395,"imgId":0,"imgUrl":"http://p1.music.126.net/brra6IhHKPP-R2GoywESZg==/18922595114287605.jpg","type":0,"category":2,"resourceType":0,"hot":true},{"name":"KTV","resourceCount":1460,"imgId":0,"imgUrl":"http://p1.music.126.net/uC9wDQaR5GltlsyV9YsuVw==/18782957139331075.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"R&B/Soul","resourceCount":1386,"imgId":0,"imgUrl":"http://p1.music.126.net/v_zcZQ4stCwUW4NHqL_cjQ==/19140298416656132.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"经典","resourceCount":1439,"imgId":0,"imgUrl":"http://p1.music.126.net/OQ8ZG1q0hCycZxHj7n-7Cg==/19013854579445576.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"旅行","resourceCount":1444,"imgId":0,"imgUrl":"http://p1.music.126.net/X70vJLgGmsbvAyD67-P6FA==/19052337486436289.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"兴奋","resourceCount":1372,"imgId":0,"imgUrl":"http://p1.music.126.net/4SZ7gkNxL19C7pl3UWGxcA==/109951163019589590.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"古典","resourceCount":1451,"imgId":0,"imgUrl":"http://p1.music.126.net/Z3d8bLA25cr5Bv0k8W2eDw==/18940187300363604.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"翻唱","resourceCount":1428,"imgId":0,"imgUrl":"http://p1.music.126.net/5UKfLNF4L-nWenu8OioazQ==/18688399139342972.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"快乐","resourceCount":1420,"imgId":0,"imgUrl":"http://p1.music.126.net/4SZ7gkNxL19C7pl3UWGxcA==/109951163019589590.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"散步","resourceCount":1441,"imgId":0,"imgUrl":"http://p1.music.126.net/4Wd53o305Uux6BeKf0PXsQ==/18896206835213710.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"酒吧","resourceCount":1413,"imgId":0,"imgUrl":"http://p1.music.126.net/IQgWsd_W2Oq5NF8p1XX6dw==/109951163015034633.jpg","type":1,"category":2,"resourceType":0,"hot":false},{"name":"吉他","resourceCount":1439,"imgId":0,"imgUrl":"http://p1.music.126.net/Pg_7T9Bk4EoNBZEoSnWj6w==/18592741627655609.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"民族","resourceCount":1440,"imgId":0,"imgUrl":"http://p1.music.126.net/gOQyn9_5kkiZ0SBGkBe83Q==/109951163017680528.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"安静","resourceCount":1424,"imgId":0,"imgUrl":"http://p1.music.126.net/XkRvNMB6NxVh77PGhBswVQ==/109951162858134081.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"英伦","resourceCount":1440,"imgId":0,"imgUrl":"http://p1.music.126.net/chLQGRMtBacOXx9DI4qT8w==/18976471183976249.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"思念","resourceCount":1432,"imgId":0,"imgUrl":"http://p1.music.126.net/Sa136LW06IeLk30EtwYYUw==/19185378393353571.jpg","type":1,"category":3,"resourceType":0,"hot":false},{"name":"钢琴","resourceCount":1462,"imgId":0,"imgUrl":"http://p1.music.126.net/JX1rlKY9bXTEKxDMcR3CMw==/18648816720716232.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"金属","resourceCount":1449,"imgId":0,"imgUrl":"http://p1.music.126.net/bsWfspB3jVIARrwe32oh8g==/19208468137393946.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"器乐","resourceCount":1455,"imgId":0,"imgUrl":"http://p1.music.126.net/tJLOwbline4YKb0jgWOW9A==/18690598162582645.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"儿童","resourceCount":1423,"imgId":0,"imgUrl":"http://p1.music.126.net/GqSnQI6-XXS95pgN6jiHeA==/18743374720683269.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"朋克","resourceCount":1443,"imgId":0,"imgUrl":"http://p1.music.126.net/l2c8rwej1wwMBYmP-__2KQ==/19200771556068744.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"蓝调","resourceCount":1434,"imgId":0,"imgUrl":"http://p1.music.126.net/vBU1UkZejGlxdJkyS4GJYA==/18797250790411120.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"榜单","resourceCount":1380,"imgId":0,"imgUrl":"http://p1.music.126.net/PVccoolTO7nTM3gDi2_LMA==/19011655556203399.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"雷鬼","resourceCount":1413,"imgId":0,"imgUrl":"http://p1.music.126.net/1sXkYHdo7y9e7elOiqF68A==/18880813672502350.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"00后","resourceCount":1425,"imgId":0,"imgUrl":"http://p1.music.126.net/bOAr1NOW86_1vIc7DjtwPg==/18758767883378519.jpg","type":1,"category":4,"resourceType":0,"hot":false},{"name":"世界音乐","resourceCount":1416,"imgId":0,"imgUrl":"http://p1.music.126.net/jd-thNjhzobOAx69hwadAw==/109951162971849439.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"拉丁","resourceCount":1422,"imgId":0,"imgUrl":"http://p1.music.126.net/2782lMxXbZ0RJVAi8FD6yw==/109951163015589389.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"另类/独立","resourceCount":1415,"imgId":0,"imgUrl":"http://p1.music.126.net/q16tT_GbT_iqK-tVfOXvyw==/19107313067859794.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"New Age","resourceCount":1455,"imgId":0,"imgUrl":"http://p1.music.126.net/9USz7swbRrcAE1UuAK7yug==/18980869230615947.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"古风","resourceCount":1420,"imgId":0,"imgUrl":"http://p1.music.126.net/yUxsEGkA9_nSPgr6P7GzKg==/18645518185873305.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"后摇","resourceCount":1440,"imgId":0,"imgUrl":"http://p1.music.126.net/q16tT_GbT_iqK-tVfOXvyw==/19107313067859794.jpg","type":1,"category":1,"resourceType":0,"hot":false},{"name":"Bossa Nova","resourceCount":1440,"imgId":0,"imgUrl":"http://p1.music.126.net/laqptQdhOKmDFJQp2b2TpQ==/109951162958427596.jpg","type":1,"category":1,"resourceType":0,"hot":false}]
     * categories : {"0":"语种","1":"风格","2":"场景","3":"情感","4":"主题"}
     * code : 200
     */

    private int code;
    /**
     * name : 流行
     * resourceCount : 1355
     * imgId : 0
     * imgUrl : http://p1.music.126.net/V94bgA3xtXVMwoPOyLH0hQ==/19060034067829712.jpg
     * type : 0
     * category : 1
     * resourceType : 0
     * hot : true
     */

    private List<SubBean> sub;

    public AllBean getAll() {
        return all;
    }

    public void setAll(AllBean all) {
        this.all = all;
    }

    public CategoriesBean getCategories() {
        return categories;
    }

    public void setCategories(CategoriesBean categories) {
        this.categories = categories;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SubBean> getSub() {
        return sub;
    }

    public void setSub(List<SubBean> sub) {
        this.sub = sub;
    }

    public static class AllBean {
        private String name;
        private int resourceCount;
        private long imgId;
        private String imgUrl;
        private int type;
        private int category;
        private int resourceType;
        private boolean hot;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResourceCount() {
            return resourceCount;
        }

        public void setResourceCount(int resourceCount) {
            this.resourceCount = resourceCount;
        }

        public long getImgId() {
            return imgId;
        }

        public void setImgId(long imgId) {
            this.imgId = imgId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }

        public boolean isHot() {
            return hot;
        }

        public void setHot(boolean hot) {
            this.hot = hot;
        }
    }

    public static class CategoriesBean {
        @JSONField(name="0")
        private String value0;
        @JSONField(name="1")
        private String value1;
        @JSONField(name="2")
        private String value2;
        @JSONField(name="3")
        private String value3;
        @JSONField(name="4")
        private String value4;

        public String getValue0() {
            return value0;
        }

        public void setValue0(String value0) {
            this.value0 = value0;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getValue3() {
            return value3;
        }

        public void setValue3(String value3) {
            this.value3 = value3;
        }

        public String getValue4() {
            return value4;
        }

        public void setValue4(String value4) {
            this.value4 = value4;
        }
    }

    public static class SubBean {
        private String name;
        private int resourceCount;
        private int imgId;
        private String imgUrl;
        private int type;
        private int category;
        private int resourceType;
        private boolean hot;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResourceCount() {
            return resourceCount;
        }

        public void setResourceCount(int resourceCount) {
            this.resourceCount = resourceCount;
        }

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }

        public boolean isHot() {
            return hot;
        }

        public void setHot(boolean hot) {
            this.hot = hot;
        }
    }
}
