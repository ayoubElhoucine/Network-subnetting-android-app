package com.tofaha.vlsmtofaha.ui.viewHolder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.tofaha.vlsmtofaha.R;

public class VlsmResultCardHolder extends ViewHolder {
    public TextView allocatedSize;
    public TextView broadcast;
    public View cardView;
    public TextView hostNumber;
    public TextView maskAddress;
    public TextView networkAddress;
    public TextView range1;
    public TextView range2;

    public VlsmResultCardHolder(View itemView) {
        super(itemView);
        this.cardView = itemView.findViewById(R.id.card_result);
        this.hostNumber = (TextView) itemView.findViewById(R.id.vlsm_result_hostNumber);
        this.allocatedSize = (TextView) itemView.findViewById(R.id.vlsm_result_allocatedSize);
        this.networkAddress = (TextView) itemView.findViewById(R.id.vlsm_result_networkAddress);
        this.maskAddress = (TextView) itemView.findViewById(R.id.vlsm_result_maskAddress);
        this.range1 = (TextView) itemView.findViewById(R.id.vlsm_result_range1);
        this.range2 = (TextView) itemView.findViewById(R.id.vlsm_result_range2);
        this.broadcast = (TextView) itemView.findViewById(R.id.vlsm_result_broadcast);
    }
}
