package com.tofaha.vlsmtofaha.ui.viewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tofaha.vlsmtofaha.R;

public class VideoHolder extends ViewHolder {
    public CardView cardView;
    public ImageView image;
    public TextView period;
    public TextView source;
    public TextView title;

    public VideoHolder(View itemView) {
        super(itemView);
        this.cardView = (CardView) itemView.findViewById(R.id.video_card_view);
        this.image = (ImageView) itemView.findViewById(R.id.video_image);
        this.period = (TextView) itemView.findViewById(R.id.video_period);
        this.title = (TextView) itemView.findViewById(R.id.video_title);
        this.source = (TextView) itemView.findViewById(R.id.video_source);
    }
}
