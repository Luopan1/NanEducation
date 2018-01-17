package com.edusdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/8/11.
 */

public class DisListView extends ListView {
    public DisListView(Context context) {
        super(context);
    }

    public DisListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
