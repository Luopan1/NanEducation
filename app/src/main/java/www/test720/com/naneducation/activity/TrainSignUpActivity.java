package www.test720.com.naneducation.activity;

import android.graphics.drawable.ColorDrawable;
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
import www.test720.com.naneducation.MapActivity;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.SearchResultAdapter;
import www.test720.com.naneducation.adapter.TrainAdapter;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Classfication;
import www.test720.com.naneducation.bean.SchoolSelect;
import www.test720.com.naneducation.bean.TrainBean;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.AnimUtil;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.utils.ItemAnimatorFactory;
import www.test720.com.naneducation.view.ClassFicationItemDecortion;
import www.test720.com.naneducation.view.CustomDatePicker;

import static android.view.View.inflate;


public class TrainSignUpActivity extends BaseToolbarActivity {


    @BindView(R.id.fanhui)
    RelativeLayout mFanhui;
    @BindView(R.id.searchEdit_text)
    EditText mSearchEditText;
    @BindView(R.id.titleLinear)
    RelativeLayout mTitleLinear;
    @BindView(R.id.classification)
    TextView mClassification;
    @BindView(R.id.downArrow1)
    ImageView mDownArrow1;
    @BindView(R.id.downArrow1_1)
    ImageView mDownArrow11;
    @BindView(R.id.coures)
    LinearLayout mCoures;
    @BindView(R.id.schooltime)
    TextView mSchooltime;
    @BindView(R.id.downArrow2)
    ImageView mDownArrow2;
    @BindView(R.id.downArrow1_2)
    ImageView mDownArrow12;
    @BindView(R.id.study)
    LinearLayout mStudy;
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
    @BindView(R.id.trainSingUpRecylcerView)
    RecyclerView mTrainSingUpRecylcerView;
    @BindView(R.id.map_Relative)
    RelativeLayout mMapRelative;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;

    private CustomDatePicker timePicker;
    private CustomDatePicker endPicker;
    private String time;
    private String date;
    private TextView mStartTime;
    private TextView mEndtTime;
    private int popWindosType = 0;
    private String startTime = "00:00";
    private String endTime = "23:59";
    BaseRecyclerAdapter<TrainBean.DataBean.ListBean> tranSignUpAdapter;

    BaseRecyclerAdapter<SchoolSelect.DataBean.ListBean> mSelectSignup;
    SearchResultAdapter SearchAdapter;
    View selectStudyTime;
    List<TextView> clickPosition = new ArrayList<>();
    List<RelativeLayout> clickView = new ArrayList<>();
    private List<TrainBean.DataBean.ListBean> schoolSignLists = new ArrayList<>();
    int i;
    private int TYPE = 0;
    private PopupWindow mCouresKindPopwindow;
    private PopupWindow mselectTimePopwindos;
    private PopupWindow mSortPopwindow;
    int mCurrentPage = 1;
    int mTotalPage = 0;
    int mType = 0;

