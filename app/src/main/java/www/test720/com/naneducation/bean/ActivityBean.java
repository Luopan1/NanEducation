package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/11.
 */

public class ActivityBean {


    /**
     * code : 1
     * data : {"list":[{"act_address":"兰桂坊","act_id":"57","act_money":"0.00","act_name":"CNM","distance":11351.5,"is_check":"审核中","logo":"Uploads/Img/2017-10-26/1508999838842324.jpg","type":"未开始"},{"act_address":"成都市体育馆","act_id":"2","act_lat":"30.62775","act_long":"104.479396","act_money":"100.00","act_name":"成都电影节","distance":39.9,"is_check":"审核成功","logo":"Uploads/Img/ceshi.jpg","type":"未开始"},{"act_address":"成都市体育馆","act_id":"3","act_lat":"30.62775","act_long":"104.479396","act_money":"80.00","act_name":"成都电影节","distance":39.9,"is_check":"审核成功","logo":"Uploads/Img/ceshi.jpg","type":"未开始"},{"act_address":"成都市体育馆","act_id":"4","act_lat":"30.62775","act_long":"104.479396","act_money":"30.00","act_name":"成都电影节","distance":39.9,"is_check":"审核成功","logo":"Uploads/Img/ceshi.jpg","type":"未开始"}],"total":1}
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
         * list : [{"act_address":"兰桂坊","act_id":"57","act_money":"0.00","act_name":"CNM","distance":11351.5,"is_check":"审核中","logo":"Uploads/Img/2017-10-26/1508999838842324.jpg","type":"未开始"},{"act_address":"成都市体育馆","act_id":"2","act_lat":"30.62775","act_long":"104.479396","act_money":"100.00","act_name":"成都电影节","distance":39.9,"is_check":"审核成功","logo":"Uploads/Img/ceshi.jpg","type":"未开始"},{"act_address":"成都市体育馆","act_id":"3","act_lat":"30.62775","act_long":"104.479396","act_money":"80.00","act_name":"成都电影节","distance":39.9,"is_check":"审核成功","logo":"Uploads/Img/ceshi.jpg","type":"未开始"},{"act_address":"成都市体育馆","act_id":"4","act_lat":"30.62775","act_long":"104.479396","act_money":"30.00","act_name":"成都电影节","distance":39.9,"is_check":"审核成功","logo":"Uploads/Img/ceshi.jpg","type":"未开始"}]
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
             * act_address : 兰桂坊
             * act_id : 57
             * act_money : 0.00
             * act_name : CNM
             * distance : 11351.5
             * is_check : 审核中
             * logo : Uploads/Img/2017-10-26/1508999838842324.jpg
             * type : 未开始
             * act_lat : 30.62775
             * act_long : 104.479396
             */

            private String act_address;
            private String act_id;
            private String act_money;
            private String act_name;
            private double distance;
            private String is_check;
            private String logo;
            private String type;
            private String act_lat;
            private String act_long;

            public String getAct_address() {
                return act_address;
            }

            public void setAct_address(String act_address) {
                this.act_address = act_address;
            }

            public String getAct_id() {
                return act_id;
            }

            public void setAct_id(String act_id) {
                this.act_id = act_id;
            }

            public String getAct_money() {
                return act_money;
            }

            public void setAct_money(String act_money) {
                this.act_money = act_money;
            }

            public String getAct_name() {
                return act_name;
            }

            public void setAct_name(String act_name) {
                this.act_name = act_name;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public String getIs_check() {
                return is_check;
            }

            public void setIs_check(String is_check) {
                this.is_check = is_check;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAct_lat() {
                return act_lat;
            }

            public void setAct_lat(String act_lat) {
                this.act_lat = act_lat;
            }

            public String getAct_long() {
                return act_long;
            }

            public void setAct_long(String act_long) {
                this.act_long = act_long;
            }
        }
    }
}
