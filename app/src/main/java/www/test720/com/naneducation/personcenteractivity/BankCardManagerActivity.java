package www.test720.com.naneducation.personcenteractivity;

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

public class BankCardManagerActivity extends BaseToolbarActivity {


    @BindView(R.id.bankCardNumber)
    EditText mBankCardNumber;
    @BindView(R.id.confirmBankCardNumber)
    EditText mConfirmBankCardNumber;
    @BindView(R.id.holdCardPersonName)
    EditText mHoldCardPersonName;
    @BindView(R.id.openTheCardBank)
    EditText mOpenTheCardBank;
    @BindView(R.id.bindNewCard)
    Button mBindNewCard;

    @Override
    protected int getContentView() {
        return R.layout.activity_bank_card_manager;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.userBankcard, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                JSONObject obj = JSON.parseObject(s);
                mBankCardNumber.setText(obj.getJSONObject("data").getString("bankcard"));
                mConfirmBankCardNumber.setText(obj.getJSONObject("data").getString("bankcard"));
                mOpenTheCardBank.setText(obj.getJSONObject("data").getString("bank_name"));
                mHoldCardPersonName.setText(obj.getJSONObject("data").getString("name"));
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
        initToobar("绑定银行卡");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
    }


    @OnClick(R.id.bindNewCard)
    public void onClick() {
        if (mBankCardNumber.getText().toString().trim().isEmpty() ||
                mHoldCardPersonName.getText().toString().trim().isEmpty() ||
                mOpenTheCardBank.getText().toString().trim().isEmpty()) {
            ShowToast("信息必须填写完整");
        } else if (!mBankCardNumber.getText().toString().trim().equals(mConfirmBankCardNumber.getText().toString().trim())) {
            ShowToast("输入的银行卡卡号不一致");
        } else {
            HttpParams params = new HttpParams();
            params.put("uid", Constans.uid);
            params.put("bankcard", mBankCardNumber.getText().toString().trim());
            params.put("bank_name", mOpenTheCardBank.getText().toString().trim());
            params.put("name", mHoldCardPersonName.getText().toString().trim());
            mSubscription = mHttpUtils.getData(UrlFactory.bindUserCard, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                    JSONObject obj = JSON.parseObject(s);
                    ShowToast(obj.getString("msg"));
                    if (obj.getInteger("code") == 1) {
                        Constans.isBindbank = true;
                        finish();
                    } else {
                        Constans.isBindbank = false;
                    }
                }
            });
        }
    }
}
