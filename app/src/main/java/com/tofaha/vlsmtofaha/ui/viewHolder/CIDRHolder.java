package com.tofaha.vlsmtofaha.ui.viewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.tofaha.vlsmtofaha.R;

public class CIDRHolder extends ViewHolder {
    public CardView cardView;
    public TextView ipAddress;
    public TextView time;
    public TextView version;

    public CIDRHolder(View itemView) {
        super(itemView);
        this.cardView = (CardView) itemView.findViewById(R.id.cidr_history_card_view);
        this.ipAddress = (TextView) itemView.findViewById(R.id.cidr_history_ipAddress);
        this.version = (TextView) itemView.findViewById(R.id.cidr_history_version);
        this.time = (TextView) itemView.findViewById(R.id.cidr_history_time);
    }
}
