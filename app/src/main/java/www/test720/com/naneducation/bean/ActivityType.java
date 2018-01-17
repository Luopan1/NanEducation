package www.test720.com.naneducation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author LuoPan on 2017/11/2 16:54.
 */

public class ActivityType implements Serializable {

    /**
     * code : 1
     * data : {"list":[{"tid":"1","name":"运动"},{"tid":"2","name":"音乐"},{"tid":"3","name":"旅游"},{"tid":"4","name":"电影"},{"tid":"5","name":"游戏"},{"tid":"6","name":"动漫"},{"tid":"7","name":"学习"},{"tid":"8","name":"其他"}]}
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

    public static class DataBean implements Serializable {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * tid : 1
             * name : 运动
             */

            private String tid;
            private String name;

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
