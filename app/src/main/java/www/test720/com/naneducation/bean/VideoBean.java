package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/17 11:22.
 */

public class VideoBean {

    /**
     * code : 1
     * data : {"list":[{"head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","live_logo":"Uploads/Img/ceshi.jpg","live_title":"阿白","name":"9527","relation_id":"2","time":"2017.11.22 15:51:56"}],"total":2}
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
         * list : [{"head":"Uploads/Img/2017-11-10/5a051ec286b4d.jpg","live_logo":"Uploads/Img/ceshi.jpg","live_title":"阿白","name":"9527","relation_id":"2","time":"2017.11.22 15:51:56"}]
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
             * head : Uploads/Img/2017-11-10/5a051ec286b4d.jpg
             * live_logo : Uploads/Img/ceshi.jpg
             * live_title : 阿白
             * name : 9527
             * relation_id : 2
             * time : 2017.11.22 15:51:56
             */

            private String head;
            private String live_logo;
            private String live_title;
            private String name;
            private String relation_id;
            private String time;

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getLive_logo() {
                return live_logo;
            }

            public void setLive_logo(String live_logo) {
                this.live_logo = live_logo;
            }

            public String getLive_title() {
                return live_title;
            }

            public void setLive_title(String live_title) {
                this.live_title = live_title;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRelation_id() {
                return relation_id;
            }

            public void setRelation_id(String relation_id) {
                this.relation_id = relation_id;
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
