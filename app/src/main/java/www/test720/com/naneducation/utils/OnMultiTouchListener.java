package www.test720.com.naneducation.utils;

/**
 * @author LuoPan on 2017/12/28 10:44.
 */

import android.view.MotionEvent;
import android.view.View;

/**
 * 连续点击事件监听器 可以用作双击事件
 */
public class OnMultiTouchListener implements View.OnTouchListener {
    int count;
    long firClick;
    long secClick;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            count++;
            if (count == 1) {
                firClick = System.currentTimeMillis();

            } else if (count == 2) {
                secClick = System.currentTimeMillis();
                if (secClick - firClick < 1000) {
                    //双击事件
                    return true;
                }
                count = 0;
                firClick = 0;
                secClick = 0;

            }
        }
        return false;
    }

}
