package com.tma.videotraining.ui.presenter;

import com.tma.videotraining.ui.interactor.PlayVideoInteractorImp;
import com.tma.videotraining.ui.view.MainView;

import javax.inject.Inject;

/**
 * Created by nbhung on 7/6/2017.
 */

public class PlayVideoPresenterImp implements PlayVideoPresenter {
    private MainView mView;
    private PlayVideoInteractorImp playInteractor;

    @Inject
    public PlayVideoPresenterImp(MainView mView, PlayVideoInteractorImp playVideoInteractorImp) {
        this.mView = mView;
        playInteractor = playVideoInteractorImp;
    }
}
