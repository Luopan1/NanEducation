package com.classroomsdk;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.talkcloud.roomsdk.RoomManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/5/19.
 */

public class WhiteBoradManager {
    private static String sync = "";
    private WBStateCallBack callBack;
    private LocalControl control;
    static private WhiteBoradManager mInstance = null;
    public ShareDoc mBlankShareDoc = new ShareDoc();
    private static AsyncHttpClient client = new AsyncHttpClient();
    private ShareDoc currentMediaDoc;
    private ShareDoc currentFileDoc;
    private ShareDoc defaultFileDoc;
    private String fileServierUrl = "";
    private int fileServierPort = 80;
    private ConcurrentHashMap<Long, ShareDoc> docMap = new ConcurrentHashMap<Long, ShareDoc>();
    private ConcurrentHashMap<Long, ShareDoc> mediaMap = new ConcurrentHashMap<Long, ShareDoc>();
    private ArrayList<ShareDoc> docList = new ArrayList<ShareDoc>();
    private ArrayList<ShareDoc> mediaList = new ArrayList<ShareDoc>();
    private int userrole = -1;

    public ShareDoc getDefaultFileDoc() {
        return defaultFileDoc;
    }

    public void setUserrole(int userrole) {
        this.userrole = userrole;
    }

    public void setLocalControl(LocalControl control) {
        this.control = control;
    }

    private boolean isdeling = false;

    public ArrayList<ShareDoc> getDocList() {
        docList.clear();
        //        docList.add(mBlankShareDoc);
        for (ShareDoc d : docMap.values()) {
            if (d.getFileid() == 0) {
                docList.add(0, d);
            } else {
                docList.add(d);
            }
        }
        Collections.sort(docList);
        return docList;
    }

    public ArrayList<ShareDoc> getMediaList() {
        mediaList.clear();
        for (ShareDoc d : mediaMap.values()) {
            mediaList.add(d);
        }
        Collections.sort(mediaList);
        return mediaList;
    }

    public void addDocList(ShareDoc doc) {
        if (doc.isMedia()) {
            //            mediaList.add(doc);
            mediaMap.put(doc.getFileid(), doc);
        } else {
            if (doc.getType() == 1) {
                defaultFileDoc = doc.clone();
            }
            docMap.put(doc.getFileid(), doc);
            //            docList.add(doc);
        }

    }

    public ShareDoc getCurrentMediaDoc() {
        if (currentMediaDoc == null) {
            return currentMediaDoc = new ShareDoc();
        }
        return currentMediaDoc;
    }

    public String getFileServierUrl() {
        return fileServierUrl;
    }

    public void setFileServierUrl(String fileServierUrl) {
        this.fileServierUrl = fileServierUrl;
    }

    public int getFileServierPort() {
        return fileServierPort;
    }

    public void setFileServierPort(int fileServierPort) {
        this.fileServierPort = fileServierPort;
    }

    public ShareDoc getCurrentFileDoc() {
        if (currentFileDoc == null) {
            return currentFileDoc = new ShareDoc();
        }
        return currentFileDoc;
    }

    public void setCurrentFileDoc(ShareDoc doc) {
        this.currentFileDoc = doc.clone();
    }

    public void setCurrentMediaDoc(ShareDoc doc) {
        this.currentMediaDoc = doc;
    }

    private WhiteBoradManager() {
        mBlankShareDoc.setFileid(0);
        mBlankShareDoc.setCurrentPage(1);
        mBlankShareDoc.setPagenum(1);
        mBlankShareDoc.setFilename("白板");
        mBlankShareDoc.setFiletype("whiteboard");
        mBlankShareDoc.setSwfpath("");
        mBlankShareDoc.setPptslide(1);
        mBlankShareDoc.setPptstep(0);
        mBlankShareDoc.setSteptotal(0);
        mBlankShareDoc.setGeneralFile(true);
        mBlankShareDoc.setDynamicPPT(false);
        mBlankShareDoc.setH5Docment(false);
        mBlankShareDoc.setMedia(false);
        docMap.put(mBlankShareDoc.getFileid(), mBlankShareDoc);

    }


