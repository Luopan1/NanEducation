package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2018/1/18 11:06.
 */

public class SignMembers {

    /**
     * code : 1
     * data : [{"user_phone":"18990899162","username":"哥哥"}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_phone : 18990899162
         * username : 哥哥
         */

        private String user_phone;
        private String username;

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
