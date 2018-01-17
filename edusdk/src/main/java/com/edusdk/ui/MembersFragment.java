package com.edusdk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edusdk.R;
import com.edusdk.entity.VideoGroup;
import com.edusdk.message.NotificationCenter;
import com.edusdk.message.RoomSession;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;

import org.webrtc.EglBase;
import org.webrtc.SurfaceViewRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MembersFragment extends Fragment implements NotificationCenter.NotificationCenterDelegate {
    private View fragmentView;

    private LinearLayout lin_students;
    private LinearLayout lin_big_video;
    private ArrayList<VideoGroup> stuVideos = new ArrayList<VideoGroup>();
    private boolean isStart = false;
    int roomType;
    VideoGroup vgTeacher = new VideoGroup();
    VideoGroup vgSec = new VideoGroup();
    boolean isAtc = false;
    private int count;
    private long firClick;
    private long secClick;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("xiao", "onCreateView");
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_members, null);
            lin_students = (LinearLayout) fragmentView.findViewById(R.id.lin_students);
            lin_big_video = (LinearLayout) fragmentView.findViewById(R.id.big_video);
            vgTeacher.rel = (RelativeLayout) fragmentView.findViewById(R.id.teacher_video_item);
            vgSec.rel = (RelativeLayout) fragmentView.findViewById(R.id.stu_video_item);

            vgTeacher.rel_video = (RelativeLayout) vgTeacher.rel.findViewById(R.id.rel_video);
            vgSec.rel_video = (RelativeLayout) vgSec.rel.findViewById(R.id.rel_video);

            vgTeacher.sf = (SurfaceViewRenderer) vgTeacher.rel.findViewById(R.id.sf_video);
            vgTeacher.sf.init(EglBase.create().getEglBaseContext(), null);
            vgSec.sf = (SurfaceViewRenderer) vgSec.rel.findViewById(R.id.sf_video);
            vgSec.sf.init(EglBase.create().getEglBaseContext(), null);

            vgTeacher.txt_name = (TextView) vgTeacher.rel.findViewById(R.id.txt_name);
            vgTeacher.txt_idef = (TextView) vgTeacher.rel.findViewById(R.id.txt_idef);
            vgSec.txt_idef = (TextView) vgSec.rel.findViewById(R.id.txt_idef);
            vgSec.txt_name = (TextView) vgSec.rel.findViewById(R.id.txt_name);

            vgTeacher.sf.setVisibility(View.INVISIBLE);
            vgSec.sf.setVisibility(View.INVISIBLE);


            initView();
        } else {
            ViewGroup parent = (ViewGroup) fragmentView.getParent();
            if (parent != null) {
                parent.removeView(fragmentView);
            }
        }
        setListener();
        return fragmentView;
    }


    private void initView() {
        if (lin_students == null) {
            return;
        }
        roomType = RoomManager.getInstance().getRoomType();
        if (roomType == 0) {
            lin_students.setVisibility(View.GONE);
        } else {
            lin_students.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        //        NotificationCenter.getInstance().addObserver(this, RoomSession.VideoPublished);
        //        NotificationCenter.getInstance().addObserver(this, RoomSession.VideoUnPublished);
        super.onStart();
        doLayout();
    }

    private void doLayout() {
        DisplayMetrics dm = new DisplayMetrics();
        if (getActivity() == null)
            return;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int wid = dm.widthPixels;
        final int hid = dm.heightPixels;
        LinearLayout.LayoutParams big_param = (LinearLayout.LayoutParams) lin_big_video.getLayoutParams();

        List<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
        Log.e("TAG+++++++", "playingList" + playingList.size());
        if (playingList.size() > 2) {
            big_param.height = hid / 3 * 2;
            LinearLayout.LayoutParams stu_param = (LinearLayout.LayoutParams) lin_students.getLayoutParams();

            stu_param.height = hid / 3;
            lin_students.setLayoutParams(stu_param);


            lin_big_video.setVisibility(View.VISIBLE);
            vgTeacher.rel.setVisibility(View.VISIBLE);
            vgSec.rel.setVisibility(View.VISIBLE);
            vgSec.sf.setVisibility(View.VISIBLE);
            vgTeacher.sf.setVisibility(View.VISIBLE);

            for (int i = 0; i < stuVideos.size(); i++) {
                LinearLayout.LayoutParams relParams = (LinearLayout.LayoutParams) stuVideos.get(i).rel.getLayoutParams();
                relParams.width = wid / 5;
                relParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                stuVideos.get(i).rel.setLayoutParams(relParams);
                stuVideos.get(i).rel.setVisibility(View.VISIBLE);
                stuVideos.get(i).sf.setVisibility(View.VISIBLE);
            }

        } else {
            big_param.height = hid;
            lin_students.setVisibility(View.GONE);
        }


        lin_big_video.setLayoutParams(big_param);
    }


    @Override
    public void onStop() {
        //       NotificationCenter.getInstance().removeObserver(this);
        super.onStop();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("TAG++++++", "setUserVisibleHint" + isVisibleToUser);
        if (isVisibleToUser) {
            doLayout();
            initView();
            for (RoomUser u : RoomSession.getInstance().getUnplayMap().values()) {
                doUnPlayVideo(u);
            }
            doPlayAllVideo();
            doPlayVideo();
            if (isAtc) {
                if (!RoomSession.isClassBegin) {
                    playSelfBeforeClassBegin();
                }
                NotificationCenter.getInstance().addObserver(this, RoomSession.VideoPublished);
                NotificationCenter.getInstance().addObserver(this, RoomSession.VideoUnPublished);
                NotificationCenter.getInstance().addObserver(this, RoomSession.UserChanged);
                NotificationCenter.getInstance().addObserver(this, RoomSession.RemoteMsg);
            }
        } else {
            doUnPlayAllVideo();
            NotificationCenter.getInstance().removeObserver(this);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    /***
     * 从别的界面切换回来的时候，要播放当前播放的视频
     */

    private void doPlayVideo() {
        Log.e("TAG+++++", "doPlayVideo");

        List<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
        boolean isPlayTeacher = false;
        for (int i = 0; i < playingList.size(); i++) {
            if (playingList.get(i).role == 0) {
                isPlayTeacher = true;
            }
        }
        String secPeerid = "";
        if (playingList.size() > 2) {
            lin_students.setVisibility(View.VISIBLE);
        } else {
            if (playingList.size() == 2 && !isPlayTeacher) {
                lin_students.setVisibility(View.VISIBLE);
            } else {
                lin_students.setVisibility(View.GONE);
            }
        }
        for (int i = 0; i < playingList.size(); i++) {
            RoomUser u = playingList.get(i);
            if (roomType == 0) {
                if (u.role == 0) {
                    doPlayTeacherVideo(u);
                } else {
                    if (RoomManager.getInstance().getMySelf().peerId.equals(u.peerId) && RoomManager.getInstance().getMySelf().role == 2) {
                        doPlaySecVideo(u, true);
                    } else {
                        if (u.role == 2) {
                            doPlaySecVideo(u, false);
                        } else if (u.role == 1) {
                            doPlayStudentVideo(u);
                        }
                    }
                }
            } else {
                if (u.role == 0) {
                    doPlayTeacherVideo(u);
                } else if (u.role != 4) {
                    if (RoomSession.getInstance().isPlayingMe()) {
                        if (!u.peerId.equals(RoomManager.getInstance().getMySelf().peerId) && RoomActivity.userrole == 4) {
                            doPlaySecVideo(u, false);
                        }

                        if (u.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
                            if (u.role == 2) {
                                doPlaySecVideo(u, true);
                            } else {
                                doPlaySecVideo(u, false);
                            }
                        } else {
                            doPlayStudentVideo(u);
                        }
                    } else {
                        if (vgSec.peerid == null || vgSec.peerid.isEmpty()) {
                            doPlaySecVideo(u, false);
                        }
                        doPlayStudentVideo(u);
                    }
                }
            }
        }
    }

    private void doUnPlayVideo(RoomUser user) {
        if (user.role == 0) {
            vgTeacher.rel_video.setVisibility(View.INVISIBLE);
        }
        if (user.peerId.equals(vgSec.peerid)) {
            vgSec.rel_video.setVisibility(View.INVISIBLE);
            vgSec.peerid = "";
            if (stuVideos.size() > 0) {
                lin_students.removeView(stuVideos.get(0).rel);
                stuVideos.get(0).sf.release();
                if (RoomManager.getInstance().getUser(stuVideos.get(0).peerid) != null)
                    doPlaySecVideo(RoomManager.getInstance().getUser(stuVideos.get(0).peerid), false);
                stuVideos.remove(0);

            }
        }
        for (int i = 0; i < stuVideos.size(); i++) {
            if (stuVideos.get(i).peerid.equals(user.peerId)) {
                stuVideos.get(i).sf.release();
                lin_students.removeView(stuVideos.get(i).rel);
                stuVideos.remove(i);
            }
        }
    }

    private boolean studentIsFullScreen = false;

    private void doPlayStudentVideo(RoomUser user) {
        Log.e("TAG+++++", "doPlayStudentVideo");
        DisplayMetrics dm = new DisplayMetrics();
        if (getActivity() == null) {
            return;
        }
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int wid = dm.widthPixels;
        final int hid = dm.heightPixels;
        boolean hasSit = false;
        for (int i = 0; i < stuVideos.size(); i++) {
            if (stuVideos.get(i).peerid.equals(user.peerId)) {
                hasSit = true;
            }
        }
        if (user.peerId.equals(vgSec.peerid) && !RoomSession.getInstance().isPlayingMe() || RoomActivity.userrole == 4) {
            hasSit = true;
        }
        if (!hasSit) {
            final VideoGroup stu = new VideoGroup();
            RelativeLayout vdi_stu = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.videoitem, null);
            stu.rel = vdi_stu;
            stu.sf = (SurfaceViewRenderer) vdi_stu.findViewById(R.id.sf_video);
            stu.sf.init(EglBase.create().getEglBaseContext(), null);
            stu.txt_name = (TextView) vdi_stu.findViewById(R.id.txt_name);
            stu.peerid = user.peerId;
            stu.txt_name.setText(user.nickName);
            stuVideos.add(stu);
            lin_students.addView(vdi_stu);
            LinearLayout.LayoutParams relParams = (LinearLayout.LayoutParams) stu.rel.getLayoutParams();
            relParams.width = wid / 5;
            relParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
            stu.rel.setLayoutParams(relParams);
            if ((user.publishState > 1 && user.publishState < 4)) {
                stu.sf.setVisibility(View.VISIBLE);
                RoomManager.getInstance().playVideo(user.peerId, stu.sf);
            } else {
                stu.sf.setVisibility(View.INVISIBLE);
            }
        } else {
            for (int i = 0; i < stuVideos.size(); i++) {
                if (user.peerId.equals(stuVideos.get(i).peerid)) {
                    if ((user.publishState > 1 && user.publishState < 4)) {
                        stuVideos.get(i).sf.setVisibility(View.VISIBLE);
                        RoomManager.getInstance().playVideo(user.peerId, stuVideos.get(i).sf);
                    } else {
                        stuVideos.get(i).sf.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        try {
            for (int i = 0; i < stuVideos.size(); i++) {
                final int finalI = i;
                stuVideos.get(i).rel.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (MotionEvent.ACTION_DOWN == event.getAction()) {
                            int position = finalI;//记住当前点击的是第几个
                            //                            String id=stuVideos.get(position).peerid;
                            count++;
                            if (count == 1) {
                                firClick = System.currentTimeMillis();

                            } else if (count == 2) {
                                secClick = System.currentTimeMillis();
                                if (secClick - firClick < 1000) {

                                    Log.e("TAG+++++", "点击了第" + finalI + "个");

                                    if (!studentIsFullScreen) {

                                        for (int j = 0; j < stuVideos.size(); j++) {
                                            if (position != j) {
                                                stuVideos.get(j).rel.setVisibility(View.GONE);
                                                stuVideos.get(j).sf.setVisibility(View.GONE);
                                            } else {
                                                stuVideos.get(j).rel.setVisibility(View.VISIBLE);
                                                stuVideos.get(j).sf.setVisibility(View.VISIBLE);

                                            }
                                        }
                                        lin_big_video.setVisibility(View.GONE);
                                        vgTeacher.rel.setVisibility(View.GONE);
                                        vgSec.rel.setVisibility(View.GONE);
                                        vgSec.sf.setVisibility(View.GONE);
                                        vgTeacher.sf.setVisibility(View.GONE);

                                        LinearLayout.LayoutParams relParams = (LinearLayout.LayoutParams) stuVideos.get(finalI).rel.getLayoutParams();
                                        relParams.width = wid;
                                        relParams.height = hid;
                                        stuVideos.get(finalI).rel.setLayoutParams(relParams);

                                        //                                        lin_students.setLayoutParams(relParams);

                                        LinearLayout.LayoutParams stu_param = (LinearLayout.LayoutParams) lin_students.getLayoutParams();
                                        stu_param.height = hid;
                                        stu_param.width = wid;
                                        lin_students.setLayoutParams(stu_param);


                                        studentIsFullScreen = true;

                                    } else {

                                        for (int j = 0; j < stuVideos.size(); j++) {
                                            stuVideos.get(j).rel.setVisibility(View.VISIBLE);
                                            stuVideos.get(j).sf.setVisibility(View.VISIBLE);
                                        }
                                        lin_big_video.setVisibility(View.VISIBLE);
                                        vgTeacher.rel.setVisibility(View.VISIBLE);
                                        vgSec.rel.setVisibility(View.VISIBLE);
                                        vgSec.sf.setVisibility(View.VISIBLE);
                                        vgTeacher.sf.setVisibility(View.VISIBLE);

                                        doLayout();

                                        doPlayAllVideo();
                                        doPlayVideo();

                                        //                                        RoomManager.getInstance().playVideo(id,stuVideos.get(position).sf);

                                        LinearLayout.LayoutParams stu_param = (LinearLayout.LayoutParams) lin_students.getLayoutParams();
                                        stu_param.height = hid / 3;
                                        stu_param.width = wid;
                                        lin_students.setLayoutParams(stu_param);


                                        LinearLayout.LayoutParams relParams = (LinearLayout.LayoutParams) stuVideos.get(finalI).rel.getLayoutParams();
                                        relParams.width = wid / 5;
                                        relParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                        stuVideos.get(finalI).rel.setLayoutParams(relParams);


                                        studentIsFullScreen = false;


                                    }
                                }
                                count = 0;
                                firClick = 0;
                                secClick = 0;

                            }
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {

        }

    }

    private void doPlaySecVideo(RoomUser u, boolean isShowIdef) {
        Log.e("TAG+++++", "doPlaySecVideo");

        vgSec.rel_video.setVisibility(View.VISIBLE);
        vgSec.txt_name.setText(u.nickName);
        if (isAdded()) {
            vgSec.txt_idef.setText("（" + getActivity().getApplicationContext().getResources().getString(R.string.me) + "）");
        }
        if (isShowIdef)
            vgSec.txt_idef.setVisibility(View.VISIBLE);
        else
            vgSec.txt_idef.setVisibility(View.INVISIBLE);
        vgSec.peerid = u.peerId;
        if ((u.publishState > 1 && u.publishState < 4)) {
            vgSec.sf.setVisibility(View.VISIBLE);
            RoomManager.getInstance().playVideo(u.peerId, vgSec.sf);
        } else {
            vgSec.sf.setVisibility(View.INVISIBLE);
        }
    }

    private void playSelfBeforeClassBegin() {
        if (!TextUtils.isEmpty(RoomManager.getInstance().getMySelf().peerId) && RoomManager.getInstance().getMySelf().role == 2) {
            vgSec.sf.setVisibility(View.VISIBLE);
            RoomManager.getInstance().playVideo(RoomManager.getInstance().getMySelf().peerId, vgSec.sf);
        }
    }

    private void unPlaySelfAfterClassBegin() {
        RoomManager.getInstance().unPlayVideo(RoomManager.getInstance().getMySelf().peerId);
        vgSec.sf.setVisibility(View.INVISIBLE);
    }

    private void doPlayTeacherVideo(RoomUser u) {
        vgTeacher.rel_video.setVisibility(View.VISIBLE);
        vgTeacher.txt_name.setText(u.nickName);
        vgTeacher.txt_idef.setVisibility(View.VISIBLE);
        if (isAdded()) {
            vgTeacher.txt_idef.setText("（" + getResources().getString(R.string.teacher) + "）");
        }
        vgTeacher.txt_idef.setVisibility(View.VISIBLE);
        if ((u.publishState > 1 && u.publishState < 4)) {
            vgTeacher.sf.setVisibility(View.VISIBLE);
            RoomManager.getInstance().playVideo(u.peerId, vgTeacher.sf);
        } else {
            vgTeacher.sf.setVisibility(View.INVISIBLE);
        }
    }

    /***
     * 切换到别的界面的时候，所有视频要停止播放
     */
    public void doUnPlayAllVideo() {

        //        ArrayList<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
        for (int i = 0; i < stuVideos.size(); i++) {
            String peerid = stuVideos.get(i).peerid;
            if (stuVideos.get(i).sf.getVisibility() == View.VISIBLE) {
                RoomManager.getInstance().unPlayVideo(peerid);
            }
        }
    }

    private void doPlayAllVideo() {
        Log.e("TAG+++++", "doPlayAllVideo");

        for (int i = 0; i < stuVideos.size(); i++) {
            String peerid = stuVideos.get(i).peerid;
            RoomUser u = RoomManager.getInstance().getUser(peerid);
            if (stuVideos.get(i).sf.getVisibility() == View.VISIBLE && u != null) {
                if ((u.publishState > 1 && u.publishState < 4)) {
                    stuVideos.get(i).sf.setVisibility(View.VISIBLE);
                    RoomManager.getInstance().playVideo(u.peerId, stuVideos.get(i).sf);
                } else {
                    stuVideos.get(i).sf.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //        if(!isAtc){
        //            NotificationCenter.getInstance().addObserver(this, RoomSession.VideoPublished);
        //            NotificationCenter.getInstance().addObserver(this, RoomSession.VideoUnPublished);
        //            NotificationCenter.getInstance().addObserver(this, RoomSession.UserChanged);
        isAtc = true;
        //        }
    }

    @Override
    public void didReceivedNotification(final int id, Object... args) {
        switch (id) {
            case RoomSession.VideoPublished:
                Log.e("TAG+++++", "VideoPublished");

                if (isAtc) {
                    doPlayVideo();
                    doLayout();
                }
                break;
            case RoomSession.VideoUnPublished:
                Log.e("TAG+++++", "VideoUnPublished");

                doLayout();
                RoomUser upuser = (RoomUser) args[0];
                doUnPlayVideo(upuser);
                List<RoomUser> playingList = RoomSession.getInstance().getPlayingList();
                boolean isPlayTeacher = false;
                for (int i = 0; i < playingList.size(); i++) {
                    if (playingList.get(i).role == 0) {
                        isPlayTeacher = true;
                    }
                }
                String secPeerid = "";
                if (playingList.size() > 2) {
                    lin_students.setVisibility(View.VISIBLE);
                } else {
                    if (playingList.size() == 2 && !isPlayTeacher) {
                        lin_students.setVisibility(View.VISIBLE);
                    } else {
                        lin_students.setVisibility(View.GONE);
                    }
                }
                break;
            case RoomSession.UserChanged:
                Log.e("TAG+++++", "UserChanged");

                doLayout();
                RoomUser chUser = (RoomUser) args[0];
                Map<String, Object> changeMap = (Map<String, Object>) args[1];

                if (changeMap.containsKey("publishstate")) {
                    doPlayVideo();
                }
                if (chUser.role == 0 && chUser.publishState <= 1) {
                    doUnPlayVideo(chUser);
                }
                break;
            case RoomSession.RemoteMsg:
                Log.e("TAG+++++", "RemoteMsg");

                boolean add = (boolean) args[0];
                String id1 = (String) args[1];
                String name = (String) args[2];
                long ts = (long) args[3];
                Object data = args[4];
                if (add) {
                    OnRemotePubMsg(id1, name, ts, data);
                }
                break;
            default:
                break;
        }
    }

    private void OnRemotePubMsg(String id, String name, long ts, Object data) {
        Log.e("TAG+++++++", "OnRemotePubMsg");
        if (name.equals("ClassBegin")) {
            unPlaySelfAfterClassBegin();
        }
    }

    @Override
    public void onDestroyView() {
        Log.e("xiao", "onDestroyView");
        vgTeacher.sf.release();
        vgSec.sf.release();
        for (int i = 0; i < stuVideos.size(); i++) {
            stuVideos.get(i).sf.release();
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (RoomManager.getInstance().getMySelf() != null) {
                playSelfBeforeClassBegin();
            }
        }
    }

    private void setListener() {
        vgTeacher.rel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    count++;
                    if (count == 1) {
                        firClick = System.currentTimeMillis();

                    } else if (count == 2) {
                        secClick = System.currentTimeMillis();
                        if (secClick - firClick < 1000) {
                            if (!isFullScreen) {
                                setFullScreen(vgTeacher.rel, vgSec.rel, vgSec.sf);
                            } else {
                                setSmallScreen(vgTeacher.rel, vgSec.rel, vgSec.sf);
                            }
                        }
                        count = 0;
                        firClick = 0;
                        secClick = 0;

                    }
                }
                return true;
            }
        });
        vgSec.rel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    count++;
                    if (count == 1) {
                        firClick = System.currentTimeMillis();

                    } else if (count == 2) {
                        secClick = System.currentTimeMillis();
                        if (secClick - firClick < 1000) {
                            if (!isFullScreen) {
                                setFullScreen(vgSec.rel, vgTeacher.rel, vgTeacher.sf);
                            } else {
                                setSmallScreen(vgSec.rel, vgTeacher.rel, vgTeacher.sf);
                            }
                        }
                        count = 0;
                        firClick = 0;
                        secClick = 0;

                    }
                }
                return true;
            }
        });


    }

    /**
     * 全屏的判断
     */
    private boolean isFullScreen = false;

    private void setFullScreen(RelativeLayout teacher, RelativeLayout stu, SurfaceViewRenderer sf) {
        DisplayMetrics dm = new DisplayMetrics();
        if (getActivity() == null)
            return;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int wid = dm.widthPixels;
        final int hid = dm.heightPixels;

        LinearLayout.LayoutParams big_param1 = (LinearLayout.LayoutParams) lin_big_video.getLayoutParams();
        big_param1.height = hid;
        lin_big_video.setLayoutParams(big_param1);

        LinearLayout.LayoutParams big_param = (LinearLayout.LayoutParams) teacher.getLayoutParams();
        //        big_param.weight=2;
        big_param.width = LinearLayout.LayoutParams.MATCH_PARENT;
        big_param.height = LinearLayout.LayoutParams.MATCH_PARENT;
        teacher.setLayoutParams(big_param);


        doPlayVideo();
        stu.setVisibility(View.GONE);
        sf.setVisibility(View.GONE);

        lin_students.setVisibility(View.GONE);
        isFullScreen = true;
    }

    private void setSmallScreen(RelativeLayout teacher, RelativeLayout stu, SurfaceViewRenderer sf) {

        LinearLayout.LayoutParams big_param = (LinearLayout.LayoutParams) teacher.getLayoutParams();
        big_param.weight = 1;
        teacher.setLayoutParams(big_param);

        stu.setVisibility(View.VISIBLE);
        sf.setVisibility(View.VISIBLE);

        doPlayVideo();

        LinearLayout.LayoutParams stu_params = (LinearLayout.LayoutParams) stu.getLayoutParams();
        stu_params.weight = 1;
        stu.setLayoutParams(big_param);

        lin_students.setVisibility(View.VISIBLE);
        isFullScreen = false;
        doLayout();
    }

}
