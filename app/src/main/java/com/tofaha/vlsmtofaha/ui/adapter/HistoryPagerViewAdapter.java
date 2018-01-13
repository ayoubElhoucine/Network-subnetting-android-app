package com.tofaha.vlsmtofaha.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.ui.animation.SmartFragmentStatePagerAdapter;
import com.tofaha.vlsmtofaha.ui.history.HistoryViewPagerFragment;

public class HistoryPagerViewAdapter extends SmartFragmentStatePagerAdapter {
    public HistoryPagerViewAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HistoryViewPagerFragment.newInstance(0);
            case 1:
                return HistoryViewPagerFragment.newInstance(1);
            default:
                return null;
        }
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constant.FRAGMENT_VLSM;
            case 1:
                return Constant.FRAGMENT_CIDR;
            default:
                return null;
        }
    }

    public int getCount() {
        return 2;
    }
}
