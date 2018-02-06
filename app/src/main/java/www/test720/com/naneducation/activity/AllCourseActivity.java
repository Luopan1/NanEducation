package www.test720.com.naneducation.activity;

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
import www.test720.com.naneducation.adapter.AllCourseAdapter;
import www.test720.com.naneducation.adapter.AllSearchResultAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.adapter.PopSelectAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.AllCourse;
import www.test720.com.naneducation.bean.AllCourseSearch;
import www.test720.com.naneducation.bean.Grade;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.AnimUtil;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.view.ClassFicationItemDecortion;
import www.test720.com.naneducation.view.SpaceItemDecoration;

import static android.view.View.inflate;
import static www.test720.com.naneducation.R.id.item;
import static www.test720.com.naneducation.R.id.moreRecyclerView;


public class AllCourseActivity extends BaseToolbarActivity {


    @BindView(R.id.fanhui)
    RelativeLayout mFanhui;
    @BindView(R.id.searchEdit_text)
    EditText mSearchEditText;
    @BindView(R.id.titleLinear)
    LinearLayout mTitleLinear;
    @BindView(R.id.classification)
    TextView mClassification;
    @BindView(R.id.downArrow1)
    ImageView mDownArrow1;
    @BindView(R.id.downArrow1_1)
    ImageView mDownArrow11;
    @BindView(R.id.coures)
    LinearLayout mCoures;
    @BindView(R.id.sort)
    TextView mSort;
    @BindView(R.id.downArrow3)
    ImageView mDownArrow3;
    @BindView(R.id.downArrow1_3)
    ImageView mDownArrow13;
    @BindView(R.id.sortLin)
    LinearLayout mSortLin;
    @BindView(R.id.lienarLayout)
    LinearLayout mLienarLayout;
    @BindView(R.id.searchResultRecyclerView)
    RecyclerView mSearchResultRecyclerView;
    @BindView(R.id.liveBroadCastRecylcerView)
    RecyclerView mLiveBroadCastRecylcerView;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;


    private List<AllCourse.DataBean.ListBean> mLiveBroadcastList = new ArrayList<>();
    private List<AllCourse.DataBean.MuenBean> mainLists = new ArrayList<>();
    List<AllCourse.DataBean.MuenBean.ZiBean> moreLists = new ArrayList<>();
    List<AllCourse.DataBean.MuenBean.ZiBean.ZidBean> classLists = new ArrayList<>();

