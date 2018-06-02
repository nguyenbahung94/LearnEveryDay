package com.example.nbhung.projectdirection.Ui.Presenter;

import android.content.Context;
import android.location.Location;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Retrofit;



public interface Presenter {
    void getLocation(Location myLocation, GoogleMap mMap);

    void direction(Retrofit retrofit, LatLng latOrigin, LatLng latEnd, GoogleMap mMap, TextView tvDistance, TextView tvDuration);

    void getWeather(Retrofit retrofit, LatLng latLng);

    void getPlaceAround(Retrofit retrofitPlace, GoogleMap mMap, LatLng latLng, String type, String radius, Context context);


}
