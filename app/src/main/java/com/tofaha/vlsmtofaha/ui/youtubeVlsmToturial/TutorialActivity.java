package com.tofaha.vlsmtofaha.ui.youtubeVlsmToturial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.firebase.FirebaseController;
import com.tofaha.vlsmtofaha.firebase.VideosFirebase;
import com.tofaha.vlsmtofaha.ui.adapter.TutorialViewPagerAdapter;
import com.tofaha.vlsmtofaha.ui.main.MainActivity;

public class TutorialActivity extends YouTubeBaseActivity implements OnInitializedListener, TutorialView {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    @BindView(R.id.adView)
    AdView adView;
    private FirebaseController firebaseController;
    private View mContentView;
    private View mControlsView;
    private final OnTouchListener mDelayHideTouchListener = new C04164();
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new C04131();
    private final Runnable mHideRunnable = new C04153();
    private final Runnable mShowPart2Runnable = new C04142();
    private boolean mVisible;
    @BindView(R.id.tab_youtube_view)
    TabLayout tabLayout;
    private TutorialViewPagerAdapter tutorialViewPagerAdapter;
    @BindView(R.id.youtube_view_pager)
    ViewPager viewPager;
    private YouTubePlayer youTubePlayer;
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;

    class C04131 implements Runnable {
        C04131() {
        }

        @SuppressLint({"InlinedApi"})
        public void run() {
            TutorialActivity.this.mContentView.setSystemUiVisibility(4871);
        }
    }

    class C04142 implements Runnable {
        C04142() {
        }

        public void run() {
            TutorialActivity.this.mControlsView.setVisibility(View.VISIBLE);
        }
    }

    class C04153 implements Runnable {
        C04153() {
        }

        public void run() {
            TutorialActivity.this.hide();
        }
    }

    class C04164 implements OnTouchListener {
        C04164() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            TutorialActivity.this.delayedHide(TutorialActivity.AUTO_HIDE_DELAY_MILLIS);
            return false;
        }
    }

    class C07425 implements PlayerStateChangeListener {
        C07425() {
        }

        public void onAdStarted() {
        }

        public void onError(ErrorReason arg0) {
        }

        public void onLoaded(String arg0) {
        }

        public void onLoading() {
        }

        public void onVideoEnded() {
        }

        public void onVideoStarted() {
        }
    }

    class C07436 implements PlaybackEventListener {
        C07436() {
        }

        public void onBuffering(boolean arg0) {
        }

        public void onPaused() {
        }

        public void onPlaying() {
        }

        public void onSeekTo(int arg0) {
        }

        public void onStopped() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind((Activity) this);
        this.adView.loadAd(new Builder().build());
        this.youTubePlayerView.initialize(Constant.API_KEY, this);
        this.tutorialViewPagerAdapter = new TutorialViewPagerAdapter(this);
        this.viewPager.setAdapter(this.tutorialViewPagerAdapter);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.firebaseController = new FirebaseController(this, this.viewPager);
        VideosFirebase.arabicVideos = this.firebaseController.getVideos(Constant.ARABIC);
        VideosFirebase.englishVideos = this.firebaseController.getVideos(Constant.ENGLISH);
        VideosFirebase.frenchVideos = this.firebaseController.getVideos(Constant.FRENCH);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void toggle() {
        if (this.mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        this.mControlsView.setVisibility(View.GONE);
        this.mVisible = false;
        this.mHideHandler.removeCallbacks(this.mShowPart2Runnable);
        this.mHideHandler.postDelayed(this.mHidePart2Runnable, 300);
    }

    @SuppressLint({"InlinedApi"})
    private void show() {
        this.mContentView.setSystemUiVisibility(1536);
        this.mVisible = true;
        this.mHideHandler.removeCallbacks(this.mHidePart2Runnable);
        this.mHideHandler.postDelayed(this.mShowPart2Runnable, 300);
    }

    private void delayedHide(int delayMillis) {
        this.mHideHandler.removeCallbacks(this.mHideRunnable);
        this.mHideHandler.postDelayed(this.mHideRunnable, (long) delayMillis);
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onPause() {
        if (this.adView != null) {
            this.adView.pause();
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (this.adView != null) {
            this.adView.resume();
        }
    }

    public void onDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
        }
        super.onDestroy();
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (player != null) {
            this.youTubePlayer = player;
            if (!wasRestored) {
                player.cueVideo("p1XpTn_KZF8");
            }
            player.setPlayerStateChangeListener(new C07425());
            player.setPlaybackEventListener(new C07436());
        }
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    }

    public void onUpdateVideo(String id) {
        this.youTubePlayer.cueVideo(id);
    }
}
