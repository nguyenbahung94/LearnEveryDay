package com.example.java8;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassJava8.exampleSort();
        // TODO: 3/9/2018 ,on click listener
        Button btn = findViewById(R.id.btnClick);
        btn.setOnClickListener(view -> Toast.makeText(this, "test lambda expression", Toast.LENGTH_SHORT).show());
        // TODO: 3/9/2018 ,runnable and thread
        Runnable r = () -> Toast.makeText(this, "test runnable and thread", Toast.LENGTH_SHORT).show();
        new Thread(r).start();

    }
}
