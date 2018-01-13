package com.tofaha.vlsmtofaha.realm;

import android.support.v7.widget.RecyclerView.Adapter;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends Adapter {
    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position) {
        return this.realmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmAdapter() {
        return this.realmBaseAdapter;
    }

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter) {
        this.realmBaseAdapter = realmAdapter;
    }
}
