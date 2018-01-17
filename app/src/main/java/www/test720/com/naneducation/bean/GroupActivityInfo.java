package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/2 9:09.
 */

public class GroupActivityInfo {

    /**
     * code : 1
     * data : {"detail":{"act_address":"成都市体育馆","act_content":"哈哈哈快来哦","act_count":"0","act_endtime":"2017.10.11 09:15:55","act_id":"2","act_money":"100","act_mun":"500","act_name":"成都电影节","act_phone":"18681373742","act_startime":"2017.10.11 09:15:54","cost_type":"1","head":"Uploads/Img/2017-10-30/59f6dacd5d31e.jpg","imgList":["Uploads/Img/ceshi.jpg"],"is_love":0,"is_sign":0,"name":"2147483647","type":"已结束"}}
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
         * detail : {"act_address":"成都市体育馆","act_content":"哈哈哈快来哦","act_count":"0","act_endtime":"2017.10.11 09:15:55","act_id":"2","act_money":"100","act_mun":"500","act_name":"成都电影节","act_phone":"18681373742","act_startime":"2017.10.11 09:15:54","cost_type":"1","head":"Uploads/Img/2017-10-30/59f6dacd5d31e.jpg","imgList":["Uploads/Img/ceshi.jpg"],"is_love":0,"is_sign":0,"name":"2147483647","type":"已结束"}
         */

        private DetailBean detail;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean {


            /**
             * act_address : 成都市体育馆
             * act_content : 哈哈哈快来哦
             * act_count : 0
             * act_endtime : 2017.10.11 09:15:55
             * act_id : 2
             * act_money : 100
             * act_mun : 500
             * act_name : 成都电影节
             * act_phone : 18681373742
             * act_startime : 2017.10.11 09:15:54
             * cost_type : 1
             * head : Uploads/Img/2017-10-30/59f6dacd5d31e.jpg
             * imgList : ["Uploads/Img/ceshi.jpg"]
             * is_love : 0
             * is_sign : 0
             * name : 2147483647
             * type : 已结束
             */


            private String act_address;
            private String act_content;
            private String act_count;
            private String act_endtime;
            private String act_id;
            private String act_money;
            private String act_mun;
            private String act_name;
            private String act_phone;
            private String act_startime;
            private String cost_type;
            private String head;
            private int is_love;
            private int is_sign;
            private String name;
            private String type;
            private List<String> imgList;

            public String getAct_address() {
                return act_address;
            }

            public void setAct_address(String act_address) {
                this.act_address = act_address;
            }

            public String getAct_content() {
                return act_content;
            }

            public void setAct_content(String act_content) {
                this.act_content = act_content;
            }

            public String getAct_count() {
                return act_count;
            }

            public void setAct_count(String act_count) {
                this.act_count = act_count;
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

            public String getAct_money() {
                return act_money;
            }

            public void setAct_money(String act_money) {
                this.act_money = act_money;
            }

            public String getAct_mun() {
                return act_mun;
            }

            public void setAct_mun(String act_mun) {
                this.act_mun = act_mun;
            }

            public String getAct_name() {
                return act_name;
            }

            public void setAct_name(String act_name) {
                this.act_name = act_name;
            }

            public String getAct_phone() {
                return act_phone;
            }

            public void setAct_phone(String act_phone) {
                this.act_phone = act_phone;
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

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public int getIs_sign() {
                return is_sign;
            }

            public void setIs_sign(int is_sign) {
                this.is_sign = is_sign;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<String> getImgList() {
                return imgList;
            }

            public void setImgList(List<String> imgList) {
                this.imgList = imgList;
            }
        }
    }
}
