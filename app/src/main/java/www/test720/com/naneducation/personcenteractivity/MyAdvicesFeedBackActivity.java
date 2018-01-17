package www.test720.com.naneducation.personcenteractivity;

import android.widget.Button;
import android.widget.EditText;

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

public class MyAdvicesFeedBackActivity extends BaseToolbarActivity {
    @BindView(R.id.advices)
    EditText mAdvices;
    @BindView(R.id.sendFeedBack)
    Button mSendFeedBack;


    @Override
    protected int getContentView() {
        return R.layout.activity_my_advices_feed_back;
    }

    @Override
    protected void initData() {

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
        initToobar("意见反馈");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }


    @OnClick(R.id.sendFeedBack)
    public void onClick() {
        if (mAdvices.getText().toString().trim().isEmpty()) {
            ShowToast("请输入反馈内容");
        } else {
            HttpParams params = new HttpParams();
            params.put("uid", Constans.uid);
            params.put("content", mAdvices.getText().toString().trim());
            mSubscription = mHttpUtils.getData(UrlFactory.userFeedBack, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

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
                    finish();
                }
            });
        }
    }
}
