package com.tma.stockmarket.ui.presenter;

import android.content.Context;

import com.tma.stockmarket.ui.interactor.ExchangeRate.ExchangeRateInteractorIpm;
import com.tma.stockmarket.ui.interactor.ExchangeRate.viewinteractor.ExchangeRateInteractor;
import com.tma.stockmarket.ui.model.ExchangeRate;
import com.tma.stockmarket.ui.presenter.viewpresenter.ExchangeRatePresenter;
import com.tma.stockmarket.ui.view.activity.ExchangeRateView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;



public class ExchangeRatePresenterIpm implements ExchangeRatePresenter, ExchangeRateInteractor.callback {
    private ExchangeRateView mView;
    private ExchangeRateInteractorIpm exchangeRateInteractorIpm;

    @Inject
    public ExchangeRatePresenterIpm(ExchangeRateView mView,
                                    ExchangeRateInteractorIpm exchangeRateInteractorIpm) {
        this.mView = mView;
        this.exchangeRateInteractorIpm = exchangeRateInteractorIpm;
    }

    @Override
    public void getExchangeRate(Retrofit retrofit, Context context) {
        exchangeRateInteractorIpm.getExchangeRate(retrofit, context, this);
    }


    @Override
    public void resultExchangeRate(List<ExchangeRate> exchangeRateList) {
        if (mView != null) {
            mView.listExchangeRate(exchangeRateList);
        }


    }

    @Override
    public void failed(int i) {

        if (mView != null) {
            if (i == 0) {
                mView.message("get exchange Rate error");
            }
        }
    }

    @Override
    public void message() {

    }
}
