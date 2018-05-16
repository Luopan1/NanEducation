package com.edusdk.message;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.classroomsdk.ShareDoc;
import com.classroomsdk.WhiteBoradManager;
import com.edusdk.entity.ChatData;
import com.edusdk.tools.SoundPlayUtils;
import com.edusdk.tools.Tools;
import com.edusdk.ui.RoomActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.talkcloud.roomsdk.IRoomManagerCbk;
import com.talkcloud.roomsdk.RoomControler;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;
import com.talkcloud.roomsdk.Stream;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2017/5/9.
 * xiaoyang 分发消息
 */

public class RoomSession implements IRoomManagerCbk {
    public final static int RoomJoined = 1001;
    public final static int RoomLeaved = 1002;
    public final static int UserJoined = 1003;
    public final static int UserLeft = 1004;
    public final static int UserChanged = 1005;
    public final static int VideoPublished = 1006;
    public final static int VideoUnPublished = 1007;
    public final static int IceStatusChanged = 1008;
    public final static int MessageReceived = 1009;
    public final static int RemoteMsg = 1010;
    public final static int SelfEvicted = 1011;
    public final static int PlayNetVideo = 1012;
    public final static int PlayNetAudio = 1013;
    public final static int PlayNetSeek = 1014;
    public final static int PlayNetStop = 1015;
    public final static int UpdataGiftNum = 1016;
    public final static int RoomBreak = 1017;
    public final static int ShowVideoView = 1018;
    public final static int MediaControl = 1019;

    public final static int playBackClearAll = 1020;
    public final static int playBackUpdateTime = 1021;
    public final static int playBackDuration = 1022;
    public final static int playBackEnd = 1023;
    public final static int ShareScreen = 1024;
    public final static int CloseShareScreen = 1025;
    public final static int TakePhotoByCamera = 1026;
    public final static int UDPError = 1027;
    public final static int PlayMovie = 1028;
    public final static int UnPlayMovie = 1029;
    private Activity activity = null;

    private static AsyncHttpClient client = new AsyncHttpClient();
    public ArrayList<ChatData> chatList = new ArrayList<ChatData>();
    public static boolean isPause = false;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    List<RoomUser> playingList = Collections.synchronizedList(new ArrayList<RoomUser>());


    private String serviceHost = "";
    private int servicePort = 80;
    Timer t;
    private boolean isExitRoom = false;
    private boolean isBreak = false;
    //    private Set<String> playingSet = new HashSet<String>();
    public static HashMap<String, Boolean> playingMap = new HashMap<String, Boolean>();
    public static HashMap<String, RoomUser> unplayMap = new HashMap<String, RoomUser>();

    public static boolean isInRoom = false;
    public static boolean isClassBegin = false;
    private boolean isShowVideoView = false;
    private boolean isAudioHasDoc = false;

    public static Stream mediaPublishStream = null;
    public static Stream mediaunPublishStream = null;
    public static Stream updateAttributeStream = null;
    public static boolean AttributeStream = false;

    public static int myGiftNum = 0;
    public static boolean isMeCandraw = false;
    public static boolean isOpenCamera = false;
    public static boolean isShowMySelf = false;

    public static JSONArray jsVideoWBTempMsg = new JSONArray();
    public static boolean isRoomLeaved = false;
    public static boolean isShowVideoWB = false;

    /*public static long playBackTime = 0;*/

