package www.test720.com.naneducation.bean;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.Serializable;

/**
 * city bean
 * Created by Administrator on 2017/3/22.
 */

public class City implements Serializable {
    private String name;
    private String pinyin;

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

    public City() {
    }

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}







