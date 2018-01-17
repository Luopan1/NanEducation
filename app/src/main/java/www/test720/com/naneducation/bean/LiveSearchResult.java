package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/15 10:55.
 */

public class LiveSearchResult {


    /**
     * code : 1
     * data : {"course":[{"back_url":"","castId":"1","livetype":"套课","logo":"Uploads/Img/ceshi.jpg","name":"套课一","price":"5006.03","room_mun":"","tc_head":"Uploads/Img/ceshi.jpg","tc_name":"杨戬"},{"back_url":"","castId":"5","livetype":"套课","logo":"Uploads/Img/2018-01-04/5a4dcfa15d012.jpg","name":"测试套课11","price":"99.99","room_mun":"","tc_head":"Uploads/Img/ceshi.jpg","tc_name":"孙大圣"}],"live":[{"back_url":"","castId":"1","livetype":"免费","logo":"Uploads/Img/ceshi.jpg","name":"阿莎","price":"80.88","room_mun":"528","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"},{"back_url":"","castId":"4","livetype":"预告","logo":"Uploads/Img/ceshi.jpg","name":"哈哈","price":"99.99","room_mun":"525","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"},{"back_url":"http://global.talk-cloud.net:80/static/h5/index.html#/replay?host=global.talk-cloud.net&serial=1939868350&type=3&path=global.talk-cloud.net:8081%2F%2Fr6%2F2017-12-29%2F1939868350%2F1514516673%2F","castId":"10","livetype":"已结束","logo":"Uploads/Img/2018-01-05/5a4f3c9eb8506.jpg","name":"邹老师开直播","price":"99.00","room_mun":"1939868350","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"},{"back_url":"http://global.talk-cloud.net:80/static/h5/index.html#/replay?host=global.talk-cloud.net&serial=1715459330&type=0&path=global.talk-cloud.net:8081%2F%2Fr3_1%2F2018-01-06%2F1715459330%2F1515210270%2F","castId":"11","livetype":"进行中","logo":"Uploads/Img/2018-01-06/5a504388c51eb.jpg","name":"邹老师","price":"88.00","room_mun":"1715459330","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"}],"lubo":[{"back_url":"","castId":"4","livetype":"回放","logo":"Uploads/Img/ceshi.jpg","name":"哈哈","price":"99.99","room_mun":"528","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"},{"back_url":"","castId":"10","livetype":"回放","logo":"Uploads/Img/2018-01-05/5a4f3c9eb8506.jpg","name":"邹老师开直播","price":"99.00","room_mun":"525","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"},{"back_url":"http://global.talk-cloud.net:80/static/h5/index.html#/replay?host=global.talk-cloud.net&serial=1939868350&type=3&path=global.talk-cloud.net:8081%2F%2Fr6%2F2017-12-29%2F1939868350%2F1514516673%2F","castId":"11","livetype":"回放","logo":"Uploads/Img/2018-01-06/5a504388c51eb.jpg","name":"邹老师","price":"88.00","room_mun":"1939868350","tc_head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","tc_name":"9529"}]}
     * msg : 搜索成功
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
             * back_url :
             * castId : 1
             * livetype : 套课
             * logo : Uploads/Img/ceshi.jpg
             * name : 套课一
             * price : 5006.03
             * room_mun :
             * tc_head : Uploads/Img/ceshi.jpg
             * tc_name : 杨戬
             */

            private String back_url;
            private String castId;
            private String livetype;
            private String logo;
            private String name;
            private String price;
            private String room_mun;
            private String tc_head;
            private String tc_name;

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

            public String getRoom_mun() {
                return room_mun;
            }

            public void setRoom_mun(String room_mun) {
                this.room_mun = room_mun;
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
             * back_url :
             * castId : 1
             * livetype : 免费
             * logo : Uploads/Img/ceshi.jpg
             * name : 阿莎
             * price : 80.88
             * room_mun : 528
             * tc_head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
             * tc_name : 9529
             */

            private String back_url;
            private String castId;
            private String livetype;
            private String logo;
            private String name;
            private String price;
            private String room_mun;
            private String tc_head;
            private String tc_name;

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

            public String getRoom_mun() {
                return room_mun;
            }

            public void setRoom_mun(String room_mun) {
                this.room_mun = room_mun;
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
             * back_url :
             * castId : 4
             * livetype : 回放
             * logo : Uploads/Img/ceshi.jpg
             * name : 哈哈
             * price : 99.99
             * room_mun : 528
             * tc_head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
             * tc_name : 9529
             */

            private String back_url;
            private String castId;
            private String livetype;
            private String logo;
            private String name;
            private String price;
            private String room_mun;
            private String tc_head;
            private String tc_name;

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

            public String getRoom_mun() {
                return room_mun;
            }

            public void setRoom_mun(String room_mun) {
                this.room_mun = room_mun;
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
