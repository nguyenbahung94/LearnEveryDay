package com.example.nbhung.projectdirection.API;


import com.example.nbhung.projectdirection.Ui.Model.ResultsSearchPlace;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;



public interface RetrofitSearchPlace {
    @GET("maps/api/place/nearbysearch/json?")
    Observable<ResultsSearchPlace> getPlace(@Query("location") String location, @Query("radius") String radius, @Query("type") String type, @Query("key") String key);
}
