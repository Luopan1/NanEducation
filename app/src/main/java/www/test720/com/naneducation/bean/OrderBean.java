package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/16 15:27.
 */

public class OrderBean {


    /**
     * code : 1
     * data : {"list":[{"is_back":0,"logo":"Uploads/Img/ceshi.jpg","name":"三年级语文","o_bindkey":"6","o_id":"34","o_number":"2017110948310862","o_ordertype":"培训机构","o_time":"1510208193","o_type":"未付款","title":"爱心教育","userlist":["","123456489"]}],"total":8}
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
         * list : [{"is_back":0,"logo":"Uploads/Img/ceshi.jpg","name":"三年级语文","o_bindkey":"6","o_id":"34","o_number":"2017110948310862","o_ordertype":"培训机构","o_time":"1510208193","o_type":"未付款","title":"爱心教育","userlist":["","123456489"]}]
         * total : 8
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
             * is_back : 0
             * logo : Uploads/Img/ceshi.jpg
             * name : 三年级语文
             * o_bindkey : 6
             * o_id : 34
             * o_number : 2017110948310862
             * o_ordertype : 培训机构
             * o_time : 1510208193
             * o_type : 未付款
             * title : 爱心教育
             * userlist : ["","123456489"]
             */

            private int is_back;
            private String logo;
            private String name;
            private String o_bindkey;
            private String o_id;
            private String o_number;
            private String o_ordertype;
            private String o_time;
            private String o_type;
            private String title;
            private List<String> userlist;

            public int getIs_back() {
                return is_back;
            }

            public void setIs_back(int is_back) {
                this.is_back = is_back;
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

            public String getO_bindkey() {
                return o_bindkey;
            }

            public void setO_bindkey(String o_bindkey) {
                this.o_bindkey = o_bindkey;
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

            public String getO_ordertype() {
                return o_ordertype;
            }

            public void setO_ordertype(String o_ordertype) {
                this.o_ordertype = o_ordertype;
            }

            public String getO_time() {
                return o_time;
            }

            public void setO_time(String o_time) {
                this.o_time = o_time;
            }

            public String getO_type() {
                return o_type;
            }

            public void setO_type(String o_type) {
                this.o_type = o_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<String> getUserlist() {
                return userlist;
            }

            public void setUserlist(List<String> userlist) {
                this.userlist = userlist;
            }
        }
    }
}