    static public WhiteBoradManager getInstance() {
        synchronized (sync) {
            if (mInstance == null) {
                mInstance = new WhiteBoradManager();
            }
            return mInstance;
        }
    }

    public void clear() {
        currentMediaDoc = null;
        currentFileDoc = null;
        mediaList.clear();
        docList.clear();
        docMap.clear();
        mediaMap.clear();
        mInstance = null;
    }

    public void setWBCallBack(WBStateCallBack wbCallBack) {
        this.callBack = wbCallBack;
    }

    public void onPageFinished() {
        if (callBack != null) {
            callBack.onPageFinished();
        }
    }

    public void fullScreenToLc(boolean isFull) {
        if (callBack != null) {
            callBack.onWhiteBoradZoom(isFull);
        }
    }

    public void onWBMediaPublish(String url, boolean isvideo, long fileid) {
        if (callBack != null) {
            callBack.onWhiteBoradMediaPublish(url, isvideo, fileid);
        }
    }


    public void onRoomFileChange(ShareDoc sdoc, boolean isdel, boolean islocal, boolean isClassBegin) {
        ShareDoc doc = null;
        if (sdoc.isMedia()) {
            doc = mediaMap.get(sdoc.getFileid());
        } else {
            doc = docMap.get(sdoc.getFileid());
        }
        if (doc == null) {
            doc = sdoc;
            if (doc.isMedia()) {
                mediaMap.put(doc.getFileid(), doc);
            } else {
                docMap.put(doc.getFileid(), doc);
            }
        } else if (islocal) {
            JSONObject data = Packager.pageSendData(doc);
            try {
                data.put("isDel", isdel);
                RoomManager.getInstance().pubMsg("DocumentChange", "DocumentChange", "__allExceptSender", data.toString(), false, null, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (doc != null) {
            if (isdel) {
                if (!doc.isMedia()) {
                    if (docMap.containsKey(doc.getFileid())) {
                        if (doc.getFileid() == currentFileDoc.getFileid()) {
                            getNextDoc(doc.getFileid(), doc.isMedia());
                            if (!doc.isMedia()) {
                                localChangeDoc(currentFileDoc);
                            }
                            if (islocal && !doc.isMedia()) {
                                if (isClassBegin) {
                                    JSONObject data = Packager.pageSendData(currentFileDoc);
                                    RoomManager.getInstance().pubMsg("ShowPage", "DocumentFilePage_ShowPage", "__all", data.toString(), true, null, null);
                                }
                            }
                        } else {
                            getNextDoc(doc.getFileid(), doc.isMedia());
                            if (islocal && !doc.isMedia()) {
                                if (isClassBegin) {
                                    JSONObject data = Packager.pageSendData(currentFileDoc);
                                    RoomManager.getInstance().pubMsg("ShowPage", "DocumentFilePage_ShowPage", "__all", data.toString(), true, null, null);
                                }
                            }
                        }
                    }
                } else {
                    if (mediaMap.containsKey(doc.getFileid())) {
                        getNextDoc(doc.getFileid(), doc.isMedia());
                    }
                }
            } else {
                if (isClassBegin) {
                    currentFileDoc = sdoc;
                    if (!doc.isMedia()) {
                        localChangeDoc(currentFileDoc);
                    }
                }
                docList.add(sdoc);
            }
        } else {

        }
        if (callBack != null) {
            callBack.onRoomDocChange();
            isdeling = false;
        }

    }

    private int getIndexByDocid(long docid, boolean ismedia) {
        if (!ismedia) {
            for (int i = 0; i < docList.size(); i++) {
                ShareDoc dc = docList.get(i);
                if (dc.getFileid() == docid) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < mediaList.size(); i++) {
                ShareDoc dc = mediaList.get(i);
                if (dc.getFileid() == docid) {
                    return i;
                }
            }
        }

        return -1;
    }

    public void getNextDoc(long docid, boolean ismedia) {
        int removeIndex = getIndexByDocid(docid, ismedia);
        if (!ismedia) {
            ShareDoc sc = docMap.get(docid);
            docList.remove(sc);
            docMap.remove(docid);

            int size = docList.size();
            if (removeIndex != -1 && removeIndex <= size && currentFileDoc.getFileid() == docid) {
                if (size == removeIndex) {

                    if (docList.size() > 0)
                        currentFileDoc = docList.get(docList.size() - 1);
                } else {

                    currentFileDoc = docList.get(removeIndex);
                }
                if (RoomManager.getInstance().getMySelf().role == 2) {
                    currentFileDoc = WhiteBoradManager.getInstance().getDocList().get(0);
                }
            }

        } else {
            ShareDoc sc = mediaMap.get(docid);
            mediaList.remove(sc);
            mediaMap.remove(docid);
        }
    }


    /**
     * ɾ�������ļ�
     *
     * @param roomID
     * @param docid
     */
    public void delRoomFile(final String roomID, final long docid, final boolean isMedia, final boolean isClassBegin) {
        //        if(isdeling){
        //            return;
        //        }
        isdeling = true;
        String url = "http://" + fileServierUrl + ":" + fileServierPort + "/ClientAPI/" + "delroomfile";

        RequestParams params = new RequestParams();
        params.put("serial", roomID + "");
        params.put("fileid", docid + "");

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    int nRet = response.getInt("result");
                    ShareDoc doc = new ShareDoc();
                    doc.setFileid(docid);
                    doc.setMedia(isMedia);
                    onRoomFileChange(doc, true, true, isClassBegin);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("emm", "error");
            }
        });

    }

