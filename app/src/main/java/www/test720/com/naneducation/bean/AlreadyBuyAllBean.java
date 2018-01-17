package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/17 14:22.
 */

public class AlreadyBuyAllBean {


    /**
     * code : 1
     * data : {"list":[{"c_logo":"Uploads/Img/ceshi.jpg","c_name":"套课二","cid":"2","tc_head":"Uploads/Img/ceshi.jpg","tc_name":"孙大圣","time":"2017-11-22 16:40:28"}],"total":1}
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
         * list : [{"c_logo":"Uploads/Img/ceshi.jpg","c_name":"套课二","cid":"2","tc_head":"Uploads/Img/ceshi.jpg","tc_name":"孙大圣","time":"2017-11-22 16:40:28"}]
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
             * c_logo : Uploads/Img/ceshi.jpg
             * c_name : 套课二
             * cid : 2
             * tc_head : Uploads/Img/ceshi.jpg
             * tc_name : 孙大圣
             * time : 2017-11-22 16:40:28
             */

            private String c_logo;
            private String c_name;
            private String cid;
            private String tc_head;
            private String tc_name;
            private String time;

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

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
