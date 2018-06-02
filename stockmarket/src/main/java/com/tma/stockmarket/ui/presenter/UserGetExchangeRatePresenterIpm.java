package com.tma.stockmarket.ui.presenter;

import com.tma.stockmarket.ui.interactor.ExchangeRate.UserGetExchangeRateInteractorIpm;
import com.tma.stockmarket.ui.interactor.ExchangeRate.viewinteractor.UserGetExchangeRateInteractor;
import com.tma.stockmarket.ui.presenter.viewpresenter.UserGetExchangeRatePresenter;
import com.tma.stockmarket.ui.view.activity.UserGetExchangeRateView;

import javax.inject.Inject;

import retrofit2.Retrofit;



public class UserGetExchangeRatePresenterIpm implements UserGetExchangeRatePresenter, UserGetExchangeRateInteractor.callback {
    private UserGetExchangeRateView mView;
    private UserGetExchangeRateInteractorIpm userGetInteractor;

    @Inject
    public UserGetExchangeRatePresenterIpm(UserGetExchangeRateView mView, UserGetExchangeRateInteractorIpm userGetInteractor) {
        this.mView = mView;
        this.userGetInteractor = userGetInteractor;
    }

    @Override
    public void success(Double result) {
          mView.resultExchangeRate(result);
    }

    @Override
    public void failed() {
       mView.failed();
    }

    @Override
    public void message() {

    }

    @Override
    public void convertCurrency(Retrofit retrofit, int value, String from, String to) {
        userGetInteractor.convertCurrency(retrofit, value, from, to, this);
    }
}
