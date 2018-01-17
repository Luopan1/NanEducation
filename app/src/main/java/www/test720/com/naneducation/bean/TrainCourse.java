package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/9.
 */

public class TrainCourse {


    /**
     * code : 1
     * data : {"detail":{"c_grade":"1","c_logo":"Uploads/Img/ceshi.jpg","c_stratime":"1507457888","is_love":1,"is_signup":1,"is_take":0,"plan":{"c_downtime":"12:00:20","c_opentime":"10:00:20","c_openwenk":"一,三,五","course_mun":"3","course_time":"45","course_type":"语文","train_address":"重庆街道办5号"},"train_icon":"Uploads/Img/ceshi.jpg","train_lat":"30.42775","train_long":"104.569796","train_name":"爱心教育","userlist":[{"binduser_id":"2000","username":"爸爸"},{"binduser_id":"2001","username":"123456489"}]}}
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
         * detail : {"c_grade":"1","c_logo":"Uploads/Img/ceshi.jpg","c_stratime":"1507457888","is_love":1,"is_signup":1,"is_take":0,"plan":{"c_downtime":"12:00:20","c_opentime":"10:00:20","c_openwenk":"一,三,五","course_mun":"3","course_time":"45","course_type":"语文","train_address":"重庆街道办5号"},"train_icon":"Uploads/Img/ceshi.jpg","train_lat":"30.42775","train_long":"104.569796","train_name":"爱心教育","userlist":[{"binduser_id":"2000","username":"爸爸"},{"binduser_id":"2001","username":"123456489"}]}
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
             * c_grade : 1
             * c_logo : Uploads/Img/ceshi.jpg
             * c_stratime : 1507457888
             * is_love : 1
             * is_signup : 1
             * is_take : 0
             * plan : {"c_downtime":"12:00:20","c_opentime":"10:00:20","c_openwenk":"一,三,五","course_mun":"3","course_time":"45","course_type":"语文","train_address":"重庆街道办5号"}
             * train_icon : Uploads/Img/ceshi.jpg
             * train_lat : 30.42775
             * train_long : 104.569796
             * train_name : 爱心教育
             * userlist : [{"binduser_id":"2000","username":"爸爸"},{"binduser_id":"2001","username":"123456489"}]
             */

            private String c_grade;
            private String c_logo;
            private String c_stratime;
            private int is_love;
            private int is_signup;
            private int is_take;
            private PlanBean plan;
            private String train_icon;
            private String train_lat;
            private String train_long;
            private String train_name;
            private List<UserlistBean> userlist;

            public String getC_grade() {
                return c_grade;
            }

            public void setC_grade(String c_grade) {
                this.c_grade = c_grade;
            }

            public String getC_logo() {
                return c_logo;
            }

            public void setC_logo(String c_logo) {
                this.c_logo = c_logo;
            }

            public String getC_stratime() {
                return c_stratime;
            }

            public void setC_stratime(String c_stratime) {
                this.c_stratime = c_stratime;
            }

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public int getIs_signup() {
                return is_signup;
            }

            public void setIs_signup(int is_signup) {
                this.is_signup = is_signup;
            }

            public int getIs_take() {
                return is_take;
            }

            public void setIs_take(int is_take) {
                this.is_take = is_take;
            }

            public PlanBean getPlan() {
                return plan;
            }

            public void setPlan(PlanBean plan) {
                this.plan = plan;
            }

            public String getTrain_icon() {
                return train_icon;
            }

            public void setTrain_icon(String train_icon) {
                this.train_icon = train_icon;
            }

            public String getTrain_lat() {
                return train_lat;
            }

            public void setTrain_lat(String train_lat) {
                this.train_lat = train_lat;
            }

            public String getTrain_long() {
                return train_long;
            }

            public void setTrain_long(String train_long) {
                this.train_long = train_long;
            }

            public String getTrain_name() {
                return train_name;
            }

            public void setTrain_name(String train_name) {
                this.train_name = train_name;
            }

            public List<UserlistBean> getUserlist() {
                return userlist;
            }

            public void setUserlist(List<UserlistBean> userlist) {
                this.userlist = userlist;
            }

            public static class PlanBean {
                /**
                 * c_downtime : 12:00:20
                 * c_opentime : 10:00:20
                 * c_openwenk : 一,三,五
                 * course_mun : 3
                 * course_time : 45
                 * course_type : 语文
                 * train_address : 重庆街道办5号
                 */

                private String c_downtime;
                private String c_opentime;
                private String c_openwenk;
                private String course_mun;
                private String course_time;
                private String course_type;
                private String train_address;

                public String getC_downtime() {
                    return c_downtime;
                }

                public void setC_downtime(String c_downtime) {
                    this.c_downtime = c_downtime;
                }

                public String getC_opentime() {
                    return c_opentime;
                }

                public void setC_opentime(String c_opentime) {
                    this.c_opentime = c_opentime;
                }

                public String getC_openwenk() {
                    return c_openwenk;
                }

                public void setC_openwenk(String c_openwenk) {
                    this.c_openwenk = c_openwenk;
                }

                public String getCourse_mun() {
                    return course_mun;
                }

                public void setCourse_mun(String course_mun) {
                    this.course_mun = course_mun;
                }

                public String getCourse_time() {
                    return course_time;
                }

                public void setCourse_time(String course_time) {
                    this.course_time = course_time;
                }

                public String getCourse_type() {
                    return course_type;
                }

                public void setCourse_type(String course_type) {
                    this.course_type = course_type;
                }

                public String getTrain_address() {
                    return train_address;
                }

                public void setTrain_address(String train_address) {
                    this.train_address = train_address;
                }
            }

            public static class UserlistBean {
                /**
                 * binduser_id : 2000
                 * username : 爸爸
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