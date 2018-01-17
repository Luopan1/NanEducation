package www.test720.com.naneducation.bean;

import java.io.Serializable;

/**
 * 后台返回已开通城市
 * Created by Administrator on 2017/5/2.
 */

public class NetworkCityBean implements Serializable {
    private String area_name;
    private String area_id;
    private String area_pid;
    private provinceInfo province_info;

    public NetworkCityBean(String area_name, String area_id, String area_pid, provinceInfo province_info) {
        this.area_name = area_name;
        this.area_id = area_id;
        this.area_pid = area_pid;
        this.province_info = province_info;
    }

    public provinceInfo getProvince_info() {
        return province_info;
    }

    public void setProvince_info(provinceInfo province_info) {
        this.province_info = province_info;
    }

    @Override
    public String toString() {
        return "NetworkCityBean{" +
                "area_name='" + area_name + '\'' +
                ", area_id='" + area_id + '\'' +
                ", area_pid='" + area_pid + '\'' +
                ", province_info=" + province_info +
                '}';
    }

    private class provinceInfo {
        private String area_name;
        private String area_id;

        @Override
        public String toString() {
            return "provinceInfo{" +
                    "area_name='" + area_name + '\'' +
                    ", area_id='" + area_id + '\'' +
                    '}';
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_pid() {
        return area_pid;
    }

    public void setArea_pid(String area_pid) {
        this.area_pid = area_pid;
    }
}
