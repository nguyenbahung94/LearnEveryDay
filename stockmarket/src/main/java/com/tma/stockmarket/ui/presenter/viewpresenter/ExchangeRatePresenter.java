package com.tma.stockmarket.ui.presenter.viewpresenter;

import android.content.Context;

import retrofit2.Retrofit;



public interface ExchangeRatePresenter {
  void getExchangeRate(Retrofit retrofit, Context context);
}
