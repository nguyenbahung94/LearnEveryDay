package com.tma.videotraining.di.module;

import android.content.Context;
import android.widget.MediaController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nbhung on 7/6/2017.
 */
@Module
public class MediaModule {
    @Singleton
    @Provides
    MediaController proMediaController(Context context) {
        return new MediaController(context);
    }
}
