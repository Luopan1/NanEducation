package www.test720.com.naneducation.splashfragment;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseFragment;

/**
 * @author LuoPan on 2018/1/18 12:30.
 */

public class TwoFragment extends BaseFragment {

    @BindView(R.id.imageview)
    ImageView mImageview;

    @Override
    protected void initView() {
        Glide.with(getActivity()).load(R.drawable.taoke2222).into(mImageview);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.splash_fragment;
    }
}
