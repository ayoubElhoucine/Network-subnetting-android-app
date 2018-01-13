package com.tofaha.vlsmtofaha.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.ui.animation.SmartFragmentStatePagerAdapter;
import com.tofaha.vlsmtofaha.ui.cidrFragment.IPv4Fragment;
import com.tofaha.vlsmtofaha.ui.cidrFragment.IPv6Fragment;

public class CIDRviewPagerAdapter extends SmartFragmentStatePagerAdapter {
    public CIDRviewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IPv4Fragment();
            case 1:
                return new IPv6Fragment();
            default:
                return null;
        }
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constant.IPV4;
            case 1:
                return Constant.IPV6;
            default:
                return null;
        }
    }

    public int getCount() {
        return 2;
    }
}
