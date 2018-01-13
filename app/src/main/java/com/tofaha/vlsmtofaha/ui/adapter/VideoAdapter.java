package com.tofaha.vlsmtofaha.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.model.Video;
import com.tofaha.vlsmtofaha.ui.viewHolder.VideoHolder;
import com.tofaha.vlsmtofaha.ui.youtubeVlsmToturial.TutorialView;
import java.util.List;

public class VideoAdapter extends Adapter<VideoHolder> {
    private Context context;
    private TutorialView tutorialView;
    private List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.tutorialView = (TutorialView) context;
        this.videoList = videoList;
    }

    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_card, parent, false));
    }

    public void onBindViewHolder(VideoHolder holder, int position) {
        final Video video = (Video) this.videoList.get(position);
        holder.title.setText(video.getTitle());
        holder.period.setText(video.getPeriod());
        holder.source.setText(video.getSource());
        Glide.with(this.context).load(video.getImage()).into(holder.image);
        holder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoAdapter.this.tutorialView.onUpdateVideo(video.getId());
            }
        });
    }

    public int getItemCount() {
        return this.videoList.size();
    }
}
