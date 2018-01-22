package www.test720.com.naneducation.personcenteractivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
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
import www.test720.com.naneducation.activity.AddNewSignUpPersonActivity;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.BindUser;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class MyBindInfoActivity extends BaseToolbarActivity {

    @BindView(R.id.commonUseSignUpListView)
    ListView mCommonUseSignUpListView;
    @BindView(R.id.addNewSignUpPeoPle)
    Button mAddNewSignUpPeoPle;
    List<BindUser.DataBean.UserlistBean> mPersonLists = new ArrayList<>();
    CommonAdaper<BindUser.DataBean.UserlistBean> commonAdapter;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    public static int refresh = 1;
    private int mType;
    private PopupWindow mPopWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_bind_info;
    }

    public void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("type", 1);
        mSubscription = mHttpUtils.getData(UrlFactory.bindUsername, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                mSubscription.unsubscribe();
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onNext(String s) {

                mPersonLists.clear();
                Gson gson = new Gson();
                BindUser bindUser = gson.fromJson(s, BindUser.class);
                mPersonLists.addAll(bindUser.getData().getUserlist());
                setAdapter();
            }
        });
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh == 2) {
            mRefreshLayout.startRefresh();
            refresh = 1;
        }
    }

    private void setAdapter() {
        if (commonAdapter == null) {
            commonAdapter = new CommonAdaper<BindUser.DataBean.UserlistBean>(this, mPersonLists, R.layout.item_add_person_item2) {
                @Override
                public void convert(ViewHolder holder, BindUser.DataBean.UserlistBean item, final int position) {
                    holder.setText(R.id.personName, item.getUsername());
                    holder.getView(R.id.more).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("name", mPersonLists.get(position).getUsername());
                            bundle.putString("phone", mPersonLists.get(position).getUser_phone());
                            bundle.putString("id", mPersonLists.get(position).getKid());
                            jumpToActivity(AddNewSignUpPersonActivity.class, bundle, false);
                        }
                    });
                }
            };
            mCommonUseSignUpListView.setAdapter(commonAdapter);

        } else {
            commonAdapter.notifyDataSetChanged();
        }

    }

    private void changerUserNickName(final String kid, String nickName) {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.pop_nick_name, null);
        final EditText et_nickName = (EditText) popView.findViewById(R.id.editTextNickName);
        TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
        TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
        et_nickName.setText(nickName);
        et_nickName.setSelection(nickName.length());// 光标移动到最后
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = et_nickName.getText().toString().trim();
                if (mName.isEmpty()) {
                    ShowToast("昵称不能为空");
                } else {
                    HttpParams params = new HttpParams();
                    params.put("name", mName);
                    params.put("kid", kid);
                    mSubscription = mHttpUtils.getData(UrlFactory.bindUserEdit, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                        @Override
                        public void onStart() {
                            showLoadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            cancleLoadingDialog();
                            mPopWindow.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            JSONObject obj = JSON.parseObject(s);
                            if (obj.getInteger("code") == 1) {
                                cancleLoadingDialog();
                                mPopWindow.dismiss();
                                mRefreshLayout.startRefresh();
                            }
                        }
                    });
                }
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });


        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);

        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


    }

    @Override
    protected void setListener() {
        mCommonUseSignUpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mType == 1) {
                    BindUser.DataBean.UserlistBean userlistBean = mPersonLists.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("person", userlistBean);
                    setResult(0x0006, intent);
                    finish();
                }

            }
        });


        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

                getData();
            }

            @Override
            public void onFinishRefresh() {

            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;

    }

    @Override
    protected void initView() {
        initToobar("添加报名人");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);

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


        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
    }


    @OnClick(R.id.addNewSignUpPeoPle)
    public void onClick() {
        Intent intent = new Intent(this, AddNewSignUpPersonActivity.class);
        startActivityForResult(intent, 0x00002);

    }

    @Override
    public void onBackPressed() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
