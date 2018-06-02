package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetQuestionResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tung Phan on 25-Jul-17.
 */

public class GetCoachingQuestions extends UseCase<GetQuestionResponse,GetCoachingQuestions.Params>{

    private SupAppRepository repository;

    public GetCoachingQuestions(SupAppRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    Observable<GetQuestionResponse> buildUseCaseObservable(Params params) {
        return repository.getCoachingQuestions(params.authorizationToken, params.brand);
    }
    @Override
    Single<GetQuestionResponse> buildUseCaseSingle(Params params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Params params) {
        return null;
    }

    public static final class Params {

        private final int brand;
        private final String authorizationToken;

        private Params(String authorizationToken, int brand) {
            this.brand = brand;
            this.authorizationToken = authorizationToken;
        }

        public static Params forGetCoachingQuestions(String authorizationToken, int brand) {
            return new Params(authorizationToken, brand);
        }
    }
}
