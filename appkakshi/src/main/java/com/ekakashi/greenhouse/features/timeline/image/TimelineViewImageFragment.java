package com.ekakashi.greenhouse.features.timeline.image;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;

/**
 * Created by nquochuy on 1/15/2018.
 */

public class TimelineViewImageFragment extends Fragment {

    private static final String KEY_ITEM = "tutorial_item";
    private String imageUrl;


    public static TimelineViewImageFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ITEM, imageUrl);

        TimelineViewImageFragment fragment = new TimelineViewImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(KEY_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_view_image, container, false);

        ImageView imgImage = rootView.findViewById(R.id.imgImage);
        Glide.with(getActivity()).load(imageUrl).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(imgImage);
        return rootView;

    }
}
