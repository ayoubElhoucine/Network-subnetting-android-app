package com.tofaha.vlsmtofaha.superClasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.tofaha.vlsmtofaha.app.TofahaApplication;
import com.tofaha.vlsmtofaha.preferences.Pref;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.inject.Inject;

public class SuperMainActivity extends AppCompatActivity {
    @Inject
    public Pref pref;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((TofahaApplication) getApplication()).getAppComponent().inject(this);
    }

    public String time() {
        return new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
