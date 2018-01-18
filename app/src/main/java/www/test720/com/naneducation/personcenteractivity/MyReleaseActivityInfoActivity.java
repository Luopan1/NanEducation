package www.test720.com.naneducation.personcenteractivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
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
import www.test720.com.naneducation.activity.CourseInfoActivity;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.application.MyApplication;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.GroupActivityInfo;
import www.test720.com.naneducation.fragment.ImageFragment;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.view.CircleImageView;

public class MyReleaseActivityInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.pagerIndecator)
    TextView mPagerIndecator;
    @BindView(R.id.activityThem)
    TextView mActivityThem;
    @BindView(R.id.checkSignMembers)
    TextView mCheckSignMembers;
    @BindView(R.id.canSignUpCount)
    TextView mCanSignUpCount;
    @BindView(R.id.alreadySignUpCunt)
    TextView mAlreadySignUpCunt;
    @BindView(R.id.originatorImage)
    CircleImageView mOriginatorImage;
    @BindView(R.id.originatorName)
    TextView mOriginatorName;
    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;
    @BindView(R.id.activitiTime)
    TextView mActivitiTime;
    @BindView(R.id.activityAddress)
    TextView mActivityAddress;
    @BindView(R.id.activityMoneyText)
    TextView mActivityMoneyText;
    @BindView(R.id.activityMoney)
    TextView mActivityMoney;
    @BindView(R.id.activityMoneyRelative)
    RelativeLayout mActivityMoneyRelative;
    @BindView(R.id.activityIntroduce)
    TextView mActivityIntroduce;
    @BindView(R.id.activityStatue)
    TextView mActivityStatue;
    @BindView(R.id.signUpStatue)
    TextView mSignUpStatue;
    @BindView(R.id.bottomLin)
    LinearLayout mBottomLin;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private GroupActivityInfo mGroupActivityInfo;
    private List<String> mImageLists = new ArrayList<>();
    private String mId;
    public static int type = -1;
    private PopupWindow mCurPopupWindow;
    private boolean isShow;
    List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentView() {

        return R.layout.activity_my_release_info;
    }


    public void getData() {
        HttpParams params = new HttpParams();
        params.put("actId", mId);
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.actListDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                mRefreshLayout.finishRefreshing();
                cancleLoadingDialog();
                mSubscription.unsubscribe();
                type = -1;

            }

            @Override
            public void onError(Throwable e) {
                mRefreshLayout.finishRefreshing();
                cancleLoadingDialog();
                ShowToast(e.getMessage());
                type = -1;
            }

            @Override
            public void onNext(String s) {
                Gson gson = new Gson();

                mImageLists.clear();

                mGroupActivityInfo = gson.fromJson(s, GroupActivityInfo.class);

                // 设置视图
                mImageLists.addAll(mGroupActivityInfo.getData().getDetail().getImgList());
                if (mFragmentList.size() == 0) {
                    for (int i = 0; i < mImageLists.size(); i++) {
                        mFragmentList.add(new ImageFragment(UrlFactory.baseImageUrl + mImageLists.get(i)));
                    }
                    mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFragmentList));
                }

                if (mImageLists.size() == 0) {
                    mPagerIndecator.setText((0) + "/" + mImageLists.size());
                } else {
                    mPagerIndecator.setText((1) + "/" + mImageLists.size());
                }

                mPhoneNumber.setText("联系方式：" + mGroupActivityInfo.getData().getDetail().getAct_phone());
                mActivityThem.setText(mGroupActivityInfo.getData().getDetail().getAct_name());
                mCanSignUpCount.setText("可参加：" + mGroupActivityInfo.getData().getDetail().getAct_mun());
                mAlreadySignUpCunt.setText("已参加：" + mGroupActivityInfo.getData().getDetail().getAct_count());
                Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mGroupActivityInfo.getData().getDetail().getHead()).asBitmap().into(mOriginatorImage);
                mOriginatorName.setText(mGroupActivityInfo.getData().getDetail().getName());
                mActivitiTime.setText(mGroupActivityInfo.getData().getDetail().getAct_startime() + "~" + mGroupActivityInfo.getData().getDetail().getAct_endtime());
                mActivityAddress.setText(mGroupActivityInfo.getData().getDetail().getAct_address());
                mActivityIntroduce.setText(mGroupActivityInfo.getData().getDetail().getAct_content());
                if (mGroupActivityInfo.getData().getDetail().getAct_money().equals("0")) {
                    mActivityMoneyRelative.setVisibility(View.GONE);
                } else {
                    mActivityMoneyRelative.setVisibility(View.VISIBLE);
                    mActivityMoney.setText(mGroupActivityInfo.getData().getDetail().getAct_money() + "元");
                }
                refreshView();

            }
        });
    }

    public void refreshView() {
        if (mGroupActivityInfo.getData().getDetail().getType().equals("已结束")) {
            mActivityStatue.setText(mGroupActivityInfo.getData().getDetail().getType());

            if (mGroupActivityInfo.getData().getDetail().getIs_sign() == 0) {
                mActivityStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setVisibility(View.GONE);
                mSignUpStatue.setText("立即报名");
            } else {
                mActivityStatue.setVisibility(View.GONE);
                mSignUpStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setText("已报名");
                mSignUpStatue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_black_gray));
            }

        } else if (mGroupActivityInfo.getData().getDetail().getType().equals("进行中")) {

            mActivityStatue.setText(mGroupActivityInfo.getData().getDetail().getType());

            if (mGroupActivityInfo.getData().getDetail().getIs_sign() == 0) {
                mActivityStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setVisibility(View.GONE);
                mSignUpStatue.setText("立即报名");
            } else {
                mActivityStatue.setVisibility(View.GONE);
                mSignUpStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setText("已报名");
                mSignUpStatue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_black_gray));
            }

        } else if (mGroupActivityInfo.getData().getDetail().getType().equals("可报名")) {
            mActivityStatue.setText(mGroupActivityInfo.getData().getDetail().getType());
            if (mGroupActivityInfo.getData().getDetail().getIs_sign() == 0) {
                mActivityStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setText("立即报名");

                mSignUpStatue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_base_color));
            } else {
                mActivityStatue.setVisibility(View.GONE);
                mSignUpStatue.setVisibility(View.VISIBLE);
                mSignUpStatue.setText("已报名");
                mSignUpStatue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_black_gray));
            }
        }

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mImageLists.size() == 0) {
                    mPagerIndecator.setText((position) + "/" + mImageLists.size());
                } else {
                    mPagerIndecator.setText((position + 1) + "/" + mImageLists.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type == 0) {
            mRefreshLayout.startRefresh();
        }
    }

    @Override
    protected void initView() {
        showLoadingDialog();
        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.startRefresh();
            }
        }, 200);

        setTitleColor(R.color.white);
        setToolbarColor(R.color.base_color);
        initToobar(R.drawable.fanhuibai, "活动详情", R.drawable.fenxiang);
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
    }


    @Override
    public void RightOnClick() {
        UMWeb web = new UMWeb(UrlFactory.downLoadUrl);
        web.setTitle("助学");//标题
        web.setDescription("我在学海app中发布了" + mGroupActivityInfo.getData().getDetail().getAct_name() + "的活动");
        new ShareAction(MyReleaseActivityInfoActivity.this)
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

    @OnClick(R.id.checkSignMembers)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putString("id", mGroupActivityInfo.getData().getDetail().getAct_id());
        jumpToActivity(SignMembersActivity.class, bundle, false);
    }
}
