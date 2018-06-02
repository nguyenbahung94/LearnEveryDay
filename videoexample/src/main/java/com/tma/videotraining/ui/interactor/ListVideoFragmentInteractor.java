package com.tma.videotraining.ui.interactor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.main.fragment.VideoFragment;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;

/**
 * Created by nbhung on 7/10/2017.
 */

public class ListVideoFragmentInteractor {
    public ListVideoFragmentInteractor() {
    }
    public void swap(ListVideo listVideo, Fragment activity){
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", listVideo);
        videoFragment.setArguments(bundle);
        FragmentManager fragmentManager = activity.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, videoFragment);
        fragmentTransaction.commit();
    }
}
