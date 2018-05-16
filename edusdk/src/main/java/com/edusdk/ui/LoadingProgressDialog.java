package com.edusdk.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.edusdk.R;


/**
 * Created by Administrator on 2018/1/5/005.
 */

public class LoadingProgressDialog extends Dialog {

    private static LoadingProgressDialog customProgressDialog = null;
    private static AnimationDrawable animationDrawable;

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static void createDialog(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        if (!activity.isFinishing()) {
            if (customProgressDialog == null) {
                customProgressDialog = new LoadingProgressDialog(activity, R.style.CustomProgressDialog);
                customProgressDialog.setContentView(R.layout.loadinggressdialog);
                customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
                customProgressDialog.getWindow().getAttributes().height = d.getHeight();
                customProgressDialog.getWindow().getAttributes().width = d.getWidth();
            }
            customProgressDialog.show();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (customProgressDialog == null) {
            return;
        }
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
    }

    public static void HideProgressDialog() {
        if (customProgressDialog != null) {
            if (animationDrawable != null && animationDrawable.isRunning()) {
                //停止动画播放
                animationDrawable.stop();
            }
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }
}
