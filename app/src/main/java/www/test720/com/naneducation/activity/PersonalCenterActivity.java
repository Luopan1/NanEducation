package www.test720.com.naneducation.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.personcenteractivity.ActivityCenterActvity;
import www.test720.com.naneducation.personcenteractivity.AlreadyBuyAllCourseActivity;
import www.test720.com.naneducation.personcenteractivity.AlreadyBuyCourseActivity;
import www.test720.com.naneducation.personcenteractivity.AlreadyBuyLiveBroadCastActivity;
import www.test720.com.naneducation.personcenteractivity.AlreadyBuyVideoCacheActivity;
import www.test720.com.naneducation.personcenteractivity.ApplyAgentActivity;
import www.test720.com.naneducation.personcenteractivity.MyAdvicesFeedBackActivity;
import www.test720.com.naneducation.personcenteractivity.MyBindInfoActivity;
import www.test720.com.naneducation.personcenteractivity.MyColocationActivity;
import www.test720.com.naneducation.personcenteractivity.MyIntegerActivity;
import www.test720.com.naneducation.personcenteractivity.MyOrderActivity;
import www.test720.com.naneducation.personcenteractivity.PersonSettingAcitivty;
import www.test720.com.naneducation.personcenteractivity.PlatformOccupancyActivity;
import www.test720.com.naneducation.personcenteractivity.UserWalletActivity;
import www.test720.com.naneducation.view.CircleImageView;

public class PersonalCenterActivity extends BaseToolbarActivity {


    @BindView(R.id.user_head)
    CircleImageView mUserHead;
    @BindView(R.id.userNickName)
    TextView mUserNickName;
    @BindView(R.id.wallet)
    RelativeLayout mWallet;
    @BindView(R.id.imageview1)
    ImageView mImageview1;
    @BindView(R.id.myOrder)
    RelativeLayout mMyOrder;
    @BindView(R.id.imageview2)
    ImageView mImageview2;
    @BindView(R.id.myColocation)
    RelativeLayout mMyColocation;
    @BindView(R.id.imageview3)
    ImageView mImageview3;
    @BindView(R.id.myActivity)
    RelativeLayout mMyActivity;
    @BindView(R.id.imageview4)
    ImageView mImageview4;
    @BindView(R.id.myCourse)
    RelativeLayout mMyCourse;
    @BindView(R.id.imageview5)
    ImageView mImageview5;
    @BindView(R.id.myLiveBroadCast)
    RelativeLayout mMyLiveBroadCast;
    @BindView(R.id.imageview6)
    ImageView mImageview6;
    @BindView(R.id.myVideoCache)
    RelativeLayout mMyVideoCache;
    @BindView(R.id.imageview7)
    ImageView mImageview7;
    @BindView(R.id.myAllCourse)
    RelativeLayout mMyAllCourse;
    @BindView(R.id.imageview8)
    ImageView mImageview8;
    @BindView(R.id.myInteger)
    RelativeLayout mMyInteger;
    @BindView(R.id.imageview9)
    ImageView mImageview9;
    @BindView(R.id.myBindInformation)
    RelativeLayout mMyBindInformation;
    @BindView(R.id.imageview10)
    ImageView mImageview10;
    @BindView(R.id.become_agent)
    RelativeLayout mBecomeAgent;
    @BindView(R.id.imageview11)
    ImageView mImageview11;
    @BindView(R.id.Platform_occupancy)
    RelativeLayout mPlatformOccupancy;
    @BindView(R.id.imageview12)
    ImageView mImageview12;
    @BindView(R.id.myAdvices)
    RelativeLayout mMyAdvices;

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar(R.drawable.fanhuihei, "个人中心", R.drawable.shezhi);
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

        if (Constans.head.startsWith("Uploads")) {
            Glide.with(this).load(UrlFactory.baseImageUrl + Constans.head).into(mUserHead);
        } else if (!Constans.head.isEmpty()) {
            Glide.with(this).load(Constans.head).into(mUserHead);
        } else {
            Glide.with(this).load(R.mipmap.icon_head_portrait).into(mUserHead);
        }


    }


    @OnClick({R.id.user_head, R.id.wallet, R.id.myOrder, R.id.myColocation, R.id.myActivity, R.id.myCourse, R.id.myLiveBroadCast, R.id.myVideoCache, R.id.myAllCourse, R.id.myInteger, R.id.myBindInformation, R.id.become_agent, R.id.Platform_occupancy, R.id.myAdvices})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head:

                break;
            case R.id.wallet:
                jumpToActivity(UserWalletActivity.class, false);
                break;
            case R.id.myOrder:
                jumpToActivity(MyOrderActivity.class, false);
                break;
            case R.id.myColocation:
                jumpToActivity(MyColocationActivity.class, false);
                break;
            case R.id.myActivity:
                jumpToActivity(ActivityCenterActvity.class, false);
                break;
            case R.id.myCourse:
                jumpToActivity(AlreadyBuyCourseActivity.class, false);
                break;
            case R.id.myLiveBroadCast:
                jumpToActivity(AlreadyBuyLiveBroadCastActivity.class, false);
                break;
            case R.id.myVideoCache:
                jumpToActivity(AlreadyBuyVideoCacheActivity.class, false);
                break;
            case R.id.myAllCourse:
                jumpToActivity(AlreadyBuyAllCourseActivity.class, false);
                break;
            case R.id.myInteger:
                jumpToActivity(MyIntegerActivity.class, false);
                break;
            case R.id.myBindInformation:
                jumpToActivity(MyBindInfoActivity.class, false);
                break;
            case R.id.become_agent:
                jumpToActivity(ApplyAgentActivity.class, false);
                break;
            case R.id.Platform_occupancy:
                jumpToActivity(PlatformOccupancyActivity.class, false);
                break;
            case R.id.myAdvices:
                jumpToActivity(MyAdvicesFeedBackActivity.class, false);
                break;
        }
    }

    @Override
    public void RightOnClick() {
        // TODO: 2017/10/23 跳转设置界面
        jumpToActivity(PersonSettingAcitivty.class, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e(Constans.head);
        if (Constans.head.startsWith("Uploads")) {
            Glide.with(this).load(UrlFactory.baseImageUrl + Constans.head).into(mUserHead);
        } else if (!Constans.head.isEmpty()) {
            Glide.with(this).load(Constans.head).into(mUserHead);
        } else {
            Glide.with(this).load(R.mipmap.icon_head_portrait).into(mUserHead);
        }
        mUserNickName.setText(Constans.name);
    }
}
