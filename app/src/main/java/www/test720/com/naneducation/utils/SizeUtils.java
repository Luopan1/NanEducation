package www.test720.com.naneducation.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by jie on 2017/1/13
 * .
 */

public class SizeUtils {

    Activity mActivity;

    public SizeUtils(Activity mActivity) {
        this.mActivity = mActivity;
    }


    /**
     * px转sp
     *
     * @param px
     * @return
     */
    public float pxToSp(int px) {
        float sp;
        DisplayMetrics dm = new DisplayMetrics();
        dm = mActivity.getResources().getDisplayMetrics();
        int ppi = dm.densityDpi;
        sp = (float) (px * 160 / ppi);
        return sp;
    }

    public DisplayMetrics screenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public void setLayoutSize(View view, int wdith, int height) {
        ViewGroup.LayoutParams lt = view.getLayoutParams();
        lt.height = height * screenHeight() / 1334;
        lt.width = wdith * screenWidth() / 750;
        view.setLayoutParams(lt);
    }


    public void setLayoutSizeHeight(View view, int height) {
        ViewGroup.LayoutParams lt = view.getLayoutParams();
        lt.height = height * screenHeight() / 1334;

        view.setLayoutParams(lt);
    }

    public int setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams lt = view.getLayoutParams();
        lt.height = height * screenHeight() / 1334;

        view.setLayoutParams(lt);
        return lt.height;
    }

    public void setLayoutSizeWidht(View view, int widht) {
        ViewGroup.LayoutParams lt = view.getLayoutParams();
        lt.width = widht * screenWidth() / 750;
        view.setLayoutParams(lt);
    }


    public void setTextSize(TextView view, int size) {
        view.setTextSize(pxToSp(size * screenWidth() / 750) + 3);
    }


    public void setButtonTextSize(Button view, int size) {
        view.setTextSize(pxToSp(size * screenWidth() / 750) + 3);
    }


    public void setRelativeLayoutMargin(
            View view,
            int left,
            int top,
            int right,
            int bottom) {
        RelativeLayout navigationLl = new RelativeLayout(mActivity);

        RelativeLayout.LayoutParams lt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        navigationLl.setLayoutParams(lt);
        lt.setMargins(left * screenWidth() / 750, top * screenHeight() / 1334, right * screenWidth() / 750, bottom * screenHeight() / 1334);
        view.setLayoutParams(lt);
    }


    public void setRelativeLayoutMargin(
            View view,
            int left,
            int top,
            int right,
            int bottom, int a) {
        RelativeLayout navigationLl = new RelativeLayout(mActivity);

        RelativeLayout.LayoutParams lt = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        navigationLl.setLayoutParams(lt);
        lt.setMargins(left * screenWidth() / 750, top * screenHeight() / 1334, right * screenWidth() / 750, bottom * screenHeight() / 1334);
        view.setLayoutParams(lt);
    }


    public void setLinearLayoutMargin(
            LinearLayout viewGroup,
            View view,
            int left,
            int top,
            int right,
            int bottom) {
        LinearLayout.LayoutParams lt = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        viewGroup.setLayoutParams(lt);
        lt.setMargins(left * screenWidth() / 750, top * screenHeight() / 1334, right * screenWidth() / 750, bottom * screenHeight() / 1334);
        view.setLayoutParams(lt);
    }

    //得到屏幕宽
    public int screenWidth() {
        return screenSize().widthPixels;
    }

    //得到屏幕的高
    public int screenHeight() {
        return screenSize().heightPixels;
    }
}
