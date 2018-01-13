package com.tofaha.vlsmtofaha.superClasses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.tofaha.vlsmtofaha.app.TofahaApplication;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.Vlsm;
import com.tofaha.vlsmtofaha.preferences.Pref;
import com.tofaha.vlsmtofaha.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class MainFragment extends Fragment {
    @Inject
    public Pref pref;
    public Realm realm;

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        ((TofahaApplication) getActivity().getApplication()).getAppComponent().inject(this);
        this.realm = RealmController.with(getActivity()).getRealm();
    }

    public void saveInRealm(RealmObject realmObject) {
        this.realm.beginTransaction();
        this.realm.copyToRealm(realmObject);
        this.realm.commitTransaction();
    }

    public List<String> cidrIPlist() {
        List<String> cidrs = new ArrayList();
        Iterator it = this.realm.where(CIDR.class).findAll().iterator();
        while (it.hasNext()) {
            cidrs.add(((CIDR) it.next()).getIpAddress());
        }
        return cidrs;
    }

    public List<String> vlsmIPlist() {
        List<String> vlsms = new ArrayList();
        Iterator it = this.realm.where(Vlsm.class).findAll().iterator();
        while (it.hasNext()) {
            vlsms.add(((Vlsm) it.next()).getIpAddress());
        }
        return vlsms;
    }

    public String time() {
        return new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
