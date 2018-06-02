package com.bat.firstcom.supervisorapp.datalayer.repository;

import com.bat.firstcom.supervisorapp.datalayer.NetworkService;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingReportResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingReportResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.GetBrandResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.GetChangeOutletResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.GetOutletResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.GetPSTListResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.GetQuestionResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.PostChangeOutletResponse;
import com.bat.firstcom.supervisorapp.domain.repository.SupAppRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by tung.phan on 5/31/2017.
 */

public class SupAppDataRepository implements SupAppRepository {

    private NetworkService networkService;

    @Inject
    public SupAppDataRepository(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public Observable<GetBrandResponse> getBrands() {
        return networkService.getBrands();
    }

    @Override
    public Observable<LoginResponse> login(int brand, LoginRequest loginRequest) {
        return networkService.login(brand, loginRequest);
    }

    @Override
    public Observable<ChangePasswordResponse> postChangePassword(String authorizationToken
            , int brand
            , ChangePasswordRequest changePasswordRequest) {
        return networkService.postChangePassword(authorizationToken, brand, changePasswordRequest);
    }

    @Override
    public Observable<GetPSTListResponse> getCoachingPstList(String authorizationToken
            , int brand) {
        return networkService.getCoachingPSTList(authorizationToken, brand);
    }

    @Override
    public Observable<GetQuestionResponse> getCoachingQuestions(String authorizationToken
            , int brand) {
        return networkService.getCoachingQuestions(authorizationToken, brand);
    }

    @Override
    public Observable<GetQuestionResponse> getCheckingQuestions(String authorizationToken
            , int brand) {
        return networkService.getCheckingQuestions(authorizationToken, brand);
    }

    @Override
    public Observable<GetPSTListResponse> getCheckingPSTList(String authorizationToken
            , int brand) {
        return networkService.getCheckingPSTList(authorizationToken, brand);
    }

    @Override
    public Observable<GetOutletResponse> getOutlets(String pstid, String authorizationToken
            , int brand) {
        return networkService.getOutlets(pstid, authorizationToken, brand);
    }

    @Override
    public Observable<GetOutletResponse> getOutletsByWeek(String pstid, String authorizationToken
            , int brand) {
        return networkService.getOutletsByWeek(pstid, authorizationToken, brand);
    }

    @Override
    public Observable<CheckingResponse> postChecking(String authorizationToken, int brand
            , CheckingRequest checkingRequest) {
        return networkService.postChecking(authorizationToken, brand, checkingRequest);
    }

    @Override
    public Observable<CoachingResponse> postCoaching(String authorizationToken, int brand
            , CoachingRequest coachingRequest) {
        return networkService.postCoaching(authorizationToken, brand, coachingRequest);
    }

    @Override
    public Observable<CoachingReportResponse> getCoachingReport(String pstId
            , String authorizationToken, int brand) {
        return networkService.getCoachingReport(pstId, authorizationToken, brand);
    }

    @Override
    public Observable<CheckingReportResponse> getCheckingReport(String pstId
            , String authorizationToken, int brand) {
        return networkService.getCheckingReport(pstId, authorizationToken, brand);
    }

    @Override
    public Observable<GetChangeOutletResponse> getChangeOutlet(String outletLocationId
            , String authorizationToken, int brand) {
        return networkService.getChangeOutlet(outletLocationId, authorizationToken, brand);
    }

    @Override
    public Observable<PostChangeOutletResponse> postChangeOutlet(String authorizationToken
            , int brand, PostChangeOutletRequest postChangeOutletRequest) {
        return networkService.postChangeOutlet(authorizationToken, brand, postChangeOutletRequest);
    }
}
