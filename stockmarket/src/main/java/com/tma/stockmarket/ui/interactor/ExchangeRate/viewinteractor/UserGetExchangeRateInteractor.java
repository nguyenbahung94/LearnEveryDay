package com.tma.stockmarket.ui.interactor.ExchangeRate.viewinteractor;

import retrofit2.Retrofit;



public interface UserGetExchangeRateInteractor {
    void convertCurrency(Retrofit retrofit, int value, String from, String to,callback call);

    interface callback {
        void success(Double result);

        void failed();

        void message();
    }
}
