package www.test720.com.naneducation.activity;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.StudyHomeAdapter;
import www.test720.com.naneducation.application.MyApplication;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.FirstDataBean;
import www.test720.com.naneducation.bean.StudyHomeType;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.utils.SPUtils;


public class MainActivity extends BaseToolbarActivity {


    @BindView(R.id.HomeRecyclerView)
    RecyclerView mHomeRecyclerView;
    public LocationClient mLocationClient = null;
    @BindView(R.id.Loaction)
    ImageView mLoaction;
    @BindView(R.id.district)
    TextView mDistrict;
    @BindView(R.id.kefu)
    ImageView mKefu;
    @BindView(R.id.titleRelative)
    RelativeLayout mTitleRelative;
    @BindView(R.id.RefreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    public static int currentIndex = 0;
    public static boolean isSuccess = false;
    Dialog restDialog;


    private double mLat;
    private double mLon;
    private AlertDialog mAlertDialog;
    private PopupWindow mPopWindow;
    private TimerTask mTask;
    private AlertDialog mDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        if (currentIndex == 2) {
            mRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.startRefresh();
                    currentIndex = 0;
                }
            }, 200);
        }
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefreshing();
            }
        }, 10000);

        super.onResume();

    }

    @Override
    protected void initData() {
       /* Intent intent=new Intent(this, DialogService.class);
        startService(intent);*/

        firstInApp = getCurrentTime();
        goGetTime();
    }

    /**
     * 获取打开app的时候  服务创建的时间
     */
    private long firstInApp = 0;
    private long currentTime;
    private long spaceTime = 90 * 60 * 1000;
    private int clickTime = 0;

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 实时获取当前的时间
     */
    public void goGetTime() {
        Timer mTimer = new Timer("1");
        mTask = new TimerTask() {
            @Override
            public void run() {
                currentTime = System.currentTimeMillis();
                if (currentTime - firstInApp > spaceTime) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showRestDialog(MainActivity.this);
                        }
                    });
                }
            }
        };
        mTimer.schedule(mTask, 1000, 1000);
    }

    public void showRestDialog(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog_common);
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.pop_rest, null);
        if (mDialog == null) {
            mDialog = builder.create();
        }
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_TOAST));
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.myDialogAnim);
        window.setContentView(contentView);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT; //设置宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes(lp);

        contentView.findViewById(R.id.guanbi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTime++;
                if (clickTime == 1 || clickTime == 2) {
                    firstInApp = getCurrentTime();
                    spaceTime = 5 * 60 * 1000;
                } else if (clickTime == 3) {
                    spaceTime = 90 * 60 * 1000;
                    firstInApp = getCurrentTime();
                    spaceTime = 0;
                }
                mDialog.dismiss();
            }
        });
        contentView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTime++;
                if (clickTime == 1 || clickTime == 2) {
                    spaceTime = 5 * 60 * 1000;
                    firstInApp = getCurrentTime();
                } else if (clickTime == 3) {
                    spaceTime = 90 * 60 * 1000;
                    firstInApp = getCurrentTime();
                    clickTime = 0;
                }
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isSuccess) {
                    LogUtils.e(isSuccess);
                    comfirmIsOpnen(Constans.City, Constans.district);
                } else {
                    getLocation();
                }
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefreshing();
                    }
                }, 10000);
            }

            @Override
            public void onFinishRefresh() {


            }
        });

        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {

            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                if (connectionStatus.getValue() == 3) {
                    isCanAutoLogin = false;

                    Constans.uid = "";
                    Constans.head = "";
                    Constans.isBindbank = false;
                    Constans.token = "";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowToast("您的账号在其他设备登录");
                            if (isTopaAtivity(MainActivity.this)) {

                            } else {
                                jumpToActivity(MainActivity.class, true);
                            }
                           /* Constans.clearData();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("canGoBack", false);
                            jumpToActivity(MainActivity.class, bundle, true);*/

                        }
                    });


                }
            }
        });
    }

    private static boolean isCanAutoLogin = true;

    private boolean isTopaAtivity(Context activity) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName().equals(activity.getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    public void getLocation() {
        mLocationClient = new LocationClient(MyApplication.getContext());
        //声明LocationClient类
        //注册监听函数
        initLocation();
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                isSuccess = true;
                mLat = bdLocation.getLatitude();
                mLon = bdLocation.getLongitude();
                if (bdLocation.getDistrict() == null) {
                    Constans.district = "定位失败";
                    Constans.City = "";
                } else {
                    mLocationClient.stop();
                    Constans.district = bdLocation.getDistrict();
                }

                mDistrict.setText(Constans.district);
                Constans.longitude = String.valueOf(bdLocation.getLongitude());
                Constans.lat = String.valueOf(bdLocation.getLatitude());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        comfirmIsOpnen(Constans.City, Constans.district);

                    }
                }, 1500);
                MyApplication.thisCityName = bdLocation.getCity();

                Constans.City = bdLocation.getCity();
                LogUtils.e(bdLocation.getCity() + "____________" + bdLocation.getDistrict());
            }
        });
        mLocationClient.start();
    }


    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死


        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }

    @Override
    protected void initView() {

        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDistrict.setText(Constans.district);

        if (Constans.uid.isEmpty() && !SPUtils.getCount().isEmpty() && isCanAutoLogin) {
            autoLogin();
        } else if (Constans.uid.isEmpty() && !SPUtils.getWeiXinId().trim().isEmpty() && isCanAutoLogin) {
            WeiChatLogin(SPUtils.getWeiXinId());

        } else if (Constans.uid.isEmpty() && !SPUtils.getQQId().trim().isEmpty() && isCanAutoLogin) {
            QQOrWeiXinLogin(SPUtils.getQQId());
        }

        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.setEnableLoadmore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.startRefresh();
            }
        }, 200);

    }


    private void WeiChatLogin(final String openid) {
        HttpParams params = new HttpParams();
        params.put("unionid", openid);
        mSubscription = mHttpUtils.getData(UrlFactory.appWeChatLogin, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                cancleLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                cancleLoadingDialog();
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {

                cancleLoadingDialog();
                JSONObject obj = JSON.parseObject(s);
                if (obj.getInteger("code") == 1) {
                    Constans.head = obj.getJSONObject("data").getString("head");
                    if (obj.getJSONObject("data").getInteger("is_bindbank") == 0) {
                        Constans.isBindbank = false;
                    } else {
                        Constans.isBindbank = true;
                    }

                    if (obj.getJSONObject("data").getInteger("is_pass") == 0) {
                        Constans.isPass = false;
                    } else {
                        Constans.isPass = true;
                    }
                    Constans.name = obj.getJSONObject("data").getString("name");
                    Constans.uid = obj.getJSONObject("data").getString("uid");
                    Constans.token = obj.getJSONObject("data").getString("rong_cloud_token");
                    RongIMClient.connect(Constans.token, new RongIMClient.ConnectCallback() {

                        @Override
                        public void onTokenIncorrect() {
                        }

                        @Override
                        public void onSuccess(String s) {
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                        }
                    });
                } else {
                    ShowToast(obj.getString("msg"));
                }
            }
        });
    }


    private void QQOrWeiXinLogin(String weiXinId) {
        HttpParams params = new HttpParams();
        params.put("unionid", weiXinId);
        mHttpUtils.getData(UrlFactory.appQqLogin, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                JSONObject obj = JSON.parseObject(s);
                if (obj.getInteger("code") == 1) {
                    Constans.head = obj.getJSONObject("data").getString("head");
                    if (obj.getJSONObject("data").getInteger("is_bindbank") == 0) {
                        Constans.isBindbank = false;
                    } else {
                        Constans.isBindbank = true;
                    }

                    if (obj.getJSONObject("data").getInteger("is_pass") == 0) {
                        Constans.isPass = false;
                    } else {
                        Constans.isPass = true;
                    }
                    Constans.name = obj.getJSONObject("data").getString("name");
                    Constans.uid = obj.getJSONObject("data").getString("uid");
                    Constans.token = obj.getJSONObject("data").getString("rong_cloud_token");

                    RongIMClient.connect(Constans.token, new RongIMClient.ConnectCallback() {

                        @Override
                        public void onTokenIncorrect() {
                            ShowToast("onTokenIncorrect");
                        }

                        @Override
                        public void onSuccess(String s) {
                            Log.e("TAG+++++onSuccess", s);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.e("TAG+++++onError", errorCode + "");
                        }
                    });


                } else {
                    ShowToast(obj.getString("msg"));
                }

            }
        });
    }

    private void comfirmIsOpnen(String city, String district) {
        HttpParams params = new HttpParams();
        params.put("cname", city);
        params.put("qname", district);
        mSubscription = mHttpUtils.getData(UrlFactory.homeLocation, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ShowToast(e.getMessage());

            }

            @Override
            public void onNext(String s) {
                JSONObject obj = JSON.parseObject(s);
                if (obj.getInteger("code") == 101) {
                    mRefreshLayout.finishRefreshing();
                    mAlertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("当前区域暂未开通 请更换区域")//设置显示的内容
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                                    startActivityForResult(intent, 0x0005);
                                }

                            }).show();
                } else if (obj.getInteger("code") == 1) {
                    Constans.city_id = obj.getJSONObject("data").getString("cityID");
                    getFirstPageData();
                }

            }

        });
    }

    private void getFirstPageData() {
        HttpParams params = new HttpParams();
        params.put("cityId", Constans.city_id);
        mSubscription = mHttpUtils.getData(UrlFactory.homeSearch, params, 3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                mRefreshLayout.finishRefreshing();
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Gson gson = new Gson();
                FirstDataBean dataBean = gson.fromJson(s, FirstDataBean.class);

                List<FirstDataBean.DataBean.BannerBean> bannerLists = new ArrayList<>();

                List<FirstDataBean.DataBean.TopListoneBean> studySelectLists = new ArrayList<>();
                List<FirstDataBean.DataBean.TopListwoBean> studyVideoLists = new ArrayList<>();
                List<FirstDataBean.DataBean.TopListhreeBean> studyAllCourseLists = new ArrayList<>();

                List<FirstDataBean.DataBean.TrainListBean> studySignUpLists = new ArrayList<>();
                List<FirstDataBean.DataBean.SchoolListBean> studyFamousSchoolUpLists = new ArrayList<>();

                List<FirstDataBean.DataBean.ActListBean> studyActivityLists = new ArrayList<>();

                bannerLists.addAll(dataBean.getData().getBanner());
                studySelectLists.addAll(dataBean.getData().getTopListone());
                studyVideoLists.addAll(dataBean.getData().getTopListwo());
                studyAllCourseLists.addAll(dataBean.getData().getTopListhree());
                studySignUpLists.addAll(dataBean.getData().getTrainList());
                studyFamousSchoolUpLists.addAll(dataBean.getData().getSchoolList());

                studyActivityLists.addAll(dataBean.getData().getActList());
                List<Integer> integerList = new ArrayList<>();
                integerList.add(1);
                integerList.add(2);
                integerList.add(3);
                integerList.add(4);
                integerList.add(5);
                integerList.add(6);
                integerList.add(7);
                integerList.add(8);


                List<StudyHomeType> studyHomeTypeList = new ArrayList<>();
                studyHomeTypeList.add(new StudyHomeType(R.drawable.zhibo, 1, "直播"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.lubo, 2, "录播"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.taoke, 3, "套课"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.peixunbaom, 4, "培训机构"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.xuexiao, 5, "学校信息"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.huodong, 6, "团体活动"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.new_page, 7, "新闻"));
                studyHomeTypeList.add(new StudyHomeType(R.drawable.gerenhzongxin, 8, "个人中心"));


                StudyHomeAdapter homeAdapter = new StudyHomeAdapter(MainActivity.this, integerList,
                        bannerLists,
                        studyHomeTypeList,
                        studySelectLists,
                        studyVideoLists,
                        studyAllCourseLists,
                        studySignUpLists,
                        studyFamousSchoolUpLists,
                        studyActivityLists);
                mHomeRecyclerView.setAdapter(homeAdapter);


            }
        });

    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void autoLogin() {
        HttpParams params = new HttpParams();
        params.put("phone", SPUtils.getCount());
        params.put("password", SPUtils.getPWD());
        mSubscription = mHttpUtils.getData(UrlFactory.Login, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                cancleLoadingDialog();
                mSubscription.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                cancleLoadingDialog();
                ShowToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                JSONObject jsonObject = JSON.parseObject(s);
                ShowToast(jsonObject.getString("msg"));

                Constans.head = jsonObject.getJSONObject("data").getString("head");
                if (jsonObject.getJSONObject("data").getInteger("is_bindbank") == 0) {
                    Constans.isBindbank = false;
                } else {
                    Constans.isBindbank = true;
                }

                if (jsonObject.getJSONObject("data").getInteger("is_pass") == 0) {
                    Constans.isPass = false;
                } else {
                    Constans.isPass = true;
                }
                Constans.name = jsonObject.getJSONObject("data").getString("name");
                Constans.uid = jsonObject.getJSONObject("data").getString("uid");
                Constans.token = jsonObject.getJSONObject("data").getString("rong_cloud_token");
                RongIMClient.connect(Constans.token, new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                        ShowToast("onTokenIncorrect");
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.e("TAG++++", Constans.token);
                        Log.e("TAG+++++onSuccess", s);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.e("TAG+++++onError", errorCode + "");
                    }
                });
            }
        });

    }


    @OnClick({R.id.district, R.id.kefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.district:
                if (Constans.district.equals("定位失败")) {
                    new AlertDialog.Builder(this).setTitle("提示")//设置对话框标题
                            .setMessage("定位失败 请选择已开通区域")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                                    startActivityForResult(intent, 0x0005);
                                    dialog.dismiss();
                                }

                            }).show();//在按键响应事件中显示此对话框、
                } else {
                    Intent intent = new Intent(this, ChooseAreaActivity.class);
                    startActivityForResult(intent, 0x0005);
                }
                //                jumpToActivity(RoomActivity.class, false);
                break;
            case R.id.kefu:
                jumpToActivity(CustomerServiceActivity.class, false);
              /*  HashMap<String, Object> map = new HashMap<>();

                map.put("userrole", 2); //
                //                map.put("host", "192.168.1.17"); // 内网地址
                map.put("host", "global.talk-cloud.net"); //公网地址
                map.put("port", 80);  //端口
                map.put("serial", "1939868350"); //课堂号
                map.put("nickname", "ifind"); // 昵称
                RoomClient.getInstance().joinRoom(this, map);*/
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x0005 && resultCode == 0x0004) {
            Constans.City = data.getStringExtra("city");
            Constans.district = data.getStringExtra("district");

            if (mAlertDialog != null && mAlertDialog.isShowing()) {
                mAlertDialog.dismiss();
            }
            mDistrict.setText(Constans.district);

        }
    }

}
