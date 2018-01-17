package www.test720.com.naneducation.personcenteractivity;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import www.test720.com.naneducation.alipay.CallBack;
import www.test720.com.naneducation.alipay.PayMain;
import www.test720.com.naneducation.alipay.PayResult;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class RechargeActivity extends BaseToolbarActivity {


    @BindView(R.id.RechargeMoney)
    EditText mRechargeMoney;
    @BindView(R.id.zhifubao)
    RadioButton mZhifubao;
    @BindView(R.id.weixin)
    RadioButton mWeixin;
    @BindView(R.id.charge)
    Button mCharge;
    @BindView(R.id.money50)
    TextView mMoney50;
    @BindView(R.id.money100)
    TextView mMoney100;
    @BindView(R.id.money150)
    TextView mMoney150;
    @BindView(R.id.money200)
    TextView mMoney200;
    @BindView(R.id.money250)
    TextView mMoney250;
    @BindView(R.id.money300)
    TextView mMoney300;
    @BindView(R.id.money350)
    TextView mMoney350;
    @BindView(R.id.money400)
    TextView mMoney400;
    @BindView(R.id.moneyOthers)
    TextView mMoneyOthers;
    @BindView(R.id.otherMoneyLinear)
    LinearLayout mOtherMoneyLinear;
    private int payType = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mRechargeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("充值");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
    }


    @OnClick({R.id.money50, R.id.money100, R.id.money150, R.id.money200, R.id.money250, R.id.money300, R.id.money350, R.id.money400, R.id.moneyOthers, R.id.zhifubao, R.id.weixin, R.id.charge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.money50:
                mRechargeMoney.setText(50 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money100:
                mRechargeMoney.setText(100 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money150:
                mRechargeMoney.setText(200 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money200:
                mRechargeMoney.setText(500 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money250:
                mRechargeMoney.setText(1000 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money300:
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money350:
                mRechargeMoney.setText(300 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.money400:
                mRechargeMoney.setText(300 + "");
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                break;
            case R.id.moneyOthers:
                mMoney50.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney100.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney150.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney200.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney250.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney300.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney350.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoney400.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
                mMoneyOthers.setBackground(getResources().getDrawable(R.drawable.nim_red_round_button));
                break;
            case R.id.zhifubao:
                payType = 1;
                break;
            case R.id.weixin:
                payType = 2;
                break;
            case R.id.charge:
                try {
                    if (mRechargeMoney.getText().toString().trim().isEmpty()) {
                        ShowToast("请选择充值金额");
                        return;
                    } else if (Double.parseDouble(mRechargeMoney.getText().toString().trim()) <= 0) {
                        ShowToast("请输入正确的金额");
                        return;
                    }
                } catch (NumberFormatException e) {
                    ShowToast("请输入正确的金额");
                    return;
                }
                getOrderNumber(payType);
                break;
        }
    }

    private void getOrderNumber(final int i) {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("type", i);
        params.put("price", mRechargeMoney.getText().toString().trim());
        mSubscription = mHttpUtils.getData(UrlFactory.userRegOrder, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                if (i == 1) {
                    if (obj.getInteger("code") == 1) {
                        goPay(obj.getJSONObject("data").getString("o_number"));
                    } else {
                        ShowToast(obj.getString("msg"));
                    }
                } else if (i == 2) {
                    /**微信支付*/
                    String prepay_id = obj.getJSONObject("data").getString("package");
                    int start = prepay_id.lastIndexOf("=");
                    int end = prepay_id.length();
                    String prepay = prepay_id.substring(start + 1, end);
                    Log.e("TAG+++", prepay);
                    String nonceStr = obj.getJSONObject("data").getString("nonceStr");
                    Constans.genPayReq(RechargeActivity.this, prepay, nonceStr);
                }
            }
        });
    }

    private void goPay(String orderNumber) {
        PayMain payMain = new PayMain(this, callBack, UrlFactory.Syntony);
        payMain.Pay(mRechargeMoney.getText().toString().trim(), orderNumber);
    }

    CallBack callBack = new CallBack(mActivity) {

        @Override
        public void call(int what, Object obj) {
            // TODO Auto-generated method stub
        }

        @Override
        public void call(PayResult payResult) {
            // TODO Auto-generated method stub

           /* *
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */

            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                ShowToast("支付成功");

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                ShowToast("付款失败");
                Log.e("order", resultInfo + "+");
            }

        }
    };

}
