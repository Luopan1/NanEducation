package www.test720.com.naneducation.bean;

/**
 * Created by LuoPan on 2017/10/11 13:59.
 */

public class Classfication {
    private int id;
    private String className;

    public Classfication(int id, String className) {
        this.id = id;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
