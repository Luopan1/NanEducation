package www.test720.com.naneducation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author LuoPan on 2017/10/31 10:44.
 */

public class BindUser implements Serializable {

    /**
     * code : 1
     * data : {"userlist":[{"binduser_id":"2000","kid":"6","username":"爸爸"}]}
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

    public static class DataBean implements Serializable {
        private List<UserlistBean> userlist;

        public List<UserlistBean> getUserlist() {
            return userlist;
        }

        public void setUserlist(List<UserlistBean> userlist) {
            this.userlist = userlist;
        }

        public static class UserlistBean implements Serializable {
            /**
             * binduser_id : 2000
             * kid : 6
             * username : 爸爸
             */

            private String binduser_id;
            private String kid;
            private String username;

            public String getBinduser_id() {
                return binduser_id;
            }

            public void setBinduser_id(String binduser_id) {
                this.binduser_id = binduser_id;
            }

            public String getKid() {
                return kid;
            }

            public void setKid(String kid) {
                this.kid = kid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
