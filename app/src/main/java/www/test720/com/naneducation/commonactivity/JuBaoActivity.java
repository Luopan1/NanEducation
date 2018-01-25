package www.test720.com.naneducation.commonactivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
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
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.MyGirdAdapter;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Grade;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.DataCleanManager;
import www.test720.com.naneducation.utils.GlideLoader;
import www.test720.com.naneducation.utils.Luban_Self;
import www.test720.com.naneducation.utils.SystemUtil;
import www.test720.com.naneducation.utils.Utility;

public class JuBaoActivity extends BaseToolbarActivity {


    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.content)
    EditText mContent;
    @BindView(R.id.girdView)
    GridView mGirdView;
    @BindView(R.id.contactNumber)
    EditText mContactNumber;
    @BindView(R.id.commit)
    Button mCommit;
    ArrayList<ImageItem> mImageItemLists = new ArrayList<>();
    List<Grade> lists = new ArrayList<>();
    List<Integer> positionLists = new ArrayList<>();
    CommonAdaper<Grade> mCommonAdaper;
    @BindView(R.id.textNum)
    TextView mTextNum;
    private MyGirdAdapter mAdapter;
    private static final int IMAGE_PICKER = 1;
    private int mTYPE;
    private String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_ju_bao;
    }

    @Override
    protected void initData() {
        mGirdView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new MyGirdAdapter(this, mImageItemLists);
        mGirdView.setAdapter(mAdapter);

        lists.add(new Grade(1, "诱导学员私下交易"));
        lists.add(new Grade(2, "讲课内容与课程描述不符"));
        lists.add(new Grade(3, "老师没有来上课"));
        lists.add(new Grade(4, "上课涉及违规内容(传销/赌博)"));
        lists.add(new Grade(5, "宣传其他平台/网站"));
        lists.add(new Grade(6, "课程内容广告过多"));
        lists.add(new Grade(7, "视屏加载失败、卡住无法播放"));
        lists.add(new Grade(8, "视屏不流畅、黑屏、模糊"));
        setAdapter();

    }

    private void setAdapter() {
        if (mCommonAdaper == null) {
            mCommonAdaper = new CommonAdaper<Grade>(this, lists, R.layout.item_jubao_item) {
                @Override
                public void convert(ViewHolder holder, Grade item, final int position) {
                    holder.setText(R.id.text, item.getGrade());
                    final CheckBox checkbox = holder.getView(R.id.smoothCheckBox);


                    holder.getView(R.id.item_chcek).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkbox.isChecked()) {
                                for (int i = 0; i < positionLists.size(); i++) {
                                    if (positionLists.get(i).equals(position + 1)) {
                                        positionLists.remove(i);
                                    }
                                }
                                checkbox.setChecked(false);
                            } else {
                                positionLists.add(position + 1);
                                checkbox.setChecked(true);
                            }
                        }
                    });
                }
            };
            mListView.setAdapter(mCommonAdaper);
            Utility.setListViewHeightBasedOnChildren(mListView);
        } else {
            mCommonAdaper.notifyDataSetChanged();
        }
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


    @Override
    protected void setListener() {
        mGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mImageItemLists.size()) {
                    initImagePicker(3);
                    Intent intent = new Intent(JuBaoActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, mImageItemLists);
                    startActivityForResult(intent, IMAGE_PICKER);
                }
            }
        });

        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = mContent.getText().toString().trim().length();
                mTextNum.setText(length + "/" + 100);
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
        initToobar("举报");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
        Intent intent = getIntent();
        mTYPE = intent.getIntExtra("type", 0);
        id = intent.getStringExtra("id");
    }

    @OnClick(R.id.commit)
    public void onClick() {
        if (positionLists.size() == 0) {
            ShowToast("请选择投诉原因");
        } else if (mContent.getText().toString().trim().isEmpty()) {
            ShowToast("请输入问题描述");
        } else if (mImageItemLists.size() == 0) {
            ShowToast("请上传截图");
        } else if (mContactNumber.getText().toString().isEmpty()) {
            ShowToast("请输入联系方式");
        } else {
            String reportId = "";
            for (int i = 0; i < positionLists.size(); i++) {
                if (i < positionLists.size()) {
                    reportId += positionLists.get(i) + ",";
                } else {
                    reportId += positionLists.get(i);
                }
            }
            final List<File> files = new ArrayList<>();
           /* for (int i = 0; i < mImageItemLists.size(); i++) {
                files.add(new File(mImageItemLists.get(i).path));
            }*/

            final HttpParams params = new HttpParams();
            params.put("uid", Constans.uid);
            params.put("type", mTYPE);
            params.put("bindId", id);
            params.put("reportId", reportId);
            params.put("content", mContent.getText().toString().trim());
            params.put("phone", mContactNumber.getText().toString().trim());
            params.put("cityId", Constans.city_id);

            final List<String> filePath = new ArrayList<>();
            for (ImageItem file : mImageItemLists) {

                filePath.add(file.path);
            }

            File DatalDir = Environment.getExternalStorageDirectory();
            File myDir = new File(DatalDir, "/DCIM/助学");
            myDir.mkdirs();

            Luban.with(JuBaoActivity.this).load(filePath).ignoreBy(100).setTargetDir(myDir.getPath()).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                    showLoadingDialog();
                }

                @Override
                public void onSuccess(File file) {
                    files.add(file);
                    LogUtils.e(files.size() + "__________" + filePath.size());

                    if (files.size() == filePath.size()) {
                        params.putFileParams("file[]", files);
                        upLoadReport(params);
                    }


                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                }
            }).launch();

        }

    }

    private void upLoadReport(HttpParams params) {
        mSubscription = mHttpUtils.getData(UrlFactory.userReport, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                File DatalDir = Environment.getExternalStorageDirectory();
                File myDir = new File(DatalDir, "/DCIM/助学");
                DataCleanManager.deleteDir(myDir);
                cancleLoadingDialog();
            }

            @Override
            public void onNext(String s) {
                cancleLoadingDialog();
                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                finish();
            }
        });

    }


    @SuppressWarnings("unchecked")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null && requestCode == IMAGE_PICKER) {
                mImageItemLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mAdapter.notifyDataChanged(mImageItemLists);

            } else {
                ShowToastLong("沒有数据");
            }
        }
    }

}
