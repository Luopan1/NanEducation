package www.test720.com.naneducation.personcenteractivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.citywheel.CityPickerView;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.http.UrlFactory;

public class ApplyAgentActivity extends BaseToolbarActivity {


    @BindView(R.id.agentName)
    EditText mAgentName;
    @BindView(R.id.agentPhone)
    EditText mAgentPhone;
    @BindView(R.id.commitApply)
    Button mCommitApply;
    @BindView(R.id.agentArea)
    TextView mAgentArea;
    private CityConfig mCityConfig;
    String sName = "";
    String qName = "";
    String XName = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_apply_agent;
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
        initToobar("申请成为代理");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);

        mCityConfig = new CityConfig.Builder(ApplyAgentActivity.this)
                .title("选择地区")
                .textSize(18)
                .province("四川")
                .city("成都")
                .district("武侯区")
                .visibleItemsCount(8)
                .provinceCyclic(true)
                .cityCyclic(true)
                .showBackground(true)
                .districtCyclic(true)
                .itemPadding(8)
                .setCityInfoType(CityConfig.CityInfoType.BASE)
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
                .build();

    }


    @OnClick({R.id.agentArea, R.id.commitApply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.agentArea:
                CityPickerView cityPicker = new CityPickerView(mCityConfig);
                cityPicker.show();

                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                        if (district != null) {
                            //返回结果
                            sName = province.toString();
                            qName = city.toString();
                            XName = district.toString();
                            mAgentArea.setText(province.toString() + city.toString() + district.toString());
                        } else {
                            mAgentArea.setText(province.toString() + city.toString());
                            sName = province.toString();
                            qName = city.toString();
                        }

                    }
                });

                break;
            case R.id.commitApply:
                if (mAgentName.getText().toString().trim().isEmpty() || mAgentPhone.getText().toString().trim().length() != 11 || sName.isEmpty()) {
                    ShowToast("请正确填写所有信息");
                    return;
                }
                HttpParams params = new HttpParams();
                params.put("name", mAgentName.getText().toString().trim());
                params.put("phone", mAgentPhone.getText().toString().trim());
                params.put("sname", sName);
                params.put("cname", qName);
                params.put("qname", XName);
                mSubscription = mHttpUtils.getData(UrlFactory.agentUser, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        cancleLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ShowToast(e.getMessage());
                        cancleLoadingDialog();
                    }

                    @Override
                    public void onNext(String s) {
                        JSONObject obj = JSON.parseObject(s);
                        ShowToast(obj.getString("msg"));
                        finish();
                    }
                });
                break;
        }
    }
}
