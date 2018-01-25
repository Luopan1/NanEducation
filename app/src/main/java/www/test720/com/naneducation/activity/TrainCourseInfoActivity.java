package www.test720.com.naneducation.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.ShowPopwindows;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.TrainCourse;
import www.test720.com.naneducation.fragment.NewsFragment;
import www.test720.com.naneducation.fragment.TrainCourseEvaluateFragment;
import www.test720.com.naneducation.fragment.TrainCoursePlanFragment;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.personcenteractivity.EvalateCourseActivity;
import www.test720.com.naneducation.utils.DensityUtil;

public class TrainCourseInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.titleImage)
    ImageView mTitleImage;
    @BindView(R.id.schoolImage)
    ImageView mSchoolImage;
    @BindView(R.id.schoolName)
    TextView mSchoolName;
    @BindView(R.id.toThere)
    TextView mToThere;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.radioButton3)
    RadioButton mRadioButton3;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.colocationRelative)
    RelativeLayout mColocationRelative;
    @BindView(R.id.immediatelySignUp)
    TextView mImmediatelySignUp;
    @BindView(R.id.immediatelySignUpRelative)
    RelativeLayout mImmediatelySignUpRelative;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.colocation)
    TextView mColocation;
    private int TYPE;
    private String mCourseId;
    private TrainCourse mTrainCourse;
    private PopupWindow mPopupWindow;
    private PagerAdapter mAdapter;
    int count = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_train_course_info;
    }

    @Override
    protected void initData() {
        count++;
        HttpParams params = new HttpParams();
        params.put("cid", mCourseId);
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.trainCourseDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                cancleLoadingDialog();
            }

            @Override
            public void onNext(String s) {
                Gson gson = new Gson();
                mTrainCourse = gson.fromJson(s, TrainCourse.class);
                Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mTrainCourse.getData().getDetail().getC_logo()).asBitmap().into(mTitleImage);
                Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mTrainCourse.getData().getDetail().getTrain_icon()).asBitmap().into(mSchoolImage);
                mSchoolName.setText(mTrainCourse.getData().getDetail().getTrain_name());

                if (mTrainCourse.getData().getDetail().getIs_love() == 0) {
                    mColocation.setText("收藏");
                    Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                    mColocation.setCompoundDrawables(drawable, null, null, null);
                } else {
                    mColocation.setText("已收藏");
                    Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                    mColocation.setCompoundDrawables(drawable, null, null, null);
                }
                if (mTrainCourse.getData().getDetail().getIs_signup() == 0) {
                    mImmediatelySignUp.setText("立即报名");
                } else {
                    if (mTrainCourse.getData().getDetail().getIs_take() == 1) {
                        mImmediatelySignUp.setText("已评价");
                        mImmediatelySignUpRelative.setBackgroundColor(getResources().getColor(R.color.system_color));
                    } else {
                        mImmediatelySignUp.setText("去评价");
                        mImmediatelySignUpRelative.setBackgroundColor(getResources().getColor(R.color.base_color));
                    }

                }

                if (count == 1) {
                    // TODO: 2017/11/9 不显示报名人列表
                    if (mTrainCourse.getData().getDetail().getIs_signup() == 1) {
                        mFragments.add(NewsFragment.getNewsFragment(UrlFactory.courseDetail + "/detailId/" + mCourseId, true, mTrainCourse.getData().getDetail().getUserlist()));
                    } else {
                        mFragments.add(new NewsFragment(UrlFactory.courseDetail + "/detailId/" + mCourseId, false));
                    }
                    mFragments.add(TrainCoursePlanFragment.getInstance(mTrainCourse.getData().getDetail().getPlan()));
                    mFragments.add(TrainCourseEvaluateFragment.getInstance(mTrainCourse.getData().getDetail().getC_grade(), mTrainCourse.getData().getDetail().getIs_take()));
                    mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragments);
                    mViewPager.setAdapter(mAdapter);

                }

            }
        });


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
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar(R.drawable.fanhuihei, "课程详情", R.drawable.fenxiang);
        setToolbarColor(R.color.white);
        Intent intent = getIntent();
        TYPE = intent.getIntExtra("type", 0);
        mCourseId = intent.getStringExtra("id");
        setTitleColor(R.color.black);
    }


    @Override
    public void RightOnClick() {
        UMWeb web = new UMWeb(UrlFactory.downLoadUrl);
        web.setTitle("助学");//标题
        web.setDescription("我在助学app里学习" + mTrainCourse.getData().getDetail().getTrain_name() + "的" + mTrainCourse.getData().getDetail().getPlan().getCourse_type());
        new ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @param platform 平台类型
         * @descrption 分享开始的回调
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.e("TAG++onStart", platform.toString());
        }

        /**
         * @param platform 平台类型
         * @descrption 分享成功的回调
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("TAG+onResult", platform.toString());
        }

        /**
         * @param platform 平台类型
         * @param t        错误原因
         * @descrption 分享失败的回调
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("TAG+onError", platform.toString() + t.toString());
        }

        /**
         * @param platform 平台类型
         * @descrption 分享取消的回调
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.e("TAG+onCancel", platform.toString());

        }
    };


    @OnClick({R.id.toThere, R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.colocationRelative, R.id.immediatelySignUpRelative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toThere:
                mPopupWindow = ShowPopwindows.showChooseMapPop(this, "", mTrainCourse.getData().getDetail().getTrain_long() + "," + mTrainCourse.getData().getDetail().getTrain_lat(), "", "");
                break;
            case R.id.radioButton1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radioButton2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.radioButton3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.colocationRelative:
                // TODO: 2017/10/18 收藏
                if (isLogined(1)) {
                    HttpParams params = new HttpParams();
                    params.put("uid", Constans.uid);
                    params.put("loveId", mCourseId);
                    params.put("typeId", 4);
                    if (mTrainCourse.getData().getDetail().getIs_love() == 0) {
                        params.put("type", 1);
                    } else if (mTrainCourse.getData().getDetail().getIs_love() == 1) {
                        params.put("type", 0);
                    }
                    mSubscription = mHttpUtils.getData(UrlFactory.userCollection, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                        @Override
                        public void onStart() {
                            showLoadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            if (mTrainCourse.getData().getDetail().getIs_love() == 0) {
                                mTrainCourse.getData().getDetail().setIs_love(1);
                                mColocation.setText("已收藏");
                                Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                                drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                mColocation.setCompoundDrawables(drawable, null, null, null);
                            } else if (mTrainCourse.getData().getDetail().getIs_love() == 1) {
                                mTrainCourse.getData().getDetail().setIs_love(0);
                                mColocation.setText("收藏");
                                Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                                drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                mColocation.setCompoundDrawables(drawable, null, null, null);
                            }
                        }
                    });

                }
                break;
            case R.id.immediatelySignUpRelative:
                if (isLogined(1)) {
                    if (mTrainCourse.getData().getDetail().getIs_signup() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("mType", 2);
                        bundle.putString("id", mCourseId);
                        jumpToActivity(SchoolBuyCourseActivity.class, bundle, false);
                    } else {
                        if (mTrainCourse.getData().getDetail().getIs_take() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", mCourseId);
                            jumpToActivity(EvalateCourseActivity.class, bundle, false);
                        } else {

                        }

                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (EvalateCourseActivity.index == 1 && mTrainCourse != null) {
            mTrainCourse.getData().getDetail().setIs_take(1);
            mImmediatelySignUp.setText("已评价");
            mImmediatelySignUpRelative.setBackgroundColor(getResources().getColor(R.color.system_color));
            EvalateCourseActivity.index = 0;
        }
        if (SchoolBuyCourseActivity.isBuySuccess) {
            initData();
            SchoolBuyCourseActivity.isBuySuccess = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void LeftOnClick() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
