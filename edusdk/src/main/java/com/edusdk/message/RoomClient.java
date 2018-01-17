package com.edusdk.message;

//import com.weiyicloud.whitepad.SharePadMgr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.edusdk.interfaces.JoinmeetingCallBack;
import com.edusdk.interfaces.MeetingNotify;
import com.edusdk.ui.RoomActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/***
 * xiaoyang for Customer
 */
public class RoomClient {
    private MeetingNotify notify;
    private JoinmeetingCallBack callBack;
    private static String sync = "";
    static private RoomClient mInstance = null;
    public static int Kickout_ChairmanKickout = 0;
    public static int Kickout_Repeat = 1;
    private static AsyncHttpClient client = new AsyncHttpClient();
    boolean isjoining = false;

    public static String webServer = "global.talk-cloud.net";
    //     public static String webServer = "global.talk-cloud.neiwang";
    //    public static String webServer = "demo.talk-cloud.net";


    static public RoomClient getInstance() {
        synchronized (sync) {
            if (mInstance == null) {
                mInstance = new RoomClient();
            }
            return mInstance;
        }
    }

    public void joinRoom(Activity activity, String host, int port, String nickname, String param, String domain) {

        //        checkRoom();
        Intent intent = new Intent(activity,
                RoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ip", host);
        bundle.putInt("port", port);
        bundle.putString("nickname", nickname);
        if (param != null && !param.isEmpty()) {
            bundle.putString("param", param);
        }
        bundle.putString("domain", domain);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /***
     * @param activity 当前Activity
     * @param map 进入教室参数集合
     */
    public void joinRoom(Activity activity, Map<String, Object> map) {
        if (isjoining) {
            return;
        }
        isjoining = true;
        checkRoom(activity, map);
    }

    public void joinPlayBackRoom(Activity activity, String temp) {
        temp = Uri.decode(temp);

        String[] temps = temp.split("&");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < temps.length; i++) {
            String[] t = temps[i].split("=");
            map.put(t[0], t[1]);
        }
        if (map.containsKey("path")) {
            String tempPath = "http://" + map.get("path");
            map.put("path", tempPath);
        }

        final String host = map.get("host") instanceof String ? (String) map.get("host") : "";
        final int port = map.get("port") instanceof Integer ? (Integer) map.get("port") : 80;
        final String serial = map.get("serial") instanceof String ? (String) map.get("serial") : "";
        final String nickname = map.get("nickname") instanceof String ? (String) map.get("nickname") : "";
        final String userid = map.get("userid") instanceof String ? (String) map.get("userid") : "";
        final int userrole = map.get("userrole") instanceof Integer ? (Integer) map.get("userrole") : 2;
        final String password = map.get("password") instanceof String ? (String) map.get("password") : "";
        final String param = map.get("param") instanceof String ? (String) map.get("param") : "";
        final String domain = map.get("domain") instanceof String ? (String) map.get("domain") : "";
        final String finalnickname = Uri.encode(nickname);
        final String path = map.get("path") instanceof String ? (String) map.get("path") : "";
        final int type = Integer.valueOf(map.get("type").toString());

        String url = "http://" + host + ":" + port + "/ClientAPI/checkroom";

        Intent intent = new Intent(activity,
                RoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("host", host);

        bundle.putInt("port", port);
        bundle.putString("nickname", finalnickname);
        bundle.putString("userid", userid);
        bundle.putString("password", password);
        bundle.putString("serial", serial);
        bundle.putInt("userrole", -1);
        if (param != null && !param.isEmpty()) {
            bundle.putString("param", param);
        }
        if (domain != null && !domain.isEmpty()) {
            bundle.putString("domain", domain);
        }
        if (path != null && !path.isEmpty()) {
            bundle.putString("path", path);
        }
        if (type != -1) {
            bundle.putInt("type", type);
        }
        RoomSession.getInstance().setServiceHost(host);
        RoomSession.getInstance().setServicePort(port);
        //                        intent.putExtras(bundle);
        //                        activity.startActivity(intent);
        //        RoomClient.getInstance().joinRoomcallBack(0);
        RoomSession.getInstance().setServiceHost(host);
        RoomSession.getInstance().setServicePort(port);
        getmobilename(activity, bundle, host, port);
    }

    public void checkRoom(final Activity activity, Map<String, Object> map) {
        final String host = map.get("host") instanceof String ? (String) map.get("host") : "";
        final int port = map.get("port") instanceof Integer ? (Integer) map.get("port") : 80;
        final String serial = map.get("serial") instanceof String ? (String) map.get("serial") : "";
        final String nickname = map.get("nickname") instanceof String ? (String) map.get("nickname") : "";
        final String userid = map.get("userid") instanceof String ? (String) map.get("userid") : "";
        final String password = map.get("password") instanceof String ? (String) map.get("password") : "";
        final String param = map.get("param") instanceof String ? (String) map.get("param") : "";
        final String domain = map.get("domain") instanceof String ? (String) map.get("domain") : "";
        final int userrole = map.get("userrole") instanceof Integer ? (Integer) map.get("userrole") : 2;
        final String servername = map.get("servername") instanceof String ? (String) map.get("servername") : "";

        String url = "http://" + host + ":" + port + "/ClientAPI/checkroom";
        RequestParams params = new RequestParams();


        if (param != null && !param.isEmpty())
            params.put("param", param);
        params.put("serial", serial);
        final String finalnickname = Uri.encode(nickname);
        params.put("nickname", finalnickname);
        if (password != null && !password.isEmpty()) {
            params.put("password", password);
        }

        if (domain != null && !domain.isEmpty())
            params.put("domain", domain);
        params.put("userrole", userrole);
        if (servername != null && !servername.isEmpty())
            params.put("servername", servername);


        //
        // params.put("instflag", 0 + "");
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    int nRet = response.getInt("result");
                    if (nRet == 0) {
                        int role = response.optInt("roomrole");
                        isjoining = false;
                        if (role == 0) {
                            if (callBack != null) {
                                callBack.callBack(5006);
                            }
                            return;
                        } else {
                            if (callBack != null) {
                                callBack.callBack(nRet);
                            }
                        }
                        Intent intent = new Intent(activity,
                                RoomActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("host", host);

                        bundle.putInt("port", port);
                        bundle.putString("nickname", finalnickname);
                        bundle.putString("userid", userid);
                        bundle.putString("password", password);
                        bundle.putString("serial", serial);
                        bundle.putInt("userrole", userrole);
                        if (param != null && !param.isEmpty()) {
                            bundle.putString("param", param);
                        }
                        if (domain != null && !domain.isEmpty()) {
                            bundle.putString("domain", domain);
                        }
                        if (servername != null && !servername.isEmpty()) {
                            bundle.putString("servername", servername);
                        }
                        intent.putExtras(bundle);
                        RoomSession.getInstance().setServiceHost(host);
                        RoomSession.getInstance().setServicePort(port);
                        RoomClient.getInstance().joinRoomcallBack(0);
                        getmobilename(activity, bundle, host, port);

                    } else {
                        isjoining = false;
                        if (callBack != null) {
                            callBack.callBack(nRet);
                        }
                    }

                } catch (JSONException e) {
                    isjoining = false;
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("emm", "error");
                isjoining = false;
                RoomClient.getInstance().joinRoomcallBack(-1);
            }
        });
    }

