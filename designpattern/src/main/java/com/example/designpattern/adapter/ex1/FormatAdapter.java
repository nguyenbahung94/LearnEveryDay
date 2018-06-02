package com.example.designpattern.adapter.ex1;

import android.util.Log;

/**
 * Created by nbhung on 3/19/2018.
 */

public class FormatAdapter implements MediaPlayer {
    private MediaPackage mediaPackage;

    public FormatAdapter(MediaPackage mediaPackage) {
        this.mediaPackage = mediaPackage;
    }

    @Override
    public void play(String fileName) {
        Log.e("Using adapter here", "");
        mediaPackage.playFile(fileName);
    }
}
