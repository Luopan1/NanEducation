package www.test720.com.naneducation.fragment;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.SchoolCast;

/**
 * Created by LuoPan on 2017/10/20 13:33.
 */

public class SchooolSingUpGradeFragment extends BaseFragment {
    @BindView(R.id.prieceListView)
    ListView mPrieceListView;
    private List<SchoolCast.DataBean.DetailBean.ListBean> mPricesLists = new ArrayList<>();
    CommonAdaper<SchoolCast.DataBean.DetailBean.ListBean> pricesAdapter;
    private List<SchoolCast.DataBean.DetailBean.ListBean> lists;

    @Override
    protected void initView() {

    }

    public SchooolSingUpGradeFragment() {
    }

    public SchooolSingUpGradeFragment(List<SchoolCast.DataBean.DetailBean.ListBean> id) {
        this.lists = id;
    }

    @Override
    protected void initData() {

        mPricesLists.addAll(lists);
        setAdapter();


    }

    private void setAdapter() {
        if (pricesAdapter == null) {

            pricesAdapter = new CommonAdaper<SchoolCast.DataBean.DetailBean.ListBean>(getActivity(), mPricesLists, R.layout.item_school_sign_up_grade_item) {
                @Override
                public void convert(ViewHolder holder, SchoolCast.DataBean.DetailBean.ListBean item, int position) {
                    holder.setText(R.id.PriceName, item.getName());
                    holder.setText(R.id.Prices, "￥" + item.getPrice());
                }
            };
            mPrieceListView.setAdapter(pricesAdapter);
        } else {
            pricesAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_school_sign_up_grade;
    }


}
