package com.tofaha.vlsmtofaha.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.Subnet;
import com.tofaha.vlsmtofaha.ui.viewHolder.VlsmResultCardHolder;
import com.tofaha.vlsmtofaha.ui.vlsmResult.VlsmResult;
import java.util.List;

public class VlsmResultAdapter extends Adapter<VlsmResultCardHolder> {
    private Context context;
    private List<Subnet> subnetList;
    private VlsmResult vlsmResult;

    class C03831 implements OnClickListener {
        C03831() {
        }

        public void onClick(View v) {
            if (VlsmResult.hide) {
                VlsmResultAdapter.this.vlsmResult.showBackLayout();
            } else {
                VlsmResultAdapter.this.vlsmResult.hideBackLayout();
            }
        }
    }

    public VlsmResultAdapter(Context context, List<Subnet> subnetList) {
        this.context = context;
        this.vlsmResult = (VlsmResult) context;
        this.subnetList = subnetList;
    }

    public VlsmResultCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VlsmResultCardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vlsm_result_card, parent, false));
    }

    public void onBindViewHolder(VlsmResultCardHolder holder, int position) {
        Subnet subnet = (Subnet) this.subnetList.get(position);
        String[] range = subnet.range.split(" // ");
        holder.hostNumber.setText(String.valueOf(subnet.neededSize));
        holder.allocatedSize.setText(String.valueOf(subnet.allocatedSize));
        holder.networkAddress.setText(subnet.decMask);
        holder.maskAddress.setText(subnet.mask);
        holder.range1.setText(range[0]);
        holder.range2.setText(range[1]);
        holder.broadcast.setText(subnet.broadcast);
        holder.cardView.setOnClickListener(new C03831());
    }

    public int getItemCount() {
        return this.subnetList.size();
    }
}
