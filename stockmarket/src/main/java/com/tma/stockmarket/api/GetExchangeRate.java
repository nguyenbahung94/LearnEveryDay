package com.tma.stockmarket.api;

import com.tma.stockmarket.ui.model.ExchangeRate;
import com.tma.stockmarket.ui.model.UserGetExchangeRate;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;



public interface GetExchangeRate {
    @GET("1.0.2/quotes?")
    Observable<ExchangeRate[]> getExchangeRate(@Query("pairs") String pairs, @Query("api_key") String key);

    @GET("1.0.2/convert?")
    Observable<UserGetExchangeRate> convertCurrency(@Query("from") String from, @Query("to") String to, @Query("quantity") int quantity, @Query("api_key") String key);
}
