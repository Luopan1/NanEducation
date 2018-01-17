package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/2 11:11.
 */

public class ActivitiOrder {

    /**
     * code : 1
     * data : {"detail":{"act_logo":"Uploads/Img/ceshi.jpg","act_money":"100","act_name":"成都电影节","cost_type":"1","signup_name":[{"binduser_id":"2000","kid":"6","username":"爸爸"}],"sponsor":["蒙牛赞助1元","伊利赞助0.5元"]}}
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
         * detail : {"act_logo":"Uploads/Img/ceshi.jpg","act_money":"100","act_name":"成都电影节","cost_type":"1","signup_name":[{"binduser_id":"2000","kid":"6","username":"爸爸"}],"sponsor":["蒙牛赞助1元","伊利赞助0.5元"]}
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
             * act_logo : Uploads/Img/ceshi.jpg
             * act_money : 100
             * act_name : 成都电影节
             * cost_type : 1
             * signup_name : [{"binduser_id":"2000","kid":"6","username":"爸爸"}]
             * sponsor : ["蒙牛赞助1元","伊利赞助0.5元"]
             */

            private String act_logo;
            private String act_money;
            private String act_name;
            private String cost_type;
            private List<SignupNameBean> signup_name;
            private List<String> sponsor;

            public String getAct_logo() {
                return act_logo;
            }

            public void setAct_logo(String act_logo) {
                this.act_logo = act_logo;
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

            public String getCost_type() {
                return cost_type;
            }

            public void setCost_type(String cost_type) {
                this.cost_type = cost_type;
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
