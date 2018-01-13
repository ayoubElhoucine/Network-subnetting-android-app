package com.tofaha.vlsmtofaha.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tofaha.vlsmtofaha.R;
import com.tofaha.vlsmtofaha.firebase.VideosFirebase;
import com.tofaha.vlsmtofaha.model.Video;
import java.util.List;

public class TutorialViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    public TutorialViewPagerAdapter(Context context) {
        this.mContext = context;
    }

    public Object instantiateItem(ViewGroup collection, int position) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.fragment_video_list, null);
        this.recyclerView = (RecyclerView) layout.findViewById(R.id.video_recycler_view);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 1));
        this.videoAdapter = new VideoAdapter(this.mContext, myVideoList(position));
        this.recyclerView.setAdapter(this.videoAdapter);
        collection.addView(layout);
        return layout;
    }

    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public int getCount() {
        return 3;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ENGLISH";
            case 1:
                return "FRANÇAIS";
            case 2:
                return "العربية";
            default:
                return null;
        }
    }

    public List<Video> myVideoList(int position) {
        switch (position) {
            case 0:
                return VideosFirebase.englishVideos;
            case 1:
                return VideosFirebase.frenchVideos;
            case 2:
                return VideosFirebase.arabicVideos;
            default:
                return null;
        }
    }
}
