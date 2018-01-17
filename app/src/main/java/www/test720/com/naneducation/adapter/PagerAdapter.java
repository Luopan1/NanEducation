package www.test720.com.naneducation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

import www.test720.com.naneducation.bean.GuoJiangTitle;


/**
 * Created by LuoPan on 2017/5/16 9:30.
 */
public class PagerAdapter extends LazyFragmentPagerAdapter {
    List<Fragment> fragments;

    private FragmentManager fm;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(ViewGroup container, int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
