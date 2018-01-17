package www.test720.com.naneducation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.OnCompressListener;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.SendSharedGirdAdapter;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.ActivityType;
import www.test720.com.naneducation.bean.LocatedCity;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.GlideLoader;
import www.test720.com.naneducation.utils.ImageLoader;
import www.test720.com.naneducation.utils.Luban_Self;
import www.test720.com.naneducation.view.PasswordView;

import static com.edusdk.tools.Tools.alertDialog;

public class ReleaseGroupActivity_Activity extends BaseToolbarActivity {


    @BindView(R.id.selectActivityArea)
    RelativeLayout mSelectActivityArea;
    @BindView(R.id.selectActivityKind)
    RelativeLayout mSelectActivityKind;
    @BindView(R.id.activityName)
    EditText mActivityName;
    @BindView(R.id.textview)
    TextView mTextview;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    @BindView(R.id.radioButton3)
    RadioButton mRadioButton3;
    @BindView(R.id.moneyRelative)
    LinearLayout mMoneyRelative;
    @BindView(R.id.textVew2)
    TextView mTextVew2;
    @BindView(R.id.textview3)
    TextView mTextview3;
    @BindView(R.id.chooseGongYiImage)
    ImageView mChooseGongYiImage;
    @BindView(R.id.chooseGongYiImageRelative)
    RelativeLayout mChooseGongYiImageRelative;
    @BindView(R.id.isUseMoneyRelative)
    RelativeLayout mIsUseMoneyRelative;
    @BindView(R.id.HostPhoneNumber)
    EditText mHostPhoneNumber;
    @BindView(R.id.activityAddress)
    EditText mActivityAddress;
    @BindView(R.id.activityHumanCount)
    EditText mActivityHumanCount;
    @BindView(R.id.textView5)
    TextView mTextView5;
    @BindView(R.id.activiyStartTime)
    TextView mActiviyStartTime;
    @BindView(R.id.activtyStartTimeRela)
    RelativeLayout mActivtyStartTimeRela;
    @BindView(R.id.textview4)
    TextView mTextview4;
    @BindView(R.id.activityEndTime)
    TextView mActivityEndTime;
    @BindView(R.id.activityEndTimeRelative)
    RelativeLayout mActivityEndTimeRelative;
    @BindView(R.id.activityImagesGridView)
    GridView mActivityImagesGridView;
    @BindView(R.id.activityIntroduce)
    EditText mActivityIntroduce;
    @BindView(R.id.businessLicenseImege)
    ImageView mBusinessLicenseImege;
    @BindView(R.id.peopleIDCardZimageView)
    ImageView mPeopleIDCardZimageView;
    @BindView(R.id.peopleFIDCardImage)
    ImageView mPeopleFIDCardImage;
    @BindView(R.id.check_aggrement)
    CheckBox mCheckAggrement;
    @BindView(R.id.aggrement)
    TextView mAggrement;
    @BindView(R.id.sendActivity)
    Button mSendActivity;
    int Type = 0;
    private static final int IMAGE_PICKER = 1;
    @BindView(R.id.activityArea)
    TextView mActivityArea;
    @BindView(R.id.activityKind)
    TextView mActivityKind;
    @BindView(R.id.money_text)
    EditText mMoneyText;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> imageList = new ArrayList<>();
    private ArrayList<ImageItem> GYimageLists = new ArrayList<>();
    private ArrayList<ImageItem> ZZimageLists = new ArrayList<>();
    private ArrayList<ImageItem> peppleZImageLists = new ArrayList<>();
    private ArrayList<ImageItem> peppleFImageLists = new ArrayList<>();
    private SendSharedGirdAdapter mAdapter;
    private ImageLoader mLoader;
    private ActivityType.DataBean.ListBean mBean;
    private LocatedCity.DataBean.ListBean mCityBean;
    private long startTime;
    private long endTime;
    PopupWindow mPopWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_release_group_;
    }

    @Override
    protected void initData() {
        mLoader = new ImageLoader(this);
        imageList = new ArrayList<>();
        setAdapter();
    }

    @Override
    protected void setListener() {
        mActivityImagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imageList.size()) {
                    initImagePicker(9);
                    Type = 2;
                    Intent intent = new Intent(ReleaseGroupActivity_Activity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, imageList);
                    startActivityForResult(intent, IMAGE_PICKER);
                }
            }
        });
    }

    /**
     * 第三方拍照和裁剪
     */
    private void initImagePicker(int size) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(size);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }

    /*
     * 相机拍照得到的图片设置到ImageView上面去
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x0005 && resultCode == 0x0004) {
            mCityBean = (LocatedCity.DataBean.ListBean) data.getSerializableExtra("cityBean");
            mActivityArea.setText(data.getStringExtra("city") + data.getStringExtra("district"));
        } else if (requestCode == 0x0006 && resultCode == 0x0007) {
            mBean = (ActivityType.DataBean.ListBean) data.getSerializableExtra("kind");
            mActivityKind.setText(mBean.getName());
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null && requestCode == IMAGE_PICKER && Type == 2) {
                imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mAdapter.notifyDataChanged(imageList);

            } else if (data != null && requestCode == IMAGE_PICKER && Type == 1) {
                GYimageLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(GYimageLists.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mChooseGongYiImage);
            } else if (data != null && requestCode == IMAGE_PICKER && Type == 3) {
                ZZimageLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(ZZimageLists.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mBusinessLicenseImege);
            } else if (data != null && requestCode == IMAGE_PICKER && Type == 4) {
                peppleZImageLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(peppleZImageLists.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mPeopleIDCardZimageView);
            } else if (data != null && requestCode == IMAGE_PICKER && Type == 5) {
                peppleFImageLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mLoader.loadImage(peppleFImageLists.get(0).path, R.mipmap.ic_launcher, R.mipmap.ic_launcher, mPeopleFIDCardImage);

                peppleZImageLists.addAll(peppleFImageLists);
            } else {
                //                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                ShowToastLong("沒有数据");
            }
        }
    }

    public void setAdapter() {
        mActivityImagesGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new SendSharedGirdAdapter(this, imageList);
        mActivityImagesGridView.setAdapter(mAdapter);
    }


    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("发布活动");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @OnClick({R.id.selectActivityArea, R.id.selectActivityKind, R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.moneyRelative, R.id.chooseGongYiImage, R.id.activtyStartTimeRela, R.id.activityEndTimeRelative,
            R.id.businessLicenseImege, R.id.peopleIDCardZimageView, R.id.peopleFIDCardImage, R.id.sendActivity, R.id.aggrement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aggrement:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 3);
                bundle.putString("title", "发布协议");
                jumpToActivity(AgreeMentActivity.class, bundle, false);
                break;
            case R.id.selectActivityArea:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                startActivityForResult(intent, 0x0005);
                break;
            case R.id.selectActivityKind:
                intent = new Intent(this, ActivityKindActivity.class);
                startActivityForResult(intent, 0x0006);
                break;
            case R.id.radioButton1:
                mIsUseMoneyRelative.setVisibility(View.GONE);
                break;
            case R.id.radioButton2:
                mIsUseMoneyRelative.setVisibility(View.VISIBLE);
                mChooseGongYiImageRelative.setVisibility(View.GONE);
                mMoneyRelative.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButton3:
                mIsUseMoneyRelative.setVisibility(View.VISIBLE);
                mChooseGongYiImageRelative.setVisibility(View.VISIBLE);
                mMoneyRelative.setVisibility(View.GONE);
                break;
            case R.id.moneyRelative:
                break;
            case R.id.chooseGongYiImage:
                Type = 1;
                GYimageLists = new ArrayList<>();
                initImagePicker(1);
                intent = new Intent(ReleaseGroupActivity_Activity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, GYimageLists);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.activtyStartTimeRela:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        startTime = date.getTime();
                        mActiviyStartTime.setText(getTime(date));
                    }
                })
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.activityEndTimeRelative:
                //时间选择器
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        endTime = date.getTime();
                        mActivityEndTime.setText(getTime(date));
                    }
                })
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.businessLicenseImege:
                Type = 3;
                ZZimageLists = new ArrayList<>();
                initImagePicker(1);
                intent = new Intent(ReleaseGroupActivity_Activity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, ZZimageLists);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.peopleIDCardZimageView:
                Type = 4;
                peppleZImageLists = new ArrayList<>();
                initImagePicker(1);
                intent = new Intent(ReleaseGroupActivity_Activity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, peppleZImageLists);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.peopleFIDCardImage:
                Type = 5;
                peppleFImageLists = new ArrayList<>();
                initImagePicker(1);
                intent = new Intent(ReleaseGroupActivity_Activity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, peppleFImageLists);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.sendActivity:
                if (mActivityArea.getText().toString().isEmpty() || mActivityKind.getText().toString().isEmpty() || mActivityName.getText().toString().trim().isEmpty()) {
                    ShowToast("活动区域、类型、名称必须全部填写");
                } else if (!mRadioButton1.isChecked() && !mRadioButton2.isChecked() && !mRadioButton3.isChecked()) {
                    ShowToast("活动费用必须选择");
                } else if (mRadioButton2.isChecked() && mMoneyText.getText().toString().trim().isEmpty()) {
                    ShowToast("活动金额必须填写");
                } else if (checkLength(mHostPhoneNumber, 11)) {
                    ShowToast("负责人电话必须填写正确");
                } else if (mActivityAddress.getText().toString().trim().isEmpty() || mActivityHumanCount.getText().toString().trim().isEmpty()) {
                    ShowToast("活动信息必须全部填写");
                } else if (mActiviyStartTime.getText().toString().trim().isEmpty() || mActivityEndTime.getText().toString().trim().isEmpty()) {
                    ShowToast("活动时间必须全部填写");
                } else if (imageList.size() == 0) {
                    ShowToast("活动图片必须上传");
                } else if (mActivityIntroduce.getText().toString().trim().isEmpty()) {
                    ShowToast("活动说明必须填写");
                } else if (!mCheckAggrement.isChecked()) {
                    ShowToast("请阅读并勾选用户发布协议");
                } else if ((endTime - startTime) < 0) {
                    ShowToast("开始时间不能大于结束时间");
                } else if ((endTime - startTime) / 1000 < 30 * 60) {
                    ShowToast("活动时间必须半小时以上");
                } else if (peppleZImageLists.size() <= 0 && peppleFImageLists.size() <= 0) {
                    ShowToast("身份证正反面必须上传");
                } else {
                    showLoadingDialog();

                    List<String> imageLists = new ArrayList<>();
                    List<File> idCardImages = new ArrayList<>();

                    final List<File> fileList = new ArrayList<>();
                    final HttpParams params = new HttpParams();
                    params.put("paltype", 2);
                    params.put("uid", Constans.uid);
                    params.put("city_id", mCityBean.getC_id());
                    params.put("type_id", mBean.getTid());
                    params.put("act_name", mActivityName.getText().toString().trim());
                    if (mRadioButton1.isChecked()) {
                        params.put("cost_type", 0);
                    } else if (mRadioButton2.isChecked()) {
                        params.put("cost_type", 1);
                    } else if (mRadioButton3.isChecked()) {
                        params.put("cost_type", 2);
                    }
                    params.put("act_phone", mHostPhoneNumber.getText().toString().trim());
                    params.put("act_address", mActivityAddress.getText().toString().trim());
                    params.put("act_mun", mActivityHumanCount.getText().toString().trim());
                    params.put("act_startime", mActiviyStartTime.getText().toString().trim());
                    params.put("act_endtime", mActivityEndTime.getText().toString().trim());
                    if (mRadioButton2.isChecked()) {
                        params.put("act_money", mMoneyText.getText().toString().trim());
                    }
                    params.put("act_content", mActivityIntroduce.getText().toString().trim());

                    for (int i = 0; i < imageList.size(); i++) {

                        imageLists.add(imageList.get(i).path);
                    }

                    for (int i = 0; i < GYimageLists.size(); i++) {
                        params.put("file[wafare_img][]", new File(GYimageLists.get(0).path));
                    }

                    for (int i = 0; i < ZZimageLists.size(); i++) {
                        params.put("file[license_img][]", new File(ZZimageLists.get(i).path));
                    }
                    for (int i = 0; i < peppleZImageLists.size(); i++) {
                        idCardImages.add(new File(peppleZImageLists.get(i).path));
                    }
                    params.putFileParams("file[card_img][]", idCardImages);

                    File DatalDir = Environment.getExternalStorageDirectory();
                    File myDir = new File(DatalDir, "/DCIM/南充教育");
                    myDir.mkdirs();

                    Luban_Self.with(this)
                            .load(imageLists)                                   // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
                            .setTargetDir(myDir.getPath())                        // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件

                                    fileList.add(file);
                                    ;
                                    LogUtils.e(fileList.size() + "-----" + imageList.size());

                                    if (fileList.size() == imageList.size()) {
                                        upLoads(fileList, params);
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(getApplicationContext(), "图片路径有误", Toast.LENGTH_SHORT).show();

                                }
                            }).launch();    //启动压缩

                }
                break;
        }
    }

    public File chageFileName(String filePath, String reName) {
        File file = new File(filePath);
        //前面路径必须一样才能修改成功
        String path = filePath.substring(0, filePath.lastIndexOf("/") + 1) + reName + filePath.substring(filePath.lastIndexOf("."), filePath.length());
        LogUtils.e("filePath:" + filePath + "-------" + "path:" + path);
        File newFile = new File(path);
        file.renameTo(newFile);
        return newFile;
    }

    private void upLoads(List<File> fileList, HttpParams params) {
        params.putFileParams("file[act_img][]", fileList);
        mSubscription = mHttpUtils.getData(UrlFactory.actRelease, params, 0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.i("onCompleted");
                        cancleLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        JSONObject obj = JSON.parseObject(s);
                        ShowToast(obj.getString("msg"));

                        if (obj.getInteger("code") == 1) {
                            GroupActivity_Activity.type = 2;
                            showDialog();
                        }
                    }
                });
    }

    private void showDialog() {
        /*pop_release_activity*/
        int DisplayWidth = this.getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = this.getResources().getDisplayMetrics().heightPixels;
        final View contentView = LayoutInflater.from(this).inflate(R.layout.pop_release_activity, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopWindow = new PopupWindow(contentView, DisplayWidth, DisplayHeight, false);

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopWindow.setBackgroundDrawable(new ColorDrawable());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        mPopWindow = new PopupWindow(contentView, DisplayWidth, DisplayHeight, false);

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopWindow.setBackgroundDrawable(new ColorDrawable());


        contentView.findViewById(R.id.guanbi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                finish();
            }
        });

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

        mPopWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        if (mPopWindow != null && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

}
