package www.test720.com.naneducation.activity;

import android.content.Intent;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.view.ProgressWebview;

public class NewsInfoWebViewActivity extends BaseToolbarActivity {


    @BindView(R.id.newsTitle)
    TextView mNewsTitle;
    @BindView(R.id.newsTime)
    TextView mNewsTime;
    @BindView(R.id.webView)
    ProgressWebview mWebView;
    private String mUri;

    @Override
    protected int getContentView() {
        return R.layout.activity_news_info_web_view;
    }

    @Override
    protected void initData() {
        mWebView.loadUrl(mUri);
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
        initToobar("新闻");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
        Intent intent = getIntent();
        mUri = intent.getStringExtra("url");
        mNewsTitle.setText(intent.getStringExtra("title"));
        mNewsTime.setText(intent.getStringExtra("time"));

    }
}
