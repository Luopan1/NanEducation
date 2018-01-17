package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/22 17:21.
 */

public class TrrainAddress {


    /**
     * code : 1
     * data : {"list":[{"a_id":"1","a_lat":"104.072371","a_long":"30.552305","a_name":"爱心教育"},{"a_id":"2","a_lat":"30.52575","a_long":"104.569796","a_name":"美博教育"},{"a_id":"3","a_lat":"30.62875","a_long":"104.569796","a_name":"宝贝教育"},{"a_id":"6","a_lat":"30.639682","a_long":"104.073098","a_name":"邹机构"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * a_id : 1
             * a_lat : 104.072371
             * a_long : 30.552305
             * a_name : 爱心教育
             */

            private String a_id;
            private String a_lat;
            private String a_long;
            private String a_name;

            public String getA_id() {
                return a_id;
            }

            public void setA_id(String a_id) {
                this.a_id = a_id;
            }

            public String getA_lat() {
                return a_lat;
            }

            public void setA_lat(String a_lat) {
                this.a_lat = a_lat;
            }

            public String getA_long() {
                return a_long;
            }

            public void setA_long(String a_long) {
                this.a_long = a_long;
            }

            public String getA_name() {
                return a_name;
            }

            public void setA_name(String a_name) {
                this.a_name = a_name;
            }
        }
    }
}
