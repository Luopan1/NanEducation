package www.test720.com.naneducation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
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
import www.test720.com.naneducation.ShowPopwindows;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.application.MyApplication;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.SchoolInfo;
import www.test720.com.naneducation.fragment.NewsFragment;
import www.test720.com.naneducation.fragment.SchoolSignUpFragment;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.view.CircleImageView;

import static com.alipay.sdk.app.statistic.c.w;

public class SchoolInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.schoolImage)
    ImageView mSchoolImage;
    @BindView(R.id.schoolBdage)
    CircleImageView mSchoolBdage;
    @BindView(R.id.schoolName)
    TextView mSchoolName;
    @BindView(R.id.schoolAddress)
    TextView mSchoolAddress;
    @BindView(R.id.schoolPhoneNumber)
    TextView mSchoolPhoneNumber;
    @BindView(R.id.toTheSchool)
    TextView mToTheSchool;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();
    public String mTitle;
    @BindView(R.id.colocation)
    TextView mColocation;
    private String mSchoolId;
    private SchoolInfo mSchoolInfo;
    private PopupWindow mPopupWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_school_info;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("sid", mSchoolId);
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.schoolDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

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
                Gson gson = new Gson();
                mSchoolInfo = gson.fromJson(s, SchoolInfo.class);

                Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mSchoolInfo.getData().getDetail().getS_logo()).asBitmap().into(mSchoolImage);
                mSchoolName.setText(mSchoolInfo.getData().getDetail().getS_name());

                mSchoolAddress.setText(mSchoolInfo.getData().getDetail().getS_address());
                mSchoolPhoneNumber.setText(mSchoolInfo.getData().getDetail().getS_phone());

                if (mSchoolInfo.getData().getDetail().getIs_love() == 0) {
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
                Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mSchoolInfo.getData().getDetail().getS_icon()).asBitmap().into(mSchoolBdage);


                mFragments.add(new NewsFragment(UrlFactory.schoolIntroduce + "/detailId/" + mSchoolId, false));
                mFragments.add(new SchoolSignUpFragment(mSchoolInfo));
                mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFragments));
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
        UMWeb web = new UMWeb(UrlFactory.downLoadUrl);
        UMImage thumb = new UMImage(this, R.drawable.zuxuelogo);
        thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        web.setTitle("助学");//标题
        web.setThumb(thumb);
        web.setDescription("我在学海app里报名了" + mTitle + "的学校");

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
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mSchoolId = intent.getStringExtra("id");
        initToobar(R.mipmap.fanhui, mTitle, R.drawable.fenxiang);
    }


    @OnClick({R.id.schoolPhoneNumber, R.id.toTheSchool, R.id.radioButton1, R.id.radioButton2, R.id.colocation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schoolPhoneNumber:
                final MaterialDialog dialog = new MaterialDialog(SchoolInfoActivity.this);
                dialog.content("是否拨打客服电话" + mSchoolPhoneNumber.getText().toString().trim())//
                        .contentTextSize(14)
                        .titleTextSize(16)
                        .btnText("拨打电话", "取消")//
                        .showAnim(new BounceTopEnter())//
                        .show();
                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        if (ActivityCompat.checkSelfPermission(SchoolInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(SchoolInfoActivity.this, "拨打电话权限已关闭，请打开权限", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SchoolInfoActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mSchoolPhoneNumber.getText().toString().trim())));
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.toTheSchool:
                mPopupWindow = ShowPopwindows.showChooseMapPop(this, "", mSchoolInfo.getData().getDetail().getS_long() + "," + mSchoolInfo.getData().getDetail().getS_lat(), "", "");
                break;
            case R.id.radioButton1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.radioButton2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.colocation:
                if (isLogined(1)) {
                    HttpParams params = new HttpParams();
                    params.put("uid", Constans.uid);
                    params.put("loveId", mSchoolId);
                    params.put("typeId", 6);
                    if (mSchoolInfo.getData().getDetail().getIs_love() == 0) {
                        params.put("type", 1);
                    } else {
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
                            mSubscription.unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            JSONObject object = JSON.parseObject(s);

                            ShowToast(object.getString("msg"));
                            if (mSchoolInfo.getData().getDetail().getIs_love() == 0) {
                                mSchoolInfo.getData().getDetail().setIs_love(1);
                                mColocation.setText("已收藏");

                                Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                                drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                mColocation.setCompoundDrawables(drawable, null, null, null);

                            } else {
                                mSchoolInfo.getData().getDetail().setIs_love(0);
                                mColocation.setText("收藏");
                                Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                                drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                mColocation.setCompoundDrawables(drawable, null, null, null);
                            }
                        }
                    });
                }


                break;
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
}
