package www.test720.com.naneducation.video;

import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;

public class VideoWebViewActivity extends BaseToolbarActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.hintText)
    TextView mHintText;
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
        mWebView.setWebViewClient(new WVClient());
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


    private class WVClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //在当前Activity打开
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //https忽略证书问题
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            LogUtils.e(view.getContentHeight() + "");
            if (view.getContentHeight() <= 0) {
                mHintText.setVisibility(View.VISIBLE);
            } else {
                mHintText.setVisibility(View.GONE);
            }

            super.onPageFinished(view, url);

        }

    }

}
