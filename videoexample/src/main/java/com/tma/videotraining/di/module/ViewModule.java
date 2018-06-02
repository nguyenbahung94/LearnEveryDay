package com.tma.videotraining.di.module;

import com.tma.videotraining.ui.interactor.PlayVideoInteractorImp;
import com.tma.videotraining.ui.view.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nbhung on 7/6/2017.
 */
@Module
public class ViewModule {
    private MainView mView;

    public ViewModule(MainView mView) {
        this.mView = mView;
    }

    @Provides
    @Singleton
    MainView proMainView() {
        return mView;
    }

    @Provides
    @Singleton
    PlayVideoInteractorImp proPlayVideoInteractorImp() {
        return new PlayVideoInteractorImp();
    }
}
