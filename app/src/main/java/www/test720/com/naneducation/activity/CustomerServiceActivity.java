package www.test720.com.naneducation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Customer;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class CustomerServiceActivity extends BaseToolbarActivity {


    @BindView(R.id.staffNumber)
    TextView mStaffNumber;
    @BindView(R.id.bossNumber)
    TextView mBossNumber;
    @BindView(R.id.questionRecyclerView)
    RecyclerView mQuestionRecyclerView;
    List<Customer.DataBean.QuesListBean> mQuesLists = new ArrayList<>();
    BaseRecyclerAdapter<Customer.DataBean.QuesListBean> baseRecyclerAdapter;
    @BindView(R.id.staffName)
    TextView mStaffName;
    @BindView(R.id.bossName)
    TextView mBossName;

    @Override
    protected int getContentView() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("cityId", Constans.city_id);
        mSubscription = mHttpUtils.getData(UrlFactory.agentInter, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                Gson gson = new Gson();
                Customer customer = gson.fromJson(s, Customer.class);
                mStaffNumber.setText(customer.getData().getAgentphone());
                mBossNumber.setText(customer.getData().getVisephone());
                mStaffName.setText(customer.getData().getAgentName());
                mBossName.setText(customer.getData().getBossName());
                mQuesLists.addAll(customer.getData().getQuesList());
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        if (baseRecyclerAdapter == null) {
            baseRecyclerAdapter = new BaseRecyclerAdapter<Customer.DataBean.QuesListBean>(this, mQuesLists, R.layout.item_question_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, Customer.DataBean.QuesListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.question, item.getQuestion());
                    holder.setText(R.id.answer, item.getAnswer());
                }
            };
            mQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mQuestionRecyclerView.setAdapter(baseRecyclerAdapter);
        } else {
            baseRecyclerAdapter.notifyDataSetChanged();
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
        initToobar(R.drawable.fanhuibai, "客服", -1);
        setToolbarColor(R.color.base_color);
        setTitleColor(R.color.white);
    }


    @OnClick({R.id.staffNumber, R.id.bossNumber})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.staffNumber:

                final MaterialDialog dialog = new MaterialDialog(CustomerServiceActivity.this);
                dialog.content("是否拨打客服电话" + mStaffNumber.getText().toString().trim())//
                        .contentTextSize(14)
                        .titleTextSize(16)
                        .btnText("拨打电话", "取消")//
                        .showAnim(new BounceTopEnter())//
                        .show();
                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        if (ActivityCompat.checkSelfPermission(CustomerServiceActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(CustomerServiceActivity.this, "拨打电话权限已关闭，请打开权限", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CustomerServiceActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mStaffNumber.getText().toString().trim())));
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });


                break;
            case R.id.bossNumber:

                final MaterialDialog bossDialog = new MaterialDialog(CustomerServiceActivity.this);
                bossDialog.content("是否拨打客服电话" + mBossNumber.getText().toString().trim())//
                        .contentTextSize(14)
                        .titleTextSize(16)
                        .btnText("拨打电话", "取消")//
                        .showAnim(new BounceTopEnter())//
                        .show();
                bossDialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        bossDialog.dismiss();
                        if (ActivityCompat.checkSelfPermission(CustomerServiceActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(CustomerServiceActivity.this, "拨打电话权限已关闭，请打开权限", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CustomerServiceActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mBossNumber.getText().toString().trim())));
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        bossDialog.dismiss();
                    }
                });

                break;
        }
    }
}
