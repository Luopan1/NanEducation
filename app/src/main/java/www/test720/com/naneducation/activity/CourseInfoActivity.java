package www.test720.com.naneducation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.edusdk.interfaces.JoinmeetingCallBack;
import com.edusdk.interfaces.MeetingNotify;
import com.edusdk.message.RoomClient;
import com.edusdk.tools.Tools;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.ShowPopwindows;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.ActivityBuyOrder;
import www.test720.com.naneducation.bean.AllCourseDetail;
import www.test720.com.naneducation.bean.LiveCourseDetail;
import www.test720.com.naneducation.bean.LiveCourseDetail.DataBean;
import www.test720.com.naneducation.commonactivity.JuBaoActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.model.PayMentCallBack;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.utils.ImageLoader;
import www.test720.com.naneducation.utils.ItemAnimatorFactory;
import www.test720.com.naneducation.video.StartPage_Activity;
import www.test720.com.naneducation.video.VideoActivity;
import www.test720.com.naneducation.video.VideoWebViewActivity;
import www.test720.com.naneducation.view.SpaceItemDecoration;

/**
 * mType : 判断是相关推荐（0,1）还是套课视屏
 * type ：1,2判断是直播还是套课（3）
 */
public class CourseInfoActivity extends BaseToolbarActivity implements JoinmeetingCallBack, MeetingNotify {

