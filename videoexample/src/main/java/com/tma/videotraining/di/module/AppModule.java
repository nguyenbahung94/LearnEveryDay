package com.tma.videotraining.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nbhung on 7/6/2017.
 */
@Module
public class AppModule {
   final private Application mApplication;
   final private Context context;

    public AppModule(Application mApplication, Context context) {
        this.mApplication = mApplication;
        this.context = context;
    }

    @Provides
    @Singleton
    Application proApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context proContext() {
        return context;
    }
}
