package com.tofaha.vlsmtofaha.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.app.Constant;
import com.tofaha.vlsmtofaha.model.CIDR;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.realm.RealmController;
import com.tofaha.vlsmtofaha.realm.RealmRecyclerViewAdapter;
import com.tofaha.vlsmtofaha.ui.main.MainActivity;
import com.tofaha.vlsmtofaha.ui.viewHolder.CIDRHolder;
import io.realm.Realm;
import io.realm.RealmResults;

public class HistoryCIDRAdapter extends RealmRecyclerViewAdapter<CIDR> {
    private Context context;
    private Realm realm;

    public HistoryCIDRAdapter(Context context) {
        this.context = context;
    }

    public CIDRHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CIDRHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cidr_card, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        this.realm = RealmController.getInstance().getRealm();
        final CIDR cidr = (CIDR) getItem(position);
        CIDRHolder cidrHolder = (CIDRHolder) holder;
        cidrHolder.ipAddress.setText(cidr.getIpAddress());
        cidrHolder.version.setText("Version : " + cidr.getType());
        cidrHolder.time.setText(cidr.getTime());
        cidrHolder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                Handler handler = new Handler();
                final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(), null, ".........");
                progressDialog.show();
                MyData.cidr = cidr;
                if (cidr.getType().equals(Constant.IPV4)) {
                    MyData.CIDR_POSITION = 0;
                } else if (cidr.getType().equals(Constant.IPV6)) {
                    MyData.CIDR_POSITION = 1;
                }
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra(Constant.FRAGMENT, Constant.FRAGMENT_CIDR);
                        v.getContext().startActivity(intent);
                        progressDialog.dismiss();
                    }
                }, 800);
            }
        });
        cidrHolder.cardView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                RealmResults<CIDR> results = HistoryCIDRAdapter.this.realm.where(CIDR.class).findAll();
                HistoryCIDRAdapter.this.realm.beginTransaction();
                results.remove(position);
                HistoryCIDRAdapter.this.realm.commitTransaction();
                HistoryCIDRAdapter.this.notifyDataSetChanged();
                return false;
            }
        });
    }

    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }
}
