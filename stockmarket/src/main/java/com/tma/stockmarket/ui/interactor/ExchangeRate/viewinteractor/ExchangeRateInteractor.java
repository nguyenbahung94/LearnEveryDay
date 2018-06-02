package com.tma.stockmarket.ui.interactor.ExchangeRate.viewinteractor;

import android.content.Context;

import com.tma.stockmarket.ui.model.ExchangeRate;

import java.util.List;

import retrofit2.Retrofit;



public interface ExchangeRateInteractor {
    void getExchangeRate(Retrofit retrofit, Context context, callback call);

    interface callback {
        void resultExchangeRate(List<ExchangeRate> exchangeRateList);

        void failed(int i);

        void message();
    }
}