    /**
     * ɾ�������ļ�
     *
     * @param roomID
     */
    public void uploadRoomFile(final String roomID, final String path) {
        //        if(isdeling){
        //            return;
        //        }
        isdeling = true;
        String url = "http://" + fileServierUrl + ":" + fileServierPort + "/ClientAPI/" + "uploaddocument";

        UploadFile uf = new UploadFile();
        uf.UploadOperation(url);
        uf.packageFile(path, roomID + "", RoomManager.getInstance().getMySelf().peerId, RoomManager.getInstance().getMySelf().nickName);
        uf.start();
    }

    public void localChangeDoc(ShareDoc doc) {
        setCurrentFileDoc(doc);
        if (control != null) {
            control.localChangeDoc();
        }
    }

    public void playbackPlayAndPauseController(boolean isplay) {
        if (control != null) {
            control.playbackPlayAndPauseController(isplay);
        }
    }

    public void resumeFileList() {
        for (int i = 0; i < docList.size(); i++) {
            ShareDoc dc = docList.get(i);
            dc.setCurrentPage(1);
            dc.setPptstep(0);
            dc.setSteptotal(0);
            dc.setPptslide(1);
            if (dc.getFileid() == 0) {
                dc.setPagenum(1);
            }
        }
    }

    public void onUploadPhotos(JSONObject response) {
        ShareDoc docPhoto = null;
        JSONObject data = null;
        try {
            docPhoto = new ShareDoc();
            docPhoto.setSwfpath(response.getString("swfpath"));
            docPhoto.setPagenum(response.getInt("pagenum"));
            docPhoto.setFileid(response.getLong("fileid"));
            docPhoto.setDownloadpath(response.getString("downloadpath"));
            docPhoto.setSize(response.getLong("size"));
            docPhoto.setStatus(response.getInt("status"));
            docPhoto.setFilename(response.getString("filename"));
            docPhoto.setFileprop(response.getInt("fileprop"));
            docPhoto.setDynamicPPT(false);
            docPhoto.setGeneralFile(true);
            docPhoto.setH5Docment(false);

            String fileType = response.getString("filename").substring(response.getString("filename").lastIndexOf(".") + 1);
            if (!TextUtils.isEmpty(fileType)) {
                docPhoto.setFiletype(fileType);
            } else {
                docPhoto.setFiletype("jpg");
            }

            data = Packager.pageSendData(docPhoto);
            data.put("isDel", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onRoomFileChange(docPhoto, false, true, true);
        RoomManager.getInstance().pubMsg("DocumentChange", "DocumentChange", "__allExceptSender", data.toString(), false, null, null);
        RoomManager.getInstance().pubMsg("ShowPage", "DocumentFilePage_ShowPage", "__all", data.toString(), true, null, null);
    }
}
