package www.test720.com.naneducation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import www.test720.com.naneducation.R;

/**
 * @author LuoPan on 2018/1/10 16:05.
 */

public class TriangleView extends TextView {

    public static final String TAG = TriangleView.class.getName();

    public static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

    private Drawable src;
    private int drawablePosition;
    private int drawableWidth;
    private int drawableHeight;
    private Bitmap mBitmap;

    public TriangleView(Context context) {
        super(context);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView, defStyleAttr, 0);
        //默认设置为“college”图片
        src = getResources().getDrawable(R.drawable.dingweilan);
        Log.d(TAG, "DrawableTextView: " + src);
        //默认设置为左边
        drawablePosition = BOTTOM;
        Log.d(TAG, "DrawableTextView: " + drawablePosition);
        //默认为20dp宽
        drawableWidth = array.getDimensionPixelSize(R.styleable.DrawableTextView_drawableWidth, 0);
        Log.d(TAG, "DrawableTextView: " + drawableWidth);
        //默认为20dp长
        drawableHeight = array.getDimensionPixelSize(R.styleable.DrawableTextView_drawableHeight, 0);
        Log.d(TAG, "DrawableTextView: " + drawableHeight);
        array.recycle();
        drawDrawable();


    }


    private void drawDrawable() {
        if (src != null) {
            Bitmap bitmap = ((BitmapDrawable) src).getBitmap();
            Drawable drawable;
            if (drawableWidth != 0 && drawableHeight != 0) {
                drawable = new BitmapDrawable(getResources(), getBitmap(bitmap, null));
            } else {
                drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true));
            }
            switch (drawablePosition) {
                case LEFT:
                    this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    break;
                case TOP:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                    break;
                case RIGHT:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                    break;
                case BOTTOM:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                    break;
            }
        }
    }


    public Bitmap getBitmap(Bitmap bitmap, Rect bounds) {
        if (bounds == null || bitmap == null) {
            return bitmap;
        } else {
            // 记录src的宽高
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int bWidth = bounds.width();
            int bHeight = bounds.height();
            // 计算缩放比例
            int scaleWidth = bWidth;
            int scaleHeight = bHeight + height;

            int scale = bWidth / width;
            // 开始缩放
            return Bitmap.createScaledBitmap(bitmap, (scale + 2) * 30, scaleHeight + 30, true);
        }
    }

}
