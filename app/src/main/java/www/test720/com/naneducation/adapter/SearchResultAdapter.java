package www.test720.com.naneducation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.AllCourseActivity;
import www.test720.com.naneducation.activity.CourseInfoActivity;
import www.test720.com.naneducation.activity.LiveBroadcastActivity;
import www.test720.com.naneducation.bean.LiveSearchResult;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.utils.SizeUtils;
import www.test720.com.naneducation.view.SpaceItemDecoration;

/**
 * Created by LuoPan on 2017/10/12 15:39.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_1 = 0xff01;
    private static final int TYPE_2 = 0xff02;
    private static final int TYPE_3 = 0xff03;
    private static final int TYPE_4 = 0xff04;
    private RecyclerView recyclerView;
    private List<Integer> results;
    private Activity context;
    private SizeUtils sizeUtils;

    private List<LiveSearchResult.DataBean.LiveBean> liveCastLists;
    private List<LiveSearchResult.DataBean.LuboBean> vedioLists;
    private List<LiveSearchResult.DataBean.CourseBean> allCourseLists;

    private BaseRecyclerAdapter<LiveSearchResult.DataBean.LiveBean> liveCastAdapter;
    private BaseRecyclerAdapter<LiveSearchResult.DataBean.LuboBean> vedioAdapter;
    private BaseRecyclerAdapter<LiveSearchResult.DataBean.CourseBean> allCourseeAdapter;
    String courseKind;

    public SearchResultAdapter(Activity context, List<Integer> results, List<LiveSearchResult.DataBean.LiveBean> liveCastLists, List<LiveSearchResult.DataBean.LuboBean> vedioLists, List<LiveSearchResult.DataBean.CourseBean> allCourseLists) {
        this.context = context;
        this.liveCastLists = liveCastLists;
        this.vedioLists = vedioLists;
        this.allCourseLists = allCourseLists;
        this.results = results;
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
                return new LiveBroadCastHolder(LayoutInflater.from(context).inflate(R.layout.item_video_study, null));
            case TYPE_2:
                return new VideoHolder(LayoutInflater.from(context).inflate(R.layout.item_video_study, null));
            case TYPE_3:
                return new AllCourseHolder(LayoutInflater.from(context).inflate(R.layout.item_video_study, null));
            default:
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LiveBroadCastHolder) {
            setLiveBroadCastData((LiveBroadCastHolder) holder, position);
        } else if (holder instanceof VideoHolder) {
            setVideoData((VideoHolder) holder, position);
        } else if (holder instanceof AllCourseHolder) {
            setAllCourseData((AllCourseHolder) holder, position);
        }

    }

    private void setAllCourseData(AllCourseHolder holder, int position) {
        if (allCourseeAdapter == null) {
            holder.mVideoKind.setText("套课");

            allCourseeAdapter = new BaseRecyclerAdapter<LiveSearchResult.DataBean.CourseBean>(context, allCourseLists, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, LiveSearchResult.DataBean.CourseBean item, int position, boolean isScrolling) {
                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    holder.setText(R.id.courseKind, item.getLivetype());
                    if (item.getLivetype().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getLivetype().equals("直播中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                    else if (item.getLivetype().equals("已结束"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.color_white_66ffffff));
                    else if (item.getLivetype().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getLivetype().equals("回放"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));
                    else if (item.getLivetype().equals("套课"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));
                    else if (item.getLivetype().equals("进行中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));


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
                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }

                    holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.taoke));
                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.studyCourseName, item.getName());
                    holder.setText(R.id.money, item.getPrice());
                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(allCourseeAdapter);

            allCourseeAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, CourseInfoActivity.class);
                    intent.putExtra("title", allCourseLists.get(position).getName());
                    intent.putExtra("type", 3);
                    intent.putExtra("id", allCourseLists.get(position).getCastId());
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
            allCourseeAdapter.notifyDataSetChanged();

        }

    }

    private void setVideoData(VideoHolder holder, int position) {
        if (vedioAdapter == null) {
            holder.mVideoKind.setText("录播");
            vedioAdapter = new BaseRecyclerAdapter<LiveSearchResult.DataBean.LuboBean>(context, vedioLists, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, LiveSearchResult.DataBean.LuboBean item, int position, boolean isScrolling) {
                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    holder.setText(R.id.courseKind, item.getLivetype());

                    TextView textView = holder.getView(R.id.courseKind);
                    if (item.getLivetype().equals("免费")) {
                        textView.setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    } else if (item.getLivetype().equals("回放")) {
                        textView.setBackgroundColor(context.getResources().getColor(R.color.huifang));
                    }

                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }

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

                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.studyCourseName, item.getName());
                    holder.setText(R.id.money, item.getPrice());
                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(vedioAdapter);

            vedioAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent(context, CourseInfoActivity.class);
                    intent.putExtra("title", vedioLists.get(position).getName());
                    intent.putExtra("type", 2);
                    intent.putExtra("id", vedioLists.get(position).getCastId());
                    intent.putExtra("path", vedioLists.get(position).getBack_url());
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
            vedioAdapter.notifyDataSetChanged();

        }

    }

    private void setLiveBroadCastData(LiveBroadCastHolder holder, int position) {
        if (liveCastAdapter == null) {
            holder.mVideoKind.setText("直播");
            liveCastAdapter = new BaseRecyclerAdapter<LiveSearchResult.DataBean.LiveBean>(context, liveCastLists, R.layout.item_select_stydy_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, LiveSearchResult.DataBean.LiveBean item, int position, boolean isScrolling) {
                    sizeUtils.setLayoutSize(holder.getView(R.id.courseImage), 325, 210);

                    holder.setText(R.id.courseKind, item.getLivetype());
                    if (item.getLivetype().equals("预告"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.yugao));
                    else if (item.getLivetype().equals("直播中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));
                    else if (item.getLivetype().equals("已结束"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.color_white_66ffffff));
                    else if (item.getLivetype().equals("免费"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.mianfei));
                    else if (item.getLivetype().equals("回放"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));
                    else if (item.getLivetype().equals("套课"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.huifang));
                    else if (item.getLivetype().equals("进行中"))
                        holder.getView(R.id.courseKind).setBackgroundColor(context.getResources().getColor(R.color.base_color));

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

                    if (item.getTc_name().length() > 3) {
                        item.setTc_name(item.getTc_name().substring(0, 3) + "...");
                    }

                    holder.setImageByUrl(R.id.courseImage, UrlFactory.baseImageUrl + item.getLogo());
                    holder.setImageByUrl(R.id.teacherPhoto, UrlFactory.baseImageUrl + item.getTc_head());
                    holder.setText(R.id.teacherName, item.getTc_name());
                    holder.setText(R.id.studyCourseName, item.getName());
                    holder.setText(R.id.money, item.getPrice());
                }
            };
            holder.myRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            holder.myRecyclerView.addItemDecoration(new SpaceItemDecoration(0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15)));
            holder.myRecyclerView.setAdapter(liveCastAdapter);
            liveCastAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Log.e("TAG+++++++", liveCastLists.size() + "____" + position);
                    Intent intent = new Intent(context, CourseInfoActivity.class);
                    intent.putExtra("title", liveCastLists.get(position).getName());
                    intent.putExtra("type", 1);
                    intent.putExtra("id", liveCastLists.get(position).getCastId());
                    intent.putExtra("room", liveCastLists.get(position).getRoom_mun());
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
            liveCastAdapter.notifyDataSetChanged();

        }
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
                    int type = getItemViewType(1);
                    switch (type) {
                        case TYPE_1:
                        case TYPE_2:
                        case TYPE_3:
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
        else
            return TYPE_4;
    }

    class LiveBroadCastHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.videoKind)
        TextView mVideoKind;
        @BindView(R.id.more)
        TextView more;

        public LiveBroadCastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.videoKind)
        TextView mVideoKind;
        @BindView(R.id.more)
        TextView more;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AllCourseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_recycler_view)
        public RecyclerView myRecyclerView;
        @BindView(R.id.videoKind)
        TextView mVideoKind;
        @BindView(R.id.more)
        TextView more;

        public AllCourseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
