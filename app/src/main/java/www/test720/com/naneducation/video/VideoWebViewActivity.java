package www.test720.com.naneducation.video;

import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;

public class VideoWebViewActivity extends BaseToolbarActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    private String mVideoPath;

    @Override
    protected int getContentView() {
        return R.layout.activity_video_web_view;
    }

    @Override
    public void setFullScreen() {
        isFullScreen = true;
        PORTRAIT = false;
    }

    @Override
    protected void initData() {
        mVideoPath = getIntent().getStringExtra("path");
        WebSettings webSettings = mWebView.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        mWebView.loadUrl(mVideoPath);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
