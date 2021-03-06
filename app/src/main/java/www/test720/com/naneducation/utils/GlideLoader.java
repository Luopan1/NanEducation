package www.test720.com.naneducation.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

import www.test720.com.naneducation.R;


/**
 * Created by lee on 2017/3/17.
 */

public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(path.contains("http") ? path : new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}