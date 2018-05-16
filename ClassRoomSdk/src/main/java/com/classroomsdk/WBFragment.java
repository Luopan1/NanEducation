package com.classroomsdk;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.talkcloud.roomsdk.IRoomWhiteBoard;
import com.talkcloud.roomsdk.RoomControler;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WBFragment extends Fragment implements IRoomWhiteBoard, WBCallback, LocalControl {

    private View fragmentView;
    XWalkView xWalkView;
    private View.OnClickListener pageClickListener;
    private boolean candraw = false;
    private boolean isTouchable = false;
    private boolean isClassBegin = false;
    private ShareDoc currentFile;
    private int role = -1;
    private boolean isPlayBack = false;

    public void setPlayBack(boolean playBack) {
        isPlayBack = playBack;
    }

    @SuppressLint("JavascriptInterface")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_white_pad, null);
            XWalkPreferences.setValue("enable-javascript", true);
            XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
            XWalkPreferences.setValue(XWalkPreferences.ALLOW_UNIVERSAL_ACCESS_FROM_FILE, true);

            XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, true);
            XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, true);

            xWalkView = (XWalkView) fragmentView.findViewById(R.id.xwalkWebView);
            xWalkView.setZOrderOnTop(false);
            XWalkSettings webs = xWalkView.getSettings();
            webs.setJavaScriptEnabled(true);
            webs.setCacheMode(WebSettings.LOAD_DEFAULT);
            webs.setDomStorageEnabled(true);
            webs.setDatabaseEnabled(true);
            webs.setAllowFileAccess(true);
            webs.setSupportZoom(false);
            webs.setBuiltInZoomControls(false);

            webs.setLoadWithOverviewMode(false);
            webs.setJavaScriptCanOpenWindowsAutomatically(true);
            webs.setLoadWithOverviewMode(true);
            webs.setDomStorageEnabled(true);
            webs.setUseWideViewPort(true);
            webs.setMediaPlaybackRequiresUserGesture(false);
            webs.setSupportSpatialNavigation(true);
            webs.setAllowFileAccessFromFileURLs(true);

            webs.setLayoutAlgorithm(XWalkSettings.LayoutAlgorithm.NORMAL);
            webs.setUseWideViewPort(true);

            xWalkView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            xWalkView.setHorizontalScrollBarEnabled(false);
            JSWhitePadInterface.getInstance().setWBCallBack(this);
            xWalkView.addJavascriptInterface(JSWhitePadInterface.getInstance(), "JSWhitePadInterface");
            xWalkView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//            xWalkView.requestFocus();
            xWalkView.setOnClickListener(pageClickListener);


            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0);
            } else {
                locale = Locale.getDefault();
            }
            String language = locale.getLanguage() + "-" + locale.getCountry();

            String lan = null;
            if (locale.equals(Locale.TAIWAN)) {
                lan = "tw";
            } else if (locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                lan = "ch";
            } else if (locale.equals(Locale.ENGLISH) || locale.toString().equals("en_US".toString())) {
                lan = "en";
            }
            if (TextUtils.isEmpty(lan)) {
                if (locale.toString().endsWith("#Hant")) {
                    lan = "tw";
                }
                if (locale.toString().endsWith("#Hans")) {
                    lan = "ch";
                }
            }
//            }
            if (Config.isWhiteBoardTest) {
//                xWalkView.loadUrl("http://192.168.1.251/index.html#/?languageType=" + lan);//建行
//                xWalkView.loadUrl("http://192.168.1.182:9403/publish/index.html#/mobileApp?ts=" + System.currentTimeMillis());
                xWalkView.loadUrl("http://192.168.1.23:9251/publish/index.html#/mobileApp?languageType=" + lan);//未经
//                 xWalkView.loadUrl("http://192.168.1.182:9403/publish/index.html#/mobileApp?languageType=" + lan);//react 重构广生
//                xWalkView.loadUrl("http://192.168.1.251:9251/publish/index.html#/mobileApp?ts=" + System.currentTimeMillis());//react 重构建行
//                xWalkView.loadUrl("http://m.banmalaoshi.com//tiyan//ThirdClassic//ThirdClassic.aspx");

            } else {
                xWalkView.loadUrl("file:///android_asset/react_mobile_publishdir/index.html#/mobileApp?languageType=" + lan);
            }