    public void addTempVideoWBRemoteMsg(boolean add, String id, String name, long ts, Object data, String fromID, String associatedMsgID, String associatedUserID) {
        if (add) {
            if (name.equals("VideoWhiteboard")) {
                isShowVideoWB = true;
            }
            JSONObject jsobj = new JSONObject();
            try {
                jsobj.put("id", id);
                jsobj.put("ts", ts);
                jsobj.put("data", data == null ? null : data.toString());
                jsobj.put("name", name);
                jsobj.put("fromID", fromID);
                if (!associatedMsgID.equals("")) {
                    jsobj.put("associatedMsgID", associatedMsgID);
                }
                if (!associatedUserID.equals("")) {
                    jsobj.put("associatedUserID", associatedUserID);
                }

                if (associatedMsgID.equals("VideoWhiteboard") || id.equals("VideoWhiteboard")) {
                    jsVideoWBTempMsg.put(jsobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            isShowVideoWB = false;
        }
    }

    public boolean isAudioHasDoc() {
        return isAudioHasDoc;
    }

    public HashMap<String, RoomUser> getUnplayMap() {
        return unplayMap;
    }

    public boolean isShowVideoView() {
        return isShowVideoView;
    }

    public void setShowVideoView(boolean showVideoView) {
        isShowVideoView = showVideoView;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setIsBreak(boolean isBreak) {
        this.isBreak = isBreak;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public void setIsExit(boolean isExit) {
        this.isExitRoom = isExit;
    }

    public String getHost() {
        return serviceHost;
    }

    public int getPort() {
        return servicePort;
    }

    private void addPlayingUser(String peerid, int publishState) {
//        playingSet.add(peerid);
        if (publishState > 0) {
//            if(RoomManager.getInstance().getRoomType() == 0){
//                if(RoomManager.getInstance().getUser(peerid)!=null&&RoomManager.getInstance().getUser(peerid).role!=1)
            playingMap.put(peerid, publishState >= 1);
//            }else{
//                playingMap.put(peerid, publishState >= 1);
//            }
        }
    }

    private void updatePlayingUser(String peerid, int publishState) {
        if (playingMap.containsKey(peerid) && publishState > 0)
            playingMap.put(peerid, publishState >= 1);
    }

    private void removePlayingUser(String peerid, int publishState) {
        playingMap.remove(peerid);
    }


    private static String sync = "";
    static private RoomSession mInstance = null;

    static public RoomSession getInstance() {
        synchronized (sync) {
            if (mInstance == null) {
                mInstance = new RoomSession();
            }
            return mInstance;
        }
    }

    @Override
    public void roomManagerRoomJoined() {

        isInRoom = true;
        isBreak = false;
        RoomManager.getInstance().getMySelf().nickName = StringEscapeUtils.unescapeHtml4(RoomManager.getInstance().getMySelf().nickName);
        Tools.HideProgressDialog();
        NotificationCenter.getInstance().postNotificationName(RoomJoined);
        if (t != null) {
            t.cancel();
            t = null;
        }

        String peerid = RoomManager.getInstance().getMySelf().peerId;
        if (RoomManager.getInstance().getRoomProperties() != null) {
            getGiftNum(RoomManager.getInstance().getRoomProperties().optString("serial"), peerid);
        }
    }

    private void getGiftNum(String roomNum, final String peerId) {

        String url = "http://" + serviceHost + ":" + servicePort + "/ClientAPI/getgiftinfo";
        RequestParams params = new RequestParams();
        params.put("serial", roomNum);
        params.put("receiveid", peerId);
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    int nRet = response.getInt("result");
                    final Map<String, Object> pert = new HashMap<String, Object>();
                    if (nRet == 0) {
                        JSONArray infos = response.optJSONArray("giftinfo");
                        JSONObject info = infos.getJSONObject(0);
                        final long gifnum = info.optInt("giftnumber", 0);
                        if (peerId != null && !peerId.isEmpty()) {
                            pert.put("giftnumber", gifnum);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "giftnumber", gifnum);
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("emm", "error=" + throwable.toString());
//                RoomClient.getInstance().joinRoomcallBack(-1);
            }
        });
    }

    public void getGiftNumJoinRoom(String roomNum, final String peerId, final String nickname, final Map<String, Object> paramsMap) {

        String url = "http://" + serviceHost + ":" + servicePort + "/ClientAPI/getgiftinfo";
        final RequestParams params = new RequestParams();
        params.put("serial", roomNum);
        params.put("receiveid", peerId);
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    int nRet = response.getInt("result");
                    final Map<String, Object> pert = new HashMap<String, Object>();
                    if (nRet == 0) {
                        JSONArray infos = response.optJSONArray("giftinfo");
                        JSONObject info = infos.getJSONObject(0);
                        long gifnum = info.optInt("giftnumber");
                        if (peerId != null && !peerId.isEmpty()) {
                            pert.put("giftnumber", gifnum);
                            NotificationCenter.getInstance().postNotificationName(UpdataGiftNum, gifnum);
                        } else {
                            pert.put("giftnumber", 0);
                            NotificationCenter.getInstance().postNotificationName(UpdataGiftNum, (long) 0);
                        }

                    }
                    Log.d("emm", "gifnum = " + pert.toString());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            RoomManager.getInstance().setTestServer("192.168.1.57", 8890);
                            int ret = RoomManager.getInstance().joinRoom(serviceHost, servicePort, nickname, paramsMap, pert, true);
                            if (ret != RoomManager.ERR_OK) {
                                Log.e("RoomActivity", "nonono");
                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("emm", "error=" + throwable.toString());
//                RoomClient.getInstance().joinRoomcallBack(-1);
            }
        });
    }


    @Override
    public void roomManagerRoomLeaved() {
        SoundPlayUtils.release();

        chatList.clear();
        isInRoom = false;
        isClassBegin = false;
        isMeCandraw = false;
        myGiftNum = 0;
        playingMap.clear();

        if (isExitRoom) {
            isExitRoom = false;
            WhiteBoradManager.getInstance().clear();
            RoomManager.getInstance().setCallbBack(null);
            RoomManager.getInstance().setWhiteBoard(null);
            if (t != null) {
                t.cancel();
                t = null;
            }
            NotificationCenter.getInstance().postNotificationName(RoomLeaved);
        } else {
//                Toast.makeText(this, "断网了！！！！！", Toast.LENGTH_LONG).show();\
            NotificationCenter.getInstance().postNotificationName(RoomBreak);
            isBreak = true;
            mediaPublishStream = null;
//            if (t != null) {
//                t.cancel();
//                t = null;
//            }
//            Log.d("emm","break");
//            if (t == null) {
//                t = new Timer();
//                t.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        if (activity != null) {
//                            activity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String peerid = RoomManager.getInstance().getMySelf().peerId;
//                                    getGiftNum(RoomManager.getInstance().getRoomProperties().optString("serial"),peerid);
//                                    Log.d("emm","getGiftNum");
//
//                                }
//                            });
//                        }
//                    }
//                }, 5000,5000);
//            }
        }
    }

    @Override
    public void roomManagerDidFailWithError(int i) {
        RoomClient.getInstance().joinRoomcallBack(i);
    }

    @Override
    public void roomManagerUserJoined(RoomUser classRoomUser, boolean b) {
        classRoomUser.nickName = StringEscapeUtils.unescapeHtml4(classRoomUser.nickName);
        if (b && classRoomUser.role != 4) {
            if (classRoomUser.role == 0 && RoomManager.getInstance().getMySelf().role == 0 ||
                    (RoomManager.getInstance().getRoomType() == 0 && classRoomUser.role == RoomManager.getInstance().getMySelf().role)) {
                RoomManager.getInstance().evictUser(classRoomUser.peerId);
            }
        }
        NotificationCenter.getInstance().postNotificationName(UserJoined, classRoomUser, b);
    }

    @Override
    public void roomManagerUserLeft(RoomUser classRoomUser) {
        NotificationCenter.getInstance().postNotificationName(UserLeft, classRoomUser);
        if (classRoomUser.role == 0) {
            if (WhiteBoradManager.getInstance().getCurrentMediaDoc().getFiletype() == null || WhiteBoradManager.getInstance().getCurrentMediaDoc().getFiletype().isEmpty()) {
                return;
            }
        }
    }

    public static RoomUser mClassRoomUser;
    public static Map<String, Object> mMap;

    @Override
    public void roomManagerUserChanged(RoomUser classRoomUser, Map<String, Object> map, String s) {
        /*  if (!classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
            return;
        }
        mClassRoomUser = classRoomUser;
        mMap = map;*/
        updatePlayingUser(classRoomUser.peerId, classRoomUser.publishState);
        if (classRoomUser.publishState >= 1)
            unplayMap.remove(classRoomUser.peerId);
        if (map.containsKey("giftnumber") && classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
            myGiftNum = Integer.valueOf(map.get("giftnumber").toString());
            SoundPlayUtils.play();
        }
        if (map.containsKey("candraw") && classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId) && RoomManager.getInstance().getMySelf().role == 2) {
            isMeCandraw = Tools.isTure(map.get("candraw"));
        }
        if (map.containsKey("servername") && classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
            String servername = (String) map.get("servername");
            SharedPreferences sp = activity.getSharedPreferences("classroom", activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("servername", servername);
            editor.commit();
            RoomManager.getInstance().switchService(servername);
        }
        if (classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
            if (classRoomUser.publishState != 0) {
                isShowMySelf = true;
            } else {
                isShowMySelf = false;
//                wbFragment.setDrawable(chUser.canDraw);
            }
        }
        NotificationCenter.getInstance().postNotificationName(UserChanged, classRoomUser, map, s);
    }

    @Override
    public void roomManagerUserPublished(RoomUser classRoomUser) {
        addPlayingUser(classRoomUser.peerId, classRoomUser.publishState);
        if (classRoomUser.publishState > 0)
            unplayMap.remove(classRoomUser.peerId);
        if (classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
            if (classRoomUser.publishState != 0) {
                isShowMySelf = true;
            } else {
                isShowMySelf = false;
            }
        }
        NotificationCenter.getInstance().postNotificationName(VideoPublished, classRoomUser);
    }

    @Override
    public void roomManagerUserUnPublished(RoomUser classRoomUser) {
        removePlayingUser(classRoomUser.peerId, classRoomUser.publishState);
        unplayMap.put(classRoomUser.peerId, classRoomUser);
        if (classRoomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
            isShowMySelf = false;
        }
        NotificationCenter.getInstance().postNotificationName(VideoUnPublished, classRoomUser);
    }

    @Override
    public void roomManagerSelfEvicted(int reason) {
        if (TextUtils.isEmpty(RoomActivity.path)) {
            isExitRoom = true;
            RoomManager.getInstance().leaveRoom();
            RoomClient.getInstance().kickout(reason == 1 ? RoomClient.Kickout_ChairmanKickout : RoomClient.Kickout_Repeat);
            NotificationCenter.getInstance().postNotificationName(SelfEvicted);
        }
    }

//    @Override
//    public void roomManagerIceStatusChanged(ClassRoomUser classRoomUser, String s) {
//        NotificationCenter.getInstance().postNotificationName(IceStatusChanged,classRoomUser,s);
//    }

    @Override
    public void roomManagerMessageReceived(RoomUser classRoomUser, String s, int type, long ts) {
        classRoomUser.nickName = StringEscapeUtils.unescapeHtml4(classRoomUser.nickName);
        ChatData cd = new ChatData();
        cd.setPeerid(classRoomUser.peerId);
        cd.setNickName(classRoomUser.nickName);
        cd.setRole(classRoomUser.role);
        if (type == 0) {
            cd.setMessage(s);
            chatList.add(cd);
        }
        NotificationCenter.getInstance().postNotificationName(MessageReceived);
    }

    @Override
    public void roomManagerOnRemoteMsg(boolean add, String id, String name, long ts, Object data, boolean inList, String fromID, String associatedMsgID, String associatedUserID) {
        addTempVideoWBRemoteMsg(add, id, name, ts, data, fromID, associatedMsgID, associatedUserID);
        if (add) {
            if (name.equals("ClassBegin")) {
                if (isClassBegin) {
                    return;
                }
                isClassBegin = true;

                boolean haveSit = false;
                int sitCount = 0;
                for (RoomUser u : RoomManager.getInstance().getUsers().values()) {
                    if (u.role == 2 && (u.publishState == 3 || u.publishState == 2)) {
                        sitCount++;
                    }
                }
                if (RoomManager.getInstance().getRoomProperties() != null && sitCount <= RoomManager.getInstance().getRoomProperties().optInt("maxvideo", 6)) {
                    haveSit = true;
                }

                if (TextUtils.isEmpty(RoomActivity.path)) {
                    if (!RoomControler.isReleasedBeforeClass()) {
                        if (RoomControler.isAutomaticUp() && haveSit && RoomManager.getInstance().getMySelf().role == 2) {
                            RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 3);
                        }
                    } else {
                        if (RoomControler.isAutomaticUp() && RoomManager.getInstance().getMySelf().role == 2 &&
                                RoomManager.getInstance().getMySelf().publishState != 3 && haveSit) {
                            RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 3);
                        }
                        if (!RoomControler.isAutomaticUp() && RoomManager.getInstance().getMySelf().role == 2) {
                            RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 0);
                        }
                    }
                    if (RoomManager.getInstance().getRoomType() == 0 && RoomManager.getInstance().getMySelf().role == 2 && RoomControler.isAutoHasDraw()) {
                        RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "candraw", true);
                    }
                }
                RoomClient.getInstance().onClassBegin();
            }
        } else {
            if (name.equals("ClassBegin")) {
                isClassBegin = false;
                try {
                    if (RoomManager.getInstance().getRoomProperties() != null && !RoomManager.getInstance().getRoomProperties().getString("companyid").equals("10035")) {
                        if (!RoomControler.isNotLeaveAfterClass()) {
                            isRoomLeaved = true;
                            RoomSession.getInstance().setIsExit(true);
                            RoomManager.getInstance().leaveRoom();
                        } else {
                            isRoomLeaved = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!RoomControler.isNotLeaveAfterClass()) {
                    RoomSession.getInstance().chatList.clear();
                }
                RoomClient.getInstance().onClassDismiss();
            }
        }
        NotificationCenter.getInstance().postNotificationName(RemoteMsg, add, id, name, ts, data);
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    @Override
    public void roomManagerOnMediaStatus(RoomUser classRoomUser, Map<String, String> map) {
        NotificationCenter.getInstance().postNotificationName(SelfEvicted);
    }

    //播放
    @Override
    public void roomManagerMediaPublish(Stream stream) {
        mediaunPublishStream = null;
        AttributeStream = false;
        mediaPublishStream = stream;
        updateAttributeStream = null;
        if (stream.isVideo()) {
            NotificationCenter.getInstance().postNotificationName(PlayNetVideo, stream);
        } else {
            NotificationCenter.getInstance().postNotificationName(PlayNetAudio, stream);
        }
    }

    //不播放
    @Override
    public void roomManagerMediaUnPublish(Stream stream) {
        updateAttributeStream = null;
        mediaunPublishStream = stream;
        NotificationCenter.getInstance().postNotificationName(PlayNetStop, stream);
    }

    //暂停
    @Override
    public void roomManagerUpdateAttributeStream(Stream stream, long pos, boolean isplay) {
        updateAttributeStream = stream;
        AttributeStream = isplay;
        NotificationCenter.getInstance().postNotificationName(MediaControl, stream, isplay);
    }

//    @Override
//    public void roomManagerRoomConnected(RoomUser roomUser) {
//        String peerid = RoomManager.getInstance().getMySelf().peerId;
//        getGiftNum(RoomManager.getInstance().getRoomProperties().optString("serial"), peerid);
//    }

    @Override
    public void roomManagerPlayBackClearAll() {
        if (chatList != null)
            chatList.clear();
        NotificationCenter.getInstance().postNotificationName(playBackClearAll);
    }

    @Override
    public void roomManagerPlayBackUpdateTime(long currenttime) {
        /*if (currenttime < playBackTime) {
            RoomManager.getInstance().seekPlayback(playBackTime);
            playBackTime = currenttime;
            return;
        } else {
            playBackTime = currenttime;
        }*/
        NotificationCenter.getInstance().postNotificationName(playBackUpdateTime, currenttime);
    }

    @Override
    public void roomManagerPlayBackDuration(long starttime, long endtime) {
        NotificationCenter.getInstance().postNotificationName(playBackDuration, starttime, endtime);
    }

    @Override
    public void roomManagerPlayBackEnd() {
        /*playBackTime = 0;*/
        NotificationCenter.getInstance().postNotificationName(playBackEnd);
    }

    @Override
    public void roomManagerCameraLost() {

    }

    @Override
    public void roomManagerPublishConnectFailed() {

    }

    @Override
    public void roomManagerScreenPublish(Stream stream) {
        NotificationCenter.getInstance().postNotificationName(ShareScreen, stream);
    }

    @Override
    public void roomManagerScreenUnPublish(Stream stream) {
        NotificationCenter.getInstance().postNotificationName(CloseShareScreen, stream);
    }

    @Override
    public void roomManagerMoviePublish(Stream stream) {
        NotificationCenter.getInstance().postNotificationName(PlayMovie, stream);
    }

    @Override
    public void roomManagerMovieUnPublish(Stream stream) {
        NotificationCenter.getInstance().postNotificationName(UnPlayMovie, stream);
    }

    @Override
    public void onCapturerStopped() {
        NotificationCenter.getInstance().postNotificationName(TakePhotoByCamera);
    }

    @Override
    public void onCapturerStarted(boolean b) {

    }

    /***
     *媒体文件播放失败
     */
    @Override
    public void onPublishError(int i) {

    }

    @Override
    public void onUDPError(int i) {
        NotificationCenter.getInstance().postNotificationName(UDPError, i);
        if (i == 2) {
            RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "udpstate", 2);
        }
    }

    @Override
    public void roomManagerRoomConnectFaild() {
        chatList.clear();
        isInRoom = false;
        isClassBegin = false;
        isMeCandraw = false;
        myGiftNum = 0;
        isExitRoom = true;
        WhiteBoradManager.getInstance().clear();
        isExitRoom = false;
        RoomManager.getInstance().setCallbBack(null);
        RoomManager.getInstance().setWhiteBoard(null);
        playingMap.clear();
        if (t != null) {
            t.cancel();
            t = null;
        }
        NotificationCenter.getInstance().postNotificationName(RoomLeaved);
        RoomClient.getInstance().joinRoomcallBack(-1);
    }

    public List<RoomUser> getPlayingList() {
        playingList.clear();
        for (String p : playingMap.keySet()) {
            if (playingMap.get(p)) {
                RoomUser u = RoomManager.getInstance().getUser(p);
                if (u != null) {
                    playingList.add(u);
                }
            }
        }
        return playingList;
    }

    private boolean isMp4(String filetype) {
        int icon = -1;
        if (filetype.toLowerCase().endsWith("mp4") || filetype.toLowerCase().endsWith("webm")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPlayingMe() {
        String myPeerid = RoomManager.getInstance().getMySelf().peerId;
        if (playingMap.containsKey(myPeerid) && playingMap.get(myPeerid))
            return true;
        else
            return false;
    }
}
