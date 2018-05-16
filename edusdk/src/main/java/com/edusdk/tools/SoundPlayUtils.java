package com.edusdk.tools;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.classroomsdk.Config;
import com.edusdk.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.talkcloud.roomsdk.RoomManager;

import java.io.File;


/**
 * Created by Administrator on 2017/11/3.
 */

public class SoundPlayUtils {

    // SoundPool对象
    public static SoundPool mSoundPlayer;
    public static SoundPlayUtils soundPlayUtils;
    // 上下文
    static Context mContext;
    static String MP3File;
    static boolean isOK = false;

    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPlayUtils init(Context context) {
        mContext = context;
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils();
        }
        if (mSoundPlayer == null) {
            mSoundPlayer = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        }
        mSoundPlayer.unload(1);
        if (TextUtils.isEmpty(RoomManager.getInstance().get_MP3Url())) {
            mSoundPlayer.load(mContext, R.raw.trophy_tones, 1);
        } else {
            mSoundPlayer.load(MP3File, 1);
        }
        return soundPlayUtils;
    }

    /**
     * 播放声音
     */
    public static void play() {
        if (mSoundPlayer != null) {
            mSoundPlayer.play(1, 1, 1, 0, 0, 1);
        }
    }

    public static void release() {
        if (soundPlayUtils != null && mSoundPlayer != null) {
            mSoundPlayer.release();
            mSoundPlayer = null;
            soundPlayUtils = null;

            if (MP3File != null) {
                File file = new File(MP3File);
                if (file != null && file.isFile() && file.exists()) {
                    file.delete();
                    Log.e("mxl", "删除文件");
                }
            }
        }
    }

    public static void loadMP3(String host, int port, final Context context) {
        mContext = context;
        MP3File = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cupRingtone" +
                RoomManager.getInstance().get_MP3Url().substring(RoomManager.getInstance().get_MP3Url().lastIndexOf("."));

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡没有使用权限", Toast.LENGTH_SHORT).show();
        }

        File file = new File(MP3File);
        if (file.exists()) {
            file.delete();
            Log.e("mxl", "删除文件");
        }

        if (!TextUtils.isEmpty(RoomManager.getInstance().get_MP3Url()) && !TextUtils.isEmpty(host)) {
            String url = "http://" + host + ":" + port + RoomManager.getInstance().get_MP3Url();
            HttpUtils http = new HttpUtils();
            http.download(url, MP3File, new RequestCallBack<File>() {
                @Override
                public void onFailure(HttpException exception, String msg) {
                    Log.e("mxl", "下载失败error");
                    isOK = false;
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                   /* Log.e("mxl", "下载成功success");*/
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    if (current == total) {
                        isOK = true;
                        init(context);
                        Log.e("mxl", "下载成功success 100%");
                    }
                }
            });
        } else {
            isOK = false;
        }
    }
}
