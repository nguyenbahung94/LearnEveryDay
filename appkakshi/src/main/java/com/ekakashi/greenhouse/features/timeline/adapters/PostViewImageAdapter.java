package com.ekakashi.greenhouse.features.timeline.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ekakashi.greenhouse.features.timeline.image.TimelineViewImageFragment;
import com.ekakashi.greenhouse.features.timeline_post.model.ImageViewer;

import java.util.ArrayList;


public class PostViewImageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<ImageViewer> imageList;

    public PostViewImageAdapter(FragmentManager fm, ArrayList<ImageViewer> imageList) {
        super(fm);
        this.imageList = imageList;
    }

    @Override
    public Fragment getItem(int position) {
        String imageUrl = imageList.get(position).getUrl();
        return TimelineViewImageFragment.newInstance(imageUrl);
    }

    @Override
    public int getCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
    }
}