    BaseRecyclerAdapter<AllCourse.DataBean.ListBean> liveCastAdapter;
    private int TYPE = 0;
    private int popWindosType = 0;
    private PopupWindow mCouresKindPopwindow;
    private PopupWindow mSortPopwindow;
    List<TextView> clickPosition = new ArrayList<>();
    List<RelativeLayout> clickView = new ArrayList<>();
    HttpParams mParams = new HttpParams();
    private double mType = 1;
    private AllCourse mAllCourse;
    private String mTypeID = "1";
    private ArrayList<Grade> mActivitySortLists;
    private PopSelectAdapter activitySortAdapter;
    private AllSearchResultAdapter SearchAdapter;
    private BaseRecyclerAdapter<AllCourse.DataBean.MuenBean.ZiBean.ZidBean> mClassFicationAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_all_course;
    }


    public void getData() {
        mParams.put("cityId", Constans.city_id);
        mSubscription = mHttpUtils.getData(UrlFactory.courseList, mParams, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onNext(String s) {
                mLiveBroadcastList.clear();
                mainLists.clear();

                Gson gson = new Gson();
                mAllCourse = gson.fromJson(s, AllCourse.class);
                mLiveBroadcastList.addAll(mAllCourse.getData().getList());
                mainLists.addAll(mAllCourse.getData().getMuen());

                setAdapter(mLiveBroadcastList);
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void setAdapter(final List<AllCourse.DataBean.ListBean> liveBroadcastList) {
        if (liveCastAdapter == null) {
            liveCastAdapter = new BaseRecyclerAdapter<AllCourse.DataBean.ListBean>(this, liveBroadcastList, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, AllCourse.DataBean.ListBean item, int position, boolean isScrolling) {
                    mSizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);
                    if (item.getIs_free().equals("0")) {
                        holder.setText(R.id.courseKind, "套课");
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.taoke));
                    } else {
                        holder.setText(R.id.courseKind, "免费");
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    }


                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }


                    TextView money = holder.getView(R.id.money);
                    if (item.getIs_free().equals("1")) {
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

                    if (item.getTc_name().trim().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }

                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getC_logo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.studyCourseName, item.getC_name());
                    holder.setText(R.id.money, item.getC_price());

                }
            };
            mLiveBroadCastRecylcerView.setLayoutManager(new GridLayoutManager(this, 2));
            mLiveBroadCastRecylcerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            mLiveBroadCastRecylcerView.setAdapter(liveCastAdapter);


            liveCastAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {

                    Bundle bundle = new Bundle();
                    bundle.putString("title", liveBroadcastList.get(position).getC_name());
                    bundle.putInt("type", 3);
                    bundle.putString("id", liveBroadcastList.get(position).getCid());
                    //                    bundle.putString("path",liveBroadcastList.get(position).get);
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
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(AllCourseActivity.this.getCurrentFocus().getWindowToken(),
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
        params.put("cityId", Constans.city_id);
        params.put("name", mSearchEditText.getText().toString().trim());
        mSubscription = mHttpUtils.getData(UrlFactory.searchCourse, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                AllCourseSearch allCourseSearch = gson.fromJson(s, AllCourseSearch.class);

                List<AllCourseSearch.DataBean.CourseBean> courseList = new ArrayList<>();
                List<AllCourseSearch.DataBean.LiveBean> livelists = new ArrayList<>();
                List<AllCourseSearch.DataBean.LuboBean> luBoList = new ArrayList<>();


                livelists.addAll(allCourseSearch.getData().getLive());
                courseList.addAll(allCourseSearch.getData().getCourse());
                luBoList.addAll(allCourseSearch.getData().getLubo());
                doSearch(livelists, courseList, luBoList);
            }
        });

    }

    private void doSearch(List<AllCourseSearch.DataBean.LiveBean> livelists, List<AllCourseSearch.DataBean.CourseBean> courseList, List<AllCourseSearch.DataBean.LuboBean> luBoList) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(3);
        integerList.add(1);
        integerList.add(2);
        SearchAdapter = new AllSearchResultAdapter(this, integerList, livelists, luBoList, courseList);
        mSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultRecyclerView.setAdapter(SearchAdapter);
    }

    @Override
    protected void initView() {
        if (isNetworkAvailable(this))
            showLoadingDialog();
        mSearchResultRecyclerView.setVisibility(View.GONE);
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
        isShowBackImage = false;
        isShowToolBar = false;
    }


    @OnClick({R.id.fanhui, R.id.coures, R.id.sortLin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.coures:
                TYPE = 1;
                if (TYPE == popWindosType) {
                    mCouresKindPopwindow.dismiss();
                    popWindosType = 0;
                } else {
                    showPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow1, mDownArrow11, 10);
                    mClassification.setTextColor(getResources().getColor(R.color.base_color));
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
        }
    }

    /**
     * 分类
     */
    private void setClassiFicationData(ListView mainRecyclerView, final RecyclerView moreRecyclerView) {


        final AllCourseAdapter mainAdapter = new AllCourseAdapter(AllCourseActivity.this, mainLists);

        mainRecyclerView.setAdapter(mainAdapter);
        mainLists.clear();
        moreLists.clear();
        mainLists.addAll(mAllCourse.getData().getMuen());

        if (mainLists.size() <= 0) {
            return;
        }
        moreLists.addAll(mainLists.get(0).getZi());

        setMoreAdapter(moreRecyclerView);


        mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
                moreLists.clear();
                moreLists.addAll(mainLists.get(position).getZi());
                popWindosType = 0;
                setMoreAdapter(moreRecyclerView);
            }
        });
        mainRecyclerView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }

    public void setMoreAdapter(RecyclerView moreRecyclerView) {

        BaseRecyclerAdapter<AllCourse.DataBean.MuenBean.ZiBean> moreAdapter = new BaseRecyclerAdapter<AllCourse.DataBean.MuenBean.ZiBean>(AllCourseActivity.this, moreLists, R.layout.item_select_classfication) {
            @Override
            public void convert(final BaseRecyclerHolder holder, final AllCourse.DataBean.MuenBean.ZiBean item, int position, boolean isScrolling) {
                holder.setText(R.id.classKInd, item.getName());
                final RecyclerView myRecyclerView = holder.getView(R.id.my_recycler_view);

                classLists = new ArrayList<>();
                classLists.addAll(moreLists.get(position).getZid());
                if (classLists.size() != 0) {
                    classLists.add(0, new AllCourse.DataBean.MuenBean.ZiBean.ZidBean("全部", classLists.get(0).getPid(), "0"));
                }
                setClassKind(myRecyclerView, classLists);

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


            }
        };
        moreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moreRecyclerView.setAdapter(moreAdapter);
    }

    /**
     * 年级
     */
    private void setClassKind(RecyclerView myRecyclerView, final List<AllCourse.DataBean.MuenBean.ZiBean.ZidBean> moreLists) {

        for (int i = 0; i < moreLists.size(); i++) {
            if (i % 3 == 0 && i >= 3) {
                moreLists.add(i, new AllCourse.DataBean.MuenBean.ZiBean.ZidBean("1", "0", "0"));
            }
        }
        mClassFicationAdapter = new BaseRecyclerAdapter<AllCourse.DataBean.MuenBean.ZiBean.ZidBean>(AllCourseActivity.this, moreLists, R.layout.item_classfiaction_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, AllCourse.DataBean.MuenBean.ZiBean.ZidBean item, int position, boolean isScrolling) {
                holder.setText(R.id.courseKind, item.getName());
                if (item.getName().equals("1")) {
                    holder.getView(R.id.item).setVisibility(View.INVISIBLE);
                } else {
                    holder.getView(R.id.item).setVisibility(View.VISIBLE);
                }
            }
        };
        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myRecyclerView.addItemDecoration(new ClassFicationItemDecortion(DensityUtil.dip2px(AllCourseActivity.this, 20), DensityUtil.dip2px(AllCourseActivity.this, 10)));
        myRecyclerView.setAdapter(mClassFicationAdapter);


        mClassFicationAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
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
            mMainRecyclerView = (ListView) classificationView.findViewById(R.id.mainRecyclerView);
            mMoreRecyclerView = (RecyclerView) classificationView.findViewById(moreRecyclerView);
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
                    mSort.setTextColor(getResources().getColor(R.color.textcolor));
                    mClassification.setGravity(Gravity.CENTER);
                    if (type == 1) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow11, mDownArrow1, 10);
                    } else if (type == 2) {
                    } else if (type == 3) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow13, mDownArrow3, 10);
                    }

                }
            });


        } else if (type == 2) {
            popWindosType = 2;

        } else if (type == 3) {
            popWindosType = 3;
            View selectSort = inflate(this, R.layout.pop_group_item_activity_kin, null);
            if (mSortPopwindow == null) {
                mSortPopwindow = new PopupWindow(selectSort, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
                mActivitySortLists = new ArrayList<>();
                mActivitySortLists.add(new Grade(0, "全部"));
                mActivitySortLists.add(new Grade(1, "免费"));
                mActivitySortLists.add(new Grade(2, "价格由低到高"));
                mActivitySortLists.add(new Grade(3, "价格由高到低"));
                mActivitySortLists.add(new Grade(4, "报名最多"));
                mActivitySortLists.add(new Grade(5, "推荐"));
                activitySortAdapter = new PopSelectAdapter(this, mActivitySortLists);
            }

            ListView activityKindListView = (ListView) selectSort.findViewById(R.id.activityListView);
            activityKindListView.setAdapter(activitySortAdapter);

            RelativeLayout spaceRelative = (RelativeLayout) selectSort.findViewById(R.id.spaceRelative);

            spaceRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindosType = 0;
                    mSortPopwindow.dismiss();
                }
            });
            activityKindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popWindosType = 0;
                    mParams.put("sort", mActivitySortLists.get(position).getId());
                    activitySortAdapter.setSelectItem(position);
                    activitySortAdapter.notifyDataSetChanged();
                    mRefreshLayout.startRefresh();
                    mSortPopwindow.dismiss();
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
                    mSort.setTextColor(getResources().getColor(R.color.textcolor));
                    mClassification.setGravity(Gravity.CENTER);
                    if (type == 1) {
                        AnimUtil.FlipAnimatorXViewShow(mDownArrow11, mDownArrow1, 10);
                    } else if (type == 2) {

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
}
