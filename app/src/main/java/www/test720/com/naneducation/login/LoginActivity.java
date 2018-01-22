package www.test720.com.naneducation.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.BindPhoneActivity;
import www.test720.com.naneducation.activity.MainActivity;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.SPUtils;

import static www.test720.com.naneducation.http.Constans.unionid;


public class LoginActivity extends BaseToolbarActivity {


    @BindView(R.id.PhoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.regiest)
    TextView mRegiest;
    @BindView(R.id.forgetPwd)
    TextView mForgetPwd;
    @BindView(R.id.Login)
    Button mLogin;
    @BindView(R.id.QQLogin)
    RelativeLayout mQQLogin;
    @BindView(R.id.weixinLogin)
    RelativeLayout mWeixinLogin;
    private int mType;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
    }

    @Override
    protected void initView() {
        initToobar("登录");
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
    }

    @OnClick({R.id.Login, R.id.QQLogin, R.id.weixinLogin, R.id.regiest, R.id.forgetPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login:
                if (mPhoneNumber.getText().toString().trim().isEmpty() || mPassword.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入账户和密码");
                    return;
                }
                HttpParams params = new HttpParams();
                params.put("phone", mPhoneNumber.getText().toString().trim());
                params.put("password", mPassword.getText().toString().trim());
                mSubscription = mHttpUtils.getData(UrlFactory.Login, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        cancleLoadingDialog();
                        if (mType == 1) {
                            finish();
                        } else {
                            jumpToActivity(MainActivity.class, true);
                        }
                        mSubscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancleLoadingDialog();
                        ShowToast(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        JSONObject jsonObject = JSON.parseObject(s);
                        ShowToast(jsonObject.getString("msg"));
                        Constans.head = jsonObject.getJSONObject("data").getString("head");
                        if (jsonObject.getJSONObject("data").getInteger("is_bindbank") == 0) {
                            Constans.isBindbank = false;
                        } else {
                            Constans.isBindbank = true;
                        }

                        if (jsonObject.getJSONObject("data").getInteger("is_pass") == 0) {
                            Constans.isPass = false;
                        } else {
                            Constans.isPass = true;
                        }
                        Constans.name = jsonObject.getJSONObject("data").getString("name");
                        Constans.uid = jsonObject.getJSONObject("data").getString("uid");

                        SPUtils.saveUserInfo(mPhoneNumber.getText().toString().trim(), mPassword.getText().toString().trim());
                    }
                });


                break;
            case R.id.QQLogin:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.weixinLogin:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.regiest:
                Bundle bundle = new Bundle();
                bundle.putString("key", "Regiest");
                jumpToActivity(RegiestActivity.class, bundle, false);
                break;
            case R.id.forgetPwd:
                bundle = new Bundle();
                bundle.putString("key", "forget");
                jumpToActivity(RegiestActivity.class, bundle, false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            showLoadingDialog();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String openid = data.get("openid");
            Constans.unionid = openid;
            WeiChatLogin(openid);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.e("TAG+++++", t.getMessage());
            cancleLoadingDialog();
            ShowToast(t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            cancleLoadingDialog();
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private void WeiChatLogin(final String openid) {
        HttpParams params = new HttpParams();
        params.put("unionid", openid);
        mSubscription = mHttpUtils.getData(UrlFactory.appWeChatLogin, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                JSONObject obj = JSON.parseObject(s);
                if (obj.getInteger("code") == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    jumpToActivity(BindPhoneActivity.class, bundle, true);

                } else if (obj.getInteger("code") == 1) {
                    SPUtils.saveWeixin(openid);
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

                    jumpToActivity(MainActivity.class, true);
                }
            }
        });
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调，可以用来处理等待框，或相关的文字提示
            showLoadingDialog();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtils.e(data.toString());
            String unionid = data.get("uid");
            Constans.unionid = unionid;
            QQLogin(unionid);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtils.e(t.getMessage());
            cancleLoadingDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            cancleLoadingDialog();
        }
    };

    public void QQLogin(final String unionid) {
        HttpParams params = new HttpParams();
        params.put("unionid", unionid);
        mHttpUtils.getData(UrlFactory.appQqLogin, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
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
                JSONObject obj = JSON.parseObject(s);
                if (obj.getInteger("code") == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    jumpToActivity(BindPhoneActivity.class, bundle, true);

                } else if (obj.getInteger("code") == 1) {
                    SPUtils.saveQQ(unionid);

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

                    jumpToActivity(MainActivity.class, true);
                }

            }
        });
    }


}
