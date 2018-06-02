package com.tma.stockmarket.ui.interactor.ExchangeRate;

import android.util.Log;

import com.tma.stockmarket.api.GetExchangeRate;
import com.tma.stockmarket.ui.interactor.ExchangeRate.viewinteractor.UserGetExchangeRateInteractor;
import com.tma.stockmarket.ui.model.UserGetExchangeRate;
import com.tma.stockmarket.utils.url;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class UserGetExchangeRateInteractorIpm implements UserGetExchangeRateInteractor {
    private Double result;

    @Override
    public void convertCurrency(Retrofit retrofit, int value, String from, String to, final callback call) {
        retrofit.create(GetExchangeRate.class).convertCurrency(from, to, value, url.API_2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserGetExchangeRate>() {
                    @Override
                    public void onCompleted() {
                        call.success(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        call.failed();
                    }

                    @Override
                    public void onNext(UserGetExchangeRate userGetExchangeRate) {
                        result = userGetExchangeRate.getValue();
                        Log.e("result", String.valueOf(userGetExchangeRate.getValue()));
                    }
                });
    }
}
