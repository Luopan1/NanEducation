package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/14.
 */

public class LiveBroad {


    /**
     * code : 1
     * data : {"list":[{"back_url":"","endtime":"2018.01.20 11:20:45","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","is_free":"0","lid":"16","live_logo":"Uploads/Img/2018-01-15/5a5c1e1241b28.jpg","live_title":"直播推荐测试","live_url":"","livetype":"预告","name":"张昊老师","price":"100.00","room_mun":"1942444274","signup_count":"0","startime":"2018.01.19 11:20:39"},{"back_url":"","endtime":"2018.01.19 11:21:17","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","is_free":"0","lid":"17","live_logo":"Uploads/Img/2018-01-15/5a5c1e3367afc.jpg","live_title":"直播三","live_url":"","livetype":"预告","name":"张昊老师","price":"10.00","room_mun":"1109176340","signup_count":"1","startime":"2018.01.18 11:21:15"},{"back_url":"","endtime":"2018.01.18 15:07:26","head":"Uploads/Img/2018-01-15/5a5c52c2a3050.jpg","is_free":"0","lid":"18","live_logo":"Uploads/Img/2018-01-15/5a5c533a5ed78.jpg","live_title":"是是是","live_url":"","livetype":"预告","name":"穆老师","price":"5.00","room_mun":"162223551","signup_count":"0","startime":"2018.01.18 15:07:23"},{"back_url":"","endtime":"2018.01.23 15:09:11","head":"Uploads/Img/2018-01-15/5a5c52c2a3050.jpg","is_free":"1","lid":"19","live_logo":"Uploads/Img/2018-01-15/5a5c539a81078.jpg","live_title":"拉拉糊","live_url":"","livetype":"免费","name":"穆老师","price":"6.00","room_mun":"396704230","signup_count":"0","startime":"2018.01.19 15:09:09"},{"back_url":"","endtime":"2018.02.03 16:43:31","head":"Uploads/Img/2018-01-15/5a5c52c2a3050.jpg","is_free":"0","lid":"20","live_logo":"Uploads/Img/2018-01-15/5a5c69b9e624e.jpg","live_title":"价格测试","live_url":"","livetype":"预告","name":"穆老师","price":"1000.00","room_mun":"1648076706","signup_count":"0","startime":"2018.02.01 16:43:22"},{"back_url":"","endtime":"2018.01.19 18:22:12","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","is_free":"0","lid":"21","live_logo":"Uploads/Img/2018-01-15/5a5c80da2caa0.jpg","live_title":"大时代","live_url":"","livetype":"预告","name":"张昊老师","price":"2.00","room_mun":"829648154","signup_count":"1","startime":"2018.01.18 18:22:10"}],"typeList":[{"name":"文化","tid":"34","zi":[{"name":"语文","tid":"37","zid":[{"name":"一年级","pid":"37","tid":"40"},{"name":"二年级","pid":"37","tid":"41"},{"name":"三年级","pid":"37","tid":"42"},{"name":"四年级","pid":"37","tid":"43"},{"name":"五年级","pid":"37","tid":"44"},{"name":"六年级","pid":"37","tid":"45"},{"name":"七年级","pid":"37","tid":"46"},{"name":"八年级","pid":"37","tid":"47"},{"name":"九年级","pid":"37","tid":"48"}]},{"name":"数学","tid":"38","zid":[{"name":"5454","pid":"38","tid":"49"},{"name":"二年级","pid":"38","tid":"50"}]},{"name":"英语","tid":"39","zid":[]}]},{"name":"体育","tid":"35","zi":[]},{"name":"艺术","tid":"36","zi":[]}]}
     * msg : 获取成功
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
        private List<ListBean> list;
        private List<TypeListBean> typeList;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class ListBean {
            /**
             * back_url :
             * endtime : 2018.01.20 11:20:45
             * head : Uploads/Img/2018-01-15/5a5c1cf17d517.jpg
             * is_free : 0
             * lid : 16
             * live_logo : Uploads/Img/2018-01-15/5a5c1e1241b28.jpg
             * live_title : 直播推荐测试
             * live_url :
             * livetype : 预告
             * name : 张昊老师
             * price : 100.00
             * room_mun : 1942444274
             * signup_count : 0
             * startime : 2018.01.19 11:20:39
             */

