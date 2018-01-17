package www.test720.com.naneducation.personcenteractivity;

import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.KeyBoardAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.view.OnPasswordInputFinish;
import www.test720.com.naneducation.view.VirtualKeyboardView;

public class SetUserPayMentPassworldActivtiy extends BaseToolbarActivity {


    @BindView(R.id.tv_pass1)
    TextView mTvPass1;
    @BindView(R.id.img_pass1)
    ImageView mImgPass1;
    @BindView(R.id.tv_pass2)
    TextView mTvPass2;
    @BindView(R.id.img_pass2)
    ImageView mImgPass2;
    @BindView(R.id.tv_pass3)
    TextView mTvPass3;
    @BindView(R.id.img_pass3)
    ImageView mImgPass3;
    @BindView(R.id.tv_pass4)
    TextView mTvPass4;
    @BindView(R.id.img_pass4)
    ImageView mImgPass4;
    @BindView(R.id.tv_pass5)
    TextView mTvPass5;
    @BindView(R.id.img_pass5)
    ImageView mImgPass5;
    @BindView(R.id.tv_pass6)
    TextView mTvPass6;
    @BindView(R.id.img_pass6)
    ImageView mImgPass6;
    @BindView(R.id.tv_pass1_1)
    TextView mTvPass11;
    @BindView(R.id.img_pass1_1)
    ImageView mImgPass11;
    @BindView(R.id.tv_pass2_1)
    TextView mTvPass21;
    @BindView(R.id.img_pass2_1)
    ImageView mImgPass21;
    @BindView(R.id.tv_pass3_1)
    TextView mTvPass31;
    @BindView(R.id.img_pass3_1)
    ImageView mImgPass31;
    @BindView(R.id.tv_pass4_1)
    TextView mTvPass41;
    @BindView(R.id.img_pass4_1)
    ImageView mImgPass41;
    @BindView(R.id.tv_pass5_1)
    TextView mTvPass51;
    @BindView(R.id.img_pass5_1)
    ImageView mImgPass51;
    @BindView(R.id.tv_pass6_1)
    TextView mTvPass61;
    @BindView(R.id.img_pass6_1)
    ImageView mImgPass61;
    @BindView(R.id.passwordOne)
    LinearLayout mPasswordOne;
    @BindView(R.id.setPaypassworld)
    Button mSetPaypassworld;


    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置
    private int index = -1;
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？
    private TextView[] tvList1;
    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？
    private ImageView[] imgList1;
    private GridView gridView;
    private PopupWindow mPopWindow;
    private String newPwd = " ";
    private String newPwdAgainl = " ";
    List<Integer> passWorldLists = new ArrayList<>();
    List<Integer> passWorldAgainLists = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_set_user_pay_ment_passworld_activtiy;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void setListener() {

        setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(List<Integer> password) {
                for (int i = 0; i < passWorldLists.size(); i++) {
                    newPwd += password.get(i).toString();
                }

                mPopWindow.dismiss();
            }

            @Override
            public void deletePassWord(int index) {

            }
        }, 1);

        setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(List<Integer> password) {
                for (int i = 0; i < passWorldAgainLists.size(); i++) {
                    newPwdAgainl += password.get(i).toString();
                }
                mPopWindow.dismiss();
            }

            @Override
            public void deletePassWord(int index) {

            }
        }, 2);


        for (int i = 0; i < tvList.length; i++) {
            tvList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopWindows(tvList, imgList, 1);
                }
            });
        }

        for (int i = 0; i < imgList.length; i++) {
            imgList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopWindows(tvList, imgList, 1);
                }
            });
        }

        for (int i = 0; i < tvList1.length; i++) {
            tvList1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopWindows(tvList1, imgList1, 2);
                }
            });
        }

        for (int i = 0; i < imgList1.length; i++) {
            imgList1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopWindows(tvList1, imgList1, 2);
                }
            });
        }


    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("设置密码");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);

        setData();

    }

    private void setData() {
        tvList = new TextView[6];
        imgList = new ImageView[6];
        tvList1 = new TextView[6];
        imgList1 = new ImageView[6];

        tvList[0] = mTvPass1;
        tvList[1] = mTvPass2;
        tvList[2] = mTvPass3;
        tvList[3] = mTvPass4;
        tvList[4] = mTvPass5;
        tvList[5] = mTvPass6;

        imgList[0] = mImgPass1;
        imgList[1] = mImgPass2;
        imgList[2] = mImgPass3;
        imgList[3] = mImgPass4;
        imgList[4] = mImgPass5;
        imgList[5] = mImgPass6;

        //第二个密码框的处理
        tvList1[0] = mTvPass11;
        tvList1[1] = mTvPass21;
        tvList1[2] = mTvPass31;
        tvList1[3] = mTvPass41;
        tvList1[4] = mTvPass51;
        tvList1[5] = mTvPass61;

        imgList1[0] = mImgPass11;
        imgList1[1] = mImgPass21;
        imgList1[2] = mImgPass31;
        imgList1[3] = mImgPass41;
        imgList1[4] = mImgPass51;
        imgList1[5] = mImgPass61;


    }

    // 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
    private void initValueList() {

        valueList = new ArrayList<>();

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass, int type) {

        if (type == 1) {
            tvList[5].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() == 1) {

                        //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                        passWorldLists.clear();
                        newPwd = " ";
                        for (int i = 0; i < 6; i++) {
                            passWorldLists.add(Integer.parseInt(tvList[i].getText().toString().trim()));
                        }
                        pass.inputFinish(passWorldLists);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                    }
                }
            });
        }

        if (type == 2) {
            tvList1[5].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() == 1) {

                        //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                        passWorldAgainLists.clear();
                        newPwdAgainl = " ";
                        for (int i = 0; i < 6; i++) {
                            passWorldAgainLists.add(Integer.parseInt(tvList1[i].getText().toString().trim()));
                        }
                        pass.inputFinish(passWorldAgainLists);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                    }
                }
            });
        }
    }


    void showPopWindows(TextView[] tvList, ImageView[] imgList, int type) {

        int DisplayWidth = getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = getResources().getDisplayMetrics().heightPixels;

        final View contentView = LayoutInflater.from(this).inflate(R.layout.pop_pay_key_board, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);


        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        mPopWindow = new PopupWindow(contentView, DisplayWidth, DensityUtil.dip2px(this, 242), false);

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopWindow.setBackgroundDrawable(new ColorDrawable());

        VirtualKeyboardView virtualKeyboardView = (VirtualKeyboardView) contentView.findViewById(R.id.VirtualKeyboardView);
        gridView = virtualKeyboardView.getGridView();
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

        initValueList();

        setupView(tvList, imgList, type);


        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        mPopWindow.setOutsideTouchable(true);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        mPopWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        mPopWindow.setFocusable(false);
        mPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });


        mPopWindow.showAtLocation(contentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }


    private void setupView(final TextView[] tvList, final ImageView[] imgList, int i) {

        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(this, valueList);
        gridView.setAdapter(keyBoardAdapter);
        if (i == 1) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position < 11 && position != 9) {    //点击0~9按钮
                        if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                            ++currentIndex;
                            tvList[currentIndex].setText(valueList.get(position).get("name"));
                            tvList[currentIndex].setVisibility(View.INVISIBLE);
                            imgList[currentIndex].setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (position == 11) {      //点击退格键
                            if (currentIndex - 1 >= -1) {
                                //判断是否删除完毕————要小心数组越界
                                if (passWorldLists.size() != 0) {
                                    passWorldLists.remove(currentIndex);
                                }

                                tvList[currentIndex].setText("");

                                tvList[currentIndex].setVisibility(View.VISIBLE);
                                imgList[currentIndex].setVisibility(View.INVISIBLE);
                                currentIndex--;


                                if (currentIndex == -1) {
                                    mPopWindow.dismiss();
                                }
                            }
                        }
                    }
                }
            });
        } else if (i == 2) {

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position < 11 && position != 9) {    //点击0~9按钮
                        if (index >= -1 && index < 5) {      //判断输入位置————要小心数组越界
                            ++index;
                            tvList[index].setText(valueList.get(position).get("name"));
                            tvList[index].setVisibility(View.INVISIBLE);
                            imgList[index].setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (position == 11) {      //点击退格键
                            if (index - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                                if (passWorldAgainLists.size() != 0) {
                                    passWorldAgainLists.remove(index);
                                }
                                tvList[index].setText("");

                                tvList[index].setVisibility(View.VISIBLE);
                                imgList[index].setVisibility(View.INVISIBLE);

                                index--;
                                if (index == -1) {
                                    mPopWindow.dismiss();
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @OnClick(R.id.setPaypassworld)
    public void onClick() {
        newPwd = "";
        newPwdAgainl = "";
        for (int i = 0; i < passWorldLists.size(); i++) {
            newPwd += passWorldLists.get(i).toString();
        }
        for (int j = 0; j < passWorldAgainLists.size(); j++) {
            newPwdAgainl += passWorldAgainLists.get(j).toString();
        }

        LogUtils.e(newPwd + "_________" + newPwdAgainl);
        if (!newPwd.equals(newPwdAgainl)) {
            ShowToast("两次输入的密码不一致");
        } else if (newPwd.length() != 6 || newPwdAgainl.length() != 6) {
            ShowToast("密码为6位数字");
        } else {
            HttpParams params = new HttpParams();
            params.put("uid", Constans.uid);
            params.put("xpass", newPwdAgainl);
            params.put("type", 2);
            mSubscription = mHttpUtils.getData(UrlFactory.setUpPaypass, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                @Override
                public void onStart() {
                    showLoadingDialog();
                }

                @Override
                public void onCompleted() {
                    mSubscription.unsubscribe();
                    cancleLoadingDialog();
                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                    cancleLoadingDialog();
                }

                @Override
                public void onNext(String s) {
                    JSONObject obg = JSON.parseObject(s);
                    ShowToast(obg.getString("msg"));
                    if (obg.getInteger("code") == 1) {
                        Constans.isPass = true;
                        finish();
                    }


                }
            });

        }
    }
}
