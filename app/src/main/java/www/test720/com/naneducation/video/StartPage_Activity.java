package www.test720.com.naneducation.video;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edusdk.interfaces.JoinmeetingCallBack;
import com.edusdk.interfaces.MeetingNotify;
import com.edusdk.message.RoomClient;
import com.edusdk.tools.Tools;
import com.talkcloud.roomsdk.RoomManager;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.Strings;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.UpdateManagerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.test720.com.naneducation.R;


public class StartPage_Activity extends Activity implements OnClickListener, JoinmeetingCallBack, MeetingNotify {
    private EditText edt_meetingid;
    private EditText edt_nickname;
    //	private EditText edt_domain;
    private Button txt_joinmeeting;
    private TextView txt_version;
    private String meetingid;
    private String domain;
    Handler applicationHandler;
    String strip;
    String strport;
    String strnickname;
    Map<String, Object> map = null;
    private final int REQUEST_CODED = 4;

    private static String HTTP = "http://";
    private static String WEBFUNC_CHECKroom = "/ClientAPI/checkroom";

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page_);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        spinner = (Spinner) findViewById(R.id.spinner);

        //数据
        data_list = new ArrayList<>();
        data_list.add(getString(R.string.student));
        data_list.add(getString(R.string.lass_patrol));

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        txt_version = (TextView) findViewById(R.id.txt_version_num);


        applicationHandler = new Handler(this.getMainLooper());
        edt_meetingid = (EditText) findViewById(R.id.edt_meetingid);
        edt_nickname = (EditText) findViewById(R.id.edt_nickname);
        //		edt_domain = (EditText) findViewById(R.id.edt_domain);
        txt_joinmeeting = (Button) findViewById(R.id.txt_joinmeeting);
        txt_joinmeeting.setOnClickListener(this);
        RoomClient.getInstance().regiestInterface(this, this);
        checkForCrashes();
        boolean wg = false;
        boolean rg = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] pers = null;
            if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pers = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                }
            } else {
                wg = true;
            }
            if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (!wg) {
                        pers = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                    } else {
                        pers = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                    }
                }
            } else {
                rg = true;
            }
            if (rg && wg) {
                checkForUpdates();
            } else {
                requestPermissions(pers, REQUEST_CODED);
            }
        } else {
            checkForUpdates();
        }
        handleIntentemm(getIntent());
        String str_version = getVersion();
        txt_version.setText(str_version);
    }

    private void checkForCrashes() {
        CrashManagerListener listener = new CrashManagerListener() {

            public String getStringForResource(int resourceID) {
                switch (resourceID) {
                    case Strings.CRASH_DIALOG_MESSAGE_ID:
                        return getResources().getString(
                                R.string.crash_dialog_message);
                    case Strings.CRASH_DIALOG_NEGATIVE_BUTTON_ID:
                        return getResources().getString(
                                R.string.crash_dialog_negative_button);
                    case Strings.CRASH_DIALOG_POSITIVE_BUTTON_ID:
                        return getResources().getString(
                                R.string.crash_dialog_positive_button);
                    case Strings.CRASH_DIALOG_TITLE_ID:
                        return getResources()
                                .getString(R.string.crash_dialog_title);
                    default:
                        return null;
                }
            }

            @Override
            public boolean shouldAutoUploadCrashes() {
                // TODO Auto-generated method stub
                return false;
            }

        };

        CrashManager.register(this, "http://global.talk-cloud.com/update/public/",
                BuildVars.HOCKEY_APP_HASH, listener);


    }

    @Override
    protected void onResume() {
        //		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onResume();
    }

    private void checkForUpdates() {
        // if (BuildVars.DEBUG_VERSION)
        {
            UpdateManagerListener listener = new UpdateManagerListener() {
                public String getStringForResource(int resourceID) {
                    switch (resourceID) {
                        case Strings.UPDATE_MANDATORY_TOAST_ID:
                            return getResources().getString(
                                    R.string.update_mandatory_toast);
                        case Strings.UPDATE_DIALOG_TITLE_ID:
                            return getResources().getString(
                                    R.string.update_dialog_title);
                        case Strings.UPDATE_DIALOG_MESSAGE_ID:
                            return getResources().getString(
                                    R.string.update_dialog_message);
                        case Strings.UPDATE_DIALOG_NEGATIVE_BUTTON_ID:
                            return getResources().getString(
                                    R.string.update_dialog_negative_button);
                        case Strings.UPDATE_DIALOG_POSITIVE_BUTTON_ID:
                            return getResources().getString(
                                    R.string.update_dialog_positive_button);
                        case Strings.DOWNLOAD_FAILED_DIALOG_TITLE_ID:
                            return getResources().getString(
                                    R.string.download_failed_dialog_title);
                        case Strings.DOWNLOAD_FAILED_DIALOG_MESSAGE_ID:
                            return getResources().getString(
                                    R.string.download_failed_dialog_message);
                        case Strings.DOWNLOAD_FAILED_DIALOG_NEGATIVE_BUTTON_ID:
                            return getResources()
                                    .getString(
                                            R.string.download_failed_dialog_negative_button);
                        case Strings.DOWNLOAD_FAILED_DIALOG_POSITIVE_BUTTON_ID:
                            return getResources()
                                    .getString(
                                            R.string.download_failed_dialog_positive_button);
                        case Strings.UPDATE_VIEW_UPDATE_BUTTON_ID:
                            return getResources().getString(
                                    R.string.update_view_update_button);
                        default:
                            return null;
                    }
                }
            };
            UpdateManager.register(this, "http://global.talk-cloud.com/update/public/", BuildVars.HOCKEY_APP_HASH, listener);
        }
    }

    @Override
    public void onClick(View v) {
        int nid = v.getId();
        if (nid == R.id.txt_joinmeeting) {
            if (!checkEmpty()) {
                txt_joinmeeting.setClickable(false);
                strnickname = edt_nickname.getText().toString().trim();
                meetingid = edt_meetingid.getText().toString().trim();
                //				domain = edt_domain.getText().toString().trim();


                map = new HashMap<String, Object>();
                String type = spinner.getSelectedItem().toString();
                if (type.equals(getString(R.string.student))) {
                    map.put("userrole", 2); //
                } else {
                    map.put("userrole", 4);
                }
                //                map.put("host", "192.168.1.17"); // 内网地址
                map.put("host", "global.talk-cloud.net"); //公网地址
                map.put("port", 80);  //端口
                map.put("serial", meetingid); //课堂号
                map.put("nickname", strnickname); // 昵称
                RoomClient.getInstance().joinRoom(StartPage_Activity.this, map);
            }
        }
    }

    private boolean checkEmpty() {
        boolean isEmpty = false;
        if (edt_nickname.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(this, getString(R.string.nickname_not_empty));
            //			Toast.makeText(this, getString(R.string.nickname_not_empty), Toast.LENGTH_LONG).show();
            isEmpty = true;
        }
        if (edt_meetingid.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(this, getString(R.string.classroomnumber_not_empty));
            //			Toast.makeText(this, getString(R.string.classroomnumber_not_empty), Toast.LENGTH_LONG).show();
            isEmpty = true;
        }
        return isEmpty;
    }

    public void inputMeetingPassward(final Activity activity, int nTipID,
                                     final String mid) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.meeting_password, null);
        final EditText etpsd = (EditText) view.findViewById(R.id.et_psd);

        builder.setPositiveButton(getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        String meetingpwd = etpsd.getText().toString();
                        if (!TextUtils.isEmpty(meetingpwd)) {
                            map.put("password", meetingpwd);
                        }
                        RoomClient.getInstance().joinRoom(StartPage_Activity.this, map);
                    }
                });
        builder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });
        AlertDialog adlg = builder.create();
        adlg.setView(view);
        adlg.setTitle(getString(nTipID));
        adlg.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface arg0) {
                if (etpsd == null) {
                    return;
                }
                etpsd.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) etpsd
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etpsd,
                        InputMethodManager.SHOW_IMPLICIT);

                ((InputMethodManager) etpsd.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).showSoftInput(etpsd, 0);
            }
        });
        adlg.show();
        adlg.setCanceledOnTouchOutside(false);
    }

    public void errorTipDialog(final Activity activity, int errorTipID) {

        AlertDialog.Builder build = new AlertDialog.Builder(activity);
        build.setTitle(getString(R.string.link_tip));
        build.setMessage(getString(errorTipID));
        build.setPositiveButton(getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }

                });
        build.show();
    }

    @Override
    public void callBack(int nRet) {
        Tools.HideProgressDialog();
        if (nRet == 0) {
            //
        } else if (nRet == 100) {
            //				Intent intent = new Intent(StartPage_Activity.this,
            //						RoomFragment.class);
            //				startActivity(intent);
        } else if (nRet == 101) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_5005);
        } else if (nRet == 4008) {
            inputMeetingPassward(this,
                    R.string.checkmeeting_error_4008, meetingid);
        } else if (nRet == 4110) {
            inputMeetingPassward(this,
                    R.string.checkmeeting_error_4110, meetingid);
        } else if (nRet == 4007) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_4007);
        } else if (nRet == 3001) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_3001);
        } else if (nRet == 3002) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_3002);
        } else if (nRet == 3003) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_3003);
        } else if (nRet == 4109) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_4109);
        } else if (nRet == 4103) {
            errorTipDialog(this,
                    R.string.checkmeeting_error_4103);
        } else if (nRet == 5006) {
            errorTipDialog(StartPage_Activity.this, R.string.checkroom_error_5006);
        } else if (nRet == 4012) {
            inputMeetingPassward(this,
                    R.string.checkmeeting_error_4110, meetingid);
        } else {
            errorTipDialog(this,
                    R.string.WaitingForNetwork);
        }
        txt_joinmeeting.setClickable(true);
    }

    @Override
    public void onKickOut(int res) {
        if (res == RoomClient.Kickout_Repeat) {
            Toast.makeText(this, getString(R.string.kick_out_tip), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWarning(int code) {
        if (code == 1) {
            //			showExitDialog("视频打开失败请前往设置设置权限");
        }
        if (code == 2) {
            //			showExitDialog("视频打开失败请前往设置设置权限");
        }
    }

    @Override
    public void onClassBegin() {
        Toast.makeText(this, getString(R.string.class_started), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onClassDismiss() {
        Toast.makeText(this, getString(R.string.class_closeed), Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean wg = false;
        boolean rg = false;
        if (grantResults.length == 0 || permissions.length == 0) {
            return;
        }

        for (int i = 0; i < permissions.length; i++) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])) {
                int grantResult = grantResults[0];
                wg = grantResult == PackageManager.PERMISSION_GRANTED;

            }
            if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permissions[i])) {
                int grantResult = grantResults[0];
                rg = grantResult == PackageManager.PERMISSION_GRANTED;

            }
        }
        if (wg && rg) {
            checkForUpdates();
        }

    }

    private boolean handleIntentemm(Intent intent) {
        if (RoomManager.getInstance().getRoomStatus() != 0 && RoomManager.getInstance().getRoomStatus() != 6) {
            return false;
        }
        Log.d("emm", intent.toString());
        boolean bfrom_title = false;
        if (intent == null) {
            Log.i("rebuild", "intent null");
        } else if (intent.getAction() != null) {
            Log.i("rebuild", intent.getAction());
        }
        if (intent != null) {
            Log.e("emm", "joinmeetingbyurl*******************");
            //			UserConfig.logout();
            Uri uri = intent.getData();
            if (uri != null) {
                String url = uri.toString();
                if (url.startsWith("enterroom://")) {
                    String temp = url.substring(url.indexOf("?") + 1);
                    String[] temps = temp.split("&");
                    Map<String, Object> tempMap = new HashMap<String, Object>();
                    for (int i = 0; i < temps.length; i++) {
                        String[] t = temps[i].split("=");
                        tempMap.put(t[0], t[1]);
                    }
                    if (tempMap.containsKey("path")) {
                        String tempPath = "http://" + tempMap.get("path");
                        tempMap.put("path", tempPath);
                        RoomClient.getInstance().joinPlayBackRoom(StartPage_Activity.this, temp);
                    } else {
                        tempMap.put("port", 80);
                        RoomClient.getInstance().joinRoom(StartPage_Activity.this, tempMap);
                    }
                }
            } else {

            }
            //			return true;
        }
        return false;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntentemm(intent);
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "v:" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
