package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/6 17:29.
 */

public class GuoJiangTitle {

    /**
     * info : 获取成功
     * data : [{"title":"f","id":"6"},{"title":"e","id":"5"},{"title":"d","id":"4"},{"title":"c","id":"3"},{"title":"b","id":"2"},{"title":"a","id":"1"}]
     * code : 0
     */

    private String info;
    private int code;
    private List<DataBean> data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        public DataBean(String title, String id) {
            this.title = title;
            this.id = id;
        }

        /**
         * title : f
         * id : 6
         */


        private String title;
        private String id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
