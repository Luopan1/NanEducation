package www.test720.com.naneducation.bean;

/**
 * Created by LuoPan on 2017/10/12 16:24.
 */

public class VideoKind {
    private int type;
    private String videoName;
    private String videoKind;
    private String teaxherName;
    private String money;
    private String courseKind;
    private String teacherPhoto;
    private String videoImage;


    public VideoKind(String videoName, String videoKind, String teaxherName, String money, String courseKind, String teacherPhoto, String videoImage, int type) {
        this.type = type;
        this.videoName = videoName;
        this.videoKind = videoKind;
        this.teaxherName = teaxherName;
        this.money = money;
        this.courseKind = courseKind;
        this.teacherPhoto = teacherPhoto;
        this.videoImage = videoImage;
    }

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }


    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoKind() {
        return videoKind;
    }

    public void setVideoKind(String videoKind) {
        this.videoKind = videoKind;
    }

    public String getTeaxherName() {
        return teaxherName;
    }

    public void setTeaxherName(String teaxherName) {
        this.teaxherName = teaxherName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCourseKind() {
        return courseKind;
    }

    public void setCourseKind(String courseKind) {
        this.courseKind = courseKind;
    }

    public String getTeacherPhoto() {
        return teacherPhoto;
    }

    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }
}
