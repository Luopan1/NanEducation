package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/16 10:27.
 */

public class AllCourseDetail {
    /**
     * code : 1
     * data : {"detail":{"c_ctime":"16节课","c_describe":"方式点发送发送发三的方式撒的粉色发","c_img":"Uploads/Img/2018-01-17/5a5eeebddff9b.jpg","c_name":"钢琴初级课程","c_people":"9","c_price":"16.00","c_type":"艺术","cid":"11","is_free":"0","is_love":0,"is_signup":0,"mun":0,"sponsor":["助学:伊利1.00元","蒙牛2.00元"],"startime":"2018-01-17 14:36:21","tc_describe":"老师介绍的哈佛设定速度会发货覅哦啊花覅哦啊花覅偶收到货佛山","tc_head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","tc_name":"张昊老师","videoList":[{"course_id":"11","is_free":"0","tc_head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","tc_name":"张昊老师","v_logo":"Uploads/Video/2018-01-17/5a5eef0bb9ab4.jpg","v_name":"地方是","v_price":"2","v_src":"Uploads/Video/2018-01-17/5a5eef0bf1c3d.mp4","vid":"15"}]}}
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
        /**
         * detail : {"c_ctime":"16节课","c_describe":"方式点发送发送发三的方式撒的粉色发","c_img":"Uploads/Img/2018-01-17/5a5eeebddff9b.jpg","c_name":"钢琴初级课程","c_people":"9","c_price":"16.00","c_type":"艺术","cid":"11","is_free":"0","is_love":0,"is_signup":0,"mun":0,"sponsor":["助学:伊利1.00元","蒙牛2.00元"],"startime":"2018-01-17 14:36:21","tc_describe":"老师介绍的哈佛设定速度会发货覅哦啊花覅哦啊花覅偶收到货佛山","tc_head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","tc_name":"张昊老师","videoList":[{"course_id":"11","is_free":"0","tc_head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","tc_name":"张昊老师","v_logo":"Uploads/Video/2018-01-17/5a5eef0bb9ab4.jpg","v_name":"地方是","v_price":"2","v_src":"Uploads/Video/2018-01-17/5a5eef0bf1c3d.mp4","vid":"15"}]}
         */

        private DetailBean detail;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * c_ctime : 16节课
             * c_describe : 方式点发送发送发三的方式撒的粉色发
             * c_img : Uploads/Img/2018-01-17/5a5eeebddff9b.jpg
             * c_name : 钢琴初级课程
             * c_people : 9
             * c_price : 16.00
             * c_type : 艺术
             * cid : 11
             * is_free : 0
             * is_love : 0
             * is_signup : 0
             * mun : 0
             * sponsor : ["助学:伊利1.00元","蒙牛2.00元"]
             * startime : 2018-01-17 14:36:21
             * tc_describe : 老师介绍的哈佛设定速度会发货覅哦啊花覅哦啊花覅偶收到货佛山
             * tc_head : Uploads/Img/2018-01-15/5a5c1cf17d517.jpg
             * tc_name : 张昊老师
             * videoList : [{"course_id":"11","is_free":"0","tc_head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","tc_name":"张昊老师","v_logo":"Uploads/Video/2018-01-17/5a5eef0bb9ab4.jpg","v_name":"地方是","v_price":"2","v_src":"Uploads/Video/2018-01-17/5a5eef0bf1c3d.mp4","vid":"15"}]
             */

            private String c_ctime;
            private String c_describe;
            private String c_img;
            private String c_name;
            private String c_people;
            private String c_price;
            private String c_type;
            private String cid;
            private String is_free;
            private int is_love;
            private int is_signup;
            private int mun;
            private String startime;
            private String tc_describe;
            private String tc_head;
            private String tc_name;
            private List<String> sponsor;
            private List<VideoListBean> videoList;

            public String getC_ctime() {
                return c_ctime;
            }

            public void setC_ctime(String c_ctime) {
                this.c_ctime = c_ctime;
            }

            public String getC_describe() {
                return c_describe;
            }

            public void setC_describe(String c_describe) {
                this.c_describe = c_describe;
            }

            public String getC_img() {
                return c_img;
            }

            public void setC_img(String c_img) {
                this.c_img = c_img;
            }

            public String getC_name() {
                return c_name;
            }

            public void setC_name(String c_name) {
                this.c_name = c_name;
            }

            public String getC_people() {
                return c_people;
            }

            public void setC_people(String c_people) {
                this.c_people = c_people;
            }

            public String getC_price() {
                return c_price;
            }

            public void setC_price(String c_price) {
                this.c_price = c_price;
            }

            public String getC_type() {
                return c_type;
            }

            public void setC_type(String c_type) {
                this.c_type = c_type;
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

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public int getIs_signup() {
                return is_signup;
            }

            public void setIs_signup(int is_signup) {
                this.is_signup = is_signup;
            }

            public int getMun() {
                return mun;
            }

            public void setMun(int mun) {
                this.mun = mun;
            }

            public String getStartime() {
                return startime;
            }

            public void setStartime(String startime) {
                this.startime = startime;
            }

            public String getTc_describe() {
                return tc_describe;
            }

            public void setTc_describe(String tc_describe) {
                this.tc_describe = tc_describe;
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

            public List<String> getSponsor() {
                return sponsor;
            }

            public void setSponsor(List<String> sponsor) {
                this.sponsor = sponsor;
            }

            public List<VideoListBean> getVideoList() {
                return videoList;
            }

            public void setVideoList(List<VideoListBean> videoList) {
                this.videoList = videoList;
            }

            public static class VideoListBean {
                /**
                 * course_id : 11
                 * is_free : 0
                 * tc_head : Uploads/Img/2018-01-15/5a5c1cf17d517.jpg
                 * tc_name : 张昊老师
                 * v_logo : Uploads/Video/2018-01-17/5a5eef0bb9ab4.jpg
                 * v_name : 地方是
                 * v_price : 2
                 * v_src : Uploads/Video/2018-01-17/5a5eef0bf1c3d.mp4
                 * vid : 15
                 */

                private String course_id;
                private String is_free;
                private String tc_head;
                private String tc_name;
                private String v_logo;
                private String v_name;
                private String v_price;
                private String v_src;
                private String vid;

                public String getCourse_id() {
                    return course_id;
                }

                public void setCourse_id(String course_id) {
                    this.course_id = course_id;
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

                public String getV_logo() {
                    return v_logo;
                }

                public void setV_logo(String v_logo) {
                    this.v_logo = v_logo;
                }

                public String getV_name() {
                    return v_name;
                }

                public void setV_name(String v_name) {
                    this.v_name = v_name;
                }

                public String getV_price() {
                    return v_price;
                }

                public void setV_price(String v_price) {
                    this.v_price = v_price;
                }

                public String getV_src() {
                    return v_src;
                }

                public void setV_src(String v_src) {
                    this.v_src = v_src;
                }

                public String getVid() {
                    return vid;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }
            }
        }
    }
}
