package www.test720.com.naneducation.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.adapter.PopActivityKindSelectAdapter;
import www.test720.com.naneducation.adapter.PopSelectAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Grade;
import www.test720.com.naneducation.bean.GroupActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.AnimUtil;

import static android.view.View.inflate;

public class GroupActivity_Activity extends BaseToolbarActivity {

    @BindView(R.id.classification)
    TextView mClassification;
    @BindView(R.id.downArrow1)
    ImageView mDownArrow1;
    @BindView(R.id.downArrow1_1)
    ImageView mDownArrow11;
    @BindView(R.id.coures)
    LinearLayout mCoures;
    @BindView(R.id.downArrow2)
    ImageView mDownArrow2;
    @BindView(R.id.downArrow1_2)
    ImageView mDownArrow12;
    @BindView(R.id.study)
    LinearLayout mStudy;
    @BindView(R.id.downArrow3)
    ImageView mDownArrow3;
    @BindView(R.id.downArrow1_3)
    ImageView mDownArrow13;
    @BindView(R.id.sortLin)
    LinearLayout mSortLin;
    @BindView(R.id.activityRecyclerView)
    RecyclerView mActivityRecyclerView;
    public int TYPE = 0;
    @BindView(R.id.schooltime)
    TextView mSchooltime;
    @BindView(R.id.sort)
    TextView mSort;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private int popWindosType = 0;
    private PopupWindow mActivityKindPop;
    private PopActivityKindSelectAdapter mSelectAdapter;
    private List<GroupActivity.DataBean.TypeListBean> mActivityLists = new ArrayList<>();
    private List<Grade> mActivityStatueLists;
    private PopupWindow mActivityStatuePop;
    private PopSelectAdapter mActivityStatueAdapter;
    private PopupWindow mActivitySortPop;
    private PopSelectAdapter activitySortAdapter;
    private List<GroupActivity.DataBean.ListBean> mLists = new ArrayList<>();
    private BaseRecyclerAdapter<GroupActivity.DataBean.ListBean> mGroupActivityAdapter;
    private int mCurrentPage = 1;
    private int mTotalPage = 0;
    private HttpParams mParams = new HttpParams();
    private List<Grade> mActivitySortLists;
    public static int type = 1;


    @Override
    protected int getContentView() {
        return R.layout.activity_group_;
    }


