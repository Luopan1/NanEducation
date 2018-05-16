package com.classroomsdk;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Administrator on 2017/6/17.
 */

public class Tools {
    public static ProgressDialog progressDialog;

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
        } else {
            return;
        }
    }

    public static void HideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }else {
            return;
        }
    }

    public static boolean isMp4(String filename) {
        int icon = -1;
        if (filename.toLowerCase().endsWith("mp4") || filename.toLowerCase().endsWith("webm")) {
            return true;
        } else {
            return false;
        }
    }
}
