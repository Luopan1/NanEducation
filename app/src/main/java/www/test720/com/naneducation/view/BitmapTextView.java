package www.test720.com.naneducation.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

/**
 * @author LuoPan on 2018/1/10 17:31.
 */

public class BitmapTextView {

    private static final String TAG = "BitmapTextView";
    public Bitmap bitmap = null;

    public Bitmap drawBitMapText(String str, Bitmap bitmapDescriptor) {

        int width = bitmapDescriptor.getWidth();
        int height = bitmapDescriptor.getHeight();//marker的获取宽高
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444); //建立一个空的Bitmap

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤
        paint.setColor(Color.RED);
        paint.setTextSize(40);

        Rect bounds = new Rect();
        if (str.length() > 7) {
            str = str.substring(0, 6) + "...";
        }
        paint.getTextBounds(str, 0, str.length(), bounds);//获取文字的范围
        bitmap = scaleBitmap(bitmap, bounds);


        //文字在mMarker中展示的位置
        float paddingLeft = (bitmap.getWidth() - bounds.width());//在中间
        Canvas canvas = new Canvas(bitmap);

        Paint paintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRect.setColor(Color.parseColor("#009CFF"));
        float left = canvas.getHeight();
        RectF r2 = new RectF();                           //RectF对象
        r2.left = paddingLeft;                                 //左边
        r2.top = 0;                                 //上边
        r2.right = paddingLeft + bounds.width();                                   //右边
        r2.bottom = bounds.height() + 10;                              //下边
        canvas.drawRoundRect(r2, 0, 0, paintRect);        //绘制圆角矩形

        canvas.drawText(str, paddingLeft, bounds.height(), paint);

        Log.e("TAG+++++++0", (bitmap.getWidth() - width) / 2 + "");
        //        canvas.drawRoundRect(r3, 10, 10, paintRect);        //测试画布的实际大小，方便查看图片是否在中点位置
        //合并两个bitmap为一个
        canvas.drawBitmap(bitmapDescriptor, 0, bounds.height() + 15, null);//marker的位置
        return bitmap;
    }


    private Bitmap scaleBitmap(Bitmap bitmap, Rect bounds) {
        if (bounds == null || bitmap == null) {
            Log.e("TAG+++++++", "==null");
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

            int scale = 2;
            Log.e("TAG+++++++", "Scale" + scale);
            // 开始缩放
            return Bitmap.createScaledBitmap(bitmap, (scale + 2) * 80, scaleHeight + 30, true);
        }
    }
}
