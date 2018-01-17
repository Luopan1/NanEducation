package www.test720.com.naneducation.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.ninepassword.util.StringUtil;
import www.test720.com.naneducation.ninepassword.util.vies.LocusPassWordView;
import www.test720.com.naneducation.personcenteractivity.SetUserPayMentPassworldActivtiy;
import www.test720.com.naneducation.utils.SPUtils;

public class AddPassWorldActivity extends BaseToolbarActivity {


    @BindView(R.id.LocusPassWordView)
    LocusPassWordView mLocusPassWordView;
    @BindView(R.id.tvReset)
    Button mTvReset;
    @BindView(R.id.tvSave)
    Button mTvSave;
    @BindView(R.id.hintText)
    TextView mHintText;
    private String mPassword;
    private String mPasswordAgain;
    private boolean isFirstIn = true;
    private int index = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_pass_world;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        isFirstIn = SPUtils.isFirstIn();
        if (isFirstIn) {
            initToobar("设置手势密码");
        } else {
            initToobar("输入手势密码");
        }
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

        mTvSave.setVisibility(View.GONE);
        mTvReset.setVisibility(View.GONE);


    }

    @Override
    protected void setListener() {

        mLocusPassWordView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {


            @Override
            public void onComplete(String password) {
                if (isFirstIn) {
                    index++;
                } else {
                    index = 1;
                }
                if (index == 1) {
                    mPassword = password;
                    if (isFirstIn)
                        mHintText.setText("请再次绘制手势密码");
                    mLocusPassWordView.clearPassword();
                } else if (index == 2) {
                    mPasswordAgain = password;

                }


                if (isFirstIn && index == 2) {

                    LogUtils.e(mPassword + "______" + mPasswordAgain);
                    if (mPassword.equals(mPasswordAgain)) {
                        mLocusPassWordView.resetPassWord(mPassword);
                        mLocusPassWordView.clearPassword();
                        ShowToast("请记住密码 否则将找不回");
                        jumpToActivity(SetUserPayMentPassworldActivtiy.class, true);
                        SPUtils.setFirstIn(false);

                    } else {
                        mHintText.setText("两次输入的密码不一致 请重新输入");
                        mLocusPassWordView.clearPassword();
                        index = 0;
                    }

                } else if (!isFirstIn && mLocusPassWordView.verifyPassword(password)) {
                    jumpToActivity(SetUserPayMentPassworldActivtiy.class, true);
                } else if (!isFirstIn && !mLocusPassWordView.verifyPassword(password)) {
                    mHintText.setText("密码错误 请重新输入");
                    mLocusPassWordView.clearPassword();
                }

            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }


    @OnClick({R.id.tvReset, R.id.tvSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvReset:
                mLocusPassWordView.clearPassword();
                break;
            case R.id.tvSave:
                if (StringUtil.isNotEmpty(mPassword)) {
                    mLocusPassWordView.resetPassWord(mPassword);
                    mLocusPassWordView.clearPassword();
                    ShowToast("密码修改成功,请记住密码.");
                    finish();
                } else {
                    mLocusPassWordView.clearPassword();
                    ShowToast("密码不能为空,请输入密码.");
                }
                break;
        }
    }


}