            private String back_url;
            private String endtime;
            private String head;
            private String is_free;
            private String lid;
            private String live_logo;
            private String live_title;
            private String live_url;
            private String livetype;
            private String name;
            private String price;
            private String room_mun;
            private String signup_count;
            private String startime;

            public String getBack_url() {
                return back_url;
            }

            public void setBack_url(String back_url) {
                this.back_url = back_url;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getIs_free() {
                return is_free;
            }

            public void setIs_free(String is_free) {
                this.is_free = is_free;
            }

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public String getLive_logo() {
                return live_logo;
            }

            public void setLive_logo(String live_logo) {
                this.live_logo = live_logo;
            }

            public String getLive_title() {
                return live_title;
            }

            public void setLive_title(String live_title) {
                this.live_title = live_title;
            }

            public String getLive_url() {
                return live_url;
            }

            public void setLive_url(String live_url) {
                this.live_url = live_url;
            }

            public String getLivetype() {
                return livetype;
            }

            public void setLivetype(String livetype) {
                this.livetype = livetype;
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

            public String getSignup_count() {
                return signup_count;
            }

            public void setSignup_count(String signup_count) {
                this.signup_count = signup_count;
            }

            public String getStartime() {
                return startime;
            }

            public void setStartime(String startime) {
                this.startime = startime;
            }
        }

        public static class TypeListBean {
            /**
             * name : 文化
             * tid : 34
             * zi : [{"name":"语文","tid":"37","zid":[{"name":"一年级","pid":"37","tid":"40"},{"name":"二年级","pid":"37","tid":"41"},{"name":"三年级","pid":"37","tid":"42"},{"name":"四年级","pid":"37","tid":"43"},{"name":"五年级","pid":"37","tid":"44"},{"name":"六年级","pid":"37","tid":"45"},{"name":"七年级","pid":"37","tid":"46"},{"name":"八年级","pid":"37","tid":"47"},{"name":"九年级","pid":"37","tid":"48"}]},{"name":"数学","tid":"38","zid":[{"name":"5454","pid":"38","tid":"49"},{"name":"二年级","pid":"38","tid":"50"}]},{"name":"英语","tid":"39","zid":[]}]
             */

            private String name;
            private String tid;
            private List<ZiBean> zi;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public List<ZiBean> getZi() {
                return zi;
            }

            public void setZi(List<ZiBean> zi) {
                this.zi = zi;
            }

            public static class ZiBean {
                /**
                 * name : 语文
                 * tid : 37
                 * zid : [{"name":"一年级","pid":"37","tid":"40"},{"name":"二年级","pid":"37","tid":"41"},{"name":"三年级","pid":"37","tid":"42"},{"name":"四年级","pid":"37","tid":"43"},{"name":"五年级","pid":"37","tid":"44"},{"name":"六年级","pid":"37","tid":"45"},{"name":"七年级","pid":"37","tid":"46"},{"name":"八年级","pid":"37","tid":"47"},{"name":"九年级","pid":"37","tid":"48"}]
                 */

                private String name;
                private String tid;
                private List<ZidBean> zid;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public List<ZidBean> getZid() {
                    return zid;
                }

                public void setZid(List<ZidBean> zid) {
                    this.zid = zid;
                }

                public static class ZidBean {

                    public ZidBean(String name, String pid, String tid) {
                        this.name = name;
                        this.pid = pid;
                        this.tid = tid;
                    }

                    /**
                     * name : 一年级
                     * pid : 37
                     * tid : 40
                     */


                    private String name;
                    private String pid;
                    private String tid;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getPid() {
                        return pid;
                    }

                    public void setPid(String pid) {
                        this.pid = pid;
                    }

                    public String getTid() {
                        return tid;
                    }

                    public void setTid(String tid) {
                        this.tid = tid;
                    }
                }
            }
        }
    }
}
