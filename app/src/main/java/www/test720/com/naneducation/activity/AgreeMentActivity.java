package www.test720.com.naneducation.activity;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.view.ProgressWebview;

public class AgreeMentActivity extends BaseToolbarActivity {


    @BindView(R.id.webView)
    ProgressWebview mWebView;
    private int mType;

    @Override
    protected int getContentView() {
        return R.layout.activity_agree_ment;
    }

    @Override
    protected void initData() {

        mWebView.loadUrl(UrlFactory.agreement + "/type/" + mType);
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
        initToobar(getIntent().getStringExtra("title"), R.drawable.fanhuibai);
        setTitleColor(R.color.white);
        setToolbarColor(R.color.base_color);
        mType = getIntent().getIntExtra("type", 1);
    }
}
