package com.tma.videotraining.ui.interactor;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import com.tma.videotraining.ui.main.fragment.VideoFragment;

/**
 * Created by nbhung on 7/10/2017.
 */

public class VideoFragmentInteractor {
    public VideoFragmentInteractor() {
    }

    public void playVideo(String url, final VideoView videoView, MediaController mController, final VideoFragment callback) {
        mController.setAnchorView(videoView);
        videoView.setMediaController(mController);

        Uri video = Uri.parse(url);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                callback.playSuccess();
                videoView.start();
            }
        });
    }
}
