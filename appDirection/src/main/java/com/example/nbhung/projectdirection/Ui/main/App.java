package com.example.nbhung.projectdirection.Ui.main;

import android.app.Application;

import com.example.nbhung.projectdirection.Di.component.ActivityComponent;



public class App extends Application {
    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
  //      mActivityComponent = DaggerActivityComponent.builder().appModule(new AppModule(this)).build();
    }

    public ActivityComponent proActivityComponent() {
        return mActivityComponent;
    }
}
