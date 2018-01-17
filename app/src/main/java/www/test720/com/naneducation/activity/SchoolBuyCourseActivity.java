package www.test720.com.naneducation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.ShowPopwindows;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.application.MyApplication;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.ActivitiOrder;
import www.test720.com.naneducation.bean.ActivityBuyOrder;
import www.test720.com.naneducation.bean.BindUser;
import www.test720.com.naneducation.bean.SchoolOrder;
import www.test720.com.naneducation.bean.TrainOrder;
import www.test720.com.naneducation.bean.TrainSchoolOrder;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.model.PayMentCallBack;
import www.test720.com.naneducation.personcenteractivity.MyBindInfoActivity;
import www.test720.com.naneducation.utils.DensityUtil;
import www.test720.com.naneducation.view.CircleImageView;

public class SchoolBuyCourseActivity extends BaseToolbarActivity {


    @BindView(R.id.schoolBadge)
    CircleImageView mSchoolBadge;
    @BindView(R.id.courseImage)
    ImageView mCourseImage;
    @BindView(R.id.coureTitle)
    TextView mCoureTitle;
    @BindView(R.id.coureMoney)
    TextView mCoureMoney;
    @BindView(R.id.signUpListView)
    ListView mSignUpListView;
    @BindView(R.id.allMoneyAndPeopleCount)
    TextView mAllMoneyAndPeopleCount;
    @BindView(R.id.toalMoney)
    TextView mToalMoney;
    @BindView(R.id.comfirmOrder)
    RelativeLayout mComfirmOrder;
    @BindView(R.id.donateConpany)
    TextView mDonateConpany;
    @BindView(R.id.titleRelative)
    RelativeLayout mTitleRelative;
    List<ActivitiOrder.DataBean.DetailBean.SignupNameBean> mPersonLists = new ArrayList<>();
    List<TrainOrder.DataBean.DetailBean.SignupNameBean> mTrainOrderLists = new ArrayList<>();
    List<SchoolOrder.DataBean.DetailBean.SignupNameBean> mSchoolOrderLists = new ArrayList<>();
    CommonAdaper<ActivitiOrder.DataBean.DetailBean.SignupNameBean> personAdapter;
    CommonAdaper<TrainOrder.DataBean.DetailBean.SignupNameBean> trianOrderAdapter;
    CommonAdaper<SchoolOrder.DataBean.DetailBean.SignupNameBean> schoolOrderAdapter;
    public static boolean isBuySuccess = false;

    public static int addPerson = 0x0001;
    @BindView(R.id.courseName)
    TextView mCourseName;
    private int mType;
    private String mId;
    private ActivitiOrder mActivitiOrder;
    private PopupWindow mPopupWindow;
    private TrainOrder mTrainOrder;
    private SchoolOrder mSchoolOrder;

    @Override
    protected int getContentView() {
        return R.layout.activity_school_buy_course;
    }

