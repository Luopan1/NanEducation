package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/10/30 12:34.
 */

public class Customer {


    /**
     * code : 1
     * data : {"agentName":"小邹","agentphone":"245555577","bossName":"杨总","quesList":[{"answer":"开课前1小时可以退","question":"报名缴费后是否可以退款？"},{"answer":"公司获取","question":"发票如何获取？"},{"answer":"每周一到周六10:00-22:00","question":"客服工作时间?"}],"visephone":"18990899162"}
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
         * agentName : 小邹
         * agentphone : 245555577
         * bossName : 杨总
         * quesList : [{"answer":"开课前1小时可以退","question":"报名缴费后是否可以退款？"},{"answer":"公司获取","question":"发票如何获取？"},{"answer":"每周一到周六10:00-22:00","question":"客服工作时间?"}]
         * visephone : 18990899162
         */

        private String agentName;
        private String agentphone;
        private String bossName;
        private String visephone;
        private List<QuesListBean> quesList;

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public String getAgentphone() {
            return agentphone;
        }

        public void setAgentphone(String agentphone) {
            this.agentphone = agentphone;
        }

        public String getBossName() {
            return bossName;
        }

        public void setBossName(String bossName) {
            this.bossName = bossName;
        }

        public String getVisephone() {
            return visephone;
        }

        public void setVisephone(String visephone) {
            this.visephone = visephone;
        }

        public List<QuesListBean> getQuesList() {
            return quesList;
        }

        public void setQuesList(List<QuesListBean> quesList) {
            this.quesList = quesList;
        }

        public static class QuesListBean {
            /**
             * answer : 开课前1小时可以退
             * question : 报名缴费后是否可以退款？
             */

            private String answer;
            private String question;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }
        }
    }
}
