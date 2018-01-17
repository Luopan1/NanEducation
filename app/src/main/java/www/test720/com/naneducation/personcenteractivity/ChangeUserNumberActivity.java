package www.test720.com.naneducation.personcenteractivity;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.login.LoginActivity;

public class ChangeUserNumberActivity extends BaseToolbarActivity {


    @BindView(R.id.oldPhoneNuber)
    EditText mOldPhoneNuber;
    @BindView(R.id.newPhoneNumber)
    EditText mNewPhoneNumber;
    @BindView(R.id.getCheckCode)
    TextView mGetCheckCode;
    @BindView(R.id.phoneCheckCode)
    EditText mPhoneCheckCode;
    @BindView(R.id.comfirmChange)
    Button mComfirmChange;
    final Handler handler = new Handler();
    int i = 60;
    private String mPhone;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_user_number;
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
        initToobar("修改手机号");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
        Intent intent = getIntent();
        mPhone = intent.getStringExtra("phone");
        mOldPhoneNuber.setText(mPhone);
    }


    @OnClick({R.id.getCheckCode, R.id.comfirmChange})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.getCheckCode:
                if (mOldPhoneNuber.getText().toString().trim().isEmpty() || mNewPhoneNumber.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入旧手机号和新手机号");
                } else {
                    HttpParams params = new HttpParams();
                    params.put("phone", mOldPhoneNuber.getText().toString().trim());
                    params.put("xphone", mNewPhoneNumber.getText().toString().trim());
                    params.put("uid", Constans.uid);
                    mSubscription = mHttpUtils.getData(UrlFactory.uphoneGetCode, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    i--;
                                    mGetCheckCode.setTextColor(getResources().getColor(R.color.white));
                                    mGetCheckCode.setText(i + "S");
                                    mGetCheckCode.setEnabled(false);
                                    if (i > 0) {
                                        handler.postDelayed(this, 1000);
                                    } else {
                                        i = 60;
                                        mGetCheckCode.setTextColor(getResources().getColor(R.color.white));
                                        mGetCheckCode.setText("发送验证码");
                                        mGetCheckCode.setEnabled(true);
                                    }
                                }
                            }, 200);
                        }
                    });
                }
                break;
            case R.id.comfirmChange:
                if (mOldPhoneNuber.getText().toString().trim().isEmpty() || mNewPhoneNumber.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入旧手机号和新手机号");
                } else if (mOldPhoneNuber.getText().toString().trim().equals(mNewPhoneNumber.getText().toString().trim())) {
                    ShowToast("新手机号与旧手机号一样");
                } else {
                    HttpParams params = new HttpParams();
                    params.put("phone", mNewPhoneNumber.getText().toString().trim());
                    params.put("code", mPhoneCheckCode.getText().toString().trim());
                    params.put("uid", Constans.uid);
                    mSubscription = mHttpUtils.getData(UrlFactory.uphoneCodeYz, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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

                            Constans.head = "";
                            Constans.city_id = "";
                            Constans.City = "";
                            Constans.district = "";
                            Constans.isPass = false;
                            Constans.isPass = false;
                            Constans.name = "";
                            Constans.uid = "";
                            jumpToActivity(LoginActivity.class, true);
                        }
                    });
                }

                break;
        }
    }
}