    @Override
    protected void initData() {
        if (mType == 1) {
            mTitleRelative.setVisibility(View.GONE);
            HttpParams params = new HttpParams();
            params.put("actId", mId);
            params.put("uid", Constans.uid);
            mSubscription = mHttpUtils.getData(UrlFactory.actbuyDetail, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                @Override
                public void onStart() {
                    showLoadingDialog();
                }

                @Override
                public void onCompleted() {
                    cancleLoadingDialog();
                    mSubscription.unsubscribe();
                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                    cancleLoadingDialog();
                }

                @Override
                public void onNext(String s) {
                    Gson gson = new Gson();
                    mActivitiOrder = gson.fromJson(s, ActivitiOrder.class);
                    Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mActivitiOrder.getData().getDetail().getAct_logo()).asBitmap().into(mCourseImage);
                    mCoureTitle.setText(mActivitiOrder.getData().getDetail().getAct_name());
                    mCoureMoney.setText(mActivitiOrder.getData().getDetail().getAct_money());
                    List<String> sponsor = mActivitiOrder.getData().getDetail().getSponsor();
                    String sponsoe = "";
                    for (int i = 0; i < sponsor.size(); i++) {
                        sponsoe += sponsor.get(i).trim() + " ";
                        mDonateConpany.setText(sponsoe);/**可能需要更改*/

                    }
                    Drawable drawable = context.getResources().getDrawable(R.drawable.jiage);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                    mCoureMoney.setCompoundDrawables(drawable, null, null, null);

                    mPersonLists.addAll(mActivitiOrder.getData().getDetail().getSignup_name());
                    setAdapter();


                }
            });
        } else if (mType == 2) {
            mTitleRelative.setVisibility(View.VISIBLE);
            HttpParams params = new HttpParams();
            params.put("cid", mId);
            params.put("uid", Constans.uid);
            mSubscription = mHttpUtils.getData(UrlFactory.coubuyDetail, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                @Override
                public void onStart() {
                    showLoadingDialog();
                }

                @Override
                public void onCompleted() {
                    cancleLoadingDialog();
                    mSubscription.unsubscribe();
                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                    cancleLoadingDialog();
                }

                @Override
                public void onNext(String s) {
                    Gson gson = new Gson();
                    mTrainOrder = gson.fromJson(s, TrainOrder.class);
                    Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mTrainOrder.getData().getDetail().getTrain_icon()).asBitmap().into(mSchoolBadge);
                    Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mTrainOrder.getData().getDetail().getC_logo()).asBitmap().into(mCourseImage);
                    mCourseName.setText(mTrainOrder.getData().getDetail().getTrain_name());
                    mCoureMoney.setText(mTrainOrder.getData().getDetail().getC_price());
                    mCoureTitle.setText(mTrainOrder.getData().getDetail().getC_name());
                    List<String> sponsor = mTrainOrder.getData().getDetail().getSponsor();
                    String sponsoe = "";
                    for (int i = 0; i < sponsor.size(); i++) {
                        sponsoe += sponsor.get(i).trim() + " ";
                        mDonateConpany.setText(sponsoe);/**可能需要更改*/
                    }
                    Drawable drawable = context.getResources().getDrawable(R.drawable.jiage);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                    mCoureMoney.setCompoundDrawables(drawable, null, null, null);
                    mTrainOrderLists.addAll(mTrainOrder.getData().getDetail().getSignup_name());
                    setAdapter();

                }
            });
        } else if (mType == 3) {
            mTitleRelative.setVisibility(View.VISIBLE);
            HttpParams params = new HttpParams();
            params.put("gradeId", mId);
            params.put("uid", Constans.uid);
            mSubscription = mHttpUtils.getData(UrlFactory.gradeBuyDetail, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                @Override
                public void onStart() {
                    showLoadingDialog();
                }

                @Override
                public void onCompleted() {
                    cancleLoadingDialog();
                    mSubscription.unsubscribe();
                }

                @Override
                public void onError(Throwable e) {
                    ShowToast(e.getMessage());
                    cancleLoadingDialog();
                }

                @Override
                public void onNext(String s) {
                    Gson gson = new Gson();
                    mSchoolOrder = gson.fromJson(s, SchoolOrder.class);

                    mSchoolOrderLists.addAll(mSchoolOrder.getData().getDetail().getSignup_name());
                    Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mSchoolOrder.getData().getDetail().getS_icon()).asBitmap().into(mSchoolBadge);
                    Glide.with(MyApplication.getContext()).load(UrlFactory.baseImageUrl + mSchoolOrder.getData().getDetail().getY_logo()).asBitmap().into(mCourseImage);
                    mCourseName.setText(mSchoolOrder.getData().getDetail().getS_name());
                    mCoureMoney.setText(mSchoolOrder.getData().getDetail().getY_xprice());
                    mCoureTitle.setText(mSchoolOrder.getData().getDetail().getY_name());

                    List<String> sponsor = mSchoolOrder.getData().getDetail().getSponsor();
                    String sponsoe = "";
                    for (int i = 0; i < sponsor.size(); i++) {
                        sponsoe += sponsor.get(i).trim() + " ";
                        mDonateConpany.setText(sponsoe);/**可能需要更改*/
                    }
                    Drawable drawable = context.getResources().getDrawable(R.drawable.jiage);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, DensityUtil.dip2px(context, 15), DensityUtil.dip2px(context, 15));
                    mCoureMoney.setCompoundDrawables(drawable, null, null, null);
                    setAdapter();
                }
            });
        }
    }

    private void setAdapter() {
        if (mType == 1)
            if (personAdapter == null) {
                personAdapter = new CommonAdaper<ActivitiOrder.DataBean.DetailBean.SignupNameBean>(this, mPersonLists, R.layout.item_add_person_item) {
                    @Override
                    public void convert(ViewHolder holder, ActivitiOrder.DataBean.DetailBean.SignupNameBean item, final int position) {
                        holder.setText(R.id.personName, item.getUsername());

                        if (position == mPersonLists.size() - 1) {
                            holder.getView(R.id.add_person).setVisibility(View.VISIBLE);
                            holder.getView(R.id.delete_person).setVisibility(View.GONE);
                        } else {
                            holder.getView(R.id.add_person).setVisibility(View.GONE);
                            holder.getView(R.id.delete_person).setVisibility(View.VISIBLE);
                        }

                        holder.getView(R.id.delete_person).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPersonLists.remove(position);
                                changeView();
                            }
                        });

                        holder.getView(R.id.add_person).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SchoolBuyCourseActivity.this, MyBindInfoActivity.class);
                                intent.putExtra("type", 1);
                                startActivityForResult(intent, addPerson);
                            }
                        });
                    }
                };
                mSignUpListView.setAdapter(personAdapter);
                changeView();
            } else {
                changeView();
            }


        if (mType == 2) {
            if (trianOrderAdapter == null) {
                trianOrderAdapter = new CommonAdaper<TrainOrder.DataBean.DetailBean.SignupNameBean>(this, mTrainOrderLists, R.layout.item_add_person_item) {
                    @Override
                    public void convert(ViewHolder holder, TrainOrder.DataBean.DetailBean.SignupNameBean item, final int position) {
                        holder.setText(R.id.personName, item.getUsername());

                        if (position == mTrainOrderLists.size() - 1) {
                            holder.getView(R.id.add_person).setVisibility(View.VISIBLE);
                            holder.getView(R.id.delete_person).setVisibility(View.GONE);
                        } else {
                            holder.getView(R.id.add_person).setVisibility(View.GONE);
                            holder.getView(R.id.delete_person).setVisibility(View.VISIBLE);
                        }

                        holder.getView(R.id.delete_person).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTrainOrderLists.remove(position);
                                changeView();
                            }
                        });

                        holder.getView(R.id.add_person).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SchoolBuyCourseActivity.this, MyBindInfoActivity.class);
                                intent.putExtra("type", 1);
                                startActivityForResult(intent, addPerson);
                            }
                        });
                    }
                };
                mSignUpListView.setAdapter(trianOrderAdapter);
                changeView();
            } else {
                changeView();
            }
        } else if (mType == 3) {
            if (schoolOrderAdapter == null) {
                schoolOrderAdapter = new CommonAdaper<SchoolOrder.DataBean.DetailBean.SignupNameBean>(this, mSchoolOrderLists, R.layout.item_add_person_item) {
                    @Override
                    public void convert(ViewHolder holder, SchoolOrder.DataBean.DetailBean.SignupNameBean item, final int position) {
                        holder.setText(R.id.personName, item.getUsername());

                        if (position == mSchoolOrderLists.size() - 1) {
                            holder.getView(R.id.add_person).setVisibility(View.VISIBLE);
                            holder.getView(R.id.delete_person).setVisibility(View.GONE);
                        } else {
                            holder.getView(R.id.add_person).setVisibility(View.GONE);
                            holder.getView(R.id.delete_person).setVisibility(View.VISIBLE);
                        }

                        holder.getView(R.id.delete_person).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSchoolOrderLists.remove(position);
                                changeView();
                            }
                        });

                        holder.getView(R.id.add_person).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SchoolBuyCourseActivity.this, MyBindInfoActivity.class);
                                intent.putExtra("type", 1);
                                startActivityForResult(intent, addPerson);
                            }
                        });
                    }
                };
                mSignUpListView.setAdapter(schoolOrderAdapter);
                changeView();
            } else {
                changeView();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x0006 && requestCode == addPerson) {
            BindUser.DataBean.UserlistBean grade = (BindUser.DataBean.UserlistBean) data.getSerializableExtra("person");
            if (mType == 1) {
                ActivitiOrder.DataBean.DetailBean.SignupNameBean bean = new ActivitiOrder.DataBean.DetailBean.SignupNameBean(grade.getBinduser_id(), grade.getKid(), grade.getUsername());
                mPersonLists.add(bean);
            } else if (mType == 2) {
                TrainOrder.DataBean.DetailBean.SignupNameBean bean = new TrainOrder.DataBean.DetailBean.SignupNameBean(grade.getBinduser_id(), grade.getKid(), grade.getUsername());
                mTrainOrderLists.add(bean);
            } else if (mType == 3) {
                SchoolOrder.DataBean.DetailBean.SignupNameBean bean = new SchoolOrder.DataBean.DetailBean.SignupNameBean(grade.getBinduser_id(), grade.getKid(), grade.getUsername());
                mSchoolOrderLists.add(bean);
            }
            changeView();
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;


    }

    @SuppressLint("SetTextI18n")
    public void changeView() {
        if (mType == 1) {
            personAdapter.notifyDataSetChanged();
            mAllMoneyAndPeopleCount.setText("¥" + mActivitiOrder.getData().getDetail().getAct_money() + "x" + mPersonLists.size());
            mToalMoney.setText("合计:" + Double.parseDouble(mActivitiOrder.getData().getDetail().getAct_money()) * mPersonLists.size());
        } else if (mType == 2) {

            trianOrderAdapter.notifyDataSetChanged();
            mAllMoneyAndPeopleCount.setText("¥" + mTrainOrder.getData().getDetail().getC_price() + "x" + mTrainOrderLists.size());
            mToalMoney.setText("合计:" + Double.parseDouble(mTrainOrder.getData().getDetail().getC_price()) * mTrainOrderLists.size());
        } else if (mType == 3) {

            schoolOrderAdapter.notifyDataSetChanged();
            mAllMoneyAndPeopleCount.setText("¥" + mSchoolOrder.getData().getDetail().getY_xprice() + "x" + mSchoolOrderLists.size());
            mToalMoney.setText("合计:" + Double.parseDouble(mSchoolOrder.getData().getDetail().getY_xprice()) * mSchoolOrderLists.size());
        }
    }

    @Override
    protected void initView() {
        initToobar("确认订单");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

        Intent intent = getIntent();

        mType = intent.getIntExtra("mType", 0);
        mId = intent.getStringExtra("id");
    }

    @OnClick(R.id.comfirmOrder)
    public void onClick() {
        if (isLogined(1)) {
            if (isSetPayPass()) {
                if (mType == 1) {
                    String kid = "[";
                    for (int i = 0; i < mPersonLists.size(); i++) {
                        if (i < mPersonLists.size() - 1)
                            kid += mPersonLists.get(i).getKid() + ",";
                        else
                            kid += mPersonLists.get(i).getKid() + "]";
                    }

                    final HttpParams params = new HttpParams();
                    params.put("actId", mId);
                    params.put("uid", Constans.uid);
                    params.put("userlist", kid);
                    params.put("costype", mActivitiOrder.getData().getDetail().getCost_type());
                    mSubscription = mHttpUtils.getData(UrlFactory.actSignUp, params, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                        @Override
                        public void onStart() {
                            showLoadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            cancleLoadingDialog();
                            mSubscription.unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            Log.e("TAG++++1", s);
                            JSONObject object = JSON.parseObject(s);
                            if (object.getInteger("code") == 103) {
                                mPopupWindow = ShowPopwindows.showNotEnoughMoney(SchoolBuyCourseActivity.this);

                            } else {
                                Gson gson = new Gson();
                                final ActivityBuyOrder buyOrder = gson.fromJson(s, ActivityBuyOrder.class);
                                String sponsor = "";
                                for (int i = 0; i < buyOrder.getData().getSponsor().size(); i++) {
                                    sponsor += buyOrder.getData().getSponsor().get(i) + " ";
                                }
                                mPopupWindow = ShowPopwindows.showPaymentPop(SchoolBuyCourseActivity.this, buyOrder.getData().getPrice() + "", sponsor, buyOrder.getData().getIntegral(), buyOrder.getData().getPayprice() + "", new PayMentCallBack() {
                                    @Override
                                    public void payBack(String pass) {
                                        HttpParams params = new HttpParams();
                                        params.put("uid", Constans.uid);
                                        params.put("paypass", pass);
                                        params.put("ordernum", buyOrder.getData().getO_number());
                                        goPay(params);
                                    }

                                });
                            }
                        }
                    });
                } else if (mType == 2) {
                    String kid = "[";
                    for (int i = 0; i < mTrainOrderLists.size(); i++) {
                        if (i < mTrainOrderLists.size() - 1)
                            kid += mTrainOrderLists.get(i).getKid() + ",";
                        else
                            kid += mTrainOrderLists.get(i).getKid() + "]";
                    }

                    HttpParams params = new HttpParams();
                    params.put("cid", mId);
                    params.put("uid", Constans.uid);
                    params.put("userlist", kid);
                    mSubscription = mHttpUtils.getData(UrlFactory.setCourseOrder, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                        @Override
                        public void onStart() {
                            showLoadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            cancleLoadingDialog();
                            mSubscription.unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            Log.e("TAG++++2", s);
                            JSONObject object = JSON.parseObject(s);
                            if (object.getInteger("code") == 103) {
                                mPopupWindow = ShowPopwindows.showNotEnoughMoney(SchoolBuyCourseActivity.this);

                            } else {
                                Gson gson = new Gson();
                                final TrainSchoolOrder order = gson.fromJson(s, TrainSchoolOrder.class);
                                String sponsor = "";
                                for (int i = 0; i < order.getData().getSponsor().size(); i++) {
                                    sponsor += order.getData().getSponsor().get(i) + " ";
                                }

                                mPopupWindow = ShowPopwindows.showPaymentPop(SchoolBuyCourseActivity.this, order.getData().getPrice() + "", sponsor, order.getData().getIntegral(), order.getData().getPayprice() + "", new PayMentCallBack() {
                                    @Override
                                    public void payBack(String pass) {
                                        HttpParams params = new HttpParams();
                                        params.put("uid", Constans.uid);
                                        params.put("paypass", pass);
                                        params.put("ordernum", order.getData().getO_number());
                                        goPay(params);
                                    }
                                });
                            }
                        }
                    });
                }
                if (mType == 3) {
                    String kid = "[";
                    for (int i = 0; i < mSchoolOrderLists.size(); i++) {
                        if (i < mSchoolOrderLists.size() - 1)
                            kid += mSchoolOrderLists.get(i).getKid() + ",";
                        else
                            kid += mSchoolOrderLists.get(i).getKid() + "]";
                    }

                    HttpParams params = new HttpParams();
                    params.put("cid", mId);
                    params.put("uid", Constans.uid);
                    params.put("userlist", kid);
                    mSubscription = mHttpUtils.getData(UrlFactory.gradeCreateOrder, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                        @Override
                        public void onStart() {
                            showLoadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            cancleLoadingDialog();
                            mSubscription.unsubscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ShowToast(e.getMessage());
                            cancleLoadingDialog();
                        }

                        @Override
                        public void onNext(String s) {
                            Log.e("TAG++++3", s);

                            JSONObject object = JSON.parseObject(s);
                            if (object.getInteger("code") == 103) {
                                mPopupWindow = ShowPopwindows.showNotEnoughMoney(SchoolBuyCourseActivity.this);

                            } else {
                                Gson gson = new Gson();
                                final TrainSchoolOrder order = gson.fromJson(s, TrainSchoolOrder.class);
                                String sponsor = "";
                                for (int i = 0; i < order.getData().getSponsor().size(); i++) {
                                    sponsor += order.getData().getSponsor().get(i) + " ";
                                }
                                mPopupWindow = ShowPopwindows.showPaymentPop(SchoolBuyCourseActivity.this, order.getData().getPrice() + "", sponsor, order.getData().getIntegral(), order.getData().getPayprice() + "", new PayMentCallBack() {
                                    @Override
                                    public void payBack(String pass) {
                                        HttpParams params = new HttpParams();
                                        params.put("uid", Constans.uid);
                                        params.put("paypass", pass);
                                        params.put("ordernum", order.getData().getO_number());
                                        goPay(params);
                                    }
                                });
                            }

                        }
                    });

                }
            } else {
                ShowPopwindows.showNotSetPayPass(this);
            }
        }
    }

    private void goPay(HttpParams params) {
        mSubscription = mHttpUtils.getData(UrlFactory.payChatOrder, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mSubscription.unsubscribe();
                mPopupWindow.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
                cancleLoadingDialog();
            }

            @Override
            public void onNext(String s) {
                mPopupWindow.dismiss();
                JSONObject obj = JSON.parseObject(s);
                ShowToast(obj.getString("msg"));
                GroupActivityInfoActivity.type = 0;
                isBuySuccess = true;
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            finish();
        }
    }
}
