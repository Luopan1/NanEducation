package com.edusdk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edusdk.R;
import com.edusdk.entity.VideoGroup;
import com.edusdk.message.NotificationCenter;
import com.edusdk.message.RoomSession;
import com.edusdk.tools.Tools;
import com.talkcloud.roomsdk.RoomControler;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;

import org.webrtc.EglBase;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MembersFragment extends Fragment implements NotificationCenter.NotificationCenterDelegate {

    private View fragmentView;
    private LinearLayout lin_students;
    private LinearLayout lin_big_video;
    private ArrayList<VideoGroup> stuVideos = new ArrayList<VideoGroup>();
    int roomType;
    VideoGroup vgTeacher = new VideoGroup();
    VideoGroup vgSec = new VideoGroup();
    boolean isAtc = false;
    private ImageView iv_back;
    private boolean isVisiable = false;

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
            vgTeacher.re_background = (RelativeLayout) vgTeacher.rel.findViewById(R.id.re_background);
            vgTeacher.tv_home = (TextView) vgTeacher.rel.findViewById(R.id.tv_home);

            vgSec.rel_video = (RelativeLayout) vgSec.rel.findViewById(R.id.rel_video);
            vgSec.re_background = (RelativeLayout) vgSec.rel.findViewById(R.id.re_background);
            vgSec.tv_home = (TextView) vgSec.rel.findViewById(R.id.tv_home);

            vgTeacher.sf = (SurfaceViewRenderer) vgTeacher.rel.findViewById(R.id.sf_video);
            vgTeacher.sf.init(EglBase.create().getEglBaseContext(), null);
            vgSec.sf = (SurfaceViewRenderer) vgSec.rel.findViewById(R.id.sf_video);
            vgSec.sf.init(EglBase.create().getEglBaseContext(), null);

            vgTeacher.txt_name = (TextView) vgTeacher.rel.findViewById(R.id.txt_name);
            vgTeacher.txt_idef = (TextView) vgTeacher.rel.findViewById(R.id.txt_idef);
            vgSec.txt_idef = (TextView) vgSec.rel.findViewById(R.id.txt_idef);
            vgSec.txt_name = (TextView) vgSec.rel.findViewById(R.id.txt_name);

            iv_back = (ImageView) fragmentView.findViewById(R.id.iv_back);

            vgTeacher.sf.setVisibility(View.INVISIBLE);
            vgSec.sf.setVisibility(View.INVISIBLE);

            initView();
        } else {
            ViewGroup parent = (ViewGroup) fragmentView.getParent();
            if (parent != null) {
                parent.removeView(fragmentView);
            }
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vgTeacher.sf != null) {
                    vgTeacher.sf.setVisibility(View.GONE);
                }
                if (vgSec.sf != null) {
                    vgSec.sf.setVisibility(View.GONE);
                }

                if (stuVideos != null && stuVideos.size() > 0) {
                    for (int i = 0; i < stuVideos.size(); i++) {
                        stuVideos.get(i).sf.setVisibility(View.GONE);
                    }
                }

                RoomActivity.vi_contaioner.arrowScroll(17);
            }
        });
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
        NotificationCenter.getInstance().addObserver(this, RoomActivity.GONE_VIDEO);
        super.onStart();
        if (isVisiable) {
            doPlayVideo();
            doLayout();
        }
    }

    private void doLayout() {
        DisplayMetrics dm = new DisplayMetrics();
        if (getActivity() == null)
            return;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int wid = dm.widthPixels;
        int hid = dm.heightPixels;
        LinearLayout.LayoutParams big_param = (LinearLayout.LayoutParams) lin_big_video.getLayoutParams();
        LinearLayout.LayoutParams stu_param = (LinearLayout.LayoutParams) lin_students.getLayoutParams();
        big_param.height = hid / 3 * 2;
        stu_param.height = hid / 3 * 1;
        stu_param.gravity = Gravity.CENTER_VERTICAL;

        /*int maxVideo = -1;
        if (RoomManager.getInstance().getRoomProperties() != null) {
            maxVideo = RoomManager.getInstance().getRoomProperties().optInt("maxvideo", 6);
        }
        if (stuVideos.size() > 0 && maxVideo > 7) {
            *//*big_param.height = hid - stuVideos.get(0).rel.getLayoutParams().height;*//*
            big_param.height = hid / 3 * 2;
            stu_param.height = stuVideos.get(0).rel.getLayoutParams().height;
        } else {
            big_param.height = hid / 3 * 2;
            stu_param.height = hid / 3 * 1;
            stu_param.gravity = Gravity.CENTER_VERTICAL;
            *//*big_param.height = hid ;*//*
        }*/


        lin_big_video.setLayoutParams(big_param);
        lin_students.setLayoutParams(stu_param);

        LinearLayout.LayoutParams lin_param = (LinearLayout.LayoutParams) vgTeacher.rel.getLayoutParams();
        lin_param.height = big_param.height;
        vgTeacher.rel.setLayoutParams(lin_param);
        vgSec.rel.setLayoutParams(lin_param);

        RelativeLayout.LayoutParams rel_param = (RelativeLayout.LayoutParams) vgTeacher.rel_video.getLayoutParams();
        rel_param.height = vgTeacher.rel_video.getWidth() / 4 * 3;
        vgTeacher.rel_video.setLayoutParams(rel_param);
        vgSec.rel_video.setLayoutParams(rel_param);
    }

    @Override
    public void onStop() {
        /*NotificationCenter.getInstance().removeObserver(this);*/
        isVisiable = false;
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisiable = isVisibleToUser;
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
                NotificationCenter.getInstance().addObserver(this, RoomSession.RoomBreak);
                NotificationCenter.getInstance().addObserver(this, RoomActivity.GONE_VIDEO);
            }
            setUserBackGroud();
        } else {
            doUnPlayAllVideo();
            NotificationCenter.getInstance().removeObserver(this);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void setUserBackGroud() {
        for (int x = 0; x < stuVideos.size(); x++) {
            RoomUser roomUser = RoomManager.getInstance().getUser(stuVideos.get(x).peerid);
            if (roomUser != null) {
                boolean isinback = Tools.isTure(roomUser.properties.get("isInBackGround"));
                if (isinback) {
                    stuVideos.get(x).re_background.setVisibility(View.VISIBLE);
                } else {
                    stuVideos.get(x).re_background.setVisibility(View.GONE);
                }
            }
        }
        if (vgSec != null) {
            if (vgSec.peerid != null && !vgSec.peerid.isEmpty()) {
                RoomUser roomUser = RoomManager.getInstance().getUser(vgSec.peerid);
                if (roomUser != null) {
                    boolean isinback = Tools.isTure(roomUser.properties.get("isInBackGround"));
                    if (isinback) {
                        vgSec.re_background.setVisibility(View.VISIBLE);
                    } else {
                        vgSec.re_background.setVisibility(View.GONE);
                    }
                }
            }
        }
        if (vgTeacher != null) {
            if (vgTeacher.peerid != null && !vgTeacher.peerid.isEmpty()) {
                RoomUser roomUser = RoomManager.getInstance().getUser(vgTeacher.peerid);
                if (roomUser != null) {
                    boolean isinback = Tools.isTure(roomUser.properties.get("isInBackGround"));
                    if (isinback) {
                        vgTeacher.re_background.setVisibility(View.VISIBLE);
                    } else {
                        vgTeacher.re_background.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    /***
     * 从别的界面切换回来的时候，要播放当前播放的视频
     */

    private void doPlayVideo() {
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
            if (isAdded()) {
                iv_back.setImageDrawable(getResources().getDrawable(R.drawable.back_pressed));
            }
        } else {
            if (isAdded()) {
                iv_back.setImageDrawable(getResources().getDrawable(R.drawable.back_normal));
            }
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
                            if (playingList.size() == 2) {
                                doPlaySecVideo(u, false);
                            }
                            if (playingList.size() > 2) {
                                doPlayStudentVideo(u);
                            }
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
                        } else {
                            doPlayStudentVideo(u);
                        }
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

    private void doPlayStudentVideo(RoomUser user) {
        if (getActivity() == null) {
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int wid = dm.widthPixels;
        final int hid = dm.heightPixels;
        boolean hasSit = false;
        for (int i = 0; i < stuVideos.size(); i++) {
            if (stuVideos.get(i).peerid.equals(user.peerId)) {
                hasSit = true;
            }
        }
        if (user.peerId.equals(vgSec.peerid) && !RoomSession.getInstance().isPlayingMe() && RoomManager.getInstance().getRoomType() != 0) {
            hasSit = true;
        }

        if (!hasSit) {
            VideoGroup stu = new VideoGroup();
            RelativeLayout vdi_stu = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.videoitem, null);
            stu.rel = vdi_stu;
            stu.sf = (SurfaceViewRenderer) vdi_stu.findViewById(R.id.sf_video);
            stu.sf.init(EglBase.create().getEglBaseContext(), null);
            stu.txt_name = (TextView) vdi_stu.findViewById(R.id.txt_name);
            stu.re_background = (RelativeLayout) vdi_stu.findViewById(R.id.re_background);
            stu.peerid = user.peerId;
            stu.txt_name.setText(user.nickName);
            stuVideos.add(stu);
            lin_students.addView(vdi_stu);

            LinearLayout.LayoutParams relParams = (LinearLayout.LayoutParams) stu.rel.getLayoutParams();
            int maxVideo = -1;
            if (RoomManager.getInstance().getRoomProperties() != null) {
                maxVideo = RoomManager.getInstance().getRoomProperties().optInt("maxvideo", 6);
            }
            if (maxVideo > 7) {
                relParams.width = wid / stuVideos.size();
            } else {
                relParams.width = wid / 5;
            }
            /*relParams.width = wid / 10;*/
            relParams.height = relParams.width / 4 * 3;
            relParams.gravity = Gravity.CENTER_VERTICAL;
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
        do1vsnLayout();
    }

    private void do1vsnLayout() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int wid = dm.widthPixels;
        int hid = dm.heightPixels;
        for (int i = 0; i < stuVideos.size(); i++) {
            LinearLayout.LayoutParams relParams = (LinearLayout.LayoutParams) stuVideos.get(i).rel.getLayoutParams();
            int maxVideo = -1;
            if (RoomManager.getInstance().getRoomProperties() != null) {
                maxVideo = RoomManager.getInstance().getRoomProperties().optInt("maxvideo", 6);
            }
            if (maxVideo > 7) {
                relParams.width = wid / 11;
            } else {
                relParams.width = wid / 5;
            }
            /*relParams.width = wid / 10;*/
            relParams.height = relParams.width / 4 * 3;
            stuVideos.get(i).rel.setLayoutParams(relParams);
            stuVideos.get(i).sf.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            stuVideos.get(i).sf.setMirror(true);
        }
        doLayout();
    }

    private void doPlaySecVideo(RoomUser u, boolean isShowIdef) {
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
        vgSec.sf.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
    }

    private void playSelfBeforeClassBegin() {
        if (RoomSession.isClassBegin) {
            return;
        }
        if (RoomManager.getInstance().getMySelf() != null) {
            if (!TextUtils.isEmpty(RoomManager.getInstance().getMySelf().peerId) && RoomManager.getInstance().getMySelf().role == 2) {
                if (RoomManager.getInstance().getMySelf() != null) {
                    if (RoomManager.getInstance().getMySelf().publishState == 1 || RoomManager.getInstance().getMySelf().publishState == 4) {
                        vgSec.sf.setVisibility(View.INVISIBLE);
                    } else {
                        vgSec.sf.setVisibility(View.VISIBLE);
                    }
                }
                if (RoomControler.isReleasedBeforeClass()) {
                    if (RoomManager.getInstance().getMySelf() != null && RoomManager.getInstance().getMySelf().publishState == 0) {
                        RoomManager.getInstance().changeUserPublish(RoomManager.getInstance().getMySelf().peerId, 3);
                    }
                } else {
                    RoomManager.getInstance().playVideo(RoomManager.getInstance().getMySelf().peerId, vgSec.sf);
                }
            }
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
            vgTeacher.txt_idef.setText("（" + getActivity().getApplicationContext().getString(R.string.teacher) + "）");
        }
        vgTeacher.txt_idef.setVisibility(View.VISIBLE);
        if ((u.publishState > 1 && u.publishState < 4)) {
            vgTeacher.sf.setVisibility(View.VISIBLE);
            RoomManager.getInstance().playVideo(u.peerId, vgTeacher.sf);
        } else {
            vgTeacher.sf.setVisibility(View.INVISIBLE);
        }
        vgTeacher.sf.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
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

        if (vgSec.peerid != null && !vgSec.peerid.isEmpty()) {
            RoomManager.getInstance().unPlayVideo(vgSec.peerid);
            vgSec.sf.setVisibility(View.INVISIBLE);
        }

        if (vgTeacher.sf != null) {
            vgTeacher.sf.setVisibility(View.GONE);
        }

        /*if (vgTeacher.peerid != null && !vgTeacher.peerid.isEmpty()) {
            RoomManager.getInstance().unPlayVideo(vgTeacher.peerid);
            vgTeacher.sf.setVisibility(View.INVISIBLE);
        }*/
    }

    private void doPlayAllVideo() {
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
                if (isAtc) {
                    doPlayVideo();
                    doLayout();
                }
                break;
            case RoomSession.VideoUnPublished:
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
                    if (isAdded()) {
                        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.back_pressed));
                    }
                } else {
                    if (isAdded()) {
                        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.back_normal));
                    }
                    if (playingList.size() == 2 && !isPlayTeacher) {
                        lin_students.setVisibility(View.VISIBLE);
                    } else {
                        lin_students.setVisibility(View.GONE);
                    }
                }
                doLayout();
                break;
            case RoomSession.UserChanged:
                RoomUser chUser = (RoomUser) args[0];
                Map<String, Object> changeMap = (Map<String, Object>) args[1];

                if (changeMap.containsKey("publishstate")) {
                    doPlayVideo();
                }
                if (/*chUser.role == 0 &&*/ chUser.publishState < 1) {
                    doUnPlayVideo(chUser);
                }
                if (changeMap.containsKey("isInBackGround")) {
                    boolean isinback = Tools.isTure(changeMap.get("isInBackGround"));
                    if (chUser.role == 0) {
                        if (isinback) {
                            vgTeacher.re_background.setVisibility(View.VISIBLE);
                            vgTeacher.tv_home.setText(R.string.tea_background);
                        } else {
                            vgTeacher.re_background.setVisibility(View.GONE);
                        }
                    }

                    if (vgSec.peerid != null && !vgSec.peerid.isEmpty()) {
                        if (vgSec.peerid != null && !vgSec.peerid.isEmpty()) {
                            if (vgSec.peerid.equals(chUser.peerId)) {
                                if (isinback) {
                                    vgSec.re_background.setVisibility(View.VISIBLE);
                                    vgSec.tv_home.setText(R.string.background);
                                } else {
                                    vgSec.re_background.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                    for (int x = 0; x < stuVideos.size(); x++)
                        if (stuVideos.get(x).peerid.equals(chUser.peerId)) {
                            if (isinback) {
                                stuVideos.get(x).re_background.setVisibility(View.VISIBLE);
                                if (stuVideos.get(x).tv_home != null) {
                                    stuVideos.get(x).tv_home.setText(R.string.background);
                                }
                            } else {
                                stuVideos.get(x).re_background.setVisibility(View.GONE);
                            }
                        }
                }
                break;
            case RoomSession.RemoteMsg:
                boolean add = (boolean) args[0];
                String id1 = (String) args[1];
                String name = (String) args[2];
                long ts = (long) args[3];
                Object data = args[4];
                if (add) {
                    OnRemotePubMsg(id1, name, ts, data);
                }
                break;
            case RoomSession.RoomBreak:
                vgSec.peerid = null;
                break;
            case RoomActivity.GONE_VIDEO:
                if (vgTeacher.sf != null) {
                    vgTeacher.sf.setVisibility(View.GONE);
                }
                if (vgSec.sf != null) {
                    vgSec.sf.setVisibility(View.GONE);
                }
                if (stuVideos != null && stuVideos.size() > 0) {
                    for (int i = 0; i < stuVideos.size(); i++) {
                        stuVideos.get(i).sf.setVisibility(View.GONE);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void OnRemotePubMsg(String id, String name, long ts, Object data) {
        if (name.equals("ClassBegin")) {
            if (!RoomControler.isReleasedBeforeClass()) {
                unPlaySelfAfterClassBegin();
            }
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
            if (RoomManager.getInstance().getMySelf() != null && !RoomSession.isClassBegin) {
                playSelfBeforeClassBegin();
            }
        }
    }
}
