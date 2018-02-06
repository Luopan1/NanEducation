package www.test720.com.naneducation.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.TrainCourseInfoActivity;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.TrainMainAdapter;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.TrainList;
import www.test720.com.naneducation.http.UrlFactory;

/**
 * Created by LuoPan on 2017/10/18 11:39.
 */

public class TrainCourseFragment extends BaseFragment {
    public String index;
    private int mPos = 0;

    public TrainCourseFragment(String id) {
        this.index = id;
    }

    public TrainCourseFragment() {

    }
    List<TrainList.DataBean.MuenBean> mainLists = new ArrayList<>();
    List<TrainList.DataBean.ListBean> dataList = new ArrayList<>();
    public List<TrainList.DataBean.MuenBean.ZiBean> spinnerLists = new ArrayList<>();
    @BindView(R.id.mainListView)
    ListView mMainListView;
    @BindView(R.id.sort_all)
    TextView mSortAll;
    @BindView(R.id.spinner)
    NiceSpinner mSpinner;
    @BindView(R.id.moreRecyclerView)
    RecyclerView mMoreRecyclerView;
    private CommonAdaper<TrainList.DataBean.MuenBean.ZiBean> mSpinnerAdapter;
    BaseRecyclerAdapter<TrainList.DataBean.ListBean> moreRecyclerViewAdapter;
    private TrainMainAdapter mClassifyMainAdapter;
    private List<String> mStringList = new ArrayList<>();
    HttpParams mParams = new HttpParams();
    private int count = 0;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        count++;
        mParams.put("trainId", index);
        mSubscription = mHttpUtils.getData(UrlFactory.trainCourse, mParams, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {


            @Override
            public void onStart() {
                showLoadingDialog("");
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
                TrainList trainList = gson.fromJson(s, TrainList.class);

                mainLists.clear();
                mainLists.addAll(trainList.getData().getMuen());
                dataList.clear();
                dataList.addAll(trainList.getData().getList());
                if (count == 1) {
                    spinnerLists.clear();
                    mStringList.clear();
                    if (mainLists.size() != 0) {
                        spinnerLists.addAll(mainLists.get(mPos).getZi());
                        for (int i = 0; i < mainLists.get(mPos).getZi().size(); i++) {
                            mStringList.add(mainLists.get(mPos).getZi().get(i).getName());
                        }
                        mSpinner.attachDataSource(mStringList);
                    }
              /*  mStringList = new ArrayList<>();

                mStringList.clear();*/
                }
                setAdapter();
            }
        });


    }

    private void setAdapter() {
        if (mClassifyMainAdapter == null) {
            mClassifyMainAdapter = new TrainMainAdapter(getActivity(), mainLists);
            mMainListView.setAdapter(mClassifyMainAdapter);

        } else {
            mClassifyMainAdapter.notifyDataSetChanged();
        }


        if (moreRecyclerViewAdapter == null) {
            moreRecyclerViewAdapter = new BaseRecyclerAdapter<TrainList.DataBean.ListBean>(getActivity(), dataList, R.layout.item_train_course_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, TrainList.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setImageByUrl(R.id.schoolImage, UrlFactory.baseImageUrl + item.getC_logo());
                    holder.setText(R.id.title, item.getC_name());
                    holder.setText(R.id.oldPriece, "¥" + item.getC_oldprice());
                    holder.setText(R.id.newPriece, "¥" + item.getC_price());
                    holder.setText(R.id.schoolStudyTime, "每周" + item.getC_openwenk() + "   " + item.getC_opentime());
                }
            };

            moreRecyclerViewAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    bundle.putString("id", dataList.get(position).getC_id());
                    jumpToActivity(TrainCourseInfoActivity.class, bundle, false);
                }
            });

            mMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mMoreRecyclerView.setAdapter(moreRecyclerViewAdapter);
        } else {
            moreRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void setListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2017/10/18  刷新界面
                mSortAll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.all_corners_system_color));
                mParams.put("typeZid", spinnerLists.get(position).getT_id());
                initData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPos = position;

                mClassifyMainAdapter.setSelectItem(position);
                mClassifyMainAdapter.notifyDataSetChanged();
                count = 0;
                spinnerLists.clear();
                mStringList.clear();
                if (position == 0) {
                    mSortAll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.all_corners_system_color));
                  /*  mParams.put("typeZid", 0);*/
                }
                mSortAll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.all_corners_base_color));
                spinnerLists.addAll(mainLists.get(position).getZi());

                for (int i = 0; i < mainLists.get(position).getZi().size(); i++) {
                    mStringList.add(mainLists.get(position).getZi().get(i).getName());
                }
                if (mStringList.size() != 0)
                    mSpinner.attachDataSource(mStringList);
                mParams.put("typeZid", 0);
                mParams.put("typeId", mainLists.get(position).getT_id());
                initData();

            }
        });
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.train_course_fragment;
    }


    @OnClick(R.id.sort_all)
    public void onClick() {
        mSortAll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.all_corners_base_color));
        mParams.put("typeZid", 0);
        initData();
    }


}
