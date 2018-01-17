package www.test720.com.naneducation.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

/**
 * Created by LuoPan on 2017/5/15 13:04.
 */
public class NetImageLoaderHolder implements Holder<String> {
    /**
     * 所需要的图片视图
     */
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        /*
         * 添加Glide依赖
         */
        Glide.with(context).load(data).centerCrop().into(imageView);
    }
}
