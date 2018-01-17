package www.test720.com.naneducation.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jie on 2017/6/21.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int index;

    private int left;
    private int right;
    private int bottom;
    private int top;

    public SpacesItemDecoration(int index, int space) {
        this.space = space;
        this.index = index;
    }

    public SpacesItemDecoration(int index, int left, int right, int bottom, int top) {
        this.index = index;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (index == 1) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
        } else {
            outRect.left = left;
            outRect.right = right;
            outRect.bottom = bottom;
            outRect.top = top;
        }

        // Add top margin only for the first item to avoid double space between items
        //        if (parent.getChildPosition(view) == 0)

    }

}