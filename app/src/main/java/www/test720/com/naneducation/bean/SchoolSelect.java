package www.test720.com.naneducation.bean;

import java.util.List;

import static www.test720.com.naneducation.R.id.grade;

/**
 * Created by LuoPan on 2017/11/7.
 */

public class SchoolSelect {


    /**
     * code : 1
     * data : {"list":[{"distance":49.6,"grade":3,"price":46,"train_id":"1","train_logo":"Uploads/Img/ceshi.jpg","train_name":"爱心教育","train_title":"全心全意为孩子服务"}],"total":1}
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
         * list : [{"distance":49.6,"grade":3,"price":46,"train_id":"1","train_logo":"Uploads/Img/ceshi.jpg","train_name":"爱心教育","train_title":"全心全意为孩子服务"}]
         * total : 1
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
             * distance : 49.6
             * grade : 3
             * price : 46
             * train_id : 1
             * train_logo : Uploads/Img/ceshi.jpg
             * train_name : 爱心教育
             * train_title : 全心全意为孩子服务
             */

            private double distance;
            private double grade;
            private double price;
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

            public double getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
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
    }
}
