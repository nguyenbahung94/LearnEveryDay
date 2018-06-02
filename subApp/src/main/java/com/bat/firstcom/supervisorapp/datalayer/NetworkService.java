package com.bat.firstcom.supervisorapp.datalayer;


import com.bat.firstcom.supervisorapp.BuildConfig;
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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by tung phan on 04-18-2017.
 * implement call to the interface here.
 */
public interface NetworkService {

    String BASE_URL = BuildConfig.SUP_APP_BASE_URL;

    //return the brands from api
    @GET("api/brand")
    Observable<GetBrandResponse> getBrands();

    //login to the app.
    @POST("api/login")
    Observable<LoginResponse> login(@Header("brand") int brand, @Body LoginRequest loginRequest);

    //login to the app.
    @PUT("api/password")
    Observable<ChangePasswordResponse> postChangePassword(@Header("Authorization") String authorizationToken
            ,@Header("brand") int brand, @Body ChangePasswordRequest changePasswordRequest);

    //get the pst list in the coaching flow.
    @GET("api/coaching/member")
    Observable<GetPSTListResponse> getCoachingPSTList(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //get the pst list in the checking flow
    @GET("api/checking/member")
    Observable<GetPSTListResponse> getCheckingPSTList(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //get outlet base on pstid.
    @GET("api/ba/{id}/outlet")
    Observable<GetOutletResponse> getOutlets(@Path("id") String pstid
            , @Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //get outlet by week base on pstid
    @GET("api/ba/{id}/outlet-by-week")
    Observable<GetOutletResponse> getOutletsByWeek(@Path("id") String pstid
            , @Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //get questions list in coaching flow
    @GET("api/coaching/question")
    Observable<GetQuestionResponse> getCoachingQuestions(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //get questions list in checking flow
    @GET("api/checking/question")
    Observable<GetQuestionResponse> getCheckingQuestions(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //post coaching marking result to server
    @POST("api/coaching")
    Observable<CoachingResponse> postCoaching(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand, @Body CoachingRequest coachingRequest);

    //post coaching marking result to server
    @POST("api/checking")
    Observable<CheckingResponse> postChecking(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand, @Body CheckingRequest checkingRequest);

    //get coaching report base on pstid
    @GET("api/ba/{id}/coaching-report")
    Observable<CoachingReportResponse> getCoachingReport(@Path("id") String pstId
            , @Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //get checking report base on pstid
    @GET("api/ba/{id}/checking-report")
    Observable<CheckingReportResponse> getCheckingReport(@Path("id") String pstId
            , @Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //return the outlet info base on outletpositionid
    @GET("api/change-request/{id}")
    Observable<GetChangeOutletResponse> getChangeOutlet(@Path("id") String outletLocationId
            , @Header("Authorization") String authorizationToken
            , @Header("brand") int brand);

    //return the outlet info base on outletpositionid
    @POST("/api/change-request")
    Observable<PostChangeOutletResponse> postChangeOutlet(@Header("Authorization") String authorizationToken
            , @Header("brand") int brand, @Body PostChangeOutletRequest postChangeOutletRequest);

}
