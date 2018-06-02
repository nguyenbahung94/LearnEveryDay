package com.tma.stockmarket.di.module;

import com.tma.stockmarket.di.component.Scope.CustomScope;
import com.tma.stockmarket.ui.interactor.ExchangeRate.UserGetExchangeRateInteractorIpm;
import com.tma.stockmarket.ui.presenter.UserGetExchangeRatePresenterIpm;
import com.tma.stockmarket.ui.view.activity.UserGetExchangeRateView;

import dagger.Module;
import dagger.Provides;


@Module
public class UserGetExchangeRateModule {
    private UserGetExchangeRateView mView;

    public UserGetExchangeRateModule(UserGetExchangeRateView mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    UserGetExchangeRatePresenterIpm proUserGetExchangeRatePresenterIpm(UserGetExchangeRateInteractorIpm userGetExchangeRateInteractorIpm) {
        return new UserGetExchangeRatePresenterIpm(mView, userGetExchangeRateInteractorIpm);
    }

    @Provides
    @CustomScope
    UserGetExchangeRateInteractorIpm proUserGetExchangeRateInteractorIpm() {
        return new UserGetExchangeRateInteractorIpm();
    }

}
