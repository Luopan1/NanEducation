package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * Created by LuoPan on 2017/11/9.
 */

public class FirstDataBean {


    /**
     * code : 1
     * data : {"actList":[{"act_id":"1","act_money":"0","act_name":"成都音乐会","head":"Uploads/Img/2017-10-30/59f6dacd5d31e.jpg","logo":"Uploads/Img/ceshi.jpg","name":"2147483647"}],"banner":[{"ba_id":"9","ba_img":"Uploads/Img/ceshi.jpg","target_type":"1","target_url":""},{"ba_id":"10","ba_img":"Uploads/Img/ceshi.jpg","target_type":"2","target_url":""},{"ba_id":"11","ba_img":"Uploads/Img/ceshi.jpg","target_type":"3","target_url":"http://www.baidu.com"},{"ba_id":"12","ba_img":"Uploads/Img/ceshi.jpg","target_type":"1","target_url":""},{"ba_id":"15","ba_img":"Uploads/Img/ceshi.jpg","target_type":"2","target_url":""}],"schoolList":[{"s_logo":"Uploads/Img/ceshi.jpg","s_name":"成都七中","sid":"1"}],"topListhree":[{"castId":"2","logo":"Uploads/Img/ceshi.jpg","name":"阿白","price":"80.88","tc_head":"Uploads/Img/2017-11-08/5a02c3b85058d.jpg","tc_name":"瞎子222","type":1,"typename":"免费"}],"topListone":[{"castId":"2","logo":"Uploads/Img/ceshi.jpg","name":"阿白","price":"80.88","tc_head":"Uploads/Img/2017-11-08/5a02c3b85058d.jpg","tc_name":"瞎子222","type":1,"typename":"免费"}],"topListwo":[{"castId":"2","logo":"Uploads/Img/ceshi.jpg","name":"阿白","price":"80.88","tc_head":"Uploads/Img/2017-11-08/5a02c3b85058d.jpg","tc_name":"瞎子222","type":2,"typename":"免费"}],"trainList":[{"train_id":"2","train_logo":"Uploads/Img/ceshi.jpg","train_name":"美博教育"}]}
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
        private List<ActListBean> actList;
        private List<BannerBean> banner;
        private List<SchoolListBean> schoolList;
        private List<TopListhreeBean> topListhree;
        private List<TopListoneBean> topListone;
        private List<TopListwoBean> topListwo;
        private List<TrainListBean> trainList;

        public List<ActListBean> getActList() {
            return actList;
        }

        public void setActList(List<ActListBean> actList) {
            this.actList = actList;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<SchoolListBean> getSchoolList() {
            return schoolList;
        }

        public void setSchoolList(List<SchoolListBean> schoolList) {
            this.schoolList = schoolList;
        }

        public List<TopListhreeBean> getTopListhree() {
            return topListhree;
        }

        public void setTopListhree(List<TopListhreeBean> topListhree) {
            this.topListhree = topListhree;
        }

        public List<TopListoneBean> getTopListone() {
            return topListone;
        }

        public void setTopListone(List<TopListoneBean> topListone) {
            this.topListone = topListone;
        }

        public List<TopListwoBean> getTopListwo() {
            return topListwo;
        }

        public void setTopListwo(List<TopListwoBean> topListwo) {
            this.topListwo = topListwo;
        }

        public List<TrainListBean> getTrainList() {
            return trainList;
        }

        public void setTrainList(List<TrainListBean> trainList) {
            this.trainList = trainList;
        }

        public static class ActListBean {
            /**
             * act_id : 1
             * act_money : 0
             * act_name : 成都音乐会
             * head : Uploads/Img/2017-10-30/59f6dacd5d31e.jpg
             * logo : Uploads/Img/ceshi.jpg
             * name : 2147483647
             */

            private String act_id;
            private String act_money;
            private String act_name;
            private String head;
            private String logo;
            private String name;

            public String getAct_id() {
                return act_id;
            }

            public void setAct_id(String act_id) {
                this.act_id = act_id;
            }

            public String getAct_money() {
                return act_money;
            }

