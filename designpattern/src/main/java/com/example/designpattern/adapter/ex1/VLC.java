package com.example.designpattern.adapter.ex1;

import android.util.Log;

/**
 * Created by nbhung on 3/19/2018.
 */

public class VLC implements MediaPackage {
    @Override
    public void playFile(String fileName) {
        Log.e("Playing VLC file ", "" + fileName);
    }
}
