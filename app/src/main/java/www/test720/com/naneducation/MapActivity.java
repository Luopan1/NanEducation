package www.test720.com.naneducation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.activity.SchoolInfoActivity;
import www.test720.com.naneducation.activity.TrainSchoolInfoActivity;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.TrrainAddress;
import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.http.UrlFactory;
import www.test720.com.naneducation.view.BitmapTextView;
import www.test720.com.naneducation.view.MapTextView;

import static android.R.attr.button;

public class MapActivity extends BaseToolbarActivity {


    @BindView(R.id.mapView)
    TextureMapView mMapView;
    @BindView(R.id.youer)
    ImageView mYouer;
    @BindView(R.id.xiaoxue)
    ImageView mXiaoxue;
    @BindView(R.id.chuzhong)
    ImageView mChuzhong;
    @BindView(R.id.gaozhong)
    ImageView mGaozhong;
    @BindView(R.id.zhixiao)
    ImageView mZhixiao;
    @BindView(R.id.schoolKindLinear)
    LinearLayout mSchoolKindLinear;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    private BitmapDescriptor mCurrentMarker;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    List<TrrainAddress.DataBean.ListBean> mLists = new ArrayList<>();
    private int mMapKind;
    HttpParams params = new HttpParams();

    @Override
    protected int getContentView() {

        return R.layout.activity_map;
    }


    /**
     * mMapKind==1 是学校
     * ==2 是培训机构
     */
    @Override
    protected void initData() {

        mMapKind = getIntent().getIntExtra("mapKind", 0);
        if (mMapKind == 2) {
            mSchoolKindLinear.setVisibility(View.GONE);
        } else {
            mSchoolKindLinear.setVisibility(View.VISIBLE);

        }

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        initBaiDu();
        mLocationClient.start();

        params.put("cityId", Constans.city_id);
        params.put("type", mMapKind);
        mSubscription = mHttpUtils.getData(UrlFactory.mapTypeList, params, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                Gson gson = new Gson();
                TrrainAddress address = gson.fromJson(s, TrrainAddress.class);

                mLists.clear();
                mBaiduMap.clear();
                mLists.addAll(address.getData().getList());
                if (mLists.size() != 0) {
                    LatLng point = new LatLng(Double.parseDouble(mLists.get(0).getA_lat()), Double.parseDouble(mLists.get(0).getA_long()));
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(point)
                            .zoom(12)
                            .build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mBaiduMap.setMapStatus(mMapStatusUpdate);
                    //改变地图状态
                    setData();
                }

            }


        });


    }

    private void setData() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.dingweilan);
        List<OverlayOptions> options = new ArrayList<>();
        for (int i = 0; i < mLists.size(); i++) {
            MapTextView bitmapTextView = new MapTextView();
            Bitmap mapText = bitmapTextView.getBitMap(getApplicationContext(), mLists.get(i).getA_name());
            BitmapDescriptor amp = BitmapDescriptorFactory.fromBitmap(mapText);

            options.add(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(mLists.get(i).getA_lat()), Double.parseDouble(mLists.get(i).getA_long())))
                    .zIndex(Integer.parseInt(mLists.get(i).getA_id()))
                    .title(mLists.get(i).getA_name())
                    .icon(amp));
            mBaiduMap.addOverlays(options);
        }

      /*for (int i=0;i<mLists.size();i++){
          Button button=new Button(getApplicationContext());
          button.setBackground(getResources().getDrawable(R.drawable.all_corners_base_color));
          button.setText(mLists.get(i).getA_name());
          LatLng pt=new LatLng(Double.parseDouble(mLists.get(i).getA_lat()),Double.parseDouble(mLists.get(i).getA_long()));
          InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
          mBaiduMap.showInfoWindow(mInfoWindow);


          Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.dingweilan);
          BitmapDescriptor amp = BitmapDescriptorFactory.fromBitmap(bitmap);
          List<OverlayOptions> options = new ArrayList<>();
          options.add(new MarkerOptions()
                  .position(new LatLng(Double.parseDouble(mLists.get(i).getA_lat()), Double.parseDouble(mLists.get(i).getA_long())))
                  .zIndex(Integer.parseInt(mLists.get(i).getA_id()))
                  .title(mLists.get(i).getA_name())
                    .icon(amp));
          mBaiduMap.addOverlays(options);
      }*/


    }


    @Override
    protected void setListener() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mMapKind == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", marker.getZIndex() + "");
                    jumpToActivity(TrainSchoolInfoActivity.class, bundle, false);
                } else if (mMapKind == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", marker.getZIndex() + "");
                    bundle.putString("title", marker.getTitle());
                    jumpToActivity(SchoolInfoActivity.class, bundle, false);
                }

                return false;
            }
        });
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar("地图");
        setTitleColor(R.color.black);
        setToolbarColor(R.color.white);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setOverlookingGesturesEnabled(true);
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.dingwei);


        mBaiduMap.setMyLocationEnabled(true);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomBy(5);
        mBaiduMap.animateMapStatus(mapStatusUpdate);


    }

    @OnClick({R.id.youer, R.id.xiaoxue, R.id.chuzhong, R.id.gaozhong, R.id.zhixiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.youer:
                params.put("typename", "幼儿");
                initData();
                break;
            case R.id.xiaoxue:
                params.put("typename", "小学");
                initData();
                break;
            case R.id.chuzhong:
                params.put("typename", "初中");
                initData();
                break;
            case R.id.gaozhong:
                params.put("typename", "高中");
                initData();
                break;
            case R.id.zhixiao:
                params.put("typename", "职校");
                initData();
                break;
        }
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(latitude)
                    .longitude(longitude).build();

            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);


        }
    }


    public void initBaiDu() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);


        option.setCoorType("bd09ll");

        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
