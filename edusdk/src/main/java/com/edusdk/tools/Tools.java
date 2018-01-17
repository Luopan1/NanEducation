package com.edusdk.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.edusdk.R;
import com.edusdk.message.RoomSession;
import com.talkcloud.roomsdk.RoomManager;

/**
 * Created by Administrator on 2017/6/17.
 */

public class Tools {
    public static ProgressDialog progressDialog;
    public static AlertDialog.Builder alertDialog;

    public static boolean isTure(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Number) {
            Number n = (Number) o;
            return n.longValue() != 0;
        } else {
            return false;
        }
    }

    public static long toLong(Object o) {
        long temLong = 0;
        if (o instanceof Integer) {
            int tem = (Integer) o;
            temLong = tem;
        } else if (o instanceof String) {
            String temstr = (String) o;
            temLong = Long.valueOf(temstr);
        } else {
            temLong = (Long) o;
        }
        return temLong;
    }

    public static void ShowProgressDialog(final Activity activity, final String message) {
        if (!activity.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity, android.R.style.Theme_Holo_Light_Dialog);
            }
            if (message != null) {
                progressDialog.setMessage(message);
            }
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.show();
        }
    }

    public static void ShowAlertDialog(final Activity activity, final String message) {
        if (!activity.isFinishing()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.remind);
            builder.setMessage(message);
            builder.setPositiveButton(R.string.sure,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
    }

    public static void HideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static String bSubstring(String s, int length) throws Exception {

        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 前两个字节是标志位，bytes[0] = -2，bytes[1] = -1。所以从第3位开始截取。
        for (; i < bytes.length && n < length; i++) {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1) {
                n++; // 在UCS2第二个字节时n加1
            } else {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0) {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)

        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
                // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }

        return new String(bytes, 0, i, "Unicode");
    }

    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;

    }
}
