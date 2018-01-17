package www.test720.com.naneducation.view;

/**
 * Created by pang on 2017/4/4.
 * 自定义 popupwindow 弹窗  仿微信下拉选择器
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import www.test720.com.naneducation.R;


public class TopPopupWindows extends PopupWindow {
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private OnClickListener myOnClick; // PopupWindow 菜单 空间单击事件


    public TopPopupWindows(Activity context, OnClickListener myOnClick) {
        super(context);
        this.context = context;
        this.myOnClick = myOnClick;

        Init();
    }


    private void Init() {
        // TODO Auto-generated method stub
        // PopupWindow 导入
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popuw_content_top_arrow_layout, null);
        TextView tip_text = (TextView) mMenuView.findViewById(R.id.tip_text);

        tip_text.setOnClickListener(myOnClick);

        // 导入布局
        this.setContentView(mMenuView);
        // 设置动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //防止虚拟键挡住
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置弹出窗体的 宽，高
        this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


}
