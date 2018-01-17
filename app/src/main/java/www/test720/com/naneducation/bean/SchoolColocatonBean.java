package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/17 12:08.
 */

public class SchoolColocatonBean {

    /**
     * code : 1
     * data : {"list":[{"relation_id":"1","s_address":"成都市锦江区1街道","s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","time":"2017.11.22 15:52:52"}],"total":2}
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
         * list : [{"relation_id":"1","s_address":"成都市锦江区1街道","s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","time":"2017.11.22 15:52:52"}]
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
             * relation_id : 1
             * s_address : 成都市锦江区1街道
             * s_logo : Uploads/Img/ceshi.jpg
             * s_name : 成都七中
             * time : 2017.11.22 15:52:52
             */

            private String relation_id;
            private String s_address;
            private String s_logo;
            private String s_name;
            private String time;

            public String getRelation_id() {
                return relation_id;
            }

            public void setRelation_id(String relation_id) {
                this.relation_id = relation_id;
            }

            public String getS_address() {
                return s_address;
            }

            public void setS_address(String s_address) {
                this.s_address = s_address;
            }

            public String getS_logo() {
                return s_logo;
            }

            public void setS_logo(String s_logo) {
                this.s_logo = s_logo;
            }

            public String getS_name() {
                return s_name;
            }

            public void setS_name(String s_name) {
                this.s_name = s_name;
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
