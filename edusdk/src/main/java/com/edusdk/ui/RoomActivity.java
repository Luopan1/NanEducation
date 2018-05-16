package com.edusdk.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.classroomsdk.Config;
import com.classroomsdk.WBStateCallBack;
import com.classroomsdk.WhiteBoradManager;
import com.edusdk.R;
import com.edusdk.adapter.RoomPageAdapter;
import com.edusdk.message.BroadcastReceiverMgr;
import com.edusdk.message.JSVideoWhitePadInterface;
import com.edusdk.message.NotificationCenter;
import com.edusdk.message.RoomClient;
import com.edusdk.message.RoomSession;
import com.edusdk.message.VideoWBCallback;
import com.edusdk.tools.PermissionTest;
import com.edusdk.tools.SoundPlayUtils;
import com.edusdk.tools.Tools;
import com.edusdk.view.NoScrollViewPager;
import com.talkcloud.roomsdk.IRoomVideoWhiteBoard;
import com.talkcloud.roomsdk.RoomControler;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;
import com.talkcloud.roomsdk.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.EglBase;
import org.webrtc.EglRenderer;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RoomActivity extends FragmentActivity implements WBStateCallBack, NotificationCenter.NotificationCenterDelegate, VideoWBCallback, IRoomVideoWhiteBoard {

    public static final int ChangeViewLayoutByKeyEvent = 2001;
    public static final int SendMessageByKeyEvent = 2002;
    public static final int SHOW_LAYOUT = 2003;
    public static final int GONE_VIDEO = 3000;

    public static NoScrollViewPager vi_contaioner;
    RoomPageAdapter adapter;
    private SurfaceViewRenderer sur_player_view;
    private XWalkView videoXWalkView, movieXWalkView;
    private RelativeLayout sur_container;
    private ImageView img_disk;
    //回放使用的控件
    private RelativeLayout rel_play_back;
    private ImageView img_play_back;
    private SeekBar sek_play_back;
    private TextView txt_play_back_time;
    private RelativeLayout tool_bar;
    private ImageView img_back;
    //    private ChatListAdapter adapter;
    //    private DisListView list_chat;
    private boolean isExitRoom = false;
    //ExData
    public static String host = "";
    private int port = 80;
    private String nickname = "";
    private String userid = "";
    private String serial = "";
    private String password = "";
    public static int userrole = -1;
    public static String path = "";
    private int type = -1;
    private String servername;

    private String param = "";
    private String domain = "";
    boolean candraw = false;
    private final int REQUEST_CODED = 3;
    Animation operatingAnim;
    Timer t;

    PowerManager pm;
    PowerManager.WakeLock mWakeLock;
    private String mobilename = "";
    private boolean mobilenameNotOnList = true;
    long starttime;
    long endtime;
    long currenttime;
    boolean isPlayPlayback = true;
    boolean isEnd = false;

    boolean isShowToolBar = false;

    private int lastX, lastY;
    private int cenX, cenY;
    private PointF mid = new PointF();// 两指中点
    static final int NONE = 0;
    static final int DRAG = 1; // 拖动中
    static final int ZOOM = 2; // 缩放中
    int mode = NONE; // 当前的事件
    private static final float MAX_SCALE = 4.0f;
    private static final float MIN_SCALE = 0.5f;
    private float beforeLenght; // 两触点距离
    private float afterLenght; // 两触点距离
    private static final float sfRation = 0.01f;
    private int screenWidth, screenHeight;
    private SurfaceViewRenderer sur_screen;
    public static boolean isVideo = false;
    NotificationManager nm = null;
    NotificationCompat.Builder mBuilder = null;
    public static boolean isBackApp = false;

    private EglRenderer.FrameListener frameListener;
    private RelativeLayout re_loading;
    private ImageView loadingImageView;
    private AnimationDrawable animationDrawable;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_room);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //        Intent startIntent = new Intent(this, LogService.class);
        //        startService(startIntent);

        resultActivityBackApp();

        RoomSession.mediaPublishStream = null;
        RoomManager.getInstance().globalInitialize(getApplicationContext());
        getExData();
        vi_contaioner = (NoScrollViewPager) findViewById(R.id.container);

        sur_container = (RelativeLayout) findViewById(R.id.sur_container);
        img_disk = (ImageView) findViewById(R.id.img_disk);
        img_disk.clearAnimation();
        img_disk.setVisibility(View.GONE);

        rel_play_back = (RelativeLayout) findViewById(R.id.rel_play_back);
        img_play_back = (ImageView) findViewById(R.id.img_play_back);
        sek_play_back = (SeekBar) findViewById(R.id.sek_play_back);
        txt_play_back_time = (TextView) findViewById(R.id.txt_play_back_time);
        tool_bar = (RelativeLayout) findViewById(R.id.tool_bar);
        img_back = (ImageView) findViewById(R.id.img_back);

        re_loading = (RelativeLayout) findViewById(R.id.re_loading);
        loadingImageView = (ImageView) findViewById(R.id.loadingImageView);

        WhiteBoradManager.getInstance().setWBCallBack(this);
        WhiteBoradManager.getInstance().setFileServierUrl(host);
        WhiteBoradManager.getInstance().setFileServierPort(port);
        JSVideoWhitePadInterface.getInstance().setVideoWBCallBack(this);
        RoomManager.getInstance().setVideoWhiteBoard(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new RoomPageAdapter(fragmentManager);
        vi_contaioner.setAdapter(adapter);
        vi_contaioner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    vi_contaioner.setNoScroll(true);
                } else if (position == 1) {
                    vi_contaioner.setNoScroll(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2 && !vi_contaioner.getScroll()) {
                    if (vi_contaioner.getCurrentItem() == 0) {
                        NotificationCenter.getInstance().postNotificationName(GONE_VIDEO, 1);
                    }
                }
                /*if (state == 0 && !vi_contaioner.getScroll()) {
                    if (vi_contaioner.getCurrentItem() == 1) {

                    }
                }*/
            }
        });

        initPlayBackView();
        operatingAnim = AnimationUtils.loadAnimation(RoomActivity.this, R.anim.disk_aim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
        sur_player_view = (SurfaceViewRenderer) findViewById(R.id.sur_player_view);

        videoXWalkView = (XWalkView) findViewById(R.id.video_white_board);
        movieXWalkView = (XWalkView) findViewById(R.id.movie_white_board);
        setXWalkView();

        DisplayMetrics display = new DisplayMetrics();
        display = this.getResources().getDisplayMetrics();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        screenWidth = display.widthPixels + frame.top;
        screenHeight = display.heightPixels;
        sur_screen = (SurfaceViewRenderer) findViewById(R.id.sur_screen);

        //sur_container.layout(-sur_container.getWidth() / 2, -sur_container.getHeight() / 2, sur_container.getWidth() * 3 / 2, sur_container.getHeight() * 3 / 2);
        sur_screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        mode = DRAG;
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        // 两根手指时计算距离
                        if (event.getPointerCount() == 2) {
                            beforeLenght = spacing(event);
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (spacing(event) > 5.0f) {
                            mode = ZOOM;
                            mid = getMid(event);
                            cenX = (int) mid.x + sur_player_view.getLeft();
                            cenY = (int) mid.y + sur_player_view.getTop();
                            beforeLenght = spacing(event);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == ZOOM) {
                            if (spacing(event) > 10f) {
                                afterLenght = spacing(event);
                                // 计算前后距离差
                                float gralengh = afterLenght - beforeLenght;
                                if (gralengh == 0) {
                                    break;
                                } else if (Math.abs(gralengh) > 5.0f) {
                                  /*  float scale = afterLenght / beforeLenght;
                                    if (scale > 1) {
                                        scale = 1 + scale * 0.1f;
                                    } else {
                                        scale = 1 - scale * 0.1f;
                                    }
                                    suf_mp4Scale(scale);*/
                                    if (gralengh > 0) {
                                        if (sur_screen != null) {
                                            ZoomOut(sur_screen, cenX, cenY);
                                        }
                                    } else {
                                        if (sur_screen != null) {
                                            ZoomIn(sur_screen, cenX, cenY);
                                        }
                                    }
                                    beforeLenght = afterLenght;
                                }
                            }
                        } else if (mode == DRAG) { // 处理拖动
                            drag(event, sur_screen, lastX, lastY);
                            // 将拖动后手指最后停留位置的坐标赋值给lastX、lastY
                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (sur_screen.getWidth() < screenHeight || sur_screen.getHeight() < screenWidth) {
                            sur_screen.setLeft(0);
                            sur_screen.setTop(0);
                            sur_screen.setRight(screenHeight + 96);
                            sur_screen.setBottom(screenWidth);
                        } else {
                            if (sur_screen.getLeft() > 0) {
                                sur_screen.setLeft(0);
                            }
                            if (sur_screen.getTop() > 0) {
                                sur_screen.setTop(0);
                            }
                            if (sur_screen.getBottom() < screenWidth) {
                                sur_screen.setBottom(screenWidth);
                            }
                            if (sur_screen.getRight() < screenHeight) {
                                sur_screen.setRight(screenHeight);
                            }
                        }
                        sur_screen.invalidate();
                        // 手指抬起时重置状态
                        mode = NONE;
                        break;
                }
                return true;
            }
        });
    }

    private void setXWalkView() {
        XWalkPreferences.setValue("enable-javascript", true);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        XWalkPreferences.setValue(XWalkPreferences.ALLOW_UNIVERSAL_ACCESS_FROM_FILE, true);

        XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, true);
        XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, true);

        XWalkSettings webs = videoXWalkView.getSettings();
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

        videoXWalkView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        videoXWalkView.setHorizontalScrollBarEnabled(false);
        JSVideoWhitePadInterface.getInstance().setVideoWBCallBack(this);
        videoXWalkView.addJavascriptInterface(JSVideoWhitePadInterface.getInstance(), "JSVideoWhitePadInterface");
        videoXWalkView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        videoXWalkView.setBackgroundColor(0);

        videoXWalkView.setUIClient(new XWalkUIClient(videoXWalkView) {
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

        XWalkSettings movieWebs = movieXWalkView.getSettings();
        movieWebs.setJavaScriptEnabled(true);
        movieWebs.setCacheMode(WebSettings.LOAD_DEFAULT);
        movieWebs.setDomStorageEnabled(true);
        movieWebs.setDatabaseEnabled(true);
        movieWebs.setAllowFileAccess(true);
        movieWebs.setSupportZoom(false);
        movieWebs.setBuiltInZoomControls(false);

        movieWebs.setLoadWithOverviewMode(false);
        movieWebs.setJavaScriptCanOpenWindowsAutomatically(true);
        movieWebs.setLoadWithOverviewMode(true);
        movieWebs.setDomStorageEnabled(true);
        movieWebs.setUseWideViewPort(true);
        movieWebs.setMediaPlaybackRequiresUserGesture(false);
        movieWebs.setSupportSpatialNavigation(true);
        movieWebs.setAllowFileAccessFromFileURLs(true);

        movieWebs.setLayoutAlgorithm(XWalkSettings.LayoutAlgorithm.NORMAL);
        movieWebs.setUseWideViewPort(true);

        movieXWalkView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        movieXWalkView.setHorizontalScrollBarEnabled(false);
        JSVideoWhitePadInterface.getInstance().setVideoWBCallBack(this);
        movieXWalkView.addJavascriptInterface(JSVideoWhitePadInterface.getInstance(), "JSVideoWhitePadInterface");
        movieXWalkView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        movieXWalkView.setBackgroundColor(0);

        movieXWalkView.setUIClient(new XWalkUIClient(movieXWalkView) {
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
    }

    //点击通知进入一个Activity，点击返回时进入指定页面。
    public void resultActivityBackApp() {
    /*    nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setTicker(getString(R.string.app_name));
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(getString(R.string.app_name));
        mBuilder.setContentText(getString(R.string.back_hint));

        //设置点击一次后消失（如果没有点击事件，则该方法无效。）
        mBuilder.setAutoCancel(true);

        //点击通知之后需要跳转的页面
        Intent resultIntent = new Intent(this, RoomActivity.class);

        //使用TaskStackBuilder为“通知页面”设置返回关系
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //为点击通知后打开的页面设定 返回 页面。（在manifest中指定）
//        stackBuilder.addParentStack(RoomActivity.class);
//        stackBuilder.addNextIntent(resultIntent);

        PendingIntent pIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
        // mId allows you to update the notification later on.
//        nm.notify(2, mBuilder.build());*/
    }

    private void requestRoomPermissions() {
        if (!PermissionTest.cameraIsCanUse()) {
            isVideo = true;
            Tools.ShowAlertDialog(this, getString(R.string.camera_hint));
        } else {
            isVideo = false;
        }
        if (PermissionTest.getRecordState() == -2) {
            Tools.ShowAlertDialog(this, getString(R.string.mic_hint));
        }
    }

    private void suf_mp4Scale(float scale) {
        if (scale > 2.5f) {
            scale = 2.5f;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) sur_player_view.getLayoutParams();
        layoutParams.height = (int) (sur_player_view.getHeight() * scale);
        layoutParams.width = (int) (sur_player_view.getWidth() * scale);
        if (layoutParams.height > getWindowManager().getDefaultDisplay().getWidth() * 3 ||
                layoutParams.width > getWindowManager().getDefaultDisplay().getHeight() * 3) {
            return;
        }
        sur_player_view.setLayoutParams(layoutParams);
        sur_player_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sur_container.getLayoutParams();
        sur_container.setLayoutParams(lp);
        //sur_player_view.invalidate();
    }

    private void drag(MotionEvent event, SurfaceViewRenderer surfaceView, int lastX,
                      int lastY) {
        if (surfaceView.getWidth() == screenHeight + 96
                && surfaceView.getHeight() == screenWidth) {
            return;
        }
        // 拖动偏移量
        int dx = (int) event.getRawX() - lastX;
        int dy = (int) event.getRawY() - lastY;
        // 将拖动的偏移量与view左、上、右、下进行位移处理
        int left = surfaceView.getLeft() + dx;
        int top = surfaceView.getTop() + dy;
        int right = surfaceView.getRight() + dx;
        int bottom = surfaceView.getBottom() + dy;
        // 处理拖拽不能超出边界
        if (left > 0) {
            left = 0;
            right = left + surfaceView.getWidth();
        }
        if (right < screenHeight + 96) {
            right = screenHeight + 96;
            left = right - surfaceView.getWidth();
        }
        if (top > 0) {
            top = 0;
            bottom = top + surfaceView.getHeight();
        }
        if (bottom < screenWidth) {
            bottom = screenWidth;
            top = bottom - surfaceView.getHeight();
        }
        surfaceView.layout(left, top, right, bottom);
    }

    private void ZoomIn(SurfaceView surfaceView, int cenX, int cenY) {
        // 设置缩小到最小后停止缩小将湖面设置为初始大小
        if (surfaceView.getWidth() <= screenHeight * MIN_SCALE
                || surfaceView.getHeight() <= screenWidth * MIN_SCALE) {
          /*  int l = screenWidth / 2;
            int t = screenHeight / 2;
            int r = (l + screenWidth);
            int b = (t + screenHeight);
            surfaceView.invalidate(l, t, r, b);
           */
            return;
        } else {
            int smallLeft = surfaceView.getLeft()
                    + (int) ((cenX - surfaceView.getLeft()) * (sfRation));
            int smallTop = surfaceView.getTop()
                    + (int) ((cenY - surfaceView.getTop()) * (sfRation));
            int smallRight = surfaceView.getRight()
                    - (int) ((surfaceView.getRight() - cenX) * (sfRation));
            int smallBottom = surfaceView.getBottom()
                    - (int) ((surfaceView.getBottom() - cenY) * (sfRation));
            setSurfaceView(surfaceView, smallLeft, smallTop, smallRight,
                    smallBottom);
        }
    }

    private void setSurfaceView(SurfaceView surfaceView, int l, int t, int r, int b) {
        surfaceView.setLeft(l);
        surfaceView.setTop(t);
        surfaceView.setRight(r);
        surfaceView.setBottom(b);
        surfaceView.invalidate(l, t, r, b);
        //surfaceView.invalidate();
    }

    private float spacing(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }
        return 0.0f;
    }

    private PointF getMid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

    private void ZoomOut(SurfaceViewRenderer surfaceView, int cenX, int cenY) {
        if (surfaceView.getWidth() >= screenWidth * MAX_SCALE
                || surfaceView.getHeight() >= screenHeight * MAX_SCALE) {
            return;
        } else {
            // 放大
            // 设置保持视频分辨率
            // 手指中心点的坐标与surfaceView的左、上、右、下进行位移放大处理
            int bigLeft = surfaceView.getLeft()
                    - (int) ((cenX - surfaceView.getLeft()) * (sfRation)) - 25;
            int bigTop = surfaceView.getTop()
                    - (int) ((cenY - surfaceView.getTop()) * (sfRation)) - 15;
            int bigRight = surfaceView.getRight()
                    + (int) ((surfaceView.getRight() - cenX) * (sfRation)) + 30;
            int bigBottom = surfaceView.getBottom()
                    + (int) ((surfaceView.getBottom() - cenY) * (sfRation)) + 15;
            setSurfaceView(surfaceView, bigLeft, bigTop, bigRight, bigBottom);
        }
    }

    private void initPlayBackView() {
        rel_play_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (isShowToolBar) {
                        tool_bar.setVisibility(View.INVISIBLE);
                    } else {
                        tool_bar.setVisibility(View.VISIBLE);
                    }
                    isShowToolBar = !isShowToolBar;
                }
                return true;
            }
        });
        sek_play_back.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    this.progress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /*RoomSession.playBackTime = 0;*/
                long pos = (long) (((double) progress / 100) * (endtime - starttime) + starttime);
                if (isEnd) {
                    img_play_back.setImageResource(R.drawable.btn_pause_normal);
                    RoomManager.getInstance().playPlayback();
                    isPlayPlayback = !isPlayPlayback;
                }
                isEnd = false;
                RoomManager.getInstance().seekPlayback(pos);
            }
        });
        img_play_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlayPlayback) {
                    RoomManager.getInstance().pausePlayback();
                    img_play_back.setImageResource(R.drawable.btn_play_normal);
                } else {
                    if (isEnd) {
                        RoomManager.getInstance().seekPlayback(starttime);
                        currenttime = starttime;
                        RoomManager.getInstance().playPlayback();
                        img_play_back.setImageResource(R.drawable.btn_pause_normal);
                        isEnd = false;
                    } else {
                        RoomManager.getInstance().playPlayback();
                        img_play_back.setImageResource(R.drawable.btn_pause_normal);
                    }
                }
                isPlayPlayback = !isPlayPlayback;
                WhiteBoradManager.getInstance().playbackPlayAndPauseController(isPlayPlayback);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        RoomSession.getInstance().setActivity(this);
        NotificationCenter.getInstance().addObserver(this, RoomSession.MessageReceived);
        NotificationCenter.getInstance().addObserver(this, RoomSession.RoomLeaved);
        NotificationCenter.getInstance().addObserver(this, RoomSession.RoomJoined);
        NotificationCenter.getInstance().addObserver(this, RoomFragment.MOVE_PAGE);
        NotificationCenter.getInstance().addObserver(this, RoomSession.UserChanged);
        NotificationCenter.getInstance().addObserver(this, RoomSession.SelfEvicted);
        NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetVideo);
        NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetAudio);
        NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetSeek);
        NotificationCenter.getInstance().addObserver(this, RoomSession.PlayNetStop);
        NotificationCenter.getInstance().addObserver(this, RoomSession.RoomBreak);
        NotificationCenter.getInstance().addObserver(this, RoomSession.ShowVideoView);
        NotificationCenter.getInstance().addObserver(this, RoomSession.MediaControl);
        NotificationCenter.getInstance().addObserver(this, RoomSession.playBackUpdateTime);
        NotificationCenter.getInstance().addObserver(this, RoomSession.playBackDuration);
        NotificationCenter.getInstance().addObserver(this, RoomSession.playBackEnd);
        NotificationCenter.getInstance().addObserver(this, RoomSession.ShareScreen);
        NotificationCenter.getInstance().addObserver(this, RoomSession.CloseShareScreen);
        NotificationCenter.getInstance().addObserver(this, RoomSession.RemoteMsg);
        NotificationCenter.getInstance().addObserver(this, RoomSession.PlayMovie);
        NotificationCenter.getInstance().addObserver(this, RoomSession.UnPlayMovie);

        super.onStart();
        mWakeLock.acquire();
        //        if(RoomSession.getInstance().chatList.size()!=0){
        //            adapter.notifyDataSetChanged();
        //            list_chat.setSelection(adapter.getCount());
        //            list_chat.setVisibility(View.VISIBLE);
        //        }

        //        checkMedia();
        if (RoomManager.getInstance().getMySelf() != null) {
            if (nm != null) {
                nm.cancel(2);
            }
            isBackApp = false;
            if (RoomManager.getInstance().getMySelf().role == 2) {
                RoomManager.getInstance().setInBackGround(false);
                RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "isInBackGround", false);
            }
        }
        if (RoomManager.getInstance().getMySelf() == null) {
            return;
        }
        if (RoomManager.getInstance().getMySelf().publishState == 1 || RoomManager.getInstance().getMySelf().publishState == 3) {
            RoomManager.getInstance().enableSendMyVoice(true);
        }
        //        RoomManager.getInstance().resumeLocalCamera();
        RoomManager.getInstance().enableOtherAudio(false);
        RoomManager.getInstance().setMuteAllStream(false);
      /*  openSpeaker();
        RoomManager.getInstance().useLoudSpeaker(true);*/
        closeSpeaker();
        if (RoomManager.getInstance() != null) {
            if (RoomManager.getInstance().getRoomStatus() > 0 && RoomManager.getInstance().getRoomStatus() < 6) {
                RoomManager.getInstance().pubMsg("UpdateTime", "UpdateTime", "__all", null, false, null, null);
            }
        }

        if (videoXWalkView != null) {
            videoXWalkView.onShow();
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            final int wid = dm.widthPixels;
            final int hid = dm.heightPixels;
            transmitWindowSize(wid, hid);
        }

        if (movieXWalkView != null) {
            movieXWalkView.onShow();
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            final int wid = dm.widthPixels;
            final int hid = dm.heightPixels;
            transmitWindowSize(wid, hid);
        }
    }

    /**
     * 打开扬声器
     */
    private void openSpeaker() {
        try {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.ROUTE_SPEAKER);
            int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            if (!audioManager.isSpeakerphoneOn()) {
                //setSpeakerphoneOn() only work when audio mode set to MODE_IN_CALL.
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
                        AudioManager.STREAM_VOICE_CALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        /*RoomSession.playBackTime = 0;*/

        NotificationCenter.getInstance().removeObserver(this);
        super.onStop();
        //        VideoPlayer.getInstance().pause();
        //        Intent intent = new Intent(this, LogService.class);
        //        stopService(intent);

        mWakeLock.release();
        if (!isFinishing()) {
            //            RoomManager.getInstance().pauseLocalCamera();
            //            RoomManager.getInstance().enableSendMyVoice(false);
            //            RoomManager.getInstance().enableOtherAudio(true);
            //            RoomManager.getInstance().setMuteAllStream(true);
            if (!isBackApp) {
                if (nm!=null){
                    nm.notify(2, mBuilder.build());
                }
            }
            if (RoomManager.getInstance().getMySelf() != null) {
                if (RoomManager.getInstance().getMySelf().role == 2) {
                    RoomManager.getInstance().setInBackGround(true);
                    RoomManager.getInstance().changeUserProperty(RoomManager.getInstance().getMySelf().peerId, "__all", "isInBackGround", true);
                }
            }
        }
        img_disk.clearAnimation();
        img_disk.setVisibility(View.GONE);
        // makeAppBackToHome(this);
    }

    public static void makeAppBackToHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (RoomSession.mediaunPublishStream == null) {
            if (RoomSession.updateAttributeStream == null) {
                img_disk.clearAnimation();
                img_disk.setVisibility(View.GONE);
                if (RoomSession.mediaPublishStream != null) {
                    if (RoomSession.mediaPublishStream.isVideo()) {
                        NotificationCenter.getInstance().postNotificationName(RoomSession.PlayNetVideo, RoomSession.mediaPublishStream);
                    } else {
                        if (RoomSession.mediaPublishStream.isAudio()) {
                            img_disk.setVisibility(View.VISIBLE);
                            img_disk.startAnimation(operatingAnim);
                            NotificationCenter.getInstance().postNotificationName(RoomSession.PlayNetAudio, RoomSession.mediaPublishStream);
                        }
                    }
                }
            } else {
                NotificationCenter.getInstance().postNotificationName(RoomSession.MediaControl, RoomSession.updateAttributeStream,
                        RoomSession.AttributeStream);
            }
        } else {
            img_disk.clearAnimation();
            img_disk.setVisibility(View.GONE);
            NotificationCenter.getInstance().postNotificationName(RoomSession.PlayNetStop, RoomSession.mediaunPublishStream);
            RoomSession.mediaunPublishStream = null;
        }
        closeSpeaker();
        if (RoomSession.getInstance().isBreak()) {
            NotificationCenter.getInstance().postNotificationName(RoomSession.RoomBreak);
        } else {
            Tools.HideProgressDialog();
        }
        if (RoomSession.isRoomLeaved) {
            NotificationCenter.getInstance().postNotificationName(RoomSession.RoomLeaved);
        }
    }

    public void closeSpeaker() {
        try {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                if (audioManager.isWiredHeadsetOn()) {
                    RoomManager.getInstance().useLoudSpeaker(false);
                } else {
                    RoomManager.getInstance().useLoudSpeaker(true);
                    openSpeaker();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getExData() {
        Bundle bundle = getIntent().getExtras();

        host = bundle.getString("host");
        port = bundle.getInt("port");
        userrole = bundle.getInt("userrole");
        nickname = bundle.getString("nickname");

        if (bundle.containsKey("param")) {
            param = bundle.getString("param");
        } else {
            serial = bundle.getString("serial");
            password = bundle.getString("password");
            userid = bundle.getString("userid");
        }
        domain = bundle.getString("domain");
        if (bundle.containsKey("path")) {
            path = bundle.getString("path");
        }
        if (bundle.containsKey("type")) {
            type = bundle.getInt("type");
        }
        if (bundle.containsKey("servername")) {
            servername = bundle.getString("servername");
        }
        try {
            String brand = Build.MODEL;
            mobilename = bundle.getString("mobilename");
            if (mobilename != null && !mobilename.isEmpty()) {
                JSONArray mNames = new JSONArray(mobilename);
                for (int i = 0; i < mNames.length(); i++) {
                    if (brand.toLowerCase().equals(mNames.optString(i).toLowerCase())) {
                        mobilenameNotOnList = false;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        showExitDialog();
    }

    public void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.remind);
        builder.setMessage(R.string.logouts);
        builder.setPositiveButton(R.string.sure,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isExitRoom = true;
                        RoomSession.getInstance().setIsExit(isExitRoom);
                        if (RoomSession.getInstance().isBreak()) {
                            RoomSession.getInstance().setIsBreak(false);
                        }
                        RoomManager.getInstance().leaveRoom();
                        if (RoomManager.getInstance() != null) {
                            if (RoomManager.getInstance().getRoomStatus() == 3) {
                                RoomManager.getInstance().leaveRoom();
                                finish();
                            }
                            if (RoomManager.getInstance().getRoomStatus() == 0 || RoomManager.getInstance().getRoomStatus() == 6) {
                                finish();
                            }
                        }
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
            RoomManager.getInstance().pubMsg(msgName, msgId, toId, data, save, associatedMsgID, associatedUserID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            RoomManager.getInstance().delMsg(msgName, msgId, toId, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageFinishedVideoWhiteBoard() {
        Log.d("xiao", "onPageFinished: ");

        final JSONObject j = new JSONObject();
        try {
            j.put("mClientType", 3);
            j.put("deviceType", 0);
            j.put("isSendLogMessage", false);
            j.put("debugLog", false);
            j.put("myself", RoomManager.getInstance().getMySelf().toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.setInitPageParameterFormPhone('" + j.toString() + "')");
                movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.setInitPageParameterFormPhone('" + j.toString() + "')");
            }
        });

        if (RoomSession.jsVideoWBTempMsg.length() > 0) {
            final JSONObject js = new JSONObject();
            try {
                js.put("type", "room-msglist");
                js.put("message", RoomSession.jsVideoWBTempMsg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                    movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                   /* RoomSession.jsVideoWBTempMsg = new JSONArray();*/
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPageFinished() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] pers = new String[2];
            if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pers[0] = Manifest.permission.CAMERA;
                }
            }
            if (!(checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    for (int i = 0; i < pers.length; i++) {
                        if (pers[i] == null) {
                            pers[i] = Manifest.permission.RECORD_AUDIO;
                        }
                    }
                }
            }
            if (pers[0] != null) {
                requestPermissions(pers, REQUEST_CODED);
            } else {
                joinRoom();
            }
        } else {
            requestRoomPermissions();
            joinRoom();
        }
    }

    @Override
    public void onRoomDocChange() {
        if (RoomControler.isDocumentClassification()) {
            WhiteBoradManager.getInstance().getClassDocList();
            WhiteBoradManager.getInstance().getAdminDocList();
            WhiteBoradManager.getInstance().getClassMediaList();
            WhiteBoradManager.getInstance().getAdminmMediaList();
        } else {
            WhiteBoradManager.getInstance().getDocList();
            WhiteBoradManager.getInstance().getMediaList();
        }
    }

    @Override
    public void onWhiteBoradZoom(boolean isZoom) {

    }

    private String url;
    private boolean isvideo;
    private long fileid;
    private boolean isWBMediaPlay = false;

    @Override
    public void onWhiteBoradMediaPublish(String url, boolean isvideo, long fileid) {
        this.url = url;
        this.isvideo = isvideo;
        this.fileid = fileid;
        isWBMediaPlay = true;
    }

    public void joinRoom() {
        Log.d("classroomsdk", "Start!");

        HashMap<String, Object> params = new HashMap<String, Object>();
        if (!param.isEmpty())
            params.put("param", param);

        params.put("userid", userid);
        params.put("password", password);
        params.put("serial", serial);
        params.put("nickname", nickname);
        params.put("userrole", userrole);
        params.put("volume", 100);

        if (domain != null && !domain.isEmpty())
            params.put("domain", domain);
        if (servername != null && !servername.isEmpty())
            params.put("servername", servername);
        params.put("mobilenameOnList", mobilenameNotOnList);
        RoomManager.getInstance().setDeviceType("AndroidPhone");
        //        RoomSession.getInstance().getGiftNumJoinRoom(serial,userid,nickname,params);
        if (path != null && !path.isEmpty()) {
            params.put("path", path);
            if (type != -1)
                params.put("type", type);
            RoomManager.getInstance().joinPlayBackRoom(host, port, nickname, params, new HashMap<String, Object>(), true);
        } else {
            int ret = RoomManager.getInstance().joinRoom(host, port, nickname, params, new HashMap<String, Object>(), true);
            if (ret != RoomManager.ERR_OK) {
                Log.e("RoomActivity", "nonono");
            }
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == RoomSession.RoomLeaved) {

            RoomSession.isRoomLeaved = false;

            WhiteBoradManager.getInstance().clear();
            RoomSession.getInstance().setIsExit(false);
            RoomManager.getInstance().setCallbBack(null);
            RoomManager.getInstance().setWhiteBoard(null);
            RoomManager.getInstance().setVideoWhiteBoard(null);
            RoomManager.getInstance().useLoudSpeaker(false);
            if (sur_player_view != null) {
                sur_player_view.release();
            }
            path = null;
            //            unregisterIt();
            img_disk.clearAnimation();
            img_disk.setVisibility(View.GONE);
            finish();
        } else if (id == RoomFragment.MOVE_PAGE) {
            vi_contaioner.arrowScroll(66);
        } else if (id == RoomSession.UserChanged) {
            RoomUser chUser = (RoomUser) args[0];
            Map<String, Object> changeMap = (Map<String, Object>) args[1];
            if (!chUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
                return;
            }
            if (vi_contaioner.getCurrentItem() == 1) {
                vi_contaioner.setNoScroll(false);
            }
        } else if (id == RoomSession.SelfEvicted) {

           /* RoomClient.getInstance().kickout(RoomClient.Kickout_Repeat);*/
            isExitRoom = true;


        } else if (id == RoomSession.RoomJoined) {

            if (TextUtils.isEmpty(RoomManager.getInstance().get_MP3Url())) {
                SoundPlayUtils.init(this);
            } else {
                SoundPlayUtils.loadMP3(host, port, this);
            }

            //            registerIt();
            boolean meHasVideo = RoomManager.getInstance().getMySelf().hasVideo;
            boolean meHasAudio = RoomManager.getInstance().getMySelf().hasAudio;
            //RoomManager.getInstance().useLoudSpeaker(true);
            closeSpeaker();
            if (path != null && !path.isEmpty()) {
                rel_play_back.setVisibility(View.VISIBLE);
            } else {
                rel_play_back.setVisibility(View.INVISIBLE);
            }
            Tools.HideProgressDialog();

            if (videoXWalkView != null) {
                if (Config.isWhiteVideoBoardTest) {
                    videoXWalkView.loadUrl("http://192.168.1.182:1314/publish/index.html#/mobileApp_videoWhiteboard?videoDrawingBoardType=" + "mediaVideo");//广生
                } else {
                    videoXWalkView.loadUrl("file:///android_asset/react_mobile_video_whiteboard_publishdir/index.html#/mobileApp_videoWhiteboard?videoDrawingBoardType=" + "mediaVideo");
                }
            }

            if (movieXWalkView != null) {
                if (Config.isWhiteMovieBoardTest) {
                    movieXWalkView.loadUrl("http://192.168.1.182:1314/publish/index.html#/mobileApp_videoWhiteboard?videoDrawingBoardType=" + "fileVideo");//广生
                } else {
                    movieXWalkView.loadUrl("file:///android_asset/react_mobile_video_whiteboard_publishdir/index.html#/mobileApp_videoWhiteboard?videoDrawingBoardType=" + "fileVideo");
                }
            }

        } else if (id == RoomSession.ShowVideoView) {

            //            VideoPlayer.getInstance().init(sur_player_view);
        } else if (id == RoomSession.PlayNetVideo) {

            if (videoXWalkView != null) {
                videoXWalkView.setVisibility(View.VISIBLE);
                videoXWalkView.setZOrderOnTop(true);
            }

            re_loading.setVisibility(View.VISIBLE);
            animationDrawable = (AnimationDrawable) loadingImageView.getDrawable();
            animationDrawable.start();

            isWBMediaPlay = false;
            if (sur_player_view != null) {
                sur_player_view.release();
            }
            sur_container.setVisibility(View.VISIBLE);
            sur_screen.setVisibility(View.GONE);
            sur_player_view.setVisibility(View.VISIBLE);
            sur_player_view.init(EglBase.create().getEglBaseContext(), null);
            sur_player_view.setZOrderOnTop(true);
            sur_player_view.setZOrderMediaOverlay(true);

            Stream stream = (Stream) args[0];
            sur_player_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            RoomManager.getInstance().playVideo(stream.getExtensionId(), sur_player_view);

            frameListener = new EglRenderer.FrameListener() {
                @Override
                public void onFrame(Bitmap bitmap) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (animationDrawable != null && animationDrawable.isRunning()) {
                                animationDrawable.stop();
                            }
                            re_loading.setVisibility(View.GONE);
                        }
                    });
                }
            };
            sur_player_view.addFrameListener(frameListener, 0);

        } else if (id == RoomSession.PlayNetAudio) {
            isWBMediaPlay = false;
            Stream stream = (Stream) args[0];
            boolean ispause = stream.attrMap.get("pause") == null ? false : (boolean) stream.attrMap.get("pause");
            if (ispause) {
                img_disk.clearAnimation();
            } else {
                img_disk.startAnimation(operatingAnim);
            }
            img_disk.setVisibility(View.VISIBLE);
        } else if (id == RoomSession.PlayNetSeek) {

        } else if (id == RoomSession.PlayNetStop) {

            if (videoXWalkView != null) {
                videoXWalkView.setVisibility(View.INVISIBLE);
                videoXWalkView.setZOrderOnTop(false);
            }

            sur_container.setVisibility(View.GONE);
            sur_screen.setVisibility(View.GONE);

            if (sur_player_view != null) {
                Stream stream = (Stream) args[0];
                if (stream.isVideo() && frameListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sur_player_view.removeFrameListener(frameListener);
                        }
                    });
                    frameListener = null;
                }
                sur_player_view.setVisibility(View.GONE);
                sur_player_view.release();
            }

            sur_container.setVisibility(View.INVISIBLE);
            img_disk.clearAnimation();
            img_disk.setVisibility(View.GONE);

            if (isWBMediaPlay) {
                //            int pos = this.url.lastIndexOf('.');
                //            this.url = String.format("%s-%d%s", url.substring(0, pos), 1, url.substring(pos));
                //            String url = "http://" + WhiteBoradManager.getInstance().getFileServierUrl() + ":" + WhiteBoradManager.getInstance().getFileServierPort() + this.url;
                RoomManager.isMediaPublishing = true;
                if (RoomSession.getInstance().isClassBegin) {
                    RoomManager.getInstance().publishMedia(url, isvideo, "", fileid, "__all");
                } else {
                    RoomManager.getInstance().publishMedia(url, isvideo, "", fileid, RoomManager.getInstance().getMySelf().peerId);
                }
                isWBMediaPlay = false;
            }
            RoomSession.mediaPublishStream = null;
        } else if (id == RoomSession.RoomBreak) {
            img_disk.clearAnimation();
            img_disk.setVisibility(View.GONE);
            //vi_contaioner.setNoScroll(false);
            candraw = false;
            sur_container.setVisibility(View.INVISIBLE);
            if (sur_player_view != null) {
                sur_player_view.setVisibility(View.INVISIBLE);
                sur_player_view.release();
            }

            if (videoXWalkView != null) {
                final JSONObject js = new JSONObject();
                try {
                    js.put("type", "room-disconnected");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (movieXWalkView != null) {
                final JSONObject js = new JSONObject();
                try {
                    js.put("type", "room-disconnected");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Tools.ShowProgressDialog(this, getResources().getString(R.string.connected));
        } else if (id == RoomSession.MediaControl) {
            Stream stream = (Stream) args[0];
            boolean isplay = (boolean) args[1];
            if (isplay) {
                img_disk.clearAnimation();
            } else {
                if (!stream.isVideo()) {
                    img_disk.setVisibility(View.VISIBLE);
                    img_disk.startAnimation(operatingAnim);
                }
            }
        } else if (id == RoomSession.playBackUpdateTime) {

            if (isEnd) {
                return;
            }
            currenttime = (long) args[0];
            double pos = (double) (currenttime - starttime) / (double) (endtime - starttime);
            sek_play_back.setProgress((int) (pos * 100));

            SimpleDateFormat formatter = new SimpleDateFormat("mm:ss ");
            Date curDate = new Date(currenttime - starttime);//获取当前时间
            Date daDate = new Date(endtime - starttime);
            String strcur = formatter.format(curDate);
            String strda = formatter.format(daDate);
            txt_play_back_time.setText(strcur + "/" + strda);
            txt_play_back_time.setTextColor(Color.WHITE);
        } else if (id == RoomSession.playBackDuration) {
            starttime = (long) args[0];
            endtime = (long) args[1];
        } else if (id == RoomSession.playBackEnd) {

            isPlayPlayback = false;
            isEnd = true;
            img_play_back.setImageResource(R.drawable.btn_play_normal);
            sek_play_back.setProgress(0);
            RoomManager.getInstance().pausePlayback();
        } else if (id == RoomSession.ShareScreen) {
            sur_container.setVisibility(View.VISIBLE);
            sur_player_view.setVisibility(View.GONE);
            sur_screen.setVisibility(View.VISIBLE);
            img_disk.clearAnimation();
            img_disk.setVisibility(View.GONE);
            isWBMediaPlay = false;
            if (sur_screen != null) {
                sur_screen.release();
            }
            sur_screen.init(EglBase.create().getEglBaseContext(), null);
            sur_screen.setZOrderOnTop(true);
            sur_screen.setZOrderMediaOverlay(true);
            Stream stream = (Stream) args[0];
            sur_screen.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            RoomManager.getInstance().playVideo(stream.getExtensionId(), sur_screen);
        } else if (id == RoomSession.CloseShareScreen) {
            sur_container.setVisibility(View.GONE);
            sur_player_view.setVisibility(View.GONE);
            if (sur_screen != null) {
                sur_screen.setVisibility(View.GONE);
                sur_screen.release();
            }
            img_disk.clearAnimation();
            img_disk.setVisibility(View.GONE);
            if (isWBMediaPlay) {
                //            int pos = this.url.lastIndexOf('.');
                //            this.url = String.format("%s-%d%s", url.substring(0, pos), 1, url.substring(pos));
                //            String url = "http://" + WhiteBoradManager.getInstance().getFileServierUrl() + ":" + WhiteBoradManager.getInstance().getFileServierPort() + this.url;
                RoomManager.isMediaPublishing = true;
                if (RoomSession.getInstance().isClassBegin) {
                    RoomManager.getInstance().publishMedia(url, isvideo, "", fileid, "__all");
                } else {
                    RoomManager.getInstance().publishMedia(url, isvideo, "", fileid, RoomManager.getInstance().getMySelf().peerId);
                }
                isWBMediaPlay = false;
            }
        } else if (id == RoomSession.UDPError) {
            int i = (int) args[0];
            String msg = i == 1 ? getString(R.string.udp_alert) : getString(R.string.fire_wall_alert);
            Tools.ShowAlertDialog(this, msg);

        } else if (id == RoomSession.playBackClearAll) {
            if (videoXWalkView != null) {
                JSONObject js = new JSONObject();
                try {
                    js.put("type", "room-playback-clear_all");
                    videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (movieXWalkView != null) {
                JSONObject js = new JSONObject();
                try {
                    js.put("type", "room-playback-clear_all");
                    movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else if (id == RoomSession.RemoteMsg) {
            boolean add = (boolean) args[0];
            String id1 = (String) args[1];
            String name = (String) args[2];
            long ts = (long) args[3];
            Object data = args[4];
            if (add) {
                OnRemotePubMsg(id1, name, ts, data);
            } else {
                OnRemoteDelMsg(id1, name, ts, data);
            }
        } else if (id == RoomSession.PlayMovie) {

            if (movieXWalkView != null) {
                movieXWalkView.setVisibility(View.VISIBLE);
                movieXWalkView.setZOrderOnTop(true);
            }

            re_loading.setVisibility(View.VISIBLE);
            animationDrawable = (AnimationDrawable) loadingImageView.getDrawable();
            animationDrawable.start();

            isWBMediaPlay = false;
            if (sur_player_view != null) {
                sur_player_view.release();
            }
            sur_container.setVisibility(View.VISIBLE);
            sur_screen.setVisibility(View.GONE);
            sur_player_view.setVisibility(View.VISIBLE);
            sur_player_view.init(EglBase.create().getEglBaseContext(), null);
            sur_player_view.setZOrderOnTop(true);
            sur_player_view.setZOrderMediaOverlay(true);

            Stream stream = (Stream) args[0];
            sur_player_view.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            RoomManager.getInstance().playVideo(stream.getExtensionId(), sur_player_view);

            frameListener = new EglRenderer.FrameListener() {
                @Override
                public void onFrame(Bitmap bitmap) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (animationDrawable != null && animationDrawable.isRunning()) {
                                animationDrawable.stop();
                            }
                            re_loading.setVisibility(View.GONE);
                        }
                    });
                }
            };
            sur_player_view.addFrameListener(frameListener, 0);

        } else if (id == RoomSession.UnPlayMovie) {

            if (movieXWalkView != null) {
                movieXWalkView.setVisibility(View.INVISIBLE);
                movieXWalkView.setZOrderOnTop(false);
            }

            sur_container.setVisibility(View.GONE);
            sur_screen.setVisibility(View.GONE);
            if (sur_player_view != null) {
                if (frameListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sur_player_view.removeFrameListener(frameListener);
                        }
                    });
                    frameListener = null;
                }
                sur_player_view.setVisibility(View.GONE);
                sur_player_view.release();
            }
            sur_container.setVisibility(View.INVISIBLE);

            if (isWBMediaPlay) {
                RoomManager.isMediaPublishing = true;
                if (RoomSession.getInstance().isClassBegin) {
                    RoomManager.getInstance().publishMedia(url, isvideo, "", fileid, "__all");
                } else {
                    RoomManager.getInstance().publishMedia(url, isvideo, "", fileid, RoomManager.getInstance().getMySelf().peerId);
                }
                isWBMediaPlay = false;
            }
            RoomSession.mediaPublishStream = null;
        }
    }

    private void OnRemotePubMsg(String id, String name, long ts, Object data) {
        if (name.equals("ClassBegin")) {

        }
    }

    private void OnRemoteDelMsg(String id, String name, long ts, Object data) {
        if (name.equals("ClassBegin")) {
            if (videoXWalkView != null) {
                videoXWalkView.setVisibility(View.INVISIBLE);
                videoXWalkView.setZOrderOnTop(false);
            }
        }

        if (name.equals("ClassBegin")) {
            if (movieXWalkView != null) {
                movieXWalkView.setVisibility(View.INVISIBLE);
                movieXWalkView.setZOrderOnTop(false);
            }
        }
    }

    @Override
    // 按键按下，所触发的事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                vi_contaioner.arrowScroll(17);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                vi_contaioner.arrowScroll(66);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                NotificationCenter.getInstance().postNotificationName(SendMessageByKeyEvent);
                break;
            case KeyEvent.KEYCODE_MENU:
                NotificationCenter.getInstance().postNotificationName(ChangeViewLayoutByKeyEvent);
                break;
            case KeyEvent.KEYCODE_BACK:
                showExitDialog();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        joinRoom();
        if (grantResults.length == 0 || permissions.length == 0) {
            return;
        }

        for (int i = 0; i < permissions.length; i++) {
            if (Manifest.permission.CAMERA.equals(permissions[i])) {
                int grantResult = grantResults[0];
                boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    Tools.ShowAlertDialog(this, getString(R.string.camera_hint));
                    RoomClient.getInstance().warning(1);
                }
            }
            if (Manifest.permission.RECORD_AUDIO.equals(permissions[i])) {
                int grantResult = grantResults[0];
                boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    Tools.ShowAlertDialog(this, getString(R.string.mic_hint));
                    RoomClient.getInstance().warning(2);
                }
            }
        }
    }

    private boolean isMp4(String filetype) {
        int icon = -1;
        if (filetype.toLowerCase().endsWith("mp4") || filetype.toLowerCase().endsWith("webm")) {
            return true;
        } else {
            return false;
        }
    }

    public final static String B_PHONE_STATE =
            TelephonyManager.ACTION_PHONE_STATE_CHANGED;

    private BroadcastReceiverMgr mBroadcastReceiver;

    //按钮1-注册广播
    public void registerIt() {
        mBroadcastReceiver = new BroadcastReceiverMgr();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(B_PHONE_STATE);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    //按钮2-撤销广播
    public void unregisterIt() {
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RoomSession.mediaPublishStream = null;
        RoomSession.mediaunPublishStream = null;
        RoomSession.updateAttributeStream = null;
        RoomSession.jsVideoWBTempMsg = new JSONArray();
        if (videoXWalkView != null) {
            videoXWalkView.removeAllViews();
            videoXWalkView.onDestroy();
        }
        if (movieXWalkView != null) {
            movieXWalkView.removeAllViews();
            movieXWalkView.onDestroy();
        }
    }

    @Override
    public boolean onRemoteMsg(boolean add, String id, String name, long ts, Object data, String fromID, String associatedMsgID, String associatedUserID) {
        if (!add) {
            RoomSession.jsVideoWBTempMsg = new JSONArray();
        }

        JSONObject jsobj = new JSONObject();
        final JSONObject js = new JSONObject();
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
            if (add) {
                js.put("type", "room-pubmsg");
            } else {
                js.put("type", "room-delmsg");
            }
            js.put("message", jsobj);
            if (associatedMsgID.equals("VideoWhiteboard") || id.equals("VideoWhiteboard")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                        movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
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

    private void roomDisConnect() {
        if (videoXWalkView != null) {
            final JSONObject js = new JSONObject();
            try {
                js.put("type", "room-disconnected");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (movieXWalkView != null) {
            final JSONObject js = new JSONObject();
            try {
                js.put("type", "room-disconnected");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void roomPlaybackClearAll() {
        JSONObject js = new JSONObject();
        try {
            js.put("type", "room-playback-clear_all");
            videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
            movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void transmitWindowSize(int wid, int hid) {
        JSONObject js = new JSONObject();
        try {
            js.put("type", "transmitWindowSize");
            JSONObject jsmsg = new JSONObject();
            jsmsg.put("windowWidth", wid);
            jsmsg.put("windowHeight", hid);
            js.put("windowSize", jsmsg);
            if (videoXWalkView != null) {
                videoXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
            }
            if (movieXWalkView != null) {
                movieXWalkView.loadUrl("javascript:MOBILETKSDK.receiveInterface.dispatchEvent(" + js.toString() + ")");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
