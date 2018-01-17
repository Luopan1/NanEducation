package www.test720.com.naneducation.personcenteractivity.colocation_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import www.test720.com.naneducation.activity.GroupActivityInfoActivity;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.CourseColocation;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

/**
 * @author LuoPan on 2017/10/25 11:31.
 */

public class ActivityFragment extends BaseFragment {
    @BindView(R.id.orderRecyclerView)
    RecyclerView mOrderRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private List<CourseColocation.DataBean.ListBean> mLists = new ArrayList<>();
    BaseRecyclerAdapter<CourseColocation.DataBean.ListBean> mAdapter;
    int mCurrentPage = 1;
    int mTotalPage = 0;

    @Override
    protected void initView() {
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

    @Override
    protected void initData() {

    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("type", 5);
        params.put("long", Constans.longitude);
        params.put("lat", Constans.lat);
        params.put("page", mCurrentPage);
        mSubscription = mHttpUtils.getData(UrlFactory.userLoveList, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                mSubscription.unsubscribe();
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
                    mLists.clear();
                }
                CourseColocation courseColocation = gson.fromJson(s, CourseColocation.class);
                mLists.addAll(courseColocation.getData().getList());
                mTotalPage = courseColocation.getData().getTotal();
                setAdapter();
            }
        });


    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<CourseColocation.DataBean.ListBean>(getActivity(), mLists, R.layout.item_group_activty_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, CourseColocation.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.activityImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setText(R.id.activityTitle, item.getAct_name());
                    holder.setText(R.id.castMoney, "￥" + item.getAct_money());
                    holder.setText(R.id.activityAddress, item.getAct_address());
                    holder.setText(R.id.activityDistance, item.getDistance() + "Km");
                    holder.setText(R.id.activityStatue, item.getType());
                }
            };
            mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderRecyclerView.setAdapter(mAdapter);


            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mLists.get(position).getAct_id());
                    jumpToActivity(GroupActivityInfoActivity.class, bundle, false);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
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
    protected int setLayoutResID() {
        return R.layout.fragment_all_my_order;
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
