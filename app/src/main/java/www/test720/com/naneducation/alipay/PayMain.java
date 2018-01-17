package www.test720.com.naneducation.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class PayMain {


    Activity mContext;
    CallBack callBack;
    String notify_url;

    public PayMain(Activity mContext, CallBack callBack, String notify_url) {
        super();
        this.mContext = mContext;
        this.callBack = callBack;
        this.notify_url = notify_url;
    }

    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCrcGQ6HKLTPLu5cHA3tjsoG7UdI30hm5PAG74mP+kU206CAvlkYp2g2ULBe/2/Ox1MquhKJUnYoU2r8WO7l1v/IXqKdStMoePiJlOYc1RH45LV3ImCsFH8Nna5kXAzS5KS2VRbfg8hk+G5iZB65mdRL6vN5JOiwbOyxFlBXMAjHaomj/OPkfzhCIS4ZldoA6OSOeDu6xesV/aXeJ1J8N2/BKHGOT67YbFzapf4IANbDLhh+3zqFFGnTHuBV66yYepzijR5xbG3XO5EsJ0IZ92YFZfXvFvBw66QCufRlJftFpUQgWx1ACQMy/4yoMpMFvCh8nA0W9jW8O6BGNhqTn75AgMBAAECggEAAPUZujFav29BZSG5nrYH0ouFEWqXUk66HoKJJ1GIDbPD1noJxoO+eZ18//OfUSgVW85Aidl3dhxAgMtSUUPhIaWgN5G78iudSDAKm3lZaSVeNo+KvQ4bBxuOwi6Lv92WLffEvtYPY5Kd2hlokyciQJPRk87I/Em0pTTvgqvv7sJDTloIPSwQOST2VJ6O6HAvXsxO/mD5ELk9PIBQ+EznnpyOX/tC9prfFubVMRr6Z4VxLlQXI+LjAmhJdvnOlwLK8nw8Z7D9x/iDe0QEJNHLGTnA4mVpDfCw4rAl2XB+ogABAROjXt2fNcFV5qZCLeqCzJ/pJseUOMBxowLXNrvDAQKBgQDhfHUvo0ypjf5h2KOiQ5ijpMjtgGl6jV/B6G56XdvHpWvsZJ/0znoTEUWEaLRWxXBe1xmaV7CGirPkKByVZZqq07bpd2yNply2msLbb0aq/paYN7tDPv9KFxIsstQZ41z57ED9jMNjnNctdT/3ETmXYSe9KTCHGBQ6LNsRb7rW6QKBgQDCo5KogFfvKD5DV3HmzlH0DHEYuKv4QyDNWJ7KXBUkbWh0p8xjMvc4a/3Xc1LWM8tr44Xtx4eJ21bumLDpKjzJOOCoyWpL9jcobiCHqI/ExapJqZcpwH9Eu2V4QuID/LCCmrWg6ldHKwc93XEIRCyWAr5EDA6kDLxB//kS0VN9kQKBgH56ylzbU5A0FsGZvEezdPYt44rdIc1daZ4iG35GEZbOIlhqJRMzkmQJpx3bgXS+sQZxz2m00E7XlIphOY9Pe89gjFQu6dQWwiCndK+TnmciASUG9eKFfrt3b3llIfYXg7qom9fdUmgYvNZB04xpvA0+sQS5CwBsMERljsgcDQipAoGAD95EE6a7BI10XgsgByAlAE+PXOOZaZkXcIZcE8VkF98vl+siN6gtDZWIUWtegoYgfIBFJ/iPbUbLlJIwQiImaIVRk5EwMFc3cf1ge47bgcBXvL+tykKeXZ0ljaNuR8LvJQa17hOM4Al7AqkuEO118RjyKBwPiYJcSskypj0YoHECgYALOWexWokIrCHkF7yfXAZy4qUEiE0Gco65yPDfiYvWms/huD6QO83TBH8lJgA4UU3De1GQQBwMI9cw2CFbkusYmMhbgHEpcFXk6tPxg4GJqStkDT9i46X8pnfzerktO1jyYlUZ/FxcyNAfgwz8HyCYKtXAqd1VWwb96kWwJsPb/w==";

    public void Pay(String price, String orderNo) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, price, orderNo, notify_url, true);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, true);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult result = new PayResult((Map<String, String>) msg.obj);
            callBack.call(result);
        }
    };


    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2018010601646303";
    private static final int SDK_PAY_FLAG = 1;


}
