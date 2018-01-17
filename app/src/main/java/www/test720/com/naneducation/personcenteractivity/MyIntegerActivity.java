package www.test720.com.naneducation.personcenteractivity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Integral;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class MyIntegerActivity extends BaseToolbarActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_my_integer;
    }


    @BindView(R.id.walletInfoRecyclerView)
    RecyclerView mWalletInfoRecyclerView;
    BaseRecyclerAdapter<Integral.DataBean.ListBean> mWalletInfoAdapter;
    List<Integral.DataBean.ListBean> mWalletLists = new ArrayList<>();


    @Override
    protected void initData() {

        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        mSubscription = mHttpUtils.getData(UrlFactory.userIntegral, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                Gson gson = new Gson();
                Integral integral = gson.fromJson(s, Integral.class);
                mWalletLists.addAll(integral.getData().getList());
                setAdapter();
            }
        });


    }

    private void setAdapter() {
        if (mWalletInfoAdapter == null) {
            mWalletInfoAdapter = new BaseRecyclerAdapter<Integral.DataBean.ListBean>(this, mWalletLists, R.layout.item_wallet_info_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, Integral.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.title, item.getContent());
                    holder.setText(R.id.costTime, item.getTime());
                    holder.setText(R.id.costMoney, item.getIntegral() + " 积分");
                    TextView textView = holder.getView(R.id.costMoney);
                    if (Integer.parseInt(item.getIntegral()) > 0) {
                        textView.setTextColor(getResources().getColor(R.color.base_color));
                    } else {
                        textView.setTextColor(Color.parseColor("#FF7140"));
                    }
                }
            };
            mWalletInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mWalletInfoRecyclerView.setAdapter(mWalletInfoAdapter);
        } else {
            mWalletInfoAdapter.notifyDataSetChanged();
        }
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
        initToobar("积分");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
    }


}
