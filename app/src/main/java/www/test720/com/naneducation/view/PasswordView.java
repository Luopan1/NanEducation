package www.test720.com.naneducation.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.KeyBoardAdapter;

/**
 * 弹框里面的View
 */
public class PasswordView extends RelativeLayout {

    Context mContext;

    private VirtualKeyboardView virtualKeyboardView;

    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？

    private GridView gridView;

    private ImageView imgCancel;

    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置
    List<Integer> passwodlLists = new ArrayList<>();
    private TextView mOriginalprice;
    private TextView mSponor;
    private TextView mGetBackIntegar;
    private TextView mPayMentMoney;
    private Button mComfirmPay;

    public PasswordView(Context context) {
        super(context, null);

    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        View view = View.inflate(context, R.layout.layout_popup_bottom, null);

        virtualKeyboardView = (VirtualKeyboardView) view.findViewById(R.id.virtualKeyboardView);
        imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
        virtualKeyboardView.getLayoutBack().setVisibility(View.GONE);
        gridView = virtualKeyboardView.getGridView();
        virtualKeyboardView.setVisibility(View.GONE);
        initValueList();

        initView(view);

        setupView();

        addView(view);
    }

    public void setData(String originaPrice, String sponnor, String getBackInteger, String pay_price) {
        mOriginalprice.setText("¥" + originaPrice);
        mSponor.setText(sponnor);
        mGetBackIntegar.setText("上课成功即返回" + getBackInteger + "积分");
        mPayMentMoney.setText(pay_price);

    }

    private void initView(View view) {


        tvList = new TextView[6];

        imgList = new ImageView[6];

        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) view.findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) view.findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) view.findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) view.findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) view.findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) view.findViewById(R.id.img_pass6);


        mOriginalprice = (TextView) view.findViewById(R.id.textAmount);
        mSponor = (TextView) view.findViewById(R.id.textShouxuFei);
        mGetBackIntegar = (TextView) view.findViewById(R.id.getbackInteger);
        mPayMentMoney = (TextView) view.findViewById(R.id.payMentMoney);
        mComfirmPay = (Button) view.findViewById(R.id.confirmPay);

        setonClickListener(tvList, imgList);


    }

    public Button getComfirmPay() {
        return mComfirmPay;
    }


    public void setonClickListener(TextView[] tvList, ImageView[] imgList) {
        for (int i = 0; i < tvList.length; i++) {
            tvList[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            });

        }

        for (int i = 0; i < imgList.length; i++) {
            imgList[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            });

        }


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

    private void setupView() {

        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮

                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));

                        passwodlLists.add(currentIndex, Integer.parseInt(valueList.get(position).get("name")));

                        tvList[currentIndex].setVisibility(View.INVISIBLE);
                        imgList[currentIndex].setVisibility(View.VISIBLE);
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            if (currentIndex >= 0) {
                                passwodlLists.remove(currentIndex);
                            }
                            tvList[currentIndex].setText("");

                            tvList[currentIndex].setVisibility(View.VISIBLE);
                            imgList[currentIndex].setVisibility(View.INVISIBLE);

                            currentIndex--;
                        }
                    }
                }
            }
        });
    }


    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {


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

                    String strPassword = "";   //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    passwodlLists = new ArrayList<>();
                    virtualKeyboardView.setVisibility(GONE);
                    for (int i = 0; i < 6; i++) {
                        passwodlLists.add(Integer.parseInt(tvList[i].getText().toString().trim()));
                    }
                    pass.inputFinish(passwodlLists);    //接口中要实现的方法，完成密码输入完成后的响应逻辑

                }
            }
        });


    }

    public VirtualKeyboardView getVirtualKeyboardView() {

        return virtualKeyboardView;
    }

    public ImageView getImgCancel() {
        return imgCancel;
    }
}
