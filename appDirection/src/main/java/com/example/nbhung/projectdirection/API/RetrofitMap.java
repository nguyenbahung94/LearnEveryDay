package com.example.nbhung.projectdirection.API;


import com.example.nbhung.projectdirection.Ui.Model.Routes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;



public interface RetrofitMap {
    @GET("maps/api/directions/json")
    Observable<Routes> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

}
