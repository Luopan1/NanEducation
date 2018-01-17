package www.test720.com.naneducation.bean;

/**
 * Created by jie on 2017/8/11.
 */

public class StudyHomeBanner {

    public StudyHomeBanner(String ba_id, String ba_img, String target_type) {
        this.ba_id = ba_id;
        this.ba_img = ba_img;
        this.target_type = target_type;
    }

    public StudyHomeBanner() {
    }

    /**
     * ba_id : 1
     * ba_img : Public/Img/4.jpg
     * target_type : 1
     */


    private String ba_id;
    private String ba_img;
    private String target_type;

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
}
