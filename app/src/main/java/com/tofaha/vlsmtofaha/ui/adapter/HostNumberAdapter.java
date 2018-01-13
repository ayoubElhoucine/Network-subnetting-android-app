package com.tofaha.vlsmtofaha.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.HostNumber;
import com.tofaha.vlsmtofaha.model.MyData;
import com.tofaha.vlsmtofaha.ui.viewHolder.HostNumberViewHolder;
import com.tofaha.vlsmtofaha.ui.vlsmCalculatorFragment.VlsmCalculatorFragment;
import io.realm.RealmList;

public class HostNumberAdapter extends Adapter<HostNumberViewHolder> {
    private Context context;
    private RealmList<HostNumber> hostNumbers;

    public HostNumberAdapter(Context context, RealmList<HostNumber> hostNumbers) {
        this.context = context;
        this.hostNumbers = hostNumbers;
    }

    public HostNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HostNumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.host_number_card, parent, false));
    }

    public void onBindViewHolder(HostNumberViewHolder holder, final int position) {
        HostNumber hostNumber = (HostNumber) this.hostNumbers.get(position);
        holder.hostNumberEdit.setHint("Subnet : " + String.valueOf(position));
        holder.hostNumberEdit.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                RecyclerView recyclerView = VlsmCalculatorFragment.recyclerView;
                if (recyclerView == null || recyclerView.findViewHolderForLayoutPosition(position + 1) != null) {
                    return false;
                }
                recyclerView.smoothScrollToPosition(position + 1);
                return true;
            }
        });
        holder.hostNumberEdit.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((HostNumber) MyData.vlsm.getHostNumberList().get(position)).setHostNumber(Integer.parseInt(String.valueOf(s)));
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    public int getItemCount() {
        return this.hostNumbers.size();
    }

    public int getItemViewType(int position) {
        return position;
    }
}
