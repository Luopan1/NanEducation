package www.test720.com.naneducation.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.bean.City;
import www.test720.com.naneducation.utils.PinyinUtils;

/**
 * @author LuoPan on 2017/10/26 15:50.
 */

public class CitySearchResultAdapter extends BaseAdapter {
    public Activity mActivity;
    public List<City> mCities;

    public CitySearchResultAdapter(Activity activity, List<City> cityList) {
        mActivity = activity;
        mCities = cityList;
    }

    @Override
    public int getCount() {
        return mCities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CityViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.item_city_listview, parent, false);
            holder = new CityViewHolder();
            holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
            holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
            view.setTag(holder);
        } else {
            holder = (CityViewHolder) view.getTag();
        }

        final String city = mCities.get(position).getName();

        holder.name.setText(city);
        String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
        String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
        if (!TextUtils.equals(currentLetter, previousLetter)) {
            holder.letter.setVisibility(View.VISIBLE);
            holder.letter.setText(currentLetter);
        } else {
            holder.letter.setVisibility(View.GONE);
        }
        return view;
    }

    private static class CityViewHolder {
        TextView letter;
        TextView name;
    }
}

