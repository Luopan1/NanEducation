package com.edusdk.adapter;

import android.content.Context;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.edusdk.R;
import com.edusdk.message.RoomClient;
import com.talkcloud.roomsdk.RequestServerListCallback;
import com.talkcloud.roomsdk.RoomManager;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Administrator on 2017/11/21.
 */

public class ServerListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<com.talkcloud.roomsdk.Service> services = new ArrayList<>();
    private int selectPostion;

    public ServerListAdapter(Context mContext) {
        this.mContext = mContext;
        RoomManager.getInstance().requestServerList(RoomClient.webServer, new RequestServerListCallback() {
            @Override
            public void callBack(int i, ArrayList<com.talkcloud.roomsdk.Service> arrayList) {
                if (i == 0) {
                    services = arrayList;
                }
            }
        });
    }

    public int getSelectPostion() {
        return selectPostion;
    }

    public void setSelectPostion(int selectPostion) {
        if (services.get(selectPostion) != null) {
            RoomManager.getInstance().changeDefaultServer(services.get(selectPostion).getServiceName());
        }
        this.selectPostion = selectPostion;
    }

    public String getSelectServerName() {
        return services.get(selectPostion).getServiceName();
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
            convertView = _LayoutInflater.inflate(R.layout.server_item, null);
            holder.txt_country_name = (TextView) convertView.findViewById(R.id.txt_country_name);
            holder.select = (RadioButton) convertView.findViewById(R.id.id_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (services.size() > 0) {
            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0);
            } else {
                locale = Locale.getDefault();
            }
            String language = locale.getLanguage() + "-" + locale.getCountry();

            String lan = null;
            if (locale.equals(Locale.TAIWAN)) {
                lan = "tw";
            } else if (locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                lan = "ch";
            } else if (locale.equals(Locale.ENGLISH) || locale.toString().equals("en_US".toString()) || locale.toString().startsWith("en")) {
                lan = "en";
            }
            if (TextUtils.isEmpty(lan)) {
                if (locale.toString().endsWith("#Hant")) {
                    lan = "tw";
                }
                if (locale.toString().endsWith("#Hans")) {
                    lan = "ch";
                }
            }
            String temp = "";
            if (lan.equals("ch")) {
                temp = services.get(position).getChinesedesc();
            } else {
                temp = services.get(position).getEnglishdesc();
            }
            if (services.get(position).isDefault()) {
                temp = temp + mContext.getString(R.string.country_default);
            }
            holder.txt_country_name.setText(temp);
            if (services.get(position).isDefault()) {
                setSelectPostion(position);
                holder.select.setChecked(true);
            } else {
                holder.select.setChecked(false);
            }

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectPostion(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        RoomManager.getInstance().requestServerList(RoomClient.webServer, new RequestServerListCallback() {
            @Override
            public void callBack(int i, ArrayList<com.talkcloud.roomsdk.Service> arrayList) {
                if (i == 0) {
                    services = arrayList;
                }
            }
        });
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        RoomManager.getInstance().requestServerList(RoomClient.webServer, new RequestServerListCallback() {
            @Override
            public void callBack(int i, ArrayList<com.talkcloud.roomsdk.Service> arrayList) {
                if (i == 0) {
                    services = arrayList;
                }
            }
        });
        super.notifyDataSetInvalidated();
    }

    class ViewHolder {
        TextView txt_country_name;
        RadioButton select;
    }
}
