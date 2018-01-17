package www.test720.com.naneducation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author LuoPan on 2017/10/30 9:42.
 */

public class LocatedCity implements Serializable {

    /**
     * code : 1
     * data : {"list":[{"c_id":"1","c_name":"成都市","q_name":"武侯区"},{"c_id":"2","c_name":"成都市","q_name":"金牛区"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * c_id : 1
             * c_name : 成都市
             * q_name : 武侯区
             */

            private String c_id;
            private String c_name;
            private String q_name;

            public String getC_id() {
                return c_id;
            }

            public void setC_id(String c_id) {
                this.c_id = c_id;
            }

            public String getC_name() {
                return c_name;
            }

            public void setC_name(String c_name) {
                this.c_name = c_name;
            }

            public String getQ_name() {
                return q_name;
            }

            public void setQ_name(String q_name) {
                this.q_name = q_name;
            }
        }
    }
}
