package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/15 17:26.
 */

public class AllCourse {

    /**
     * code : 1
     * data : {"list":[{"c_logo":"Uploads/Img/2018-01-15/5a5c1fe82c1e6.jpg","c_name":"一年级语文全课程","c_price":"288.00","cid":"7","count":"2","is_free":"0","tc_head":"Uploads/Img/2018-01-15/5a5c1cf17d517.jpg","tc_name":"张昊老师"},{"c_logo":"Uploads/Img/2018-01-16/5a5d6ace63782.jpg","c_name":"语文课二年级整套","c_price":"108.00","cid":"8","count":0,"is_free":"0","tc_head":"Uploads/Img/2018-01-15/5a5c52c2a3050.jpg","tc_name":"穆老师"}],"muen":[{"name":"文化","tid":"35","zi":[{"name":"语文","tid":"38","zid":[{"name":"一年级","pid":"38","tid":"41"},{"name":"二年级","pid":"38","tid":"42"},{"name":"三年级","pid":"38","tid":"43"},{"name":"四年级","pid":"38","tid":"44"}]},{"name":"数学","tid":"39","zid":[{"name":"一年级","pid":"39","tid":"45"}]},{"name":"英语","tid":"40","zid":[]}]},{"name":"体育","tid":"36","zi":[]},{"name":"艺术","tid":"37","zi":[]}]}
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
        private List<ListBean> list;
        private List<MuenBean> muen;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<MuenBean> getMuen() {
            return muen;
        }

        public void setMuen(List<MuenBean> muen) {
            this.muen = muen;
        }

        public static class ListBean {
            /**
             * c_logo : Uploads/Img/2018-01-15/5a5c1fe82c1e6.jpg
             * c_name : 一年级语文全课程
             * c_price : 288.00
             * cid : 7
             * count : 2
             * is_free : 0
             * tc_head : Uploads/Img/2018-01-15/5a5c1cf17d517.jpg
             * tc_name : 张昊老师
             */

            private String c_logo;
            private String c_name;
            private String c_price;
            private String cid;
            private String count;
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

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
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

        public static class MuenBean {
            /**
             * name : 文化
             * tid : 35
             * zi : [{"name":"语文","tid":"38","zid":[{"name":"一年级","pid":"38","tid":"41"},{"name":"二年级","pid":"38","tid":"42"},{"name":"三年级","pid":"38","tid":"43"},{"name":"四年级","pid":"38","tid":"44"}]},{"name":"数学","tid":"39","zid":[{"name":"一年级","pid":"39","tid":"45"}]},{"name":"英语","tid":"40","zid":[]}]
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
                 * tid : 38
                 * zid : [{"name":"一年级","pid":"38","tid":"41"},{"name":"二年级","pid":"38","tid":"42"},{"name":"三年级","pid":"38","tid":"43"},{"name":"四年级","pid":"38","tid":"44"}]
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
                     * pid : 38
                     * tid : 41
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
