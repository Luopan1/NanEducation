package www.test720.com.naneducation;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.splashfragment.OneFragment;
import www.test720.com.naneducation.splashfragment.ThreeFragment;
import www.test720.com.naneducation.splashfragment.TwoFragment;

public class SplashActivity extends BaseToolbarActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.iv3)
    ImageView mIv3;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        mFragments.add(new OneFragment());
        mFragments.add(new TwoFragment());
        mFragments.add(new ThreeFragment());
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFragments));
    }

    @Override
    protected void setListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mIv1.setImageDrawable(getResources().getDrawable(R.mipmap.circle2));
                    mIv2.setImageDrawable(getResources().getDrawable(R.mipmap.circle1));
                    mIv3.setImageDrawable(getResources().getDrawable(R.mipmap.circle1));
                    mIv1.setVisibility(View.VISIBLE);
                    mIv2.setVisibility(View.VISIBLE);
                    mIv3.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    mIv2.setImageDrawable(getResources().getDrawable(R.mipmap.circle2));
                    mIv1.setImageDrawable(getResources().getDrawable(R.mipmap.circle1));
                    mIv3.setImageDrawable(getResources().getDrawable(R.mipmap.circle1));
                    mIv1.setVisibility(View.VISIBLE);
                    mIv2.setVisibility(View.VISIBLE);
                    mIv3.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    mIv3.setImageDrawable(getResources().getDrawable(R.mipmap.circle2));
                    mIv1.setImageDrawable(getResources().getDrawable(R.mipmap.circle1));
                    mIv2.setImageDrawable(getResources().getDrawable(R.mipmap.circle1));

                    mIv1.setVisibility(View.INVISIBLE);
                    mIv2.setVisibility(View.INVISIBLE);
                    mIv3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }
}