            public void setAct_money(String act_money) {
                this.act_money = act_money;
            }

            public String getAct_name() {
                return act_name;
            }

            public void setAct_name(String act_name) {
                this.act_name = act_name;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class BannerBean {
            /**
             * ba_id : 9
             * ba_img : Uploads/Img/ceshi.jpg
             * target_type : 1
             * target_url :
             */

            private String ba_id;
            private String ba_img;
            private String target_type;
            private String target_url;

            public String getBa_id() {
                return ba_id;
            }

            public void setBa_id(String ba_id) {
                this.ba_id = ba_id;
            }

            public String getBa_img() {
                return ba_img;
            }

            public void setBa_img(String ba_img) {
                this.ba_img = ba_img;
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
        }

        public static class SchoolListBean {
            /**
             * s_logo : Uploads/Img/ceshi.jpg
             * s_name : 成都七中
             * sid : 1
             */

            private String s_logo;
            private String s_name;
            private String sid;

            public String getS_logo() {
                return s_logo;
            }

            public void setS_logo(String s_logo) {
                this.s_logo = s_logo;
            }

            public String getS_name() {
                return s_name;
            }

            public void setS_name(String s_name) {
                this.s_name = s_name;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }
        }

        public static class TopListhreeBean {
            /**
             * castId : 2
             * logo : Uploads/Img/ceshi.jpg
             * name : 阿白
             * price : 80.88
             * tc_head : Uploads/Img/2017-11-08/5a02c3b85058d.jpg
             * tc_name : 瞎子222
             * type : 1
             * typename : 免费
             */

            private String castId;
            private String logo;
            private String name;
            private String price;
            private String tc_head;
            private String tc_name;
            private int type;
            private String typename;

            public String getCastId() {
                return castId;
            }

            public void setCastId(String castId) {
                this.castId = castId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTc_head() {
                return tc_head;
            }

            public void setTc_head(String tc_head) {
                this.tc_head = tc_head;
            }

            public String getTc_name() {
                return tc_name;
            }

            public void setTc_name(String tc_name) {
                this.tc_name = tc_name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }
        }

        public static class TopListoneBean {
            /**
             * castId : 2
             * logo : Uploads/Img/ceshi.jpg
             * name : 阿白
             * price : 80.88
             * tc_head : Uploads/Img/2017-11-08/5a02c3b85058d.jpg
             * tc_name : 瞎子222
             * type : 1
             * typename : 免费
             */

            private String castId;
            private String logo;
            private String name;
            private String price;
            private String tc_head;
            private String tc_name;
            private int type;
            private String typename;

            public String getCastId() {
                return castId;
            }

            public void setCastId(String castId) {
                this.castId = castId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTc_head() {
                return tc_head;
            }

            public void setTc_head(String tc_head) {
                this.tc_head = tc_head;
            }

            public String getTc_name() {
                return tc_name;
            }

            public void setTc_name(String tc_name) {
                this.tc_name = tc_name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }
        }

        public static class TopListwoBean {
            /**
             * castId : 2
             * logo : Uploads/Img/ceshi.jpg
             * name : 阿白
             * price : 80.88
             * tc_head : Uploads/Img/2017-11-08/5a02c3b85058d.jpg
             * tc_name : 瞎子222
             * type : 2
             * typename : 免费
             */

            private String castId;
            private String logo;
            private String name;
            private String price;
            private String tc_head;
            private String tc_name;
            private int type;
            private String typename;

            public String getCastId() {
                return castId;
            }

            public void setCastId(String castId) {
                this.castId = castId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTc_head() {
                return tc_head;
            }

            public void setTc_head(String tc_head) {
                this.tc_head = tc_head;
            }

            public String getTc_name() {
                return tc_name;
            }

            public void setTc_name(String tc_name) {
                this.tc_name = tc_name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }
        }

        public static class TrainListBean {
            /**
             * train_id : 2
             * train_logo : Uploads/Img/ceshi.jpg
             * train_name : 美博教育
             */

            private String train_id;
            private String train_logo;
            private String train_name;

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
        }
    }
}
