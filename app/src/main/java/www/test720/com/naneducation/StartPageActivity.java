package www.test720.com.naneducation;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.activity.MainActivity;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.SPUtils;
import www.test720.com.naneducation.view.CountDownProgressView;

public class StartPageActivity extends BaseToolbarActivity {

    @BindView(R.id.imageview)
    ImageView mImageview;
    @BindView(R.id.countdownProgressView)
    CountDownProgressView mCountdownProgressView;

    @Override
    protected int getContentView() {
        return R.layout.activity_start_page;
    }

    @Override
    protected void initData() {
        mSubscription = mHttpUtils.getData(UrlFactory.startPage, new HttpParams(), 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                JSONObject obj = JSON.parseObject(s);
                if (obj.getInteger("code") == 1) {
                    String img = obj.getJSONObject("data").getString("imgSrc");
                    Glide.with(getApplicationContext()).load(UrlFactory.baseImageUrl + img).into(mImageview);
                }
            }
        });
    }

    @Override
    protected void setListener() {

        mCountdownProgressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress == 0) {
                    if (!SPUtils.getIsInstall()) {
                        jumpToActivity(SplashActivity.class, true);
                    } else {
                        jumpToActivity(MainActivity.class, true);
                    }
                }
            }
        });
        mCountdownProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SPUtils.getIsInstall()) {
                    jumpToActivity(SplashActivity.class, true);
                } else {
                    jumpToActivity(MainActivity.class, true);
                    mCountdownProgressView.stop();
                }
            }
        });
    }

    @Override
    public void setFullScreen() {
        isFullScreen = true;
        PORTRAIT = true;
    }

    @Override
    protected void initView() {
        mCountdownProgressView.setTimeMillis(3000);
        mCountdownProgressView.start();
    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }
}
