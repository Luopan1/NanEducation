package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/2 19:49.
 */

public class TrainBean {


    /**
     * code : 1
     * data : {"list":[{"distance":9.7,"train_id":"6","train_logo":"Uploads/Img/2018-01-10/5a561306ebb17.jpg","train_name":"邹机构","train_title":"哈哈哈哈"},{"distance":48.3,"train_id":"3","train_logo":"Uploads/Img/ceshi.jpg","train_name":"宝贝教育","train_title":"全心全意为孩子服务"},{"distance":47.7,"train_id":"2","train_logo":"Uploads/Img/ceshi.jpg","train_name":"美博教育","train_title":"全心全意为孩子服务"},{"distance":7149.7,"train_id":"1","train_logo":"Uploads/Img/ceshi.jpg","train_name":"爱心教育","train_title":"全心全意为孩子服务"}],"total":1,"typelist":[{"name":"语文","t_id":"1","zi":[{"name":"一年级","t_id":"7"},{"name":"二年级","t_id":"8"},{"name":"三年级","t_id":"9"},{"name":"四年级","t_id":"24"}]},{"name":"数学","t_id":"2","zi":[{"name":"一年级","t_id":"10"},{"name":"二年级","t_id":"11"},{"name":"三年级","t_id":"12"}]},{"name":"体育","t_id":"3","zi":[{"name":"一年级","t_id":"13"},{"name":"二年级","t_id":"14"},{"name":"三年级","t_id":"15"}]},{"name":"历史","t_id":"4","zi":[{"name":"一年级","t_id":"16"},{"name":"二年级","t_id":"17"},{"name":"三年级","t_id":"18"}]},{"name":"英语","t_id":"6","zi":[{"name":"一年级","t_id":"19"},{"name":"二年级","t_id":"20"},{"name":"三年级","t_id":"21"}]},{"name":"化学","t_id":"23","zi":[]},{"name":"金牛语文","t_id":"25","zi":[{"name":"一年级","t_id":"26"}]}]}
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
         * list : [{"distance":9.7,"train_id":"6","train_logo":"Uploads/Img/2018-01-10/5a561306ebb17.jpg","train_name":"邹机构","train_title":"哈哈哈哈"},{"distance":48.3,"train_id":"3","train_logo":"Uploads/Img/ceshi.jpg","train_name":"宝贝教育","train_title":"全心全意为孩子服务"},{"distance":47.7,"train_id":"2","train_logo":"Uploads/Img/ceshi.jpg","train_name":"美博教育","train_title":"全心全意为孩子服务"},{"distance":7149.7,"train_id":"1","train_logo":"Uploads/Img/ceshi.jpg","train_name":"爱心教育","train_title":"全心全意为孩子服务"}]
         * total : 1
         * typelist : [{"name":"语文","t_id":"1","zi":[{"name":"一年级","t_id":"7"},{"name":"二年级","t_id":"8"},{"name":"三年级","t_id":"9"},{"name":"四年级","t_id":"24"}]},{"name":"数学","t_id":"2","zi":[{"name":"一年级","t_id":"10"},{"name":"二年级","t_id":"11"},{"name":"三年级","t_id":"12"}]},{"name":"体育","t_id":"3","zi":[{"name":"一年级","t_id":"13"},{"name":"二年级","t_id":"14"},{"name":"三年级","t_id":"15"}]},{"name":"历史","t_id":"4","zi":[{"name":"一年级","t_id":"16"},{"name":"二年级","t_id":"17"},{"name":"三年级","t_id":"18"}]},{"name":"英语","t_id":"6","zi":[{"name":"一年级","t_id":"19"},{"name":"二年级","t_id":"20"},{"name":"三年级","t_id":"21"}]},{"name":"化学","t_id":"23","zi":[]},{"name":"金牛语文","t_id":"25","zi":[{"name":"一年级","t_id":"26"}]}]
         */

        private int total;
        private List<ListBean> list;
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

        public List<TypelistBean> getTypelist() {
            return typelist;
        }

        public void setTypelist(List<TypelistBean> typelist) {
            this.typelist = typelist;
        }

        public static class ListBean {
            /**
             * distance : 9.7
             * train_id : 6
             * train_logo : Uploads/Img/2018-01-10/5a561306ebb17.jpg
             * train_name : 邹机构
             * train_title : 哈哈哈哈
             */

            private double distance;
            private String train_id;
            private String train_logo;
            private String train_name;
            private String train_title;

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public String getTrain_id() {
                return train_id;
            }

            public void setTrain_id(String train_id) {
                this.train_id = train_id;
            }

            public String getTrain_logo() {
                return train_logo;
            }

            public void setTrain_logo(String train_logo) {
                this.train_logo = train_logo;
            }

            public String getTrain_name() {
                return train_name;
            }

            public void setTrain_name(String train_name) {
                this.train_name = train_name;
            }

            public String getTrain_title() {
                return train_title;
            }

            public void setTrain_title(String train_title) {
                this.train_title = train_title;
            }
        }

        public static class TypelistBean {
            /**
             * name : 语文
             * t_id : 1
             * zi : [{"name":"一年级","t_id":"7"},{"name":"二年级","t_id":"8"},{"name":"三年级","t_id":"9"},{"name":"四年级","t_id":"24"}]
             */


            private String name;
            private String t_id;
            private List<ZiBean> zi;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getT_id() {
                return t_id;
            }

            public void setT_id(String t_id) {
                this.t_id = t_id;
            }

            public List<ZiBean> getZi() {
                return zi;
            }

            public void setZi(List<ZiBean> zi) {
                this.zi = zi;
            }

            public static class ZiBean {
                /**
                 * name : 一年级
                 * t_id : 7
                 */

                private String name;
                private String t_id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getT_id() {
                    return t_id;
                }

                public void setT_id(String t_id) {
                    this.t_id = t_id;
                }
            }
        }
    }
}
