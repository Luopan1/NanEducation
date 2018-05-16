package com.classroomsdk;

/**
 * Created by Administrator on 2017/5/9.
 */

public interface WBStateCallBack {
    void onPageFinished();
    void onRoomDocChange();
    void onWhiteBoradZoom(boolean isZoom);
    void onWhiteBoradMediaPublish(String url, boolean isvideo, long fileid);
}
