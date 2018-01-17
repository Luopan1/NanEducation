package com.classroomsdk;

/**
 * Created by Administrator on 2017/5/11.
 */

public interface WBCallback {

    void pubMsg(String js);

    void delMsg(String js);

    void onPageFinished();

    void changeWebPageFullScreen(boolean isFull);

    void onJsPlay(String url, boolean isvideo, long fileid);

}
