package www.test720.com.naneducation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.AllCourseActivity;
import www.test720.com.naneducation.activity.CourseInfoActivity;
import www.test720.com.naneducation.activity.GroupActivityInfoActivity;
import www.test720.com.naneducation.activity.GroupActivity_Activity;
import www.test720.com.naneducation.activity.H5Activity;
import www.test720.com.naneducation.activity.LiveBroadcastActivity;
import www.test720.com.naneducation.activity.NewsActivity;
import www.test720.com.naneducation.activity.PersonalCenterActivity;
import www.test720.com.naneducation.activity.SchoolInfoActivity;
import www.test720.com.naneducation.activity.SchoolSignUpActivity;
import www.test720.com.naneducation.activity.TrainSchoolInfoActivity;
import www.test720.com.naneducation.activity.TrainSignUpActivity;
import www.test720.com.naneducation.bean.FirstDataBean;
import www.test720.com.naneducation.bean.StudyHomeType;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.login.LoginActivity;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.utils.ItemAnimatorFactory;
import www.test720.com.naneducation.utils.NetImageLoaderHolder;
import www.test720.com.naneducation.utils.SizeUtils;
import www.test720.com.naneducation.view.DividerGridItemDecoration;
import www.test720.com.naneducation.view.SpaceItemDecoration;


/**
 * Created by LuoPan on 2017/10/9 16:48.
 */

