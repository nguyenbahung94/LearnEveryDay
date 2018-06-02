package com.example.nbhung.demotwitter.datalayer.repository;

import com.example.nbhung.demotwitter.datalayer.NetWorkService;
import com.example.nbhung.demotwitter.datalayer.model.LoginResponse;
import com.example.nbhung.demotwitter.datalayer.model.RegisterResponse;
import com.example.nbhung.demotwitter.domain.repository.TwitterAppRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class TwitterAppDataRepository implements TwitterAppRepository {
    private NetWorkService netWorkService;

    @Inject
    public TwitterAppDataRepository(NetWorkService netWorkService) {
        this.netWorkService = netWorkService;
    }

    @Override
    public Observable<LoginResponse> login(String userName, String password) {
        return netWorkService.login(userName, password);
    }

    @Override
    public Observable<RegisterResponse> register(String email, String userName, String password, String passWordConf) {
        return netWorkService.register(email, userName, password, passWordConf);
    }
}
