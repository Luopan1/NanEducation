
package com.edusdk.ui;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classroomsdk.WBFragment;
import com.classroomsdk.WhiteBoradManager;
import com.edusdk.R;
import com.edusdk.adapter.ChatListAdapter;
import com.edusdk.adapter.FaceGVAdapter;
import com.edusdk.message.NotificationCenter;
import com.edusdk.message.RoomClient;
import com.edusdk.message.RoomSession;
import com.edusdk.tools.KeyBoardUtil;
import com.edusdk.tools.PersonInfo_ImageUtils;
import com.edusdk.tools.Tools;
import com.edusdk.view.DisListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.talkcloud.roomsdk.RoomControler;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.EglBase;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomFragment extends Fragment implements View.OnClickListener, NotificationCenter.NotificationCenterDelegate {

    private static String TAG = "kdemo";
    private View fragmentView;

    private final static Integer lock = 1;
    private static volatile Handler applicationHandler = null;

    private static String me = "ad";
    private static String watchingWhom = null;
    //    private boolean isExitRoom = false;

    private ChatListAdapter adapter;
//    private ServerListAdapter serverListAdapter;

//    private WebView web_white_pad;


    private SurfaceViewRenderer sf_teacher;
    private SurfaceViewRenderer sf_my_video;

    private RelativeLayout rel_teacher;
    private RelativeLayout rel_my_video;
    private DisListView list_chat;
    private ImageView img_memmber;
    private TextView txt_gift_num;
    private ImageView img_gift;
    private ImageView img_send_message;
    private FrameLayout wb_container;

    //private TextView txt_hand_up;
    private ImageView img_video;
    private ImageView img_audio;
    private ImageView img_clock;
    //    private ImageView img_server;
    private long localTime;
    private long classStartTime;
    private long serviceTime;
    //    Timer timerhide = new Timer();
    Timer timerAddTime;
    boolean m_blayoutsShow = true;
    private String lastPeerId = "";
//    private boolean isShowMySelf = false;


    //    private Timer timer;
    public WBFragment wbFragment;
    private boolean isStart = false;
    public static final int MOVE_PAGE = 2000;

    public boolean keyBoardShown = false;
    private boolean isClassBegin = false;
    private boolean isMuteAudio = false;
    private boolean isvisi = false;
    //ExData
    private String ip = "";
    private int port = 80;
    private String nickname = "";
    private String param = "";
    private String domain = "";
    //    Timer checkNetTimer = null;
    //    private boolean isInRoom = false;
    int netBreakCount = 0;
    boolean candraw = false;
    private ImageView iv_dissclass;
    private ImageView iv_photo;
    private AsyncHttpClient client = new AsyncHttpClient();
    private AlertDialog alertDialog;
    private boolean isVisiable = false;

    private RelativeLayout sf_re_background;
    private RelativeLayout t_re_background;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("xiao", "room_onCreateView");
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_room, null);
//            getExData();

//            web_white_pad = (WebView) fragmentView.findViewById(R.id.web_white_pad);


            wb_container = (FrameLayout) fragmentView.findViewById(R.id.wb_container);

            iv_photo = (ImageView) fragmentView.findViewById(R.id.iv_photo);
            list_chat = (DisListView) fragmentView.findViewById(R.id.list_chat);

            sf_teacher = (SurfaceViewRenderer) fragmentView.findViewById(R.id.sf_teacher);
            sf_teacher.init(EglBase.create().getEglBaseContext(), null);
            sf_teacher.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);

            sf_my_video = (SurfaceViewRenderer) fragmentView.findViewById(R.id.sf_my_video);
            sf_my_video.init(EglBase.create().getEglBaseContext(), null);
            sf_my_video.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);

            rel_teacher = (RelativeLayout) fragmentView.findViewById(R.id.rel_teacher);
            rel_my_video = (RelativeLayout) fragmentView.findViewById(R.id.rel_my);

            sf_re_background = (RelativeLayout) fragmentView.findViewById(R.id.sf_re_background);
            t_re_background = (RelativeLayout) fragmentView.findViewById(R.id.t_re_background);


//            img_server = (ImageView) fragmentView.findViewById(R.id.img_server);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            final int wid = dm.widthPixels;
            final int hid = dm.heightPixels - getStatusBarHeight();
            rel_teacher.setOnTouchListener(new View.OnTouchListener() {
                float x = 0;
                float y = 0;
                private boolean isFirstFinish;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (!isFirstFinish) {
                                x = event.getRawX();
                                y = event.getRawY();
                            }
                            RoomActivity.vi_contaioner.setNoScroll(true);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (!(Math.abs(x - event.getRawX()) < 30 && Math.abs(y - event.getRawY()) < 30)) {
                                if (rel_teacher.getLeft() - (x - event.getRawX()) >= 0 && rel_teacher.getRight() - (x - event.getRawX()) <= wid) {
                                    rel_teacher.setTranslationX(-(x - event.getRawX()));
                                }
                                if (rel_teacher.getTop() - (y - event.getRawY()) >= 0 && rel_teacher.getBottom() - (y - event.getRawY()) <= hid) {
                                    rel_teacher.setTranslationY(-(y - event.getRawY()));
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            isFirstFinish = true;
                            /*RoomActivity.vi_contaioner.setNoScroll(candraw);*/
                            break;
                    }
                    return true;
                }
            });

            rel_my_video.setOnTouchListener(new View.OnTouchListener() {
                float x = 0;
                float y = 0;
                private boolean isFirstFinish;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (!isFirstFinish) {
                                x = event.getRawX();
                                y = event.getRawY();
                            }
                            RoomActivity.vi_contaioner.setNoScroll(true);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (!(Math.abs(x - event.getRawX()) < 20 && Math.abs(y - event.getRawY()) < 20)) {
                                if (rel_my_video.getLeft() - (x - event.getRawX()) >= 0 && rel_my_video.getRight() - (x - event.getRawX()) <= wid) {
                                    rel_my_video.setTranslationX(-(x - event.getRawX()));
                                }
                                if (rel_my_video.getTop() - (y - event.getRawY()) >= 0 && rel_my_video.getBottom() - (y - event.getRawY()) <= hid) {
                                    rel_my_video.setTranslationY(-(y - event.getRawY()));
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            isFirstFinish = true;
                           /* RoomActivity.vi_contaioner.setNoScroll(candraw);*/
                            break;
                    }
                    return true;
                }
            });

            img_memmber = (ImageView) fragmentView.findViewById(R.id.img_memmber);
            img_hand_up = (ImageView) fragmentView.findViewById(R.id.hands_up);
            // txt_hand_up = (TextView) fragmentView.findViewById(R.id.txt_hand_up);
            img_send_message = (ImageView) fragmentView.findViewById(R.id.img_send_message);


            txt_gift_num = (TextView) fragmentView.findViewById(R.id.txt_gift_num);
            img_gift = (ImageView) fragmentView.findViewById(R.id.img_gift);
            img_video = (ImageView) fragmentView.findViewById(R.id.img_video);
            img_audio = (ImageView) fragmentView.findViewById(R.id.img_audio);
            iv_dissclass = (ImageView) fragmentView.findViewById(R.id.iv_dissclass);

            if (RoomActivity.userrole == 2) {
                txt_gift_num.setVisibility(View.VISIBLE);
            } else {
                iv_dissclass.setVisibility(View.GONE);
                txt_gift_num.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(RoomActivity.path)) {
                iv_dissclass.setVisibility(View.GONE);
                img_send_message.setVisibility(View.GONE);
                img_memmber.setVisibility(View.GONE);
//                img_server.setVisibility(View.GONE);
            }

            /*img_hand_up.setOnClickListener(this);*/

            img_memmber.setOnClickListener(this);
            //          txt_hand_up.setOnClickListener(this);
            img_video.setOnClickListener(this);
            img_audio.setOnClickListener(this);
            iv_dissclass.setOnClickListener(this);
            iv_photo.setOnClickListener(this);
            img_send_message.setOnClickListener(this);
//            img_server.setOnClickListener(this);
//            wb_container.requestFocus();
            adapter = new ChatListAdapter(RoomSession.getInstance().chatList, getActivity());
