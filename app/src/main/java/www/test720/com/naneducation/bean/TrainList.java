package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/8.
 */

public class TrainList {

    /**
     * code : 1
     * data : {"list":[{"c_downtime":"09:40:00","c_id":"15","c_logo":"Uploads/Img/2018-01-15/5a5c53f457107.jpg","c_name":"一年级语文","c_oldprice":"1000.00","c_opentime":"09:00:00","c_openwenk":"六,日","c_price":"850.00"},{"c_downtime":"15:50:52","c_id":"16","c_logo":"Uploads/Img/2018-01-15/5a5c545347c37.jpg","c_name":"二年级语文","c_oldprice":"15.00","c_opentime":"15:11:50","c_openwenk":"四,五","c_price":"12.00"}],"muen":[{"name":"语文","t_id":"27","zi":[{"name":"一年级","t_id":"30"},{"name":"二年级","t_id":"31"}]}]}
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
             * c_downtime : 09:40:00
             * c_id : 15
             * c_logo : Uploads/Img/2018-01-15/5a5c53f457107.jpg
             * c_name : 一年级语文
             * c_oldprice : 1000.00
             * c_opentime : 09:00:00
             * c_openwenk : 六,日
             * c_price : 850.00
             */

            private String c_downtime;
            private String c_id;
            private String c_logo;
            private String c_name;
            private String c_oldprice;
            private String c_opentime;
            private String c_openwenk;
            private String c_price;

            public String getC_downtime() {
                return c_downtime;
            }

            public void setC_downtime(String c_downtime) {
                this.c_downtime = c_downtime;
            }

            public String getC_id() {
                return c_id;
            }

            public void setC_id(String c_id) {
                this.c_id = c_id;
            }

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

            public String getC_oldprice() {
                return c_oldprice;
            }

            public void setC_oldprice(String c_oldprice) {
                this.c_oldprice = c_oldprice;
            }

            public String getC_opentime() {
                return c_opentime;
            }

            public void setC_opentime(String c_opentime) {
                this.c_opentime = c_opentime;
            }

            public String getC_openwenk() {
                return c_openwenk;
            }

            public void setC_openwenk(String c_openwenk) {
                this.c_openwenk = c_openwenk;
            }

            public String getC_price() {
                return c_price;
            }

            public void setC_price(String c_price) {
                this.c_price = c_price;
            }
        }

        public static class MuenBean {
            /**
             * name : 语文
             * t_id : 27
             * zi : [{"name":"一年级","t_id":"30"},{"name":"二年级","t_id":"31"}]
             */

            private String name;
            private String t_id;
            private List<ZiBean> zi;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getT_id() {
                return t_id;
            }

            public void setT_id(String t_id) {
                this.t_id = t_id;
            }

            public List<ZiBean> getZi() {
                return zi;
            }

            public void setZi(List<ZiBean> zi) {
                this.zi = zi;
            }

            public static class ZiBean {
                /**
                 * name : 一年级
                 * t_id : 30
                 */

                private String name;
                private String t_id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getT_id() {
                    return t_id;
                }

                public void setT_id(String t_id) {
                    this.t_id = t_id;
                }
            }
        }
    }
}
