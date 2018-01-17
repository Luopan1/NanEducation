package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/10.
 */

public class SchoolInfo {


    /**
     * code : 1
     * data : {"detail":{"gradeList":[{"overmun":68,"y_id":"1","y_name":"一年级","y_xprice":"11020","y_yprice":"12000"},{"overmun":77,"y_id":"2","y_name":"二年级","y_xprice":"10800","y_yprice":"12000"},{"overmun":43,"y_id":"3","y_name":"三年级","y_xprice":"11050","y_yprice":"12000"}],"is_love":0,"s_address":"成都市锦江区1街道","s_icon":"Uploads/Img/ceshi.jpg","s_lat":"30.42775","s_logo":"Uploads/Img/ceshi.jpg","s_long":"104.569796","s_name":"成都七中","s_phone":"18681373742"}}
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
         * detail : {"gradeList":[{"overmun":68,"y_id":"1","y_name":"一年级","y_xprice":"11020","y_yprice":"12000"},{"overmun":77,"y_id":"2","y_name":"二年级","y_xprice":"10800","y_yprice":"12000"},{"overmun":43,"y_id":"3","y_name":"三年级","y_xprice":"11050","y_yprice":"12000"}],"is_love":0,"s_address":"成都市锦江区1街道","s_icon":"Uploads/Img/ceshi.jpg","s_lat":"30.42775","s_logo":"Uploads/Img/ceshi.jpg","s_long":"104.569796","s_name":"成都七中","s_phone":"18681373742"}
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
             * gradeList : [{"overmun":68,"y_id":"1","y_name":"一年级","y_xprice":"11020","y_yprice":"12000"},{"overmun":77,"y_id":"2","y_name":"二年级","y_xprice":"10800","y_yprice":"12000"},{"overmun":43,"y_id":"3","y_name":"三年级","y_xprice":"11050","y_yprice":"12000"}]
             * is_love : 0
             * s_address : 成都市锦江区1街道
             * s_icon : Uploads/Img/ceshi.jpg
             * s_lat : 30.42775
             * s_logo : Uploads/Img/ceshi.jpg
             * s_long : 104.569796
             * s_name : 成都七中
             * s_phone : 18681373742
             */

            private int is_love;
            private String s_address;
            private String s_icon;
            private String s_lat;
            private String s_logo;
            private String s_long;
            private String s_name;
            private String s_phone;
            private List<GradeListBean> gradeList;

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public String getS_address() {
                return s_address;
            }

            public void setS_address(String s_address) {
                this.s_address = s_address;
            }

            public String getS_icon() {
                return s_icon;
            }

            public void setS_icon(String s_icon) {
                this.s_icon = s_icon;
            }

            public String getS_lat() {
                return s_lat;
            }

            public void setS_lat(String s_lat) {
                this.s_lat = s_lat;
            }

            public String getS_logo() {
                return s_logo;
            }

            public void setS_logo(String s_logo) {
                this.s_logo = s_logo;
            }

            public String getS_long() {
                return s_long;
            }

            public void setS_long(String s_long) {
                this.s_long = s_long;
            }

            public String getS_name() {
                return s_name;
            }

            public void setS_name(String s_name) {
                this.s_name = s_name;
            }

            public String getS_phone() {
                return s_phone;
            }

            public void setS_phone(String s_phone) {
                this.s_phone = s_phone;
            }

            public List<GradeListBean> getGradeList() {
                return gradeList;
            }

            public void setGradeList(List<GradeListBean> gradeList) {
                this.gradeList = gradeList;
            }

            public static class GradeListBean {
                /**
                 * overmun : 68
                 * y_id : 1
                 * y_name : 一年级
                 * y_xprice : 11020
                 * y_yprice : 12000
                 */

                private int overmun;
                private String y_id;
                private String y_name;
                private String y_xprice;
                private String y_yprice;

                public int getOvermun() {
                    return overmun;
                }

                public void setOvermun(int overmun) {
                    this.overmun = overmun;
                }

                public String getY_id() {
                    return y_id;
                }

                public void setY_id(String y_id) {
                    this.y_id = y_id;
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

                public String getY_yprice() {
                    return y_yprice;
                }

                public void setY_yprice(String y_yprice) {
                    this.y_yprice = y_yprice;
                }
            }
        }
    }
}
