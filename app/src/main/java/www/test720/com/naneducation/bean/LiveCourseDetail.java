package www.test720.com.naneducation.bean;


import java.util.List;

/**
 * @author LuoPan on 2017/11/15 11:36.
 */

public class LiveCourseDetail {


    /**
     * code : 1
     * data : {"detail":{"cash_time":"0小时0分钟","content":"阿莎收到货","count":"2","endtime":"2017-07-14 23:52:57","head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","is_canup":1,"is_free":"1","is_love":1,"is_signup":1,"lid":"1","live_img":"Uploads/Img/ceshi.jpg","live_logo":"Uploads/Img/ceshi.jpg","live_title":"阿莎","name":"9529","opentype":"进入直播课堂","people":"5-12","price":"80.88","sponsor":["蒙牛赞助1.00元","伊利赞助0.50元"],"startime":"2017-11-07 18:44:53","teacher_content":"lhssbsbsb阿萨达","tname":"语文","topList":[{"back_url":"","head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","lid":"4","live_logo":"Uploads/Img/ceshi.jpg","live_title":"哈哈","livetype":"回放","name":"9529","price":"99.99","room_mun":"525"}]}}
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


        /**
         * detail : {"cash_time":"0小时0分钟","content":"阿莎收到货","count":"2","endtime":"2017-07-14 23:52:57","head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","is_canup":1,"is_free":"1","is_love":1,"is_signup":1,"lid":"1","live_img":"Uploads/Img/ceshi.jpg","live_logo":"Uploads/Img/ceshi.jpg","live_title":"阿莎","name":"9529","opentype":"进入直播课堂","people":"5-12","price":"80.88","sponsor":["蒙牛赞助1.00元","伊利赞助0.50元"],"startime":"2017-11-07 18:44:53","teacher_content":"lhssbsbsb阿萨达","tname":"语文","topList":[{"back_url":"","head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","lid":"4","live_logo":"Uploads/Img/ceshi.jpg","live_title":"哈哈","livetype":"回放","name":"9529","price":"99.99","room_mun":"525"}]}
         */

        private DetailBean detail;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean {

            @Override
            public String toString() {
                return "DetailBean{" +
                        "cash_time='" + cash_time + '\'' +
                        ", content='" + content + '\'' +
                        ", count='" + count + '\'' +
                        ", endtime='" + endtime + '\'' +
                        ", head='" + head + '\'' +
                        ", is_canup=" + is_canup +
                        ", is_free='" + is_free + '\'' +
                        ", is_love=" + is_love +
                        ", is_signup=" + is_signup +
                        ", lid='" + lid + '\'' +
                        ", live_img='" + live_img + '\'' +
                        ", live_logo='" + live_logo + '\'' +
                        ", live_title='" + live_title + '\'' +
                        ", name='" + name + '\'' +
                        ", opentype='" + opentype + '\'' +
                        ", people='" + people + '\'' +
                        ", price='" + price + '\'' +
                        ", startime='" + startime + '\'' +
                        ", teacher_content='" + teacher_content + '\'' +
                        ", tname='" + tname + '\'' +
                        ", sponsor=" + sponsor +
                        ", topList=" + topList +
                        '}';
            }

            /**
             * cash_time : 0小时0分钟
             * content : 阿莎收到货
             * count : 2
             * endtime : 2017-07-14 23:52:57
             * head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
             * is_canup : 1
             * is_free : 1
             * is_love : 1
             * is_signup : 1
             * lid : 1
             * live_img : Uploads/Img/ceshi.jpg
             * live_logo : Uploads/Img/ceshi.jpg
             * live_title : 阿莎
             * name : 9529
             * opentype : 进入直播课堂
             * people : 5-12
             * price : 80.88
             * sponsor : ["蒙牛赞助1.00元","伊利赞助0.50元"]
             * startime : 2017-11-07 18:44:53
             * teacher_content : lhssbsbsb阿萨达
             * tname : 语文
             * topList : [{"back_url":"","head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","lid":"4","live_logo":"Uploads/Img/ceshi.jpg","live_title":"哈哈","livetype":"回放","name":"9529","price":"99.99","room_mun":"525"}]
             */

            private String cash_time;
            private String content;
            private String count;
            private String endtime;
            private String head;
            private int is_canup;
            private String is_free;
            private int is_love;
            private int is_signup;
            private String lid;
            private String live_img;
            private String live_logo;
            private String live_title;
            private String name;
            private String opentype;
            private String people;
            private String price;
            private String startime;
            private String teacher_content;
            private String tname;
            private List<String> sponsor;
            private List<TopListBean> topList;

            public String getCash_time() {
                return cash_time;
            }

            public void setCash_time(String cash_time) {
                this.cash_time = cash_time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
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

            public int getIs_canup() {
                return is_canup;
            }

            public void setIs_canup(int is_canup) {
                this.is_canup = is_canup;
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

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public String getLive_img() {
                return live_img;
            }

            public void setLive_img(String live_img) {
                this.live_img = live_img;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOpentype() {
                return opentype;
            }

            public void setOpentype(String opentype) {
                this.opentype = opentype;
            }

            public String getPeople() {
                return people;
            }

            public void setPeople(String people) {
                this.people = people;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStartime() {
                return startime;
            }

            public void setStartime(String startime) {
                this.startime = startime;
            }

            public String getTeacher_content() {
                return teacher_content;
            }

            public void setTeacher_content(String teacher_content) {
                this.teacher_content = teacher_content;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public List<String> getSponsor() {
                return sponsor;
            }

            public void setSponsor(List<String> sponsor) {
                this.sponsor = sponsor;
            }

            public List<TopListBean> getTopList() {
                return topList;
            }

            public void setTopList(List<TopListBean> topList) {
                this.topList = topList;
            }

            public static class TopListBean {
                /**
                 * back_url :
                 * head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
                 * lid : 4
                 * live_logo : Uploads/Img/ceshi.jpg
                 * live_title : 哈哈
                 * livetype : 回放
                 * name : 9529
                 * price : 99.99
                 * room_mun : 525
                 */

                private String back_url;
                private String head;
                private String lid;
                private String live_logo;
                private String live_title;
                private String livetype;
                private String name;
                private String price;
                private String room_mun;

                public String getBack_url() {
                    return back_url;
                }

                public void setBack_url(String back_url) {
                    this.back_url = back_url;
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
            }
        }
    }
}
