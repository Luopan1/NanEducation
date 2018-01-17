package www.test720.com.naneducation.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.SPUtils;

import static www.test720.com.naneducation.R.id.show;

public class ChangePassworldActivity extends BaseToolbarActivity {


    @BindView(R.id.seePassworld)
    CheckBox mSeePassworld;
    @BindView(R.id.seePassworld1)
    CheckBox mSeePassworld1;
    @BindView(R.id.comfirm)
    Button mComfirm;
    @BindView(R.id.pwdEditText)
    EditText mPwdEditText;
    @BindView(R.id.pwdEditText1)
    EditText mPwdEditText1;
    private String content;
    private String mContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_passworld;
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
        initToobar("设置密码");
        setTitleColor(R.color.white);
        setTopLeftButton(R.drawable.fanhuibai);
        setToolbarColor(R.color.base_color);
    }


    @OnClick({R.id.seePassworld, R.id.seePassworld1, R.id.comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.seePassworld1:
                if (mSeePassworld1.isChecked()) {
                    mPwdEditText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mContent = mPwdEditText1.getText().toString().trim();
                    if (!mContent.isEmpty()) {
                        mPwdEditText1.setText(mContent);
                        mPwdEditText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mPwdEditText1.setSelection(mContent.length());
                    }
                } else {
                    mContent = mPwdEditText1.getText().toString().trim();
                    mPwdEditText1.setText(mContent);
                    mPwdEditText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPwdEditText1.setSelection(mContent.length());
                }
                break;
            case R.id.comfirm:
                if (mPwdEditText.getText().toString().trim().length() < 6 || mPwdEditText1.getText().toString().trim().length() > 18) {
                    ShowToast("密码为6-18位");
                } else if (!mPwdEditText.getText().toString().trim().equals(mPwdEditText1.getText().toString().trim())) {
                    ShowToast("两次输入的密码不一致");
                } else {
                    setPassWorld();
                }
                break;
        }
    }

    private void setPassWorld() {
        HttpParams params = new HttpParams();
        params.put("phone", getIntent().getStringExtra("phone"));
        params.put("password", mPwdEditText.getText().toString().trim());
        params.put("type", getIntent().getStringExtra("type"));
        params.put("unionid", getIntent().getStringExtra("id"));
        mSubscription = mHttpUtils.getData(UrlFactory.weChatBindPhone, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                JSONObject obg = JSON.parseObject(s);
                ShowToast(obg.getString("msg"));
                if (obg.getInteger("code") == 2) {
                    Constans.uid = obg.getJSONObject("data").getString("uid");
                    SPUtils.saveUserInfo(getIntent().getStringExtra("phone"), mPwdEditText.getText().toString().trim());
                    finish();
                }
            }
        });
    }
}
