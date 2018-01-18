package www.test720.com.naneducation.splashfragment;

import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.MainActivity;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.utils.SPUtils;

/**
 * @author LuoPan on 2018/1/18 12:30.
 */

public class ThreeFragment extends BaseFragment {

    @BindView(R.id.imageview)
    ImageView mImageview;
    @BindView(R.id.enter)
    Button mEnter;

    @Override
    protected void initView() {
        Glide.with(getActivity()).load(R.drawable.p3).into(mImageview);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.splash_fragment_end;
    }

    @OnClick(R.id.enter)
    public void onClick() {
        SPUtils.setHasInstall(true);
        jumpToActivity(MainActivity.class, true);
    }
}
