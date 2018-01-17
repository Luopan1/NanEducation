package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/17 14:02.
 */

public class AreadyBuyVideo {

    /**
     * code : 1
     * data : {"list":[{"back_url":"http://global.talk-cloud.net:80/static/h5/index.html#/replay?host=global.talk-cloud.net&serial=126412687&type=3&path=global.talk-cloud.net:8081%2F%2Fr8%2F2018-01-17%2F126412687%2F1516155358%2F","endtime":"1516159200","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","lid":"26","live_logo":"Uploads/Img/2018-01-17/5a5eb1a038b8a.jpg","live_title":"个人中心测试","livetype":"已结束","name":"张昊老师","room_mun":"126412687","startime":"1516155420","time":"2018-01-17 10:15:44"},{"back_url":"","endtime":"1516156320","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","lid":"25","live_logo":"Uploads/Img/2018-01-17/5a5ea8a797ee9.jpg","live_title":"股一个月预告与故意预估跟玉观音与故意故意","livetype":"已结束","name":"张昊老师","room_mun":"1826157159","startime":"1516156200","time":"2018-01-17 10:03:10"},{"back_url":"","endtime":"1516003500","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","lid":"14","live_logo":"Uploads/Img/2018-01-15/5a5c1d9689ee1.jpg","live_title":"张昊一年级语文课","livetype":"已结束","name":"张昊老师","room_mun":"1026182288","startime":"1516003200","time":"2018-01-15 14:32:18"}],"total":1}
     * msg : 成功
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
         * list : [{"back_url":"http://global.talk-cloud.net:80/static/h5/index.html#/replay?host=global.talk-cloud.net&serial=126412687&type=3&path=global.talk-cloud.net:8081%2F%2Fr8%2F2018-01-17%2F126412687%2F1516155358%2F","endtime":"1516159200","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","lid":"26","live_logo":"Uploads/Img/2018-01-17/5a5eb1a038b8a.jpg","live_title":"个人中心测试","livetype":"已结束","name":"张昊老师","room_mun":"126412687","startime":"1516155420","time":"2018-01-17 10:15:44"},{"back_url":"","endtime":"1516156320","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","lid":"25","live_logo":"Uploads/Img/2018-01-17/5a5ea8a797ee9.jpg","live_title":"股一个月预告与故意预估跟玉观音与故意故意","livetype":"已结束","name":"张昊老师","room_mun":"1826157159","startime":"1516156200","time":"2018-01-17 10:03:10"},{"back_url":"","endtime":"1516003500","head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","lid":"14","live_logo":"Uploads/Img/2018-01-15/5a5c1d9689ee1.jpg","live_title":"张昊一年级语文课","livetype":"已结束","name":"张昊老师","room_mun":"1026182288","startime":"1516003200","time":"2018-01-15 14:32:18"}]
         * total : 1
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * back_url : http://global.talk-cloud.net:80/static/h5/index.html#/replay?host=global.talk-cloud.net&serial=126412687&type=3&path=global.talk-cloud.net:8081%2F%2Fr8%2F2018-01-17%2F126412687%2F1516155358%2F
             * endtime : 1516159200
             * head : Uploads/Img/2018-01-15/5a5c1cf17d517.jpg
             * lid : 26
             * live_logo : Uploads/Img/2018-01-17/5a5eb1a038b8a.jpg
             * live_title : 个人中心测试
             * livetype : 已结束
             * name : 张昊老师
             * room_mun : 126412687
             * startime : 1516155420
             * time : 2018-01-17 10:15:44
             */

            private String back_url;
            private String endtime;
            private String head;
            private String lid;
            private String live_logo;
            private String live_title;
            private String livetype;
            private String name;
            private String room_mun;
            private String startime;
            private String time;

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

            public String getRoom_mun() {
                return room_mun;
            }

            public void setRoom_mun(String room_mun) {
                this.room_mun = room_mun;
            }

            public String getStartime() {
                return startime;
            }

            public void setStartime(String startime) {
                this.startime = startime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
