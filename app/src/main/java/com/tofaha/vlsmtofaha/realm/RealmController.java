package com.tofaha.vlsmtofaha.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.Vlsm;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    private static RealmController instance;
    private final Realm realm = Realm.getDefaultInstance();

    public RealmController(Application application) {
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return this.realm;
    }

    public void refresh() {
        this.realm.refresh();
    }

    public void clearAll() {
        this.realm.beginTransaction();
        this.realm.clear(Vlsm.class);
        this.realm.commitTransaction();
    }

    public RealmResults<Vlsm> getVlsms() {
        return this.realm.where(Vlsm.class).findAll();
    }

    public RealmResults<CIDR> getCidrs() {
        return this.realm.where(CIDR.class).findAll();
    }

    public Vlsm getVlsm(String id) {
        return (Vlsm) this.realm.where(Vlsm.class).equalTo("id", id).findFirst();
    }

    public boolean hasVlsms() {
        return !this.realm.allObjects(Vlsm.class).isEmpty();
    }
}
