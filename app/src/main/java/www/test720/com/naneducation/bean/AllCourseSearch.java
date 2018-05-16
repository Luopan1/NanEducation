package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/16 14:57.
 */

public class AllCourseSearch {

    /**
     * code : 1
     * data : {"course":[{"c_logo":"Uploads/Img/ceshi.jpg","c_name":"语文","c_price":"3008.00","cid":"2","is_free":"0","tc_head":"Uploads/Img/ceshi.jpg","tc_name":"孙大圣"}],"live":[{"castId":"1","livetype":"免费","logo":"Uploads/Img/ceshi.jpg","name":"阿莎","price":"80.88","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9527"},{"castId":"4","livetype":"预告","logo":"Uploads/Img/ceshi.jpg","name":"哈哈","price":"99.99","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9527"}],"lubo":[{"castId":"4","livetype":"回放","logo":"Uploads/Img/ceshi.jpg","name":"哈哈","price":"99.99","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9527"}]}
     * msg : 查询成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<CourseBean> course;
        private List<LiveBean> live;
        private List<LuboBean> lubo;

        public List<CourseBean> getCourse() {
            return course;
        }

        public void setCourse(List<CourseBean> course) {
            this.course = course;
        }

        public List<LiveBean> getLive() {
            return live;
        }

        public void setLive(List<LiveBean> live) {
            this.live = live;
        }

        public List<LuboBean> getLubo() {
            return lubo;
        }

        public void setLubo(List<LuboBean> lubo) {
            this.lubo = lubo;
        }

        public static class CourseBean {
            /**
             * c_logo : Uploads/Img/ceshi.jpg
             * c_name : 语文
             * c_price : 3008.00
             * cid : 2
             * is_free : 0
             * tc_head : Uploads/Img/ceshi.jpg
             * tc_name : 孙大圣
             */

            private String c_logo;
            private String c_name;
            private String c_price;
            private String cid;
            private String is_free;
            private String tc_head;
            private String tc_name;

            public String getC_logo() {
                return c_logo;
            }

            public void setC_logo(String c_logo) {
                this.c_logo = c_logo;
            }

            public String getC_name() {
                return c_name;
            }

            public void setC_name(String c_name) {
                this.c_name = c_name;
            }

            public String getC_price() {
                return c_price;
            }

            public void setC_price(String c_price) {
                this.c_price = c_price;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getIs_free() {
                return is_free;
            }

            public void setIs_free(String is_free) {
                this.is_free = is_free;
            }

            public String getTc_head() {
                return tc_head;
            }

            public void setTc_head(String tc_head) {
                this.tc_head = tc_head;
            }

            public String getTc_name() {
                return tc_name;
            }

            public void setTc_name(String tc_name) {
                this.tc_name = tc_name;
            }
        }

        public static class LiveBean {
            /**
             * castId : 1
             * livetype : 免费
             * logo : Uploads/Img/ceshi.jpg
             * name : 阿莎
             * price : 80.88
             * tc_head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
             * tc_name : 9527
             */

            private String castId;
            private String livetype;
            private String logo;
            private String name;
            private String price;
            private String tc_head;
            private String tc_name;
            private String room_mun;
            private String back_url;

            public String getRoom_mun() {
                return room_mun;
            }

            public void setRoom_mun(String room_mun) {
                this.room_mun = room_mun;
            }

            public String getBack_url() {
                return back_url;
            }

            public void setBack_url(String back_url) {
                this.back_url = back_url;
            }

            public String getCastId() {
                return castId;
            }

            public void setCastId(String castId) {
                this.castId = castId;
            }

            public String getLivetype() {
                return livetype;
            }

            public void setLivetype(String livetype) {
                this.livetype = livetype;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTc_head() {
                return tc_head;
            }

            public void setTc_head(String tc_head) {
                this.tc_head = tc_head;
            }

            public String getTc_name() {
                return tc_name;
            }

            public void setTc_name(String tc_name) {
                this.tc_name = tc_name;
            }
        }

        public static class LuboBean {
            /**
             * castId : 4
             * livetype : 回放
             * logo : Uploads/Img/ceshi.jpg
             * name : 哈哈
             * price : 99.99
             * tc_head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
             * tc_name : 9527
             */


            /* "livetype":"进行中",
                "name":"测试",
                "logo":"Uploads/Img/2018-04-21/825f9cd5f0390bc77c1fed3c94885c87.jpg",
                "tc_head":"",
                "tc_name":"1899089916怒",
                "price":"5.00",
                "castId":"132",
                "room_mun":"363296143",
                "back_url":""*/
            private String room_mun;
            private String back_url;

            public String getRoom_mun() {
                return room_mun;
            }

            public void setRoom_mun(String room_mun) {
                this.room_mun = room_mun;
            }

            public String getBack_url() {
                return back_url;
            }

            public void setBack_url(String back_url) {
                this.back_url = back_url;
            }

            private String castId;
            private String livetype;
            private String logo;
            private String name;
            private String price;
            private String tc_head;
            private String tc_name;

            public String getCastId() {
                return castId;
            }

            public void setCastId(String castId) {
                this.castId = castId;
            }

            public String getLivetype() {
                return livetype;
            }

            public void setLivetype(String livetype) {
                this.livetype = livetype;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTc_head() {
                return tc_head;
            }

            public void setTc_head(String tc_head) {
                this.tc_head = tc_head;
            }

            public String getTc_name() {
                return tc_name;
            }

            public void setTc_name(String tc_name) {
                this.tc_name = tc_name;
            }
        }
    }
}
