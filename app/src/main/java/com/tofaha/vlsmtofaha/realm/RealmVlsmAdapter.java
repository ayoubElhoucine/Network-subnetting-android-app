package com.tofaha.vlsmtofaha.realm;

import android.content.Context;
import com.tofaha.vlsmtofaha.model.Vlsm;
import io.realm.RealmResults;

public class RealmVlsmAdapter extends RealmModelAdapter<Vlsm> {
    public RealmVlsmAdapter(Context context, RealmResults<Vlsm> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