//            web_white_pad.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return false;
//                }
//
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                }
//            });
            xWalkView.setUIClient(new XWalkUIClient(xWalkView) {
                @Override
                protected Object getBridge() {
                    return super.getBridge();
                }

                @Override
                public void onPageLoadStarted(XWalkView view, String url) {
                    super.onPageLoadStarted(view, url);
                }

                @Override
                public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
                    super.onPageLoadStopped(view, url, status);
                }
            });

            xWalkView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {

//                        case MotionEvent.ACTION_DOWN:
//
//                        case MotionEvent.ACTION_MOVE:

                        case MotionEvent.ACTION_UP:
                            if (!candraw || !isTouchable) {
                                xWalkView.callOnClick();
                            } else {
                                return v.onTouchEvent(event);
                            }
                            break;
                        default:
                            break;
                    }
                    return false;
                }

            });
        } else {
            ViewGroup parent = (ViewGroup) fragmentView.getParent();
            if (parent != null) {
                parent.removeView(fragmentView);
            }
        }
        return fragmentView;
    }

    public void setWebWhitePadOnClickListener(View.OnClickListener padOnClickListener) {
        this.pageClickListener = padOnClickListener;
    }

//    public void setWBCallBack(WBStateCallBack wbCallBack) {
//        this.callBack = wbCallBack;
//    }

    public void setDrawable(boolean candraw) {
        this.candraw = candraw;
    }

    public void setWBTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    @Override
    public void onFileList(Object o) {
        ArrayList<ShareDoc> docList = new ArrayList<ShareDoc>();
        try {
            JSONArray jsobjs = new JSONArray(o.toString());
            for (int i = 0; i < jsobjs.length(); i++) {
                JSONObject jsobj = jsobjs.optJSONObject(i);
                ShareDoc doc = new ShareDoc();

                doc.setPdfpath(jsobj.optString("pdfpath"));
                doc.setFilepath(jsobj.optString("filepath"));
                doc.setAnimation(jsobj.optInt("animation"));
                doc.setStatus(jsobj.optInt("status"));
                doc.setDownloadpath(jsobj.optString("downloadpath"));
                doc.setPagenum(jsobj.optInt("pagenum"));
                doc.setUploadusername(jsobj.optString("uploadusername"));
                doc.setNewfilename(jsobj.optString("newfilename"));
                doc.setUploaduserid(jsobj.optInt("uploaduserid"));
                doc.setSwfpath(jsobj.optString("swfpath"));
                doc.setFileid(jsobj.optInt("fileid"));
                doc.setIsconvert(jsobj.optInt("isconvert"));
                doc.setSize(jsobj.optInt("size"));
                doc.setCompanyid(jsobj.optInt("companyid"));
                doc.setFileserverid(jsobj.optInt("fileserverid"));
                doc.setUploadtime(jsobj.optString("uploadtime"));
                doc.setActive(jsobj.optInt("active"));
                doc.setFilename(jsobj.optString("filename"));
                doc.setFiletype(jsobj.optString("filetype"));
                doc.setCurrentPage(1);
                doc.setCurrentTime(jsobj.optDouble("currenttime"));
                doc.setType(jsobj.optInt("type"));
                doc.setMedia(getIsMedia(doc.getFilename()));
                doc.setFileprop(jsobj.optInt("fileprop"));
                doc.setDynamicPPT(doc.getFileprop() == 2);
                doc.setGeneralFile(doc.getFileprop() == 0);
                doc.setH5Docment(doc.getFileprop() == 3);
//                if(doc.getFileprop() != 1){
                WhiteBoradManager.getInstance().addDocList(doc);
//                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean getIsMedia(String filename) {
        boolean isMedia = false;
        if (filename.toLowerCase().endsWith(".mp3")
                || filename.toLowerCase().endsWith("mp4")
                || filename.toLowerCase().endsWith("webm")
                || filename.toLowerCase().endsWith("ogg")
                || filename.toLowerCase().endsWith("wav")) {
            isMedia = true;
        }
        return isMedia;
    }

    @Override
    public boolean onRemoteMsg(boolean add, String id, String name, long ts, Object data, String fromID, String associatedMsgID, String associatedUserID) {
        if (add) {
            if (name.equals("ClassBegin")) {
                isClassBegin = true;
                JSWhitePadInterface.isClassbegin = true;

                if (RoomManager.getInstance().getMySelf().role == 0) {
                    if (RoomControler.isNotLeaveAfterClass()) {
                        WhiteBoradManager.getInstance().resumeFileList();
                        currentFile = WhiteBoradManager.getInstance().getCurrentFileDoc();
                    }
                    JSONObject jsdata = Packager.pageSendData(currentFile);
                    RoomManager.getInstance().pubMsg("ShowPage", "DocumentFilePage_ShowPage", "__all", jsdata.toString(), true, null, null);
                }
//                sendRoomTypeWB();

            } else if (id.equals("DocumentFilePage_ShowPage")) {
                currentFile = WhiteBoradManager.getInstance().getCurrentFileDoc();
                String strdata = (String) data;
                try {
                    JSONObject jsmdata = new JSONObject(strdata);
                    currentFile = Packager.pageDoc(jsmdata);
                    WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (name.equals("DocumentChange")) {
                String strdata = (String) data;
                ShareDoc doc = new ShareDoc();
                try {
                    JSONObject jsmdata = new JSONObject(strdata);
                    boolean isdel = Tools.isTure(jsmdata.get("isDel"));
                    doc = Packager.pageDoc(jsmdata);
                    if (!isClassBegin && doc.getFileid() == RoomManager.getInstance().currentMediaStreamFileId) {
                        RoomManager.getInstance().unPublishMedia();
                    }
                    WhiteBoradManager.getInstance().onRoomFileChange(doc, isdel, false, isClassBegin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (name.equals("ClassBegin")) {
                isClassBegin = false;
                JSWhitePadInterface.isClassbegin = false;
//                clearLcAllData();
                if (!RoomControler.isNotLeaveAfterClass()) {
                    WhiteBoradManager.getInstance().resumeFileList();
                    if (WhiteBoradManager.getInstance().getDefaultFileDoc() == null) {
                        if (WhiteBoradManager.getInstance().getDocList().size() > 1) {
                            currentFile = WhiteBoradManager.getInstance().getDocList().get(1);
                        } else {
                            if (WhiteBoradManager.getInstance().getDocList().size() > 0) {
                                currentFile = WhiteBoradManager.getInstance().getDocList().get(0);
                            } else {
                                currentFile = WhiteBoradManager.getInstance().getmBlankShareDoc();
                            }
                        }
                        /*setCurrentFile();*/
                    } else {
                        currentFile = WhiteBoradManager.getInstance().getDefaultFileDoc();
                    }
                    WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
                    JSONObject jsobj = new JSONObject();
                    JSONObject js = new JSONObject();
                    JSONObject resumedasta = Packager.pageSendData(currentFile);
                    try {
                        jsobj.put("data", resumedasta.toString());
                        jsobj.put("name", "ShowPage");
                        jsobj.put("id", "DocumentFilePage_ShowPage");
                        js.put("type", "room-pubmsg");
                        js.put("message", jsobj);
                        xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        JSONObject jsobj = new JSONObject();
        JSONObject js = new JSONObject();
        try {
            jsobj.put("id", id);
            jsobj.put("ts", ts);
            jsobj.put("data", data == null ? null : data.toString());
            jsobj.put("name", name);
            jsobj.put("fromID", fromID);
            if (add) {
                js.put("type", "room-pubmsg");
            } else {
                js.put("type", "room-delmsg");
            }
            js.put("message", jsobj);
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (name.equals("WBPageCount") || name.equals("SharpsChange")) {
            return true;
        }
        return false;
    }

    private void setCurrentFile() {
        if (RoomControler.isDocumentClassification()) {
            if (WhiteBoradManager.getInstance().getClassDocList().size() > 0) {
                currentFile = WhiteBoradManager.getInstance().getClassDocList().get(0);
                WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
            } else {
                if (WhiteBoradManager.getInstance().getAdminDocList().size() > 0) {
                    currentFile = WhiteBoradManager.getInstance().getAdminDocList().get(0);
                    WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
                } else {
                    currentFile = WhiteBoradManager.getInstance().getmBlankShareDoc();
                    WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
                }
            }
        } else {
            if (WhiteBoradManager.getInstance().getDocList().size() > 1) {
                currentFile = WhiteBoradManager.getInstance().getDocList().get(1);
                WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
            } else if (WhiteBoradManager.getInstance().getDocList().size() > 0) {
                currentFile = WhiteBoradManager.getInstance().getDocList().get(0);
                WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
            }
        }
    }

    @Override
    public void onRoomConnected(JSONArray jsonArray, List jsonObject) {
        Log.d("xiao", jsonObject.toString());
        changeWBUrlAndPort();

        JSONObject jsdata = new JSONObject();
        for (int i = 0; i < jsonObject.size(); i++) {
            JSONObject js = mapToJson((Map<String, Object>) jsonObject.get(i));
            String id = js.optString("id");
            Object data = js.opt("data");
            try {
                if (!js.optString("associatedMsgID", "").equals("VideoWhiteboard")) {
                    jsdata.put(id, js);
                }
                if ("ClassBegin".equals(js.optString("name"))) {
                    isClassBegin = true;
                    JSWhitePadInterface.isClassbegin = true;
//                    sendRoomTypeWB();

                } else if (id.equals("DocumentFilePage_ShowPage")) {
                    currentFile = WhiteBoradManager.getInstance().getCurrentFileDoc();
                    JSONObject jsmdata = null;
                    if (data instanceof JSONObject) {
                        jsmdata = (JSONObject) data;
                    } else if (data instanceof String) {
                        String strdata = (String) data;
                        jsmdata = new JSONObject(strdata);
                    }
                    currentFile = Packager.pageDoc(jsmdata);
                    WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
                    WhiteBoradManager.getInstance().getDocList();
                } else if (id.equals("WBPageCount")) {
                    JSONObject jsmdata = null;
                    if (data instanceof JSONObject) {
                        jsmdata = (JSONObject) data;
                    } else if (data instanceof String) {
                        String strdata = (String) data;
                        jsmdata = new JSONObject(strdata);
                    }
                    WhiteBoradManager.getInstance().getDocList().get(0).setPagenum(jsmdata.optInt("totalPage"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONArray jsaar = new JSONArray();
        for (RoomUser u : RoomManager.getInstance().getUsers().values()) {
            jsaar.put(u.toJson());
        }
        JSONObject js = new JSONObject();
        try {
            js.put("type", "room-connected");
            js.put("userlist", jsaar);
            js.put("msglist", jsdata);
            js.put("myself", RoomManager.getInstance().getMySelf().toJson());
            JSONObject jsProperties = new JSONObject();
            jsProperties.put("roomtype", RoomManager.getInstance().getRoomType());
            jsProperties.put("roomname", RoomManager.getInstance().getRoomName());

            if (RoomManager.getInstance().getRoomProperties() != null) {
                jsProperties.put("companyid", RoomManager.getInstance().getRoomProperties().optString("companyid"));
                jsProperties.put("chairmancontrol", RoomManager.getInstance().getRoomProperties().optString("chairmancontrol"));
                jsProperties.put("starttime", RoomManager.getInstance().getRoomProperties().optLong("starttime"));
                jsProperties.put("endtime", RoomManager.getInstance().getRoomProperties().optLong("endtime"));
                jsProperties.put("serial", RoomManager.getInstance().getRoomProperties().optString("serial"));
            }
            js.put("roomProperties", jsProperties);
            String strjs = js.toString();
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + strjs + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!jsdata.has("DocumentFilePage_ShowPage")) {
            ArrayList<ShareDoc> list = WhiteBoradManager.getInstance().getDocList();
            if (WhiteBoradManager.getInstance().getDefaultFileDoc() == null) {
                if (WhiteBoradManager.getInstance().getDocList().size() > 1) {
                    currentFile = WhiteBoradManager.getInstance().getDocList().get(1);
                } else {
                    if (WhiteBoradManager.getInstance().getDocList().size() > 0) {
                        currentFile = WhiteBoradManager.getInstance().getDocList().get(0);
                    } else {
                        currentFile = WhiteBoradManager.getInstance().getmBlankShareDoc();
                    }
                }
                /*setCurrentFile();*/
            } else {
                currentFile = WhiteBoradManager.getInstance().getDefaultFileDoc();
            }
            WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
            if (currentFile != null) {
                JSONObject jsobj = new JSONObject();
                JSONObject data = Packager.pageSendData(currentFile);
                JSONObject jsmsg = new JSONObject();
                try {
                    jsobj.put("data", data.toString());
                    jsobj.put("name", "ShowPage");
                    jsobj.put("id", "DocumentFilePage_ShowPage");
                    jsmsg.put("type", "room-pubmsg");
                    jsmsg.put("message", jsobj);
                    xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + jsmsg.toString() + ")");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
        RoomManager.getInstance().pubMsg("UpdateTime", "UpdateTime", RoomManager.getInstance().getMySelf().peerId, null, false, null, null);
    }

    public void changeWBUrlAndPort() {
        JSONObject js = new JSONObject();
        try {
            JSONObject jsServiceUrl = new JSONObject();
            if (TextUtils.isEmpty(RoomManager.getInstance().getClassDocServerAddr())) {
                jsServiceUrl.put("address", "http://" + RoomManager.getInstance().get_host());
                jsServiceUrl.put("port", RoomManager.getInstance().get_port());
            } else {
                jsServiceUrl.put("address", "http://" + RoomManager.getInstance().getClassDocServerAddr());
                jsServiceUrl.put("port", "80");
            }
            js.put("serviceUrl", jsServiceUrl);
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.changeInitPageParameterFormPhone(" + js + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void sendRoomTypeWB() {
//        JSONObject jsobj = new JSONObject();
//        try {
//            jsobj.put("roomType", RoomManager.getInstance().getRoomType());
//            if (xWalkView != null)
//                xWalkView.loadUrl("javascript:GLOBAL.phone.oneToMany (" + jsobj.toString() + ")");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    public void roomConnected(JSONObject jsdata) {
//        JSONObject jsobj = new JSONObject();
//        try {
//            jsobj.put("peerid", RoomManager.getInstance().getMySelf().peerId);
//            jsobj.put("nickname", RoomManager.getInstance().getMySelf().nickName);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (xWalkView != null) {
//            xWalkView.loadUrl("javascript:GLOBAL.phone.joinRoom(" + jsobj.toString() + ")");
//        }
//    }

    private static HashMap<String, Object> toHashMap(String str) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        try {
            JSONObject jsonObject = new JSONObject(str);
            Iterator it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Object value = jsonObject.get(key);
                data.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void roomManagerUserJoined(RoomUser roomUser, boolean b) {
        userjoin(roomUser);
    }

    @Override
    public void roomManagerUserLeft(RoomUser roomUser) {
        userleft(roomUser.peerId);
    }

    @Override
    public void roomManagerUserChanged(RoomUser roomUser, Map<String, Object> map, String s) {
        userChange(roomUser, map, s);
    }

    @Override
    public void roomManagerRoomLeaved() {
        roomDisConnect();
    }

    @Override
    public void roomManagerPlayBackClearAll() {
        roomPlaybackClearAll();
    }

    @Override
    public void roomManagerRoomConnectFaild() {
        roomDisConnect();
    }


//    private void sendPlayingListToWB() {
//        JSONObject jsusers = new JSONObject();
//        for (RoomUser u : RoomManager.getInstance().getUsers().values()) {
//            if (u.publishState > 0 && u.role == 2) {
//                try {
//                    jsusers.put(u.peerId, u.nickName);
//                    if (xWalkView != null) {
//                        xWalkView.loadUrl("javascript:GLOBAL.phone.userSelector (" + jsusers.toString() + ")");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            xWalkView.requestFocus();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void localChangeDoc() {
        JSONObject jsobj = new JSONObject();
        currentFile = WhiteBoradManager.getInstance().getCurrentFileDoc();
        JSONObject data = Packager.pageSendData(currentFile);
        try {
            jsobj.put("data", data.toString());
            jsobj.put("name", "ShowPage");
            jsobj.put("id", "DocumentFilePage_ShowPage");
            JSONObject js = new JSONObject();
            js.put("type", "room-pubmsg");
            js.put("message", jsobj);
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playbackPlayAndPauseController(boolean isplay) {
        if (xWalkView != null) {
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.playbackPlayAndPauseController (" + isplay + ")");
        }
    }

    private JSONObject mapToJson(Map<String, Object> map) {
        JSONObject jsb = new JSONObject(map);
        return jsb;
    }

    @Override
    public void pubMsg(String js) {
        Log.d("xiao", js);
        try {
            JSONObject jsobj = new JSONObject(js);
            String msgName = jsobj.optString("signallingName");
            String msgId = jsobj.optString("id");
            String toId = jsobj.optString("toID");
            String data = jsobj.optString("data");
            String associatedMsgID = jsobj.optString("associatedMsgID");
            String associatedUserID = jsobj.optString("associatedUserID");
            boolean save = jsobj.optBoolean("do_not_save", false);
            if (jsobj.has("do_not_save")) {
                save = false;
            } else {
                save = true;
            }

            JSONObject jsdata = new JSONObject(data);
            if (msgId.equals("DocumentFilePage_ShowPage")) {
                currentFile = Packager.pageDoc(jsdata);
                /*WhiteBoradManager.getInstance().addDocList(currentFile);*/
                WhiteBoradManager.getInstance().setCurrentFileDoc(currentFile);
                WhiteBoradManager.getInstance().getDocList();
            }
//            if (!isClassBegin||(RoomManager.getInstance().getMySelf().role == 2&&msgId.equals("DocumentFilePage_ShowPage"))) {
//                return;
//            }
            int myrole = RoomManager.getInstance().getMySelf().role;

            if (isClassBegin && msgId.equals("DocumentFilePage_ShowPage") && (myrole == 0 || (myrole == 2 && candraw))) {
                RoomManager.getInstance().pubMsg(msgName, msgId, toId, data, save, associatedMsgID, associatedUserID);
            } else if (isClassBegin && !msgId.equals("DocumentFilePage_ShowPage")) {
                RoomManager.getInstance().pubMsg(msgName, msgId, toId, data, save, associatedMsgID, associatedUserID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    @Override
    public void delMsg(String js) {
        Log.d("xiao", js);
        try {
            JSONObject jsobj = new JSONObject(js);
            String msgName = jsobj.optString("signallingName");
            String msgId = jsobj.optString("id");
            String toId = jsobj.optString("toID");
            String data = jsobj.optString("data");
//            Map<String,Object> datamap = new HashMap<String,Object>();
//            if(data!=null){
//                datamap =  toMap(data);
//            }
            boolean save = true;
            RoomManager.getInstance().delMsg(msgName, msgId, toId, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageFinished() {
        Log.d("xiao", "onPageFinished: ");

        final JSONObject j = new JSONObject();
        try {
            j.put("mClientType", 3);
            j.put("deviceType", 0);
            JSONObject jsServiceUrl = new JSONObject();
            if (TextUtils.isEmpty(RoomManager.getInstance().getClassDocServerAddr())) {
                jsServiceUrl.put("address", "http://" + RoomManager.getInstance().get_host());
                jsServiceUrl.put("port", RoomManager.getInstance().get_port());
            } else {
                jsServiceUrl.put("address", "http://" + RoomManager.getInstance().getClassDocServerAddr());
                jsServiceUrl.put("port", "80");
            }
            j.put("serviceUrl", jsServiceUrl);
            j.put("isSendLogMessage", true);
            j.put("playback", isPlayBack);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.setInitPageParameterFormPhone('" + j.toString() + "')");
                }
            });
        }
        WhiteBoradManager.getInstance().onPageFinished();
    }

    private void roomDisConnect() {
        if (xWalkView != null) {
            final JSONObject js = new JSONObject();
            try {
                js.put("type", "room-disconnected");
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeWebPageFullScreen(boolean isFull) {
        WhiteBoradManager.getInstance().fullScreenToLc(isFull);
    }

    @Override
    public void onJsPlay(String url, boolean isvideo, long fileid) {
        WhiteBoradManager.getInstance().onWBMediaPublish(url, isvideo, fileid);
    }

//    public void setAddPagePermission(boolean canAdd) {
//        JSONObject js = new JSONObject();
//        try {
//            js.put("addPagePermission", canAdd);
//            xWalkView.loadUrl("javascript:GLOBAL.phone.changeInitPageParameterFormPhone(" + js + ")");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    public void setTurnPagePermission(boolean canTrun) {
//        xWalkView.loadUrl("javascript:GLOBAL.phone.pageTurningPermission(" + canTrun + ")");
//    }

//    public void setToolBarMode(int mode) {
//        this.role = mode;
//        JSONObject js = new JSONObject();
//        try {
//            js.put("role", mode);
//            xWalkView.loadUrl("javascript:GLOBAL.phone.changeInitPageParameterFormPhone(" + js + ")");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    public void wbRequestFoucs(boolean isvi) {
//        if (xWalkView != null) {
//            if (isvi)
//                xWalkView.onResume();
//            else
//                xWalkView.onPause();
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity().isFinishing()) {
            xWalkView.removeAllViews();
            xWalkView.onDestroy();
        }
    }

    public void closeNewPptVideo() {
        if (xWalkView != null) {
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.closeDynamicPptWebPlay()");
        }
    }

    public void executeWebCallback(String callbackId, Map<String, Object> map) {
        if (xWalkView != null) {
            JSONObject js = new JSONObject();
            try {
                js.put("callbackId", callbackId);
                JSONObject jsdata = mapToJson(map);
                js.put("data", jsdata);
                xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.executeWebCallback(" + js.toString() + ")");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void roomPlaybackClearAll() {
        JSONObject js = new JSONObject();
        try {
            js.put("type", "room-playback-clear_all");
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void userleft(String userid) {
        JSONObject js = new JSONObject();
        try {
            js.put("type", "room-participant_leave");
            js.put("userid", userid);
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void userjoin(RoomUser user) {
        JSONObject js = new JSONObject();
        try {
            js.put("type", "room-participant_join");
            js.put("user", user.toJson());
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void userChange(RoomUser user, Map<String, Object> changedProperties, String fromId) {
        JSONObject js = new JSONObject();
        try {
            js.put("type", "room-userproperty-changed");
            JSONObject jsmsg = new JSONObject();
            jsmsg.put("userid", user.peerId);
            JSONObject jsmap = mapToJson(changedProperties);
            jsmsg.put("properties", jsmap);
            jsmsg.put("fromID", fromId);
            js.put("message", jsmsg);
            xWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
