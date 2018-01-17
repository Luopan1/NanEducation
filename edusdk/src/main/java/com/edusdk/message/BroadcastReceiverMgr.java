package com.edusdk.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.talkcloud.roomsdk.RoomManager;

/**
 * Created by Administrator on 2017/9/13.
 */

public class BroadcastReceiverMgr extends BroadcastReceiver {

    private final String TAG = "classroom";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "[Broadcast]" + action);

        //呼入电话
        if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            Log.i(TAG, "[Broadcast]PHONE_STATE");
            doReceivePhone(context, intent);
        }
    }

    /**
     * 处理电话广播.
     *
     * @param context
     * @param intent
     */
    public void doReceivePhone(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(
                TelephonyManager.EXTRA_INCOMING_NUMBER);
        TelephonyManager telephony =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int state = telephony.getCallState();
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(TAG, "[Broadcast]等待接电话=" + phoneNumber);
                RoomManager.getInstance().enableSendMyVoice(false);
                RoomManager.getInstance().enableOtherAudio(true);
                RoomManager.getInstance().setMuteAllStream(true);
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(TAG, "[Broadcast]电话挂断=" + phoneNumber);
                if (RoomManager.getInstance().getMySelf() != null) {
                    if (RoomManager.getInstance().getMySelf().publishState == 1 || RoomManager.getInstance().getMySelf().publishState == 3) {
                        RoomManager.getInstance().enableSendMyVoice(true);
                    }
                }
                RoomManager.getInstance().enableOtherAudio(false);
                RoomManager.getInstance().setMuteAllStream(false);
                RoomManager.getInstance().useLoudSpeaker(true);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(TAG, "[Broadcast]通话中=" + phoneNumber);
                RoomManager.getInstance().enableSendMyVoice(false);
                RoomManager.getInstance().enableOtherAudio(true);
                RoomManager.getInstance().setMuteAllStream(true);
                break;
        }
    }
}