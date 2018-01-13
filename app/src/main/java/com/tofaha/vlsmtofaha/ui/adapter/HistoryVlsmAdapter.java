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
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.model.Vlsm;
import com.tofaha.vlsmtofaha.realm.RealmController;
import com.tofaha.vlsmtofaha.realm.RealmRecyclerViewAdapter;
import com.tofaha.vlsmtofaha.ui.viewHolder.VlsmHistoryCardHolder;
import com.tofaha.vlsmtofaha.ui.vlsmResult.VlsmResult;
import io.realm.Realm;
import io.realm.RealmResults;

public class HistoryVlsmAdapter extends RealmRecyclerViewAdapter<Vlsm> {
    private Context context;
    private Realm realm;

    public HistoryVlsmAdapter(Context context) {
        this.context = context;
    }

    public VlsmHistoryCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VlsmHistoryCardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vlsm_card, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        this.realm = RealmController.getInstance().getRealm();
        final Vlsm vlsm = (Vlsm) getItem(position);
        VlsmHistoryCardHolder myHolder = (VlsmHistoryCardHolder) holder;
        myHolder.ipAddress.setText(vlsm.getIpAddress());
        myHolder.subnets.setText("Number of subnet : " + String.valueOf(vlsm.getHostNumberList().size()));
        myHolder.time.setText(vlsm.getTime());
        myHolder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                MyData.vlsm = vlsm;
                Handler handler = new Handler();
                final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(), null, ".........");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        v.getContext().startActivity(new Intent(v.getContext(), VlsmResult.class));
                        progressDialog.dismiss();
                    }
                }, 800);
            }
        });
        myHolder.cardView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                RealmResults<Vlsm> results = HistoryVlsmAdapter.this.realm.where(Vlsm.class).findAll();
                String title = ((Vlsm) results.get(position)).getIpAddress();
                HistoryVlsmAdapter.this.realm.beginTransaction();
                results.remove(position);
                HistoryVlsmAdapter.this.realm.commitTransaction();
                HistoryVlsmAdapter.this.notifyDataSetChanged();
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