    @BindView(R.id.descriptionImage)
    ImageView mDescriptionImage;
    @BindView(R.id.teacherPhoto)
    ImageView mTeacherPhoto;
    @BindView(R.id.teacherName)
    TextView mTeacherName;
    @BindView(R.id.cousePrices)
    TextView mCousePrices;
    @BindView(R.id.BaoMingCount)
    TextView mBaoMingCount;
    @BindView(R.id.courseDonation)
    TextView mCourseDonation;
    @BindView(R.id.courseDescription)
    TextView mCourseDescription;
    @BindView(R.id.teacherDescripition)
    TextView mTeacherDescripition;
    @BindView(R.id.courseOnTime)
    TextView mCourseOnTime;
    @BindView(R.id.courseTimeLength)
    TextView mCourseTimeLength;
    @BindView(R.id.coureSortKind)
    TextView mCoureSortKind;
    @BindView(R.id.CourseCanStudyMan)
    TextView mCourseCanStudyMan;
    @BindView(R.id.titleLinear)
    LinearLayout mTitleLinear;
    @BindView(R.id.titleRelative)
    RelativeLayout mTitleRelative;
    @BindView(R.id.recommendRecyclerView)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.already_not_on)
    RelativeLayout mAlreadyNotOn;
    @BindView(R.id.colocationRelative)
    RelativeLayout mColocationRelative;
    @BindView(R.id.intoLiveBroadCastTV)
    TextView mIntoLiveBroadCastTV;
    @BindView(R.id.enterLiveBroadCastRelative)
    RelativeLayout mEnterLiveBroadCastRelative;
    @BindView(R.id.already_yes_on)
    LinearLayout mAlreadyYesOn;

    BaseRecyclerAdapter<DataBean.DetailBean.TopListBean> recommandAdapter;
    List<DataBean.DetailBean.TopListBean> mLiveBroadcastList;

    BaseRecyclerAdapter<AllCourseDetail.DataBean.DetailBean.VideoListBean> allCourseRecommandAdapter;
    List<AllCourseDetail.DataBean.DetailBean.VideoListBean> allCourseRecommandLists;

    @BindView(R.id.about_recmond)
    TextView mAboutRecmond;
    @BindView(R.id.colocation)
    TextView mColocation;
    @BindView(R.id.fanhuiRelative)
    LinearLayout mFanhuiRelative;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.shareImage)
    ImageView mShareImage;
    @BindView(R.id.juBaoImage)
    ImageView mJuBaoImage;
    private ImageLoader mImageLoader;
    private int mLiveType;
    private String mLiveId;
    private LiveCourseDetail mDetail;
    private PopupWindow mPopupWindow;
    private AllCourseDetail mAllCourseDetail;
    private String mTitle1;

    @Override
    protected int getContentView() {
        return R.layout.activity_course_info;
    }

    @Override
    protected void initData() {
        if (mLiveType == 1 || mLiveType == 2) {
            HttpParams params = new HttpParams();
            params.put("type", mLiveType);
            params.put("lid", mLiveId);
            params.put("uid", Constans.uid);
            mSubscription = mHttpUtils.getData(UrlFactory.liveDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                @Override
                public void onStart() {
                    showLoadingDialog();
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

                    cancleLoadingDialog();

                    mDetail = JSON.parseObject(s, LiveCourseDetail.class);

                    Log.e("TAG+++++++++", mDetail.getData().getDetail().toString());

                    Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mDetail.getData().getDetail().getHead()).into(mTeacherPhoto);
                    Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mDetail.getData().getDetail().getLive_img()).asBitmap().placeholder(R.mipmap.default_image).into(mDescriptionImage);

                    mTeacherName.setText(mDetail.getData().getDetail().getName());
                    mBaoMingCount.setText("报名人数：" + mDetail.getData().getDetail().getCount());
                    String sponsoe = "";
                    for (int i = 0; i < mDetail.getData().getDetail().getSponsor().size(); i++) {
                        sponsoe += mDetail.getData().getDetail().getSponsor().get(i).trim() + " ";
                    }
                    mCourseDonation.setText(sponsoe);
                    mCousePrices.setText(mDetail.getData().getDetail().getPrice());
                    mCourseDescription.setText(mDetail.getData().getDetail().getContent());
                    mTeacherDescripition.setText(mDetail.getData().getDetail().getTeacher_content());

                    mCourseOnTime.setText(mDetail.getData().getDetail().getStartime());
                    mCourseTimeLength.setText(mDetail.getData().getDetail().getCash_time());
                    mCoureSortKind.setText(mDetail.getData().getDetail().getTname());
                    mCourseCanStudyMan.setText(mDetail.getData().getDetail().getPeople() + "岁");
                    mLiveBroadcastList = new ArrayList<>();
                    mLiveBroadcastList.addAll(mDetail.getData().getDetail().getTopList());
                    setAdapter(1);

                    if (mDetail.getData().getDetail().getIs_love() == 1) {
                        mColocation.setText("已收藏");
                        Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                        drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                        mColocation.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        mColocation.setText("收藏");
                        Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                        drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                        mColocation.setCompoundDrawables(drawable, null, null, null);
                    }

                    if (mDetail.getData().getDetail().getIs_canup() == 1) {
                        mIntoLiveBroadCastTV.setText(mDetail.getData().getDetail().getOpentype());
                        mAlreadyNotOn.setVisibility(View.GONE);
                        mAlreadyYesOn.setVisibility(View.VISIBLE);
                        mEnterLiveBroadCastRelative.setBackgroundColor(getResources().getColor(R.color.base_color));

                    } else if (mDetail.getData().getDetail().getIs_canup() == 2) {
                        mIntoLiveBroadCastTV.setText(mDetail.getData().getDetail().getOpentype());
                        mAlreadyNotOn.setVisibility(View.GONE);
                        mAlreadyYesOn.setVisibility(View.VISIBLE);
                        mEnterLiveBroadCastRelative.setBackgroundColor(getResources().getColor(R.color.base_color));

                    } else if (mDetail.getData().getDetail().getIs_canup() == 3) {
                        mAlreadyNotOn.setVisibility(View.VISIBLE);
                        mAlreadyYesOn.setVisibility(View.GONE);

                    } else if (mDetail.getData().getDetail().getIs_canup() == 4) {
                        mIntoLiveBroadCastTV.setText(mDetail.getData().getDetail().getOpentype());
                        mAlreadyNotOn.setVisibility(View.GONE);
                        mAlreadyYesOn.setVisibility(View.VISIBLE);
                        mEnterLiveBroadCastRelative.setBackgroundColor(getResources().getColor(R.color.base_color));
                    } else if (mDetail.getData().getDetail().getIs_canup() == 5) {
                        mIntoLiveBroadCastTV.setText(mDetail.getData().getDetail().getOpentype());
                        mAlreadyNotOn.setVisibility(View.GONE);
                        mAlreadyYesOn.setVisibility(View.VISIBLE);
                        mEnterLiveBroadCastRelative.setBackgroundColor(getResources().getColor(R.color.system_color));
                    }
                }
            });
        } else if (mLiveType == 3) {
            HttpParams params = new HttpParams();
            params.put("cid", mLiveId);
            params.put("uid", Constans.uid);
            mSubscription = mHttpUtils.getData(UrlFactory.courseDetail_TK, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                @Override
                public void onStart() {
                    showLoadingDialog();
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
                    cancleLoadingDialog();
                    Gson gson = new Gson();
                    mAllCourseDetail = gson.fromJson(s, AllCourseDetail.class);

                    Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mAllCourseDetail.getData().getDetail().getTc_head()).into(mTeacherPhoto);
                    Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + mAllCourseDetail.getData().getDetail().getC_img()).asBitmap().placeholder(R.mipmap.default_image).into(mDescriptionImage);

                    mTeacherName.setText(mAllCourseDetail.getData().getDetail().getTc_name());
                    mBaoMingCount.setText("报名人数：" + mAllCourseDetail.getData().getDetail().getMun());
                    String sponsoe = "";
                    for (int i = 0; i < mAllCourseDetail.getData().getDetail().getSponsor().size(); i++) {
                        sponsoe += mAllCourseDetail.getData().getDetail().getSponsor().get(i).trim() + " ";
                    }
                    mCourseDonation.setText(sponsoe);
                    mCousePrices.setText(mAllCourseDetail.getData().getDetail().getC_price());
                    mCourseDescription.setText(mAllCourseDetail.getData().getDetail().getC_describe());
                    mTeacherDescripition.setText(mAllCourseDetail.getData().getDetail().getTc_describe());

                    mCourseOnTime.setText(mAllCourseDetail.getData().getDetail().getStartime());
                    mCourseTimeLength.setText(mAllCourseDetail.getData().getDetail().getC_ctime());
                    mCoureSortKind.setText(mAllCourseDetail.getData().getDetail().getC_type());
                    mCourseCanStudyMan.setText(mAllCourseDetail.getData().getDetail().getC_people() + "岁");
                    allCourseRecommandLists = new ArrayList<>();
                    allCourseRecommandLists.addAll(mAllCourseDetail.getData().getDetail().getVideoList());
                    setAdapter(2);

                    if (mAllCourseDetail.getData().getDetail().getIs_love() == 1) {
                        mColocation.setText("已收藏");
                        Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                        drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                        mColocation.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        mColocation.setText("收藏");
                        Drawable drawable = context.getResources().getDrawable(R.drawable.shouckong);
                        drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                        mColocation.setCompoundDrawables(drawable, null, null, null);
                    }

                    if (mAllCourseDetail.getData().getDetail().getIs_signup() == 1) {
                        mIntoLiveBroadCastTV.setText("观看");
                        mAlreadyNotOn.setVisibility(View.GONE);
                        mAlreadyYesOn.setVisibility(View.VISIBLE);
                        mEnterLiveBroadCastRelative.setBackgroundColor(getResources().getColor(R.color.base_color));
                    } else {
                        mIntoLiveBroadCastTV.setText("立即报名");
                        mAlreadyNotOn.setVisibility(View.GONE);
                        mAlreadyYesOn.setVisibility(View.VISIBLE);
                        mEnterLiveBroadCastRelative.setBackgroundColor(getResources().getColor(R.color.base_color));
                    }
                }
            });
        }
    }

    private void setAdapter(int i) {
        if (i == 1) {
            if (recommandAdapter == null) {
                recommandAdapter = new BaseRecyclerAdapter<DataBean.DetailBean.TopListBean>(this, mLiveBroadcastList, R.layout.item_select_stydy_item) {
                    @Override
                    public void convert(BaseRecyclerHolder holder, DataBean.DetailBean.TopListBean item, int position, boolean isScrolling) {
                        mSizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);
                        holder.setText(R.id.courseKind, item.getLivetype());
                        if (item.getLivetype().equals("预告"))
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                        else if (item.getLivetype().equals("直播中"))
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                        else if (item.getLivetype().equals("已结束"))
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.color_black_ff666666));
                        else if (item.getLivetype().equals("免费"))
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                        else if (item.getLivetype().equals("回放"))
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));
                        else if (item.getLivetype().equals("套课"))
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));

                        TextView money = holder.getView(R.id.money);
                        if (item.getLivetype().equals("免费")) {
                            money.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.free_line));
                            Drawable drawable = context.getResources().getDrawable(R.mipmap.free_money);
                            drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                            money.setTextColor(Color.parseColor("#999999"));
                            money.setCompoundDrawables(drawable, null, null, null);
                        } else {
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

                mRecommendRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                mRecommendRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
                mRecommendRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
                mRecommendRecyclerView.setAdapter(recommandAdapter);

                recommandAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", mLiveBroadcastList.get(position).getLive_title());
                        bundle.putInt("type", mLiveType);
                        bundle.putString("id", mLiveBroadcastList.get(position).getLid());
                        if (mLiveType == 1) {
                            bundle.putString("room", mLiveBroadcastList.get(position).getRoom_mun());
                        } else {
                            bundle.putString("path", mLiveBroadcastList.get(position).getBack_url());
                        }
                        jumpToActivity(CourseInfoActivity.class, bundle, false);
                    }
                });
            } else {
                recommandAdapter.notifyDataSetChanged();
            }
        } else if (i == 2) {
            if (allCourseRecommandAdapter == null) {
                allCourseRecommandAdapter = new BaseRecyclerAdapter<AllCourseDetail.DataBean.DetailBean.VideoListBean>(this, allCourseRecommandLists, R.layout.item_select_stydy_item) {
                    @Override
                    public void convert(BaseRecyclerHolder holder, AllCourseDetail.DataBean.DetailBean.VideoListBean item, int position, boolean isScrolling) {
                        mSizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);
                        if (item.getIs_free().equals("1")) {
                            holder.setText(R.id.courseKind, "免费");
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                        } else {
                            holder.setText(R.id.courseKind, "套课");
                            holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.taoke));
                        }


                        TextView money = holder.getView(R.id.money);
                        if (item.getIs_free().equals("1")) {
                            money.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.free_line));
                            Drawable drawable = context.getResources().getDrawable(R.mipmap.free_money);
                            drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                            money.setTextColor(Color.parseColor("#999999"));
                            money.setCompoundDrawables(drawable, null, null, null);
                        } else {
                            Drawable drawable = context.getResources().getDrawable(R.drawable.money);
                            drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                            money.setTextColor(Color.parseColor("#4295CB"));
                            money.setCompoundDrawables(drawable, null, null, null);
                        }

                        if (mAllCourseDetail.getData().getDetail().getTc_name().trim().length() > 3) {
                            mAllCourseDetail.getData().getDetail().setTc_name(mAllCourseDetail.getData().getDetail().getTc_name().substring(0, 3) + "...");
                        }
                        holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getV_logo());
                        holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                        holder.setText(R.id.teacherName, mAllCourseDetail.getData().getDetail().getTc_name());
                        holder.setText(R.id.studyCourseName, item.getV_name());
                        holder.setText(R.id.money, item.getV_price());
                    }
                };
            }
            mRecommendRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mRecommendRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            mRecommendRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            mRecommendRecyclerView.setAdapter(allCourseRecommandAdapter);

            allCourseRecommandAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", allCourseRecommandLists.get(position).getV_name());
                    bundle.putInt("type", mLiveType);
                    bundle.putString("id", allCourseRecommandLists.get(position).getCourse_id());

                    if (mAllCourseDetail.getData().getDetail().getIs_signup() == 1) {
                        //  已经购买了  直接播放点击的
                        Bundle b = new Bundle();
                        b.putString(VideoActivity.TITLE, allCourseRecommandLists.get(position).getV_name());
                        b.putString(VideoActivity.URL, UrlFactory.taokepath + allCourseRecommandLists.get(position).getV_src());
                        jumpToActivity(VideoActivity.class, b, false);

                    } else {
                        //没有购买的  跳转到详情页面
                        jumpToActivity(CourseInfoActivity.class, bundle, false);
                    }
                }
            });

        } else {
            allCourseRecommandAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {

    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };


    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();

        /**判断是直播还是录播*/
        mLiveType = intent.getIntExtra("type", 0);

        LogUtils.e(mLiveType);

        mLiveId = intent.getStringExtra("id");

        if (mLiveType == 1 || mLiveType == 2) {
            mAboutRecmond.setText("相关推荐");
        } else {
            mAboutRecmond.setText("套课视频");

        }
        mTitle1 = intent.getStringExtra("title");
        if (mTitle1.length() > 8) {
            mTitle1 = mTitle1.substring(0, 9) + "...";
        }
        mTitle.setText(mTitle1);
        mRecommendRecyclerView.setNestedScrollingEnabled(false);


    }


    @OnClick({R.id.already_not_on, R.id.colocationRelative, R.id.enterLiveBroadCastRelative, R.id.already_yes_on, R.id.fanhuiRelative, R.id.shareImage, R.id.juBaoImage})
    public void onClick(View view) {
        switch (view.getId()) {
            /*已经报名 但是没有开播*/
            case R.id.already_not_on:

                break;
            /*收藏*/
            case R.id.colocationRelative:
                if (isLogined(1)) {
                    HttpParams params = new HttpParams();
                    params.put("uid", Constans.uid);
                    params.put("loveId", mLiveId);
                    if (mLiveType == 1) {
                        params.put("typeId", 1);
                    } else if (mLiveType == 2) {
                        params.put("typeId", 2);
                    } else if (mLiveType == 3) {
                        params.put("typeId", 3);
                    }

                    if (mLiveType == 1 || mLiveType == 2) {
                        if (mDetail.getData().getDetail().getIs_love() == 1) {
                            params.put("type", 0);
                        } else {
                            params.put("type", 1);
                        }
                    } else if (mLiveType == 3) {
                        if (mAllCourseDetail.getData().getDetail().getIs_love() == 1) {
                            params.put("type", 0);
                        } else {
                            params.put("type", 1);
                        }
                    }

                    mSubscription = mHttpUtils.getData(UrlFactory.userCollection, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                        @Override
                        public void onStart() {
                            showLoadingDialog();
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
                            cancleLoadingDialog();
                            ShowToast(JSON.parseObject(s).getString("msg"));
                            if (mLiveType == 1 || mLiveType == 2) {
                                if (mDetail.getData().getDetail().getIs_love() == 0) {
                                    mColocation.setText("已收藏");
                                    Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                    mColocation.setCompoundDrawables(drawable, null, null, null);
                                    mDetail.getData().getDetail().setIs_love(1);
                                } else {
                                    mColocation.setText("收藏");
                                    Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                    mColocation.setCompoundDrawables(drawable, null, null, null);
                                    mDetail.getData().getDetail().setIs_love(0);
                                }
                            } else if (mLiveType == 3) {
                                if (mAllCourseDetail.getData().getDetail().getIs_love() == 0) {
                                    mColocation.setText("已收藏");
                                    Drawable drawable = getResources().getDrawable(R.drawable.yishouc);
                                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                    mColocation.setCompoundDrawables(drawable, null, null, null);
                                    mAllCourseDetail.getData().getDetail().setIs_love(1);
                                } else {
                                    mColocation.setText("收藏");
                                    Drawable drawable = getResources().getDrawable(R.drawable.shouckong);
                                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                                    mColocation.setCompoundDrawables(drawable, null, null, null);
                                    mAllCourseDetail.getData().getDetail().setIs_love(0);
                                }
                            }
                        }

                    });
                }
                break;
            /*进入直播间   如果没有报名 就改成立即报名*/
            case R.id.enterLiveBroadCastRelative:
                if (isLogined(1)) {
                    if (isSetPayPass()) {
                        if (mLiveType == 1 || mLiveType == 2) {
                            if (mDetail.getData().getDetail().getIs_signup() == 0 && mDetail.getData().getDetail().getIs_canup() == 4) {
                                HttpParams params = new HttpParams();
                                params.put("type", mLiveType);
                                params.put("lid", mLiveId);
                                params.put("uid", Constans.uid);
                                mSubscription = mHttpUtils.getData(UrlFactory.liveSignUp, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                                    @Override
                                    public void onStart() {
                                        showLoadingDialog();
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
                                        cancleLoadingDialog();
                                        JSONObject object = JSON.parseObject(s);
                                        if (object.getInteger("code") == 103) {
                                            mPopupWindow = ShowPopwindows.showNotEnoughMoney(CourseInfoActivity.this);
                                        } else {

                                            Gson gson = new Gson();
                                            final ActivityBuyOrder buyOrder = gson.fromJson(s, ActivityBuyOrder.class);
                                            String sponsor = "";
                                            for (int i = 0; i < buyOrder.getData().getSponsor().size(); i++) {
                                                sponsor += buyOrder.getData().getSponsor().get(i) + " ";
                                            }
                                            mPopupWindow = ShowPopwindows.showPaymentPop(CourseInfoActivity.this, buyOrder.getData().getPrice() + "", sponsor, buyOrder.getData().getIntegral(), buyOrder.getData().getPayprice() + "", new PayMentCallBack() {
                                                @Override
                                                public void payBack(String pass) {
                                                    HttpParams params = new HttpParams();
                                                    params.put("uid", Constans.uid);
                                                    params.put("paypass", pass);
                                                    params.put("ordernum", buyOrder.getData().getO_number());
                                                    goPay(params);
                                                }
                                            });
                                        }
                                    }
                                });

                            } else if (mDetail.getData().getDetail().getIs_canup() == 1 && mDetail.getData().getDetail().getIs_signup() == 1) {
                                if (mLiveType == 1) {
                                    // TODO: 2017/11/15 进入直播课堂
                                    HashMap<String, Object> map = new HashMap<>();

                                    map.put("userrole", 2); //
                                    //                map.put("host", "192.168.1.17"); // 内网地址
                                    map.put("host", "global.talk-cloud.net"); //公网地址
                                    map.put("port", 80);  //端口
                                    map.put("serial", getIntent().getStringExtra("room")); //课堂号
                                    map.put("nickname", Constans.name); // 昵称
                                    RoomClient.getInstance().joinRoom(CourseInfoActivity.this, map);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("path", getIntent().getStringExtra("path"));
                                    jumpToActivity(VideoWebViewActivity.class, bundle, false);
                                }
                            }
                        } else if (mLiveType == 3) {
                            if (mAllCourseDetail.getData().getDetail().getIs_signup() == 0) {
                                HttpParams params = new HttpParams();
                                params.put("uid", Constans.uid);
                                params.put("cid", mLiveId);
                                mSubscription = mHttpUtils.getData(UrlFactory.courseSignUp, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                                    @Override
                                    public void onStart() {
                                        showLoadingDialog();
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
                                        cancleLoadingDialog();
                                        JSONObject object = JSON.parseObject(s);
                                        if (object.getInteger("code") == 103) {
                                            mPopupWindow = ShowPopwindows.showNotEnoughMoney(CourseInfoActivity.this);

                                        } else {
                                            Gson gson = new Gson();
                                            final ActivityBuyOrder buyOrder = gson.fromJson(s, ActivityBuyOrder.class);


                                            String sponsor = "";
                                            for (int i = 0; i < buyOrder.getData().getSponsor().size(); i++) {
                                                sponsor += buyOrder.getData().getSponsor().get(i) + " ";
                                            }

                                            mPopupWindow = ShowPopwindows.showPaymentPop(CourseInfoActivity.this, buyOrder.getData().getPrice() + "", sponsor, buyOrder.getData().getIntegral(), buyOrder.getData().getPayprice() + "", new PayMentCallBack() {
                                                @Override
                                                public void payBack(String pass) {
                                                    HttpParams params = new HttpParams();
                                                    params.put("uid", Constans.uid);
                                                    params.put("paypass", pass);
                                                    params.put("ordernum", buyOrder.getData().getO_number());
                                                    goPay(params);
                                                }
                                            });
                                        }
                                    }

                                });
                            } else {
                                //  已经购买了  直接播放点击的
                                if (allCourseRecommandLists.size() <= 0) {
                                    ShowToast("当前暂无播放资源");
                                    return;
                                }
                                Bundle b = new Bundle();
                                b.putString(VideoActivity.TITLE, allCourseRecommandLists.get(0).getV_name());
                                b.putString(VideoActivity.URL, UrlFactory.taokepath + allCourseRecommandLists.get(0).getV_src());
                                jumpToActivity(VideoActivity.class, b, false);
                            }

                        }
                    } else {
                        ShowPopwindows.showNotSetPayPass(this);
                    }
                }

                break;
            /*已经报名了 并且直播间也可以进入了 然后显示这个 隐藏掉already_not_on */
            case R.id.already_yes_on:
                LogUtils.e("R.id.already_yes_on");
                break;
            case R.id.fanhuiRelative:
                finish();
                break;
            case R.id.shareImage:
                UMWeb web = new UMWeb(UrlFactory.downLoadUrl);
                web.setTitle("助学");//标题
                if (mLiveType == 1) {
                    web.setDescription("我在学海app中学习" + mDetail.getData().getDetail().getName() + "的" + mDetail.getData().getDetail().getLive_title() + "的直播");
                } else if (mLiveType == 2) {
                    web.setDescription("我在学海app中学习" + mDetail.getData().getDetail().getName() + "的" + mDetail.getData().getDetail().getLive_title() + "的录播");
                } else if (mLiveType == 3) {
                    web.setDescription("我在学海app中学习" + mAllCourseDetail.getData().getDetail().getTc_name() + "的" + mTitle1 + "的套课");
                }
                new ShareAction(CourseInfoActivity.this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener)
                        .open();
                break;
            case R.id.juBaoImage:

                mPopupWindow = ShowPopwindows.showJuBao(CourseInfoActivity.this, mJuBaoImage, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.tip_text) {
                            if (isLogined(1)) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", mLiveType);
                                bundle.putString("id", mLiveId);
                                jumpToActivity(JuBaoActivity.class, bundle, false);
                            }
                        }
                    }
                });
                break;

        }
    }

    private void goPay(HttpParams params) {

        mSubscription = mHttpUtils.getData(UrlFactory.payChatOrder, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mPopupWindow.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                cancleLoadingDialog();
            }

            @Override
            public void onNext(String s) {
                cancleLoadingDialog();
                mPopupWindow.dismiss();
                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                //                GroupActivityInfoActivity.type = 0;
                if (obj.getInteger("code") == 1) {
                    initData();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            finish();
        }
    }


    @Override
    public void onKickOut(int res) {
        if (res == RoomClient.Kickout_Repeat) {
            Toast.makeText(this, "有相同的身份进入  你已被踢出房间", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWarning(int code) {
        if (code == 1) {
            ShowToast("打开失败 请稍候再试");
        }
        if (code == 2) {
            ShowToast("打开失败");
        }
    }

    @Override
    public void onClassBegin() {
        LogUtils.e("TAG++++++++++", "已经上课了");
        Toast.makeText(this, getString(R.string.class_started), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onClassDismiss() {
        LogUtils.e("TAG++++++++++", "课堂不存在");
        Toast.makeText(this, getString(R.string.class_closeed), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void callBack(int nRet) {
        LogUtils.e("TAG++++++++++", nRet);
        Tools.HideProgressDialog();
        if (nRet == 0) {

        } else if (nRet == 100) {
        } else if (nRet == 101) {
            ShowToast("协议格式不正确");
        } else if (nRet == 4008) {
            ShowToast("进入失败");
        } else if (nRet == 4110) {
            ShowToast("课堂需要密码 创建错误");
        } else if (nRet == 4007) {
            ShowToast("课堂不存在");
        } else if (nRet == 3001) {
            ShowToast("服务器过期");
        } else if (nRet == 3002) {
            ShowToast("公司被冻结");
        } else if (nRet == 3003) {
            ShowToast("课堂被删除或过期");
        } else if (nRet == 4109) {
            ShowToast("Auth不正确");
        } else if (nRet == 4103) {
            ShowToast("课堂人数超限");
        } else if (nRet == 5006) {
            ShowToast("请您使用学员地址进入课堂");
        } else if (nRet == 4012) {
            ShowToast("该课堂需要密码，请输入密码");
        } else {
            errorTipDialog(this,
                    R.string.WaitingForNetwork);
        }
    }

    public void errorTipDialog(final Activity activity, int errorTipID) {

        AlertDialog.Builder build = new AlertDialog.Builder(activity);
        build.setTitle(getString(R.string.link_tip));
        build.setMessage(getString(errorTipID));
        build.setPositiveButton(getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }

                });
        build.show();
    }




}
