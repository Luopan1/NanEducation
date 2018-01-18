package www.test720.com.naneducation.personcenteractivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.CustomerServiceActivity;
import www.test720.com.naneducation.adapter.BaseRecyclerAdapter;
import www.test720.com.naneducation.adapter.BaseRecyclerHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.SignMembers;
import www.test720.com.naneducation.http.UrlFactory;

public class SignMembersActivity extends BaseToolbarActivity {


    @BindView(R.id.memberRecyclerView)
    RecyclerView mMemberRecyclerView;
    private String mId;
    private BaseRecyclerAdapter<SignMembers.DataBean> mSignAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_members;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("actId", mId);
        mSubscription = mHttpUtils.getData(UrlFactory.actSignDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                SignMembers members = gson.fromJson(s, SignMembers.class);
                List<SignMembers.DataBean> data = members.getData();
                setAdapter(data);
            }
        });
    }

    private void setAdapter(List<SignMembers.DataBean> members) {
        if (mSignAdapter == null) {
            mSignAdapter = new BaseRecyclerAdapter<SignMembers.DataBean>(this, members, R.layout.item_sign_members) {
                @Override
                public void convert(BaseRecyclerHolder holder, final SignMembers.DataBean item, int position, boolean isScrolling) {
                    holder.setText(R.id.name, item.getUsername());
                    holder.setText(R.id.phone, item.getUser_phone());
                    holder.getView(R.id.phone).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final MaterialDialog dialog = new MaterialDialog(SignMembersActivity.this);
                            dialog.content("是否拨打客服电话" + item.getUser_phone().trim())//
                                    .contentTextSize(14)
                                    .titleTextSize(16)
                                    .btnText("拨打电话", "取消")//
                                    .showAnim(new BounceTopEnter())//
                                    .show();
                            dialog.setOnBtnClickL(new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                    if (ActivityCompat.checkSelfPermission(SignMembersActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        Toast.makeText(SignMembersActivity.this, "拨打电话权限已关闭，请打开权限", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    SignMembersActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + item.getUser_phone().trim())));
                                }
                            }, new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });

                }
            };
            mMemberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mMemberRecyclerView.setAdapter(mSignAdapter);
        } else {
            mSignAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        setTitleColor(R.color.white);
        setToolbarColor(R.color.base_color);
        initToobar("成员名单", R.drawable.fanhuibai);
        mId = getIntent().getStringExtra("id");
    }
}
