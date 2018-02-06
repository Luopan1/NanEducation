package www.test720.com.naneducation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.application.MyApplication;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.SchoolCast;
import www.test720.com.naneducation.fragment.NewsFragment;
import www.test720.com.naneducation.fragment.SchooolSingUpGradeFragment;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class SchoolSignUpGradeInfoActivty extends BaseToolbarActivity {


    @BindView(R.id.SchooltitleImage)
    ImageView mSchooltitleImage;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments;
    @BindView(R.id.immediatelySignUp)
    Button mImmediatelySignUp;
    private String mGradeId;
    private SchoolCast mCast;
    private String mTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_school_sign_up_grade_info_activty;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("gradeId", mGradeId);
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.costDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {


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
                mCast = gson.fromJson(s, SchoolCast.class);

                Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mCast.getData().getDetail().getLogo()).asBitmap().into(mSchooltitleImage);
                mFragments = new ArrayList<>();
                mFragments.add(NewsFragment.getNewsFragment(UrlFactory.schoolGradeDetail + "/detailId/" + mGradeId, false));
                mFragments.add(new SchooolSingUpGradeFragment(mCast.getData().getDetail().getList()));
                mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFragments));

                if (mCast.getData().getDetail().getIs_signup() == 0) {
                    mImmediatelySignUp.setText("立即报名");
                } else {
                    mImmediatelySignUp.setText("已报名");
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (SchoolBuyCourseActivity.isBuySuccess) {
            mCast.getData().getDetail().setIs_signup(1);
            mImmediatelySignUp.setText("已报名");
            SchoolBuyCourseActivity.isBuySuccess = false;
        }
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
                } else if (position == 1) {
                    mRadioButton1.setChecked(false);
                    mRadioButton2.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void RightOnClick() {
        UMImage thumb = new UMImage(this, R.drawable.zuxuelogo);
        thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        UMWeb web = new UMWeb(UrlFactory.downLoadUrl);
        web.setTitle("助学");//标题
        web.setThumb(thumb);
        web.setDescription("我在学海app里报名学习了" + mTitle + "的课程");

        new ShareAction(this)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.e("TAG++onStart", platform.toString());
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("TAG+onResult", platform.toString());
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("TAG+onError", platform.toString() + t.toString());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.e("TAG+onCancel", platform.toString());

        }
    };

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
        mGradeId = getIntent().getStringExtra("id");
        mTitle = getIntent().getStringExtra("title");
        initToobar(R.mipmap.fanhui, mTitle, R.drawable.fenxiang);
    }


    @OnClick({R.id.radioButton1, R.id.radioButton2, R.id.immediatelySignUp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radioButton2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.immediatelySignUp:
                if (isLogined(1)) {
                    if (mCast.getData().getDetail().getIs_signup() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("mType", 3);
                        bundle.putString("id", mGradeId);
                        jumpToActivity(SchoolBuyCourseActivity.class, bundle, false);
                    } else {
                        ShowToast("已报名  请到订单详情页面查看信息");
                    }
                }


                break;
        }
    }


}
