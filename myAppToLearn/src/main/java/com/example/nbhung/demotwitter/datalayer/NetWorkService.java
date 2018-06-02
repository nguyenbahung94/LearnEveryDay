package com.example.nbhung.demotwitter.datalayer;

import com.example.nbhung.demotwitter.datalayer.model.LoginResponse;
import com.example.nbhung.demotwitter.datalayer.model.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetWorkService {
    String BASE_URL = "http://185.201.8.65:4000/";

    @FormUrlEncoded
    @POST("login")
    Observable<LoginResponse> login(@Field("username") String userName, @Field("password") String passWord);
    @FormUrlEncoded
    @POST("users")
    Observable<RegisterResponse> register(@Field("email") String email,@Field("username") String userName,@Field("password") String password,@Field("passwordConf") String passwordConf);
}
