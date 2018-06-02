package com.ekakashi.greenhouse.features.timeline.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ekakashi.greenhouse.features.timeline.image.TimelineViewImageFragment;

import java.util.ArrayList;

public class TimelineViewImageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> imageList;

    public TimelineViewImageAdapter(FragmentManager fm, ArrayList<String> imageList) {
        super(fm);
        this.imageList = imageList;
    }

    @Override
    public Fragment getItem(int position) {
        String imageUrl = imageList.get(position);
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
