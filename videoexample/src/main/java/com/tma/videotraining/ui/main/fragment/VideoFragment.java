package com.tma.videotraining.ui.main.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.tma.videotraining.R;
import com.tma.videotraining.di.component.DaggerFragmentComponent;
import com.tma.videotraining.di.module.FragmentModule;
import com.tma.videotraining.ui.interactor.VideoFragmentInteractor;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;
import com.tma.videotraining.ui.view.ViewFragment;

import javax.inject.Inject;

/**
 * Created by nbhung on 7/7/2017.
 */

public class VideoFragment extends Fragment implements ViewFragment.ViewVideoFragment {
    @Inject
    VideoFragmentInteractor videoFragmentInteractor;
    private ProgressDialog progressDialog;
    private VideoView videoView;
    private MediaController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        mController = new MediaController(getActivity());
        videoView = (VideoView) rootView.findViewById(R.id.vvplayvideo);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ListVideo mvideo = (ListVideo) bundle.getSerializable("object");
            if (mvideo != null) {
                progressDialog = ProgressDialog.show(getActivity(), "",
                        "Buffering video...", false);
                progressDialog.setCancelable(true);
                videoFragmentInteractor.playVideo(mvideo.getUrl(), videoView, mController, this);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "on attach video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("onDetach", "video");
    }

    @Override
    public void playSuccess() {
        progressDialog.dismiss();
    }
}
