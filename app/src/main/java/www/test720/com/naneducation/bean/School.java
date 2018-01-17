package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/10.
 */

public class School {

    /**
     * code : 1
     * data : {"list":[{"distance":49.6,"s_address":"成都市锦江区1街道","s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","sid":"1","signUpmun":188}],"naturelist":[{"name":"1级性质","nid":"1"},{"name":"2级性质","nid":"2"},{"name":"3级性质","nid":"3"},{"name":"4级性质","nid":"4"}],"total":3,"typelist":[{"name":"高等院校","tid":"1"},{"name":"中等院校","tid":"2"},{"name":"一级院校","tid":"3"},{"name":"二级院校","tid":"4"}]}
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
         * list : [{"distance":49.6,"s_address":"成都市锦江区1街道","s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","sid":"1","signUpmun":188}]
         * naturelist : [{"name":"1级性质","nid":"1"},{"name":"2级性质","nid":"2"},{"name":"3级性质","nid":"3"},{"name":"4级性质","nid":"4"}]
         * total : 3
         * typelist : [{"name":"高等院校","tid":"1"},{"name":"中等院校","tid":"2"},{"name":"一级院校","tid":"3"},{"name":"二级院校","tid":"4"}]
         */

        private int total;
        private List<ListBean> list;
        private List<NaturelistBean> naturelist;
        private List<TypelistBean> typelist;

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

        public List<NaturelistBean> getNaturelist() {
            return naturelist;
        }

        public void setNaturelist(List<NaturelistBean> naturelist) {
            this.naturelist = naturelist;
        }

        public List<TypelistBean> getTypelist() {
            return typelist;
        }

        public void setTypelist(List<TypelistBean> typelist) {
            this.typelist = typelist;
        }

        public static class ListBean {
            /**
             * distance : 49.6
             * s_address : 成都市锦江区1街道
             * s_logo : Uploads/Img/ceshi.jpg
             * s_name : 成都七中
             * sid : 1
             * signUpmun : 188
             */

            private double distance;
            private String s_address;
            private String s_logo;
            private String s_name;
            private String sid;
            private int signUpmun;

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
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

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public int getSignUpmun() {
                return signUpmun;
            }

            public void setSignUpmun(int signUpmun) {
                this.signUpmun = signUpmun;
            }
        }

        public static class NaturelistBean {
            public NaturelistBean(String name, String nid) {
                this.name = name;
                this.nid = nid;
            }

            /**
             * name : 1级性质
             * nid : 1
             */


            private String name;
            private String nid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNid() {
                return nid;
            }

            public void setNid(String nid) {
                this.nid = nid;
            }
        }

        public static class TypelistBean {
            public TypelistBean(String name, String tid) {
                this.name = name;
                this.tid = tid;
            }

            /**
             * name : 高等院校
             * tid : 1
             */


            private String name;
            private String tid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }
        }
    }
}
