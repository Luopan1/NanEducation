package www.test720.com.naneducation.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

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
import www.test720.com.naneducation.personcenteractivity.MyBindInfoActivity;

import static www.test720.com.naneducation.http.UrlFactory.bindUserEdit;

public class AddNewSignUpPersonActivity extends BaseToolbarActivity {


    @BindView(R.id.editName)
    EditText mEditName;
    @BindView(R.id.editPhoneNumber)
    EditText mEditPhoneNumber;
    @BindView(R.id.addNewSignUpPeoPle)
    Button mAddNewSignUpPeoPle;
    private String mUrl;
    private String KID;
    String name = "";
    String phone = "";
    @Override
    protected int getContentView() {
        return R.layout.activity_add_new_sign_up_person;
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
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        if (name != null && !name.isEmpty()) {
            mEditName.setText(name);
            mUrl = UrlFactory.bindUserEdit;
            initToobar("修改报名人");
            mAddNewSignUpPeoPle.setText("确认修改");
        } else {
            mUrl = UrlFactory.bindUsername;
            initToobar("添加报名人");
            mAddNewSignUpPeoPle.setText("添加报名人");
        }


        if (phone != null && !phone.isEmpty()) {
            mEditPhoneNumber.setText(phone);
        }

        KID = intent.getStringExtra("id");


        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
    }


    @OnClick(R.id.addNewSignUpPeoPle)
    public void onClick() {
        if (mEditName.getText().toString().trim().isEmpty()) {
            ShowToast("姓名不能为空");
        } else if (mEditPhoneNumber.getText().toString().trim().length() != 11) {
            ShowToast("请输入正确的电话号码");
        } else {
            HttpParams params = new HttpParams();
            params.put("uid", Constans.uid);
            params.put("type", 2);
            params.put("name", mEditName.getText().toString().trim());
            params.put("kid", KID);
            params.put("username", mEditName.getText().toString().trim());
            params.put("phone", mEditPhoneNumber.getText().toString().trim());
            mSubscription = mHttpUtils.getData(mUrl, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                    MyBindInfoActivity.refresh = 2;
                    finish();
                }
            });
        }


    }
}
