package www.test720.com.naneducation.personcenteractivity;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;

public class AboutUsActivity extends BaseToolbarActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
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
        initToobar("关于我们");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);
    }
}