    private void getmobilename(final Activity activity, final Bundle bundle, String host, int port) {
        String url = "http://" + host + ":" + port + "/ClientAPI/getmobilename";
        RequestParams params = new RequestParams();
        client.post(url, params, new JsonHttpResponseHandler() {
            Intent intent = new Intent(activity,
                    RoomActivity.class);

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {

                try {
                    int nRet = response.getInt("result");
                    if (nRet == 0) {
                        bundle.putString("mobilename", response.optJSONArray("mobilename").toString());
                    }
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    isjoining = false;

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("emm", "error");
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                intent.putExtras(bundle);
                activity.startActivity(intent);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                intent.putExtras(bundle);
                activity.startActivity(intent);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /***
     * @param notify MeetingNotify的实现类
     * @param callBack JoinmeetingCallBack的实现类
     */
    public void regiestInterface(MeetingNotify notify, JoinmeetingCallBack callBack) {
        this.notify = notify;
        this.callBack = callBack;
    }

    public void setNotify(MeetingNotify notify) {
        this.notify = notify;
    }

    public void setCallBack(JoinmeetingCallBack callBack) {
        this.callBack = callBack;
    }

    public void joinRoomcallBack(int code) {
        if (this.callBack != null) {
            callBack.callBack(code);
        }
    }

    public void kickout(int res) {
        if (notify != null) {
            notify.onKickOut(res);
        }
    }

    /***
     * 警告权限
     * @param code 1没有视频权限2没有音频权限
     */
    public void warning(int code) {
        if (notify != null) {
            notify.onWarning(code);
        }
    }

    public void onClassBegin() {
        if (notify != null) {
            notify.onClassBegin();
        }
    }

    public void onClassDismiss() {
        if (notify != null) {
            notify.onClassDismiss();
        }
    }


}
