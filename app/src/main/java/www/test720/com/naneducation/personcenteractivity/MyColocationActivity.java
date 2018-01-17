package www.test720.com.naneducation.personcenteractivity;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.personcenteractivity.colocation_fragment.ActivityFragment;
import www.test720.com.naneducation.personcenteractivity.colocation_fragment.AllCourseFragment;
import www.test720.com.naneducation.personcenteractivity.colocation_fragment.CourseFragment;
import www.test720.com.naneducation.personcenteractivity.colocation_fragment.LiveBroadCastFragment;
import www.test720.com.naneducation.personcenteractivity.colocation_fragment.SchoolFragment;
import www.test720.com.naneducation.personcenteractivity.colocation_fragment.VideoCacheFragment;

public class MyColocationActivity extends BaseToolbarActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.radioButton0)
    RadioButton mRadioButton0;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.radioButton3)
    RadioButton mRadioButton3;
    @BindView(R.id.radioButton4)
    RadioButton mRadioButton4;
    @BindView(R.id.radioButton5)
    RadioButton mRadioButton5;
    List<Fragment> mFragments;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_colocation;
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new LiveBroadCastFragment());
        mFragments.add(new VideoCacheFragment());
        mFragments.add(new AllCourseFragment());
        mFragments.add(new CourseFragment());
        mFragments.add(new ActivityFragment());
        mFragments.add(new SchoolFragment());

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
                        mRadioButton2.setChecked(false);
                        mRadioButton3.setChecked(false);
                        mRadioButton4.setChecked(false);
                        mRadioButton5.setChecked(false);
                        break;
                    case 1:
                        mRadioButton0.setChecked(false);
                        mRadioButton1.setChecked(true);
                        mRadioButton2.setChecked(false);
                        mRadioButton3.setChecked(false);
                        mRadioButton4.setChecked(false);
                        mRadioButton5.setChecked(false);
                        break;
                    case 2:
                        mRadioButton0.setChecked(false);
                        mRadioButton1.setChecked(false);
                        mRadioButton2.setChecked(true);
                        mRadioButton3.setChecked(false);
                        mRadioButton4.setChecked(false);
                        mRadioButton5.setChecked(false);
                        break;
                    case 3:
                        mRadioButton0.setChecked(false);
                        mRadioButton1.setChecked(false);
                        mRadioButton2.setChecked(false);
                        mRadioButton3.setChecked(true);
                        mRadioButton4.setChecked(false);
                        mRadioButton5.setChecked(false);
                        break;
                    case 4:
                        mRadioButton0.setChecked(false);
                        mRadioButton1.setChecked(false);
                        mRadioButton2.setChecked(false);
                        mRadioButton3.setChecked(false);
                        mRadioButton4.setChecked(true);
                        mRadioButton5.setChecked(false);
                        break;
                    case 5:
                        mRadioButton0.setChecked(false);
                        mRadioButton1.setChecked(false);
                        mRadioButton2.setChecked(false);
                        mRadioButton3.setChecked(false);
                        mRadioButton4.setChecked(false);
                        mRadioButton5.setChecked(true);
                        break;
                    default:
                        Log.e("MyColocationActivity", " postion" + position);
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
        initToobar("我的收藏");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }

    @OnClick({R.id.radioButton0, R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4, R.id.radioButton5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton0:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radioButton1:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.radioButton2:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.radioButton3:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.radioButton4:
                mViewPager.setCurrentItem(4);
                break;
            case R.id.radioButton5:
                mViewPager.setCurrentItem(5);
                break;
        }
    }
}
