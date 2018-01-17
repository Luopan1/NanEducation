package www.test720.com.naneducation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
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
import www.test720.com.naneducation.bean.CityNews;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class NewsActivity extends BaseToolbarActivity {


    @BindView(R.id.newsRecyclerView)
    RecyclerView mNewsRecyclerView;
    List<CityNews.DataBean.ListBean> mNewsLists = new ArrayList<>();
    BaseRecyclerAdapter<CityNews.DataBean.ListBean> newAdapter;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private int currentPage = 1;
    private CityNews mCityNews;

    @Override
    protected int getContentView() {
        return R.layout.activity_news;
    }


    public void getData() {
        HttpParams params = new HttpParams();
        params.put("cityId", Constans.city_id);
        params.put("page", currentPage);
        mSubscription = mHttpUtils.getData(UrlFactory.cityNews, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                if (currentPage == 1) {
                    mNewsLists.clear();
                }
                Gson gson = new Gson();
                mCityNews = gson.fromJson(s, CityNews.class);
                mNewsLists.addAll(mCityNews.getData().getList());
                setAdapter();
            }
        });

    }

    @Override
    protected void initData() {


    }

    private void setAdapter() {
        if (newAdapter == null) {
            newAdapter = new BaseRecyclerAdapter<CityNews.DataBean.ListBean>(this, mNewsLists, R.layout.item_news_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, CityNews.DataBean.ListBean item, int position, boolean isScrolling) {
                    ImageView newsImage = holder.getView(R.id.newsImage);
                    holder.setText(R.id.newsTime, item.getTime());
                    holder.setText(R.id.newTitle, item.getName());
                    Glide.with(NewsActivity.this).load(UrlFactory.baseImageUrl + item.getLogo()).into(newsImage);
                }

            };
            mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mNewsRecyclerView.setAdapter(newAdapter);

            newAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();

                    if (mNewsLists.get(position).getTarget_type().equals("2")) {
                        bundle.putString("url", mNewsLists.get(position).getTarget_url());
                        jumpToActivity(WebViewAcitivty.class, bundle, false);
                    } else if (mNewsLists.get(position).getTarget_type().equals("1")) {
                        bundle.putString("url", UrlFactory.citynewsDetail + "/detailId/" + mNewsLists.get(position).getN_id());
                        bundle.putString("title", mNewsLists.get(position).getName());
                        bundle.putString("time", mNewsLists.get(position).getTime());
                        jumpToActivity(NewsInfoWebViewActivity.class, bundle, false);
                    } else {
                        ShowToast("当前新闻没有详情页面");
                    }

                }
            });

        } else {
            newAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setEnableLoadmore(true);

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                currentPage = 1;
                getData();
            }

            @Override
            public void onFinishRefresh() {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

                LogUtils.e(currentPage + "______" + mCityNews.getData().getTotal());
                if (currentPage < mCityNews.getData().getTotal()) {
                    currentPage++;
                    getData();

                } else {
                    mRefreshLayout.finishLoadmore();
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

        initToobar("新闻");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
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
        if (currentPage == 1) {
            mRefreshLayout.finishRefreshing();
        } else {
            mRefreshLayout.finishLoadmore();
        }
    }

}
