package www.test720.com.naneducation.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.view.ScrollWebView;

/**
 * Created by LuoPan on 2017/10/18 11:27.
 */

public class NewsScrollWebViewFragment extends BaseFragment {

    String index;
    @BindView(R.id.webView)
    ScrollWebView mWebView;
    @BindView(R.id.immediatelySignUp)
    Button mImmediatelySignUp;


    @SuppressLint({"NewApi", "ValidFragment"})
    public NewsScrollWebViewFragment() {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public NewsScrollWebViewFragment(String i) {

        index = i;
    }

    @Override
    protected void initView() {
        mWebView.loadUrl(index);

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mWebView.setOnScrollChangedCallback(new ScrollWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                LogUtils.e(dx);
                if (dx < 0) {

                    mImmediatelySignUp.setVisibility(View.VISIBLE);
                } else if (dx > 0) {
                    LogUtils.e("dx>0");
                    mImmediatelySignUp.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.scroll_webview_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.immediatelySignUp)
    public void onClick() {
    }
}
