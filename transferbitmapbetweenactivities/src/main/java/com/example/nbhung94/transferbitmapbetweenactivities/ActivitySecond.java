package com.example.nbhung94.transferbitmapbetweenactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ActivitySecond extends AppCompatActivity {
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imgView = findViewById(R.id.secondActivity);
        imgView.setImageBitmap(BitmapHelper.getInstance().getBitmap());
    }
}
