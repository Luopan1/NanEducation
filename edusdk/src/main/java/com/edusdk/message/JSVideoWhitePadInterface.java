package com.edusdk.message;

/**
 * Created by Administrator on 2017/5/3.
 */

public class JSVideoWhitePadInterface {

    private static String sync = "";
    static private JSVideoWhitePadInterface mInstance = null;
    public static boolean isClassbegin = false;

    static public JSVideoWhitePadInterface getInstance() {
        synchronized (sync) {
            if (mInstance == null) {
                mInstance = new JSVideoWhitePadInterface();
            }
            return mInstance;
        }
    }

    private VideoWBCallback callBack;

    public void setVideoWBCallBack(VideoWBCallback wbCallBack) {
        this.callBack = wbCallBack;
    }

    @org.xwalk.core.JavascriptInterface
    public void pubMsg_videoWhiteboardPage(String js) {
        if (callBack != null)
            callBack.pubMsg(js);

    }

    @org.xwalk.core.JavascriptInterface
    public void delMsg_videoWhiteboardPage(String js) {
        if (callBack != null)
            callBack.delMsg(js);

    }

    @org.xwalk.core.JavascriptInterface
    public void onPageFinished_videoWhiteboardPage(String temp) {
        if (callBack != null)
            callBack.onPageFinishedVideoWhiteBoard();
    }

    @org.xwalk.core.JavascriptInterface
    public void printLogMessage_videoWhiteboardPage(String msg) {

//        Log.d("emm", msg);
    }






}
