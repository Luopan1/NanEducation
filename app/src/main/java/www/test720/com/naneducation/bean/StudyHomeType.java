package www.test720.com.naneducation.bean;

/**
 * Created by LuoPan on 2017/10/10 9:47.
 */

public class StudyHomeType {
    public int typeImage;
    public int typeId;
    public String typeString;


    public StudyHomeType(int typeImage, int typeId, String typeString) {
        this.typeImage = typeImage;
        this.typeId = typeId;
        this.typeString = typeString;
    }

    public int getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(int typeImage) {
        this.typeImage = typeImage;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }
}
