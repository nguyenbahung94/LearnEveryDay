package com.bat.firstcom.supervisorapp.domain.usecases;

import com.bat.firstcom.supervisorapp.datalayer.model.GetQuestionResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by tung.phan on 5/31/2017.
 */

public class GetCheckingQuestions extends UseCase<GetQuestionResponse, GetCheckingQuestions.Params> {

    private SupAppRepository dataRepository;

    public GetCheckingQuestions(SupAppRepository dataRepository) {
        super();
        this.dataRepository = dataRepository;
    }

    @Override
    Observable<GetQuestionResponse> buildUseCaseObservable(Params params) {
        return dataRepository.getCheckingQuestions(params.authorizationToken, params.brand);
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

        public static Params forGetCheckingQuestions(String authorizationToken, int brand) {
            return new Params(authorizationToken, brand);
        }
    }
}
