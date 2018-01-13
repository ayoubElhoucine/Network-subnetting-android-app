package com.tofaha.vlsmtofaha.ui.viewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.tofaha.vlsmtofaha.R;

public class VlsmHistoryCardHolder extends ViewHolder {
    public CardView cardView;
    public TextView ipAddress;
    public TextView subnets;
    public TextView time;

    public VlsmHistoryCardHolder(View itemView) {
        super(itemView);
        this.cardView = (CardView) itemView.findViewById(R.id.vlsm_history_card_view);
        this.ipAddress = (TextView) itemView.findViewById(R.id.vlsm_history_ipAddress);
        this.subnets = (TextView) itemView.findViewById(R.id.vlsm_history_hosts);
        this.time = (TextView) itemView.findViewById(R.id.vlsm_history_time);
    }
}
