package www.test720.com.naneducation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.adapter.LiveMainAdapter;
import www.test720.com.naneducation.adapter.SearchResultAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.LiveBroad;
import www.test720.com.naneducation.bean.LiveSearchResult;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.AnimUtil;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.utils.SizeUtils;
import www.test720.com.naneducation.view.CustomDatePicker;
import www.test720.com.naneducation.view.SpaceItemDecoration;

import static android.view.View.inflate;
import static www.test720.com.naneducation.R.id.item;
import static www.test720.com.naneducation.R.id.mainRecyclerView;

public class LiveBroadcastActivity extends BaseToolbarActivity {


    @BindView(R.id.fanhui)
    RelativeLayout mFanhui;
    @BindView(R.id.classification)
    TextView mClassification;
    @BindView(R.id.schooltime)
    TextView mSchooltime;
    @BindView(R.id.sort)
    TextView mSort;
    @BindView(R.id.liveBroadCastRecylcerView)
    RecyclerView mLiveBroadCastRecylcerView;
    @BindView(R.id.titleLinear)
    LinearLayout mTitleLinear;
    @BindView(R.id.downArrow1)
    ImageView mDownArrow1;
    @BindView(R.id.downArrow2)
    ImageView mDownArrow2;
    @BindView(R.id.downArrow3)
    ImageView mDownArrow3;
    @BindView(R.id.downArrow1_1)
    ImageView mDownArrow11;
    @BindView(R.id.coures)
    LinearLayout mCoures;
    @BindView(R.id.study)
    LinearLayout mStudy;
    @BindView(R.id.sortLin)
    LinearLayout mSortLin;
    @BindView(R.id.lienarLayout)
    LinearLayout mLienarLayout;
    @BindView(R.id.downArrow1_2)
    ImageView mDownArrow12;
    @BindView(R.id.downArrow1_3)
    ImageView mDownArrow13;

    View selectStudyTime;
    @BindView(R.id.searchEdit_text)
    EditText mSearchEditText;
    @BindView(R.id.searchResultRecyclerView)
    RecyclerView mSearchResultRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;

    private int TYPE = 0;
    private PopupWindow mCouresKindPopwindow;

    private PopupWindow mselectTimePopwindos;
    private PopupWindow mSortPopwindow;

    private View mItemView;
    private CustomDatePicker timePicker;
    private CustomDatePicker endPicker;
    private String time;
    private String date;
    private TextView mStartTime;
    private TextView mEndtTime;
    private int popWindosType = 0;
    private String startTime = "00:00";
    private String endTime = "23:59";
    BaseRecyclerAdapter<LiveBroad.DataBean.ListBean> liveCastAdapter;
    SearchResultAdapter SearchAdapter;
    List<TextView> clickPosition = new ArrayList<>();
    List<RelativeLayout> clickView = new ArrayList<>();
    private List<LiveBroad.DataBean.ListBean> mLiveBroadcastList = new ArrayList<>();
    int i;
    private SizeUtils sizeUtils;
    private LiveBroad mLiveBroad;
    private BaseRecyclerAdapter<LiveBroad.DataBean.TypeListBean.ZiBean> mMoreAdapter;
    private LiveMainAdapter mMainAdapter;
    private List<LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean> mZiLists;
    private List<LiveBroad.DataBean.TypeListBean.ZiBean> mMoreLists;
    private BaseRecyclerAdapter<LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean> classFicationAdapter;
    private HttpParams mParams = new HttpParams();
    private int TYPEID = 0;
    private String mWeek;
    private int mType = 1;
    private int mLiveType;

    @Override
    protected int getContentView() {
        return R.layout.activity_live_broadcast;
    }

    @Override
    protected void initData() {


    }

