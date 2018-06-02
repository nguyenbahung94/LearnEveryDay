package com.ekakashi.greenhouse.features.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.features.login_screen.LoginActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type != null)
            App.notificationType = Integer.valueOf(type);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,
                        Prefs.getInstance(getApplicationContext()).isFirstTimeLaunch() ? TutorialActivity.class : LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.left_to_right_enter, R.anim.right_to_left_exit);
            }
        }, 800);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
