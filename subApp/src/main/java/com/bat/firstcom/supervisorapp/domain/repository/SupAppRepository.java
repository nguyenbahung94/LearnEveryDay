package com.bat.firstcom.supervisorapp.domain.repository;

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

import io.reactivex.Observable;

/**
 * Created by tung.phan on 5/31/2017.
 */

public interface SupAppRepository {

    Observable<GetBrandResponse> getBrands();

    Observable<LoginResponse> login(int brand, LoginRequest loginRequest);

    Observable<ChangePasswordResponse> postChangePassword(String authorizationToken, int brand
            , ChangePasswordRequest changePasswordRequest);

    Observable<GetPSTListResponse> getCoachingPstList(String authorizationToken, int brand);

    Observable<GetQuestionResponse> getCoachingQuestions(String authorizationToken
            , int brand);

    Observable<GetQuestionResponse> getCheckingQuestions(String authorizationToken
            , int brand);

    Observable<GetPSTListResponse> getCheckingPSTList(String authorizationToken, int brand);

    Observable<GetOutletResponse> getOutlets(String pstid, String authorizationToken, int brand);

    Observable<GetOutletResponse> getOutletsByWeek(String pstid, String authorizationToken, int brand);

    Observable<CoachingResponse> postCoaching(String authorizationToken
            , int brand, CoachingRequest coachingRequest);

    Observable<CheckingResponse> postChecking(String authorizationToken
            , int brand, CheckingRequest checkingRequest);

    Observable<CoachingReportResponse> getCoachingReport(String pstId, String authorizationToken
            , int brand);

    Observable<CheckingReportResponse> getCheckingReport(String pstId, String authorizationToken
            , int brand);

    Observable<GetChangeOutletResponse> getChangeOutlet(String outletLocationId
            , String authorizationToken
            , int brand);

    Observable<PostChangeOutletResponse> postChangeOutlet(String authorizationToken, int brand
            , PostChangeOutletRequest postChangeOutletRequest);

}
