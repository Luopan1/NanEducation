package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/17 11:41.
 */

public class CiurseBean {


    /**
     * code : 1
     * data : {"list":[{"c_logo":"Uploads/Img/ceshi.jpg","c_name":"一年级语文","is_signup":1,"relation_id":"1","time":"2017.11.22 15:52:38"}],"total":2}
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
         * list : [{"c_logo":"Uploads/Img/ceshi.jpg","c_name":"一年级语文","is_signup":1,"relation_id":"1","time":"2017.11.22 15:52:38"}]
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
             * c_logo : Uploads/Img/ceshi.jpg
             * c_name : 一年级语文
             * is_signup : 1
             * relation_id : 1
             * time : 2017.11.22 15:52:38
             */

            private String c_logo;
            private String c_name;
            private int is_signup;
            private String relation_id;
            private String time;

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

            public int getIs_signup() {
                return is_signup;
            }

            public void setIs_signup(int is_signup) {
                this.is_signup = is_signup;
            }

            public String getRelation_id() {
                return relation_id;
            }

            public void setRelation_id(String relation_id) {
                this.relation_id = relation_id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
