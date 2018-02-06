package www.test720.com.naneducation.personcenteractivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.activity.AgreeMentActivity;
import www.test720.com.naneducation.activity.ChoosePlatFormAreaActivity;
import www.test720.com.naneducation.adapter.SendSharedGirdAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.LocatedCity;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.GlideLoader;
import www.test720.com.naneducation.utils.ImageLoader;

public class PlatformOccupancyActivity extends BaseToolbarActivity {


    @BindView(R.id.textview)
    TextView mTextview;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.radioButton3)
    RadioButton mRadioButton3;
    @BindView(R.id.selectArea)
    RelativeLayout mSelectArea;
    @BindView(R.id.businessLicenseImege)
    ImageView mBusinessLicenseImege;
    @BindView(R.id.BusinessLicense)
    LinearLayout mBusinessLicense;
    @BindView(R.id.activityImagesGridView)
    GridView mActivityImagesGridView;
    @BindView(R.id.Qualification)
    LinearLayout mQualification;
    @BindView(R.id.peopleIDCardZimageView)
    ImageView mPeopleIDCardZimageView;
    @BindView(R.id.peopleFIDCardImage)
    ImageView mPeopleFIDCardImage;
    @BindView(R.id.mechanismHandingNumber)
    EditText mMechanismHandingNumber;
    @BindView(R.id.mechanismAgentContactNumber)
    EditText mMechanismAgentContactNumber;
    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.mechanismBankName)
    EditText mMechanismBankName;
    @BindView(R.id.relatvie1)
    RelativeLayout mRelatvie1;
    @BindView(R.id.mechanismBankCardNumber)
    EditText mMechanismBankCardNumber;
    @BindView(R.id.personNumber)
    EditText mPersonNumber;
    @BindView(R.id.text11)
    TextView mText11;
    @BindView(R.id.perSonBankName)
    EditText mPerSonBankName;
    @BindView(R.id.relatvie11)
    RelativeLayout mRelatvie11;
    @BindView(R.id.personBankCardNumber)
    EditText mPersonBankCardNumber;
    @BindView(R.id.check_aggrement)
    CheckBox mCheckAggrement;
    @BindView(R.id.aggrement)
    TextView mAggrement;
    @BindView(R.id.commitApply)
    Button mCommitApply;
    @BindView(R.id.person_occupancy)
    View mPersonOccupancy;
    @BindView(R.id.agent_mechanism)
    View mAgentMechanism;
    private static final int IMAGE_PICKER = 1;
    @BindView(R.id.text_area)
    TextView mTextArea;
    @BindView(R.id.sfz)
    TextView mSfz;
    private ImagePicker imagePicker;
    private SendSharedGirdAdapter mAdapter;
    ArrayList<ImageItem> businessLicenseList = new ArrayList<>();
    int Type = 0;
    private ImageLoader mLoader;
    private ArrayList<ImageItem> imageList = new ArrayList<>();
    private ArrayList<ImageItem> peppleZImageLists = new ArrayList<>();
    private ArrayList<ImageItem> peppleFImageLists = new ArrayList<>();
    int requsetCode = 0x0005;
    private String mCity_id;


    @Override
    protected int getContentView() {
        return R.layout.activity_platform_occupancy;
    }

    @Override
    protected void initData() {
        mLoader = new ImageLoader(this);
        imageList = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {

        mActivityImagesGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new SendSharedGirdAdapter(this, imageList);
        mActivityImagesGridView.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {
        mActivityImagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imageList.size()) {
                    initImagePicker(9);
                    Type = 2;
                    Intent intent = new Intent(PlatformOccupancyActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, imageList);
                    startActivityForResult(intent, IMAGE_PICKER);
                }
            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("申请入驻平台");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }

    @OnClick({R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.selectArea, R.id.businessLicenseImege, R.id.peopleIDCardZimageView,
            R.id.peopleFIDCardImage, R.id.aggrement, R.id.commitApply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                mSfz.setText("请上传经办人身份证正反面");
                mQualification.setVisibility(View.GONE);
                mBusinessLicense.setVisibility(View.VISIBLE);
                mAgentMechanism.setVisibility(View.VISIBLE);
                mPersonOccupancy.setVisibility(View.GONE);
                break;
            case R.id.radioButton2:
                mSfz.setText("请上传经办人身份证正反面");
                mQualification.setVisibility(View.GONE);
                mBusinessLicense.setVisibility(View.VISIBLE);
                mAgentMechanism.setVisibility(View.VISIBLE);
                mPersonOccupancy.setVisibility(View.GONE);

                break;
            case R.id.radioButton3:
                mSfz.setText("请上传本人身份证正反面");
                mQualification.setVisibility(View.VISIBLE);
                mBusinessLicense.setVisibility(View.GONE);
                mAgentMechanism.setVisibility(View.GONE);
                mPersonOccupancy.setVisibility(View.VISIBLE);
                break;
            case R.id.selectArea:
                Intent intent = new Intent(this, ChoosePlatFormAreaActivity.class);
                startActivityForResult(intent, requsetCode);
                break;
            case R.id.businessLicenseImege:
                initImagePicker(1);
                Type = 1;
                businessLicenseList = new ArrayList<>();
                intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, businessLicenseList);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.peopleIDCardZimageView:
                initImagePicker(1);
                peppleZImageLists = new ArrayList<>();
                Type = 3;
                intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, peppleZImageLists);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.peopleFIDCardImage:
                initImagePicker(1);
                peppleFImageLists = new ArrayList<>();
                Type = 4;
                intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, peppleFImageLists);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.aggrement:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                bundle.putString("title", "入住协议");
                jumpToActivity(AgreeMentActivity.class, bundle, false);
                break;
            case R.id.commitApply:
                if (mRadioButton1.isChecked() || mRadioButton2.isChecked() || mRadioButton3.isChecked()) {
                    HttpParams params = new HttpParams();
                    params.put("paltype", 2);
                    if (mRadioButton1.isChecked()) {
                        params.put("type", 1);
                    } else if (mRadioButton2.isChecked()) {
                        params.put("type", 2);
                    } else if (mRadioButton3.isChecked()) {
                        params.put("type", 3);
                    }
                    if (mTextArea.getText().toString().trim().isEmpty()) {
                        ShowToast("请选择区域");
                        return;
                    } else {
                        params.put("cityId", mCity_id);
                    }
                    if (mRadioButton1.isChecked() || mRadioButton2.isChecked()) {
                        if (businessLicenseList.size() == 0) {
                            ShowToast("请上传营业执照");
                        } else {
                            List<File> files = new ArrayList<>();
                            files.add(new File(businessLicenseList.get(0).path));
                            params.putFileParams("file[walf_img][]", files);
                        }
                    }
                    if (mRadioButton1.isChecked() || mRadioButton2.isChecked() || mRadioButton3.isChecked()) {
                        if (peppleZImageLists.size() == 0 || peppleFImageLists.size() == 0) {
                            ShowToast("请上传身份证正反面");
                            return;
                        } else {
                            List<File> files = new ArrayList<>();
                            files.add(new File(peppleZImageLists.get(0).path));
                            files.add(new File(peppleFImageLists.get(0).path));
                            params.putFileParams("file[card_img][]", files);
                        }
                    }
                    if (mRadioButton1.isChecked() || mRadioButton2.isChecked()) {
                        if (mMechanismHandingNumber.getText().toString().trim().length() != 11) {
                            ShowToast("请输入正确的手机号");
                            return;
                        } else {
                            params.put("userphone", mMechanismHandingNumber.getText().toString().trim());
                        }
                        if (mMechanismAgentContactNumber.getText().toString().trim().isEmpty()) {
                            ShowToast("请输入单位联系方式");
                            return;
                        } else {
                            params.put("company", mMechanismAgentContactNumber.getText().toString().trim());
                        }
                        if (mMechanismBankName.getText().toString().isEmpty() || mMechanismBankCardNumber.getText().toString().trim().isEmpty()) {
                            ShowToast("请输入对公账户信息");
                            return;
                        } else {
                            params.put("bankname", mMechanismBankName.getText().toString().trim());
                            params.put("bankcard", mMechanismBankCardNumber.getText().toString().trim());
                        }
                    } else if (mRadioButton3.isChecked()) {
                        if (imageList.size() == 0) {
                            ShowToast("请上传资格证书");
                            return;
                        } else {
                            List<File> files = new ArrayList<>();
                            for (int i = 0; i < imageList.size(); i++) {
                                files.add(new File(imageList.get(i).path));
                            }
                            params.putFileParams("file[walf_img][]", files);
                        }

                        if (mPersonNumber.getText().toString().trim().isEmpty()) {
                            ShowToast("请输入本人手机号");
                            return;
                        } else {
                            params.put("userphone", mPersonNumber.getText().toString().trim());
                        }
                        if (mPerSonBankName.getText().toString().trim().isEmpty() || mPersonBankCardNumber.getText().toString().trim().isEmpty()) {
                            ShowToast("请输入本人银行卡信息");
                            return;
                        } else {
                            params.put("bankname", mPerSonBankName.getText().toString().trim());
                            params.put("bankcard", mPersonBankCardNumber.getText().toString().trim());
                        }
                    }

                    if (!mCheckAggrement.isChecked()) {
                        ShowToast("请阅读并勾选入驻协议");
                        return;
                    } else {
                        applyAgent(params);
                    }

                } else {
                    ShowToast("请选择入住角色");
                }


                break;
        }
    }

    private void applyAgent(HttpParams params) {
        mSubscription = mHttpUtils.getData(UrlFactory.platformAplay, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
    }

    /*
   * 相机拍照得到的图片设置到ImageView上面去
   */
    @SuppressWarnings("unchecked")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null && requestCode == IMAGE_PICKER && Type == 1) {
                businessLicenseList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(businessLicenseList.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mBusinessLicenseImege);
            } else if (data != null && requestCode == IMAGE_PICKER && Type == 2) {
                imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mAdapter.notifyDataChanged(imageList);
            } else if (data != null && requestCode == IMAGE_PICKER && Type == 3) {
                peppleZImageLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(peppleZImageLists.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mPeopleIDCardZimageView);
            } else if (data != null && requestCode == IMAGE_PICKER && Type == 4) {
                peppleFImageLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(peppleFImageLists.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mPeopleFIDCardImage);
            } else {
                ShowToastLong("沒有数据");
            }
        } else if (data != null && requestCode == requsetCode && resultCode == 0x0004) {
            if ((LocatedCity.DataBean.ListBean) data.getSerializableExtra("cityBean") != null) {
                LocatedCity.DataBean.ListBean bean = (LocatedCity.DataBean.ListBean) data.getSerializableExtra("cityBean");
                mTextArea.setText(bean.getC_name() + bean.getQ_name());
                mCity_id = bean.getC_id();
            }

        }
    }


    /**
     * 第三方拍照和裁剪
     */
    private void initImagePicker(int size) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(size);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(2000);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(2000);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(2000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(2000);//保存文件的高度。单位像素
    }

}
