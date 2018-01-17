package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/9.
 */

public class TrainOrder {

    /**
     * code : 1
     * data : {"detail":{"c_logo":"Uploads/Img/ceshi.jpg","c_name":"一年级语文","c_price":"25","signup_name":[{"binduser_id":"2000","kid":"6","username":"爸爸"}],"sponsor":["蒙牛赞助1元","伊利赞助0.5元"],"train_icon":"Uploads/Img/ceshi.jpg","train_name":"爱心教育"}}
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
         * detail : {"c_logo":"Uploads/Img/ceshi.jpg","c_name":"一年级语文","c_price":"25","signup_name":[{"binduser_id":"2000","kid":"6","username":"爸爸"}],"sponsor":["蒙牛赞助1元","伊利赞助0.5元"],"train_icon":"Uploads/Img/ceshi.jpg","train_name":"爱心教育"}
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
             * c_logo : Uploads/Img/ceshi.jpg
             * c_name : 一年级语文
             * c_price : 25
             * signup_name : [{"binduser_id":"2000","kid":"6","username":"爸爸"}]
             * sponsor : ["蒙牛赞助1元","伊利赞助0.5元"]
             * train_icon : Uploads/Img/ceshi.jpg
             * train_name : 爱心教育
             */

            private String c_logo;
            private String c_name;
            private String c_price;
            private String train_icon;
            private String train_name;
            private List<SignupNameBean> signup_name;
            private List<String> sponsor;

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

            public String getTrain_icon() {
                return train_icon;
            }

            public void setTrain_icon(String train_icon) {
                this.train_icon = train_icon;
            }

            public String getTrain_name() {
                return train_name;
            }

            public void setTrain_name(String train_name) {
                this.train_name = train_name;
            }

            public List<SignupNameBean> getSignup_name() {
                return signup_name;
            }

            public void setSignup_name(List<SignupNameBean> signup_name) {
                this.signup_name = signup_name;
            }

            public List<String> getSponsor() {
                return sponsor;
            }

            public void setSponsor(List<String> sponsor) {
                this.sponsor = sponsor;
            }

            public static class SignupNameBean {

                public SignupNameBean(String binduser_id, String kid, String username) {

                    this.binduser_id = binduser_id;
                    this.kid = kid;
                    this.username = username;
                }

                /**
                 * binduser_id : 2000
                 * kid : 6
                 * username : 爸爸
                 */


                private String binduser_id;
                private String kid;
                private String username;

                public String getBinduser_id() {
                    return binduser_id;
                }

                public void setBinduser_id(String binduser_id) {
                    this.binduser_id = binduser_id;
                }

                public String getKid() {
                    return kid;
                }

                public void setKid(String kid) {
                    this.kid = kid;
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
