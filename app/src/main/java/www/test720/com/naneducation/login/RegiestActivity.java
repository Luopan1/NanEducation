package www.test720.com.naneducation.login;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.AgreeMentActivity;
import www.test720.com.naneducation.activity.MainActivity;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.ImageLoader;
import www.test720.com.naneducation.utils.SPUtils;

public class RegiestActivity extends BaseToolbarActivity {


    @BindView(R.id.PhoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.ImageCheckCodeEditText)
    EditText mImageCheckCodeEditText;
    @BindView(R.id.ImageCheckCode)
    ImageView mImageCheckCode;
    @BindView(R.id.CheckCode)
    EditText mCheckCode;
    @BindView(R.id.sendCheckCode)
    Button mSendCheckCode;
    @BindView(R.id.pwdEditText)
    EditText mPwdEditText;
    @BindView(R.id.seePassworld)
    CheckBox mSeePassworld;
    @BindView(R.id.pwdAgain)
    EditText mPwdAgain;
    @BindView(R.id.seePassworldAga)
    CheckBox mSeePassworldAga;
    @BindView(R.id.readAgreement)
    CheckBox mReadAgreement;
    @BindView(R.id.userAgreement)
    TextView mUserAgreement;
    @BindView(R.id.checkRelative)
    RelativeLayout checkRelative;
    @BindView(R.id.complete)
    Button complete;
    private ImageLoader mImageLoader;

    private String content;
    private String mContent;
    final Handler handler = new Handler();
    int i = 60;

    int type = 1;
    private String mGetCheckCodeUrl;
    private String mCommitUrl;

    @Override
    protected int getContentView() {
        return R.layout.activity_regiest;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        mSubscription = mHttpUtils.getData(UrlFactory.getGrapcode, params, 0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.e("onCompleted");

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
                        JSONObject jsonObject = JSON.parseObject(s);
                        String imageUrl = jsonObject.getJSONObject("data").getString("codeUrl");
                        mImageLoader.loadImage(UrlFactory.baseImageUrl + imageUrl, R.mipmap.default_image, R.mipmap.default_image, mImageCheckCode);
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
        mImageLoader = new ImageLoader(this);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if (key.equals("Regiest")) {
            checkRelative.setVisibility(View.VISIBLE);
            initToobar("注册");
            complete.setText("注册");
            type = 1;
        } else if (key.equals("forget")) {
            checkRelative.setVisibility(View.GONE);
            initToobar("忘记密码");
            complete.setText("确认修改");
            type = 2;

        }

    }


    @OnClick({R.id.sendCheckCode, R.id.complete, R.id.ImageCheckCode, R.id.seePassworld, R.id.seePassworldAga, R.id.userAgreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userAgreement:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("title", "用户协议");
                jumpToActivity(AgreeMentActivity.class, bundle, false);
                break;
            case R.id.sendCheckCode:
                if (mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号");
                    return;
                } else if (mImageCheckCodeEditText.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入图形验证码");
                }
                if (type == 1) {
                    //  type==1 注册时的验证码
                    mGetCheckCodeUrl = UrlFactory.getRegisterCode;
                } else {
                    //  type==2 忘记密码时的验证码
                    mGetCheckCodeUrl = UrlFactory.forgetGetCode;
                }
                HttpParams params = new HttpParams();
                params.put("phone", mPhoneNumber.getText().toString().trim());
                params.put("emsCode", mImageCheckCodeEditText.getText().toString().trim());

                mSubscription = mHttpUtils.getData(mGetCheckCodeUrl, params, 2).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

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
                                cancleLoadingDialog();
                                JSONObject jsonObject = JSON.parseObject(s);
                                ShowToast(jsonObject.getString("msg"));
                                mSubscription.unsubscribe();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        i--;
                                        mSendCheckCode.setText(i + "S");
                                        mSendCheckCode.setTextColor(getResources().getColor(R.color.black));
                                        mSendCheckCode.setEnabled(false);
                                        if (i > 0) {
                                            handler.postDelayed(this, 1000);
                                        } else {
                                            i = 60;
                                            mSendCheckCode.setText("发送验证码");
                                            mSendCheckCode.setTextColor(getResources().getColor(R.color.black));
                                            mSendCheckCode.setEnabled(true);
                                        }

                                    }
                                }, 200);

                            }
                        });

                break;
            case R.id.complete:
                if (type == 1) {
                    if (!mReadAgreement.isChecked()) {
                        ShowToast("请阅读并勾选用户协议");
                        return;
                    }
                }
                if (mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号");
                } else if (mImageCheckCodeEditText.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入图形验证码");
                } else if (mCheckCode.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入短信验证码");
                } else if (mPwdEditText.getText().toString().trim().isEmpty() || mPwdAgain.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入密码");
                } else if (!mPwdEditText.getText().toString().trim().equals(mPwdAgain.getText().toString().trim())) {
                    ShowToast("两次输入的密码不一致");
                } else if (isCorrentPasswrold(mPwdEditText)) {
                    ShowToast("密码长度为6~18位");
                } else {
                    params = new HttpParams();
                    if (type == 1) {
                        mCommitUrl = UrlFactory.register;
                        params.put("phone", mPhoneNumber.getText().toString().trim());
                        params.put("code", mCheckCode.getText().toString().trim());
                        params.put("password", mPwdEditText.getText().toString().trim());
                    } else if (type == 2) {
                        mCommitUrl = UrlFactory.forgetEditPwd;
                        params.put("phone", mPhoneNumber.getText().toString().trim());
                        params.put("code", mCheckCode.getText().toString().trim());
                        params.put("newPwd", mPwdEditText.getText().toString().trim());
                    }

                    mSubscription = mHttpUtils.getData(mCommitUrl, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

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
                            cancleLoadingDialog();
                            ShowToast(e.getMessage());
                        }

                        @Override
                        public void onNext(String s) {
                            cancleLoadingDialog();
                            JSONObject jsonObject = JSON.parseObject(s);
                            ShowToast(jsonObject.getString("msg"));
                            mSubscription.unsubscribe();
                            jumpToActivity(MainActivity.class, true);
                            SPUtils.saveUserInfo(mPhoneNumber.getText().toString().trim(), mPwdEditText.getText().toString().trim());
                        }
                    });
                }


                break;
            case R.id.ImageCheckCode:
                initData();
                break;
            case R.id.seePassworld:
                if (mSeePassworld.isChecked()) {
                    mPwdEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    content = mPwdEditText.getText().toString().trim();
                    if (!content.isEmpty()) {
                        mPwdEditText.setText(content);
                        mPwdEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mPwdEditText.setSelection(content.length());
                    }
                } else {
                    content = mPwdEditText.getText().toString().trim();
                    mPwdEditText.setText(content);
                    mPwdEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPwdEditText.setSelection(content.length());
                }

                break;
            case R.id.seePassworldAga:

                if (mSeePassworldAga.isChecked()) {
                    mPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mContent = mPwdAgain.getText().toString().trim();
                    if (!mContent.isEmpty()) {
                        mPwdAgain.setText(mContent);
                        mPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mPwdAgain.setSelection(mContent.length());
                    }
                } else {
                    mContent = mPwdAgain.getText().toString().trim();
                    mPwdAgain.setText(mContent);
                    mPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPwdAgain.setSelection(mContent.length());
                }


                break;
        }
    }

}
