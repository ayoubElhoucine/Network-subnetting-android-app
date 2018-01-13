package com.tofaha.vlsmtofaha.ui.cidrFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.superClasses.MainFragment;
import com.tofaha.vlsmtofaha.ui.adapter.CIDRviewPagerAdapter;
import com.tofaha.vlsmtofaha.ui.animation.ZoomOutPagerTransformer;

public class CidrFragment extends MainFragment {
    private CIDRviewPagerAdapter cidRviewPagerAdapter;
    @BindView(R.id.tab_cidr_view)
    TabLayout tabLayout;
    @BindView(R.id.cidr_view_pager)
    ViewPager viewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cidr, container, false);
        ButterKnife.bind((Object) this, view);
        this.cidRviewPagerAdapter = new CIDRviewPagerAdapter(getFragmentManager());
        this.viewPager.setAdapter(this.cidRviewPagerAdapter);
        this.viewPager.setPageTransformer(true, new ZoomOutPagerTransformer());
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.viewPager.setCurrentItem(MyData.CIDR_POSITION);
        return view;
    }
}
