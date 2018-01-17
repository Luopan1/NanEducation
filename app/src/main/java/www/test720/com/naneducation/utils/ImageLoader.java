package www.test720.com.naneducation.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by LuoPan on 2017/10/13 12:19.
 */

public class ImageLoader {
    private Activity activity;
    private Fragment fragment;
    private RequestManager manager;

    public ImageLoader(Activity activity) {
        this.activity = activity;
        manager = Glide.with(activity);
    }

    public ImageLoader(Fragment fragment) {
        this.fragment = fragment;
        manager = Glide.with(fragment);
    }

    /**
     * 获取RequestManager对象
     *
     * @return
     */
    public RequestManager getManager() {
        return manager;
    }

    /**
     * 加载普通图片
     *
     * @param object
     * @param error
     * @param placeHolder
     * @param view
     */
    public void loadImage(Object object, int error, int placeHolder, ImageView view) {
        manager.load(object)
                .error(error)
                .placeholder(placeHolder)
                .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param object
     * @param error
     * @param placeHolder
     * @param view
     */
    public void loadCircularImage(Object object, int error, int placeHolder, ImageView view) {
        manager.load(object)
                .error(error)
                .placeholder(placeHolder)
                .into(view);
    }
}
