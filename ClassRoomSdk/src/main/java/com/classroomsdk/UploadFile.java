package com.classroomsdk;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.params.ClientPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

//import org.apache.http.entity.mime.content.StringBody;


public class UploadFile {
    public UpLoadFileDelegate delegate;
    public int state = 0;
    private String httpUrl;
    private String remoteFilename;
    private long currentFileId;
    private String uploadingFilePath;
    private String uploadserial;
    private String uploaduserid;
    private String uploadsender;

    RequestParams fileParams = null;
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static PersistentCookieStore myCookieStore = null;
    //	public static volatile Context applicationContext = null;
    private int count = 0;


    public static interface UpLoadFileDelegate {
        public abstract void didFinishUploadingFile(UploadFile operation, String fileName, String result);

        public abstract void didFailedUploadingFile(UploadFile operation, int code);

        public abstract void didChangedUploadProgress(UploadFile operation, float progress);
    }

    public void UploadOperation(String url) {
        httpUrl = url;
//        applicationContext = appContext;
//        myCookieStore = new PersistentCookieStore(applicationContext);
    }


    public void start() {
        if (state != 0) {
            return;
        }
        state = 1;

//        //sam
//    	if(myCookieStore != null)
//    		client.setCookieStore(myCookieStore);

        if (httpUrl != null) {
            startUploadHTTPRequest();
        }
    }

    public void cancel() {

    }

    private void startUploadHTTPRequest() {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        if (state != 1) {
            return;
        }
        if (client != null) {
            try {
                client.post(httpUrl, fileParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                        try {
                            int nRet = response.getInt("result");
                            if (nRet == 0) {
                                Log.e("mxl", "上传成功");
                                WhiteBoradManager.getInstance().onUploadPhotos(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e("mxl", "上传失败error:" + throwable.toString());
                    }
                });
            } catch (Exception e) {
                delegate.didFailedUploadingFile(UploadFile.this, 999);
                return;
            }
        }
    }

    public void packageFile(String path, String strserial, String peerid, String username) {
        count++;
        uploadingFilePath = path;
        this.remoteFilename = path.substring(path.lastIndexOf("/") + 1);
        RequestParams params = new RequestParams();
        try {
            uploadserial = strserial;//MeetingSession.getInstance().getM_strMeetingID();
            uploaduserid = peerid;//MeetingSession.getInstance().getUserMgr().getSelfUser().getPeerID();
            uploadsender = username;//MeetingSession.getInstance().getM_strUserName();
            String fileOldName = path.substring(path.lastIndexOf("/") + 1);
            String fileType = path.substring(path.lastIndexOf(".") + 1);

            File file = new File(path);

            params.put("filedata", file);
            params.put("serial", uploadserial);
            params.put("userid", uploaduserid);
            params.put("sender", uploadsender);
            params.put("conversion", "1");
            params.put("isconversiondone", "0");
            params.put("fileoldname", fileOldName);
            params.put("filename", path);
            params.put("filetype", fileType);
            params.put("alluser", "1");
            params.put("writedb", 1);
            params.put("filenewname", username + "_" + "mobile"+ "_" + fileOldName );

            this.fileParams = params;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath() {
        return uploadingFilePath;
    }

    public String getserial() {
        return uploadserial;
    }


    public String getuserid() {
        return uploaduserid;
    }


    public String getsender() {
        return uploadsender;
    }

    public int getCount() {
        return count;
    }
}
