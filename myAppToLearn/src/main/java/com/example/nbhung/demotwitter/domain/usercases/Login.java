package com.example.nbhung.demotwitter.domain.usercases;

import com.example.nbhung.demotwitter.datalayer.model.LoginResponse;
import com.example.nbhung.demotwitter.domain.repository.TwitterAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class Login extends UseCase<LoginResponse, Login.Params> {
    private TwitterAppRepository repository;

    public Login(TwitterAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<LoginResponse> buildUseCaseObservable(Params params) {
        return repository.login(params.userName, params.passWord);
    }

    @Override
    Single<LoginResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {
        private final String userName;
        private final String passWord;

        private Params(String userName, String passWord) {
            this.userName = userName;
            this.passWord = passWord;
        }

        public static Params forLogin(String userName, String passWord) {
            return new Params(userName, passWord);
        }
    }
}