    private void setAdapter(final List<LiveBroad.DataBean.ListBean> liveBroadcastList) {
        if (liveCastAdapter == null) {
            liveCastAdapter = new BaseRecyclerAdapter<LiveBroad.DataBean.ListBean>(this, liveBroadcastList, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, LiveBroad.DataBean.ListBean item, int position, boolean isScrolling) {
                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);
                    sizeUtils.setTextSize((TextView) holder.getView(R.id.money), 17);
                    holder.setText(R.id.courseKind, item.getLivetype());
                    if (item.getLivetype().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getLivetype().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getLivetype().equals("进行中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                    else if (item.getLivetype().equals("已结束"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.color_black_ff666666));
                    else if (item.getLivetype().equals("回放"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));
                    TextView money = holder.getView(R.id.money);
                    if (item.getLivetype().equals("免费")) {
                        money.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.free_line));
                        Drawable drawable = context.getResources().getDrawable(R.mipmap.free_money);
                        drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                        money.setTextColor(Color.parseColor("#999999"));
                        money.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        money.setBackgroundDrawable(null);
                        Drawable drawable = context.getResources().getDrawable(R.drawable.money);
                        drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                        money.setTextColor(Color.parseColor("#4295CB"));
                        money.setCompoundDrawables(drawable, null, null, null);
                    }
                    if (item.getName().length() > 3) {
                        item.setName(item.getName().substring(0, 3) + "...");
                    }


                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLive_logo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getHead());
                    holder.setText(R.id.teacherName, item.getName());
                    holder.setText(R.id.studyCourseName, item.getLive_title());
                    holder.setText(R.id.money, item.getPrice());

                }
            };
            mLiveBroadCastRecylcerView.setLayoutManager(new GridLayoutManager(this, 2));
            mLiveBroadCastRecylcerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            mLiveBroadCastRecylcerView.setAdapter(liveCastAdapter);

            liveCastAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", liveBroadcastList.get(position).getLive_title());
                    bundle.putInt("type", mLiveType);
                    bundle.putString("id", liveBroadcastList.get(position).getLid());
                    bundle.putString("path", liveBroadcastList.get(position).getBack_url());
                    bundle.putString("room", liveBroadcastList.get(position).getRoom_mun());
                    jumpToActivity(CourseInfoActivity.class, bundle, false);


                }
            });


        } else {
            liveCastAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (mType == 1) {
                    getData();
                } else {
                    doSerach();
                }

            }
        });

        mSearchEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                // 修改回车键功能
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LiveBroadcastActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    if (mSearchEditText.getText().toString().isEmpty()) {
                        ShowToast("请输入搜索内容");
                    } else {
                        doSerach();
                    }
                }
                return false;
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    mLienarLayout.setVisibility(View.VISIBLE);
                    mLiveBroadCastRecylcerView.setVisibility(View.VISIBLE);
                    mSearchResultRecyclerView.setVisibility(View.GONE);
                    mType = 1;
                } else {
                    mLienarLayout.setVisibility(View.GONE);
                    mType = 2;
                    mLiveBroadCastRecylcerView.setVisibility(View.GONE);
                    mSearchResultRecyclerView.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void doSerach() {
        HttpParams params = new HttpParams();
        params.put("type", 1);
        params.put("cityId", Constans.city_id);
        params.put("name", mSearchEditText.getText().toString().trim());
        mSubscription = mHttpUtils.getData(UrlFactory.searchLive, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                LiveSearchResult searchResult = gson.fromJson(s, LiveSearchResult.class);
                List<LiveSearchResult.DataBean.LiveBean> livelists = new ArrayList<LiveSearchResult.DataBean.LiveBean>();
                List<LiveSearchResult.DataBean.CourseBean> courseList = new ArrayList<>();
                List<LiveSearchResult.DataBean.LuboBean> luBoList = new ArrayList<>();
                livelists.addAll(searchResult.getData().getLive());
                courseList.addAll(searchResult.getData().getCourse());
                luBoList.addAll(searchResult.getData().getLubo());
                doSearch(livelists, courseList, luBoList);
            }
        });

    }

    private void getData() {
        mParams.put("cityId", Constans.city_id);
        mParams.put("type", mLiveType);
        mParams.put("cityId", Constans.city_id);
        mSubscription = mHttpUtils.getData(UrlFactory.liveList, mParams, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mRefreshLayout.finishRefreshing();
                mSubscription.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onNext(String s) {
                Gson gson = new Gson();
                mLiveBroadcastList.clear();
                mLiveBroad = gson.fromJson(s, LiveBroad.class);
                mLiveBroadcastList.addAll(mLiveBroad.getData().getList());
                setAdapter(mLiveBroadcastList);

            }
        });


    }

    private void doSearch(List<LiveSearchResult.DataBean.LiveBean> livelists, List<LiveSearchResult.DataBean.CourseBean> courseList, List<LiveSearchResult.DataBean.LuboBean> luBoList) {
        List<Integer> integerList = new ArrayList<>();
        if (mLiveType == 1) {
            integerList.add(1);
            integerList.add(2);
            integerList.add(3);
        } else if (mLiveType == 2) {
            integerList.add(2);
            integerList.add(1);
            integerList.add(3);
        }


        mLiveBroadCastRecylcerView.setVisibility(View.GONE);
        mSearchResultRecyclerView.setVisibility(View.VISIBLE);
        mLienarLayout.setVisibility(View.GONE);


        SearchAdapter = new SearchResultAdapter(this, integerList, livelists, luBoList, courseList);
        mSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultRecyclerView.setAdapter(SearchAdapter);

    }

    @Override
    protected void initView() {
        if (isNetworkAvailable(this))
            showLoadingDialog();
        Intent intent = getIntent();
        mLiveType = intent.getIntExtra("type", 1);
        if (mLiveType == 1) {
            mStudy.setVisibility(View.VISIBLE);
        } else if (mLiveType == 2) {
            mStudy.setVisibility(View.GONE);
        }
        mSearchResultRecyclerView.setVisibility(View.GONE);
        sizeUtils = new SizeUtils(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];


        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {

                startTime = time;
                LogUtils.e(startTime);
                mStartTime.setText(time);
            }
        }, time, "2027-12-31 23:59");//"2027-12-31 23:59"
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);


        endPicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                endTime = time;

                if (startTime == null) {
                    ShowToast("请先选择开始时间");
                } else {
                    int start = Integer.parseInt(startTime.split(":")[0]);
                    int end = Integer.parseInt(endTime.split(":")[0]);

                    if (end <= start) {
                        ShowToast("结束时间不能小于开始时间");
                    } else {
                        mEndtTime.setText(time);
                    }
                }
            }
        }, time, "2027-12-31 23:59");//"2027-12-31 23:59"
        endPicker.showSpecificTime(true);
        endPicker.setIsLoop(true);

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
        isShowToolBar = false;
        isShowBackImage = false;
    }


    @OnClick({R.id.fanhui, R.id.coures, R.id.study, R.id.sortLin})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.coures:
                TYPE = 1;
                Log.e("TAG++++++", "TYPE:" + TYPE + "popWindosType:" + popWindosType);
                if (TYPE == popWindosType) {
                    mCouresKindPopwindow.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow1, mDownArrow11, 10);
                    mClassification.setTextColor(getResources().getColor(R.color.base_color));
                }

                break;
            case R.id.study:
                TYPE = 2;
                if (TYPE == popWindosType) {
                    mselectTimePopwindos.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow2, mDownArrow12, 10);
                    mSchooltime.setTextColor(getResources().getColor(R.color.base_color));
                }


                break;
            case R.id.sortLin:
                TYPE = 3;
                if (TYPE == popWindosType) {
                    mSortPopwindow.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow3, mDownArrow13, 10);
                    mSort.setTextColor(getResources().getColor(R.color.base_color));
                }


                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }


    private void showPopWindos(final int type) {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mCoures.measure(w, h);
        mTitleLinear.measure(w, h);
        int height = mCoures.getBottom();

        int height1 = mTitleLinear.getBottom();

        int DisplayWidth = getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = getResources().getDisplayMetrics().heightPixels;

        if (type == 0) {
            return;
        } else if (type == 1) {
            popWindosType = 1;
            View classificationView = inflate(this, R.layout.pop_classification, null);// 得到加载view
            ListView mMainRecyclerView;
            RecyclerView mMoreRecyclerView;
            // 创建自定义样式dialog
            if (mCouresKindPopwindow == null) {
                mCouresKindPopwindow = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }
            mMainRecyclerView = (ListView) classificationView.findViewById(mainRecyclerView);
            mMoreRecyclerView = (RecyclerView) classificationView.findViewById(R.id.moreRecyclerView);
            setClassiFicationData(mMainRecyclerView, mMoreRecyclerView);
            for (int i = 0; i < clickView.size(); i++) {
                clickView.get(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_base_color));
            }


            mCouresKindPopwindow.setAnimationStyle(R.style.AnimBottom);
            mCouresKindPopwindow.setFocusable(false);
            mCouresKindPopwindow.update();
            mCouresKindPopwindow.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mCouresKindPopwindow.setBackgroundDrawable(dw);
            mCouresKindPopwindow.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);

            mCouresKindPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
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


        } else if (type == 2) {
            popWindosType = 2;
            if (selectStudyTime == null) {
                selectStudyTime = View.inflate(this, R.layout.pop_select_study_time, null);
            }
            if (mselectTimePopwindos == null) {

                mselectTimePopwindos = new PopupWindow(selectStudyTime, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }

            mStartTime = (TextView) selectStudyTime.findViewById(R.id.start_time);
            mEndtTime = (TextView) selectStudyTime.findViewById(R.id.end_time);
            RelativeLayout space = (RelativeLayout) selectStudyTime.findViewById(R.id.spaceRelative);
            TextView commit = (TextView) selectStudyTime.findViewById(R.id.commit);
            TextView all = (TextView) selectStudyTime.findViewById(R.id.all);

            TextView xingqiyi1 = (TextView) selectStudyTime.findViewById(R.id.xingqi1);
            TextView xingqiyi2 = (TextView) selectStudyTime.findViewById(R.id.xingqi2);
            TextView xingqiyi3 = (TextView) selectStudyTime.findViewById(R.id.xingqi3);
            TextView xingqiyi4 = (TextView) selectStudyTime.findViewById(R.id.xingqi4);
            TextView xingqiyi5 = (TextView) selectStudyTime.findViewById(R.id.xingqi5);
            TextView xingqiyi6 = (TextView) selectStudyTime.findViewById(R.id.xingqi6);
            TextView xingqiyi7 = (TextView) selectStudyTime.findViewById(R.id.xingqi7);


            TextView[] textView = {xingqiyi1, xingqiyi2, xingqiyi3, xingqiyi4, xingqiyi5, xingqiyi6, xingqiyi7};
            setClickListener(textView);

            for (int i = 0; i < clickPosition.size(); i++) {
                TextView textView1 = clickPosition.get(i);
                textView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_base_color));
            }

            selectStudyTime.findViewById(R.id.start_relatvie).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePicker.show(time);
                }
            });

            selectStudyTime.findViewById(R.id.end_Relative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endPicker.show(time);
                }
            });
            space.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mselectTimePopwindos.dismiss();
                    popWindosType = 0;
                }
            });

            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTimeSelect();
                    mselectTimePopwindos.dismiss();
                    popWindosType = 0;
                }
            });
            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParams.put("week", 0);
                    mParams.put("weektime", 0);
                    mRefreshLayout.startRefresh();
                    mselectTimePopwindos.dismiss();
                    popWindosType = 0;
                }
            });


            mselectTimePopwindos.setAnimationStyle(R.style.AnimBottom);
            mselectTimePopwindos.setFocusable(false);
            mselectTimePopwindos.update();
            mselectTimePopwindos.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mselectTimePopwindos.setBackgroundDrawable(dw);
            mselectTimePopwindos.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);


            mselectTimePopwindos.setOnDismissListener(new PopupWindow.OnDismissListener() {
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


        } else if (type == 3) {
            popWindosType = 3;
            View selectSort = inflate(this, R.layout.item_selset_sort, null);
            if (mSortPopwindow == null) {
                mSortPopwindow = new PopupWindow(selectSort, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }


            RelativeLayout selectAll = (RelativeLayout) selectSort.findViewById(R.id.sort_all);
            RelativeLayout selectUp = (RelativeLayout) selectSort.findViewById(R.id.sort_up);
            RelativeLayout selectdown = (RelativeLayout) selectSort.findViewById(R.id.sort_down);
            RelativeLayout selectfree = (RelativeLayout) selectSort.findViewById(R.id.sort_free);
            RelativeLayout selectDistance = (RelativeLayout) selectSort.findViewById(R.id.sort_distance);
            RelativeLayout selectFast = (RelativeLayout) selectSort.findViewById(R.id.sort_fast);
            RelativeLayout selectMuch = (RelativeLayout) selectSort.findViewById(R.id.sort_much);

            selectDistance.setVisibility(View.GONE);
            TextView free = (TextView) selectSort.findViewById(R.id.free);
            TextView fast = (TextView) selectSort.findViewById(R.id.fast);
            if (mLiveType == 1) {
                fast.setText("最快开始");
                selectMuch.setVisibility(View.VISIBLE);
            } else if (mLiveType == 2) {
                fast.setText("报名最多");
                selectMuch.setVisibility(View.GONE);
            }
            free.setText("免费");
            RelativeLayout spaceRelative = (RelativeLayout) selectSort.findViewById(R.id.spaceRelative);

            spaceRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSortPopwindow.dismiss();
                }
            });
            final ImageView imageView1 = (ImageView) selectSort.findViewById(R.id.imageview1);
            final ImageView imageView2 = (ImageView) selectSort.findViewById(R.id.imageview2);
            final ImageView imageView3 = (ImageView) selectSort.findViewById(R.id.imageview3);
            final ImageView imageView4 = (ImageView) selectSort.findViewById(R.id.imageview4);
            final ImageView imageView6 = (ImageView) selectSort.findViewById(R.id.imageview6);
            final ImageView imageView7 = (ImageView) selectSort.findViewById(R.id.imageview7);

            selectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    mParams.put("sortId", "0");
                    mSortPopwindow.dismiss();
                    mRefreshLayout.startRefresh();
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView6.setVisibility(View.INVISIBLE);
                    imageView7.setVisibility(View.INVISIBLE);
                }
            });

            selectUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    mParams.put("sortId", "1");
                    mSortPopwindow.dismiss();
                    mRefreshLayout.startRefresh();
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView6.setVisibility(View.INVISIBLE);
                    imageView7.setVisibility(View.INVISIBLE);
                }
            });

            selectdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    mParams.put("sortId", "2");
                    mSortPopwindow.dismiss();
                    mRefreshLayout.startRefresh();
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView6.setVisibility(View.INVISIBLE);
                    imageView7.setVisibility(View.INVISIBLE);
                }
            });

            selectfree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    mParams.put("sortId", "3");
                    mSortPopwindow.dismiss();
                    mRefreshLayout.startRefresh();
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView6.setVisibility(View.INVISIBLE);
                    imageView7.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);

                }
            });

            selectFast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    if (mLiveType == 1) {
                        mParams.put("sortId", "4");
                    } else {
                        mParams.put("sortId", "5");
                    }
                    mSortPopwindow.dismiss();
                    mRefreshLayout.startRefresh();
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView7.setVisibility(View.INVISIBLE);
                    imageView6.setVisibility(View.VISIBLE);
                }
            });

            /**直播的购买最多*/
            selectMuch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParams.put("sortId", "5");
                    mSortPopwindow.dismiss();
                    mRefreshLayout.startRefresh();
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView6.setVisibility(View.INVISIBLE);
                    imageView7.setVisibility(View.VISIBLE);

                }
            });

            mSortPopwindow.setAnimationStyle(R.style.AnimBottom);
            mSortPopwindow.setFocusable(false);
            mSortPopwindow.update();
            mSortPopwindow.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mSortPopwindow.setBackgroundDrawable(dw);
            mSortPopwindow.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);

            mSortPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
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

    private void getTimeSelect() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < clickPosition.size(); i++) {
            if (clickPosition.get(i).getText().equals("星期一"))
                lists.add("星期一");
            if (clickPosition.get(i).getText().equals("星期二"))
                lists.add("星期二");
            if (clickPosition.get(i).getText().equals("星期三"))
                lists.add("星期三");
            if (clickPosition.get(i).getText().equals("星期四"))
                lists.add("星期四");
            if (clickPosition.get(i).getText().equals("星期五"))
                lists.add("星期五");
            if (clickPosition.get(i).getText().equals("星期六"))
                lists.add("星期六");
            if (clickPosition.get(i).getText().equals("星期日"))
                lists.add("星期日");
        }
        mWeek = "";
        for (int i = 0; i < lists.size(); i++) {
            if (i < lists.size() - 1) {
                mWeek += lists.get(i) + ",";
            } else {
                mWeek += lists.get(i) + "";
            }
        }
        mParams.put("week", mWeek);
        mParams.put("weektime", startTime);
        mRefreshLayout.startRefresh();
    }


    public void setClickListener(TextView[] textview) {
        for (i = 0; i < textview.length; i++) {
            final TextView view = textview[i];

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    if (clickPosition.contains(view)) {
                        clickPosition.remove(view);
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_system_color));
                    } else {
                        clickPosition.add(view);
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_base_color));
                    }
                }
            });

        }
    }


    /**
     * 分类
     */
    private void setClassiFicationData(ListView mainRecyclerView, final RecyclerView moreRecyclerView) {

        final List<LiveBroad.DataBean.TypeListBean> mainLists = new ArrayList<>();
        try {

            mMoreLists = new ArrayList<>();
            mainLists.clear();
            mMoreLists.clear();
            if (mLiveBroad != null)
                mainLists.addAll(mLiveBroad.getData().getTypeList());
            if (mainLists.size() != 0)
                mMoreLists.addAll(mainLists.get(0).getZi());
        } catch (Exception e) {

        }
        setMoreAdapter(moreRecyclerView);


        if (mMainAdapter == null) {
            mMainAdapter = new LiveMainAdapter(LiveBroadcastActivity.this, mainLists);
            mainRecyclerView.setAdapter(mMainAdapter);
        } else {
            mMainAdapter.notifyDataSetChanged();
        }


        mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG++++++", "mainLists的大小" + mainLists.size());

                if (mainLists.size() <= 1) {

                    return;
                }
                mMainAdapter.setSelectItem(position);
                popWindosType = 0;
                mMoreLists.clear();

                mMoreLists.addAll(mainLists.get(position).getZi());


                //                mMoreAdapter.notifyDataSetChanged();
                mMainAdapter.notifyDataSetChanged();
                setMoreAdapter(moreRecyclerView);


            }
        });


    }

    private void setMoreAdapter(RecyclerView moreRecyclerView) {


        mMoreAdapter = new BaseRecyclerAdapter<LiveBroad.DataBean.TypeListBean.ZiBean>(LiveBroadcastActivity.this, mMoreLists, R.layout.item_select_classfication) {
            @Override
            public void convert(final BaseRecyclerHolder holder, final LiveBroad.DataBean.TypeListBean.ZiBean item, final int position, boolean isScrolling) {
                holder.setText(R.id.classKInd, item.getName());
                final RecyclerView myRecyclerView = holder.getView(R.id.my_recycler_view);
                mZiLists = new ArrayList<>();

                mZiLists.addAll(mMoreLists.get(position).getZid());
                if (mZiLists.size() != 0) {
                    mZiLists.add(0, new LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean("全部", item.getZid().get(0).getPid(), "0"));
                }

                holder.getView(R.id.more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myRecyclerView.getVisibility() == View.GONE) {
                            myRecyclerView.setVisibility(View.VISIBLE);
                            AnimUtil.FlipAnimatorXViewShow(holder.getView(R.id.gone), holder.getView(R.id.show), 100);
                        } else {
                            myRecyclerView.setVisibility(View.GONE);
                            AnimUtil.FlipAnimatorXViewShow(holder.getView(R.id.show), holder.getView(R.id.gone), 100);
                        }
                    }
                });


                setClassKind(myRecyclerView, mZiLists);
            }
        };
        moreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moreRecyclerView.setAdapter(mMoreAdapter);


    }

    /**
     * 年级
     */
    private void setClassKind(RecyclerView myRecyclerView, final List<LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean> moreLists) {

        for (int i = 0; i < moreLists.size(); i++) {

            if (i % 3 == 0 && i >= 3) {
                moreLists.add(i, new LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean("1", "0", "0"));
            }
        }
        classFicationAdapter = new BaseRecyclerAdapter<LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean>(LiveBroadcastActivity.this, moreLists, R.layout.item_classfiaction_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean item, int position, boolean isScrolling) {

                holder.setText(R.id.courseKind, item.getName());
                if (item.getName().equals("1")) {
                    holder.getView(R.id.item).setVisibility(View.INVISIBLE);
                } else {
                    holder.getView(R.id.item).setVisibility(View.VISIBLE);
                }
            }
        };

        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myRecyclerView.setAdapter(classFicationAdapter);
        classFicationAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                popWindosType = 0;
                if (moreLists.get(position).getName().equals("1")) {
                    return;
                }

                mClassification.setText(moreLists.get(position).getName());

                RelativeLayout mView = (RelativeLayout) view.findViewById(item);
                if (clickView.contains(mView)) {

                    for (int i = 0; i < clickView.size(); i++) {
                        clickView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_system_color));
                    }
                    clickView.clear();
                } else {
                    /**在点击后 将所有的item都设置成system_color  然后再清除集合 添加新的点击的item到集合中 将第一个设置成Base_color*/
                    for (int i = 0; i < clickView.size(); i++) {
                        clickView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_system_color));
                    }
                    clickView.clear();
                    clickView.add(0, mView);
                    clickView.get(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_base_color));
                }
                mCouresKindPopwindow.dismiss();
                mParams.put("typeId", moreLists.get(position).getPid());
                mParams.put("typeZid", moreLists.get(position).getTid());
                mRefreshLayout.startRefresh();

            }
        });
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
    public void onBackPressed() {
        if (mCouresKindPopwindow != null && mCouresKindPopwindow.isShowing()) {
            mCouresKindPopwindow.dismiss();
        } else if (mselectTimePopwindos != null && mselectTimePopwindos.isShowing()) {
            mselectTimePopwindos.dismiss();
        } else if (mSortPopwindow != null && mSortPopwindow.isShowing()) {
            mSortPopwindow.dismiss();
        } else if (mSearchResultRecyclerView.getVisibility() == View.VISIBLE) {

            mSearchResultRecyclerView.setVisibility(View.GONE);
            mLienarLayout.setVisibility(View.VISIBLE);
            mLiveBroadCastRecylcerView.setVisibility(View.VISIBLE);

        } else {
            super.onBackPressed();
        }


    }

    public void clearResult() {
        mLiveBroadcastList.clear();
        if (liveCastAdapter != null)
            liveCastAdapter.notifyDataSetChanged();
        liveCastAdapter = null;
    }

}
