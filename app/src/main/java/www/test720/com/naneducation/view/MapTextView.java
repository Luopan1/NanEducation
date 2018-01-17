package www.test720.com.naneducation.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.widget.TextView;

import www.test720.com.naneducation.R;


/**
 * @author LuoPan on 2018/1/16 14:55.
 */

public class MapTextView {


    public Bitmap getBitMap(Context context, String mapString) {

        TextView textView = new TextView(context);
        textView.setText(mapString);
        textView.setTextSize(20);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setBackground(context.getResources().getDrawable(R.drawable.all_corners_base_color));
        textView.setPadding(10, 10, 10, 10);
        textView.setDrawingCacheEnabled(true);
        textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(textView.getDrawingCache());
        textView.destroyDrawingCache();

        return mergeBitmap_TB(bitmap, BitmapFactory.decodeResource(context.getResources(), R.drawable.dingweilan), false);
    }

    public Bitmap mergeBitmap_TB(Bitmap background, Bitmap foreground, boolean isBaseMax) {

        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight + fgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2, bgHeight, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

}
