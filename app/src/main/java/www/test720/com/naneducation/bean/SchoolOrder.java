package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/10.
 */

public class SchoolOrder {

    /**
     * code : 1
     * data : {"detail":{"s_icon":"Uploads/Img/ceshi.jpg","s_name":"成都七中","signup_name":[{"binduser_id":"2000","kid":"6","username":"爸爸"}],"sponsor":["蒙牛赞助1元","伊利赞助0.5元"],"y_logo":"Uploads/Img/ceshi.jpg","y_name":"一年级","y_xprice":"11020"}}
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
         * detail : {"s_icon":"Uploads/Img/ceshi.jpg","s_name":"成都七中","signup_name":[{"binduser_id":"2000","kid":"6","username":"爸爸"}],"sponsor":["蒙牛赞助1元","伊利赞助0.5元"],"y_logo":"Uploads/Img/ceshi.jpg","y_name":"一年级","y_xprice":"11020"}
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
             * s_icon : Uploads/Img/ceshi.jpg
             * s_name : 成都七中
             * signup_name : [{"binduser_id":"2000","kid":"6","username":"爸爸"}]
             * sponsor : ["蒙牛赞助1元","伊利赞助0.5元"]
             * y_logo : Uploads/Img/ceshi.jpg
             * y_name : 一年级
             * y_xprice : 11020
             */

            private String s_icon;
            private String s_name;
            private String y_logo;
            private String y_name;
            private String y_xprice;
            private List<SignupNameBean> signup_name;
            private List<String> sponsor;

            public String getS_icon() {
                return s_icon;
            }

            public void setS_icon(String s_icon) {
                this.s_icon = s_icon;
            }

            public String getS_name() {
                return s_name;
            }

            public void setS_name(String s_name) {
                this.s_name = s_name;
            }

            public String getY_logo() {
                return y_logo;
            }

            public void setY_logo(String y_logo) {
                this.y_logo = y_logo;
            }

            public String getY_name() {
                return y_name;
            }

            public void setY_name(String y_name) {
                this.y_name = y_name;
            }

            public String getY_xprice() {
                return y_xprice;
            }

            public void setY_xprice(String y_xprice) {
                this.y_xprice = y_xprice;
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
