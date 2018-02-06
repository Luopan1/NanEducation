package www.test720.com.naneducation.http;

import android.content.Context;
import android.widget.Toast;

import com.alipay.share.sdk.openapi.algorithm.MD5;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

import www.test720.com.naneducation.application.MyApplication;

/**
 * @author LuoPan on 2017/10/27 17:40.
 */

public class Constans {
    public static String uid = "";
    public static String head = "";
    public static boolean isBindbank = false;
    public static boolean isPass = false;
    public static String name = "";
    public static String City = "";
    public static String district = "定位中...";
    public static String city_id = "0";
    public static String longitude = "";
    public static String lat = "";
    public static String WxId = "wx69ca9f37e8c96143";
    public static String unionid = "";
    public static String token = "";


    public static void clearData() {
        uid = "";
        head = "";
        isBindbank = false;
        isPass = false;
        name = "";
      /*  City = "";
        district = "定位中...";
        city_id = "0";*/
        longitude = "";
        lat = "";
        unionid = "";
        token = "";
    }

    public static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append("202cb962ac59075b964b07152d234b70");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        return appSign;
    }

    public static void genPayReq(Context context, String prepayId, String nonceStr) {
        if (!MyApplication.api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        PayReq req = new PayReq();
        req.appId = Constans.WxId;
        req.partnerId = "1494880252";
        req.prepayId = prepayId;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = nonceStr;
        req.timeStamp = String.valueOf((System.currentTimeMillis() / 1000) + "");
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        MyApplication.api.sendReq(req);
    }

}



