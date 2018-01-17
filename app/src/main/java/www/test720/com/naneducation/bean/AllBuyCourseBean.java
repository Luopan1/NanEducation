package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/20 9:19.
 */

public class AllBuyCourseBean {


    /**
     * code : 1
     * data : {"list":[{"c_id":"1","c_logo":"Uploads/Img/ceshi.jpg","c_name":"一年级语文","order_id":"35","time":"2017-11-09 14:24:14","userlist":[{"binduser_id":"2000","username":""},{"binduser_id":"2001","username":"123456489"}]}],"total":2}
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
         * list : [{"c_id":"1","c_logo":"Uploads/Img/ceshi.jpg","c_name":"一年级语文","order_id":"35","time":"2017-11-09 14:24:14","userlist":[{"binduser_id":"2000","username":""},{"binduser_id":"2001","username":"123456489"}]}]
         * total : 2
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
             * c_id : 1
             * c_logo : Uploads/Img/ceshi.jpg
             * c_name : 一年级语文
             * order_id : 35
             * time : 2017-11-09 14:24:14
             * userlist : [{"binduser_id":"2000","username":""},{"binduser_id":"2001","username":"123456489"}]
             */

            private String c_id;
            private String c_logo;
            private String c_name;
            private String order_id;
            private String time;
            private List<UserlistBean> userlist;

            public String getC_id() {
                return c_id;
            }

            public void setC_id(String c_id) {
                this.c_id = c_id;
            }

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

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public List<UserlistBean> getUserlist() {
                return userlist;
            }

            public void setUserlist(List<UserlistBean> userlist) {
                this.userlist = userlist;
            }

            public static class UserlistBean {
                /**
                 * binduser_id : 2000
                 * username :
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
