package com.tofaha.vlsmtofaha.ui.history;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.Vlsm;
import com.tofaha.vlsmtofaha.realm.RealmController;
import com.tofaha.vlsmtofaha.superClasses.SuperMainActivity;
import com.tofaha.vlsmtofaha.ui.adapter.HistoryPagerViewAdapter;
import com.tofaha.vlsmtofaha.ui.animation.ZoomOutPagerTransformer;
import com.tofaha.vlsmtofaha.ui.main.MainActivity;
import io.realm.Realm;

public class History extends SuperMainActivity {
    private HistoryPagerViewAdapter historyPagerViewAdapter;
    private Realm realm;
    @BindView(R.id.tab_history_view)
    TabLayout tabLayout;
    @BindView(R.id.history_toolbar)
    Toolbar toolbar;
    @BindView(R.id.history_view_pager)
    ViewPager viewPager;

    class C03971 implements OnClickListener {
        C03971() {
        }

        public void onClick(View view) {
            History.this.startActivity(new Intent(History.this, MainActivity.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_history);
        ButterKnife.bind((Activity) this);
        this.realm = RealmController.with((Activity) this).getRealm();
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationOnClickListener(new C03971());
        this.historyPagerViewAdapter = new HistoryPagerViewAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(this.historyPagerViewAdapter);
        this.viewPager.setPageTransformer(true, new ZoomOutPagerTransformer());
        this.tabLayout.setupWithViewPager(this.viewPager);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.action_settings) {
            return super.onOptionsItemSelected(item);
        }
        Handler handler = new Handler();
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "...............");
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            public void run() {
                History.this.clearAll(History.this.viewPager.getCurrentItem());
                switch (History.this.viewPager.getCurrentItem()) {
                    case 0:
                        HistoryViewPagerFragment.mainFragment1.setRecyclerViewContent(0);
                        break;
                    case 1:
                        HistoryViewPagerFragment.mainFragment2.setRecyclerViewContent(1);
                        break;
                }
                progressDialog.dismiss();
            }
        }, 800);
        return true;
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void clearAll(int position) {
        this.realm.beginTransaction();
        switch (position) {
            case 0:
                this.realm.clear(Vlsm.class);
                break;
            case 1:
                this.realm.clear(CIDR.class);
                break;
        }
        this.realm.commitTransaction();
    }
}
