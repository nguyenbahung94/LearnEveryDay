package com.tma.stockmarket.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application proApplication() {
        return mApplication;
    }

    @Provides
    Context proContext() {
        return mApplication.getApplicationContext();
    }
}
