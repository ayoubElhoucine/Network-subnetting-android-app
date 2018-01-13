package com.tofaha.vlsmtofaha.app;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.ads.MobileAds;
import com.tofaha.vlsmtofaha.dagger.AppComponent;
import com.tofaha.vlsmtofaha.dagger.AppModule;
import com.tofaha.vlsmtofaha.dagger.DaggerAppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration.Builder;

public class TofahaApplication extends Application {
    private AppComponent appComponent;

    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, Constant.BANNER_APP_ID);
        this.appComponent = initDagger(this);
        realmConfig();
    }

    public void realmConfig() {
        Realm.setDefaultConfiguration(new Builder((Context) this).name(Realm.DEFAULT_REALM_NAME).schemaVersion(0).deleteRealmIfMigrationNeeded().build());
    }

    protected AppComponent initDagger(TofahaApplication application) {
        return DaggerAppComponent.builder().appModule(new AppModule(application)).build();
    }

    public AppComponent getAppComponent() {
        return this.appComponent;
    }
}
