package www.test720.com.naneducation.view;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LuoPan on 2017/7/22.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int top, left, right;


    public SpaceItemDecoration(int top, int left, int right) {
        this.top = top;
        this.left = left;
        this.right = right;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.top = top;
        outRect.right = right;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0 || parent.getChildPosition(view) == 1) {
            outRect.top = top / 2;
        }


    }
}
