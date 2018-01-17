package www.test720.com.naneducation.personcenteractivity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.ShowPopwindows;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.OrderInfo;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.model.PayMentCallBack;
import www.test720.com.naneducation.view.CircleImageView;

public class OrderInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.orderStatue)
    TextView mOrderStatue;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.orderLeftTime)
    TextView mOrderLeftTime;
    @BindView(R.id.schoolImage)
    CircleImageView mSchoolImage;
    @BindView(R.id.schoolCourseImage)
    ImageView mSchoolCourseImage;
    @BindView(R.id.orderCouseTitle)
    TextView mOrderCouseTitle;
    @BindView(R.id.orderCastMoney)
    TextView mOrderCastMoney;
    @BindView(R.id.orderSignRecyclerView)
    RecyclerView mOrderSignRecyclerView;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.orderContactNumber)
    TextView mOrderContactNumber;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.orderTotal)
    TextView mOrderTotal;
    @BindView(R.id.oderConfirm)
    TextView mOderConfirm;
    BaseRecyclerAdapter<OrderInfo.DataBean.DetailBean.UserlistBean> mSignUpAdapter;
    List<OrderInfo.DataBean.DetailBean.UserlistBean> mSignLists = new ArrayList<>();
    @BindView(R.id.oderMoney)
    TextView mOderMoney;
    String oNumber;
    @BindView(R.id.titleName)
    TextView mTitleName;
    @BindView(R.id.donateConpany)
    TextView mDonateConpany;
    @BindView(R.id.orderTime)
    TextView mOrderTime;
    private int mType;
    private OrderInfo mInfo;
    private Timer mTimer;
    private int mCatime;
    private PopupWindow mPopupWindow;
    public static boolean isBuySuccess = false;


    @Override
    protected int getContentView() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initData() {

        HttpParams params = new HttpParams();
        params.put("o_number", oNumber);
        mSubscription = mHttpUtils.getData(UrlFactory.userOrderDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                Gson gson = new Gson();
                mInfo = gson.fromJson(s, OrderInfo.class);
                if (mType == 3) {
                    mOrderLeftTime.setText(mInfo.getData().getDetail().getO_paytime());
                } else if (mType == 1) {
                    mOrderLeftTime.setText(formatTime(mInfo.getData().getDetail().getCatime()));
                } else if (mType == 2) {
                    mOrderLeftTime.setText(mInfo.getData().getDetail().getO_paytime());
                }
                mOderMoney.setText("需支付金额" + mInfo.getData().getDetail().getPrice());
                mTitleName.setText(mInfo.getData().getDetail().getTitle_name());
                Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mInfo.getData().getDetail().getTitle_logo()).asBitmap().into(mSchoolImage);
                Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mInfo.getData().getDetail().getLogo()).asBitmap().into(mSchoolCourseImage);
                mOrderCouseTitle.setText(mInfo.getData().getDetail().getName());
                mOrderCastMoney.setText(mInfo.getData().getDetail().getPrice());
                String sponsoe = "";
                for (int i = 0; i < mInfo.getData().getDetail().getSponsor().size(); i++) {
                    sponsoe += mInfo.getData().getDetail().getSponsor().get(i).trim() + " ";
                }
                mDonateConpany.setText(sponsoe);
                mOrderContactNumber.setText(mInfo.getData().getDetail().getPhone());
                mOrderTime.setText(mInfo.getData().getDetail().getO_time());

                mSignLists.addAll(mInfo.getData().getDetail().getUserlist());

                mOrderTotal.setText("合计：￥" + Double.parseDouble(mInfo.getData().getDetail().getPrice()) * mInfo.getData().getDetail().getUserlist().size());
                setAdapter();


                if (mType == 1) {
                    mCatime = mInfo.getData().getDetail().getCatime();
                    mTimer = new Timer("1");
                    if (mCatime <= 0) {
                        mOrderStatue.setText("订单已失效");
                        mOrderTotal.setVisibility(View.GONE);
                        mOderConfirm.setVisibility(View.GONE);
                    } else {
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                if (mCatime > 0) {

                                    mCatime -= 1;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mOrderLeftTime.setText(formatTime(mCatime));
                                        }
                                    });

                                } else {
                                    mTimer.cancel();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mOrderStatue.setText("订单已失效");
                                            mOrderTotal.setVisibility(View.GONE);
                                            mOderConfirm.setVisibility(View.GONE);
                                        }
                                    });

                                }
                            }
                        };
                        mTimer.schedule(task, 1000, 1000);
                    }
                } else if (mType == 3) {
                    mOrderStatue.setText("订单已支付");
                } else if (mType == 2) {
                    mOrderStatue.setText("订单已退款");
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();//回退时，网络未取消，取消当前网络请求
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public String formatTime(int time) {

        int h = time / 3600;
        int mine = (time - h * 3600) / 60;
        int second = (time - h * 3600 - mine * 60) % 60;

        String leftTIme = h + "时" + mine + "分" + second + "秒";

        return leftTIme;
    }

    private void setAdapter() {
        if (mSignUpAdapter == null) {
            mSignUpAdapter = new BaseRecyclerAdapter<OrderInfo.DataBean.DetailBean.UserlistBean>(this, mSignLists, R.layout.item_order_sign_up_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, OrderInfo.DataBean.DetailBean.UserlistBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.oderSignUpName, "昵称：" + item.getUsername());
                    holder.setText(R.id.orderSignUpID, "ID:" + item.getBinduser_id());
                }
            };
            mOrderSignRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mOrderSignRecyclerView.setAdapter(mSignUpAdapter);
        } else {
            mSignUpAdapter.notifyDataSetChanged();
        }
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
        initToobar("订单详情");
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 1);
        oNumber = intent.getStringExtra("orderNumber");


        if (mType == 1) {

        } else if (mType == 2) {
            mTextView.setText("退款时间");
            mOderMoney.setVisibility(View.GONE);
            mOrderStatue.setText("已退款");
            mOrderTotal.setVisibility(View.GONE);
            mOderConfirm.setVisibility(View.GONE);
        } else if (mType == 3) {
            mTextView.setText("支付时间：");
            mOderMoney.setVisibility(View.GONE);
            mOrderStatue.setText("已付款");
            mOrderTotal.setVisibility(View.GONE);
            mOderConfirm.setVisibility(View.GONE);

        }

        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }


    @OnClick(R.id.oderConfirm)
    public void onClick() {

        String sponsor = "";
        for (int i = 0; i < mInfo.getData().getDetail().getSponsor().size(); i++) {
            sponsor += mInfo.getData().getDetail().getSponsor().get(i) + " ";
        }
        mPopupWindow = ShowPopwindows.showPaymentPop(OrderInfoActivity.this, mInfo.getData().getDetail().getPrice(), sponsor, mInfo.getData().getDetail().getIntegral() + "", mInfo.getData().getDetail().getO_payprice() + "", new PayMentCallBack() {
            @Override
            public void payBack(String pass) {
                HttpParams params = new HttpParams();
                params.put("uid", Constans.uid);
                params.put("paypass", pass);
                params.put("ordernum", oNumber);
                goPay(params);
            }
        });

    }

    private void goPay(HttpParams params) {
        mSubscription = mHttpUtils.getData(UrlFactory.payChatOrder, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mPopupWindow.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                cancleLoadingDialog();
            }

            @Override
            public void onNext(String s) {
                cancleLoadingDialog();
                mPopupWindow.dismiss();
                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                //                GroupActivityInfoActivity.type = 0;
                if (obj.getInteger("code") == 1) {
                    isBuySuccess = true;
                    finish();
                }
            }
        });
    }


}
