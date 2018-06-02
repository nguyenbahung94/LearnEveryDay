package com.example.nbhung94.transferbitmapbetweenactivities;

import android.graphics.Bitmap;

/**
 * Created by nbhung on 12/5/2017.
 */

public class BitmapHelper {
    private Bitmap bitmap = null;
    private static final BitmapHelper instance = new BitmapHelper();

    public BitmapHelper() {
    }

    public static BitmapHelper getInstance() {
        return instance;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
