package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/1 10:38.
 */

public class UserMoneyDetail {


    /**
     * code : 1
     * data : {"list":[{"cash_type":"0","content":"培训机构报名","money":"-48.50","time":"2017-11-09 14:24:14","type":"4"}],"total":10}
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
        /**
         * list : [{"cash_type":"0","content":"培训机构报名","money":"-48.50","time":"2017-11-09 14:24:14","type":"4"}]
         * total : 10
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
             * cash_type : 0
             * content : 培训机构报名
             * money : -48.50
             * time : 2017-11-09 14:24:14
             * type : 4
             */

            private String cash_type;
            private String content;
            private String money;
            private String time;
            private String type;

            public String getCash_type() {
                return cash_type;
            }

            public void setCash_type(String cash_type) {
                this.cash_type = cash_type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
