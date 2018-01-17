package www.test720.com.naneducation.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.SchoolSignUpGradeInfoActivty;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.SchoolInfo;

/**
 * Created by LuoPan on 2017/10/20 11:41.
 */

public class SchoolSignUpFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    List<SchoolInfo.DataBean.DetailBean.GradeListBean> mGradeLists = new ArrayList<>();
    BaseRecyclerAdapter<SchoolInfo.DataBean.DetailBean.GradeListBean> schoolAdapter;
    public SchoolInfo info;

    private static SchoolSignUpFragment fragment;

    public SchoolSignUpFragment() {
    }

    public SchoolSignUpFragment(SchoolInfo info) {
        this.info = info;
    }

    public static SchoolSignUpFragment getInstance(SchoolInfo info) {
        if (fragment == null) {
            fragment = new SchoolSignUpFragment(info);
        }
        return fragment;
    }

    @Override
    protected void initView() {
        try {
            mGradeLists.addAll(info.getData().getDetail().getGradeList());
        } catch (Exception e) {

        }
        setAdapter();
    }

    private void setAdapter() {
        if (schoolAdapter == null) {
            schoolAdapter = new BaseRecyclerAdapter<SchoolInfo.DataBean.DetailBean.GradeListBean>(getActivity(), mGradeLists, R.layout.item_school_sign_up_fragemnt) {
                @Override
                public void convert(BaseRecyclerHolder holder, SchoolInfo.DataBean.DetailBean.GradeListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.grade, item.getY_name());
                    holder.setText(R.id.LeftCount, "剩余名额：" + item.getOvermun());
                    holder.setText(R.id.Prices, "官方价格：" + item.getY_yprice() + " | 平台价格:" + item.getY_xprice());
                }
            };
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(schoolAdapter);

            schoolAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", info.getData().getDetail().getS_name());
                    bundle.putString("id", mGradeLists.get(position).getY_id());
                    jumpToActivity(SchoolSignUpGradeInfoActivty.class, bundle, false);
                }
            });

        } else {
            schoolAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragemnt_sign_up;
    }


}
