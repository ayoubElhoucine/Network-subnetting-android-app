package com.tofaha.vlsmtofaha.dagger;

import android.content.Context;
import com.tofaha.vlsmtofaha.preferences.Pref;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class SettingModule {
    @Singleton
    @Provides
    Pref providePref(Context context) {
        return new Pref(context);
    }
}