//            serverListAdapter = new ServerListAdapter(getActivity());
            list_chat.setAdapter(adapter);
            list_chat.setOnTouchListener(new View.OnTouchListener() {
                float x = 0;
                float y = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                           /* RoomActivity.vi_contaioner.setNoScroll(true);*/
                            x = event.getX();
                            y = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (x - event.getX() - y - event.getY() > 20) {
                                list_chat.setTranslationX(-(x - event.getX()));
                            } else {
                                v.onTouchEvent(event);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (x - event.getX() > 100) {
                                list_chat.setVisibility(View.INVISIBLE);
                            } else {
                                list_chat.setTranslationX(0);
                            }
                            /*RoomActivity.vi_contaioner.setNoScroll(candraw);*/
                            break;
                    }
                    return true;
                }
            });

            wbFragment = new WBFragment();
            WhiteBoradManager.getInstance().setWBCallBack((RoomActivity) getActivity());
            if (RoomActivity.path != null && !RoomActivity.path.isEmpty()) {
                wbFragment.setPlayBack(true);
            }
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.wb_container, wbFragment);
            ft.commit();
            wbFragment.setWebWhitePadOnClickListener(this);

            WhiteBoradManager.getInstance().setFileServierUrl(RoomSession.getInstance().getHost());
            WhiteBoradManager.getInstance().setFileServierPort(RoomSession.getInstance().getPort());
            WhiteBoradManager.getInstance().setLocalControl(wbFragment);
            RoomManager.getInstance().setCallbBack(RoomSession.getInstance());
            RoomManager.getInstance().setWhiteBoard(wbFragment);


        } else {
            ViewGroup parent = (ViewGroup) fragmentView.getParent();
            if (parent != null) {
                parent.removeView(fragmentView);
            }
        }
//        if (checkNetTimer == null) {
//
//            checkNetTimer = new Timer();
//            checkNetTimer.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    if (!Tools.isNetworkAvailable(getActivity()) && RoomSession.isInRoom && netBreakCount == 10) {
//                        wbFragment.roomDisConnect();
//                    }
//                    netBreakCount++;
//                }
//            }, 0, 1000);
//        }
       /* fragmentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        int[] loca = new int[2];
//                        lin_input_chat.getLocationOnScreen(loca);
//                        int heightDiff = fragmentView.getRootView().getHeight() - fragmentView.getHeight();
//                        // 大于100像素，是打开的情况
//                        if (heightDiff > 100) {
//                            // 如果已经打开软键盘，就不理会
//                            if (keyBoardShown) {
//                                return;
//                            }
//                            // do something when keyboard show，
//                            // i.e. listView or recyclerView scrolls to bottom
//                            img_hand_up.setVisibility(View.GONE);
//                            keyBoardShown = true;
//                            return;
//                        }else{
//                            // 软键盘收起的情况
//                            if(RoomManager.getInstance().getMySelf()!=null){
//                                int publisthstate = RoomManager.getInstance().getMySelf().publishState;
//                                if((publisthstate == 0||publisthstate == 2)&&isClassBegin){
//                                    img_hand_up.setVisibility(View.VISIBLE);
//                                }else{
//                                    img_hand_up.setVisibility(View.GONE);
//                                }
//                                keyBoardShown = false;
//                            }
//
//                        }
                        DisplayMetrics dm = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                        final int wid = dm.widthPixels;
                        final int hid = dm.heightPixels;
                        int screenHeight = hid;
                        //阀值设置为屏幕高度的1/3
                        int keyHeight = screenHeight / 3;
                        int[] loca = new int[2];
                        lin_input_chat.getLocationOnScreen(loca);
                        int inputHight = hid - loca[1];
                        if (inputHight > keyHeight) {
                            // 如果已经打开软键盘，就不理会
                            if (keyBoardShown) {
                                return;
                            }
                            // do something when keyboard show，
                            // i.e. listView or recyclerView scrolls to bottom
                            //txt_hand_up.setVisibility(View.GONE);
                            keyBoardShown = true;
                        } else {
                            //软键盘收起的情况
                            if (RoomManager.getInstance().getMySelf() != null) {
                                if ((RoomManager.getInstance().getMySelf().publishState == 2 || RoomManager.getInstance().getMySelf().publishState == 0 || RoomManager.getInstance().getMySelf().publishState == 4) && isClassBegin) {
                                    if (RoomManager.getInstance().getMySelf().disableaudio) {
                                        //txt_hand_up.setBackgroundResource(R.drawable.round_back_red_black);
                                    } else {
                                        //txt_hand_up.setBackgroundResource(R.drawable.round_back_red);
                                    }
//                            txt_hand_up.setClickable(true);
                                } else {
                                    // txt_hand_up.setBackgroundResource(R.drawable.round_back_red_black);
//                            txt_hand_up.setClickable(false);
                                }
                                if (RoomManager.getInstance().getMySelf().properties.containsKey("raisehand")) {
                                    boolean israisehand = Tools.isTure(RoomManager.getInstance().getMySelf().properties.get("raisehand"));
                                    if (israisehand) {
                                        //  txt_hand_up.setText(R.string.no_raise);
                                    } else {
                                        //txt_hand_up.setText(R.string.raise); //同意了，或者拒绝了
                                    }
                                } else {
                                    //txt_hand_up.setText(R.string.raise); //还没举手
                                }
                                if (RoomManager.getInstance().getRoomType() != 0) {
                                    // txt_hand_up.setVisibility(View.VISIBLE);
                                } else {
                                    // txt_hand_up.setVisibility(View.GONE);
                                }
                            }
                            keyBoardShown = false;
                        }
                    }
                    // do something when keyboard hide

                }
        );*/

