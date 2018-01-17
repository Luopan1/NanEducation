package www.test720.com.naneducation.personcenteractivity.orderfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
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
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.OrderBean;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.personcenteractivity.OrderInfoActivity;

/**
 * @author luopan
 *         Created by LuoPan on 2017/10/24 16:42.
 */

public class MyAllOrderFragment extends BaseFragment {
    @BindView(R.id.orderRecyclerView)
    RecyclerView mOrderRecyclerView;
    BaseRecyclerAdapter<OrderBean.DataBean.ListBean> orderAdapter;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private List<OrderBean.DataBean.ListBean> mOrderLists = new ArrayList<>();
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
        LogUtils.e("getData()");
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("type", 3);
        params.put("page", mCurrentPage);
        mSubscription = mHttpUtils.getData(UrlFactory.userOrderList, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                onStopLoad();
                mSubscription.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                onStopLoad();
            }

            @Override
            public void onNext(String s) {
                if (mCurrentPage == 1) {
                    mOrderLists.clear();
                }

                Gson gson = new Gson();
                OrderBean bean = gson.fromJson(s, OrderBean.class);
                mTotalPage = bean.getData().getTotal();
                mOrderLists.addAll(bean.getData().getList());
                setAdapter();
            }
        });


    }

    private void setAdapter() {
        if (orderAdapter == null) {
            orderAdapter = new BaseRecyclerAdapter<OrderBean.DataBean.ListBean>(getActivity(), mOrderLists, R.layout.item_order_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, final OrderBean.DataBean.ListBean item, final int position, boolean isScrolling) {
                    holder.setText(R.id.orderStatue, item.getO_type());
                    holder.setText(R.id.orderTitle, item.getTitle());
                    holder.setText(R.id.orderKind, item.getO_ordertype());
                    holder.setImageByUrl(R.id.orderImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setText(R.id.orderThem, item.getName());


                    if (item.getO_type().equals("未付款")) {
                        holder.getView(R.id.orderDelete).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.orderDelete).setVisibility(View.GONE);
                    }

                    if (item.getIs_back() == 1) {
                        holder.getView(R.id.orderRefund).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.orderRefund).setVisibility(View.GONE);
                    }
                    holder.setText(R.id.orderSignUpName, item.getUserlist().toString().replace("[", "").replace("]", " ").replace(",", " "));
                    holder.getView(R.id.orderDelete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final MaterialDialog dialog = new MaterialDialog(getActivity());
                            dialog.content("是否删除订单")//
                                    .contentTextSize(14)
                                    .titleTextSize(16)
                                    .btnText("删除", "取消")//
                                    .showAnim(new BounceTopEnter())//
                                    .show();
                            dialog.setOnBtnClickL(new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                    deleteOrder(item.getO_number(), position);
                                }
                            }, new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    holder.getView(R.id.orderRefund).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final MaterialDialog dialog = new MaterialDialog(getActivity());
                            dialog.content("是否退款")//
                                    .contentTextSize(14)
                                    .titleTextSize(16)
                                    .btnText("退款", "取消")//
                                    .showAnim(new BounceTopEnter())//
                                    .show();
                            dialog.setOnBtnClickL(new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                    refound(item.getO_number(), position);
                                }
                            }, new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });

                }
            };
            mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderRecyclerView.setAdapter(orderAdapter);

            orderAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    if (mOrderLists.get(position).getO_type().equals("未付款"))
                        bundle.putInt("type", 1);
                    else if (mOrderLists.get(position).getO_type().equals("已付款"))
                        bundle.putInt("type", 3);
                    else if (mOrderLists.get(position).getO_type().equals("已退款"))
                        bundle.putInt("type", 2);
                    bundle.putString("orderNumber", mOrderLists.get(position).getO_number());
                    jumpToActivity(OrderInfoActivity.class, bundle, false);
                }
            });

        } else {
            orderAdapter.notifyDataSetChanged();
        }

    }

    private void refound(String o_number, final int position) {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("o_number", o_number);
        mSubscription = mHttpUtils.getData(UrlFactory.userBackOrder, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog("");
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
                JSONObject json = JSON.parseObject(s);
                ShowToast(json.getString("msg"));
                mOrderLists.get(position).setO_type("已退款");
                mOrderLists.get(position).setIs_back(0);
                orderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void deleteOrder(String o_number, final int positon) {
        HttpParams params = new HttpParams();
        params.put("o_number", o_number);
        mSubscription = mHttpUtils.getData(UrlFactory.userDelateOrder, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog("");
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
                JSONObject json = JSON.parseObject(s);
                ShowToast(json.getString("msg"));
                mOrderLists.remove(positon);
                orderAdapter.notifyDataSetChanged();
            }
        });

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
