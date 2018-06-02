package com.example.nbhung.projectdirection.API;


import com.example.nbhung.projectdirection.Ui.Model.InforWeather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;




public interface RetrofitWeather {
    @GET("data/2.5/weather")
    Observable<InforWeather> getWeather(@Query("lat") String lat, @Query("lon") String longt, @Query("units") String units, @Query("appid") String tam);

}

