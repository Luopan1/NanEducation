package www.test720.com.naneducation.personcenteractivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.personcenteractivity.activityfragment.MyJoinActivityFragment;
import www.test720.com.naneducation.personcenteractivity.activityfragment.MyReleaseActivityFragment;

public class ActivityCenterActvity extends BaseToolbarActivity {


    @BindView(R.id.radioButton0)
    RadioButton mRadioButton0;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_center_actvity;
    }

    @Override
    protected void initData() {
        mFragments.add(new MyReleaseActivityFragment());
        mFragments.add(new MyJoinActivityFragment());
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
                switch (position) {
                    case 0:
                        mRadioButton0.setChecked(true);
                        mRadioButton1.setChecked(false);
                        break;
                    case 1:
                        mRadioButton0.setChecked(false);
                        mRadioButton1.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;

    }

    @Override
    protected void initView() {
        initToobar("活动");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }

    @OnClick({R.id.radioButton0, R.id.radioButton1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton0:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radioButton1:
                mViewPager.setCurrentItem(1);
                break;
        }
    }
}
