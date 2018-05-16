package com.classroomsdk;

import android.util.Log;

import com.talkcloud.roomsdk.RoomManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/3.
 */

public class JSWhitePadInterface {

    private static String sync = "";
    static private JSWhitePadInterface mInstance = null;
    public static boolean isClassbegin = false;

    static public JSWhitePadInterface getInstance() {
        synchronized (sync) {
            if (mInstance == null) {
                mInstance = new JSWhitePadInterface();
            }
            return mInstance;
        }
    }

    private WBCallback callBack;

    public void setWBCallBack(WBCallback wbCallBack) {
        this.callBack = wbCallBack;
    }

    @org.xwalk.core.JavascriptInterface
    public void pubMsg(String js) {
        if (callBack != null)
            callBack.pubMsg(js);

    }

    @org.xwalk.core.JavascriptInterface
    public void delMsg(String js) {
        if (callBack != null)
            callBack.delMsg(js);

    }

    @org.xwalk.core.JavascriptInterface
    public void onPageFinished(String temp) {
        if (callBack != null)
            callBack.onPageFinished();
    }

    @org.xwalk.core.JavascriptInterface
    public void printLogMessage(String msg) {
        Log.d("emm", msg);
    }

    @org.xwalk.core.JavascriptInterface
    public void changeWebPageFullScreen(boolean isFull) {
        if (callBack != null) {
            callBack.changeWebPageFullScreen(isFull);
        }
    }

    @org.xwalk.core.JavascriptInterface
    public void onJsPlay(final String videoData) {
        try {
            JSONObject jsdata = new JSONObject(videoData);
            String url = jsdata.getString("url");
            long fileid = ((Number)jsdata.opt("fileid")).longValue();
            boolean isvideo = Tools.isTure(jsdata.opt("isvideo"));
            if(RoomManager.getInstance().getMySelf().role == 2&&!RoomManager.getInstance().getMySelf().canDraw){
                return;
            }
            boolean issuccess = RoomManager.getInstance().unPublishMedia();
            if(!issuccess){
                RoomManager.isMediaPublishing = true;

                if(isClassbegin){
                    RoomManager.getInstance().publishMedia(url,isvideo,"",fileid,"__all");
                }else{
                    RoomManager.getInstance().publishMedia(url,isvideo,"",fileid, RoomManager.getInstance().getMySelf().peerId);
                }
            }
            if (callBack != null) {
                callBack.onJsPlay(url,isvideo,fileid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