//        lin_input_chat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                DisplayMetrics dm = new DisplayMetrics();
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//                final int wid = dm.widthPixels;
//                final int hid = dm.heightPixels;
//                int screenHeight = hid;
//                //阀值设置为屏幕高度的1/3
//                int keyHeight = screenHeight / 3;
//                int[] loca = new int[2];
//                lin_input_chat.getLocationOnScreen(loca);
//                int inputHight = hid - loca[1];
//                if (inputHight > keyHeight) {
//                    // 如果已经打开软键盘，就不理会
//                    if (keyBoardShown) {
//                        return;
//                    }
//                    // do something when keyboard show，
//                    // i.e. listView or recyclerView scrolls to bottom
//                    img_hand_up.setVisibility(View.GONE);
//                    keyBoardShown = true;
//                } else {
//                    //软键盘收起的情况
//                    if (RoomManager.getInstance().getMySelf() != null) {
//                        int publisthstate = RoomManager.getInstance().getMySelf().publishState;
//                        if ((publisthstate == 0 || publisthstate == 2) && isClassBegin) {
//                            img_hand_up.setVisibility(View.VISIBLE);
//                        } else {
//                            img_hand_up.setVisibility(View.GONE);
//                        }
//                        keyBoardShown = false;
//                    }
//
//                }
//            }
//
//        });

        /*img_hand_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!RoomSession.isClassBegin) {
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
                        break;
                    case MotionEvent.ACTION_UP:
                        RoomUser roomUser = RoomManager.getInstance().getMySelf();
                        //判断是否在台上
                        if (roomUser.publishState == 0) {
                            RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
                        } else {
                            RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", false);
                        }
                        break;
                }
                return true;
            }
        });*/

        img_hand_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!RoomSession.isClassBegin) {
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RoomUser roomUser = RoomManager.getInstance().getMySelf();
                        if (roomUser != null && roomUser.publishState != 0) {
                            RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        RoomUser user = RoomManager.getInstance().getMySelf();
                        //判断是否在台上
                        if (user.publishState == 0) {
                            if (RoomManager.getInstance().getMySelf().properties.containsKey("raisehand")) {
                                boolean israisehand = Tools.isTure(RoomManager.getInstance().getMySelf().properties.get("raisehand"));
                                if (israisehand) {
                                    RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", false);
                                } else {
                                    RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
                                }
                            } else {
                                RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
                            }
                        } else {
                            RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", false);
                        }
                        break;
                }
                return true;
            }
        });

        initChatExpressionPop();
        initToolBarPop();
        initStaticFaces();
        return fragmentView;
    }

    private void initGridView() {
        FaceGVAdapter mGvAdapter = new FaceGVAdapter(staticFacesList, getContext());
        chart_face_gv.setAdapter(mGvAdapter);
        chart_face_gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        chart_face_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 41) {
                        delete();
                    }
                    if (position < 8) {
                        insert(getFace(staticFacesList.get(position)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void delete() {
        if (edt_input_chat.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(edt_input_chat.getText());
            int iCursorStart = Selection.getSelectionStart(edt_input_chat.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        String st = "[em_1]";
                        ((Editable) edt_input_chat.getText()).delete(iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        ((Editable) edt_input_chat.getText()).delete(iCursorEnd - 1, iCursorEnd);
                    }
                } else {
                    ((Editable) edt_input_chat.getText()).delete(iCursorStart, iCursorEnd);
                }
            }
        }
    }

    private boolean isDeletePng(int cursor) {
        String st = "[em_1]";
        String content = edt_input_chat.getText().toString().substring(0, cursor);
        if (content.length() >= st.length()) {
            String checkStr = content.substring(content.length() - st.length(),
                    content.length());
            String regex = "(\\[em_)\\d{1}(\\])";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    private void insert(CharSequence text) {
        int iCursorStart = Selection.getSelectionStart((edt_input_chat.getText()));
        int iCursorEnd = Selection.getSelectionEnd((edt_input_chat.getText()));
        if (iCursorStart != iCursorEnd) {
            ((Editable) edt_input_chat.getText()).replace(iCursorStart, iCursorEnd, "");
        }
        int iCursor = Selection.getSelectionEnd((edt_input_chat.getText()));
        ((Editable) edt_input_chat.getText()).insert(iCursor, text);
    }

    private SpannableStringBuilder getFace(String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            String[] splitText = png.split("\\.");
            String tempText = "[" + splitText[0] + "]";
            Bitmap bitmap = BitmapFactory.decodeStream(getContext().getAssets().open("face/" + png));
            Drawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(0, 0, KeyBoardUtil.dp2px(getContext(), 28), KeyBoardUtil.dp2px(getContext(), 28));
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            sb.append(tempText);
            sb.setSpan(span, sb.length() - tempText.length(), sb.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onStart() {
        if (isvisi) {
            NotificationCenter.getInstance().addObserver(this, RoomSession.MessageReceived);
            NotificationCenter.getInstance().addObserver(this, RoomSession.RemoteMsg);
            NotificationCenter.getInstance().addObserver(this, RoomSession.IceStatusChanged);
            NotificationCenter.getInstance().addObserver(this, RoomSession.VideoUnPublished);
            NotificationCenter.getInstance().addObserver(this, RoomSession.RoomJoined);
            NotificationCenter.getInstance().addObserver(this, RoomSession.UserChanged);
            NotificationCenter.getInstance().addObserver(this, RoomSession.UserJoined);
            NotificationCenter.getInstance().addObserver(this, RoomSession.UserLeft);
            NotificationCenter.getInstance().addObserver(this, RoomSession.VideoPublished);
            NotificationCenter.getInstance().addObserver(this, RoomSession.RoomLeaved);
            NotificationCenter.getInstance().addObserver(this, RoomSession.SelfEvicted);
            NotificationCenter.getInstance().addObserver(this, RoomActivity.SendMessageByKeyEvent);
            NotificationCenter.getInstance().addObserver(this, RoomActivity.ChangeViewLayoutByKeyEvent);
            NotificationCenter.getInstance().addObserver(this, RoomSession.UpdataGiftNum);
            NotificationCenter.getInstance().addObserver(this, RoomSession.RoomBreak);
            NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetVideo);
            NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetAudio);
            NotificationCenter.getInstance().addObserver(this, RoomSession.playBackClearAll);
            NotificationCenter.getInstance().addObserver(this, RoomSession.TakePhotoByCamera);

        }
//        ((RoomActivity)this.getActivity()).joinRoom();
        super.onStart();
        if (isVisiable) {
            doPlayAllVideo();
            doVideoLayout();
        }
        isStart = true;
        if (RoomSession.getInstance().chatList.size() != 0) {
            adapter.notifyDataSetChanged();
            list_chat.setSelection(adapter.getCount());
//            list_chat.setVisibility(View.VISIBLE);
        }
        if (RoomSession.isInRoom) {
            Tools.HideProgressDialog();
        }
        setDisableState();
        txt_gift_num.setText(RoomSession.myGiftNum + "");
        if (wbFragment != null && RoomManager.getInstance().getMySelf() != null) {
            if (isVisiable) {
                RoomActivity.vi_contaioner.setNoScroll(true);
            }
            /*if (RoomSession.isClassBegin) {
//                wbFragment.setDrawable(RoomSession.isMeCandraw);
                RoomActivity.vi_contaioner.setNoScroll(RoomSession.isMeCandraw);
            } else {
//                wbFragment.setDrawable(false);
                RoomActivity.vi_contaioner.setNoScroll(true);
            }*/
        }
        RoomSession.isOpenCamera = false;
    }

    private void initStaticFaces() {
        try {
            staticFacesList = new ArrayList<String>();
            staticFacesList.clear();
            String[] faces = getContext().getAssets().list("face");
            for (int i = 0; i < faces.length; i++) {
                staticFacesList.add(faces[i]);
            }
           /* //去掉删除图片
            staticFacesList.remove("emotion_del_normal.png");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doVideoLayout();
    }

    @Override
    public void onStop() {
        super.onStop();
        NotificationCenter.getInstance().removeObserver(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisiable = isVisibleToUser;
        if (isVisibleToUser) {
            if (isStart) {
                if (RoomSession.isClassBegin) {
                    if (!RoomControler.isReleasedBeforeClass()) {
                        unPlaySelfAfterClassBegin();
                    }
                } else {
                    if (!RoomControler.isReleasedBeforeClass()) {
                        playSelfBeforeClassBegin();
                    } else {
                        doPlayAllVideo();
                    }
                }
                if (RoomSession.isClassBegin) {
                    doPlayAllVideo();
                }
            }
            isvisi = true;
            NotificationCenter.getInstance().addObserver(this, RoomSession.VideoUnPublished);
            NotificationCenter.getInstance().addObserver(this, RoomSession.MessageReceived);
            NotificationCenter.getInstance().addObserver(this, RoomSession.VideoPublished);
            NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetVideo);
            NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetAudio);
            NotificationCenter.getInstance().addObserver(this, RoomSession.UserChanged);
            NotificationCenter.getInstance().addObserver(this, RoomSession.TakePhotoByCamera);
            setDisableState();
            if (RoomManager.getInstance().getMySelf() != null && RoomManager.getInstance().getRoomType() == 0 && RoomManager.getInstance().getMySelf().role == 2 && RoomControler.isAutoHasDraw()) {

            } else {
                if (wbFragment != null) {
                    if (RoomSession.isClassBegin) {
                        wbFragment.setDrawable(RoomSession.isMeCandraw);
                    } else {
                        wbFragment.setDrawable(false);
                    }
                }
            }
            if (txt_gift_num != null) {
                txt_gift_num.setText(RoomSession.myGiftNum + "");
            }
            setUserBackGroud();
        } else {
            isvisi = false;
            doUnPlayAllVideo();
            NotificationCenter.getInstance().removeObserver(this, RoomSession.VideoPublished);
            NotificationCenter.getInstance().removeObserver(this, RoomSession.VideoUnPublished);
            NotificationCenter.getInstance().removeObserver(this, RoomSession.PlayNetVideo);
            NotificationCenter.getInstance().removeObserver(this, RoomSession.PlayNetAudio);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    /***
     * 切换到别的界面的时候，所有视频要停止播放
     */
    public void doUnPlayAllVideo() {
        List<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
        for (int i = 0; i < playingList.size(); i++) {
            RoomUser u = playingList.get(i);
            if (u == null) {
                return;
            }
            if (u.role == 0) {
                RoomManager.getInstance().unPlayVideo(u.peerId);
                if (sf_teacher != null) {
                    sf_teacher.setVisibility(View.GONE);
                }
            }
            if (u.peerId.equals(RoomManager.getInstance().getMySelf().peerId) && u.role == 2) {
                RoomManager.getInstance().unPlayVideo(u.peerId);
                if (sf_teacher != null) {
                    sf_teacher.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setUserBackGroud() {
        List<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
        for (int x = 0; x < playingList.size(); x++) {
            RoomUser roomUser = playingList.get(x);
            if (roomUser != null) {
                boolean isinback = Tools.isTure(roomUser.properties.get("isInBackGround"));
                if (roomUser.role == 0) {
                    if (isinback) {
                        t_re_background.setVisibility(View.VISIBLE);
                    } else {
                        t_re_background.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public void doPlayAllVideo() {
        boolean hasteacher = false;
        boolean hasMe = false;
        List<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
        for (int i = 0; i < playingList.size(); i++) {
            RoomUser u = playingList.get(i);
            if (u == null) {
                return;
            }
            if (u.role == 0) {
                if (u.publishState > 1 && u.publishState < 4) {
                    sf_teacher.setVisibility(View.VISIBLE);
                    RoomManager.getInstance().playVideo(u.peerId, sf_teacher);
                } else {
                    sf_teacher.setVisibility(View.INVISIBLE);
                }
                hasteacher = true;
            } else if (u.peerId.equals(RoomManager.getInstance().getMySelf().peerId) && u.role == 2) {
                if (u.publishState > 1 && u.publishState < 4 && !u.disablevideo) {
                    if (!RoomActivity.isVideo) {
                        sf_my_video.setVisibility(View.VISIBLE);
                        RoomManager.getInstance().playVideo(u.peerId, sf_my_video);
                    } else {
                        sf_my_video.setVisibility(View.INVISIBLE);
                    }
                }
                if (u.publishState == 1 || u.publishState == 4) {
                    sf_my_video.setVisibility(View.INVISIBLE);
                }
                hasMe = true;
            } else if (!u.peerId.equals(RoomManager.getInstance().getMySelf().peerId) && u.role == 2 || RoomManager.getInstance().getMySelf().role == 4) {
                if (RoomManager.getInstance().getRoomType() == 0) {
                    if (u.publishState > 1 && u.publishState < 4 && !u.disablevideo) {
                        if (!RoomActivity.isVideo) {
                            sf_my_video.setVisibility(View.VISIBLE);
                            RoomManager.getInstance().playVideo(u.peerId, sf_my_video);
                        } else {
                            sf_my_video.setVisibility(View.INVISIBLE);
                        }
                    }
                    hasMe = true;
                }
//                else {
//                    hasMe = false;
//                    /*rel_my_video.setVisibility(View.GONE);*/
//                }
            }

            if (!TextUtils.isEmpty(RoomActivity.path)) {

                if (RoomManager.getInstance().getRoomType() == 0) {
                    if (u.role == 0) {
                        hasteacher = true;
                        sf_teacher.setVisibility(View.VISIBLE);
                        RoomManager.getInstance().playVideo(u.peerId, sf_teacher);
                    } else {
                        if (!RoomActivity.isVideo) {
                            hasMe = true;
                            sf_my_video.setVisibility(View.VISIBLE);
                            RoomManager.getInstance().playVideo(u.peerId, sf_my_video);
                        }
                    }
                } else {
                    hasMe = false;
                    rel_my_video.setVisibility(View.GONE);
                    sf_my_video.setVisibility(View.GONE);
                }
            }
            /*if (isClassBegin) {
                if (!hasMe) {
                    sf_my_video.setVisibility(View.INVISIBLE);
                } else {
                    sf_my_video.setVisibility(View.VISIBLE);
                }
            }*/
        }

        if (!hasMe) {
            if (isClassBegin) {
                sf_my_video.setVisibility(View.INVISIBLE);
            }
        }
        if (!hasteacher) {
            sf_teacher.setVisibility(View.INVISIBLE);
        }
        if (RoomSession.isClassBegin) {
            if (RoomManager.getInstance().getMySelf().role == 4 && RoomManager.getInstance().getRoomType() != 0) {
                rel_my_video.setVisibility(View.GONE);
            }
        }
    }

    private void playSelfBeforeClassBegin() {
        if (RoomSession.isClassBegin) {
            return;
        }
        if (sf_my_video != null) {
            if (RoomManager.getInstance().getMySelf() != null) {
                if (!RoomActivity.isVideo && RoomManager.getInstance().getMySelf().role == 2) {
                    sf_my_video.setVisibility(View.VISIBLE);
                    if (RoomControler.isReleasedBeforeClass()) {
                        RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 3);
                    } else {
                        RoomManager.getInstance().playVideo(RoomManager.getInstance().getMySelf().peerId, sf_my_video);
                    }
                } else {
                    sf_my_video.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void unPlaySelfAfterClassBegin() {
        RoomManager.getInstance().unPlayVideo(RoomManager.getInstance().getMySelf().peerId);
        sf_my_video.setVisibility(View.INVISIBLE);
    }

    private void OnRemotePubMsg(String id, String name, long ts, Object data) {
        if (name.equals("ClassBegin")) {
            isClassBegin = true;
            Log.d(TAG, "class begins");
            if (RoomManager.getInstance().getMySelf() != null && RoomManager.getInstance().getRoomType() == 0 && RoomManager.getInstance().getMySelf().role == 2 && RoomControler.isAutoHasDraw()) {
                RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "candraw", true);
            }
            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_normal));
            if (!RoomControler.isReleasedBeforeClass()) {
                unPlaySelfAfterClassBegin();
            }

//            if (RoomManager.getInstance().getRoomType() == 0) {
//                img_hand_up.setVisibility(View.GONE);
//            }
//                boolean haveSit = false;
//                int sitCount = 0;
//                for (RoomUser u:RoomManager.getInstance().getUsers().values()) {
//                    if(u.role==2&&(u.publishState==3||u.publishState==2)){
//                        sitCount++;
//                    }
//                }
//                if(sitCount<=6){
//                    haveSit = true;
//                }
//
//                if (RRoomControler.isAutomaticUp() && haveSit) {
//                    if (isMuteAudio) {
//                        RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 2);
//                    } else {
//                        RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 3);
//                    }
//                }

            if (RoomManager.getInstance().getMySelf().role == 2) {
                iv_dissclass.setVisibility(View.GONE);
            } else {
                if (TextUtils.isEmpty(RoomActivity.path)) {
                    if (RoomSession.isClassBegin) {
                        iv_dissclass.setVisibility(View.VISIBLE);
                    }
                }
            }
            classStartTime = ts;
            RoomManager.getInstance().pubMsg("UpdateTime", "UpdateTime", RoomManager.getInstance().getMySelf().peerId, null, false, null, null);
        } else if (name.equals("UpdateTime")) {
            if (isClassBegin) {
                if (img_clock != null && txt_time != null) {
                    img_clock.setVisibility(View.VISIBLE);
                    txt_time.setVisibility(View.VISIBLE);
                }
                serviceTime = ts;
                localTime = serviceTime - classStartTime;
                if (timerAddTime == null) {
                    timerAddTime = new Timer();
                    timerAddTime.schedule(new AddTime(), 1000, 1000);
                }
            }
        } else if (name.equals("MuteAudio")) {
            isMuteAudio = true;
            int publishState = RoomManager.getInstance().getMySelf().publishState;
            String peerid = RoomManager.getInstance().getMySelf().peerId;
            if (publishState == 3) {
                RoomManager.getInstance().changeUserPublish(peerid, 2);
            }
            if (publishState == 1) {
                RoomManager.getInstance().changeUserPublish(peerid, 0);
            }
        }
    }

    private void OnRemoteDelMsg(String id, String name, long ts, Object data) {
        if (name.equals("ClassBegin")) {
            isClassBegin = false;
            Log.d(TAG, "class ends");

            if (RoomManager.getInstance().getMySelf().role == 4) {
                iv_dissclass.setVisibility(View.GONE);
            }

            if (!RoomControler.isNotLeaveAfterClass()) {
                RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 0);
            }

            if (RoomManager.getInstance().getMySelf().publishState == 0) {
                img_video.setVisibility(View.GONE);
                img_audio.setVisibility(View.GONE);
            }

            localTime = 0;
            if (timerAddTime != null) {
                if (!RoomControler.haveTimeQuitClassroomAfterClass()) {
                    timerAddTime.cancel();
                    timerAddTime = null;
                }
            }
            try {
                if (RoomManager.getInstance().getRoomProperties() != null && !RoomManager.getInstance().getRoomProperties().getString("companyid").equals("10035")) {
                    /*if (!RoomControler.isNotLeaveAfterClass()) {
                        RoomSession.getInstance().setIsExit(true);
                        RoomManager.getInstance().leaveRoom();
                    }*/
                    if (img_clock != null && txt_time != null) {
                        txt_time.setText("00:00:00");
                        img_clock.setVisibility(View.INVISIBLE);
                        txt_time.setVisibility(View.INVISIBLE);
                    }
                    img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                    img_hand_up.setClickable(false);
                    iv_photo.setImageDrawable(getResources().getDrawable(R.drawable.btn_photo_disabled));
                    iv_photo.setClickable(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (name.equals("MuteAudio")) {
            isMuteAudio = false;
            int publishState = RoomManager.getInstance().getMySelf().publishState;
            String peerid = RoomManager.getInstance().getMySelf().peerId;
            if (publishState == 2) {
                RoomManager.getInstance().changeUserPublish(peerid, 3);
            }
            if (publishState == 0) {
                RoomManager.getInstance().changeUserPublish(peerid, 1);
            }
        }
    }

    private void doVideoLayout() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int wid = dm.widthPixels;
        final int hid = dm.heightPixels;
        /*RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) sf_teacher.getLayoutParams();*/
        RelativeLayout.LayoutParams lin_param = (RelativeLayout.LayoutParams) rel_teacher.getLayoutParams();
        RelativeLayout.LayoutParams lin_paramstu = (RelativeLayout.LayoutParams) rel_my_video.getLayoutParams();
       /* param.width = wid / 16 * 3;
        param.height = wid / 16 * 3 / 4 * 3;*/
        lin_param.width = wid / 16 * 3 / 4 * 3;
        lin_param.height = wid / 16 * 3 / 4 * 3 / 4 * 3;
        lin_paramstu.width = wid / 16 * 3 / 4 * 3;
        lin_paramstu.height = wid / 16 * 3 / 4 * 3 / 4 * 3;
//        sf_teacher.setLayoutParams(param);
//        sf_my_video.setLayoutParams(param);
        rel_teacher.setLayoutParams(lin_param);
        lin_paramstu.topMargin = lin_param.height;
        rel_my_video.setLayoutParams(lin_paramstu);
    }

    @Override
    public void onClick(View v) {
        final int vid = v.getId();
        if (vid == R.id.xwalkWebView) {
            if (m_blayoutsShow) {
                hideToolBarPop();
                hideChatExpressionPop();
                chart_face_gv.setVisibility(View.GONE);
                iv_chat.setVisibility(View.VISIBLE);
                iv_broad.setVisibility(View.GONE);
            } else {
                /*if (isClassBegin) {
                    showLayouts();
                    if (RoomActivity.userrole == 2) {
                        lin_input_chat.setVisibility(View.VISIBLE);
                    }
                }*/
               /* if (RoomManager.getInstance().getMySelf() != null) {
                    if ((RoomManager.getInstance().getMySelf().publishState == 2 || RoomManager.getInstance().getMySelf().publishState == 0 || RoomManager.getInstance().getMySelf().publishState == 4) && isClassBegin) {
//                        txt_hand_up.setClickable(true);
                        if (RoomManager.getInstance().getMySelf().disableaudio) {
                            // txt_hand_up.setBackgroundResource(R.drawable.round_back_red_black);
                        } else {
                            // txt_hand_up.setBackgroundResource(R.drawable.round_back_red);
                        }
                    } else {
                        // txt_hand_up.setBackgroundResource(R.drawable.round_back_red_black);
//                        txt_hand_up.setClickable(false);
                       // img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                    }
                    if (RoomManager.getInstance().getMySelf().properties.containsKey("raisehand")) {
                        boolean israisehand = Tools.isTure(RoomManager.getInstance().getMySelf().properties.get("raisehand"));
                        if (israisehand) {
                            //txt_hand_up.setText(R.string.no_raise);
                         //   img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.cacel_hand_normal));
                        } else {
                            //txt_hand_up.setText(R.string.raise); //同意了，或者拒绝了
                          //  img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_pressed));
                        }
                    } else {
                        // txt_hand_up.setText(R.string.raise); //还没举手
                        if (RoomSession.isClassBegin) {
                          //  img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_normal));
                        }
                    }
                }*/
            }
            wbFragment.setWBTouchable(true);

        } else if (vid == R.id.img_back) {
            getActivity().onBackPressed();
        } else if (vid == R.id.img_memmber) {
            hideToolBarPop();
            NotificationCenter.getInstance().postNotificationName(MOVE_PAGE, 1);
        } else if (vid == R.id.hands_up) {
            /*//举手功能
            if (RoomManager.getInstance().getMySelf().publishState != 2 && RoomManager.getInstance().getMySelf().publishState != 0 && RoomManager.getInstance().getMySelf().publishState != 4 || !isClassBegin || RoomManager.getInstance().getMySelf().disableaudio)
                return;
            if (RoomManager.getInstance().getMySelf().properties.containsKey("raisehand")) {
                boolean isup = Tools.isTure(RoomManager.getInstance().getMySelf().properties.get("raisehand"));
                if (isup) {
                    RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", false);
                } else {
                    RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
                }
            } else {
                RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", true);
            }*/
        } else if (vid == R.id.txt_send) {
            String message = edt_input_chat.getText().toString().trim();
            if (!message.isEmpty()) {
                RoomManager.getInstance().sendMessage(message, 0);
                edt_input_chat.setText("");
            }
            hideToolBarPop();
            hideChatExpressionPop();
            chart_face_gv.setVisibility(View.GONE);
            iv_chat.setVisibility(View.VISIBLE);
            iv_broad.setVisibility(View.GONE);
            KeyBoardUtil.hideKeyBoard(getContext(), edt_input_chat);
        } else if (vid == R.id.img_send_message) {
            wbFragment.setWBTouchable(false);
            if (RoomManager.getInstance().getMySelf().role == 2) {
                showChatExpressionPop();
            }
            showLayouts();
            initGridView();
            chart_face_gv.setVisibility(View.GONE);


        } else if (vid == R.id.img_video) {
            RoomUser user = RoomManager.getInstance().getMySelf();
//            RoomManager.getInstance().disableLocalVideo(!user.disablevideo);
            if (user.publishState == 2 || user.publishState == 3) {
                if (user.publishState == 2) {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 4);
                } else {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 1);
                }
            } else {
                if (user.publishState == 4) {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 2);
                } else {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 3);
                }
            }
        } else if (vid == R.id.img_audio) {
            RoomUser user = RoomManager.getInstance().getMySelf();
            if (user.publishState == 1 || user.publishState == 3) {
                if (user.publishState == 1) {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 4);
                } else {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 2);
                }
            } else {
                if (user.publishState == 2) {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 3);
                } else {
                    RoomManager.getInstance().changeUserPublish(user.peerId, 1);
                }
            }
            if (!user.disableaudio) {
                RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "raisehand", false);
            }

        } else if (vid == R.id.iv_dissclass) {
            showClassDissMissDialog();
        } else if (vid == R.id.iv_photo) {
            showPhoto();
        } else if (vid == R.id.iv_chat) {
            chart_face_gv.setVisibility(View.VISIBLE);
            iv_broad.setVisibility(View.VISIBLE);
            iv_chat.setVisibility(View.GONE);
            KeyBoardUtil.hideKeyBoard(getContext(), edt_input_chat);
        } else if (vid == R.id.iv_broad) {
            chart_face_gv.setVisibility(View.GONE);
            iv_broad.setVisibility(View.GONE);
            iv_chat.setVisibility(View.VISIBLE);

           /* KeyBoardUtil.showKeyBoard(getContext(), edt_input_chat);
            edt_input_chat.requestFocus();
            edt_input_chat.setSelection(edt_input_chat.getText().toString().length());
            edt_input_chat.setCursorVisible(true);*/


            edt_input_chat.setFocusable(true);
            edt_input_chat.setFocusableInTouchMode(true);
            edt_input_chat.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) edt_input_chat.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edt_input_chat, 0);

        } else if (vid == R.id.edt_input_chat) {
            chart_face_gv.setVisibility(View.GONE);
            iv_broad.setVisibility(View.GONE);
            iv_chat.setVisibility(View.VISIBLE);

            edt_input_chat.setFocusable(true);
            edt_input_chat.setFocusableInTouchMode(true);
            edt_input_chat.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) edt_input_chat.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edt_input_chat, 0);


            //KeyBoardUtil.showKeyBoard(getContext(), edt_input_chat);
        }
//        else if (vid == R.id.img_server) {
////            showList(4);
//        }
    }

    BasePopupWindow chatWindow;
    PopupWindow ToolBarPop;
    EditText chat;
    private LinearLayout lin_input_chat;

    private EditText edt_input_chat;
    private TextView txt_send;
    private ImageView img_hand_up;
    private List<String> staticFacesList;
    private GridView chart_face_gv;
    private ImageView iv_chat, iv_broad;

    private void initChatExpressionPop() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.chat_input, null);
        chat = (EditText) contentView.findViewById(R.id.edt_input_chat);
        edt_input_chat = (EditText) contentView.findViewById(R.id.edt_input_chat);
        txt_send = (TextView) contentView.findViewById(R.id.txt_send);
        lin_input_chat = (LinearLayout) contentView.findViewById(R.id.lin_input_chat);
        iv_chat = (ImageView) contentView.findViewById(R.id.iv_chat);
        chart_face_gv = (GridView) contentView.findViewById(R.id.chart_face_gv);
        iv_broad = (ImageView) contentView.findViewById(R.id.iv_broad);

        iv_chat.setOnClickListener(this);
        iv_broad.setOnClickListener(this);
        edt_input_chat.setOnClickListener(this);
        txt_send.setOnClickListener(this);

        chatWindow = new BasePopupWindow(getActivity());
        chatWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        chatWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        chatWindow.setContentView(contentView);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        chatWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chatWindow.setFocusable(true);
        chatWindow.setBackgroundDrawable(new BitmapDrawable());
        chatWindow.setOutsideTouchable(true);
        chatWindow.setTouchable(true);
        chatWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideToolBarPop();
            }
        });
    }

    private RelativeLayout tool_bar;
    private ImageView img_back;
    private TextView txt_meeting_name;
    private TextView txt_time;

    private void initToolBarPop() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pop_tool_bar, null);
        tool_bar = (RelativeLayout) contentView.findViewById(R.id.tool_bar);
        img_back = (ImageView) contentView.findViewById(R.id.img_back);
        txt_meeting_name = (TextView) contentView.findViewById(R.id.txt_pad_name);
        txt_time = (TextView) contentView.findViewById(R.id.txt_time);
        img_clock = (ImageView) contentView.findViewById(R.id.img_clock);
        img_back.setOnClickListener(this);

        ToolBarPop = new PopupWindow(getActivity());
        ToolBarPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        ToolBarPop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        ToolBarPop.setContentView(contentView);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        ToolBarPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ToolBarPop.setFocusable(false);
        ToolBarPop.setBackgroundDrawable(new BitmapDrawable());
        ToolBarPop.setOutsideTouchable(false);
        ToolBarPop.setTouchable(true);
        ToolBarPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideChatExpressionPop();
            }
        });
    }

    private void showToolBarPop() {
        ToolBarPop.showAtLocation(fragmentView, Gravity.TOP, 0, 0);
//        KeyBoardUtil.hideKeyBoard(getContext(), chat);
    }

    private void hideToolBarPop() {
        if (ToolBarPop != null) {
            ToolBarPop.dismiss();
        }
    }

    private void showChatExpressionPop() {
        chatWindow.showAtLocation(fragmentView, Gravity.BOTTOM, 0, 0);
//        KeyBoardUtil.hideKeyBoard(getContext(), chat);
    }

    private void hideChatExpressionPop() {
        if (chatWindow != null) {
            chatWindow.dismiss();
        }
    }

    private void showPhoto() {
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        //此处设置位置窗体大小
        alertDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.setCanceledOnTouchOutside(true);
        window.setContentView(R.layout.dialog_photo);
        TextView txt_camera = (TextView) window.findViewById(R.id.txt_camera);
        TextView txt_selectphoto = (TextView) window.findViewById(R.id.txt_selectphoto);
        TextView txt_cacel = (TextView) window.findViewById(R.id.txt_cacel);
        txt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomManager.getInstance().pauseLocalCamera();
               /* openCamera();*/
                RoomSession.isOpenCamera = true;
                RoomActivity.isBackApp = true;
                alertDialog.dismiss();
            }
        });
        txt_selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                RoomActivity.isBackApp = true;
                alertDialog.dismiss();
            }
        });
        txt_cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALBUM_IMAGE);
    }

    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int ALBUM_IMAGE = 2; //相册
    public static File tempFile;
    private Uri imageUri;

    public void openCamera() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (PersonInfo_ImageUtils.hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.e("mxl", "启存储权限没有开启");
                    return;
                }
                imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri;
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                RoomSession.isOpenCamera = false;
                RoomActivity.isBackApp = false;
              /*  RoomManager.getInstance().resumeLocalCamera();*/
                if (resultCode == Activity.RESULT_CANCELED) {
                    Log.e("mxl", "取消了");
                    return;
                }
                if (resultCode == Activity.RESULT_OK) {
                    Log.e("mxl", "拍照OK");
                    if (data != null) {
                        uri = data.getData();
                    } else {
                        uri = imageUri;
                    }
                    if (!TextUtils.isEmpty(uri.toString())) {
                        try {
                            String path = PersonInfo_ImageUtils.scaleAndSaveImage(PersonInfo_ImageUtils.getRealFilePath(getContext(),
                                    PersonInfo_ImageUtils.getFileUri(uri, getContext())), 800, 800, getContext());
                            if (RoomManager.getInstance().getRoomProperties() != null) {
                                WhiteBoradManager.getInstance().uploadRoomFile(
                                        RoomManager.getInstance().getRoomProperties().getString("serial"), path);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case ALBUM_IMAGE:
                RoomActivity.isBackApp = false;
                if (resultCode == Activity.RESULT_OK) {
                    String imagePath = null;
                    try {
                        if (Build.VERSION.SDK_INT >= 19) {
                            imagePath = PersonInfo_ImageUtils.getImageAfterKitKat(data, getContext());
                        } else {
                            imagePath = PersonInfo_ImageUtils.getImageBeforeKitKat(data, getContext());
                        }
                        if (!TextUtils.isEmpty(imagePath)) {
                            String path = PersonInfo_ImageUtils.scaleAndSaveImage(imagePath, 800, 800, getContext());
                            if (RoomManager.getInstance().getRoomProperties() != null) {
                                WhiteBoradManager.getInstance().uploadRoomFile(
                                        RoomManager.getInstance().getRoomProperties().getString("serial"), path);
                            }
                        } else {
                            Toast.makeText(getContext(), "图片选择失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void showClassDissMissDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.remind);
        builder.setMessage(R.string.make_sure_class_dissmiss);
        builder.setPositiveButton(R.string.sure,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RoomManager.getInstance().delMsg("ClassBegin", "ClassBegin", "__all", new HashMap<String, Object>());
                        sendClassDissToPhp();
                    }
                }).setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void sendClassDissToPhp() {
        /*if (!(RoomManager.getInstance().getMySelf().role == 0)) {
            return;
        }*/
        String webFun_controlroom = "http://" + RoomActivity.host + ":" + port + "/ClientAPI" + "/roomover";
        RequestParams params = new RequestParams();
        try {
            params.put("act", 3);
            /*if (RoomControler.isAutoClassDissMiss()) {
                params.put("endsign", 1);
            }*/
            if(RoomManager.getInstance().getRoomProperties()!=null){
                params.put("serial", RoomManager.getInstance().getRoomProperties().get("serial"));
                params.put("companyid", RoomManager.getInstance().getRoomProperties().get("companyid"));
            }
            client.post(webFun_controlroom, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                    try {
                        int nRet = response.getInt("result");
                        if (nRet == 0) {
//                            RoomManager.getInstance().delMsg("ClassBegin", "ClassBegin", "__all", new HashMap<String, Object>());
//                            txt_class_begin.setVisibility(View.GONE);
                        } else {
                            Log.e("demo", "下课接口调用失败，失败数据：");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showLayouts() {
//        m_blayoutsShow = true;
        String roomname = RoomManager.getInstance().getRoomName();
        roomname = StringEscapeUtils.unescapeHtml4(roomname);
        txt_meeting_name.setText(roomname);
//        tool_bar.setVisibility(View.VISIBLE);
        showToolBarPop();
    }

//    private void hideLayouts() {
//        m_blayoutsShow = false;
//        tool_bar.setVisibility(View.INVISIBLE);
////        if (timerhide != null)
////            timerhide.cancel();
//    }

    private void showTime() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String H = "";
                String M = "";
                String S = "";
                long temps = localTime;
                long tempm = temps / 60;
                long temph = tempm / 60;
                long sec = temps - tempm * 60;
                tempm = tempm - temph * 60;
                H = temph == 0 ? "00" : temph >= 10 ? temph + "" : "0" + temph;
                M = tempm == 0 ? "00" : tempm >= 10 ? tempm + "" : "0" + tempm;
                S = sec == 0 ? "00" : sec >= 10 ? sec + "" : "0" + sec;
                txt_time.setText(H + ":" + M + ":" + S);


                try {
                    if (RoomManager.getInstance().getRoomProperties() != null &&
                            serviceTime == RoomManager.getInstance().getRoomProperties().getLong("endtime")) {
                        if (timerAddTime != null) {
                            timerAddTime.cancel();
                            timerAddTime = null;
                        }
                        RoomClient.getInstance().onClassDismiss();
                        RoomSession.getInstance().setIsExit(true);
                        RoomManager.getInstance().leaveRoom();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void showGiftAim() {
        //初始化 Translate动画
        if (!isvisi) {
            return;
        }
        int[] loca = new int[2];
        txt_gift_num.getLocationOnScreen(loca);
        float x = loca[0] / 2 - txt_gift_num.getWidth() * 2 + txt_gift_num.getWidth() / 2;
        float y = loca[1] / 2 + txt_gift_num.getWidth() / 2;
        TranslateAnimation translateAnimation = new TranslateAnimation(-img_gift.getWidth(), x, -img_gift.getHeight(), y);
//        translateAnimation.setFillAfter(true);
//        ScaleAnimation scaleAnimationBig = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f);
//        scaleAnimationBig.setStartOffset(1000);
        ScaleAnimation scaleAnimation = new ScaleAnimation(4.0f, 0.1f, 4.0f, 0.1f);
        scaleAnimation.setFillAfter(true);
        //动画集
        AnimationSet set = new AnimationSet(true);
//        set.setFillAfter(true);
        set.setFillBefore(false);
//        set.addAnimation(scaleAnimationBig);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimation);

        //设置动画时间 (作用到每个动画)
        set.setDuration(3000);
        img_gift.setVisibility(View.VISIBLE);
        img_gift.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                animation.cancel();
                img_gift.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

//    @Override
//    public void onPageFinished() {
//        joinRoom();
//    }


    @Override
    public void didReceivedNotification(final int id, final Object... args) {
        switch (id) {
            case RoomSession.RoomJoined:
                if (RoomManager.getInstance().getMySelf().role == 4) {
                    if (!RoomSession.isClassBegin) {
                        iv_dissclass.setVisibility(View.GONE);
                    } else {
                        iv_dissclass.setVisibility(View.VISIBLE);
                    }
                    txt_gift_num.setVisibility(View.GONE);
                }

                Log.d(TAG, "roomManagerRoomJoined");
                doVideoLayout();
                RoomManager.getInstance().getRoomProperties();
//                if (wbFragment != null) {
//                    wbFragment.idForWhiteBorad(RoomManager.getInstance().getMySelf().role);
//                    wbFragment.sendJoinRoomDataToWB();
////                    wbFragment.localChangeDoc();
//                }
                setDisableState();
                Tools.HideProgressDialog();
                RoomSession.isInRoom = true;
                if (RoomManager.getInstance().getMySelf().role == 2) {
                    img_hand_up.setVisibility(View.VISIBLE);
                    iv_photo.setVisibility(View.VISIBLE);
                    iv_photo.setClickable(false);
                    img_hand_up.setClickable(false);
                    img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                    iv_photo.setImageDrawable(getResources().getDrawable(R.drawable.btn_photo_disabled));
                } else {
                    img_hand_up.setVisibility(View.GONE);
                    iv_photo.setVisibility(View.GONE);
                }
                playSelfBeforeClassBegin();
                if (RoomManager.getInstance().getRoomType() == 0) {
                    img_hand_up.setVisibility(View.GONE);
                }
//                sf_my_video.setVisibility(View.VISIBLE);
//                RoomManager.getInstance().playVideo(RoomManager.getInstance().getMySelf().peerId,sf_my_video);
//                RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, RoomManager.getInstance().getMySelf().publishState);
//                RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "publishstate", RoomManager.getInstance().getMySelf().publishState);
                break;

            case RoomSession.UserJoined:
                RoomUser user = (RoomUser) args[0];
                boolean inList = (boolean) args[1];
                Log.d(TAG, "roomManagerPeerJoined " + user.peerId + (inList ? " inlist" : ""));

//                if (inList) {
//                    if (user.role == 0 && RoomManager.getInstance().getMySelf().role == 0 ||
//                            (RoomManager.getInstance().getRoomType() == 0 && user.role == RoomManager.getInstance().getMySelf().role)) {
//                        RoomManager.getInstance().evictUser(user.peerId);
//                    }
//                }
                break;
            case RoomSession.UserLeft:
                RoomUser user1 = (RoomUser) args[0];
                Log.d(TAG, "roomManagerPeerLeft " + user1.peerId);
//                if(user1.role == 0){
//                    int publishstate = RoomManager.getInstance().getMySelf().publishState;
//                    String peerid = RoomManager.getInstance().getMySelf().peerId;
//                    if(publishstate!=0){
//                        RoomManager.getInstance().changeUserPublish(peerid,0);
//                    }
//                }
                break;
            case RoomSession.UserChanged:
                RoomUser chUser = (RoomUser) args[0];
                if (chUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
                    if (chUser.publishState == 0) {
                        wbFragment.setDrawable(RoomSession.isMeCandraw);
                    }
                }
                if (/*RoomSession.isClassBegin &&*/ isVisiable) {
                    doPlayAllVideo();
                }
                Map<String, Object> changeMap = (Map<String, Object>) args[1];

                if (changeMap.containsKey("isInBackGround")) {
                    boolean isinback = Tools.isTure(changeMap.get("isInBackGround"));
                    if (chUser.role == 2) {
                        if (RoomManager.getInstance().getMySelf().role == 2 &&
                                chUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
                            if (isinback) {
                                sf_re_background.setVisibility(View.VISIBLE);
                            } else {
                                sf_re_background.setVisibility(View.GONE);
                            }
                        }
                    }
                    if (chUser.role == 0) {
                        if (isinback) {
                            t_re_background.setVisibility(View.VISIBLE);
                        } else {
                            t_re_background.setVisibility(View.GONE);
                        }
                    }
                }

                if (chUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
                    if (changeMap.containsKey("giftnumber")) {
                        String fromid = (String) args[2];
                        if (!chUser.peerId.equals(fromid)) {
                            showGiftAim();
                        }
                        txt_gift_num.setText(changeMap.get("giftnumber").toString());
                    }
                    if (changeMap.containsKey("candraw")) {
                        candraw = Tools.isTure(changeMap.get("candraw"));
                        if (candraw) {
                            iv_photo.setImageDrawable(getResources().getDrawable(R.drawable.btn_photo_pressed));
                            iv_photo.setClickable(true);
                        } else {
                            iv_photo.setImageDrawable(getResources().getDrawable(R.drawable.btn_photo_disabled));
                            iv_photo.setClickable(false);
                        }
                        if (RoomSession.isClassBegin) {
                            if (chUser.publishState != 0) {
                                wbFragment.setDrawable(candraw);
                            }
                        } else {
                            wbFragment.setDrawable(false);
                        }
                        if (RoomSession.isClassBegin) {
                            if (candraw) {
                                hideChatExpressionPop();
                                hideToolBarPop();
                            }
                        }
                    }
                    /*if (RoomManager.getInstance().getMySelf().publishState == 2 || RoomManager.getInstance().getMySelf().publishState == 0 || RoomManager.getInstance().getMySelf().publishState == 4) {
                        if (RoomSession.isClassBegin) {
                            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_normal));
                        } else {
                            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                        }
                    } else {
                        //img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                    }*/
                    if (RoomManager.getInstance().getMySelf().properties.containsKey("raisehand")) {
                        boolean israisehand = Tools.isTure(RoomManager.getInstance().getMySelf().properties.get("raisehand"));
                        RoomUser roomUser = RoomManager.getInstance().getMySelf();
                        if (israisehand) {
                            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.cacel_hand_pressed));
                        } else {
                            if (RoomSession.isClassBegin) {
                                img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_normal));
                            } else {
                                img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                            }
                           /* if (RoomManager.getInstance().getMySelf().publishState != 3 && RoomManager.getInstance().getMySelf().publishState != 1 && RoomSession.isClassBegin) {
                                img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_pressed));
                            } else {
                                img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_normal));
                            }*/
                        }
                    } else {
                        if (RoomSession.isClassBegin) {
                            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_up_normal));
                        } else {
                            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
                        }
                    }
                    setDisableState();
                }

                break;
            case RoomSession.VideoPublished:
                RoomUser user2 = (RoomUser) args[0];
                if (isVisiable) {
                    doPlayAllVideo();
                }
//                doVideoLayout();
//                SurfaceViewRenderer remoteView = null;
//                if (user2 != null && user2.role == 0) {
//                    remoteView = sf_teacher;
//                    sf_teacher.setVisibility(View.VISIBLE);
//                } else if (user2.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
//                    remoteView = sf_my_video;
//                    isShowMySelf = true;
//                    sf_my_video.setVisibility(View.VISIBLE);
//                }
//                RoomManager.getInstance().playVideo(user2.peerId, remoteView);
                break;
            case RoomSession.VideoUnPublished:
                RoomUser user3 = (RoomUser) args[0];
                Log.d(TAG, user3.peerId + " unpublished ");
                SurfaceViewRenderer remoteView1 = null;
                if (user3 != null && user3.role == 0) {
                    sf_teacher.setVisibility(View.GONE);
                }
//                else if (user3.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
//                    isShowMySelf = false;
//                }
                doPlayAllVideo();
                break;
            case RoomSession.MessageReceived:
//                RoomUser classRoomUser = (RoomUser) args[0];
//                String s = (String) args[1];
//                Log.d(TAG, "roomManagerMessageReceived " + classRoomUser.peerId);
//                ChatData cd = new ChatData();
//                cd.setPeerid(classRoomUser.peerId);
//                cd.setMessage(s);
//                chatList.add(cd);
                adapter.notifyDataSetChanged();
                list_chat.setSelection(adapter.getCount());
                list_chat.setVisibility(View.VISIBLE);
                list_chat.setTranslationX(0);
                break;
            case RoomSession.IceStatusChanged:
                break;
            case RoomSession.RemoteMsg:
                boolean add = (boolean) args[0];
                String id1 = (String) args[1];
                String name = (String) args[2];
                long ts = (long) args[3];
                Object data = args[4];
                Log.d(TAG, "roomManagerOnRemoteMsg " + add + " " + name + " data: " + data);
                if (add) {
                    OnRemotePubMsg(id1, name, ts, data);
                } else {
                    OnRemoteDelMsg(id1, name, ts, data);
                }
                break;
            case RoomSession.RoomLeaved:
                localTime = 0;
                if (timerAddTime != null) {
                    timerAddTime.cancel();
                    timerAddTime = null;
                }
//                if (checkNetTimer != null) {
//                    checkNetTimer.cancel();
//                    checkNetTimer = null;
//                }
                RoomSession.isInRoom = false;
                break;
            case RoomActivity.ChangeViewLayoutByKeyEvent:
                changeShowLayoutByKeyEvent();
                break;
            case RoomActivity.SendMessageByKeyEvent:
                sendMessageByKeyEvent();
                break;
            case RoomSession.UpdataGiftNum:
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long gifnum = (long) args[0];
                        txt_gift_num.setText(gifnum + "");
                    }
                });
                break;
            case RoomSession.RoomBreak:
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (wbFragment != null) {
                            wbFragment.setDrawable(false);
//                            wbFragment.roomDisConnect();
                        }
                    }
                });
                sf_teacher.setVisibility(View.INVISIBLE);
                sf_my_video.setVisibility(View.INVISIBLE);
               /* Tools.ShowProgressDialog(getActivity(), getResources().getString(R.string.connected));*/
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                break;
            case RoomSession.PlayNetVideo:
                hideToolBarPop();
                hideChatExpressionPop();
                if (wbFragment != null) {
                    wbFragment.closeNewPptVideo();
                }
                break;
            case RoomSession.PlayNetAudio:
                if (wbFragment != null) {
                    wbFragment.closeNewPptVideo();
                }
                break;
            case RoomSession.playBackClearAll:
//                if (wbFragment != null) {
//                    wbFragment.roomPlaybackClearAll();
//                }
                if (RoomSession.getInstance().chatList != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
            case RoomSession.TakePhotoByCamera:
                if (RoomSession.isOpenCamera) {
                    openCamera();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (RoomManager.getInstance().getMySelf() != null) {
            if (RoomSession.isMeCandraw && RoomSession.isClassBegin) {
                iv_photo.setClickable(true);
                iv_photo.setImageDrawable(getResources().getDrawable(R.drawable.btn_photo_pressed));
            } else {
                iv_photo.setClickable(false);
                iv_photo.setImageDrawable(getResources().getDrawable(R.drawable.btn_photo_disabled));
            }
        }
        if (getUserVisibleHint() && !RoomSession.isClassBegin) {
            try {
                if (RoomManager.getInstance().getRoomProperties() != null && RoomManager.getInstance().getRoomProperties().getString("chairmancontrol").length() > 41) {
                    if (!RoomControler.isReleasedBeforeClass()) {
                        playSelfBeforeClassBegin();
                    }
                } else {
                    playSelfBeforeClassBegin();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!RoomSession.isClassBegin) {
            if (img_clock != null && txt_time != null) {
                txt_time.setText("00:00:00");
                img_clock.setVisibility(View.INVISIBLE);
                txt_time.setVisibility(View.INVISIBLE);
            }
            img_hand_up.setImageDrawable(getResources().getDrawable(R.drawable.hand_disabled));
            img_hand_up.setClickable(false);
        }
    }

    private void setDisableState() {
        if (RoomManager.getInstance().getMySelf() == null || img_audio == null || img_video == null) {
            return;
        }
        if (RoomManager.getInstance().getMySelf().publishState == 1 || RoomManager.getInstance().getMySelf().publishState == 3) {
            img_audio.setImageResource(R.drawable.audio_off);
        } else {
            img_audio.setImageResource(R.drawable.audio_on);
        }
        if (RoomManager.getInstance().getMySelf().publishState >= 2 && RoomManager.getInstance().getMySelf().publishState != 4) {
            img_video.setImageResource(R.drawable.video_off);
        } else {
            img_video.setImageResource(R.drawable.video_on);
        }
        if (RoomManager.getInstance().getMySelf().publishState == 0 || !RoomControler.isAllowStudentControlAV()) {
            img_audio.setVisibility(View.GONE);
            img_video.setVisibility(View.GONE);
        } else {
            if (RoomActivity.userrole == 2) {
                img_audio.setVisibility(View.VISIBLE);
                img_video.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeShowLayoutByKeyEvent() {
        if (lin_input_chat == null || tool_bar == null) {
            return;
        }
        if (m_blayoutsShow) {
            hideToolBarPop();
            hideChatExpressionPop();
        } else {
            showLayouts();
            if (RoomActivity.userrole == 2) {
                showChatExpressionPop();
            }
        }
        edt_input_chat.requestFocus();
    }

    private void sendMessageByKeyEvent() {
        String message = edt_input_chat.getText().toString().trim();
        if (!message.isEmpty()) {
            RoomManager.getInstance().sendMessage(message, 0);
            edt_input_chat.setText("");
        }
        hideToolBarPop();
        hideChatExpressionPop();
    }

    @Override
    public void onDestroyView() {
        if (sf_teacher != null)
            sf_teacher.release();
        if (sf_my_video != null)
            sf_my_video.release();
        localTime = 0;
        if (timerAddTime != null) {
            timerAddTime.cancel();
            timerAddTime = null;
        }
        if (chatWindow != null) {
            chatWindow.dismiss();
        }
        Log.e("xiao", "room_onDestroyView");

        super.onDestroyView();
    }

    class whenHide extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (m_blayoutsShow)
                        hideToolBarPop();
                }
            });
        }
    }

    class AddTime extends TimerTask {

        @Override
        public void run() {
            serviceTime += 1;
            localTime = serviceTime - classStartTime;
            showTime();
        }
    }

//    private void showList(int type) {
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        final int wid = dm.widthPixels;
//        final int hid = dm.heightPixels;
//        View contentView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.list_pop, null);
////        contentView.setBackgroundColor(Color.BLUE);
//        ListView list = (ListView) contentView.findViewById(R.id.list);
//        TextView txt_topic = (TextView) contentView.findViewById(R.id.topic);
//        TextView txt_sure = (TextView) contentView.findViewById(R.id.txt_sure);
//
//
//        final PopupWindow popupWindow = new PopupWindow(fragmentView.findViewById(R.id.mainLayout), wid / 3, hid);
//        popupWindow.setContentView(contentView);
//        if (type == 4) {
//            txt_topic.setText(getResources().getText(R.string.select_country));
//            list.setAdapter(serverListAdapter);
//            serverListAdapter.notifyDataSetChanged();
//            txt_sure.setVisibility(View.VISIBLE);
//            txt_sure.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RoomManager.getInstance().switchService(serverListAdapter.getSelectServerName());
//                    popupWindow.dismiss();
//                }
//            });
//        }
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(fragmentView.findViewById(R.id.mainLayout), Gravity.LEFT, 0, 0);
//    }

}

