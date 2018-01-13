package com.tofaha.vlsmtofaha.firebase;

import android.content.Context;
import android.support.v4.view.ViewPager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tofaha.vlsmtofaha.model.Video;
import com.tofaha.vlsmtofaha.ui.adapter.TutorialViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class FirebaseController {
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = this.database.getReference();
    private TutorialViewPagerAdapter tutorialViewPagerAdapter;
    private ViewPager viewPager;

    public FirebaseController(Context context, ViewPager viewPager) {
        this.viewPager = viewPager;
        this.context = context;
    }

    public List<Video> getVideos(String videoLan) {
        final List<Video> videoList = new ArrayList();
        this.myRef.child("Videos").child(videoLan).addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                videoList.add((Video) dataSnapshot.getValue(Video.class));
                FirebaseController.this.tutorialViewPagerAdapter = new TutorialViewPagerAdapter(FirebaseController.this.context);
                FirebaseController.this.viewPager.setAdapter(FirebaseController.this.tutorialViewPagerAdapter);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return videoList;
    }
}
