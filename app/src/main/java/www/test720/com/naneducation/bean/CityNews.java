package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/1 11:16.
 */

public class CityNews {

    /**
     * code : 1
     * data : {"list":[{"logo":"Uploads/Img/ceshi.jpg","n_id":"4","name":"公益标题2","target_type":"2","target_url":"http://www.baidu.com","time":"2017.10.08 05:37:24"}],"total":3}
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
         * list : [{"logo":"Uploads/Img/ceshi.jpg","n_id":"4","name":"公益标题2","target_type":"2","target_url":"http://www.baidu.com","time":"2017.10.08 05:37:24"}]
         * total : 3
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
             * logo : Uploads/Img/ceshi.jpg
             * n_id : 4
             * name : 公益标题2
             * target_type : 2
             * target_url : http://www.baidu.com
             * time : 2017.10.08 05:37:24
             */

            private String logo;
            private String n_id;
            private String name;
            private String target_type;
            private String target_url;
            private String time;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getN_id() {
                return n_id;
            }

            public void setN_id(String n_id) {
                this.n_id = n_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTarget_type() {
                return target_type;
            }

            public void setTarget_type(String target_type) {
                this.target_type = target_type;
            }

            public String getTarget_url() {
                return target_url;
            }

            public void setTarget_url(String target_url) {
                this.target_url = target_url;
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
