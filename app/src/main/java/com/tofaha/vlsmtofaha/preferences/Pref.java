package com.tofaha.vlsmtofaha.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.tofaha.vlsmtofaha.app.Constant;

public class Pref {
    private Context context;
    private SharedPreferences preferences;

    public Pref(Context context) {
        this.preferences = context.getSharedPreferences(context.getPackageName(), 0);
        this.context = context;
    }

    public String getLanguage() {
        return this.preferences.getString(Constant.LANGUAGE, Constant.ENGLISH);
    }

    public void setLanguage(String language) {
        this.preferences.edit().putString(Constant.LANGUAGE, language).apply();
    }

    public int getHistoryEntries() {
        return this.preferences.getInt(Constant.HISTORY_ENTRIES, 100);
    }

    public void setHistoryEntries(int entries) {
        this.preferences.edit().putInt(Constant.HISTORY_ENTRIES, entries).apply();
    }

    public boolean isAutoComplete() {
        return this.preferences.getBoolean(Constant.AUTO_COMPLETE, true);
    }

    public void setAutoComplete(boolean autoComplete) {
        this.preferences.edit().putBoolean(Constant.AUTO_COMPLETE, autoComplete).apply();
    }

    public void setIPv4(int iPv4) {
        this.preferences.edit().putInt(Constant.IPV4, iPv4).apply();
    }

    public int getIPv4() {
        return this.preferences.getInt(Constant.IPV4, 0);
    }

    public void setIPv6(int iPv6) {
        this.preferences.edit().putInt(Constant.IPV6, iPv6).apply();
    }

    public int getIPv6() {
        return this.preferences.getInt(Constant.IPV6, 0);
    }
}
