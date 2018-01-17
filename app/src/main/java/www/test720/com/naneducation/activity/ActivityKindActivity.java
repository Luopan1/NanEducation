package www.test720.com.naneducation.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.ActivityType;
import www.test720.com.naneducation.http.UrlFactory;

public class ActivityKindActivity extends BaseToolbarActivity {


    @BindView(R.id.lisiView)
    ListView mLisiView;

    List<ActivityType.DataBean.ListBean> mLists = new ArrayList<>();
    CommonAdaper<ActivityType.DataBean.ListBean> adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_kind;
    }

    @Override
    protected void initData() {
        mSubscription = mHttpUtils.getData(UrlFactory.traintypeList, new HttpParams(), 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                ActivityType type = gson.fromJson(s, ActivityType.class);
                mLists.addAll(type.getData().getList());
                setAdapter();
            }
        });

    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new CommonAdaper<ActivityType.DataBean.ListBean>(this, mLists, R.layout.item_selct_mainlist) {
                @Override
                public void convert(ViewHolder holder, ActivityType.DataBean.ListBean item, int position) {
                    holder.setText(R.id.mainitem_txt, item.getName());
                    holder.getView(R.id.mainitem_img).setVisibility(View.INVISIBLE);
                }
            };
            mLisiView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {
        mLisiView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityKindActivity.this, ReleaseGroupActivity_Activity.class);
                intent.putExtra("kind", mLists.get(position));
                setResult(0x0007, intent);
                finish();
            }
        });
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;

    }

    @Override
    protected void initView() {
        initToobar("选择活动类型");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }
}
