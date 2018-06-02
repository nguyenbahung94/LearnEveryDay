package com.bat.firstcom.supervisorapp.presentation.editoutlet;

import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.datalayer.model.GetChangeOutletResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletResponse;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.domain.usecases.GetChangeOutlet;
import com.bat.firstcom.supervisorapp.domain.usecases.PostChangeOutlet;
import com.bat.firstcom.supervisorapp.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class EditOutletPresenter extends BasePresenter<EditOutletView> {

    private final int LACK_OF_OUTLET_LOCATION_ID = 201;
    private final int INVALID_REQUEST_TYPE = 202;
    private final int INVALID_REASON = 203;
    private final int INVALID_TIME = 204;
    private final int FROM_TIME_MUST_BE_LESS_THAN_TO_TIME = 205;
    private final int INVALID_OWNER_INFORMATION = 206;
    private final int INVALID_VISITOR_QUANTITY = 207;
    private final int CANNOT_ADD_REQUEST = 200;
    private final int NO_ERROR_CODE = 0;
    private GetChangeOutlet getChangeOutlet;
    private PostChangeOutlet postChangeOutlet;

    @Inject
    public EditOutletPresenter(SupAppDataRepository dataRepository) {
        getChangeOutlet = new GetChangeOutlet(dataRepository);
        postChangeOutlet = new PostChangeOutlet(dataRepository);
    }

    void getChangeOutlet(String outletLocationId, String token, int brand) {
        getView().showLoading();
        disposable.add(getChangeOutlet.param(GetChangeOutlet.Params
                .forGetChangeOutlet(outletLocationId, token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetChangeOutletObserver()));
    }

    void postChangeOutlet(String token, int brand, PostChangeOutletRequest postChangeOutletRequest) {
        getView().showLoading();
        disposable.add(postChangeOutlet.param(PostChangeOutlet.Params
                .forPostOutletChange(token, brand, postChangeOutletRequest))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new PostChangeOutletObserver()));
    }

    private final class GetChangeOutletObserver extends DisposableObserver<GetChangeOutletResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_QUESTION_LIST);
        }

        @Override
        public void onNext(GetChangeOutletResponse response) {
            if (response.getSuccess()) {
                getView().updateLayoutFrom(response.getChangeOutletDatum());
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_POST_COACHING);
            }
        }
    }

    private final class PostChangeOutletObserver extends DisposableObserver<PostChangeOutletResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_QUESTION_LIST);
        }

        @Override
        public void onNext(PostChangeOutletResponse response) {
            //TODO: should using pattern to shorter code.
            if (response.isSuccess()) {
                getView().postChangeOutletSuccessFully();
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_POST_COACHING);
            }
        }
    }
}
