package com.example.nbhung.projectdirection.Di.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;


@Module
class AppModule {
    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


}
