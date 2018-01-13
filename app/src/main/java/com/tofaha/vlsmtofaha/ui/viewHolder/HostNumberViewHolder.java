package com.tofaha.vlsmtofaha.ui.viewHolder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.EditText;

import com.tofaha.vlsmtofaha.R;

public class HostNumberViewHolder extends ViewHolder {
    public EditText hostNumberEdit;

    public HostNumberViewHolder(View itemView) {
        super(itemView);
        this.hostNumberEdit = (EditText) itemView.findViewById(R.id.host_number_card_edit);
    }
}
