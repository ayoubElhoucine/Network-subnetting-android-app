package com.tofaha.vlsmtofaha.realm;

import android.content.Context;
import com.tofaha.vlsmtofaha.model.CIDR;
import io.realm.RealmResults;

public class RealmCIDRAdapter extends RealmModelAdapter<CIDR> {
    public RealmCIDRAdapter(Context context, RealmResults<CIDR> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
