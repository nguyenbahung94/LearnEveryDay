package com.example.nbhung.demotwitter;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nbhung.demotwitter.di.component.AppComponent;
import com.example.nbhung.demotwitter.di.component.DaggerAppComponent;
import com.example.nbhung.demotwitter.di.module.NetworkModule;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static App appInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        initInjector();
    }

    public static App getAppInstance() {
        return appInstance;
    }

    private void initInjector() {
        this.appComponent = DaggerAppComponent.builder().networkModule(new NetworkModule(this)).build();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((App) context.getApplicationContext()).appComponent;
    }
}
