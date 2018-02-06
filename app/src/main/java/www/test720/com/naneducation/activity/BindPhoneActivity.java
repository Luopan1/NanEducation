package www.test720.com.naneducation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;


public class BindPhoneActivity extends BaseToolbarActivity {

    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.ImageCheckCode)
    EditText mImageCheckCode;
    @BindView(R.id.imageCode)
    ImageView mImageCode;
    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.checkCode)
    EditText mCheckCode;
    @BindView(R.id.sendCheckCode)
    Button mSendCheckCode;
    @BindView(R.id.comfirmBind)
    Button mComfirmBind;

    final Handler handler = new Handler();
    int i = 60;
    int mType = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initData() {
        mType = getIntent().getIntExtra("type", 1);
        mHttpUtils.getData(UrlFactory.getGrapcode, new HttpParams(), 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                JSONObject jsonObject = JSON.parseObject(s);
                String imageUrl = jsonObject.getJSONObject("data").getString("codeUrl");
                Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + imageUrl).asBitmap().into(mImageCode);
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
        initToobar("绑定手机号");
        setTitleColor(R.color.white);
        setTopLeftButton(R.drawable.fanhuibai);
        setToolbarColor(R.color.base_color);
    }

    @OnClick({R.id.imageCode, R.id.sendCheckCode, R.id.comfirmBind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageCode:
                initData();
                break;
            case R.id.sendCheckCode:
                if (mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号");
                } else if (mImageCheckCode.getText().toString().trim().length() == 0) {
                    ShowToast("请输入图形验证码");
                } else {
                    sendCheckCode();
                }
                break;
            case R.id.comfirmBind:
                if (mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号");
                } else if (mImageCheckCode.getText().toString().trim().length() == 0) {
                    ShowToast("请输入图形验证码");
                } else if (mCheckCode.getText().toString().trim().length() == 0) {
                    ShowToast("请输入短信验证码");
                } else {
                    bindPhone();
                }
                break;
        }
    }

    private void bindPhone() {
        HttpParams params = new HttpParams();
        params.put("phone", mPhoneNumber.getText().toString().trim());
        params.put("code", mCheckCode.getText().toString().trim());
        params.put("type", mType);
        params.put("emsCode", mImageCheckCode.getText().toString().trim());
        params.put("unionid", Constans.unionid);
        mHttpUtils.getData(UrlFactory.weChatBindPhone, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                if (obj.getInteger("code") == 2) {
                    // TODO: 2018/1/8 跳转首页
                    Constans.head = obj.getJSONObject("data").getString("head");
                    if (obj.getJSONObject("data").getInteger("is_bindbank") == 0) {
                        Constans.isBindbank = false;
                    } else {
                        Constans.isBindbank = true;
                    }

                    if (obj.getJSONObject("data").getInteger("is_pass") == 0) {
                        Constans.isPass = false;
                    } else {
                        Constans.isPass = true;
                    }
                    Constans.name = obj.getJSONObject("data").getString("name");
                    Constans.uid = obj.getJSONObject("data").getString("uid");
                    Constans.token = obj.getJSONObject("data").getString("rong_cloud_token");
                    RongIMClient.connect(Constans.token, new RongIMClient.ConnectCallback() {

                        @Override
                        public void onTokenIncorrect() {

                        }

                        @Override
                        public void onSuccess(String s) {
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                        }
                    });
                    jumpToActivity(MainActivity.class, true);

                } else if (obj.getInteger("code") == 1) {
                    // TODO: 2018/1/8  去设置密码
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", mPhoneNumber.getText().toString().trim());
                    bundle.putString("type", String.valueOf(mType));
                    bundle.putString("id", Constans.unionid);
                    jumpToActivity(ChangePassworldActivity.class, bundle, true);
                }
            }
        });
    }

    private void sendCheckCode() {
        HttpParams params = new HttpParams();
        params.put("phone", mPhoneNumber.getText().toString().trim());
        params.put("emsCode", mImageCheckCode.getText().toString().trim());
        mSubscription = mHttpUtils.getData(UrlFactory.bindGetCode, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
    }
}
