package www.test720.com.naneducation.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.LocatedCity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class ChooseAReleaseAreActivity extends BaseToolbarActivity {


    @BindView(R.id.locatedCity)
    TextView mLocatedCity;
    @BindView(R.id.areadyOpenRecyclerView)
    RecyclerView mAreadyOpenRecyclerView;
    ArrayList<LocatedCity.DataBean.ListBean> citiesLists = new ArrayList<>();
    BaseRecyclerAdapter<LocatedCity.DataBean.ListBean> baseRecylerAdapter;
    boolean isOpen = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_area;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        mSubscription = mHttpUtils.getData(UrlFactory.location, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                Gson gson = new Gson();
                LocatedCity locatedCity = gson.fromJson(s, LocatedCity.class);
                citiesLists.addAll(locatedCity.getData().getList());
                setAdapter();
                //                doCompare();

            }
        });

    }

    private void setAdapter() {
        if (baseRecylerAdapter == null) {
            baseRecylerAdapter = new BaseRecyclerAdapter<LocatedCity.DataBean.ListBean>(this, citiesLists, R.layout.item_licated_city_item) {
                @Override
                public void convert(BaseRecyclerHolder holder, LocatedCity.DataBean.ListBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.openCity, item.getC_name() + item.getQ_name());
                }
            };
            mAreadyOpenRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            mAreadyOpenRecyclerView.setAdapter(baseRecylerAdapter);

            baseRecylerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("city", citiesLists.get(position).getC_name());
                    intent.putExtra("district", citiesLists.get(position).getQ_name());
                    intent.putExtra("cityBean", citiesLists.get(position));
                    setResult(0x0005, intent);
                    finish();
                }
            });

        } else {
            baseRecylerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar(R.drawable.fanhuibai, "选择区县", -1);
        setTitleColor(R.color.white);
        setToolbarColor(R.color.base_color);

        mLocatedCity.setText(Constans.City + Constans.district);
    }


    @OnClick(R.id.locatedCity)
    public void onClick() {
    }
}
