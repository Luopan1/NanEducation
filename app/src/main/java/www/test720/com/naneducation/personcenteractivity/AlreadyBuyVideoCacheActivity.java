package www.test720.com.naneducation.personcenteractivity;

import android.graphics.Color;
import android.os.Bundle;
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
import www.test720.com.naneducation.activity.CourseInfoActivity;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.AreadyBuyVideo;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class AlreadyBuyVideoCacheActivity extends BaseToolbarActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    List<AreadyBuyVideo.DataBean.ListBean> mLists = new ArrayList<>();
    BaseRecyclerAdapter<AreadyBuyVideo.DataBean.ListBean> mAdapter;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    int mCurrentPage = 1;
    int mTotalPage = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_already_buy_live_broad_cast;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("type", 2);
        params.put("page", mCurrentPage);
        mSubscription = mHttpUtils.getData(UrlFactory.userLiveList, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

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
                    mLists.clear();
                }
                AreadyBuyVideo video = gson.fromJson(s, AreadyBuyVideo.class);
                mLists.addAll(video.getData().getList());
                mTotalPage = video.getData().getTotal();
                setAdapter();

            }
        });
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter<AreadyBuyVideo.DataBean.ListBean>(this, mLists, R.layout.item_my_colocation_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, AreadyBuyVideo.DataBean.ListBean item, int position, boolean isScrolling) {

                    holder.setImageByUrl(R.id.couseImage, UrlFactory.baseImageUrl + item.getLive_logo());
                    holder.setText(R.id.courseTitle, item.getLive_title());
                    holder.setImageByUrl(R.id.course_teacher_image, UrlFactory.baseImageUrl + item.getHead());
                    holder.setText(R.id.course_teacher_name, item.getName());
                    holder.setText(R.id.time, item.getTime());

                    holder.setText(R.id.statue, item.getLivetype());
                    TextView statue = holder.getView(R.id.statue);
                    if (item.getLivetype().equals("未开始"))
                        statue.setTextColor(Color.parseColor("#FF7241"));
                    else if (item.getLivetype().equals("已结束"))
                        statue.setTextColor(Color.parseColor("#B3B3B3"));

                }
            };
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", mLists.get(position).getLive_title());
                    bundle.putInt("type", 2);
                    bundle.putString("id", mLists.get(position).getLid());
                    bundle.putString("path", mLists.get(position).getBack_url());
                    jumpToActivity(CourseInfoActivity.class, bundle, false);

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
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar("录播");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
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
