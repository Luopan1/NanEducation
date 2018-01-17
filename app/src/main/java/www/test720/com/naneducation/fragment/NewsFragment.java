package www.test720.com.naneducation.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.TrainCourse;
import www.test720.com.naneducation.view.ProgressWebview;

import static www.test720.com.naneducation.R.id.recyclerView;

/**
 * Created by LuoPan on 2017/10/18 11:27.
 */

public class NewsFragment extends BaseFragment {

    String index;
    @BindView(R.id.webView)
    ProgressWebview mWebView;
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    private static boolean iShowRecyclerView = false;
    private BaseRecyclerAdapter<TrainCourse.DataBean.DetailBean.UserlistBean> mSignUpAdapter;
    public static NewsFragment mNewsFragment;
    List<TrainCourse.DataBean.DetailBean.UserlistBean> userlists = new ArrayList<>();

    @SuppressLint({"NewApi", "ValidFragment"})
    public NewsFragment() {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public NewsFragment(String i, boolean ishow, List<TrainCourse.DataBean.DetailBean.UserlistBean> userlists) {
        index = i;
        iShowRecyclerView = ishow;
        this.userlists = userlists;
        Log.e("TAG++", userlists.toString());
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public NewsFragment(String i, boolean ishow) {
        index = i;
        iShowRecyclerView = ishow;
    }

    public NewsFragment getmNewsFragment(String i, boolean isshow) {
        return getNewsFragment(i, isshow);
    }

    public static NewsFragment getNewsFragment(String i, boolean isshow) {
        if (mNewsFragment == null) {
            synchronized (NewsFragment.class) {
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment(i, isshow);
                }
            }
        }
        return mNewsFragment;
    }

    public static NewsFragment getNewsFragment(String i, boolean isshow, List<TrainCourse.DataBean.DetailBean.UserlistBean> userlists) {
        if (mNewsFragment == null) {
            synchronized (NewsFragment.class) {
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment(i, isshow, userlists);
                }
            }
        }
        return mNewsFragment;
    }


    @Override
    protected void initView() {

        mWebView.loadUrl(index);
        if (iShowRecyclerView) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        setAdapter();

    }

    private void setAdapter() {
        if (mSignUpAdapter == null) {
            mSignUpAdapter = new BaseRecyclerAdapter<TrainCourse.DataBean.DetailBean.UserlistBean>(getActivity(), userlists, R.layout.item_order_sign_up_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, TrainCourse.DataBean.DetailBean.UserlistBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.oderSignUpName, item.getUsername());
                    holder.setText(R.id.orderSignUpID, "ID: " + item.getBinduser_id());
                }
            };
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setAdapter(mSignUpAdapter);
        } else {
            mSignUpAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.train_webview_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
