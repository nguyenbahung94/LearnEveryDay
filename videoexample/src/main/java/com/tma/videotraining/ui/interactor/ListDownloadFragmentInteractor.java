package com.tma.videotraining.ui.interactor;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tma.videotraining.Utils.DownloadVideo;

/**
 * Created by nbhung on 7/12/2017.
 */

public class ListDownloadFragmentInteractor {
    private DownloadVideo down;
    private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    public ListDownloadFragmentInteractor() {
    }

    public void downLoadVideo(TextView tvCounfile, TextView tvStatus, ProgressBar mProgressBar, Button btnPause, Button canCel, Button downoad) {
        down = new DownloadVideo(tvCounfile, tvStatus, mProgressBar, btnPause, canCel, downoad);
        down.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    public void pauseVideo() {
        down.pauseVideo();
    }

    public void resumeVideo() {
        down.resumeVideo();
    }

    public void CancelVideo() {
        down.cancel(true);
    }

}
