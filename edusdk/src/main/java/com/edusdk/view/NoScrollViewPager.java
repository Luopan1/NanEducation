package com.edusdk.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/12.
 */

public class NoScrollViewPager extends ViewPager {
    private boolean noScroll = true;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll) {
            //            super.onTouchEvent(arg0);
            return false;
        } else {
            try {
                return super.onTouchEvent(arg0);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            //            super.onInterceptTouchEvent(arg0);
            return false;
        } else {
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //        if (noScroll){
        //            super.dispatchTouchEvent(ev);
        //            return false;
        //        } else {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return false;
        }
        //        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
