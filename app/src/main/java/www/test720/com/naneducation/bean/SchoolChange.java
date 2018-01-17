package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/10.
 */

public class SchoolChange {

    /**
     * code : 1
     * data : {"list":[{"distance":49.6,"meanPrice":10957,"s_address":"成都市锦江区1街道","s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","sid":"1","signUpmun":188}],"total":1}
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
         * list : [{"distance":49.6,"meanPrice":10957,"s_address":"成都市锦江区1街道","s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","sid":"1","signUpmun":188}]
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
             * distance : 49.6
             * meanPrice : 10957
             * s_address : 成都市锦江区1街道
             * s_logo : Uploads/Img/ceshi.jpg
             * s_name : 成都七中
             * sid : 1
             * signUpmun : 188
             */

            private double distance;
            private int meanPrice;
            private String s_address;
            private String s_logo;
            private String s_name;
            private String sid;
            private int signUpmun;

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public int getMeanPrice() {
                return meanPrice;
            }

            public void setMeanPrice(int meanPrice) {
                this.meanPrice = meanPrice;
            }

            public String getS_address() {
                return s_address;
            }

            public void setS_address(String s_address) {
                this.s_address = s_address;
            }

            public String getS_logo() {
                return s_logo;
            }

            public void setS_logo(String s_logo) {
                this.s_logo = s_logo;
            }

            public String getS_name() {
                return s_name;
            }

            public void setS_name(String s_name) {
                this.s_name = s_name;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public int getSignUpmun() {
                return signUpmun;
            }

            public void setSignUpmun(int signUpmun) {
                this.signUpmun = signUpmun;
            }
        }
    }
}
