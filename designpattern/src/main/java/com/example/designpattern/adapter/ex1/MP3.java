package com.example.designpattern.adapter.ex1;

import android.util.Log;

/**
 * Created by nbhung on 3/19/2018.
 */

public class MP3 implements MediaPlayer {
    @Override
    public void play(String fileName) {
        Log.e("Playing mp3 file ", ""+ fileName);
    }
}
