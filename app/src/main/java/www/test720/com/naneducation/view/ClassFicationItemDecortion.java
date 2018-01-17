package www.test720.com.naneducation.view;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by LuoPan on 2017/7/22.
 */

public class ClassFicationItemDecortion extends RecyclerView.ItemDecoration {
    private int top, left, right;


    public ClassFicationItemDecortion(int top, int right) {
        this.top = top;
        this.right = right;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = top;

        outRect.right = right;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) <= 2) {
            outRect.top = 0;
        }


    }
}
