package www.test720.com.naneducation.application;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.baidu.mapapi.SDKInitializer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.util.logging.Level;

import okhttp3.OkHttpClient;
import www.test720.com.naneducation.http.Constans;


/**
 * Created by LuoPan on 2017/10/9 12:28.
 */

public class MyApplication extends MultiDexApplication {
    public static MyApplication context;
    public static String thisCityName;
    public static String cityName;
    public static IWXAPI api;

    {
        PlatformConfig.setWeixin("wx69ca9f37e8c96143", "dbf528a66e68f98f97edf0695797f570");
        PlatformConfig.setQQZone("1106664430", "MrCwDClfvk2AaRu2");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SDKInitializer.initialize(this);


        /**友盟*/
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = true;
        UMShareAPI.get(this);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        okBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        okBuilder.addInterceptor(loggingInterceptor);

        OkGo.getInstance().init(this)
                .setOkHttpClient(okBuilder.build())   //必须调用初始化//建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);
        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0


        //         解决7.0不能打开照相机的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }


        LogUtils.getLogConfig()
                .configAllowLog(false)
                .configTagPrefix("CityGuestSociety")
                .configShowBorders(false)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
        preinitX5WebCore();

        api = WXAPIFactory.createWXAPI(this, Constans.WxId, true);
        //         将APP_ID注册到微信l
        api.registerApp(Constans.WxId);

    }

    private void preinitX5WebCore() {

    }

    public static MyApplication getContext() {
        return context;
    }


}
