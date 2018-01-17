package www.test720.com.naneducation.bean;

import java.io.Serializable;

/**
 * Created by LuoPan on 2017/10/18 13:58.
 */

public class Grade implements Serializable {
    private int Id;
    private String grade;

    public Grade(int id, String grade) {
        Id = id;
        this.grade = grade;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "Id=" + Id +
                ", grade='" + grade + '\'' +
                '}';
    }
}
