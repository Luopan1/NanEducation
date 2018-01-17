package www.test720.com.naneducation.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import www.test720.com.naneducation.MapActivity;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.adapter.PopSchoolSortAdapter;
import www.test720.com.naneducation.adapter.PopSchoolXZAdapter;
import www.test720.com.naneducation.adapter.PopSelectAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Grade;
import www.test720.com.naneducation.bean.School;
import www.test720.com.naneducation.bean.SchoolChange;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.AnimUtil;

import static android.view.View.inflate;

public class SchoolSignUpActivity extends BaseToolbarActivity {


    private int TYPE = 0;
    @BindView(R.id.fanhui)
    RelativeLayout mFanhui;
    @BindView(R.id.searchEdit_text)
    EditText mSearchEditText;
    @BindView(R.id.map_Relative)
    RelativeLayout mMapRelative;
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
    BaseRecyclerAdapter<School.DataBean.ListBean> schoolAdapter;
    int mCurrentPage = 1;
    int mTotalPage;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    List<School.DataBean.ListBean> schoolLists = new ArrayList<>();
    private PopupWindow mCouresKindPopwindow;
    private PopupWindow mSchoolXZPopwindow;
    private PopupWindow mSortPopwindow;
    private List<School.DataBean.TypelistBean> mainLists = new ArrayList<>();
    private List<School.DataBean.NaturelistBean> sencodLists = new ArrayList<>();
    private List<Grade> ThirdLists = new ArrayList<>();
    private int popWindosType = 0;
    private PopSelectAdapter activitySortAdapter;
    public int dataType = 1;
    HttpParams mParams = new HttpParams();
    private PopSchoolSortAdapter mAdaper;
    private PopSchoolXZAdapter mNaturelistBeanCommonAdaper;
    List<SchoolChange.DataBean.ListBean> mseletLists = new ArrayList<>();
    BaseRecyclerAdapter<SchoolChange.DataBean.ListBean> schoolSellectAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_train_sign_up;
    }

    @Override
    protected void initData() {

    }

    protected void getData() {
        if (dataType == 1) {
            HttpParams params = new HttpParams();
            params.put("cityId", Constans.city_id);
            params.put("long", Constans.longitude);
            params.put("lat", Constans.lat);
            params.put("page", mCurrentPage);
            mSubscription = mHttpUtils.getData(UrlFactory.schoolList, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                @Override
                public void onStart() {

                }

                @Override
                public void onCompleted() {
                    onStopLoad();
                    mSubscription.unsubscribe();
                    cancleLoadingDialog();
                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                    cancleLoadingDialog();
                    onStopLoad();
                }

                @Override
                public void onNext(String s) {
                    mainLists.clear();
                    sencodLists.clear();
                    if (mCurrentPage == 1) {
                        schoolLists.clear();
                    }
                    Gson gson = new Gson();
                    School school = gson.fromJson(s, School.class);
                    schoolLists.addAll(school.getData().getList());
                    mTotalPage = school.getData().getTotal();
                    setAdapter();
                    mainLists.addAll(school.getData().getTypelist());
                    mainLists.add(0, new School.DataBean.TypelistBean("全部", "0"));
                    sencodLists.addAll(school.getData().getNaturelist());
                    sencodLists.add(0, new School.DataBean.NaturelistBean("全部", "0"));
                }
            });
        } else if (dataType == 2) {
            mParams.put("cityId", Constans.city_id);
            mParams.put("long", Constans.longitude);
            mParams.put("lat", Constans.lat);
            mSubscription = mHttpUtils.getData(UrlFactory.searchSchool, mParams, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

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
                    Gson gson = new Gson();
                    SchoolChange schoolChange = gson.fromJson(s, SchoolChange.class);

                    if (mCurrentPage == 1) {
                        mseletLists.clear();
                    }
                    mseletLists.addAll(schoolChange.getData().getList());
                    mTotalPage = schoolChange.getData().getTotal();
                    setSeletcAdapter();
                }
            });

        } else if (dataType == 3) {
            doSerach();
        }


    }

    private void setSeletcAdapter() {
        if (schoolSellectAdapter == null) {
            schoolSellectAdapter = new BaseRecyclerAdapter<SchoolChange.DataBean.ListBean>(this, mseletLists, R.layout.item_school_sign_up_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, SchoolChange.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.schoolName, item.getS_name());
                    holder.setImageByUrl(R.id.schoolImage, UrlFactory.baseImageUrl + item.getS_logo());
                    holder.setText(R.id.schoolDescription, "剩余入学名额" + item.getSignUpmun());
                    holder.setText(R.id.schoolAddress, item.getS_address());
                    holder.setText(R.id.distance, item.getDistance() + "Km");
                }
            };
            mTrainSingUpRecylcerView.setLayoutManager(new LinearLayoutManager(this));
            mTrainSingUpRecylcerView.setAdapter(schoolSellectAdapter);

            schoolSellectAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mseletLists.get(position).getSid());
                    bundle.putString("title", mseletLists.get(position).getS_name());
                    jumpToActivity(SchoolInfoActivity.class, bundle, false);
                }
            });
        } else {
            schoolSellectAdapter.notifyDataSetChanged();
        }
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

    private void setAdapter() {
        if (schoolAdapter == null) {
            schoolAdapter = new BaseRecyclerAdapter<School.DataBean.ListBean>(this, schoolLists, R.layout.item_school_sign_up_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, School.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.schoolName, item.getS_name());
                    holder.setImageByUrl(R.id.schoolImage, UrlFactory.baseImageUrl + item.getS_logo());
                    holder.setText(R.id.schoolDescription, "剩余入学名额" + item.getSignUpmun());
                    holder.setText(R.id.schoolAddress, item.getS_address());
                    holder.setText(R.id.distance, item.getDistance() + "Km");

                }
            };
            mTrainSingUpRecylcerView.setLayoutManager(new LinearLayoutManager(this));
            mTrainSingUpRecylcerView.setAdapter(schoolAdapter);

            schoolAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", schoolLists.get(position).getSid());
                    bundle.putString("title", schoolLists.get(position).getS_name());
                    jumpToActivity(SchoolInfoActivity.class, bundle, false);
                }
            });


        } else {
            schoolAdapter.notifyDataSetChanged();
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


       /* mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResult();
            }
        });*/

        mSearchEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                // TODO Auto-generated method stub
                // 修改回车键功能
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SchoolSignUpActivity.this.getCurrentFocus().getWindowToken(),
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
                    dataType = 1;
                    mCurrentPage = 1;
                    clearResult();
                    mLienarLayout.setVisibility(View.VISIBLE);
                } else {
                    if (schoolAdapter != null) {
                        schoolLists.clear();
                        schoolAdapter.notifyDataSetChanged();
                    }
                    mLienarLayout.setVisibility(View.GONE);
                }
            }
        });


    }

    private void doSerach() {

        HttpParams params = new HttpParams();
        params.put("name", mSearchEditText.getText().toString().trim());
        params.put("cityId", Constans.city_id);
        params.put("long", Constans.longitude);
        params.put("lat", Constans.lat);
        mSubscription = mHttpUtils.getData(UrlFactory.findSchool, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                mCurrentPage = 1;
                showLoadingDialog();
                dataType = 3;
                clearResult();
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                cancleLoadingDialog();
                onStopLoad();
            }

            @Override
            public void onNext(String s) {
                cancleLoadingDialog();
                Gson gson = new Gson();
                SchoolChange schoolChange = gson.fromJson(s, SchoolChange.class);

                if (mCurrentPage == 1) {
                    mseletLists.clear();
                }
                mseletLists.addAll(schoolChange.getData().getList());
                mTotalPage = schoolChange.getData().getTotal();
                setSeletcAdapter();
            }
        });

    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }

    @Override
    protected void initView() {
        mClassification.setText("学校分类");
        mSchooltime.setText("学校性质");
        mSort.setText("排序");
        showLoadingDialog();
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

    @OnClick({R.id.fanhui, R.id.map_Relative, R.id.coures, R.id.study, R.id.sortLin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.map_Relative:
                Bundle bundle = new Bundle();
                bundle.putInt("mapKind", 1);
                jumpToActivity(MapActivity.class, bundle, false);
                break;
            case R.id.coures:
                TYPE = 1;
                if (TYPE == popWindosType) {
                    mCouresKindPopwindow.dismiss();
                    popWindosType = 0;
                } else {
                    showSortPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow1, mDownArrow11, 10);
                    mClassification.setTextColor(getResources().getColor(R.color.base_color));
                }

                break;
            case R.id.study:
                TYPE = 2;
                if (popWindosType == TYPE) {
                    mSchoolXZPopwindow.dismiss();
                    popWindosType = 0;
                } else {
                    showSortPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow2, mDownArrow12, 10);
                    mSchooltime.setTextColor(getResources().getColor(R.color.base_color));
                }

                break;
            case R.id.sortLin:
                TYPE = 3;
                if (popWindosType == TYPE) {
                    mSortPopwindow.dismiss();
                    popWindosType = 0;
                } else {
                    showSortPopWindos(TYPE);
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow3, mDownArrow13, 10);
                    mSort.setTextColor(getResources().getColor(R.color.base_color));
                }
                break;
        }
    }

    public void showSortPopWindos(int type) {

        ListView mMainRecyclerView;

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

        if (type == 1) {
            // 创建自定义样式dialog
            popWindosType = 1;
            View classificationView = inflate(this, R.layout.pop_school_sing_classification, null);// 得到加载view
            if (mCouresKindPopwindow == null) {
                mCouresKindPopwindow = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }
            mMainRecyclerView = (ListView) classificationView.findViewById(R.id.mainRecyclerView);
            setClassiFicationData(mMainRecyclerView, 1);

            mCouresKindPopwindow.setAnimationStyle(R.style.PopWindowAnimStyle);
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
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow11, mDownArrow1, 10);
                }
            });
        } else if (type == 2) {
            // 创建自定义样式dialog
            popWindosType = 2;
            View classificationView = inflate(this, R.layout.pop_school_sing_classification, null);// 得到加载view
            if (mSchoolXZPopwindow == null) {
                mSchoolXZPopwindow = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }
            mMainRecyclerView = (ListView) classificationView.findViewById(R.id.mainRecyclerView);
            setClassiFicationData(mMainRecyclerView, 2);

            mSchoolXZPopwindow.setAnimationStyle(R.style.AnimBottom);
            mSchoolXZPopwindow.setFocusable(false);
            mSchoolXZPopwindow.update();
            mSchoolXZPopwindow.setOutsideTouchable(true);// 设置允许在外点击消失
            ColorDrawable dw = new ColorDrawable(0x30000000);
            mSchoolXZPopwindow.setBackgroundDrawable(dw);
            mSchoolXZPopwindow.showAtLocation(parent, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 500);

            mSchoolXZPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mSchooltime.setTextColor(getResources().getColor(R.color.textcolor));
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow12, mDownArrow2, 10);
                }
            });
        } else if (type == 3) {
            // 创建自定义样式dialog
            popWindosType = 3;
            View classificationView = inflate(this, R.layout.pop_school_sing_classification, null);// 得到加载view
            if (mSortPopwindow == null) {
                mSortPopwindow = new PopupWindow(classificationView, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);

                ThirdLists.add(new Grade(0, "全部"));
                ThirdLists.add(new Grade(1, "距离由近到远"));
                ThirdLists.add(new Grade(2, "价格由低到高"));
                ThirdLists.add(new Grade(3, "价格由高到低"));


            }

            mMainRecyclerView = (ListView) classificationView.findViewById(R.id.mainRecyclerView);
            setClassiFicationData(mMainRecyclerView, 3);


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
                    mSort.setTextColor(getResources().getColor(R.color.textcolor));
                    AnimUtil.FlipAnimatorXViewShow(mDownArrow13, mDownArrow3, 10);
                }
            });
        }
    }

    /**
     * 分类
     */
    private void setClassiFicationData(ListView mainRecyclerView, final int type) {

        if (type == 1) {
            if (mAdaper == null) {
                mAdaper = new PopSchoolSortAdapter(this, mainLists);
                mainRecyclerView.setAdapter(mAdaper);

                mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mClassification.setText(mainLists.get(position).getName());
                        mCouresKindPopwindow.dismiss();
                        dataType = 2;
                        if (position == 0) {
                            mParams.put("typeId", "");
                        } else {
                            mParams.put("typeId", mainLists.get(position).getTid());
                        }
                        popWindosType = 1;
                        mAdaper.setSelectItem(position);
                        mRefreshLayout.startRefresh();
                        clearResult();
                    }
                });
            } else {
                mAdaper.notifyDataSetChanged();
            }


        } else if (type == 2) {
            if (mNaturelistBeanCommonAdaper == null) {
                mNaturelistBeanCommonAdaper = new PopSchoolXZAdapter(this, sencodLists);
                mainRecyclerView.setAdapter(mNaturelistBeanCommonAdaper);
                mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSchoolXZPopwindow.dismiss();
                        dataType = 2;
                        if (position == 0) {
                            mParams.put("natureId", "");
                        } else {
                            mParams.put("natureId", sencodLists.get(position).getNid());
                        }
                        popWindosType = 1;
                        mNaturelistBeanCommonAdaper.setSelectItem(position);
                        mRefreshLayout.startRefresh();
                        clearResult();
                    }
                });
            } else {
                mNaturelistBeanCommonAdaper.notifyDataSetChanged();
            }

        } else if (type == 3) {
            if (activitySortAdapter == null) {
                activitySortAdapter = new PopSelectAdapter(this, ThirdLists);
                mainRecyclerView.setAdapter(activitySortAdapter);
            } else {
                activitySortAdapter.notifyDataSetChanged();
            }


            mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSortPopwindow.dismiss();
                    dataType = 2;
                    if (position == 0) {
                        mParams.put("ordertype", "");
                    } else {
                        mParams.put("ordertype", ThirdLists.get(position).getId());
                    }

                    popWindosType = 1;
                    activitySortAdapter.setSelectItem(position);
                    mRefreshLayout.startRefresh();
                    clearResult();
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (mSchoolXZPopwindow != null && mSchoolXZPopwindow.isShowing()) {
            mSchoolXZPopwindow.dismiss();
        } else if (mCouresKindPopwindow != null && mCouresKindPopwindow.isShowing()) {
            mCouresKindPopwindow.dismiss();
        } else if (mSortPopwindow != null && mSortPopwindow.isShowing()) {
            mSortPopwindow.dismiss();
        } else {
            finish();
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

    public void clearResult() {
        if (dataType == 1) {
            if (schoolLists != null)
                schoolLists.clear();
            if (schoolAdapter != null)
                schoolAdapter.notifyDataSetChanged();
            schoolAdapter = null;

        } else if (dataType == 0) {
            if (mseletLists != null)
                mseletLists.clear();
            if (schoolSellectAdapter != null)
                schoolSellectAdapter.notifyDataSetChanged();
            schoolSellectAdapter = null;
        }

    }

}