    List<TrainBean.DataBean.TypelistBean> mainLists = new ArrayList<>();
    List<SchoolSelect.DataBean.ListBean> selectLists = new ArrayList<>();
    HttpParams mParams = new HttpParams();
    private String mWeek;
    private BaseRecyclerAdapter<TrainBean.DataBean.TypelistBean.ZiBean> mMoreAdapter;
    private TrainAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_train_sign_up;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        if (mType == 0) {
            HttpParams params = new HttpParams();
            params.put("cityId", Constans.city_id);
            params.put("page", mCurrentPage);
            params.put("long", Constans.longitude);
            params.put("lat", Constans.lat);
            mSubscription = mHttpUtils.getData(UrlFactory.trainList, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                    mRefreshLayout.finishRefreshing();
                    ShowToast(e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    if (mCurrentPage == 1) {
                        schoolSignLists.clear();
                    }
                    mainLists.clear();
                    Gson gson = new Gson();
                    TrainBean bean = gson.fromJson(s, TrainBean.class);
                    schoolSignLists.addAll(bean.getData().getList());
                    mTotalPage = bean.getData().getTotal();
                    mainLists.addAll(bean.getData().getTypelist());
                    setAdapter();
                }
            });
        } else if (mType == 1) {
            mParams.put("cityId", Constans.city_id);
            mParams.put("long", Constans.longitude);
            mParams.put("lat", Constans.lat);
            mSubscription = mHttpUtils.getData(UrlFactory.trainScreen, mParams, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                @Override
                public void onStart() {
                    mRefreshLayout.startRefresh();
                }

                @Override
                public void onCompleted() {
                    onStopLoad();
                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                    mRefreshLayout.finishRefreshing();
                }

                @Override
                public void onNext(String s) {
                    Gson gson = new Gson();
                    if (mCurrentPage == 1) {
                        selectLists.clear();
                    }
                    SchoolSelect schoolSelect = gson.fromJson(s, SchoolSelect.class);
                    selectLists.addAll(schoolSelect.getData().getList());
                    mTotalPage = schoolSelect.getData().getTotal();
                    setSelectAdapter();
                }
            });
        } else if (mType == 3) {
            doSerach();
        }

    }

    public void setSelectAdapter() {
        if (mSelectSignup == null) {
            mSelectSignup = new BaseRecyclerAdapter<SchoolSelect.DataBean.ListBean>(this, selectLists, R.layout.item_sign_up_item) {
                @Override
                public void convert(final BaseRecyclerHolder holder, final SchoolSelect.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.schoolImage, UrlFactory.baseImageUrl + item.getTrain_logo());
                    holder.setText(R.id.schoolName, item.getTrain_name());
                    holder.setText(R.id.schoolDescription, item.getTrain_title());
                    holder.setText(R.id.distance, item.getDistance() + "km");
                    holder.getView(R.id.schoolDistance).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShowToast(item.getDistance() + "km");
                        }
                    });
                }
            };
            mTrainSingUpRecylcerView.setLayoutManager(new LinearLayoutManager(this));
            mTrainSingUpRecylcerView.setItemAnimator(ItemAnimatorFactory.slidein());
            mTrainSingUpRecylcerView.setAdapter(mSelectSignup);

            mSelectSignup.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", selectLists.get(position).getTrain_id());
                    jumpToActivity(TrainSchoolInfoActivity.class, bundle, false);
                }
            });

        } else {
            mSelectSignup.notifyDataSetChanged();
        }
    }

    private void setAdapter() {
        if (tranSignUpAdapter == null) {

            LogUtils.e("setAdapter");

            tranSignUpAdapter = new BaseRecyclerAdapter<TrainBean.DataBean.ListBean>(this, schoolSignLists, R.layout.item_sign_up_item) {
                @Override
                public void convert(final BaseRecyclerHolder holder, final TrainBean.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.schoolImage, UrlFactory.baseImageUrl + item.getTrain_logo());
                    holder.setText(R.id.schoolName, item.getTrain_name());
                    holder.setText(R.id.schoolDescription, item.getTrain_title());
                    holder.setText(R.id.distance, item.getDistance() + "km");
                    holder.getView(R.id.schoolDistance).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ShowToast(item.getDistance() + "km");
                        }
                    });
                }
            };
            mTrainSingUpRecylcerView.setLayoutManager(new LinearLayoutManager(this));
            mTrainSingUpRecylcerView.setItemAnimator(ItemAnimatorFactory.slidein());
            mTrainSingUpRecylcerView.setAdapter(tranSignUpAdapter);

            tranSignUpAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", schoolSignLists.get(position).getTrain_id());
                    jumpToActivity(TrainSchoolInfoActivity.class, bundle, false);
                }
            });
        } else {
            tranSignUpAdapter.notifyDataSetChanged();
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
                    getData();
                } else {
                    onStopLoad();
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
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(TrainSignUpActivity.this.getCurrentFocus().getWindowToken(),
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
                    mType = 0;
                    mCurrentPage = 1;
                    clearResult();
                    mLienarLayout.setVisibility(View.VISIBLE);
                } else {
                    schoolSignLists.clear();
                    setAdapter();
                }
            }
        });

    }

    private void doSerach() {

        mLienarLayout.setVisibility(View.GONE);

        HttpParams params = new HttpParams();
        params.put("name", mSearchEditText.getText().toString().trim());
        params.put("cityId", Constans.city_id);
        params.put("long", Constans.longitude);
        params.put("lat", Constans.lat);
        mSubscription = mHttpUtils.getData(UrlFactory.searchTrain, params, 5).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
                mCurrentPage = 1;
                showLoadingDialog();
                clearResult();
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mType = 3;
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                cancleLoadingDialog();
                ShowToast(e.getMessage());
                onStopLoad();
            }

            @Override
            public void onNext(String s) {
                cancleLoadingDialog();
                Gson gson = new Gson();
                if (mCurrentPage == 1) {
                    selectLists.clear();
                }
                SchoolSelect schoolSelect = gson.fromJson(s, SchoolSelect.class);
                selectLists.addAll(schoolSelect.getData().getList());
                mTotalPage = schoolSelect.getData().getTotal();
                setSelectAdapter();
            }
        });
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
    protected void initView() {
        mTrainSingUpRecylcerView.setVisibility(View.VISIBLE);

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
        }, time, "3000-12-31 23:59");//"2027-12-31 23:59"
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
                        endTime = time;
                    }
                }
            }
        }, time, "3000-12-31 23:59");//"2027-12-31 23:59"
        endPicker.showSpecificTime(true);
        endPicker.setIsLoop(true);

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

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }


    @OnClick({R.id.fanhui, R.id.coures, R.id.study, R.id.sortLin, R.id.map_Relative})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.map_Relative:
                // TODO: 2017/10/18 百度地图
                Bundle bundle = new Bundle();
                bundle.putInt("mapKind", 2);
                jumpToActivity(MapActivity.class, bundle, false);
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
            View classificationView = inflate(this, R.layout.pop_train_classification, null);// 得到加载view
            ListView mMainRecyclerView;
            RecyclerView mMoreRecyclerView;
            // 创建自定义样式dialog
            if (mCouresKindPopwindow == null) {
                mCouresKindPopwindow = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }
            mMainRecyclerView = (ListView) classificationView.findViewById(R.id.mainRecyclerView);
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
                selectStudyTime = inflate(this, R.layout.pop_select_study_time, null);
            }
            if (mselectTimePopwindos == null) {

                mselectTimePopwindos = new PopupWindow(selectStudyTime, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }

            mStartTime = (TextView) selectStudyTime.findViewById(R.id.start_time);
            mEndtTime = (TextView) selectStudyTime.findViewById(R.id.end_time);
            RelativeLayout spaceRelative = (RelativeLayout) selectStudyTime.findViewById(R.id.spaceRelative);

            TextView all = (TextView) selectStudyTime.findViewById(R.id.all);
            TextView commit = (TextView) selectStudyTime.findViewById(R.id.commit);
            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < clickPosition.size(); i++) {
                        TextView textView1 = clickPosition.get(i);
                        textView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_system_color));
                    }

                    mStartTime.setText("00:00");
                    mEndtTime.setText("23:59");
                    startTime = "00:00";
                    endTime = "23:59";

                    clickPosition.clear();
                    getTimeSelect();
                    mselectTimePopwindos.dismiss();
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getTimeSelect();

                    mselectTimePopwindos.dismiss();

                }
            });

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
            spaceRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mselectTimePopwindos.dismiss();
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
            RelativeLayout sortDistance = (RelativeLayout) selectSort.findViewById(R.id.sort_distance);

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
            final ImageView imageView5 = (ImageView) selectSort.findViewById(R.id.imageview5);


            selectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSortOrder("");
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView5.setVisibility(View.INVISIBLE);
                }
            });

            selectUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSortOrder("2");
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView5.setVisibility(View.INVISIBLE);
                }
            });

            selectdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSortOrder("3");
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView5.setVisibility(View.INVISIBLE);
                }
            });

            selectfree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSortOrder("4");
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    imageView5.setVisibility(View.INVISIBLE);
                }
            });
            sortDistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSortOrder("1");
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    imageView5.setVisibility(View.VISIBLE);
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

    public void setSortOrder(String type) {
        mParams.put("ordertype", type);
        mType = 1;
        clearResult();
        mCurrentPage = 1;
        mSortPopwindow.dismiss();
        mRefreshLayout.startRefresh();
    }

    private void getTimeSelect() {
        List<Integer> lists = new ArrayList<>();
        for (int i = 0; i < clickPosition.size(); i++) {
            if (clickPosition.get(i).getText().equals("星期一"))
                lists.add(1);
            if (clickPosition.get(i).getText().equals("星期二"))
                lists.add(2);
            if (clickPosition.get(i).getText().equals("星期三"))
                lists.add(3);
            if (clickPosition.get(i).getText().equals("星期四"))
                lists.add(4);
            if (clickPosition.get(i).getText().equals("星期五"))
                lists.add(5);
            if (clickPosition.get(i).getText().equals("星期六"))
                lists.add(6);
            if (clickPosition.get(i).getText().equals("星期日"))
                lists.add(7);
        }
        mWeek = "";
        for (int i = 0; i < lists.size(); i++) {
            if (i < lists.size() - 1) {
                mWeek += lists.get(i).toString() + ",";
            } else {
                mWeek += lists.get(i).toString();
            }
        }

        mParams.put("week", mWeek);
        mParams.put("stratime", startTime);
        mParams.put("endtime", endTime);
        mType = 1;
        mCurrentPage = 1;
        clearResult();
        mRefreshLayout.startRefresh();
    }


    public void setClickListener(TextView[] textview) {
        for (i = 0; i < textview.length; i++) {
            final TextView view = textview[i];

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
    private void setClassiFicationData(ListView mainRecyclerView, RecyclerView moreRecyclerView) {

        final List<TrainBean.DataBean.TypelistBean.ZiBean> moreLists = new ArrayList<>();
        if (mainLists.size() > 0) {
            moreLists.addAll(mainLists.get(0).getZi());
            mParams.put("type_id", mainLists.get(0).getT_id());
        }
        if (mAdapter == null) {
            mAdapter = new TrainAdapter(this, mainLists);
            mainRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
                mParams.put("type_id", mainLists.get(position).getT_id());
                moreLists.clear();
                moreLists.addAll(mainLists.get(position).getZi());
                mMoreAdapter.notifyDataSetChanged();
            }
        });

        mainRecyclerView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //setClassKind(myRecyclerView);
        if (mMoreAdapter == null) {
            mMoreAdapter = new BaseRecyclerAdapter<TrainBean.DataBean.TypelistBean.ZiBean>(TrainSignUpActivity.this, moreLists, R.layout.item_select_classfication) {
                @Override
                public void convert(BaseRecyclerHolder holder, TrainBean.DataBean.TypelistBean.ZiBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.classKInd, item.getName());
                    holder.getView(R.id.more).setVisibility(View.GONE);
                    holder.getView(R.id.my_recycler_view).setVisibility(View.GONE);
                    //setClassKind(myRecyclerView);
                }
            };
            moreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            moreRecyclerView.setAdapter(mMoreAdapter);

            mMoreAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    popWindosType = 0;
                    mParams.put("type_zid", moreLists.get(position).getT_id());
                    mCurrentPage = 1;
                    mType = 1;
                    clearResult();
                    mRefreshLayout.startRefresh();
                    mCouresKindPopwindow.dismiss();
                    mClassification.setText(moreLists.get(position).getName());

                }
            });
        }

    }

    /**
     * 年级
     */
    private void setClassKind(RecyclerView myRecyclerView) {
        final List<Classfication> classLists = new ArrayList<>();
        classLists.add(new Classfication(0, "全部"));
        classLists.add(new Classfication(0, "一年级"));
        classLists.add(new Classfication(0, "二年级"));
        classLists.add(new Classfication(0, "三年级"));
        classLists.add(new Classfication(0, "四年级"));
        classLists.add(new Classfication(0, "五年级"));
        classLists.add(new Classfication(0, "六年级"));
        classLists.add(new Classfication(0, "七年级"));
        classLists.add(new Classfication(0, "八年级"));
        classLists.add(new Classfication(0, "九年级"));
        classLists.add(new Classfication(0, "十年级"));
        classLists.add(new Classfication(0, "十一年级"));
        classLists.add(new Classfication(0, "十二年级"));
        for (int i = 0; i < classLists.size(); i++) {

            if (i % 3 == 0 && i >= 3) {
                classLists.add(i, new Classfication(0, "1"));
            }
        }

        BaseRecyclerAdapter<Classfication> classFicationAdapter = new BaseRecyclerAdapter<Classfication>(TrainSignUpActivity.this, classLists, R.layout.item_classfiaction_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, Classfication item, int position, boolean isScrolling) {
                holder.setText(R.id.courseKind, item.getClassName());
                if (item.getClassName().equals("1")) {
                    holder.getView(R.id.item).setVisibility(View.INVISIBLE);
                }
            }
        };
        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myRecyclerView.addItemDecoration(new ClassFicationItemDecortion(DensityUtil.dip2px(TrainSignUpActivity.this, 20), DensityUtil.dip2px(TrainSignUpActivity.this, 10)));
        myRecyclerView.setAdapter(classFicationAdapter);


        classFicationAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                mClassification.setText(classLists.get(position).getClassName());
                RelativeLayout mView = (RelativeLayout) view.findViewById(R.id.item);

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

                //                mCouresKindPopwindow.dismiss();
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
        } else {
            super.onBackPressed();
        }
    }

    public void clearResult() {
        if (mType == 1) {
            if (schoolSignLists != null)
                schoolSignLists.clear();
            if (tranSignUpAdapter != null)
                tranSignUpAdapter.notifyDataSetChanged();
            tranSignUpAdapter = null;
        } else if (mType == 0) {
            if (selectLists != null)
                selectLists.clear();
            if (mSelectSignup != null)
                mSelectSignup.notifyDataSetChanged();
            mSelectSignup = null;

            if (tranSignUpAdapter != null) {
                tranSignUpAdapter = null;
            }
        }

    }

}
