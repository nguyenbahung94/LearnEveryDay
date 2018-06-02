package com.tma.stockmarket.di.module;

import com.tma.stockmarket.di.component.Scope.CustomScope;
import com.tma.stockmarket.ui.interactor.ExchangeRate.ExchangeRateInteractorIpm;
import com.tma.stockmarket.ui.presenter.ExchangeRatePresenterIpm;
import com.tma.stockmarket.ui.view.activity.ExchangeRateView;

import dagger.Module;
import dagger.Provides;


@Module
public class MainViewModule {
    private ExchangeRateView exchangeRateView;

    public MainViewModule(ExchangeRateView exchangeRateView) {
        this.exchangeRateView = exchangeRateView;
    }

    @Provides
    @CustomScope
    ExchangeRatePresenterIpm proExchangeRatePresenterIpm(
            ExchangeRateInteractorIpm exchangeRateInteractorIpm) {
        return new ExchangeRatePresenterIpm(exchangeRateView, exchangeRateInteractorIpm);
    }

    @Provides
    @CustomScope
    ExchangeRateInteractorIpm proExchangeRateInteractorIpm() {
        return new ExchangeRateInteractorIpm();
    }
}