    public void getData() {
        mParams.put("cityId", Constans.city_id);
        mParams.put("page", mCurrentPage);
        mParams.put("long", Constans.longitude);
        mParams.put("lat", Constans.lat);
        mSubscription = mHttpUtils.getData(UrlFactory.actList, mParams, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                onStopLoad();
                type = 1;

            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                mRefreshLayout.finishRefreshing();
                type = 1;
            }

            @Override
            public void onNext(String s) {
                if (mCurrentPage == 1) {
                    mLists.clear();
                }
                mActivityLists.clear();
                Gson gson = new Gson();
                GroupActivity grou = gson.fromJson(s, GroupActivity.class);
                mTotalPage = grou.getData().getTotal();
                mLists.addAll(grou.getData().getList());
                mActivityLists.addAll(grou.getData().getTypeList());
                mActivityLists.add(0, new GroupActivity.DataBean.TypeListBean("全部", " "));
                setAdapter();

            }
        });
    }

    @Override
    protected void initData() {
    }

    private void setAdapter() {
        if (mGroupActivityAdapter == null) {
            mGroupActivityAdapter = new BaseRecyclerAdapter<GroupActivity.DataBean.ListBean>(this, mLists, R.layout.item_group_activty_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, GroupActivity.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.activityImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setText(R.id.activityTitle, item.getAct_name());
                    if (item.getAct_money().equals("0")) {
                        holder.getView(R.id.castMoney).setVisibility(View.GONE);
                    } else {
                        holder.getView(R.id.castMoney).setVisibility(View.VISIBLE);
                        holder.setText(R.id.castMoney, "￥" + item.getAct_money());
                    }
                    holder.setText(R.id.activityAddress, item.getAct_address());
                    holder.setText(R.id.activityDistance, item.getDistance() + "Km");
                    holder.setText(R.id.activityStatue, item.getType());

                }
            };
            mActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mActivityRecyclerView.setAdapter(mGroupActivityAdapter);

            mGroupActivityAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mLists.get(position).getAct_id());
                    bundle.putBoolean("isShow", true);
                    jumpToActivity(GroupActivityInfoActivity.class, bundle, false);
                }
            });

        } else {
            mGroupActivityAdapter.notifyDataSetChanged();
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
                LogUtils.e(mCurrentPage + "__________" + mTotalPage);
                if (mCurrentPage < mTotalPage) {
                    mCurrentPage++;
                    getData();
                } else {
                    onStopLoad();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type == 2) {
            mRefreshLayout.startRefresh();
        }
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;

    }

    @Override
    protected void initView() {
        initToobar(R.drawable.fanhuihei, "团体活动", "发布");
        setRightTextImage(R.drawable.fabu);
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

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

    @OnClick({R.id.coures, R.id.downArrow2, R.id.downArrow1_2, R.id.study, R.id.sortLin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coures:
                TYPE = 1;
                if (TYPE == popWindosType) {
                    mActivityKindPop.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindows(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow1, mDownArrow11, 10);
                    mClassification.setTextColor(getResources().getColor(R.color.base_color));
                }
                break;
            case R.id.downArrow2:

                break;
            case R.id.downArrow1_2:
                break;
            case R.id.study:
                TYPE = 2;
                if (TYPE == popWindosType) {
                    mActivityStatuePop.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindows(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow2, mDownArrow12, 10);
                    mSchooltime.setTextColor(getResources().getColor(R.color.base_color));
                }


                break;
            case R.id.sortLin:
                TYPE = 3;
                if (TYPE == popWindosType) {
                    mActivitySortPop.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindows(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow3, mDownArrow13, 10);
                    mSort.setTextColor(getResources().getColor(R.color.base_color));
                }

                break;
        }
    }

    public void showPopWindows(final int type) {

        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        toolbar.measure(w, h);
        mCoures.measure(w, h);
        int toolBarHeight = toolbar.getBottom();
        int courseHeight = mCoures.getBottom();
        int DisplayWidth = getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = getResources().getDisplayMetrics().heightPixels;

        if (TYPE == 0) {
            return;
        }
        if (TYPE == 1) {
            popWindosType = 1;
            View classificationView = inflate(this, R.layout.pop_group_item_activity_kin, null);// 得到加载view
            if (mActivityKindPop == null) {
                mActivityKindPop = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - toolBarHeight - getStatusBarHeight() - courseHeight);

                mSelectAdapter = new PopActivityKindSelectAdapter(this, mActivityLists);
            }
            ListView activityKindListView = (ListView) classificationView.findViewById(R.id.activityListView);
            RelativeLayout spaceRelative = (RelativeLayout) classificationView.findViewById(R.id.spaceRelative);

            activityKindListView.setAdapter(mSelectAdapter);
            activityKindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mClassification.setText(mActivityLists.get(position).getName());
                    mSelectAdapter.setSelectItem(position);
                    mSelectAdapter.notifyDataSetChanged();

                    mParams.put("typeId", mActivityLists.get(position).getTid());
                    mParams.put("sortype", 1);
                    mRefreshLayout.startRefresh();
                    popWindosType = 0;
                    mActivityKindPop.dismiss();

                }
            });
            spaceRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivityKindPop.dismiss();
                }
            });

            mActivityKindPop.setAnimationStyle(R.style.AnimBottom);
            mActivityKindPop.setFocusable(false);
            mActivityKindPop.update();
            mActivityKindPop.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mActivityKindPop.setBackgroundDrawable(dw);
            mActivityKindPop.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);

            mActivityKindPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mClassification.setTextColor(getResources().getColor(R.color.textcolor));
                    mSchooltime.setTextColor(getResources().getColor(R.color.textcolor));
                    mSort.setTextColor(getResources().getColor(R.color.textcolor));
                    mClassification.setGravity(Gravity.CENTER);
                    if (type == 1) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow11, mDownArrow1, 10);
                    } else if (type == 2) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow12, mDownArrow2, 10);
                    } else if (type == 3) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow13, mDownArrow3, 10);
                    }

                }
            });

        } else if (TYPE == 2) {
            popWindosType = 2;
            View classificationView = inflate(this, R.layout.pop_group_item_activity_kin, null);// 得到加载view
            if (mActivityStatuePop == null) {
                mActivityStatuePop = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - toolBarHeight - getStatusBarHeight() - courseHeight);
                mActivityStatueLists = new ArrayList<>();
                mActivityStatueLists.add(new Grade(0, "全部"));
                mActivityStatueLists.add(new Grade(1, "可报名"));
                mActivityStatueLists.add(new Grade(2, "进行中"));
                mActivityStatueLists.add(new Grade(3, "已结束"));

                mActivityStatueAdapter = new PopSelectAdapter(this, mActivityStatueLists);
            }

            ListView activityKindListView = (ListView) classificationView.findViewById(R.id.activityListView);
            RelativeLayout spaceRelative = (RelativeLayout) classificationView.findViewById(R.id.spaceRelative);

            activityKindListView.setAdapter(mActivityStatueAdapter);
            activityKindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mActivityStatueAdapter.setSelectItem(position);
                    mActivityStatueAdapter.notifyDataSetChanged();
                    popWindosType = 0;
                    if (position == 0) {
                        mParams.put("state", "");
                        mParams.put("sortype", 1);
                    } else {
                        mParams.put("state", mActivityStatueLists.get(position).getId());
                        mParams.put("sortype", 1);
                    }
                    mRefreshLayout.startRefresh();
                    mActivityStatuePop.dismiss();

                }
            });
            spaceRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    mActivityStatuePop.dismiss();
                }
            });

            mActivityStatuePop.setAnimationStyle(R.style.AnimBottom);
            mActivityStatuePop.setFocusable(false);
            mActivityStatuePop.update();
            mActivityStatuePop.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mActivityStatuePop.setBackgroundDrawable(dw);
            mActivityStatuePop.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);

            mActivityStatuePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mClassification.setTextColor(getResources().getColor(R.color.textcolor));
                    mSchooltime.setTextColor(getResources().getColor(R.color.textcolor));
                    mSort.setTextColor(getResources().getColor(R.color.textcolor));
                    mClassification.setGravity(Gravity.CENTER);
                    if (type == 1) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow11, mDownArrow1, 10);
                    } else if (type == 2) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow12, mDownArrow2, 10);
                    } else if (type == 3) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow13, mDownArrow3, 10);
                    }

                }
            });


        } else if (TYPE == 3) {
            popWindosType = 3;
            View classificationView = inflate(this, R.layout.pop_group_item_activity_kin, null);// 得到加载view
            if (mActivitySortPop == null) {
                mActivitySortPop = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - toolBarHeight - getStatusBarHeight() - courseHeight);
                mActivitySortLists = new ArrayList<>();
                mActivitySortLists.add(new Grade(0, "全部"));
                mActivitySortLists.add(new Grade(1, "价格由高到低"));
                mActivitySortLists.add(new Grade(2, "价格由低到高"));
                mActivitySortLists.add(new Grade(3, "免费"));
                activitySortAdapter = new PopSelectAdapter(this, mActivitySortLists);
            }

            ListView activityKindListView = (ListView) classificationView.findViewById(R.id.activityListView);
            RelativeLayout spaceRelative = (RelativeLayout) classificationView.findViewById(R.id.spaceRelative);

            activityKindListView.setAdapter(activitySortAdapter);
            activityKindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    activitySortAdapter.setSelectItem(position);
                    activitySortAdapter.notifyDataSetChanged();
                    popWindosType = 0;
                    if (position == 0) {
                        mParams.put("sort", "");
                        mParams.put("sortype", 1);
                    } else {
                        mParams.put("sort", mActivitySortLists.get(position).getId());
                        mParams.put("sortype", 1);
                    }
                    mRefreshLayout.startRefresh();
                    mActivitySortPop.dismiss();
                }
            });
            spaceRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivitySortPop.dismiss();
                }
            });
            mActivitySortPop.setAnimationStyle(R.style.AnimBottom);
            mActivitySortPop.setFocusable(false);
            mActivitySortPop.update();
            mActivitySortPop.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mActivitySortPop.setBackgroundDrawable(dw);
            mActivitySortPop.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);

            mActivitySortPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mClassification.setTextColor(getResources().getColor(R.color.textcolor));
                    mSchooltime.setTextColor(getResources().getColor(R.color.textcolor));
                    mSort.setTextColor(getResources().getColor(R.color.textcolor));
                    mClassification.setGravity(Gravity.CENTER);
                    if (type == 1) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow11, mDownArrow1, 10);
                    } else if (type == 2) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow12, mDownArrow2, 10);
                    } else if (type == 3) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow13, mDownArrow3, 10);
                    }

                }
            });

        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void LeftOnClick() {
        if (mActivityKindPop != null && mActivityKindPop.isShowing()) {
            mActivityKindPop.dismiss();
        } else if (mActivityStatuePop != null && mActivityStatuePop.isShowing()) {
            mActivityStatuePop.dismiss();
        } else if (mActivitySortPop != null && mActivitySortPop.isShowing()) {
            mActivitySortPop.dismiss();
        } else {
            finish();
        }

    }

    @Override
    public void RightOnClick() {
        if (isLogined(1))
            jumpToActivity(ReleaseGroupActivity_Activity.class, false);
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

    @Override
    public void onBackPressed() {
        if (mActivityKindPop != null && mActivityKindPop.isShowing()) {
            mActivityKindPop.dismiss();
        } else if (mActivityStatuePop != null && mActivityStatuePop.isShowing()) {
            mActivityStatuePop.dismiss();
        } else if (mActivitySortPop != null && mActivitySortPop.isShowing()) {
            mActivitySortPop.dismiss();
        } else {
            finish();
        }
    }
}
