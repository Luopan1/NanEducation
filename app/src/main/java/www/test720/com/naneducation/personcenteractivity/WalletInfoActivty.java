package www.test720.com.naneducation.personcenteractivity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
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
import www.test720.com.naneducation.bean.UserMoneyDetail;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class WalletInfoActivty extends BaseToolbarActivity {

    @BindView(R.id.walletInfoRecyclerView)
    RecyclerView mWalletInfoRecyclerView;
    BaseRecyclerAdapter<UserMoneyDetail.DataBean.ListBean> mWalletInfoAdapter;
    List<UserMoneyDetail.DataBean.ListBean> mWalletLists = new ArrayList<>();
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    int mCurrentPage = 1;
    int mTotalPage = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet_info_activty;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("page", mCurrentPage);
        mSubscription = mHttpUtils.getData(UrlFactory.userMoneyDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                onStopLoad();
            }

            @Override
            public void onNext(String s) {
                Gson gson = new Gson();
                if (mCurrentPage == 1) {
                    mWalletLists.clear();
                }
                UserMoneyDetail detail = gson.fromJson(s, UserMoneyDetail.class);
                mWalletLists.addAll(detail.getData().getList());
                mTotalPage = detail.getData().getTotal();
                setAdapter();

            }
        });


    }

    private void setAdapter() {
        if (mWalletInfoAdapter == null) {
            mWalletInfoAdapter = new BaseRecyclerAdapter<UserMoneyDetail.DataBean.ListBean>(this, mWalletLists, R.layout.item_wallet_info_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, UserMoneyDetail.DataBean.ListBean item, int position, boolean isScrolling) {

                    TextView money = holder.getView(R.id.costMoney);
                    if (item.getType().equals("7")) {
                        holder.getView(R.id.statue).setVisibility(View.VISIBLE);
                        holder.setText(R.id.statue, item.getCash_type());
                        money.setTextColor(getResources().getColor(R.color.base_color));
                    } else {
                        holder.getView(R.id.statue).setVisibility(View.GONE);
                        money.setTextColor(Color.parseColor("#FF7140"));

                    }
                    holder.setText(R.id.title, item.getContent());
                    holder.setText(R.id.costTime, item.getTime());
                    holder.setText(R.id.costMoney, item.getMoney());

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

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mCurrentPage = 1;
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (mCurrentPage < mTotalPage) {
                    mCurrentPage++;
                    LogUtils.e(mTotalPage + "___" + mCurrentPage);
                    getData();
                } else {
                    onStopLoad();
                }
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
        initToobar("明细");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setEnableLoadmore(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.startRefresh();
            }
        }, 200);
    }

    //停止刷新
    private void onStopLoad() {
        if (mCurrentPage == 1) {
            mRefreshLayout.finishRefreshing();
        } else {
            mRefreshLayout.finishLoadmore();
        }
        if (mTotalPage == mCurrentPage) {
            mRefreshLayout.finishLoadmore();
        }
    }

}
