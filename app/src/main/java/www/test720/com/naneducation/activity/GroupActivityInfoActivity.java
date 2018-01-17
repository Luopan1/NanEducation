package www.test720.com.naneducation.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.net.URISyntaxException;
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
import www.test720.com.naneducation.application.MyApplication;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.GroupActivity;
import www.test720.com.naneducation.bean.GroupActivityInfo;
import www.test720.com.naneducation.commonactivity.JuBaoActivity;
import www.test720.com.naneducation.fragment.ImageFragment;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.view.CircleImageView;

import static android.R.attr.mode;
import static www.test720.com.naneducation.R.id.activityAddress;
import static www.test720.com.naneducation.personcenteractivity.EvalateCourseActivity.index;

public class GroupActivityInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.fanhuiRelative)
    LinearLayout mFanhuiRelative;
    @BindView(R.id.shareImage)
    ImageView mShareImage;
    @BindView(R.id.juBaoImage)
    ImageView mJuBaoImage;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.pagerIndecator)
    TextView mPagerIndecator;
    List<Fragment> mFragmentList = new ArrayList<>();
    @BindView(R.id.activityThem)
    TextView mActivityThem;
    @BindView(R.id.canSignUpCount)
    TextView mCanSignUpCount;
    @BindView(R.id.alreadySignUpCunt)
    TextView mAlreadySignUpCunt;
    @BindView(R.id.originatorImage)
    CircleImageView mOriginatorImage;
    @BindView(R.id.activitiTime)
    TextView mActivitiTime;
    @BindView(activityAddress)
    TextView mActivityAddress;
    @BindView(R.id.activityIntroduce)
    TextView mActivityIntroduce;
    @BindView(R.id.activityStatue)
    TextView mActivityStatue;
    @BindView(R.id.signUpStatue)
    TextView mSignUpStatue;
    @BindView(R.id.colocation)
    TextView mColocation;
    @BindView(R.id.originatorName)
    TextView mOriginatorName;
    @BindView(R.id.activityMoney)
    TextView mActivityMoney;
    @BindView(R.id.activityMoneyRelative)
    RelativeLayout mActivityMoneyRelative;
    @BindView(R.id.activityMoneyText)
    TextView mActivityMoneyText;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.bottomLin)
    LinearLayout mBottomLin;
    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;
    private GroupActivityInfo mGroupActivityInfo;
    private List<String> mImageLists = new ArrayList<>();
    private String mId;
    public static int type = -1;
    private PopupWindow mCurPopupWindow;
    private boolean isShow;

    @Override
    protected int getContentView() {
        return R.layout.activity_group_info;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
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

        if (mGroupActivityInfo.getData().getDetail().getIs_love() == 0) {
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
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
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
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        isShow = intent.getBooleanExtra("isShow", false);
        if (isShow) {
            mBottomLin.setVisibility(View.VISIBLE);
        } else {
            mBottomLin.setVisibility(View.INVISIBLE);
        }


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

    @OnClick({R.id.fanhuiRelative, R.id.shareImage, R.id.juBaoImage, R.id.colocation, R.id.signUpStatue, R.id.phoneNumber, R.id.activityAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phoneNumber:
                final MaterialDialog dialog = new MaterialDialog(GroupActivityInfoActivity.this);
                dialog.content("是否拨打客服电话" + mPhoneNumber.getText().toString().trim())//
                        .contentTextSize(14)
                        .titleTextSize(16)
                        .btnText("拨打电话", "取消")//
                        .showAnim(new BounceTopEnter())//
                        .show();
                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        GroupActivityInfoActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPhoneNumber.getText().toString().trim())));
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.activityAddress:
                // TODO: 2018/1/17 导航
                showChooosMap("");
                break;

            case R.id.fanhuiRelative:
                finish();
                break;
            case R.id.shareImage:
                UMWeb web = new UMWeb(UrlFactory.downLoadUrl);
                web.setTitle("助学");//标题
                web.setDescription("我在学海app里面报名参加了" + mGroupActivityInfo.getData().getDetail().getAct_name() + "的活动");
                new ShareAction(this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener)
                        .open();
                break;
            case R.id.juBaoImage:
                mCurPopupWindow = ShowPopwindows.showJuBao(GroupActivityInfoActivity.this, mJuBaoImage, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.tip_text) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", 5);
                            bundle.putString("id", mId);
                            jumpToActivity(JuBaoActivity.class, bundle, false);
                        }
                    }
                });


                break;
            case R.id.colocation:
                if (isLogined(1)) {
                    HttpParams params = new HttpParams();
                    params.put("uid", Constans.uid);
                    params.put("loveId", mId);
                    params.put("typeId", 5);
                    if (mGroupActivityInfo.getData().getDetail().getIs_love() == 0) {
                        params.put("type", 1);
                    } else {
                        params.put("type", 0);
                    }

                    mSubscription = mHttpUtils.getData(UrlFactory.userCollection, params, 4).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                        @Override
                        public void onStart() {
                            showLoadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            cancleLoadingDialog();
                            mSubscription.unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            JSONObject jsonObject = JSON.parseObject(s);
                            ShowToast(jsonObject.getString("msg"));
                            if (mGroupActivityInfo.getData().getDetail().getIs_love() == 0) {
                                mColocation.setText("已收藏");
                                mGroupActivityInfo.getData().getDetail().setIs_love(1);

                                Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                                drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                mColocation.setCompoundDrawables(drawable, null, null, null);


                            } else {
                                mColocation.setText("收藏");
                                mGroupActivityInfo.getData().getDetail().setIs_love(0);


                                Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                                drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                mColocation.setCompoundDrawables(drawable, null, null, null);
                            }

                        }
                    });

                }

                break;
            case R.id.signUpStatue:
                if (isLogined(1)) {
                    if (mSignUpStatue.getText().toString().trim().equals("已报名")) {
                        return;
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("mType", 1);
                        bundle.putString("id", mGroupActivityInfo.getData().getDetail().getAct_id());
                        jumpToActivity(SchoolBuyCourseActivity.class, bundle, false);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void LeftOnClick() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }


    public void showChooosMap(String desLocation) {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.pop_window_choosemap, null);
        Button gaode = (Button) popView.findViewById(R.id.gaode);
        Button baidu = (Button) popView.findViewById(R.id.baidu);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        mCurPopupWindow = new PopupWindow(popView, width, height);
        mCurPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mCurPopupWindow.setFocusable(true);
        mCurPopupWindow.update();
        mCurPopupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mCurPopupWindow.setBackgroundDrawable(dw);
        mCurPopupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvilible(GroupActivityInfoActivity.this, "com.baidu.BaiduMap")) {
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/direction?region=" + Constans.City + "&destination=" + mGroupActivityInfo.getData().getDetail().getAct_address() + "&mode=driving"));
                    startActivity(i1);
                    mCurPopupWindow.dismiss();
                } else {
                    ShowToast("尚未安装百度地图");
                }
            }
        });
        gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvilible(GroupActivityInfoActivity.this, "com.autonavi.minimap")) {
                    Intent intent = null;
                    try {
                        intent = Intent.getIntent("androidamap://route/plan/?sid=BGVIS1&&did=BGVIS2&dname=" + mGroupActivityInfo.getData().getDetail().getAct_address() + "&dev=0&t=0");
                        startActivity(intent);
                        mCurPopupWindow.dismiss();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                    ShowToast("尚未安装高德地图");
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurPopupWindow.dismiss();
            }
        });

    }


    /**
     * 判断地图是否安装
     */
    public boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

}
