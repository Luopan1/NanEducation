package www.test720.com.naneducation.personcenteractivity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import www.test720.com.naneducation.utils.AmountEditText;

public class WithDrawalsActiviy extends BaseToolbarActivity {

    private final int DECIMAL_DIGITS = 2;//小数的位数
    @BindView(R.id.canWithDrwasMoney)
    TextView mCanWithDrwasMoney;
    @BindView(R.id.WithdrawalMoney)
    AmountEditText mWithdrawalMoney;
    @BindView(R.id.leastCanWithDrawal)
    TextView mLeastCanWithDrawal;
    @BindView(R.id.confirmWithDrawal)
    Button mConfirmWithDrawal;
    @BindView(R.id.ServiceCharge)
    TextView mServiceCharge;
    private JSONObject mJsonObject;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_drawals_activiy;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.userDeposit, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                mJsonObject = JSON.parseObject(s);
                mCanWithDrwasMoney.setText("可提现金额：" + mJsonObject.getJSONObject("data").getString("price"));
                mLeastCanWithDrawal.setText("最少提现金额" + mJsonObject.getJSONObject("data").getString("downprice") + "元");
                mServiceCharge.setText("请输入提现金额(提现收取" + mJsonObject.getJSONObject("data").getString("procedures") + "%的手续费)");
            }
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar("提现");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }


    @OnClick(R.id.confirmWithDrawal)
    public void onClick() {
        try {
            if (mWithdrawalMoney.getText().toString().trim().isEmpty()) {
                ShowToast("请输入提现金额");
                return;
            }
            Double totlaMoney = Double.parseDouble(mJsonObject.getJSONObject("data").getString("price"));
            Double withDrwalMoney = Double.parseDouble(mWithdrawalMoney.getText().toString().trim());
            Double atLeaseWhitdrwalMoney = Double.parseDouble(mJsonObject.getJSONObject("data").getString("downprice"));
            if (withDrwalMoney > totlaMoney) {
                ShowToast("你输入的金额大于可提现金额");
            } else if (withDrwalMoney < atLeaseWhitdrwalMoney) {
                ShowToast("你输入的金额小于最少提现金额");
            } else {
                HttpParams params = new HttpParams();
                params.put("uid", Constans.uid);
                params.put("money", withDrwalMoney);
                mSubscription = mHttpUtils.getData(UrlFactory.userMoneyCash, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                        ShowToast(obj.getString("msg"));
                        if (obj.getInteger("code") == 1) {
                            finish();

                        }
                    }
                });
            }
        } catch (NumberFormatException e) {
            ShowToast("出错了 请稍候再试");
        }
    }

}
