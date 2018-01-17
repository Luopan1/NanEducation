package www.test720.com.naneducation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.bean.AllCourse;

public class AllCourseAdapter extends BaseAdapter {

    private Context context;
    private List<AllCourse.DataBean.MuenBean> list;
    private int position = 0;
    private boolean islodingimg = true;
    Holder hold;

    public AllCourseAdapter(Context context, List<AllCourse.DataBean.MuenBean> list) {
        this.context = context;
        this.list = list;
    }

    public AllCourseAdapter(Context context, List<AllCourse.DataBean.MuenBean> list, boolean islodingimg) {
        this.context = context;
        this.list = list;
        this.islodingimg = islodingimg;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int arg0, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_classify_mainlist, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        if (islodingimg == true) {

        }
        hold.txt.setText(list.get(arg0).getName());
        hold.layout.setBackgroundColor(context.getResources().getColor(R.color.system_color));
        if (arg0 == position) {
            hold.layout.setBackgroundColor(context.getResources().getColor(R.color.white));

        } else {
            hold.layout.setBackgroundColor(context.getResources().getColor(R.color.system_color));

        }
        return view;
    }

    public void setSelectItem(int position) {
        this.position = position;
    }

    public int getSelectItem() {
        return position;
    }

    private static class Holder {
        LinearLayout layout;
        ImageView img;
        TextView txt;

        public Holder(View view) {
            txt = (TextView) view.findViewById(R.id.mainitem_txt);
            img = (ImageView) view.findViewById(R.id.mainitem_img);
            layout = (LinearLayout) view.findViewById(R.id.mainitem_layout);
        }
    }
}
