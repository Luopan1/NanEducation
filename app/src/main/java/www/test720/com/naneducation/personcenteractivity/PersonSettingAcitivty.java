package www.test720.com.naneducation.personcenteractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.MainActivity;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DataCleanManager;
import www.test720.com.naneducation.utils.GlideLoader;
import www.test720.com.naneducation.utils.ImageLoader;
import www.test720.com.naneducation.utils.SPUtils;
import www.test720.com.naneducation.view.CircleImageView;

public class PersonSettingAcitivty extends BaseToolbarActivity {


    @BindView(R.id.user_head)
    CircleImageView mUserHead;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.user_head_relative)
    RelativeLayout mUserHeadRelative;
    @BindView(R.id.userPhone)
    RelativeLayout mUserPhone;
    @BindView(R.id.switchButton)
    SwitchButton mSwitchButton;
    @BindView(R.id.customNumber)
    TextView mCustomNumber;
    @BindView(R.id.aboutUs)
    RelativeLayout mAboutUs;
    @BindView(R.id.clearCache)
    LinearLayout mClearCache;
    @BindView(R.id.loginOut)
    Button mLoginOut;
    ArrayList<ImageItem> headImages = new ArrayList<>();

    private static final int IMAGE_PICKER = 1;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.nickName)
    TextView mNickName;
    @BindView(R.id.userNickName)
    RelativeLayout mUserNickName;
    @BindView(R.id.cacheSize)
    TextView mCacheSize;
    private ImagePicker imagePicker;
    private ImageLoader mImageLoader;
    private PopupWindow mPopWindow;
    private String mName;

    @Override
    protected int getContentView() {
        return R.layout.activity_person_setting_acitivty;
    }


    /**
     * 第三方拍照和裁剪
     */
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);//选中数量限制
        imagePicker.setCrop(true);     //允许裁剪（单选才有效）
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }

    /*
     * 相机拍照得到的图片设置到ImageView上面去
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                headImages = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                HttpParams params = new HttpParams();
                params.put("header", new File(headImages.get(0).path));
                upLoadHeaderImage(1, params);
                //                mImageLoader.loadImage(headImages.get(0).path, R.mipmap.icon_head_portrait, R.mipmap.icon_head_portrait, mUserHead);
            }
        }
    }

    private void upLoadHeaderImage(final int type, HttpParams params) {
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.editUserInfo, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                mSubscription.unsubscribe();
                cancleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                cancleLoadingDialog();
            }

            @Override
            public void onNext(String s) {

                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                if (type == 1) {
                    Constans.head = headImages.get(0).path;
                    Glide.with(PersonSettingAcitivty.this).load(Constans.head).asBitmap().into(mUserHead);
                } else if (type == 2) {
                    Constans.name = mName;
                    mNickName.setText(Constans.name);
                    mPopWindow.dismiss();

                }
            }
        });
    }


    @Override
    protected void initData() {
        mImageLoader = new ImageLoader(this);
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("type", 2);
        params.put("cityId", Constans.city_id);
        mSubscription = mHttpUtils.getData(UrlFactory.getUserInfo, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                if (mPopWindow != null && mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                }
                cancleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                JSONObject object = JSON.parseObject(s);
                mCustomNumber.setText(object.getJSONObject("data").getJSONObject("userInfo").getString("agentPhone"));
                Glide.with(PersonSettingAcitivty.this).load(UrlFactory.baseImageUrl + object.getJSONObject("data").getJSONObject("userInfo").getString("head")).asBitmap()
                        .error(R.mipmap.icon_head_portrait).error(R.mipmap.icon_head_portrait).into(mUserHead);

                mPhone.setText(object.getJSONObject("data").getJSONObject("userInfo").getString("phone"));
                if (object.getJSONObject("data").getJSONObject("userInfo").getString("is_remind").equals("0")) {
                    mSwitchButton.setChecked(false);
                } else {
                    mSwitchButton.setChecked(true);
                }
            }
        });
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
        initToobar("设置");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);

        mNickName.setText(Constans.name);

        try {
            mCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            ShowToast("没有权限获取应用数据");
        }
    }


    @OnClick({R.id.user_head_relative, R.id.userPhone, R.id.customNumber, R.id.aboutUs, R.id.clearCache, R.id.loginOut, R.id.userNickName, R.id.switchButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head_relative:
                initImagePicker();
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.userPhone:
                Bundle bundle = new Bundle();
                bundle.putString("phone", mPhone.getText().toString().trim());
                jumpToActivity(ChangeUserNumberActivity.class, bundle, false);
                break;
            case R.id.customNumber:
                showPopWindos();
                break;
            case R.id.aboutUs:
                jumpToActivity(AboutUsActivity.class, false);
                break;
            case R.id.clearCache:
                new AlertDialog.Builder(PersonSettingAcitivty.this).setTitle("温馨提示")//设置对话框标题
                        .setMessage("清除缓存可能导致自动登录失效，如失效，请重新登录")//设置显示的内容
                        .setCancelable(true)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                clearDataCache();
                            }

                        }).show();
                break;
            case R.id.loginOut:
                SPUtils.saveUserInfo("", "");
                Constans.clearData();
                SPUtils.saveQQ("");
                SPUtils.saveWeixin("");
                MainActivity.currentIndex = 2;
                MainActivity.isSuccess = false;
                RongIMClient.getInstance().logout();
                jumpToActivity(MainActivity.class, true);
                break;
            case R.id.userNickName:
                showChangeNickNamePop();
                break;
            case R.id.switchButton:
                HttpParams params = new HttpParams();
                params.put("uid", Constans.uid);
                if (mSwitchButton.isChecked()) {
                    params.put("is_remind", 1);
                } else {
                    params.put("is_remind", 0);
                }
                changeSwitchButton(params);
                break;
        }
    }

    private void changeSwitchButton(HttpParams params) {
        mSubscription = mHttpUtils.getData(UrlFactory.editUserInfo, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());

            }

            @Override
            public void onNext(String s) {
                cancleLoadingDialog();
                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
            }
        });
    }

    private void clearDataCache() {
        mSubscription = Observable.create(new Observable.OnSubscribe<List<Integer>>() {
            @Override
            public void call(Subscriber<? super List<Integer>> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    List<Integer> nullLits = new ArrayList<Integer>();
                    DataCleanManager.clearAllCache(PersonSettingAcitivty.this);
                    subscriber.onNext(nullLits);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Integer>>() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Integer> cities) {
                        try {
                            mCacheSize.setText(DataCleanManager.getTotalCacheSize(PersonSettingAcitivty.this));
                        } catch (Exception e) {
                            ShowToast("清除缓存失败");
                        }
                    }
                });
    }

    private void showPopWindos() {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.pop_call_custom, null);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);

        TextView cancle = (TextView) popView.findViewById(R.id.cancle);
        TextView confirm = (TextView) popView.findViewById(R.id.confirm);
        final TextView tv_number = (TextView) popView.findViewById(R.id.phoneNumber);
        tv_number.setText("拨打" + mCustomNumber.getText().toString().trim());

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mCustomNumber.getText().toString().trim()));
                startActivity(intent);
                mPopWindow.dismiss();
            }
        });

        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    public void showChangeNickNamePop() {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.pop_nick_name, null);
        final EditText et_nickName = (EditText) popView.findViewById(R.id.editTextNickName);
        TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
        TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
        et_nickName.setText(mNickName.getText().toString().trim());
        et_nickName.setSelection(mNickName.getText().length());// 光标移动到最后
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = et_nickName.getText().toString().trim();
                if (mName.isEmpty()) {
                    ShowToast("昵称不能为空");
                } else if (mName.length() >= 6) {
                    ShowToast("昵称不能超过6个字");
                } else {
                    HttpParams params = new HttpParams();
                    params.put("name", mName);
                    upLoadHeaderImage(2, params);
                }
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });


        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);

        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }


    @Override
    public void onBackPressed() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        } else {
            finish();
        }
    }
}
