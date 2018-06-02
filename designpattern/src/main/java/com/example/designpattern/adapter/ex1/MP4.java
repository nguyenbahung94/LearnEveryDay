package com.example.designpattern.adapter.ex1;

import android.util.Log;

/**
 * Created by nbhung on 3/19/2018.
 */

public class MP4 implements MediaPackage {
    @Override
    public void playFile(String fileName) {
        Log.e("Playing mp4 file ", ""+ fileName);
    }
}