public class StudyHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private SizeUtils sizeUtils;

    private List<FirstDataBean.DataBean.BannerBean> studyHomeBannerList;
    private List<StudyHomeType> studyHomeTypeList;
    private List<FirstDataBean.DataBean.TopListoneBean> studySelectedTypeList;
    private List<FirstDataBean.DataBean.TopListwoBean> studyVedioTypeList;
    private List<FirstDataBean.DataBean.TopListhreeBean> studyAllCourseTypeList;
    private List<FirstDataBean.DataBean.TrainListBean> studySignUpTypeList;
    private List<FirstDataBean.DataBean.SchoolListBean> studyFamousSchoolTypeList;
    private List<FirstDataBean.DataBean.ActListBean> studyActivityTypeList;

    private List<Integer> results;


    private BaseRecyclerAdapter<StudyHomeType> adapter;
    private BaseRecyclerAdapter<FirstDataBean.DataBean.TopListoneBean> selectStudyAdapter;
    private BaseRecyclerAdapter<FirstDataBean.DataBean.TopListwoBean> studyVideoAdapter;
    private BaseRecyclerAdapter<FirstDataBean.DataBean.TopListhreeBean> studyAllCourseAdapter;
    private BaseRecyclerAdapter<FirstDataBean.DataBean.TrainListBean> signUpAdapter;
    private BaseRecyclerAdapter<FirstDataBean.DataBean.SchoolListBean> famousSchoolAdapter;
    private BaseRecyclerAdapter<FirstDataBean.DataBean.ActListBean> activityAdapter;
    private static final int TYPE_1 = 0xff01;
    private static final int TYPE_2 = 0xff02;
    private static final int TYPE_3 = 0xff03;
    private static final int TYPE_4 = 0xff04;
    private static final int TYPE_5 = 0xff05;
    private static final int TYPE_6 = 0xff06;
    private static final int TYPE_7 = 0xff07;
    private static final int TYPE_8 = 0xff08;
    private static final int TYPE_9 = 0xff09;
    private RecyclerView recyclerView;

    public StudyHomeAdapter(Activity context, List<Integer> results,
                            List<FirstDataBean.DataBean.BannerBean> studyHomeBannerList, List<StudyHomeType> studyHomeTypeList,
                            List<FirstDataBean.DataBean.TopListoneBean> studySelectedTypeList, List<FirstDataBean.DataBean.TopListwoBean> studyVedioTypeList,
                            List<FirstDataBean.DataBean.TopListhreeBean> studyAllCourseTypeList, List<FirstDataBean.DataBean.TrainListBean> studySignUpTypeList,
                            List<FirstDataBean.DataBean.SchoolListBean> studyFamousSchoolTypeList, List<FirstDataBean.DataBean.ActListBean> studyActivityTypeList) {
        this.context = context;
        this.results = results;
        this.studyHomeBannerList = studyHomeBannerList;
        this.studyHomeTypeList = studyHomeTypeList;
        this.studySelectedTypeList = studySelectedTypeList;
        this.studyVedioTypeList = studyVedioTypeList;
        this.studyAllCourseTypeList = studyAllCourseTypeList;
        this.studySignUpTypeList = studySignUpTypeList;
        this.studyFamousSchoolTypeList = studyFamousSchoolTypeList;
        this.studyActivityTypeList = studyActivityTypeList;

        sizeUtils = new SizeUtils(context);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_1:
                return new BannerHolder(LayoutInflater.from(context).inflate(R.layout.item_banner_study, null));
            case TYPE_2:
                return new ClassificationHolder(LayoutInflater.from(context).inflate(R.layout.item_classification_study, null));
            case TYPE_3:
                return new StudySelectHolder(LayoutInflater.from(context).inflate(R.layout.item_select_study, null));
            case TYPE_4:
                return new StudyVideoHolder(LayoutInflater.from(context).inflate(R.layout.item_video_study, null));
            case TYPE_5:
                return new StudyAllCourse(LayoutInflater.from(context).inflate(R.layout.item_all_coures_study, null));
            case TYPE_6:
                return new SignUpHolder(LayoutInflater.from(context).inflate(R.layout.item_sign_up_study, null));
            case TYPE_7:
                return new FamousSchoolHolder(LayoutInflater.from(context).inflate(R.layout.item_famous_school_study, null));
            case TYPE_8:
                return new ActivityHolder(LayoutInflater.from(context).inflate(R.layout.item_activity_study, null));
            default:
                Log.d("error", "viewholder is null");
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerHolder) {
            setBannerData((BannerHolder) holder, position);
        } else if (holder instanceof ClassificationHolder) {
            setClassificationData((ClassificationHolder) holder, position);
        } else if (holder instanceof StudySelectHolder) {
            setSelectStudyData((StudySelectHolder) holder, position);
        } else if (holder instanceof StudyVideoHolder) {
            setStudVideoData((StudyVideoHolder) holder, position);
        } else if (holder instanceof StudyAllCourse) {
            setAllCourseData((StudyAllCourse) holder, position);
        } else if (holder instanceof SignUpHolder) {
            setSignUpData((SignUpHolder) holder, position);
        } else if (holder instanceof FamousSchoolHolder) {
            setFamousSchoolData((FamousSchoolHolder) holder, position);
        } else if (holder instanceof ActivityHolder) {
            setActivityData((ActivityHolder) holder, position);
        }
    }

    private void setActivityData(ActivityHolder holder, int position) {
        if (activityAdapter == null) {
            activityAdapter = new BaseRecyclerAdapter<FirstDataBean.DataBean.ActListBean>(context, studyActivityTypeList, R.layout.item_activity_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, FirstDataBean.DataBean.ActListBean item, int position, boolean isScrolling) {

                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    TextView money = holder.getView(R.id.money);

                    if (item.getAct_money().equals("0")) {
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
                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getHead());
                    holder.setText(R.id.teacherName, item.getName());
                    holder.setText(R.id.studyCourseName, item.getAct_name());
                    holder.setText(R.id.money, item.getAct_money());

                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(activityAdapter);
            activityAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, GroupActivityInfoActivity.class);
                    intent.putExtra("id", studyActivityTypeList.get(position).getAct_id());
                    intent.putExtra("isShow", true);
                    context.startActivity(intent);
                }
            });

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GroupActivity_Activity.class);
                    context.startActivity(intent);
                }
            });

        } else {
            activityAdapter.notifyDataSetChanged();
        }
    }

    private void setFamousSchoolData(FamousSchoolHolder holder, int position) {
        if (famousSchoolAdapter == null) {
            famousSchoolAdapter = new BaseRecyclerAdapter<FirstDataBean.DataBean.SchoolListBean>(context, studyFamousSchoolTypeList, R.layout.item_sign_up_iten) {
                @Override
                public void convert(BaseRecyclerHolder holder, FirstDataBean.DataBean.SchoolListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.item_Relative, UrlFactory.baseImageUrl + item.getS_logo());
                    holder.setText(R.id.signUpName, item.getS_name());
                }
            };
            holder.myRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.myRecyclerView.setAdapter(famousSchoolAdapter);
            famousSchoolAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, SchoolInfoActivity.class);
                    intent.putExtra("id", studyFamousSchoolTypeList.get(position).getSid());
                    intent.putExtra("title", studyFamousSchoolTypeList.get(position).getS_name());
                    context.startActivity(intent);
                }
            });
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SchoolSignUpActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            famousSchoolAdapter.notifyDataSetChanged();
        }
    }

    private void setSignUpData(SignUpHolder holder, int position) {
        if (signUpAdapter == null) {
            signUpAdapter = new BaseRecyclerAdapter<FirstDataBean.DataBean.TrainListBean>(context, studySignUpTypeList, R.layout.item_sign_up_iten) {
                @Override
                public void convert(BaseRecyclerHolder holder, FirstDataBean.DataBean.TrainListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.item_Relative, UrlFactory.baseImageUrl + item.getTrain_logo());
                    holder.setText(R.id.signUpName, item.getTrain_name());
                }
            };
            holder.myRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.myRecyclerView.setAdapter(signUpAdapter);
            signUpAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, TrainSchoolInfoActivity.class);
                    intent.putExtra("id", studySignUpTypeList.get(position).getTrain_id());
                    context.startActivity(intent);
                }
            });
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrainSignUpActivity.class);
                    context.startActivity(intent);
                }
            });

        } else {
            signUpAdapter.notifyDataSetChanged();
        }


    }

    private void setAllCourseData(StudyAllCourse holder, int position) {
        if (studyAllCourseAdapter == null) {
            studyAllCourseAdapter = new BaseRecyclerAdapter<FirstDataBean.DataBean.TopListhreeBean>(context, studyAllCourseTypeList, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, FirstDataBean.DataBean.TopListhreeBean item, int position, boolean isScrolling) {

                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }
                    if (item.getTypename().equals("免费")) {

                        holder.setText(R.id.courseKind, "免费");
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    } else if (item.getTypename().equals("套课")) {
                        holder.setText(R.id.courseKind, "套课");
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.taoke));
                    }

                    TextView money = holder.getView(R.id.money);

                    if (item.getTypename().equals("免费")) {
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


                    /// 这一步必须要做,否则不会显示.


                    holder.setText(R.id.courseKind, item.getTypename());
                    if (item.getTypename().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getTypename().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getTypename().equals("直播中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                    else if (item.getTypename().equals("回放"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));

                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.studyCourseName, item.getName());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.money, item.getPrice());


                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(studyAllCourseAdapter);
            studyAllCourseAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, CourseInfoActivity.class);
                    intent.putExtra("title", studyAllCourseTypeList.get(position).getName());
                    intent.putExtra("type", 3);
                    intent.putExtra("id", studyAllCourseTypeList.get(position).getCastId());
                    context.startActivity(intent);
                }
            });
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllCourseActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            studyAllCourseAdapter.notifyDataSetChanged();
        }
    }

    private void setStudVideoData(StudyVideoHolder holder, int position) {
        if (studyVideoAdapter == null) {
            studyVideoAdapter = new BaseRecyclerAdapter<FirstDataBean.DataBean.TopListwoBean>(context, studyVedioTypeList, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, FirstDataBean.DataBean.TopListwoBean item, int position, boolean isScrolling) {

                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);
                    holder.setText(R.id.courseKind, item.getTypename());
                    if (item.getTypename().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getTypename().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getTypename().equals("直播中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                    else if (item.getTypename().equals("回放"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));


                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }

                    TextView money = holder.getView(R.id.money);
                    if (item.getTypename().equals("免费")) {
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


                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.studyCourseName, item.getName());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.money, item.getPrice());


                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(studyVideoAdapter);
            studyVideoAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, CourseInfoActivity.class);
                    intent.putExtra("title", studyVedioTypeList.get(position).getName());
                    intent.putExtra("type", 2);
                    intent.putExtra("id", studyVedioTypeList.get(position).getCastId());
                    context.startActivity(intent);
                }
            });
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LiveBroadcastActivity.class);
                    intent.putExtra("type", 2);
                    context.startActivity(intent);
                }
            });

        } else {
            studyVideoAdapter.notifyDataSetChanged();
        }

    }

    private void setSelectStudyData(StudySelectHolder holder, int position) {
        if (selectStudyAdapter == null) {
            selectStudyAdapter = new BaseRecyclerAdapter<FirstDataBean.DataBean.TopListoneBean>(context, studySelectedTypeList, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, FirstDataBean.DataBean.TopListoneBean item, int position, boolean isScrolling) {

                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    holder.setText(R.id.courseKind, item.getTypename());
                    if (item.getTypename().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getTypename().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getTypename().equals("直播中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));

                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }


                    TextView money = holder.getView(R.id.money);

                    if (item.getTypename().equals("免费")) {
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


                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.studyCourseName, item.getName());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.money, item.getPrice());

                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(selectStudyAdapter);
            selectStudyAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, CourseInfoActivity.class);
                    intent.putExtra("title", studySelectedTypeList.get(position).getName());
                    intent.putExtra("type", 1);
                    intent.putExtra("id", studySelectedTypeList.get(position).getCastId());
                    context.startActivity(intent);
                }
            });
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LiveBroadcastActivity.class);
                    intent.putExtra("type", 1);
                    context.startActivity(intent);
                }
            });
        } else {
            selectStudyAdapter.notifyDataSetChanged();
        }
    }

    private void setClassificationData(ClassificationHolder holder, int position) {
        if (adapter == null) {
            adapter = new BaseRecyclerAdapter<StudyHomeType>(context, studyHomeTypeList, R.layout.item_classification_study_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, StudyHomeType item, int position, boolean isScrolling) {

                    sizeUtils.setLayoutSize(holder.getView(R.id.iv_icon), 70, 70);
                    sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title), 25);
                    holder.setImageResource(R.id.iv_icon, item.getTypeImage());
                    holder.setText(R.id.tv_title, item.getTypeString());
                }
            };

            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false));
            holder.myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
            holder.myRecyclerView.addItemDecoration(new DividerGridItemDecoration(context));
            holder.myRecyclerView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(context, LiveBroadcastActivity.class);
                        intent.putExtra("type", 1);
                        context.startActivity(intent);

                      /*  Intent intent = new Intent(context, VideoActivity.class);
                        context.startActivity(intent);*/

                        break;
                    case 1:

                        intent = new Intent(context, LiveBroadcastActivity.class);
                        intent.putExtra("type", 2);
                        context.startActivity(intent);

                        break;
                    case 2:
                        context.startActivity(new Intent(context, AllCourseActivity.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, TrainSignUpActivity.class));
                        break;
                    case 4:
                        context.startActivity(new Intent(context, SchoolSignUpActivity.class));
                        break;
                    case 5:
                        context.startActivity(new Intent(context, GroupActivity_Activity.class));
                        break;
                    case 6:
                        context.startActivity(new Intent(context, NewsActivity.class));
                        break;
                    case 7:
                        if (Constans.uid.equals("")) {
                            intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("type", 1);
                            context.startActivity(intent);
                        } else {
                            context.startActivity(new Intent(context, PersonalCenterActivity.class));
                        }
                        break;
                }
            }
        });


    }

    private void setBannerData(BannerHolder holder, int position) {
        List<String> imagsList = new ArrayList<>();
        for (int i = 0; i < studyHomeBannerList.size(); i++) {
            imagsList.add(UrlFactory.baseImageUrl + studyHomeBannerList.get(i).getBa_img());
        }

        int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
        holder.CarouselImage.setPointViewVisible(true);//设置小圆点可见
        holder.CarouselImage.setPageIndicator(dots);//设置小圆点
        holder.CarouselImage.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        holder.CarouselImage.setManualPageable(true);//手动滑动

        holder.CarouselImage.setPages(new CBViewHolderCreator<NetImageLoaderHolder>() {
            @Override
            public NetImageLoaderHolder createHolder() {
                return new NetImageLoaderHolder();
            }
        }, imagsList);

        if (!holder.CarouselImage.isTurning()) {
            holder.CarouselImage.startTurning(3000);//自动轮滑
        }
        holder.CarouselImage.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (studyHomeBannerList.get(position).getTarget_type().equals("1")) {

                } else if (studyHomeBannerList.get(position).getTarget_type().equals("2")) {
                    Intent intent = new Intent(context, H5Activity.class);
                    intent.putExtra("url", UrlFactory.bannerDetail + "/detailId/" + studyHomeBannerList.get(position).getBa_id());
                    context.startActivity(intent);

                } else if (studyHomeBannerList.get(position).getTarget_type().equals("3")) {
                    Intent intent = new Intent(context, H5Activity.class);
                    intent.putExtra("url", studyHomeBannerList.get(position).getTarget_url());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_1:
                        case TYPE_2:
                        case TYPE_3:
                        case TYPE_4:
                        case TYPE_5:
                        case TYPE_6:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (results.get(position) == 1)
            return TYPE_1;
        else if (results.get(position) == 2)
            return TYPE_2;
        else if (results.get(position) == 3)
            return TYPE_3;
        else if (results.get(position) == 4)
            return TYPE_4;
        else if (results.get(position) == 5)
            return TYPE_5;
        else if (results.get(position) == 6)
            return TYPE_6;
        else if (results.get(position) == 7)
            return TYPE_7;
        else if (results.get(position) == 8)
            return TYPE_8;
        else
            return TYPE_9;
    }


    class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.CarouselImage)
        public ConvenientBanner CarouselImage;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ClassificationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;

        public ClassificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StudySelectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.more)
        TextView more;


        public StudySelectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StudyVideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.more)
        TextView more;

        public StudyVideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StudyAllCourse extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.more)
        TextView more;

        public StudyAllCourse(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SignUpHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.more)
        TextView more;

        public SignUpHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FamousSchoolHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.more)
        TextView more;

        public FamousSchoolHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ActivityHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.more)
        TextView more;

        public ActivityHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
