package www.test720.com.naneducation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.view.GlideRoundTransform;


/**
 * Created by LuoPan on 2017/5/16 14:09.
 */
public class ViewHolder {
    //现在对于int作为键的官方推荐用SparseArray替代HashMap
    private final SparseArray<View> views;
    private View convertView;
    private Context context;
    public static int mPosition;

    private ViewHolder(Context context, ViewGroup parent, int itemLayoutId, int position) {
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
        convertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {

            return new ViewHolder(context, parent, layoutId, position);
        }
        mPosition = position;
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }


    /**
     * 设置字符串
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        if (url == null) {
            setImageResource(viewId, R.mipmap.zhuanquan);
        } else {
            Glide.with(context).load(url).error(R.mipmap.zhuanquan).into((ImageView) getView(viewId));
        }
        return this;
    }

    /**
     * 设置图片样式
     *
     * @param tag 0表示纯圆形图片 1表示圆形图片外边有白框
     */
    public ViewHolder setCicleImageByUrl(Context context, int viewId, String url, int tag) {
        if (url == null) {
            setImageResource(viewId, R.mipmap.ic_launcher);
        } else {
            glideLoader(context, url, R.mipmap.ic_launcher, R.mipmap.ic_launcher, viewId, tag);
        }
        return this;
    }

    public void glideLoader(Context context, String url, int erroImg, int emptyImg, int viewId, int tag) {
        //        if (0 == tag) {
        //            Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).thumbnail(0.2f).transform(new GlideCircleTransform(context)).into((ImageView) getView(viewId));
        //        } else if (1 == tag) {
        //            Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).thumbnail(0.2f).transform(new GlideRoundTransform(context, 10)).into((ImageView) getView(viewId));
        //        }
    }
}
