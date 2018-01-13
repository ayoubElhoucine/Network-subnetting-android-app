package com.tofaha.vlsmtofaha.dagger;

import com.tofaha.vlsmtofaha.superClasses.MainFragment;
import com.tofaha.vlsmtofaha.superClasses.SuperMainActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, SettingModule.class})
public interface AppComponent {
    void inject(MainFragment mainFragment);

    void inject(SuperMainActivity superMainActivity);
}
