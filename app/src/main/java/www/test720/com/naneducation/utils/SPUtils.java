package www.test720.com.naneducation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import www.test720.com.naneducation.application.MyApplication;

import static www.test720.com.naneducation.http.Constans.unionid;


/**
 * Created by LuoPan on 2017/9/7 17:49.
 */

public class SPUtils {
    static SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("nanEducation", Context.MODE_PRIVATE);

  /*  public static void saveInfo(String uuid, String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString("phoneNumber", phoneNumber);

        editor.putString("uuid", uuid);

        editor.commit();
    }*/

    public static String getUUID() {
        String name = sharedPreferences.getString("uuid", "");
        Log.e("uuid", name);
        return name;
    }

    public static void saveUserInfo(String phoneNumber, String pasword) {


        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString("phoneNumber", phoneNumber);

        editor.putString("pasword", MD5Util.encrypt(pasword));

        editor.commit();
    }

    public static void setUnionid(String unionid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("unionid", unionid);
        editor.commit();

    }

    public static void saveQQ(String qqId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("qqId", qqId);
        editor.commit();
    }

    public static void saveWeixin(String weixinId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("weixinId", weixinId);
        editor.commit();
    }

    public static String getQQId() {
        String unionid = sharedPreferences.getString("qqId", "");

        return unionid;
    }

    public static String getWeiXinId() {
        String unionid = sharedPreferences.getString("weixinId", "");

        return unionid;
    }

    public static boolean isFirstChange() {
        return sharedPreferences.getBoolean("isfirstChange", true);
    }

    public static void setHasInstall(boolean isInstall) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("isInstall", isInstall);
        editor.commit();
    }

    public static boolean getIsInstall() {
        return sharedPreferences.getBoolean("isInstall", false);
    }

    public static boolean isFirstIn() {
        return sharedPreferences.getBoolean("isfirstin", true);
    }

    public static void setFirstIn(boolean isfrist) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("isfirstin", isfrist);
        editor.commit();
    }

    public static void setFirstChange(boolean isfrist) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("isfirstChange", isfrist);
        editor.commit();
    }

    public static String getUnionid() {
        String unionid = sharedPreferences.getString("unionid", "");

        return unionid;
    }

    public static String getCount() {
        String name = sharedPreferences.getString("phoneNumber", "");

        return name;
    }

    public static String getPWD() {
        String Pwd = sharedPreferences.getString("pasword", "");

        return MD5Util.decrypt(Pwd);
    }
}
