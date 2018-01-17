package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/10.
 */

public class SchoolCast {


    /**
     * code : 1
     * data : {"detail":{"is_signup":0,"list":[{"name":"书本费","price":"600"},{"name":"住宿费","price":"1000"},{"name":"伙食费","price":"5000"}],"logo":"Uploads/Img/ceshi.jpg"}}
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
         * detail : {"is_signup":0,"list":[{"name":"书本费","price":"600"},{"name":"住宿费","price":"1000"},{"name":"伙食费","price":"5000"}],"logo":"Uploads/Img/ceshi.jpg"}
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
             * is_signup : 0
             * list : [{"name":"书本费","price":"600"},{"name":"住宿费","price":"1000"},{"name":"伙食费","price":"5000"}]
             * logo : Uploads/Img/ceshi.jpg
             */

            private int is_signup;
            private String logo;
            private List<ListBean> list;

            public int getIs_signup() {
                return is_signup;
            }

            public void setIs_signup(int is_signup) {
                this.is_signup = is_signup;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * name : 书本费
                 * price : 600
                 */

                private String name;
                private String price;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }
        }
    }
}
