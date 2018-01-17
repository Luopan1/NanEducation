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
import www.test720.com.naneducation.personcenteractivity.orderfragment.MyAleardyPayOrderFragment;
import www.test720.com.naneducation.personcenteractivity.orderfragment.MyAllOrderFragment;
import www.test720.com.naneducation.personcenteractivity.orderfragment.MyWaiteForPayOrderFragment;

public class MyOrderActivity extends BaseToolbarActivity {


    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.radioButton3)
    RadioButton mRadioButton3;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initData() {
        mFragments.add(new MyAllOrderFragment());
        mFragments.add(new MyWaiteForPayOrderFragment());
        mFragments.add(new MyAleardyPayOrderFragment());
        setAdapter();
    }

    private void setAdapter() {
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
                    mRadioButton1.setChecked(true);
                    mRadioButton2.setChecked(false);
                    mRadioButton3.setChecked(false);
                } else if (position == 1) {
                    mRadioButton1.setChecked(false);
                    mRadioButton2.setChecked(true);
                    mRadioButton3.setChecked(false);
                } else if (position == 2) {
                    mRadioButton1.setChecked(false);
                    mRadioButton2.setChecked(false);
                    mRadioButton3.setChecked(true);
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
        initToobar("我的订单");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
    }


    @OnClick({R.id.radioButton1, R.id.radioButton2, R.id.radioButton3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radioButton2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.radioButton3:
                mViewPager.setCurrentItem(2);
                break;
        }
    }
}
