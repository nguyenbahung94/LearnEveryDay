package com.tma.videotraining.di.module;

import android.app.DownloadManager;
import android.net.Uri;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nbhung on 7/6/2017.
 */
@Module
public class DownloadManagerModule {
   final private String url;

    public DownloadManagerModule(String url) {
        this.url = url;
    }

    @Provides
    DownloadManager.Request proRequest() {
        return new DownloadManager.Request(Uri.parse(url));
    }

    @Provides
    @Singleton
    DownloadManager.Query proQuery() {
        return new DownloadManager.Query();
    }
}
