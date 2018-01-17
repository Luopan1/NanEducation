package www.test720.com.naneducation.fragment;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseFragment;

/**
 * Created by LuoPan on 2017/10/19 18:56.
 */

public class ImageFragment extends BaseFragment {
    @BindView(R.id.imageView)
    ImageView mImageView;
    String url;

    public ImageFragment() {
    }


    @SuppressLint("ValidFragment")
    public ImageFragment(String url) {
        this.url = url;
    }

    @Override
    public void initView() {
        Glide.with(getActivity()).load(url).into(mImageView);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_image;
    }


}
