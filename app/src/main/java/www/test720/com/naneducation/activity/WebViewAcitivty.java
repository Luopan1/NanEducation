package www.test720.com.naneducation.activity;

import android.content.Intent;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.view.ProgressWebview;

public class WebViewAcitivty extends BaseToolbarActivity {


    @BindView(R.id.webView)
    ProgressWebview mWebView;
    private String mUri;

    @Override
    protected int getContentView() {
        return R.layout.activity_web_view_acitivty;
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
        setTitleColor(R.color.white);
        setToolbarColor(R.color.base_color);
        Intent intent = getIntent();
        mUri = intent.getStringExtra("url");

    }


}
