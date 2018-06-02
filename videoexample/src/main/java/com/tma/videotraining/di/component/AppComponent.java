package com.tma.videotraining.di.component;

import com.tma.videotraining.di.module.AppModule;
import com.tma.videotraining.di.module.DownloadManagerModule;
import com.tma.videotraining.di.module.ViewModule;
import com.tma.videotraining.ui.main.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nbhung on 7/6/2017.
 */
@Singleton
@Component(modules = {AppModule.class, DownloadManagerModule.class, ViewModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
