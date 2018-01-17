package www.test720.com.naneducation.personcenteractivity;


import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
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

public class UserWalletActivity extends BaseToolbarActivity {


    @BindView(R.id.fanhuiRelative)
    RelativeLayout mFanhuiRelative;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.walletInfo)
    RelativeLayout mWalletInfo;
    @BindView(R.id.canUseMoney)
    TextView mCanUseMoney;
    @BindView(R.id.FrozenMoney)
    TextView mFrozenMoney;
    @BindView(R.id.Recharge)
    RelativeLayout mRecharge;
    @BindView(R.id.Withdrawals)
    RelativeLayout mWithdrawals;
    @BindView(R.id.bankCardManager)
    RelativeLayout mBankCardManager;
    @BindView(R.id.Payment_password_set)
    RelativeLayout mPaymentPasswordSet;
    @BindView(R.id.totalMoney)
    TextView mTotalMoney;
    @BindView(R.id.bankCardNumber)
    TextView mBankCardNumber;

    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.integral)
    TextView mIntegral;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_wallet;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.userWallet, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                mRefreshLayout.finishRefreshing();
                mSubscription.unsubscribe();

            }

            @Override
            public void onError(Throwable e) {
                mRefreshLayout.finishRefreshing();
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                JSONObject obj = JSON.parseObject(s);
                mTotalMoney.setText(obj.getJSONObject("data").getString("price"));
                mCanUseMoney.setText("可用金额：" + obj.getJSONObject("data").getString("userprice") + "元");
                mFrozenMoney.setText("冻结金额：" + obj.getJSONObject("data").getString("dieprice") + "元");
                mBankCardNumber.setText(obj.getJSONObject("data").getString("bankcard"));
                mIntegral.setText(obj.getJSONObject("data").getString("userIntegral"));
            }
        });
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onFinishRefresh() {

            }
        });

    }

    @Override
    protected void initView() {
        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setEnableLoadmore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.startRefresh();
            }
        }, 200);
    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }


    @OnClick({R.id.fanhuiRelative, R.id.walletInfo, R.id.Recharge, R.id.Withdrawals, R.id.bankCardManager, R.id.Payment_password_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhuiRelative:
                finish();
                break;
            case R.id.walletInfo:
                jumpToActivity(WalletInfoActivty.class, false);

                break;
            case R.id.Recharge:
                jumpToActivity(RechargeActivity.class, false);
                break;
            case R.id.Withdrawals:
                if (Constans.isBindbank) {
                    jumpToActivity(WithDrawalsActiviy.class, false);
                } else {
                    new AlertDialog.Builder(UserWalletActivity.this).setTitle("温馨提示")//设置对话框标题
                            .setMessage("当前没有绑定银行卡，请先绑定银行卡再提现")//设置显示的内容
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
                                    jumpToActivity(BankCardManagerActivity.class, false);
                                }

                            }).show();
                }

                break;
            case R.id.bankCardManager:
                jumpToActivity(BankCardManagerActivity.class, false);
                break;
            case R.id.Payment_password_set:
                /**如果设置了密码 就先去验证密码  没有设置就去设置密码页面*/
                if (Constans.isPass) {
                    jumpToActivity(ComfirmPayPassWorldActivity.class, false);

                } else {
                    jumpToActivity(SetUserPayMentPassworldActivtiy.class, false);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.startRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshLayout.finishRefreshing();
    }
}
