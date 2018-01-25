package com.edusdk.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.edusdk.Constans_VIdeo;
import com.edusdk.R;
import com.edusdk.adapter.CommonAdaper;
import com.edusdk.adapter.MyGirdAdapter;
import com.edusdk.adapter.ViewHolder;
import com.edusdk.bean.Grade;
import com.edusdk.http.HttpUtils_Video;
import com.edusdk.http.UrlFactoty_Video;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.model.HttpParams;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class JuBaoClassActivity extends AppCompatActivity implements View.OnClickListener {

    ListView mListView;
    EditText mContent;
    TextView mTextNum;
    GridView mGirdView;
    EditText mContactNumber;
    Button mCommit;
    ImageView fanhui;
    ArrayList<ImageItem> mImageItemLists = new ArrayList<>();
    List<Grade> lists = new ArrayList<>();
    List<Integer> positionLists = new ArrayList<>();
    CommonAdaper<Grade> mCommonAdaper;
    private MyGirdAdapter mAdapter;
    private static final int IMAGE_PICKER = 1;
    private int mTYPE;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_bao_class);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        mGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mImageItemLists.size()) {
                    initImagePicker(3);
                    Intent intent = new Intent(JuBaoClassActivity.this, ImageGridActivity.class);
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
        mCommit.setOnClickListener(this);
        fanhui.setOnClickListener(this);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listView);
        mContent = (EditText) findViewById(R.id.content);
        mTextNum = (TextView) findViewById(R.id.textNum);
        mGirdView = (GridView) findViewById(R.id.girdView);
        mContactNumber = (EditText) findViewById(R.id.contactNumber);
        mCommit = (Button) findViewById(R.id.commit);
        fanhui = (ImageView) findViewById(R.id.fanhui);
    }

    private void initData() {
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
            mCommonAdaper = new CommonAdaper<Grade>(this, lists, R.layout.item_jubao_video) {
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
    public void onClick(View v) {
        if (v.getId() == R.id.commit) {
            if (positionLists.size() == 0) {
                Toast.makeText(JuBaoClassActivity.this, "请选择投诉原因", Toast.LENGTH_SHORT).show();
            } else if (mContent.getText().toString().trim().isEmpty()) {
                Toast.makeText(JuBaoClassActivity.this, "请输入问题描述", Toast.LENGTH_SHORT).show();

            } else if (mImageItemLists.size() == 0) {
                Toast.makeText(JuBaoClassActivity.this, "请上传截图", Toast.LENGTH_SHORT).show();

            } else if (mContactNumber.getText().toString().isEmpty()) {
                Toast.makeText(JuBaoClassActivity.this, "请输入联系方式", Toast.LENGTH_SHORT).show();
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
                for (int i = 0; i < mImageItemLists.size(); i++) {
                    files.add(new File(mImageItemLists.get(i).path));
                }


                HttpParams params = new HttpParams();
                params.put("uid", Constans_VIdeo.uid);
                params.put("type", mTYPE);
                params.put("bindId", id);
                params.put("reportId", reportId);
                params.put("content", mContent.getText().toString().trim());
                params.put("phone", mContactNumber.getText().toString().trim());
                params.putFileParams("file[]", files);
                HttpUtils_Video http = new HttpUtils_Video();
                http.getData(UrlFactoty_Video.userReport, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        JSONObject obj = JSONObject.parseObject(s);
                        Toast.makeText(JuBaoClassActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        } else if (v.getId() == R.id.fanhui) {
            finish();
        }
    }


    @SuppressWarnings("unchecked")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null && requestCode == IMAGE_PICKER) {
                mImageItemLists = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mAdapter.notifyDataChanged(mImageItemLists);

            } else {
                Toast.makeText(JuBaoClassActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
