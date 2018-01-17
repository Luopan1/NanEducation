package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/10/31 13:42.
 */

public class Integral {

    /**
     * code : 1
     * data : {"list":[{"content":"培训机构报名","integral":"8","time":"2017.10.25 11:04:17"},{"content":"团体活动报名","integral":"6","time":"2017.10.25 11:45:19"},{"content":"学校报名","integral":"7","time":"2017.10.26 10:34:21"},{"content":"团体活动退款","integral":"0","time":"2017.10.30 16:29:23"}]}
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
             * content : 培训机构报名
             * integral : 8
             * time : 2017.10.25 11:04:17
             */

            private String content;
            private String integral;
            private String time;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
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
