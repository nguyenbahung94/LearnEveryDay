package com.tma.stockmarket.ui.presenter.viewpresenter;

import retrofit2.Retrofit;



public interface UserGetExchangeRatePresenter {
    void convertCurrency(Retrofit retrofit, int value, String from, String to);
}
