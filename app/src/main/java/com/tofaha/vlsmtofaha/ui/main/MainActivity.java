package com.tofaha.vlsmtofaha.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.NativeExpressAdView;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.superClasses.SuperMainActivity;
import com.tofaha.vlsmtofaha.ui.binaryCalculatorFragment.BinaryCalculatorFragment;
import com.tofaha.vlsmtofaha.ui.cidrFragment.CidrFragment;
import com.tofaha.vlsmtofaha.ui.history.History;
import com.tofaha.vlsmtofaha.ui.setting.Setting;
import com.tofaha.vlsmtofaha.ui.vlsmCalculatorFragment.VlsmCalculatorFragment;
import com.tofaha.vlsmtofaha.ui.youtubeVlsmToturial.TutorialActivity;

public class MainActivity extends SuperMainActivity implements OnNavigationItemSelectedListener, MainView {
    private String fragment;
    private Fragment myFragment;

    @BindView(R.id.native_ads_layout)
    View nativeAdsLayout;

    @BindView(R.id.native_ad_view)
    NativeExpressAdView nativeExpressAdView;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.menu_red)
    FloatingActionMenu floatingActionMenu ;

    class C07401 extends AdListener {
        C07401() {
        }

        public void onAdLoaded() {
            super.onAdLoaded();
            MainActivity.this.nativeAdsLayout.setVisibility(View.VISIBLE);
        }

        public void onAdOpened() {
            super.onAdOpened();
            MainActivity.this.nativeAdsLayout.setVisibility(View.VISIBLE);
        }

        public void onAdClosed() {
            super.onAdClosed();
            MainActivity.this.nativeAdsLayout.setVisibility(View.GONE);
        }

        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            MainActivity.this.nativeAdsLayout.setVisibility(View.GONE);
        }

        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        
        floatingActionMenu.bringToFront();

        this.nativeExpressAdView.loadAd(new Builder().build());
        this.nativeAdsLayout.setVisibility(View.GONE);
        this.nativeExpressAdView.setAdListener(new C07401());
        this.fragment = getIntent().getStringExtra(Constant.FRAGMENT);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, this.toolbar, R.string.nativ_ads, R.string.nativ_ads);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        if (this.fragment != null) {
            loadFragment(Constant.FRAGMENT_CIDR);
        } else {
            loadFragment(Constant.FRAGMENT_VLSM);
        }
    }

    @OnClick(R.id.fab_binary)
    void binaryFragment() {
        loadFragment(Constant.FRAGMENT_BINARY);
    }

    @OnClick(R.id.fab_cidr)
    void ciderFragment() {
        loadFragment(Constant.FRAGMENT_CIDR);
    }

    @OnClick(R.id.fab_vlsm)
    void vlsmFragment() {
        loadFragment(Constant.FRAGMENT_VLSM);
    }

    public void closeAds(View view) {
        this.nativeAdsLayout.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
        } else {
            loadFragment(Constant.FRAGMENT_VLSM);
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_tutorial) {
            startActivity(new Intent(this, TutorialActivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, History.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, Setting.class));
        } else if (id == R.id.nav_share ) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.tofaha.vlsmtofaha");
            startActivity(Intent.createChooser(sharingIntent, "Share with"));
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    @Override
    public void loadFragment(String fragment) {
        switch (fragment) {
            case Constant.FRAGMENT_CIDR :
                if (fragment.equals(Constant.FRAGMENT_CIDR)) {
                    this.myFragment = new CidrFragment();
                    replaceFragment(this.myFragment);
                    setMainTitle(Constant.FRAGMENT_CIDR);
                    break;
                }
                break;
            case Constant.FRAGMENT_VLSM:
                if (fragment.equals(Constant.FRAGMENT_VLSM)) {
                    this.myFragment = new VlsmCalculatorFragment();
                    replaceFragment(this.myFragment);
                    setMainTitle(Constant.FRAGMENT_VLSM);
                    break;
                }
                break;
            case Constant.FRAGMENT_BINARY:
                if (fragment.equals(Constant.FRAGMENT_BINARY)) {
                    this.myFragment = new BinaryCalculatorFragment();
                    replaceFragment(this.myFragment);
                    setMainTitle(Constant.FRAGMENT_BINARY);
                    break;
                }
                break;
        }
    }

    public void setMainTitle(String title) {
        getSupportActionBar().setTitle((CharSequence) title);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }

    public void onResume() {
        super.onResume();
        this.nativeExpressAdView.resume();
    }

    public void onPause() {
        this.nativeExpressAdView.pause();
        super.onPause();
    }

    public void onDestroy() {
        this.nativeExpressAdView.destroy();
        super.onDestroy();
    }
}
