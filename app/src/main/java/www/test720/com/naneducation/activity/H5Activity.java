package www.test720.com.naneducation.activity;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.view.ProgressWebview;


public class H5Activity extends BaseToolbarActivity {


    @BindView(R.id.webView)
    ProgressWebview mWebView;

    @Override
    protected int getContentView() {
        return R.layout.activity_h5;
    }

    @Override
    protected void initData() {
        mWebView.loadUrl(getIntent().getStringExtra("url"));
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
        initToobar("详情", R.drawable.fanhuibai);
    }
}
