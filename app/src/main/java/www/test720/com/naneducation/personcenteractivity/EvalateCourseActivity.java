package www.test720.com.naneducation.personcenteractivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.RatingBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;

public class EvalateCourseActivity extends BaseToolbarActivity {


    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.commit)
    Button mCommit;
    private String mCid;
    private float rate;
    public static int index = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_evalate_course;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
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
        initToobar("课程评价");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
        Intent intent = getIntent();
        mCid = intent.getStringExtra("id");
    }

    @OnClick(R.id.commit)
    public void onClick() {
        HttpParams params = new HttpParams();
        params.put("uid", Constans.uid);
        params.put("cid", mCid);
        params.put("grade", rate);
        mSubscription = mHttpUtils.getData(UrlFactory.userEvalaute, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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

                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                if (obj.getInteger("code") == 1) {
                    index = 1;
                    finish();
                }
            }
        });
    }
}
