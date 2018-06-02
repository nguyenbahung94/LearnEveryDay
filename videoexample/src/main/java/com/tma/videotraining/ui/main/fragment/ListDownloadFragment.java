package com.tma.videotraining.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.Utils.NetWorkUtils;
import com.tma.videotraining.di.component.DaggerFragmentComponent;
import com.tma.videotraining.di.module.FragmentModule;
import com.tma.videotraining.ui.interactor.ListDownloadFragmentInteractor;

import javax.inject.Inject;

/**
 * Created by nbhung on 7/7/2017.
 */

public class ListDownloadFragment extends Fragment {
    @Inject
    ListDownloadFragmentInteractor mlListDownloadFragmentInteractor;
    private View view;
    private Button btnDownload, btnPause, btnCancel;
    private ProgressBar mProgressbar;
    private TextView tvStatus, tvCounFile;
    private boolean statusStop = false;

    public ListDownloadFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_download, container, false);
        init();
        event();
        return view;
    }

    private void init() {
        btnDownload = (Button) view.findViewById(R.id.btn_download);
        btnPause = (Button) view.findViewById(R.id.btnpause);
        btnCancel = (Button) view.findViewById(R.id.btncancel);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        tvStatus = (TextView) view.findViewById(R.id.statusoffile);
        tvCounFile = (TextView) view.findViewById(R.id.countfile);
    }

    private void event() {
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWorkUtils.isNetWorkConnected(getActivity())) {
                    btnCancel.setEnabled(true);
                    btnPause.setEnabled(true);
                    statusStop = false;
                    mProgressbar.setProgress(0);
                    btnDownload.setEnabled(false);
                    tvStatus.setText(R.string.download);
                    mlListDownloadFragmentInteractor.downLoadVideo(tvCounFile, tvStatus, mProgressbar, btnPause, btnCancel, btnDownload);

                } else {
                    Toast.makeText(getActivity(), "Please connect a network", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!statusStop) {
                    tvStatus.setText(R.string.pause);
                    btnPause.setText(R.string.resume);
                    statusStop = true;
                    mlListDownloadFragmentInteractor.pauseVideo();
                } else {
                    if (NetWorkUtils.isNetWorkConnected(getActivity())) {
                        tvStatus.setText(R.string.download);
                        btnPause.setText(R.string.pause);
                        statusStop = false;
                        mlListDownloadFragmentInteractor.resumeVideo();
                    } else {
                        Toast.makeText(getActivity(), "Please connect a network", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlListDownloadFragmentInteractor.CancelVideo();
                tvStatus.setText(R.string.cancel);
                tvCounFile.setText("0%");
                btnPause.setText(R.string.pause);
                btnPause.setEnabled(false);
                btnCancel.setEnabled(false);
                btnDownload.setEnabled(true);
                mProgressbar.setProgress(0);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "on attach download video", Toast.LENGTH_SHORT).show();
    }

//
//    public void swap(ListVideo listVideo, Fragment activity) {
//        VideoFragment videoFragment = new VideoFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("object", listVideo);
//        videoFragment.setArguments(bundle);
//        FragmentManager fragmentManager = activity.getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container_body, videoFragment);
//        fragmentTransaction.commit();
//    }


}
