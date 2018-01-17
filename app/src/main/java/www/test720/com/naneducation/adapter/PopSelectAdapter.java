package www.test720.com.naneducation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import www.test720.com.naneducation.R;
import www.test720.com.naneducation.bean.Grade;
import www.test720.com.naneducation.bean.StudyHomeBanner;

public class PopSelectAdapter extends BaseAdapter {

    private Context context;
    private List<Grade> list;
    private int position = 0;
    private boolean islodingimg = true;
    Holder hold;

    public PopSelectAdapter(Context context, List<Grade> list) {
        this.context = context;
        this.list = list;
    }

    public PopSelectAdapter(Context context, List<Grade> list, boolean islodingimg) {
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
            view = View.inflate(context, R.layout.item_selct_mainlist, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        if (islodingimg == true) {

        }
        hold.txt.setText(list.get(arg0).getGrade());
        hold.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        if (arg0 == position) {
            hold.img.setVisibility(View.VISIBLE);
        } else {
            hold.img.setVisibility(View.INVISIBLE);

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
        RelativeLayout layout;
        ImageView img;
        TextView txt;

        public Holder(View view) {
            txt = (TextView) view.findViewById(R.id.mainitem_txt);
            img = (ImageView) view.findViewById(R.id.mainitem_img);
            layout = (RelativeLayout) view.findViewById(R.id.mainitem_layout);
        }
    }
}
