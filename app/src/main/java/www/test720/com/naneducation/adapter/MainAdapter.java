package www.test720.com.naneducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.bean.LiveBroad;

/**
 * Created by LuoPan on 2017/11/14.
 */

public class MainAdapter extends BaseAdapter {
    List<LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean> mLists;
    Context mContext;
    ViewHolder hold;

    public MainAdapter(List<LiveBroad.DataBean.TypeListBean.ZiBean.ZidBean> lists, Context context) {
        mLists = lists;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_classfiaction_item, null);
            hold = new ViewHolder(convertView);
            convertView.setTag(hold);
        } else {
            hold = (ViewHolder) convertView.getTag();
        }

        hold.tv.setText(mLists.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView tv;

        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.courseKind);
        }
    }
}
