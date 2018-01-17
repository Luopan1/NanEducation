package www.test720.com.naneducation.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.test720.com.naneducation.activity.NewsActivity;
import www.test720.com.naneducation.bean.StudyHomeBanner;

/**
 * Created by LuoPan on 2017/10/23 10:53.
 */

public abstract class QucikAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public QucikAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, T item) {

    }


}
