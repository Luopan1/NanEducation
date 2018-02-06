package www.test720.com.naneducation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.ShowPopwindows;
import www.test720.com.naneducation.adapter.PagerAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.commonactivity.JuBaoActivity;
import www.test720.com.naneducation.fragment.NewsFragment;
import www.test720.com.naneducation.fragment.TrainCourseFragment;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DensityUtil;

import static android.R.attr.id;

public class TrainSchoolInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.schoolImage)
    ImageView mSchoolImage;
    @BindView(R.id.schoolName)
    TextView mSchoolName;
    @BindView(R.id.schoolAddress)
    TextView mSchoolAddress;
    @BindView(R.id.schoolPhone)
    TextView mSchoolPhone;
    @BindView(R.id.toTheSchool)
    TextView mToTheSchool;
    @BindView(R.id.button1)
    RadioButton mButton1;
    @BindView(R.id.button2)
    RadioButton mButton2;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.grades)
    TextView mGrades;
    private PopupWindow mCurPopupWindow;
    private String mTrainID;
    private double mTrain_lat;
    private double mTrain_long;
    private PopupWindow mPopupWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_train_school_info;
    }

    @Override
    protected void initData() {

        HttpParams params = new HttpParams();
        params.put("trainId", mTrainID);
        mSubscription = mHttpUtils.getData(UrlFactory.trainListDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                cancleLoadingDialog();
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                JSONObject obj = JSON.parseObject(s);
                mSchoolName.setText(obj.getJSONObject("data").getJSONObject("detail").getString("train_name"));
                mSchoolAddress.setText(obj.getJSONObject("data").getJSONObject("detail").getString("train_address"));
                mSchoolPhone.setText(obj.getJSONObject("data").getJSONObject("detail").getString("train_phone"));
                Glide.with(TrainSchoolInfoActivity.this).load(UrlFactory.baseImageUrl + obj.getJSONObject("data").getJSONObject("detail").getString("train_logo")).into(mSchoolImage);

                mTrain_lat = Double.parseDouble(obj.getJSONObject("data").getJSONObject("detail").getString("train_lat"));
                mTrain_long = Double.parseDouble(obj.getJSONObject("data").getJSONObject("detail").getString("train_long"));

                mGrades.setText(obj.getJSONObject("data").getJSONObject("detail").getString("grade") + "分");
            }
        });


        fragments.add(new NewsFragment(UrlFactory.webView + "detailId/" + mTrainID, false));
        fragments.add(new TrainCourseFragment(mTrainID));
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));

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
                    mButton1.setChecked(true);
                    mButton2.setChecked(false);
                } else {
                    mButton1.setChecked(false);
                    mButton2.setChecked(true);
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

        Intent intent = getIntent();
        mTrainID = intent.getStringExtra("id");
        initToobar(R.drawable.fanhuihei, "机构详情", R.drawable.xuanxaing);
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }


    @OnClick({R.id.toTheSchool, R.id.button1, R.id.button2, R.id.schoolPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toTheSchool:
                mPopupWindow = ShowPopwindows.showChooseMapPop(this, "", mTrain_long + "," + mTrain_lat, "", "");
                break;
            case R.id.button1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.button2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.schoolPhone:
                final MaterialDialog dialog = new MaterialDialog(TrainSchoolInfoActivity.this);
                dialog.content("是否拨打客服电话" + mSchoolPhone.getText().toString().trim())//
                        .contentTextSize(14)
                        .titleTextSize(16)
                        .btnText("拨打电话", "取消")//
                        .showAnim(new BounceTopEnter())//
                        .show();
                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        if (ActivityCompat.checkSelfPermission(TrainSchoolInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(TrainSchoolInfoActivity.this, "拨打电话权限已关闭，请打开权限", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        TrainSchoolInfoActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mSchoolPhone.getText().toString().trim())));
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void RightOnClick() {
        mCurPopupWindow = ShowPopwindows.showJuBao(this, mRightRelative, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.tip_text) {
                    if (isLogined(1)) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 4);
                        bundle.putString("id", mTrainID);
                        jumpToActivity(JuBaoActivity.class, bundle, false);
                    }
                }
            }
        });
    }

    @Override
    public void LeftOnClick() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

}
