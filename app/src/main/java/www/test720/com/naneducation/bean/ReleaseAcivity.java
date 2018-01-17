package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/21 11:25.
 */

public class ReleaseAcivity {


    /**
     * code : 1
     * data : {"list":[{"act_address":"天府三街","act_id":"59","act_lat":"30.66056","act_long":"104.072235","act_money":"0.00","act_name":"1111","distance":12,"is_check":"审核成功","logo":"Uploads/Img/2017-11-22/21576518_1480419388717403_517680529970036736_n.jpg","type":"进行中"}],"total":1}
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
         * list : [{"act_address":"天府三街","act_id":"59","act_lat":"30.66056","act_long":"104.072235","act_money":"0.00","act_name":"1111","distance":12,"is_check":"审核成功","logo":"Uploads/Img/2017-11-22/21576518_1480419388717403_517680529970036736_n.jpg","type":"进行中"}]
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
             * act_address : 天府三街
             * act_id : 59
             * act_lat : 30.66056
             * act_long : 104.072235
             * act_money : 0.00
             * act_name : 1111
             * distance : 12
             * is_check : 审核成功
             * logo : Uploads/Img/2017-11-22/21576518_1480419388717403_517680529970036736_n.jpg
             * type : 进行中
             */

            private String act_address;
            private String act_id;
            private String act_lat;
            private String act_long;
            private String act_money;
            private String act_name;
            private int distance;
            private String is_check;
            private String logo;
            private String type;

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

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
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
        }
    }
}
