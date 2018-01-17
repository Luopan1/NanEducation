package www.test720.com.naneducation.utils;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoPan on 2017/9/27 10:40.
 */

public class Checker_Self {
    private static List<String> format = new ArrayList<>();
    private static final String JPG = "jpg";
    private static final String JPEG = "jpeg";
    private static final String PNG = "png";
    private static final String WEBP = "webp";
    private static final String GIF = "gif";

    static {
        format.add(JPG);
        format.add(JPEG);
        format.add(PNG);
        format.add(WEBP);
        format.add(GIF);
    }

    static boolean isImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        String suffix = path.substring(path.lastIndexOf(".") + 1, path.length());
        return format.contains(suffix.toLowerCase());
    }

    static boolean isJPG(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        String suffix = path.substring(path.lastIndexOf("."), path.length()).toLowerCase();
        return suffix.contains(JPG) || suffix.contains(JPEG);
    }

    static String checkSuffix(String path) {
        if (TextUtils.isEmpty(path)) {
            return ".jpg";
        }

        return path.substring(path.lastIndexOf("."), path.length());
    }

    static boolean isNeedCompress(int leastCompressSize, String path) {
        if (leastCompressSize > 0) {
            File source = new File(path);
            if (!source.exists()) {
                return false;
            }
            /**修改部分  以gif结尾的动图 不去压缩*/
            if (path.endsWith("gif")) {
                return false;
            }

            if (source.length() <= (leastCompressSize << 10)) {
                return false;
            }
        }
        return true;
    }
}
