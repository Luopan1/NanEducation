package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/16 16:10.
 */

public class OrderInfo {


    /**
     * code : 1
     * data : {"detail":{"catime":591,"integral":3,"logo":"Uploads/Img/2018-01-15/5a5c24afa9c09.jpg","name":"语文一年级","o_id":"213","o_number":"2018011771378833","o_payprice":"7.00","o_paytime":"1970-01-01 08:00:00","o_time":"2018-01-17 09:41:17","phone":"18108126352","price":"10.00","sponsor":["助学:伊利1.00元","蒙牛2.00元"],"title_logo":"Uploads/Img/2018-01-15/5a5c22b22a7c7.jpg","title_name":"信天教育","userlist":[{"binduser_id":"9000","username":"工体休息XP一爪子是我给转切转切行个工作以后"}]}}
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
         * detail : {"catime":591,"integral":3,"logo":"Uploads/Img/2018-01-15/5a5c24afa9c09.jpg","name":"语文一年级","o_id":"213","o_number":"2018011771378833","o_payprice":"7.00","o_paytime":"1970-01-01 08:00:00","o_time":"2018-01-17 09:41:17","phone":"18108126352","price":"10.00","sponsor":["助学:伊利1.00元","蒙牛2.00元"],"title_logo":"Uploads/Img/2018-01-15/5a5c22b22a7c7.jpg","title_name":"信天教育","userlist":[{"binduser_id":"9000","username":"工体休息XP一爪子是我给转切转切行个工作以后"}]}
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
             * catime : 591
             * integral : 3
             * logo : Uploads/Img/2018-01-15/5a5c24afa9c09.jpg
             * name : 语文一年级
             * o_id : 213
             * o_number : 2018011771378833
             * o_payprice : 7.00
             * o_paytime : 1970-01-01 08:00:00
             * o_time : 2018-01-17 09:41:17
             * phone : 18108126352
             * price : 10.00
             * sponsor : ["助学:伊利1.00元","蒙牛2.00元"]
             * title_logo : Uploads/Img/2018-01-15/5a5c22b22a7c7.jpg
             * title_name : 信天教育
             * userlist : [{"binduser_id":"9000","username":"工体休息XP一爪子是我给转切转切行个工作以后"}]
             */

            private int catime;
            private int integral;
            private String logo;
            private String name;
            private String o_id;
            private String o_number;
            private String o_payprice;
            private String o_paytime;
            private String o_time;
            private String phone;
            private String price;
            private String title_logo;
            private String title_name;
            private List<String> sponsor;
            private List<UserlistBean> userlist;

            public int getCatime() {
                return catime;
            }

            public void setCatime(int catime) {
                this.catime = catime;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getO_id() {
                return o_id;
            }

            public void setO_id(String o_id) {
                this.o_id = o_id;
            }

            public String getO_number() {
                return o_number;
            }

            public void setO_number(String o_number) {
                this.o_number = o_number;
            }

            public String getO_payprice() {
                return o_payprice;
            }

            public void setO_payprice(String o_payprice) {
                this.o_payprice = o_payprice;
            }

            public String getO_paytime() {
                return o_paytime;
            }

            public void setO_paytime(String o_paytime) {
                this.o_paytime = o_paytime;
            }

            public String getO_time() {
                return o_time;
            }

            public void setO_time(String o_time) {
                this.o_time = o_time;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTitle_logo() {
                return title_logo;
            }

            public void setTitle_logo(String title_logo) {
                this.title_logo = title_logo;
            }

            public String getTitle_name() {
                return title_name;
            }

            public void setTitle_name(String title_name) {
                this.title_name = title_name;
            }

            public List<String> getSponsor() {
                return sponsor;
            }

            public void setSponsor(List<String> sponsor) {
                this.sponsor = sponsor;
            }

            public List<UserlistBean> getUserlist() {
                return userlist;
            }

            public void setUserlist(List<UserlistBean> userlist) {
                this.userlist = userlist;
            }

            public static class UserlistBean {
                /**
                 * binduser_id : 9000
                 * username : 工体休息XP一爪子是我给转切转切行个工作以后
                 */

                private String binduser_id;
                private String username;

                public String getBinduser_id() {
                    return binduser_id;
                }

                public void setBinduser_id(String binduser_id) {
                    this.binduser_id = binduser_id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
            }
        }
    }
}
