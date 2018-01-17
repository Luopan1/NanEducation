package www.test720.com.naneducation.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/2 12:50.
 */

public class ActivityBuyOrder {

    /**
     * code : 1
     * data : {"integral":"6","o_number":"2017110293390151","payprice":198.5,"price":200,"sponsor":["蒙牛赞助1元","伊利赞助0.5元"]}
     * msg : 生成订单成功
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
         * integral : 6
         * o_number : 2017110293390151
         * payprice : 198.5
         * price : 200
         * sponsor : ["蒙牛赞助1元","伊利赞助0.5元"]
         */

        private String integral;
        private String o_number;
        private double payprice;
        private double price;
        private List<String> sponsor;

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getO_number() {
            return o_number;
        }

        public void setO_number(String o_number) {
            this.o_number = o_number;
        }

        public double getPayprice() {
            return payprice;
        }

        public void setPayprice(double payprice) {
            this.payprice = payprice;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public List<String> getSponsor() {
            return sponsor;
        }

        public void setSponsor(List<String> sponsor) {
            this.sponsor = sponsor;
        }
    }
}
