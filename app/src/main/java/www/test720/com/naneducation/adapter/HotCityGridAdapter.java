package www.test720.com.naneducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.bean.NetworkCityBean;

/**
 * 热门城市的adapter
 * Created by Administrator on 2017/3/22.
 */

public class HotCityGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mCities;

    public HotCityGridAdapter(Context context, List<NetworkCityBean> networkHotCityBeen) {
        this.mContext = context;
        mCities = new ArrayList<>();
        for (int i = 0; i < networkHotCityBeen.size(); i++) {
            mCities.add(networkHotCityBeen.get(i).getArea_name());
        }
        mCities.add("成都");
        mCities.add("重庆");
        mCities.add("北京");
        mCities.add("上海");
        mCities.add("广州");
        mCities.add("深圳");
        mCities.add("杭州");
        mCities.add("南京");
        mCities.add("武汉");
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public String getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position));
        return view;
    }

    public static class HotCityViewHolder {
        TextView name;
    }
}
