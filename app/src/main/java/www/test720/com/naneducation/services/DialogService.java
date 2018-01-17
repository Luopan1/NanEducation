package www.test720.com.naneducation.services;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import www.test720.com.naneducation.R;


/**
 * @author LuoPan on 2018/1/12 12:21.
 */

public class DialogService extends Service {
    private long firstInApp = 0;
    private long currentTime;
    private TimerTask mTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        firstInApp = getCurrentTime();
        goGetTime();
    }

    /**
     * 获取打开app的时候  服务创建的时间
     */
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 实时获取当前的时间
     */
    public void goGetTime() {
        Timer mTimer = new Timer("1");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                currentTime = System.currentTimeMillis();
                Log.e("currentTime", currentTime - firstInApp + "");
                if (currentTime - firstInApp > 5000) {
                    Looper.prepare();
                    showRestDialog(getApplicationContext());
                    Looper.loop();
                }
            }
        };
        mTimer.schedule(task, 1000, 1000);
    }

    public static void showRestDialog(Context context) {
        final Dialog restDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pop_rest, null);
        restDialog.setContentView(view);
        restDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        restDialog.show();
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restDialog.dismiss();
            }
        });
    }


}
