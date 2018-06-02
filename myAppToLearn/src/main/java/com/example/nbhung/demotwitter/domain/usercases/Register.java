package com.example.nbhung.demotwitter.domain.usercases;

import com.example.nbhung.demotwitter.datalayer.model.RegisterResponse;
import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class Register extends UseCase<RegisterResponse, Register.Params> {
    private TwitterAppDataRepository twitterAppDataRepository;

    public Register(TwitterAppDataRepository twitterAppDataRepository) {
        super();
        this.twitterAppDataRepository = twitterAppDataRepository;
    }

    @Override
    Observable<RegisterResponse> buildUseCaseObservable(Params params) {
        return twitterAppDataRepository.register(params.email, params.userName, params.passWord, params.confirmPassWord);
    }

    @Override
    Single<RegisterResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {
        private final String email;
        private final String userName;
        private final String passWord;
        private final String confirmPassWord;

        public Params(String email, String userName, String passWord, String confirmPassWord) {
            this.email = email;
            this.userName = userName;
            this.passWord = passWord;
            this.confirmPassWord = confirmPassWord;
        }

        public static Params forRegister(String userName, String email, String passWord, String confirmPassWord) {
            return new Params(userName, email, passWord, confirmPassWord);
        }
    }
}
