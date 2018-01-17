package www.test720.com.naneducation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import www.test720.com.naneducation.http.Constans;
import www.test720.com.naneducation.model.PayMentCallBack;
import www.test720.com.naneducation.personcenteractivity.RechargeActivity;
import www.test720.com.naneducation.personcenteractivity.SetUserPayMentPassworldActivtiy;
import www.test720.com.naneducation.view.OnPasswordInputFinish;
import www.test720.com.naneducation.view.PasswordView;
import www.test720.com.naneducation.view.TopPopupWindows;

/**
 * @author LuoPan on 2017/10/26 17:09.
 */

public class ShowPopwindows {

    static String pass = "";

    private static List<Integer> passworldLists = new ArrayList<>();

    public static PopupWindow showPaymentPop(final Context activity, String originaPrice, String sponnor, String getBackInteger, String pay_price, final PayMentCallBack callback) {

        final PopupWindow mPopWindow;

        int DisplayWidth = activity.getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = activity.getResources().getDisplayMetrics().heightPixels - getStatusBarHeight(activity);


        final View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_buy, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);


        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        mPopWindow = new PopupWindow(contentView, DisplayWidth, DisplayHeight, false);

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopWindow.setBackgroundDrawable(new ColorDrawable());

        PasswordView passworldView = (PasswordView) contentView.findViewById(R.id.passwordView);

        passworldView.setData(originaPrice, sponnor, getBackInteger, pay_price);

        passworldView.getComfirmPay().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.trim().length() != 6) {
                    Toast.makeText(activity, "请输入6位正确密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pass.trim().length() == 0) {
                    Toast.makeText(activity, "请输密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pass.trim().length() == 6) {
                    callback.payBack(pass);
                    pass = "";
                }
            }
        });
        passworldView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = "";
                mPopWindow.dismiss();
            }
        });

        passworldView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(List<Integer> password) {
                passworldLists.clear();
                passworldLists = password;
                pass = "";
                for (int i = 0; i < password.size(); i++) {
                    pass += passworldLists.get(i).toString();
                }


            }

            @Override
            public void deletePassWord(int index) {
                passworldLists.remove(index);
                pass = "";
                for (int i = 0; i < passworldLists.size(); i++) {
                    pass += passworldLists.get(i).toString();
                }
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

        return mPopWindow;
    }


    public static PopupWindow showNotEnoughMoney(final Context activity) {
        final PopupWindow mPopWindow;


        int DisplayWidth = activity.getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = activity.getResources().getDisplayMetrics().heightPixels - getStatusBarHeight(activity);


        final View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_not_enough_money, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        mPopWindow = new PopupWindow(contentView, DisplayWidth, DisplayHeight, false);
        mPopWindow.setBackgroundDrawable(new ColorDrawable());

        mPopWindow.setOutsideTouchable(true);

        mPopWindow.setTouchable(true);


        mPopWindow.setFocusable(false);
        mPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mPopWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);


        contentView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

        contentView.findViewById(R.id.gotoCZ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                Intent intent = new Intent(activity, RechargeActivity.class);
                activity.startActivity(intent);
            }
        });


        return mPopWindow;
    }


    public static PopupWindow showNotSetPayPass(final Context activity) {
        final PopupWindow mPopWindow;


        int DisplayWidth = activity.getResources().getDisplayMetrics().widthPixels;
        int DisplayHeight = activity.getResources().getDisplayMetrics().heightPixels - getStatusBarHeight(activity);


        final View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_not_set_pay_pass, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        mPopWindow = new PopupWindow(contentView, DisplayWidth, DisplayHeight, false);
        mPopWindow.setBackgroundDrawable(new ColorDrawable());

        mPopWindow.setOutsideTouchable(true);

        mPopWindow.setTouchable(true);


        mPopWindow.setFocusable(false);
        mPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mPopWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);


        contentView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

        contentView.findViewById(R.id.gotoCZ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                Intent intent = new Intent(activity, SetUserPayMentPassworldActivtiy.class);
                activity.startActivity(intent);
            }
        });


        return mPopWindow;
    }

    public static int getStatusBarHeight(Context activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static PopupWindow showJuBao(Activity activity, View view, View.OnClickListener listener) {
        TopPopupWindows topPopupWindows = new TopPopupWindows(activity, listener);
        topPopupWindows.showAsDropDown(view, 0, 0);
        return topPopupWindows;
    }


    public static PopupWindow showChooseMapPop(final Activity activity, String start, String end, final String startName, final String endName) {
        final PopupWindow mPopWindow;
        start = Constans.longitude + "," + Constans.lat;
        final String slat = start.split(",")[1];
        final String slong = start.split(",")[0];

        final String eLat = end.split(",")[1];
        final String elong = end.split(",")[0];
        Log.e("TAG++++start", slong + "______________" + slat);
        Log.e("TAG++++end", elong + "______________" + eLat);

        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(activity, R.layout.pop_window_choosemap, null);

        Button gaode = (Button) popView.findViewById(R.id.gaode);
        Button baidu = (Button) popView.findViewById(R.id.baidu);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;

        mPopWindow = new PopupWindow(popView, width, height);
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvilible(activity, "com.autonavi.minimap")) {
                    try {

                        Double bdSlat = Double.parseDouble(slat);
                        Double bdLong = Double.parseDouble(slong);
                        double[] start = baiduToGaoDe(bdLong, bdSlat);

                        Double endLat = Double.parseDouble(eLat);
                        Double endLong = Double.parseDouble(elong);

                        double[] end = baiduToGaoDe(endLong, endLat);

                        Intent intent = Intent.getIntent("androidamap://route/plan/?sid=BGVIS1&slat=" + start[1] + "&slon=" + start[0] + "&sname=" + startName + "&did=BGVIS2&dlat=" + end[1] + "&dlon=" + end[0] + "&dname=" + endName + "&dev=0&t=0");
                        activity.startActivity(intent);
                        mPopWindow.dismiss();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                } else {
                 /*   Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);*/
                    Toast.makeText(activity, "未安装高德地图", Toast.LENGTH_SHORT).show();
                }
            }
        });

        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvilible(activity, "com.baidu.BaiduMap")) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse("baidumap://map/direction?origin=name:" + startName + "|latlng:" + slat + "," + slong + "&destination" + "=name:" + endName + "|latlng:" + eLat + "," + elong + "&mode=driving&sy=0&index=0&target=1"));
                    activity.startActivity(intent);
                    mPopWindow.dismiss();
                } else {
                    Toast.makeText(activity, "未安装百度地图", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopWindow.dismiss();
            }
        });


        return mPopWindow;
    }

    /**
     * 判断地图是否安装
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 高德转百度
     */
    private static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }


    /***
     * 百度转高德
     * */

    private static double[] baiduToGaoDe(double bd_lon, double bd_lat) {
        double[] bd_lat_lon = new double[2];
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        bd_lat_lon[0] = z * Math.cos(theta);
        bd_lat_lon[1] = z * Math.sin(theta);
        return bd_lat_lon;
    }


}
