package com.example.nbhung.demotwitter.domain.repository;

import com.example.nbhung.demotwitter.datalayer.model.LoginResponse;
import com.example.nbhung.demotwitter.datalayer.model.RegisterResponse;

import io.reactivex.Observable;

public interface TwitterAppRepository {
    Observable<LoginResponse> login(String userName, String password);

    Observable<RegisterResponse> register(String email,String userName,String passWord,String passwordConf);

}
