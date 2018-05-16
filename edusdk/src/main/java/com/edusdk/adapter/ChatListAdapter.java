package com.edusdk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edusdk.tools.KeyBoardUtil;
import com.edusdk.tools.Tools;
import com.talkcloud.roomsdk.RoomManager;
import com.talkcloud.roomsdk.RoomUser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.edusdk.R;
import com.edusdk.entity.ChatData;


/**
 * Created by Administrator on 2017/4/28.
 */

public class ChatListAdapter extends BaseAdapter {
    private ArrayList<ChatData> chatlist;
    private Context context;

    public ChatListAdapter(ArrayList<ChatData> chatlist, Context context) {
        this.chatlist = chatlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatlist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_list_item, null);
            viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.txt_message = (TextView) convertView.findViewById(R.id.txt_message);
            viewHolder.lin_background = (LinearLayout) convertView.findViewById(R.id.chat_background);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (chatlist.size() > 0) {
            SpannableStringBuilder sb = getFace(chatlist.get(position).getMessage());
            viewHolder.txt_message.setText(sb);

            RoomUser roomUser = RoomManager.getInstance().getUser(chatlist.get(position).getPeerid());
            if (roomUser != null) {
//                roomUser.nickName = StringEscapeUtils.unescapeHtml4(roomUser.nickName);
                String username = roomUser.nickName;
                String tempname = null;
                try {
                    tempname = Tools.getWordCount(username) > 10 ? Tools.bSubstring(username, 8) + "..." : username;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (roomUser.role == 0) {
                    viewHolder.txt_name.setTextColor(context.getResources().getColor(R.color.red));
                    //viewHolder.txt_name.setText(tempname + "(" + context.getResources().getString(R.string.teacher) + ")" + ":");
                    viewHolder.txt_name.setText(tempname + ":");

                } else {
                    viewHolder.txt_name.setTextColor(context.getResources().getColor(R.color.yellow));
                    if (roomUser.peerId.equals(RoomManager.getInstance().getMySelf().peerId)) {
                        //viewHolder.txt_name.setText(tempname + "(" + context.getResources().getString(R.string.me) + ")" + ":");
                        viewHolder.txt_name.setText(tempname + ":");
                    } else {
                        viewHolder.txt_name.setText(tempname + ":");
                    }
                }
            } else {
                if (chatlist.get(position).getRole() != 0) {
                    viewHolder.txt_name.setTextColor(context.getResources().getColor(R.color.yellow));
                    //viewHolder.txt_name.setText(chatlist.get(position).getNickName() + "(" + context.getResources().getString(R.string.teacher) + ")" + ":");
                    viewHolder.txt_name.setText(chatlist.get(position).getNickName() + ":");
                } else {
                    viewHolder.txt_name.setTextColor(context.getResources().getColor(R.color.red));
                    viewHolder.txt_name.setText(chatlist.get(position).getNickName() + ":");
                }
            }
        }
        return convertView;
    }

    private SpannableStringBuilder getFace(String content) {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "(\\[em_)\\d{1}(\\])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String tempText = m.group();
            try {
                String png = tempText.substring("[".length(), tempText.length() - "]".length()) + ".png";
                Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("face/" + png));
                Drawable drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, KeyBoardUtil.dp2px(context,16), KeyBoardUtil.dp2px(context,16));
                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              /*  sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open("face/" + png))),
                        m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    class ViewHolder {
        TextView txt_name;
        TextView txt_message;
        LinearLayout lin_background;
    }
}
