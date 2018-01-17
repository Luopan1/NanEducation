package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/1 14:33.
 */

public class GroupActivity {


    /**
     * code : 1
     * data : {"list":[{"act_address":"透视镜","act_endtime":"1515809464","act_id":"6","act_lat":"30.61507","act_long":"104.040759","act_money":"0.00","act_name":"3","act_startime":"1515723002","cost_type":"2","distance":7.6,"logo":"Uploads/Img/2018-01-12/(试看)教师资格证(幼教)-综合素质.png","type":"进行中"},{"act_address":"你提一提","act_endtime":"1516500328","act_id":"5","act_lat":"30.636451","act_long":"104.048951","act_money":"10.00","act_name":"就会有共鸣","act_startime":"1515809126","cost_type":"1","distance":9.6,"logo":"Uploads/Img/2018-01-12/1509618803988365.jpg","type":"可报名"},{"act_address":"后悔","act_endtime":"1515981792","act_id":"4","act_lat":"30.585665","act_long":"104.053335","act_money":"0.00","act_name":"听VPN名字明厅OMG宫廷戏明听你 滚你听我话跟你提我怀疑星宿海","act_startime":"1515808989","cost_type":"0","distance":4.1,"logo":"Uploads/Img/2018-01-12/1515722629720562.jpg","type":"可报名"}],"total":1,"typeList":[{"name":"科技","tid":"29"},{"name":"交流","tid":"28"},{"name":"阅读","tid":"24"},{"name":"音乐","tid":"25"},{"name":"绘画","tid":"26"},{"name":"朗诵","tid":"27"}]}
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
         * list : [{"act_address":"透视镜","act_endtime":"1515809464","act_id":"6","act_lat":"30.61507","act_long":"104.040759","act_money":"0.00","act_name":"3","act_startime":"1515723002","cost_type":"2","distance":7.6,"logo":"Uploads/Img/2018-01-12/(试看)教师资格证(幼教)-综合素质.png","type":"进行中"},{"act_address":"你提一提","act_endtime":"1516500328","act_id":"5","act_lat":"30.636451","act_long":"104.048951","act_money":"10.00","act_name":"就会有共鸣","act_startime":"1515809126","cost_type":"1","distance":9.6,"logo":"Uploads/Img/2018-01-12/1509618803988365.jpg","type":"可报名"},{"act_address":"后悔","act_endtime":"1515981792","act_id":"4","act_lat":"30.585665","act_long":"104.053335","act_money":"0.00","act_name":"听VPN名字明厅OMG宫廷戏明听你 滚你听我话跟你提我怀疑星宿海","act_startime":"1515808989","cost_type":"0","distance":4.1,"logo":"Uploads/Img/2018-01-12/1515722629720562.jpg","type":"可报名"}]
         * total : 1
         * typeList : [{"name":"科技","tid":"29"},{"name":"交流","tid":"28"},{"name":"阅读","tid":"24"},{"name":"音乐","tid":"25"},{"name":"绘画","tid":"26"},{"name":"朗诵","tid":"27"}]
         */

        private int total;
        private List<ListBean> list;
        private List<TypeListBean> typeList;

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

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class ListBean {
            /**
             * act_address : 透视镜
             * act_endtime : 1515809464
             * act_id : 6
             * act_lat : 30.61507
             * act_long : 104.040759
             * act_money : 0.00
             * act_name : 3
             * act_startime : 1515723002
             * cost_type : 2
             * distance : 7.6
             * logo : Uploads/Img/2018-01-12/(试看)教师资格证(幼教)-综合素质.png
             * type : 进行中
             */

            private String act_address;
            private String act_endtime;
            private String act_id;
            private String act_lat;
            private String act_long;
            private String act_money;
            private String act_name;
            private String act_startime;
            private String cost_type;
            private double distance;
            private String logo;
            private String type;

            public String getAct_address() {
                return act_address;
            }

            public void setAct_address(String act_address) {
                this.act_address = act_address;
            }

            public String getAct_endtime() {
                return act_endtime;
            }

            public void setAct_endtime(String act_endtime) {
                this.act_endtime = act_endtime;
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

            public String getAct_startime() {
                return act_startime;
            }

            public void setAct_startime(String act_startime) {
                this.act_startime = act_startime;
            }

            public String getCost_type() {
                return cost_type;
            }

            public void setCost_type(String cost_type) {
                this.cost_type = cost_type;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
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

        public static class TypeListBean {
            public TypeListBean(String name, String tid) {
                this.name = name;
                this.tid = tid;
            }

            /**
             * name : 科技
             * tid : 29
             */

            private String name;
            private String tid;

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
        }
    }
}
