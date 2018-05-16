package com.edusdk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edusdk.ui.MembersFragment;
import com.edusdk.ui.RoomFragment;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RoomPageAdapter extends FragmentPagerAdapter {

    public RoomPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment ft = null;
        switch (position) {
            case 0:
                ft = new RoomFragment();
                break;
            case 1:
                ft = new MembersFragment();
                break;
            default:
                break;
        }
        return ft;
    }


    @Override
    public int getCount() {
        return 2;
    }


}
