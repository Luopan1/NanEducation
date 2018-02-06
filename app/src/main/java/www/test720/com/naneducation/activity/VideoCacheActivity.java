package www.test720.com.naneducation.activity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
import www.test720.com.naneducation.adapter.ClassifyMainAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Classfication;
import www.test720.com.naneducation.bean.LiveBroad;
import www.test720.com.naneducation.bean.StudyHomeBanner;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.AnimUtil;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.view.ClassFicationItemDecortion;
import www.test720.com.naneducation.view.SpaceItemDecoration;

import static android.view.View.inflate;
import static www.test720.com.naneducation.R.id.item;

public class VideoCacheActivity extends BaseToolbarActivity {

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
    private List<LiveBroad.DataBean.ListBean> mLiveBroadcastList = new ArrayList<>();

    BaseRecyclerAdapter<LiveBroad.DataBean.ListBean> liveCastAdapter;
    private int TYPE = 0;
    private int popWindosType = 0;
    private PopupWindow mCouresKindPopwindow;
    private PopupWindow mSortPopwindow;
    List<TextView> clickPosition = new ArrayList<>();
    List<RelativeLayout> clickView = new ArrayList<>();
    private HttpParams mParams = new HttpParams();
    private LiveBroad mLiveBroad;


    @Override
    protected int getContentView() {
        return R.layout.activity_video_cache;
    }


    private void getData() {

        mParams.put("type", 2);
        mSubscription = mHttpUtils.getData(UrlFactory.liveList, mParams, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
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


    @Override
    protected void initData() {

    }


    private void setAdapter(List<LiveBroad.DataBean.ListBean> liveBroadcastList) {
        if (liveCastAdapter == null) {
            liveCastAdapter = new BaseRecyclerAdapter<LiveBroad.DataBean.ListBean>(this, liveBroadcastList, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, LiveBroad.DataBean.ListBean item, int position, boolean isScrolling) {
                    mSizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    holder.setText(R.id.courseKind, item.getLivetype());
                    if (item.getLivetype().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getLivetype().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getLivetype().equals("直播中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                    else if (item.getLivetype().equals("已结束"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.color_black_ff666666));

                    Drawable drawable = context.getResources().getDrawable(R.drawable.jiage);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                    TextView money = holder.getView(R.id.money);
                    money.setCompoundDrawables(drawable, null, null, null);

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
        } else {
            liveCastAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void setListener() {


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
    protected void initView() {
        mSearchResultRecyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void initBase() {
        isShowToolBar = false;
        isShowBackImage = false;
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
    private void setClassiFicationData(ListView mainRecyclerView, RecyclerView moreRecyclerView) {

        List<StudyHomeBanner> mainLists = new ArrayList<>();
        mainLists.add(new StudyHomeBanner("1", "文化", "1"));
        mainLists.add(new StudyHomeBanner("1", "体育", "1"));
        mainLists.add(new StudyHomeBanner("1", "艺术", "1"));

        final ClassifyMainAdapter mainAdapter = new ClassifyMainAdapter(VideoCacheActivity.this, mainLists);
        mainRecyclerView.setAdapter(mainAdapter);
        mainRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
            }
        });
        mainRecyclerView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        List<StudyHomeBanner> moreLists = new ArrayList<>();
        moreLists.add(new StudyHomeBanner("全部", "", "1"));
        moreLists.add(new StudyHomeBanner("语文", "", "1"));
        moreLists.add(new StudyHomeBanner("英语", "", "1"));


        BaseRecyclerAdapter<StudyHomeBanner> moreAdapter = new BaseRecyclerAdapter<StudyHomeBanner>(VideoCacheActivity.this, moreLists, R.layout.item_select_classfication) {
            @Override
            public void convert(BaseRecyclerHolder holder, StudyHomeBanner item, int position, boolean isScrolling) {
                holder.setText(R.id.classKInd, item.getBa_id());
                RecyclerView myRecyclerView = holder.getView(R.id.my_recycler_view);
                setClassKind(myRecyclerView);
            }
        };
        moreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moreRecyclerView.setAdapter(moreAdapter);

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

        BaseRecyclerAdapter<Classfication> classFicationAdapter = new BaseRecyclerAdapter<Classfication>(VideoCacheActivity.this, classLists, R.layout.item_classfiaction_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, Classfication item, int position, boolean isScrolling) {
                holder.setText(R.id.courseKind, item.getClassName());
                if (item.getClassName().equals("1")) {
                    holder.getView(R.id.item).setVisibility(View.INVISIBLE);

                }
            }
        };
        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myRecyclerView.addItemDecoration(new ClassFicationItemDecortion(DensityUtil.dip2px(VideoCacheActivity.this, 20), DensityUtil.dip2px(VideoCacheActivity.this, 10)));
        myRecyclerView.setAdapter(classFicationAdapter);


        classFicationAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                mClassification.setText(classLists.get(position).getClassName());
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

                //                mCouresKindPopwindow.dismiss();
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
            View selectSort = inflate(this, R.layout.item_selset_sort, null);
            if (mSortPopwindow == null) {
                mSortPopwindow = new PopupWindow(selectSort, DisplayWidth, DisplayHeight - height - getStatusBarHeight() - height1);
            }


            RelativeLayout selectAll = (RelativeLayout) selectSort.findViewById(R.id.sort_all);
            RelativeLayout selectUp = (RelativeLayout) selectSort.findViewById(R.id.sort_up);
            RelativeLayout selectdown = (RelativeLayout) selectSort.findViewById(R.id.sort_down);
            RelativeLayout selectfree = (RelativeLayout) selectSort.findViewById(R.id.sort_free);

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


            selectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                }
            });

            selectUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                }
            });

            selectdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                }
            });

            selectfree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
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
