package com.edusdk.tools;

import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;

/**
 * Created by Administrator on 2017/10/20/020.
 */

public class PermissionTest {


    /**
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Camera mCamera = null;
            try {
                mCamera = Camera.open();
                Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
                mCamera.setParameters(mParameters);
            } catch (Exception e) {
                isCanUse = false;
            }

            if (mCamera != null) {
                try {
                    mCamera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    return isCanUse;
                }
            }
        }else{
            isCanUse = true;
        }
        return isCanUse;
    }


    public static final int STATE_RECORDING = -1;
    public static final int STATE_NO_PERMISSION = -2;
    public static final int STATE_SUCCESS = 1;

    /**
     * 用于检测录音权限是禁用还是允许状态
     *
     * @return 返回1表示权限是允许状态，返回-2表示权限是禁用状态
     * @author ZhuJian
     */
    public static int getRecordState() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            int minBuffer = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, (minBuffer * 100));
            short[] point = new short[minBuffer];
            int readSize = 0;
            try {
                audioRecord.startRecording();//检测是否可以进入初始化状态
            } catch (Exception e) {
                if (audioRecord != null) {
                    audioRecord.release();
                    audioRecord = null;
                }
                return STATE_NO_PERMISSION;
            }
            if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
//6.0以下机型都会返回状态，故使用时需要判断bulid版本
//检测是否在录音中
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                }
                return STATE_RECORDING;
            } else {//检测是否可以获取录音结果
                readSize = audioRecord.read(point, 0, point.length);
                if (readSize <= 0) {
                    if (audioRecord != null) {
                        audioRecord.stop();
                        audioRecord.release();
                        audioRecord = null;
                    }
                    return STATE_NO_PERMISSION;
                } else {
                    if (audioRecord != null) {
                        audioRecord.stop();
                        audioRecord.release();
                        audioRecord = null;
                    }
                    return STATE_SUCCESS;
                }
            }
        }else{
            return STATE_SUCCESS;
        }
    }
}
