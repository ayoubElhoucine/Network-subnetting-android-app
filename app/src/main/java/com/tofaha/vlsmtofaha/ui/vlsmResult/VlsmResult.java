package com.tofaha.vlsmtofaha.ui.vlsmResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.HostNumber;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.model.Subnet;
import com.tofaha.vlsmtofaha.superClasses.SuperMainActivity;
import com.tofaha.vlsmtofaha.ui.adapter.VlsmResultAdapter;
import com.tofaha.vlsmtofaha.ui.main.MainActivity;
import com.tofaha.vlsmtofaha.vlsmLogic.VlsmLogic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VlsmResult extends SuperMainActivity implements ResultView {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    public static boolean hide = true;
    @BindView(R.id.resultAdView)
    AdView adView;
    private Map<String, Integer> inputsMap = new HashMap();
    @BindView(R.id.layout_result_back)
    View layoutBack;
    @BindView(R.id.fullscreen_content)
    View mContentView;
    private final OnTouchListener mDelayHideTouchListener = new C04103();
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new C04081();
    private final Runnable mHideRunnable = new C04092();
    private boolean mVisible;
    @BindView(R.id.vlsm_result_recycler_view)
    RecyclerView recyclerView;
    private List<Subnet> subnetList;
    private VlsmResultAdapter vlsmResultAdapter;

    class C04081 implements Runnable {
        C04081() {
        }

        @SuppressLint({"InlinedApi"})
        public void run() {
            VlsmResult.this.mContentView.setSystemUiVisibility(4871);
        }
    }

    class C04092 implements Runnable {
        C04092() {
        }

        public void run() {
            VlsmResult.this.hide();
        }
    }

    class C04103 implements OnTouchListener {
        C04103() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            VlsmResult.this.delayedHide(VlsmResult.AUTO_HIDE_DELAY_MILLIS);
            return false;
        }
    }

    class C04115 implements OnClickListener {
        C04115() {
        }

        public void onClick(View v) {
            if (VlsmResult.hide) {
                VlsmResult.this.showBackLayout();
            } else {
                VlsmResult.this.hideBackLayout();
            }
        }
    }

    class C04126 implements Runnable {
        C04126() {
        }

        public void run() {
            VlsmResult.this.layoutBack.setVisibility(View.GONE);
            VlsmResult.hide = true;
        }
    }

    class C07414 extends AdListener {
        C07414() {
        }

        public void onAdLoaded() {
            super.onAdLoaded();
            VlsmResult.this.adView.setVisibility(View.VISIBLE);
        }

        public void onAdOpened() {
            super.onAdOpened();
            VlsmResult.this.adView.setVisibility(View.VISIBLE);
        }

        public void onAdClosed() {
            super.onAdClosed();
            VlsmResult.this.adView.setVisibility(View.GONE);
        }

        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            VlsmResult.this.adView.setVisibility(View.GONE);
        }

        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_vlsm_result);
        ButterKnife.bind((Activity) this);
        this.adView.loadAd(new Builder().build());
        this.adView.setAdListener(new C07414());
        this.mVisible = true;
        this.mContentView.setOnClickListener(new C04115());
        for (int i = 0; i < MyData.vlsm.getHostNumberList().size(); i++) {
            this.inputsMap.put("subnet number : " + String.valueOf(i + 1), Integer.valueOf(((HostNumber) MyData.vlsm.getHostNumberList().get(i)).getHostNumber()));
        }
        this.subnetList = VlsmLogic.calcVLSM(MyData.vlsm.getIpAddress(), this.inputsMap);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.vlsmResultAdapter = new VlsmResultAdapter(this, this.subnetList);
        this.recyclerView.setAdapter(this.vlsmResultAdapter);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void toggle() {
        if (this.mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        this.mHideHandler.postDelayed(this.mHidePart2Runnable, 300);
    }

    @SuppressLint({"InlinedApi"})
    private void show() {
        this.mContentView.setSystemUiVisibility(1536);
        this.mVisible = true;
        this.mHideHandler.removeCallbacks(this.mHidePart2Runnable);
    }

    private void delayedHide(int delayMillis) {
        this.mHideHandler.removeCallbacks(this.mHideRunnable);
        this.mHideHandler.postDelayed(this.mHideRunnable, (long) delayMillis);
    }

    public void backResult(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void hideBackLayout() {
        YoYo.with(Techniques.SlideOutLeft).duration(800).playOn(this.layoutBack);
        new Handler().postDelayed(new C04126(), 800);
    }

    public void showBackLayout() {
        this.layoutBack.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInLeft).duration(800).playOn(this.layoutBack);
        hide = false;
    }
}
